/**
 * Middleware to catch errors thrown from the app.
 * @param {Object} err Error object
 * @param {Object} req The request object.
 * @param {Object} res The response object.
 * @param {Function} next Next function to call the next middleware
 */
const errorHandler = (err, req, res, next) => {
  const error = { ...err };

  error.message = err.message;

  if (error.httpStatusCode === '500' || error.httpStatusCode === undefined) {
    const { log } = res.locals;
    error.message = undefined;
    log.info(err.stack);
  }

  res.status(error.httpStatusCode || 500).json({
    success: false,
    message: error.message || 'Server Error',
  });
};

export default errorHandler;
