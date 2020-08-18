import { asyncHandler } from '../../middlewares';
import { UserService } from '../../services';
import { successResponse } from '../../utils/helpers';

/**
 * Registers a new user.
 * @param {Function} controller The controller function.
 * @returns {Object} The response object containing some response data.
 */
export const register = asyncHandler(async (req, res) => {
  const token = await UserService.register(req.body);

  return successResponse(res, 'User created successfully.', { data: { token } }, 201);
});
