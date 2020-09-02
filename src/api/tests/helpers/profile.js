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

export const positionDetails = {
  title: 'Test Position Title',
  summary: 'Summary of the test position.',
  startDate: 'May, 2020',
  endDate: 'Aug, 2020',
  isCurrent: false,
  company: 'TREP Labs',
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
 * An helper function to alter some or all the properties in -
 * the position details (positionDetails).
 * @param {Object} newPositionDetails This object would be used to update the position details.
 * @returns {Object} The updated position details.
 */
export const alterPositionDetails = (newPositionDetails) => ({
  ...positionDetails,
  ...newPositionDetails,
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

export const createPosition = (positionDetails, assertions) => {
  const chaiRequest = chai.request(app).post('/api/v1/profiles/positions');

  sendRequest(chaiRequest, positionDetails, assertions);
};
