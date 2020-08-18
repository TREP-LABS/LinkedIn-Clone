import jwt from 'jsonwebtoken';

/**
 * Format the user data to be returned to the client.
 * @param {Object} userData The raw user data gotten from the database
 * @returns {Object} The formatted user data.
 */
export const formatUserData = (userData) => ({
  id: userData._id,
  name: userData.name,
  email: userData.email,
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
