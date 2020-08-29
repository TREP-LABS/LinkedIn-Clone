import { asyncHandler } from '../middlewares';
import { successResponse } from '../utils/helpers';
import { ProfileService } from '../services';

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

  await ProfileService.deleteEducation(req.user, educationId);

  return successResponse(res, 'Education deleted successfully.', { data: {} }, 204);
});
