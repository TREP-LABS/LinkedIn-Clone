import { BaseHelpers, UserHelpers, ProfileHelpers } from '../helpers';
import { failureAssertions } from '../helpers/base';

const { authFailureAssertions } = BaseHelpers;
const { userDetails, loginUser } = UserHelpers;
const {
  educationDetails,
  alterEducationDetails,
  getProfile,
  createEducation,
  updateEducation,
  deleteEducation,
} = ProfileHelpers;

let userToken;
let userId;
let educationId;
let wrongUserId = '5f4a2f6db262d23d4808948b';

describe('Profile Endpoints', () => {
  before('Login the user', (done) => {
    loginUser(userDetails, (err, res) => {
      userToken = res.body.data.token;
      userId = res.body.data.user.id;
      educationDetails.token = userToken;

      done();
    });
  });

  describe('Education Endpoints', () => {
    describe('Get Profile', () => {
      it('should get basic profile', (done) => {
        getProfile('basic', userId, (err, res) => {
          res.should.have.status(200);
          res.body.data.user.should.be.a('object');

          done();
        });
      });

      it('should get full profile', (done) => {
        getProfile('full', userId, (err, res) => {
          res.should.have.status(200);
          res.body.data.user.should.be.a('object');
          res.body.data.user.profile.should.be.a('object');

          done();
        });
      });

      it('should not get profile with wrong user ID', (done) => {
        getProfile(
          'basic',
          wrongUserId,
          failureAssertions('User profile does not exist.', 404, done),
        );
      });
    });

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

          educationId = res.body.data.education.id;

          done();
        });
      });
    });

    describe('Update Education', () => {
      it('should not allow guest users update an education entry', (done) => {
        updateEducation(
          alterEducationDetails({ schoolName: 'Havard University', token: null }),
          educationId,
          authFailureAssertions(done),
        );
      });

      it('should update an education entry', (done) => {
        updateEducation(
          alterEducationDetails({ schoolName: 'Havard University' }),
          educationId,
          (err, res) => {
            res.should.have.status(200);
            res.body.success.should.be.eql(true);
            res.body.data.education.schoolName.should.be.eql('Havard University');

            done();
          },
        );
      });
    });

    describe('Delete Education', () => {
      it('should not allow guest users delete an education entry', (done) => {
        deleteEducation(
          alterEducationDetails({ token: null }),
          educationId,
          authFailureAssertions(done),
        );
      });

      it('should update an education entry', (done) => {
        deleteEducation(educationDetails, educationId, (err, res) => {
          res.should.have.status(204);

          done();
        });
      });
    });
  });
});
