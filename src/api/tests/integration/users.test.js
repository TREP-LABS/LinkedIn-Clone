import { BaseHelpers, UserHelpers } from '../helpers';

const { failureAssertions } = BaseHelpers;
const { userDetails, createUser, alterUserDetails } = UserHelpers;

describe('User Endpoints', () => {
  describe('Sign Up Endpoint', () => {
    it('should create a new user', (done) => {
      createUser(userDetails, (err, res) => {
        res.should.have.status(201);
        res.body.success.should.be.eql(true);
        res.body.data.token.should.be.a('string');

        done();
      });
    });

    it('should create another new user', (done) => {
      createUser(alterUserDetails({ email: 'test2@gmail.com' }), (err, res) => {
        res.should.have.status(201);
        res.body.success.should.be.eql(true);
        res.body.data.token.should.be.a('string');

        done();
      });
    });

    it('should not create a user if email address already exists', (done) => {
      createUser(
        alterUserDetails({ email: 'test@gmail.com' }),
        failureAssertions('User with this email already exist.', 400, done),
      );
    });
  });
});
