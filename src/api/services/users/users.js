import db from '../../models';
import { getUserByEmail, generateToken, formatUserData } from './helpers';
import { ServiceError } from '../helpers';

const { User } = db;

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

  const token = generateToken(formatUserData(newUser));

  return token;
};
