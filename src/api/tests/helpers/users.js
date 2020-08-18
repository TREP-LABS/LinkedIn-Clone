import chai from 'chai';
import chaiHttp from 'chai-http';
import app from '../../../app';

const should = chai.should();

chai.use(chaiHttp);

export const userDetails = {
  name: 'test',
  email: 'test@gmail.com',
  password: 'Password123',
  confirmPassword: 'Password123',
};

/**
 * An helper function to alter some or all the properties in -
 * the user details (normalUserDetails).
 * @param {Object} newUserDetails This object would be used to update the normal user details.
 * @returns {Object} The updated user details.
 */
export const alterUserDetails = (newUserDetails) => ({
  ...userDetails,
  ...newUserDetails,
});

/**
 * An helper function to help create a user (signup).
 * @param {Object} userDetails Details of the user to create.
 * @param {Function} asertions The assertions to execute after the request is complete.
 */
export const createUser = (userDetails, assertions) => {
  chai.request(app).post('/api/v1/auth/register').send(userDetails).end(assertions);
};
