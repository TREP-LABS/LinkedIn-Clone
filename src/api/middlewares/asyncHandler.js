/**
 * Middleware to catch unhandled promise errors from controller functions.
 * @param {Object} req The request object.
 * @param {Object} res The response object.
 * @param {Function} next Function to call the next middleware
 */
const asyncHandler = (fn) => (req, res, next) => Promise.resolve(fn(req, res, next)).catch(next);

export default asyncHandler;
