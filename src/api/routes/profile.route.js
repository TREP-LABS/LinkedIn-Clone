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
 * /profiles/basic/{userId}:
 *  get:
 *    tags: [Profile]
 *    summary: Gets a user's basic profile.
 *    parameters:
 *      - name: userId
 *        in: path
 *        required: true
 *        description: ID of the user's profile to get.
 *        schema:
 *          type: string
 *    operationId: getBasicProfile
 *    responses:
 *       '200':
 *         description: Basic profile retrieved successfully.
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
 *                    user:
 *                      $ref: '#/components/schemas/User'
 *       '404':
 *          description: Profile does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 */
router.get('/basic/:userId', profile.getBasicProfile);

/**
 * @swagger
 * /profiles/full/{userId}:
 *  get:
 *    tags: [Profile]
 *    summary: Gets a user's full profile.
 *    parameters:
 *      - name: userId
 *        in: path
 *        required: true
 *        description: ID of the user's profile to get.
 *        schema:
 *          type: string
 *    operationId: getFullProfile
 *    responses:
 *       '200':
 *         description: Full profile retrieved successfully.
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
 *                    user:
 *                      $ref: '#/components/schemas/Profile'
 *       '404':
 *          description: Profile does not exists.
 *          content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/FailureResponse'
 */
router.get('/full/:userId', profile.getFullProfile);

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
 *                example: 2020
 *              endDate:
 *                type: string
 *                example: 2020
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
 *                    education:
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
 *                example: 2020
 *              endDate:
 *                type: string
 *                example: 2020
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
 *                    education:
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

/**
 * @swagger
 * /profiles/positions:
 *  post:
 *    tags: [Profile]
 *    summary: Adds a new position entry.
 *    parameters:
 *      - name: Authorization
 *        in: header
 *        required: true
 *        description: The authorization token.
 *        schema:
 *          type: string
 *          example: Bearer {token}
 *    operationId: addPosition
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            required:
 *              - title
 *              - startDate
 *              - company
 *            properties:
 *              title:
 *                type: string
 *              summary:
 *                type: string
 *              startDate:
 *                type: string
 *                example: Jun, 2020
 *              endDate:
 *                type: string
 *                example: Jun, 2020
 *              isCurrent:
 *                type: boolean
 *              company:
 *                type: string
 *    responses:
 *       '200':
 *         description: Position added successfully.
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
 *                    position:
 *                      $ref: '#/components/schemas/Position'
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
  '/positions',
  isUser,
  validateInputs('ProfileSchemas', 'PositionSchema'),
  profile.addPosition,
);

/**
 * @swagger
 * /profiles/positions/{positionId}:
 *  put:
 *    tags: [Profile]
 *    summary: Updates an existing position entry.
 *    parameters:
 *      - name: Authorization
 *        in: header
 *        required: true
 *        description: The authorization token.
 *        schema:
 *          type: string
 *          example: Bearer {token}
 *      - name: positionId
 *        in: path
 *        required: true
 *        description: ID of the position entry to update.
 *        schema:
 *          type: string
 *    operationId: updatePosition
 *    requestBody:
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            required:
 *              - title
 *              - startDate
 *              - company
 *            properties:
 *              title:
 *                type: string
 *              summary:
 *                type: string
 *              startDate:
 *                type: string
 *                example: Jun, 2020
 *              endDate:
 *                type: string
 *                example: Jun, 2020
 *              isCurrent:
 *                type: boolean
 *              company:
 *                type: string
 *    responses:
 *       '200':
 *         description: Position updated successfully.
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
 *                    position:
 *                      $ref: '#/components/schemas/Position'
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
  '/positions/:positionId',
  isUser,
  validateInputs('ProfileSchemas', 'PositionSchema'),
  profile.updatePosition,
);

export default router;