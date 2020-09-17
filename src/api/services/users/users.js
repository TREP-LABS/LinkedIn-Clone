import db from '../../models';
import {
  getUserByEmail,
  generateToken,
  formatUserData,
  verifyPassword,
  getResetPasswordDetails,
  sendResetPasswordEmail,
  hashResetToken,
  getUserByResetPasswordToken,
} from './helpers';
import { ServiceError } from '../helpers';

const { User, Profile } = db;

/**
 * Creates a new user.
 * @param {Object} data Request data from the controller.
 * @returns {String} The created user token.
 */
export const register = async (data) => {
  const { firstname, lastname, email, password } = data;

  let user = await getUserByEmail(User, email);

  if (user) throw new ServiceError('User with this email already exist.', 400);

  let newUser = new User({
    firstname,
    lastname,
    email,
    password,
  });

  newUser = await newUser.save();

  const profile = new Profile({ user: newUser._id });

  await profile.save();

  newUser.profile = profile._id;

  await newUser.save();

  const token = generateToken(formatUserData(newUser));

  return token;
};

/**
 * Login a user.
 * @param {Object} data Request data fromt the controller.
 * @returns {Object} Object containing the logged user token and user object.
 */
export const login = async (data) => {
  const { email, password } = data;

  const user = await getUserByEmail(User, email);

  if (!user) throw new ServiceError('User with this email does not exist.', 404);

  if (!verifyPassword(password, user.password)) {
    throw new ServiceError('Incorrect password.', 400);
  }

  const token = generateToken(formatUserData(user));

  return { token, user: formatUserData(user) };
};

/**
 * Request password reset token.
 * @param {Object} req The request object.
 * @param {String} email The email of the user.
 * @returns {String} Reset password token.
 */
export const forgotPassword = async (req, email) => {
  const user = await getUserByEmail(User, email);

  if (!user) throw new ServiceError('User with this email does not exist.', 404);

  const { resetToken, resetTokenHash, resetPasswordExpire } = getResetPasswordDetails();

  const sendEmail = await sendResetPasswordEmail(req, user, resetToken);

  if (sendEmail) {
    user.resetPasswordToken = resetTokenHash;
    user.resetPasswordExpire = resetPasswordExpire;
  }

  await user.save({ validateBeforeSave: false });

  return resetToken;
};

/**
 * Resets the user's password.
 * @param {String} resetToken The user's password reset token.
 * @param {String} password The user's password.
 * @returns {Boolean} Truthy value if the user's password is reset successfully.
 */
export const resetPassword = async (resetToken, password) => {
  const resetPasswordToken = hashResetToken(resetToken);

  const user = await getUserByResetPasswordToken(User, {
    resetPasswordToken,
    resetPasswordExpire: { $gt: Date.now() },
  });

  if (!user) throw new ServiceError('Invalid reset token.', 400);

  user.password = password;
  user.resetPasswordToken = undefined;
  user.resetPasswordExpire = undefined;

  await user.save();

  return true;
};

/**
 * Updates a string field given the field name and the value.
 * @param {Object} user The authenticated user object.
 * @param {String} field The field in the profile model to update.
 * @param {String} value The value of the field to updated.
 */
export const updateUserDetails = async (authUser, field, value) => {
  const user = await getUserByEmail(User, authUser.email);
  if (!user) throw new ServiceError('User does not exist.', 404);

  user[field] = value;
  await user.save();

  return formatUserData(user);
};
