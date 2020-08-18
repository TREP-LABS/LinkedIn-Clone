import Joi from '@hapi/joi';

/**
 * Generic user schema to be used in other Joi ObjectSchema.
 */
const UserSchema = {
  name: Joi.string().min(3).trim(true),
  email: Joi.string().email(),
  password: Joi.string().min(6).trim(true).prefs({ abortEarly: true }).messages({
    'string.min': 'Password must be at least 6 characters long.',
  }),
  confirmPassword: Joi.string().valid(Joi.ref('password')).messages({
    'any.only': 'Confirm password must match password value.',
  }),
};

/**
 * Joi ObjectSchema to validate register inputs.
 */
export const register = Joi.object({
  name: UserSchema.name.required(),
  email: UserSchema.email.required(),
  password: UserSchema.password.required(),
  confirmPassword: UserSchema.confirmPassword.required().messages({
    'any.required': 'Confirm password is required.',
  }),
});
