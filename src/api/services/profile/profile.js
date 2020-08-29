import moment from 'moment';
import db from '../../models';
import { ServiceError } from '../helpers';
import { getProfileByUser, formatEducationData, getEducationById } from './helpers';

const { Profile } = db;

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
