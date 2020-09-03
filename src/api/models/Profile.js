import mongoose from 'mongoose';

const { Schema } = mongoose;

/**
 * @swagger
 *  components:
 *    schemas:
 *      Education:
 *        type: object
 *        required:
 *          - id
 *          - schoolName
 *          - fieldOfStudy
 *          - startDate
 *          - endDate
 *        properties:
 *          id:
 *            type: string
 *          schoolName:
 *            type: string
 *          fieldOfStudy:
 *            type: string
 *          startDate:
 *            type: string
 *          endDate:
 *            type: string
 *          degree:
 *            type: string
 *          activities:
 *            type: string
 *          notes:
 *            type: string
 *        example:
 *          id: 537e1f77bcf86cd799439011
 *          schoolName: Havard University
 *          fieldOfStudy: Computer Science
 *          startDate: 2018
 *          endDate: 2022
 *          activities: Some activities.
 *          notes: Some notes.
 */
const EducationSchema = new Schema({
  schoolName: { type: String, required: true },
  fieldOfStudy: { type: String, required: true },
  startDate: { type: String, required: true },
  endDate: { type: String, required: true },
  degree: { type: String },
  activities: { type: String },
  notes: { type: String },
});

/**
 * @swagger
 *  components:
 *    schemas:
 *      Position:
 *        type: object
 *        properties:
 *          id:
 *            type: string
 *          title:
 *            type: string
 *            example: Software Engineer
 *          summary:
 *            type: string
 *            example: Software Engineer at TREP Labs
 *          startDate:
 *            type: string
 *            example: May, 2019
 *          endDate:
 *            type: string
 *            example: Jun, 2020
 *          isCurrent:
 *            type: boolean
 *            example: false
 *          company:
 *            type: string
 *            example: TREP Labs
 */
const PositionSchema = new Schema({
  title: { type: String, required: true },
  summary: { type: String },
  startDate: { type: Date, required: true },
  endDate: { type: Date },
  isCurrent: { type: Boolean },
  company: { type: String, required: true },
});

/**
 * @swagger
 *  components:
 *    schemas:
 *      Profile:
 *        type: object
 *        required:
 *          - id
 *          - firstname
 *          - lastname
 *          - email
 *          - slug
 *        properties:
 *          id:
 *            type: string
 *            example: 537e1f77bcf86cd799439011
 *          firstname:
 *            type: string
 *            example: John
 *          lastname:
 *            type: string
 *            example: Doe
 *          email:
 *            type: string
 *            example: johndoe@gmail.com
 *          slug:
 *            type: string
 *            example: john-doe
 *          profile:
 *            type: object
 *            properties:
 *              educations:
 *                type: array
 *                items:
 *                  $ref: '#/components/schemas/Education'
 */
const ProfileSchema = new Schema({
  educations: [EducationSchema],
  positions: [PositionSchema],
  user: { type: Schema.Types.ObjectId, ref: 'User', required: true },
});

export default mongoose.model('Profile', ProfileSchema);
