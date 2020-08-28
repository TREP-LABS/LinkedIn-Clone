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
