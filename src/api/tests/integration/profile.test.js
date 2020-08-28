import { BaseHelpers, UserHelpers, ProfileHelpers } from '../helpers';

const { authFailureAssertions } = BaseHelpers;
const { userDetails, loginUser } = UserHelpers;
const { educationDetails, alterEducationDetails, createEducation } = ProfileHelpers;

let userToken;

describe('Profile Endpoints', () => {
  before('Login the user', (done) => {
    loginUser(userDetails, (err, res) => {
      userToken = res.body.data.token;
      educationDetails.token = userToken;

      done();
    });
  });

  describe('Education Endpoints', () => {
    describe('Create Education', () => {
      it('should not allow guest users create new education entry', (done) => {
        createEducation(alterEducationDetails({ token: null }), authFailureAssertions(done));
      });

      it('should create a new education entry', (done) => {
        createEducation(educationDetails, (err, res) => {
          res.should.have.status(201);
          res.body.success.should.be.eql(true);
          res.body.data.education.should.be.a('object');
          res.body.data.education.schoolName.should.be.eql(educationDetails.schoolName);

          done();
        });
      });
    });
  });
});
