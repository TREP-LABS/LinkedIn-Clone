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

export const sendRequest = (chaiRequest, details, assertions) => {
  if (details.token) chaiRequest.set('Authorization', `Bearer ${details.token}`);

  chaiRequest.send(details).end(assertions);
};
