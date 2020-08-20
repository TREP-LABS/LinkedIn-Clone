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
 *            $ref: '#/components/schemas/User'
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
router.route('/register').post(validateInputs('UserSchemas', 'register'), users.register);

export default router;
