import moment from 'moment';
import db from '../../models';
import { ServiceError } from '../helpers';
import {
  getProfileByUser,
  getFullProfileByUser,
  formatEducationData,
  formatPositionData,
  formatSkillData,
  formatCertificationData,
  formatLanguageData,
} from './helpers';
import { formatUserData } from '../users/helpers';

const { User, Profile, Skill } = db;

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
  const user = await User.findOne({ _id: userId });

  if (!user) throw new ServiceError('User profile does not exist.', 404);

  const profile = await getFullProfileByUser(Profile, userId);

  user.profile = profile;

  return formatUserData(user);
};

/**
 * Updates a string field given the field name and the value.
 * @param {Object} user The authenticated user object.
 * @param {String} field The field in the profile model to update.
 * @param {String} value The value of the field to updated.
 */
export const updateProfileDetails = async (user, field, value) => {
  const profile = await getFullProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  profile[field] = value;

  await profile.save();

  user.profile = profile;

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

  const education = profile.educations.id(educationId);

  if (!education) throw new ServiceError('Education entry does not exist.', 404);

  education.remove();

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

/**
 * Update position entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} positionId The ID of the position to update.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The updated position entry.
 */
export const updatePosition = async (user, positionId, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const position = profile.positions.id(positionId);

  if (!position) throw new ServiceError('Position entry does not exist.', 404);

  data.isCurrent = data.endDate ? false : true;

  Object.assign(position, data);

  await profile.save();

  return formatPositionData(position);
};

/**
 * Delete a position entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} positionId The ID of the position to delete.
 * @returns {boolean} Truthy value.
 */
export const deletePosition = async (user, positionId) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const position = profile.positions.id(positionId);

  if (!position) throw new ServiceError('Position entry does not exist.', 404);

  position.remove();

  await profile.save();

  return true;
};

/**
 * Adds a skills to the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 */
export const addSkills = async (user, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const skills = profile.skills;

  for (const skill of data.skills) {
    // Check if skill is already in DB.
    const foundSkill = await Skill.findOne({ name: skill });

    if (foundSkill) {
      // If skill is in the DB and skill is not in the user's array of skills;
      // Push the skill into the user's array of skills.
      if (!skills.some((skill) => skill.skill.equals(foundSkill._id)))
        skills.push({ skill: foundSkill._id });
    } else {
      // If skill is not in the DB, create the skill and push the skill into the user's array of skills.
      const newSkill = await Skill.create({ name: skill });
      skills.push({ skill: newSkill._id });
    }
  }

  await profile.save();

  profile = await getProfileByUser(Profile, user.id, ['skills.skill']);

  return profile.skills.map((skill) => formatSkillData(skill));
};

/**
 * Updates skills in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 */
export const updateSkills = async (user, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  profile.skills = profile.skills.filter((profileSkill) =>
    data.skills.some((skill) => profileSkill._id.equals(skill.id)),
  );

  await profile.save();

  profile = await getProfileByUser(Profile, user.id, ['skills.skill']);

  return profile.skills.map((skill) => formatSkillData(skill));
};

/**
 * Delete skill from a user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} skillId The id of the skill to delete from user's profile.
 */
export const removeSkill = async (user, skillId) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const skill = profile.skills.id(skillId);

  if (!skill) throw new ServiceError('Skill does not exist.');

  skill.remove();

  await profile.save();

  return true;
};

/**
 * Search DB for skills matching a query string.
 * @param {Object} user The authenticated user object.
 * @param {String} query The search query.
 */
export const searchSkills = async (user, query) => {
  const profile = await getProfileByUser(Profile, user.id);

  let skills = await Skill.find({ name: { $regex: `.*${query}.*`, $options: 'i' } }).limit(20);

  skills = skills.filter(
    (skill) => !profile.skills.some((profileSkill) => profileSkill.skill.equals(skill._id)),
  );

  return skills.map((skill) => skill.name);
};

/**
 * Adds a certificate to the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The added certification entry.
 */
export const addCertification = async (user, data) => {
  let profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  profile.certifications.push(data);

  await profile.save();

  return formatCertificationData(profile.certifications[profile.certifications.length - 1]);
};

/**
 * Update certification entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} certificationId The ID of the certification to update.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The updated certification entry.
 */
export const updateCertification = async (user, certificationId, data) => {
  const profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const certification = profile.certifications.id(certificationId);

  if (!certification) throw new ServiceError('Certification entry does not exist.', 404);

  Object.assign(certification, data);

  certification.endDate = !data.endDate ? undefined : certification.endDate;

  await profile.save();

  return formatCertificationData(certification);
};

/**
 * Delete a certification entry in the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {String} certificationId The ID of the certificate to delete.
 * @returns {boolean} Truthy value.
 */
export const deleteCertification = async (user, certificationId) => {
  const profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  const certification = profile.certifications.id(certificationId);

  if (!certification) throw new ServiceError('Certification entry does not exist.', 404);

  certification.remove();

  await profile.save();

  return true;
};

/**
 * Adds a language to the user's profile.
 * @param {Object} user The authenticated user object.
 * @param {Object} data Request data from the controller.
 * @returns {Object} The added language entry.
 */
export const addLanguage = async (user, data) => {
  const profile = await getProfileByUser(Profile, user.id);

  if (!profile) throw new ServiceError('User profile does not exist.', 404);

  profile.languages.push(data);

  await profile.save();

  return formatLanguageData(profile.languages[profile.languages.length - 1]);
};
