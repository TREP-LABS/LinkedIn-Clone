import moment from 'moment';
import db from '../../models';
import { ServiceError } from '../helpers';
import { getProfileByUser, formatEducationData } from './helpers';

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
