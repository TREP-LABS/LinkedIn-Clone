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

/**
 * Add certification entry to user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const addCertification = asyncHandler(async (req, res) => {
  const certification = await ProfileService.addCertification(req.user, req.body);

  return successResponse(
    res,
    'Certification added successfully.',
    { data: { certification } },
    201,
  );
});

/**
 * Update certification entry in the user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const updateCertification = asyncHandler(async (req, res) => {
  const { certificationId } = req.params;

  const certification = await ProfileService.updateCertification(
    req.user,
    certificationId,
    req.body,
  );

  return successResponse(
    res,
    'Certification updated successfully.',
    { data: { certification } },
    200,
  );
});

/**
 * Delete certification entry from user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const deleteCertification = asyncHandler(async (req, res) => {
  const { certificationId } = req.params;

  ProfileService.deleteCertification(req.user, certificationId);

  return successResponse(res, 'Certification removed successfully.', {}, 204);
});

/**
 * Update user profile summary.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const updateSummary = asyncHandler(async (req, res) => {
  const user = await ProfileService.updateProfileDetails(req.user, 'summary', req.body.summary);

  return successResponse(res, 'Summary updated successfully.', { data: { user } }, 200);
});

/**
 * Add language entry to user's profile.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const addLanguage = asyncHandler(async (req, res) => {
  const language = await ProfileService.addLanguage(req.user, req.body);

  return successResponse(res, 'Language added successfully.', { data: { language } }, 201);
});
