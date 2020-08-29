import express from 'express';
import { profile } from '../controllers';
import { validateInputs, validateUser } from '../middlewares';

const { isUser } = validateUser;

const router = express.Router();

/**
 * @swagger
 * tags:
 *  name: Profile
 *  description: Operations on the user's profile.
 */

/**
 * @swagger
 * /profiles/educations:
 *  post:
 *    tags: [Profile]
 *    summary: Adds a new education entry.
 *    parameters:
 *      - name: Authorization
 *        in: header
 *        required: true
 *        description: The authorization token.
 *        schema:
 *          type: string
 *          example: Bearer {token}
 *    operationId: addEducation
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            required:
 *              - schoolName
 *              - fieldOfStudy
 *              - startDate
 *              - endDate
 *            properties:
 *              schoolName:
 *                type: string
 *              fieldOfStudy:
 *                type: string
 *              startDate:
 *                type: string
 *              endDate:
 *                type: string
 *              degree:
 *                type: string
 *              activities:
 *                type: string
 *              notes:
 *                type: string
 *    responses:
 *       '200':
 *         description: Education added successfully.
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
 *                      $ref: '#/components/schemas/Education'
 *       '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 *       '401':
 *         description: 'Unauthorized user.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 *       '404':
 *          description: Profile does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 */
router.post(
  '/educations',
  isUser,
  validateInputs('ProfileSchemas', 'EducationSchema'),
  profile.addEducation,
);

/**
 * @swagger
 * /profiles/educations/{educationId}:
 *  put:
 *    tags: [Profile]
 *    summary: Updates an existing education entry.
 *    parameters:
 *      - name: Authorization
 *        in: header
 *        required: true
 *        description: The authorization token.
 *        schema:
 *          type: string
 *          example: Bearer {token}
 *      - name: educationId
 *        in: path
 *        required: true
 *        description: ID of the education entry to update.
 *        schema:
 *          type: string
 *    operationId: updateEducation
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            required:
 *              - schoolName
 *              - fieldOfStudy
 *              - startDate
 *              - endDate
 *            properties:
 *              schoolName:
 *                type: string
 *              fieldOfStudy:
 *                type: string
 *              startDate:
 *                type: string
 *              endDate:
 *                type: string
 *              degree:
 *                type: string
 *              activities:
 *                type: string
 *              notes:
 *                type: string
 *    responses:
 *       '200':
 *         description: Education updated successfully.
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
 *                      $ref: '#/components/schemas/Education'
 *       '400':
 *         description: 'Invalid inputs.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/InvalidRequestResponse'
 *       '401':
 *         description: 'Unauthorized user.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 *       '404':
 *          description: Profile does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 */
router.put(
  '/educations/:educationId',
  isUser,
  validateInputs('ProfileSchemas', 'EducationSchema'),
  profile.updateEducation,
);

/**
 * @swagger
 * /profiles/educations/{educationId}:
 *  delete:
 *    tags: [Profile]
 *    summary: Deletes an existing education entry.
 *    parameters:
 *      - name: Authorization
 *        in: header
 *        required: true
 *        description: The authorization token.
 *        schema:
 *          type: string
 *          example: Bearer {token}
 *      - name: educationId
 *        in: path
 *        required: true
 *        description: ID of the education entry to delete.
 *        schema:
 *          type: string
 *    operationId: deleteEducation
 *    responses:
 *       '204':
 *         description: Education deleted successfully.
 *       '401':
 *         description: 'Unauthorized user.'
 *         content:
 *           application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 *       '404':
 *          description: Profile does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 */
router.delete('/educations/:educationId', isUser, profile.deleteEducation);

export default router;
