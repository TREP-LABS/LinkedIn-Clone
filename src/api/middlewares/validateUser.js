import jwt from 'jsonwebtoken';
import db from '../models';
import { failureResponse } from '../utils/helpers';
import { formatUserData } from '../services/users/helpers';

const { User } = db;

/**
 * A middleware.
 * Ensures that the user that sent the request is already registered.
 * @param {Object} req The request object.
 * @param {Object} res The response object.
 * @param {Function} next The function that calls the next middleware.
 * @returns {Object} The response object with the error message.
 */
export const isUser = async (req, res, next) => {
  let token = '';

  if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
    token = req.headers.authorization.split(' ')[1];
  }

  if (!token) return failureResponse(res, 'You are not authorized to access this route.', {}, 401);

  try {
    const decoded = jwt.verify(token, process.env.JSON_WEB_TOKEN_SECRET || 'secret');
    const user = await User.findById(decoded.id);

    if (!user) return failureResponse(res, 'You are not authorized to access this route.', {}, 401);

    req.user = formatUserData(user);
    next();
  } catch (err) {
    if (err.name === 'TokenExpiredError') return failureResponse(res, 'Token expired', {}, 401);

    return failureResponse(res, 'Unable to authenticate token.', {}, 401);
  }
};
