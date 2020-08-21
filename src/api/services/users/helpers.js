import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';
import crypto from 'crypto';
import sendEmail from '../../utils/sendEmail';

/**
 * Format the user data to be returned to the client.
 * @param {Object} userData The raw user data gotten from the database
 * @returns {Object} The formatted user data.
 */
export const formatUserData = (userData) => ({
  id: userData._id,
  firstname: userData.firstname,
  lastname: userData.lastname,
  email: userData.email,
  slug: userData.slug,
});

/**
 * Generates a token.
 * @param {Object} payload The information to embed in token.
 * @returns {String} The token.
 */
export const generateToken = (payload) =>
  jwt.sign(payload, process.env.JSON_WEB_TOKEN_SECRET, {
    expiresIn: process.env.JSON_WEB_TOKEN_EXPIRE,
  });

/**
 * Get a single user from the database with an email address.
 * @param {Object} User The query interface for users in the database.
 * @param {String} email The email of the user to get.
 * @returns {Object} The user gotten from the database.
 */
export const getUserByEmail = async (User, email) => {
  const user = await User.findOne({ email: email.toLowerCase() });
  return user;
};

/**
 * Get a user from the database with their reset token details.
 * @param {Object} User The query interface for users in the database.
 * @param {Object} resetPasswordDetails Contains the rest token hash, and expiry date.
 * @returns {Object} The user gotten from the database.
 */
export const getUserByResetPasswordToken = async (User, resetPasswordDetails) => {
  const user = await User.findOne({ ...resetPasswordDetails });
  return user;
};

/**
 * Checks if a user input password is correct.
 * @param {String} textPassword The user input password.
 * @param {String} hashedPassword The hashed password.
 * @returns {Boolean} Truthy or falsy values representing if the password is correct or not.
 */
export const verifyPassword = (textPassword, hashedPassword) =>
  bcrypt.compareSync(textPassword, hashedPassword);

/**
 * Hash the reset token. So that it can be saved securely to the database.
 * @param {String} resetToken
 * @returns {String} The hashed reset token.
 */
export const hashResetToken = (resetToken) =>
  crypto.createHash('sha256').update(resetToken).digest('hex');

/**
 * Generate reset password details — token, token hash, and expiration date.
 * @returns {Object} The reset password details.
 */
export const getResetPasswordDetails = () => {
  // Generate reset token.
  const resetToken = crypto.randomBytes(20).toString('hex');

  // Hash reset token. Returned value of the hash will be saved in the db.
  const resetTokenHash = crypto.createHash('sha256').update(resetToken).digest('hex');

  // Set reset token expiration date.
  const resetPasswordExpire = Date.now() + 10 * 60 * 1000;

  return { resetToken, resetTokenHash, resetPasswordExpire };
};

/**
 * Send the reset password email.
 * @param {Object} req The request object.
 * @param {Object} user The user object.
 * @param {String} resetToken The reset token
 * @returns {Boolean} Truthy value if the email is sent.
 */
export const sendResetPasswordEmail = async (req, user, resetToken) => {
  const resetUrl = `${req.protocol}://${req.get('host')}/api/v1/auth/password/reset/${resetToken}`;

  const message = `You are receiving this email because you have requested the reset of your password. Click on this link to reset your password: \n\n ${resetUrl}`;

  try {
    await sendEmail({
      email: user.email,
      subject: 'Password reset token',
      message,
    });
  } catch (err) {
    throw new ServiceError('Email could not be sent', 500);
  }

  return true;
};
