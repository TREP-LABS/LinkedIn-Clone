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

/**
 * Joi ObjectSchema to validate position inputs.
 */
export const PositionSchema = Joi.object({
  title: GenericSchema.stringField.required(),
  summary: GenericSchema.stringField,
  startDate: GenericSchema.monthYearField.required(),
  endDate: GenericSchema.monthYearField,
  isCurrent: GenericSchema.booleanField,
  company: GenericSchema.stringField.required(),
});
