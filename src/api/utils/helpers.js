import dotenv from 'dotenv';

dotenv.config();

/**
 * An helper to abstract sending of success response sent to the API consumer.
 * @param {Object} responseObject The response object.
 * @param {String} message A message to send as part of the response.
 * @param {Object} payload The payload containing the response data.
 * @param {Number} statusCode Status code of the response.
 * @returns {Object} The response object from express.
 */
export const successResponse = (responseObject, message, payload, statusCode = 200) => {
  responseObject.status(statusCode).json(Object.assign({ success: true, message }, payload));
};

/**
 * An helper to abstract sending of failure response sent to the API consumer.
 * @param {Object} responseObject The response object.
 * @param {String} message A message to send as part of the response.
 * @param {Object} payload The payload containing the response data.
 * @param {Number} statusCode Status code of the response.
 * @returns {Object} The response object from express.
 */
export const failureResponse = (responseObject, message, payload, statusCode = 400) => {
  responseObject.status(statusCode).json({ success: false, message, ...payload });
};

/**
 * An helper to format Joi validation errors.
 * @param {Object} JoiError Error object.
 * @returns {Object} Formatted error.
 */
export const formatJoiError = (JoiError) => {
  const { details } = JoiError;
  const error = {};

  details.forEach((detail) => {
    if (error[detail.context.label]) {
      error[detail.context.label].push(detail.message);
    } else {
      error[detail.context.label] = [detail.message];
    }
  });

  return error;
};

/**
 * An helper to validate request data using Joi package.
 * @param {Object} validationSchema Joi validation schema.
 * @param {Object} Data Request data to be validated.
 * @returns {Object} Validation messages if any.
 */
export const joiValidate = (validationSchema, data) => {
  const validationOptions = {
    abortEarly: false,
    allowUnknown: true,
    stripUnknown: true,
    skipFunctions: true,
  };

  const result = validationSchema.validate(data, validationOptions);

  if (result.error) {
    const formattedError = formatJoiError(result.error);
    result.error = formattedError;
    return result;
  }

  return result.value;
};
