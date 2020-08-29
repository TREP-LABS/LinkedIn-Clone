import chai from 'chai';
import chaiHttp from 'chai-http';
import app from '../../../app';
import { sendRequest } from './base';

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

export const getProfile = (type, userId, assertions) => {
  chai.request(app).get(`/api/v1/profiles/${type}/${userId}`).end(assertions);
};

export const createEducation = (educationDetails, assertions) => {
  const chaiRequest = chai.request(app).post('/api/v1/profiles/educations');

  sendRequest(chaiRequest, educationDetails, assertions);
};

export const updateEducation = (educationDetails, educationId, assertions) => {
  const chaiRequest = chai.request(app).put(`/api/v1/profiles/educations/${educationId}`);

  sendRequest(chaiRequest, educationDetails, assertions);
};

export const deleteEducation = (educationDetails, educationId, assertions) => {
  const chaiRequest = chai.request(app).delete(`/api/v1/profiles/educations/${educationId}`);

  sendRequest(chaiRequest, educationDetails, assertions);
};
