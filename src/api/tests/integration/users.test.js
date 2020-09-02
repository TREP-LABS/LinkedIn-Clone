import { BaseHelpers, UserHelpers } from '../helpers';

const { failureAssertions, alterDetails } = BaseHelpers;
const { userDetails, createUser, loginUser } = UserHelpers;

describe('User Endpoints', () => {
  describe('Register Endpoint', () => {
    it('should create a new user', (done) => {
      createUser(userDetails, (err, res) => {
        res.should.have.status(201);
        res.body.success.should.be.eql(true);
        res.body.data.token.should.be.a('string');

        done();
      });
    });

    it('should create another new user', (done) => {
      createUser(alterDetails(userDetails, { email: 'test2@gmail.com' }), (err, res) => {
        res.should.have.status(201);
        res.body.success.should.be.eql(true);
        res.body.data.token.should.be.a('string');

        done();
      });
    });

    it('should not create a user if email address already exists', (done) => {
      createUser(
        alterDetails(userDetails, { email: 'test@gmail.com' }),
        failureAssertions('User with this email already exist.', 400, done),
      );
    });
  });

  describe('Login Endpoint', () => {
    it('should not login a user that does not exist', (done) => {
      loginUser(
        alterDetails(userDetails, { email: 'notregisteredemail@gmail.com' }),
        failureAssertions('User with this email does not exist.', 404, done),
      );
    });

    it('should not sign in a user if the password is wrong.', (done) => {
      loginUser(
        alterDetails(userDetails, { password: 'wrongPassword' }),
        failureAssertions('Incorrect password.', 400, done),
      );
    });

    it('should login a user', (done) => {
      loginUser(userDetails, (err, res) => {
        res.should.have.status(200);
        res.body.success.should.be.eql(true);
        res.body.data.token.should.be.a('string');
        res.body.data.user.should.be.a('object');
        res.body.data.user.email.should.be.eql(userDetails.email);

        done();
      });
    });
  });
});
