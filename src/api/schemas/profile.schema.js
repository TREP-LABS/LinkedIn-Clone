import Joi from 'joi';
import { GenericSchema } from './generic.schema';

/**
 * Joi ObjectSchema to validate education inputs.
 */
export const EducationSchema = Joi.object({
  schoolName: GenericSchema.stringField.required(),
  fieldOfStudy: GenericSchema.stringField.required(),
  startDate: GenericSchema.yearField.required(),
  endDate: GenericSchema.yearField.required(),
  degree: GenericSchema.stringField,
  activities: GenericSchema.stringField,
  notes: GenericSchema.stringField,
});
