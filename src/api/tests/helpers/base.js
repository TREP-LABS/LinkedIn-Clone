import chai from 'chai';
import chaiHttp from 'chai-http';
import app from '../../../app';

const should = chai.should();

chai.use(chaiHttp);

/**
 * An helper function that constructs assertions for a test that is meant to fail.
 * @param {String} message The message expected in the response body.
 * @param {Number} statusCode The status code expected in the response body.
 * @param {Function} done A callback from mocha to know when this test is complete.
 * @returns {void} The assertion.
 */
export const failureAssertions = (message, statusCode = 400, done) => (req, res) => {
  res.should.have.status(statusCode);
  res.body.message.should.be.eql(message);
  res.body.success.should.be.eql(false);
  done();
};

/**
 * An helper function that constructs assertions for a test that is meant-
 * to fail because of authentication.
 * @param {Function} done A callback from mocha to know when this test is complete.
 * @param {String} message A custom message to assert.
 * @returns {void}
 */
export const authFailureAssertions = (
  done,
  message = 'You are not authorized to access this route.',
) => {
  return failureAssertions(message, 401, done);
};

/**
 *
 * @param {Object} request A request object containing the request method and endpoint.
 * @param {*} details The request body.
 * @param {*} assertions The assertions to execute after the request is complete.
 */
export const makeRequest = (request, details, assertions) => {
  const { method, endpoint } = request;

  const chaiRequest = chai.request(app)[method](endpoint);

  if (details.token) chaiRequest.set('Authorization', `Bearer ${details.token}`);

  chaiRequest.send(details).end(assertions);
};

/**
 * Get full endpoint url.
 * @param {String} url The endpoint url.
 * @return {String} The full endpoint url.
 */
export const getEndpoint = (url) => {
  return `/api/v1/${url}`;
};

/**
 * An helper function to alter some or all the properties in -
 * the education details (educationDetails).
 * @param {Object} oldDetails The old details object.
 * @param {Object} newDetails This object would be used to update the education details.
 * @returns {Object} The updated education details.
 */
export const alterDetails = (oldDetails, newDetails) => ({
  ...oldDetails,
  ...newDetails,
});
