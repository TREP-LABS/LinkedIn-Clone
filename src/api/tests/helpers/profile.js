import chai from 'chai';
import chaiHttp from 'chai-http';
import app from '../../../app';

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

export const certificationDetails = {
  name: 'Complete Web Developer Course',
  authority: 'Udemy, Inc',
  number: '100243783872',
  startDate: 'Feb, 2020',
  endDate: 'Feb, 2021',
  url: 'https://certificates.udemy.com/100243783872',
};

export const getProfile = (type, userId, assertions) => {
  chai.request(app).get(`/api/v1/profiles/${type}/${userId}`).end(assertions);
};
