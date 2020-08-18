import { failureResponse, joiValidate } from '../utils/helpers';
import * as schemas from '../schemas';

/**
 * A middleware.
 * Validate incoming inputs from request body using Joi.
 * @param {String} schemaCollection Collection of all schema related to a resource.
 * @param {String} schemaName Name of schema to validate.
 */
const validateInputs = (schemaCollection, schemaName) => (req, res, next) => {
  const controllerSchemaFunc = schemas[schemaCollection][schemaName];

  const requestData = joiValidate(controllerSchemaFunc, req.body);

  if (requestData.error) {
    return failureResponse(res, 'Invalid inputs.', { errors: requestData.error }, 400);
  }

  Object.assign(req.body, requestData);

  next();
};

export default validateInputs;
