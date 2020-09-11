import { asyncHandler } from '../middlewares';
import { successResponse } from '../utils/helpers';
import { ProfileService } from '../services';

/**
 * Add new education entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const getBasicProfile = asyncHandler(async (req, res) => {
  const { userId } = req.params;

  const user = await ProfileService.getBasicProfile(userId);

  return successResponse(res, 'Basic profile retrieved successfully.', { data: { user } }, 200);
});

/**
 * Add new education entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const getFullProfile = asyncHandler(async (req, res) => {
  const { userId } = req.params;

  const user = await ProfileService.getFullProfile(userId);

  return successResponse(res, 'Full profile retrieved successfully.', { data: { user } }, 200);
});

/**
 * Add new education entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const addEducation = asyncHandler(async (req, res) => {
  const education = await ProfileService.addEducation(req.user, req.body);

  return successResponse(res, 'Education added successfully.', { data: { education } }, 201);
});

/**
 * Add new education entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const updateEducation = asyncHandler(async (req, res) => {
  const { educationId } = req.params;

  const education = await ProfileService.updateEducation(req.user, educationId, req.body);

  return successResponse(res, 'Education updated successfully.', { data: { education } }, 200);
});

/**
 * Add new education entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const deleteEducation = asyncHandler(async (req, res) => {
  const { educationId } = req.params;

  ProfileService.deleteEducation(req.user, educationId);

  return successResponse(res, 'Education removed successfully.', { data: {} }, 204);
});

/**
 * Add new position entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const addPosition = asyncHandler(async (req, res) => {
  const position = await ProfileService.addPosition(req.user, req.body);

  return successResponse(res, 'Position added successfully.', { data: { position } }, 201);
});

/**
 * Add new position entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const updatePosition = asyncHandler(async (req, res) => {
  const { positionId } = req.params;

  const position = await ProfileService.updatePosition(req.user, positionId, req.body);

  return successResponse(res, 'Position updated successfully.', { data: { position } }, 200);
});

/**
 * Add new position entry to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const deletePosition = asyncHandler((req, res) => {
  const { positionId } = req.params;

  ProfileService.deletePosition(req.user, positionId);

  return successResponse(res, 'Position removed successfully.', { data: {} }, 204);
});

/**
 * Add new skills to the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const addSkills = asyncHandler(async (req, res) => {
  const skills = await ProfileService.addSkills(req.user, req.body);

  return successResponse(res, 'Skills added successfully.', { data: { skills } }, 200);
});

/**
 * Updates skills in the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const updateSkills = asyncHandler(async (req, res) => {
  const skills = await ProfileService.updateSkills(req.user, req.body);

  return successResponse(res, 'SKills updated successfully.', { data: { skills } }, 200);
});

/**
 * Search for skills in the DB.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const searchSkills = asyncHandler(async (req, res) => {
  const { q } = req.query;

  const skills = await ProfileService.searchSkills(req.user, q);

  return successResponse(res, 'Skills search result.', { data: { skills } }, 200);
});

/**
 * Delete skill from a user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const removeSkill = asyncHandler(async (req, res) => {
  const { skillId } = req.params;

  ProfileService.removeSkill(req.user, skillId);

  return successResponse(res, 'Skill removed successfully.', { data: {} }, 204);
});
