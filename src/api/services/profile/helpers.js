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
 * @param {Object} educationData The raw user data gotten from the database
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
 * Get a profile from the database using the user's ID.
 * @param {Object} Profile The query interface for profile in the database.
 * @param {String} user The user ID of the profile to get.
 * @returns {Object} The profile gotten from the database.
 */
export const getProfileByUser = async (Profile, user) => {
  let profile = await Profile.findOne({ user });
  return profile;
};
