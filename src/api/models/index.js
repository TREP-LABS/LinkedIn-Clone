import mongoose from 'mongoose';
import config from '../config/config';

// Import models.
import User from './User';
import Profile from './Profile';
import Skill from './Skill';

/**
 * @swagger
 * components:
 *  schemas:
 *    InvalidRequestResponse:
 *      type: object
 *      properties:
 *        success:
 *          type: boolean
 *          example: false
 *        message:
 *          type: string
 *        errors:
 *          type: object
 *          properties:
 *            fieldName:
 *              type: array
 *              items:
 *                type: string
 *
 *    FailureResponse:
 *      type: object
 *      properties:
 *        success:
 *          type: boolean
 *          example: false
 *        message:
 *          type: string
 */

const env = process.env.NODE_ENV?.trim() || 'development';

const presetConfig = config[env];

mongoose.connect(presetConfig.dbUrl, {
  useNewUrlParser: true,
  useCreateIndex: true,
  useUnifiedTopology: true,
});

export default {
  User,
  Profile,
  Skill,
};
