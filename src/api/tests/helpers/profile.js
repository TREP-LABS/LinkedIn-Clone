import chai from 'chai';
import chaiHttp from 'chai-http';
import app from '../../../app';

const should = chai.should();

chai.use(chaiHttp);

export const educationDetails = {
  schoolName: 'Test University Name',
  fieldOfStudy: 'Test Field of Study',
  startDate: '2017',
  endDate: '2022',
  degree: 'B.Tech',
  activities: 'I went to learn.',
  notes: 'I am just taking a note',
  token: null,
};

/**
 * An helper function to alter some or all the properties in -
 * the education details (educationDetails).
 * @param {Object} newEducationDetails This object would be used to update the education details.
 * @returns {Object} The updated education details.
 */
export const alterEducationDetails = (newEducationDetails) => ({
  ...educationDetails,
  ...newEducationDetails,
});

/**
 * An helper function to create a new education entry.
 * @param {Object} userDetails Details of the education entry to add.
 * @param {Function} asertions The assertions to execute after the request is complete.
 */
export const createEducation = (educationDetails, assertions) => {
  const chaiRequest = chai.request(app).post('/api/v1/profiles/educations');

  if (educationDetails.token) chaiRequest.set('Authorization', `Bearer ${educationDetails.token}`);

  chaiRequest.send(educationDetails).end(assertions);
};
