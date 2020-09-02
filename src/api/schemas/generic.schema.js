import JoiBase from 'joi';
import JoiDatePackage from '@hapi/joi-date';

const Joi = JoiBase.extend(JoiDatePackage);

/**
 * Common schema to be used in other Joi ObjectSchemas.
 */
export const GenericSchema = {
  stringField: Joi.string().min(2).trim(true),
  booleanField: Joi.boolean(),
  monthYearField: Joi.date().format('MMM, YYYY'),
  yearField: Joi.date().format('YYYY'),
};
