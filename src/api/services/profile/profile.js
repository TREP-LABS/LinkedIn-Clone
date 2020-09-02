import moment from 'moment';
import db from '../../models';
import { ServiceError } from '../helpers';
import { getProfileByUser, formatEducationData, formatPositionData } from './helpers';
import { formatUserData } from '../users/helpers';

const { User, Profile } = db;

/**
 * Get the user's basic profile.
 * @param {Object} user The authenticated user object.
 * @returns {Object} The returned user's profile.
 */
export const getBasicProfile = async (userId) => {
  const user = await User.findOne({ _id: userId });

  if (!user) throw new ServiceError('User profile does not exist.', 404);

  return formatUserData(user);
};

/**
 * Get the user's full profile.
 * @param {Object} user The authenticated user object.
 * @returns {Object} The returned user's profile.
 */
export const getFullProfile = async (userId) => {
  const user = await User.findOne({ _id: userId }).populate('profile');

  if (!user) throw new ServiceError('User profile does not exist.', 404);

  return formatUserData(user);
};

/**
 * Add new education entry to the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The added education entry.
 */
export const addEducation = async (user, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  data.startDate = moment(data.startDate).format('YYYY');
  data.endDate = moment(data.endDate).format('YYYY');

  profile.educations.push(data);

  await profile.save();

  return formatEducationData(profile.educations[profile.educations.length - 1]);
};

/**
 * Update education entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} educationId The ID of the education to update.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The updated education entry.
 */
export const updateEducation = async (user, educationId, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const education = profile.educations.id(educationId);

  if (!education) throw new ServiceError('Education entry does not exist.', 404);

  data.startDate = moment(data.startDate).format('YYYY');
  data.endDate = moment(data.endDate).format('YYYY');

  Object.assign(education, data);

  await profile.save();

  return formatEducationData(education);
};

/**
 * Delete an education entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} educationId The ID of the education to update.
 * @returns {boolean} Truthy value.
 */
export const deleteEducation = async (user, educationId) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  profile.educations.id(educationId).remove();

  await profile.save();

  return true;
};

/**
 * Add new position entry to the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The added position entry.
 */
export const addPosition = async (user, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  data.isCurrent = data.endDate ? false : true;

  profile.positions.push(data);

  await profile.save();

  return formatPositionData(profile.positions[profile.positions.length - 1]);
};
