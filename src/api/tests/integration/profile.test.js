import { BaseHelpers, UserHelpers, ProfileHelpers } from '../helpers';

const {
  failureAssertions,
  authFailureAssertions,
  makeRequest,
  getEndpoint,
  alterDetails,
} = BaseHelpers;
const { userDetails, loginUser } = UserHelpers;
const { educationDetails, positionDetails, certificationDetails, getProfile } = ProfileHelpers;

let userToken;
let userId;
let educationId;
let positionId;
let skillId;
let certificationId;
let wrongUserId = '5f4a2f6db262d23d4808948b';

describe('Profile Endpoints', () => {
  before('Login the user', (done) => {
    loginUser(userDetails, (err, res) => {
      userToken = res.body.data.token;
      userId = res.body.data.user.id;

      done();
    });
  });

  describe('Profile Endpoints', () => {
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

    describe('Update Profile Details', () => {
      it('should update firstname', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint('profiles/firstname') },
          alterDetails(userDetails, { firstname: 'John', token: userToken }),
          (err, res) => {
            res.should.have.status(200);
            res.body.data.user.firstname.should.be.eql('John');

            done();
          },
        );
      });

      it('should update lastname', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint('profiles/lastname') },
          alterDetails(userDetails, { lastname: 'Doe', token: userToken }),
          (err, res) => {
            res.should.have.status(200);
            res.body.data.user.lastname.should.be.eql('Doe');

            done();
          },
        );
      });

      it('should update headline', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint('profiles/headline') },
          alterDetails(userDetails, {
            headline: 'Software Engineer at TREP Labs',
            token: userToken,
          }),
          (err, res) => {
            res.should.have.status(200);
            res.body.data.user.headline.should.be.eql('Software Engineer at TREP Labs');

            done();
          },
        );
      });

      it('should update summary', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint('profiles/summary') },
          alterDetails(userDetails, {
            summary: 'I am a software engineer at TREP Labs.',
            token: userToken,
          }),
          (err, res) => {
            res.should.have.status(200);
            res.body.data.user.profile.summary.should.be.eql(
              'I am a software engineer at TREP Labs.',
            );

            done();
          },
        );
      });
    });
  });

  describe('Education Endpoints', () => {
    describe('Create Education', () => {
      it('should not allow guest users create new education entry', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/educations') },
          alterDetails(educationDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should create a new education entry', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/educations') },
          alterDetails(educationDetails, { token: userToken }),
          (err, res) => {
            res.should.have.status(201);
            res.body.success.should.be.eql(true);
            res.body.data.education.should.be.a('object');
            res.body.data.education.schoolName.should.be.eql(educationDetails.schoolName);

            educationId = res.body.data.education.id;

            done();
          },
        );
      });
    });

    describe('Update Education', () => {
      it('should not allow guest users update an education entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/educations/${educationId}`) },
          alterDetails(educationDetails, { schoolName: 'Havard University', token: null }),
          authFailureAssertions(done),
        );
      });

      it('should update an education entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/educations/${educationId}`) },
          alterDetails(educationDetails, { schoolName: 'Havard University', token: userToken }),
          (err, res) => {
            res.should.have.status(200);
            res.body.success.should.be.eql(true);
            res.body.data.education.schoolName.should.be.eql('Havard University');

            done();
          },
        );
      });
    });

    describe('Remove Education', () => {
      it('should not allow guest users delete an education entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/educations/${educationId}`) },
          alterDetails(educationDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should delete an education entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/educations/${educationId}`) },
          alterDetails(educationDetails, { token: userToken }),
          (err, res) => {
            res.should.have.status(204);

            done();
          },
        );
      });
    });
  });

  describe('Position Endpoints', () => {
    describe('Create Position', () => {
      it('should not allow guest users create new position entry', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/positions') },
          alterDetails(positionDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should create a new position entry', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/positions') },
          alterDetails(positionDetails, { token: userToken }),
          (err, res) => {
            res.should.status(201);
            res.body.success.should.be.eql(true);
            res.body.data.position.should.be.a('object');
            res.body.data.position.title.should.be.eql(positionDetails.title);

            positionId = res.body.data.position.id;

            done();
          },
        );
      });

      it('should have isCurrent value as true if endDate is empty', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/positions') },
          alterDetails(positionDetails, { token: userToken, endDate: undefined }),
          (err, res) => {
            res.should.have.status(201);
            res.body.data.position.should.be.a('object');
            res.body.data.position.isCurrent.should.be.eql(true);

            done();
          },
        );
      });
    });

    describe('Update Position', () => {
      it('should not allow guest users create new position entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/positions/${positionId}`) },
          alterDetails(positionDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should update a position entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/positions/${positionId}`) },
          alterDetails(positionDetails, { token: userToken, title: 'Research Student' }),
          (err, res) => {
            res.should.have.status(200);
            res.body.success.should.be.eql(true);
            res.body.data.position.should.be.a('object');
            res.body.data.position.title.should.be.eql('Research Student');

            done();
          },
        );
      });
    });

    describe('Remove Position', () => {
      it('should not allow guest users remove position entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/positions/${positionId}`) },
          alterDetails(positionDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should remove an position entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/positions/${positionId}`) },
          alterDetails(positionDetails, { token: userToken }),
          (err, res) => {
            res.should.have.status(204);

            done();
          },
        );
      });
    });
  });

  describe('Skills Endpoints', () => {
    describe('Add Skills', () => {
      it('should not allow guest users add skills to profile', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/skills') },
          { skills: ['Nodejs', 'Express'], token: null },
          authFailureAssertions(done),
        );
      });

      it('should add new skills to profile', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/skills') },
          { skills: ['Nodejs', 'Express'], token: userToken },
          (err, res) => {
            res.should.have.status(200);
            res.body.success.should.be.eql(true);
            res.body.data.skills[0].name.should.be.eql('Nodejs');
            res.body.data.skills[1].name.should.be.eql('Express');

            skillId = res.body.data.skills[0].id;

            done();
          },
        );
      });
    });

    describe('Remove Skill', () => {
      it('should not allow guest delete skills', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/skills/${skillId}`) },
          { token: null },
          authFailureAssertions(done),
        );
      });

      it("should delete skill from user's profile", (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/skills/${skillId}`) },
          { token: userToken },
          (err, res) => {
            res.should.have.status(204);

            done();
          },
        );
      });
    });

    describe('Search Skills', () => {
      it('should not return skills that user already have', (done) => {
        makeRequest(
          { method: 'get', endpoint: getEndpoint('profiles/skills?Nod') },
          { token: userToken },
          (err, res) => {
            res.should.have.status(200);
            res.body.data.skills.should.be.eql([]);

            done();
          },
        );
      });
    });
  });

  describe('Certifications Endpoints', () => {
    describe('Add Certification', () => {
      it('should not allow guest users add certification to profile', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/certifications') },
          alterDetails(certificationDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should add new certification to profile', (done) => {
        makeRequest(
          { method: 'post', endpoint: getEndpoint('profiles/certifications') },
          alterDetails(certificationDetails, { token: userToken }),
          (err, res) => {
            res.should.have.status(201);
            res.body.success.should.be.eql(true);
            res.body.data.certification.name.should.be.eql(certificationDetails.name);

            certificationId = res.body.data.certification.id;

            done();
          },
        );
      });
    });

    describe('Update Certification', () => {
      it('should not allow guest users create new certification entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/certifications/${certificationId}`) },
          alterDetails(certificationDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should update a certification entry', (done) => {
        makeRequest(
          { method: 'put', endpoint: getEndpoint(`profiles/certifications/${certificationId}`) },
          alterDetails(certificationDetails, {
            token: userToken,
            name: 'Complete Web Developer Course 2.0',
          }),
          (err, res) => {
            res.should.have.status(200);
            res.body.success.should.be.eql(true);
            res.body.data.certification.should.be.a('object');
            res.body.data.certification.name.should.be.eql('Complete Web Developer Course 2.0');

            done();
          },
        );
      });
    });

    describe('Delete Certification', () => {
      it('should not allow guest users delete certification entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/certifications/${certificationId}`) },
          alterDetails(positionDetails, { token: null }),
          authFailureAssertions(done),
        );
      });

      it('should delete a certification entry', (done) => {
        makeRequest(
          { method: 'delete', endpoint: getEndpoint(`profiles/certifications/${certificationId}`) },
          alterDetails({ token: userToken }),
          (err, res) => {
            res.should.have.status(204);

            done();
          },
        );
      });
    });
  });
});
