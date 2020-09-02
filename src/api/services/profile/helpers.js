import moment from 'moment';

/**
 * Format the profile data to be returned to the client.
 * @param {Object} profileData The raw user data gotten from the database
 * @returns {Object} The formatted profile data.
 */
export const formatProfileData = (profileData) => {
  const educations = profileData.educations.map((education) => formatEducationData(education));

  return {
    educations,
  };
};

/**
 * Format the education data to be returned to the client.
 * @param {Object} educationData The raw education data gotten from the database.
 * @returns {Object} The formatted education data.
 */
export const formatEducationData = (educationData) => ({
  id: educationData._id,
  schoolName: educationData.schoolName,
  fieldOfStudy: educationData.fieldOfStudy,
  startDate: educationData.startDate,
  endDate: educationData.endDate,
  degree: educationData.degree,
  activities: educationData.activities,
  notes: educationData.notes,
});

/**
 * Format the position data to be returned to the client.
 * @param {Object} positionData The raw position data gotten from the database.
 * @returns {Object} The formatted position data.
 */
export const formatPositionData = (positionData) => ({
  id: positionData._id,
  title: positionData.title,
  summary: positionData.summary,
  startDate: moment(positionData.startDate).format('MMM, YYYY'),
  endDate: positionData.endDate ? moment(positionData.endDate).format('MMM, YYYY') : undefined,
  isCurrent: positionData.isCurrent,
  company: positionData.company,
});

/**
 * Get a profile from the database using the user's ID.
 * @param {Object} Profile The query interface for profile in the database.
 * @param {String} user The user ID of the profile to get.
 * @returns {Object} The profile gotten from the database.
 */
export const getProfileByUser = async (Profile, user) => {
  let profile = await Profile.findOne({ user });
  return profile;
};
