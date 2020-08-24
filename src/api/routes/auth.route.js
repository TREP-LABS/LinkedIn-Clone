import express from 'express';
import { users } from '../controllers';
import { validateInputs } from '../middlewares';

const router = express.Router();

/**
 * @swagger
 * tags:
 *  name: Users
 *  description: Operations on the user.
 */

/**
 * @swagger
 * /auth/register:
 *   post:
 *     tags: [Users]
 *     summary: Creates a new user.
 *     requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            properties:
 *              firstname:
 *                type: string
 *              lastname:
 *                type: string
 *              email:
 *                type: string
 *              password:
 *                type: string
 *     responses:
 *       '201':
 *         description: User created successfully.
 *         content:
 *          application/json:
 *            schema:
 *              type: object
 *              properties:
 *                success:
 *                  type: boolean
 *                message:
 *                  type: string
 *                data:
 *                  $ref: '#/components/schemas/User'
 *
 *       '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 */
router.post('/register', validateInputs('UserSchemas', 'register'), users.register);

/**
 * @swagger
 * /auth/login:
 *  post:
 *    tags: [Users]
 *    summary: Login a user.
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            properties:
 *              email:
 *                type: string
 *              password:
 *                type: string
 *    responses:
 *       '200':
 *         description: User logged in successfully.
 *         content:
 *          application/json:
 *            schema:
 *              type: object
 *              properties:
 *                success:
 *                  type: boolean
 *                message:
 *                  type: string
 *                data:
 *                  type: object
 *                  properties:
 *                    token:
 *                      type: string
 *                    user:
 *                      $ref: '#/components/schemas/User'
 *
 *       '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 *
 *       '404':
 *          description: User does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                  success:
 *                    type: boolean
 *                    example: false
 *                  message:
 *                    type: string
 */
router.post('/login', validateInputs('UserSchemas', 'login'), users.login);

/**
 * @swagger
 * /auth/password/request:
 *  post:
 *    tags: [Users]
 *    summary: Sends a reset password email containing reset link to user.
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            properties:
 *              email:
 *                type: string
 *    responses:
 *      '200':
 *         description: Reset link sent to mail.
 *         content:
 *          application/json:
 *            schema:
 *              type: object
 *              properties:
 *                success:
 *                  type: boolean
 *                message:
 *                  type: string
 *      '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 *      '404':
 *          description: User does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                  success:
 *                    type: boolean
 *                    example: false
 *                  message:
 *                    type: string
 */
router.post(
  '/password/request',
  validateInputs('UserSchemas', 'forgotPassword'),
  users.forgotPassword,
);

/**
 * @swagger
 * /auth/password/reset:
 *  post:
 *    tags: [Users]
 *    summary: Reset the user's password.
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            properties:
 *              resetToken:
 *                type: string
 *              email:
 *                type: string
 *    responses:
 *      '200':
 *         description: Password reset successfully.
 *         content:
 *          application/json:
 *            schema:
 *              type: object
 *              properties:
 *                success:
 *                  type: boolean
 *                message:
 *                  type: string
 *      '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 */
router.put('/password/reset', validateInputs('UserSchemas', 'resetPassword'), users.resetPassword);

export default router;
