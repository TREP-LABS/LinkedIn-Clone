import mongoose from 'mongoose';

const { Schema } = mongoose;

/**
 * @swagger
 *  components:
 *    schemas:
 *      Skill:
 *        type: object
 *        properties:
 *          id:
 *            type: string
 *          name:
 *            type: string
 *            example: Nodejs
 */
const SkillSchema = new Schema({
  name: { type: String, required: true, unique: true },
});

SkillSchema.index({ name: 'text' });

export default mongoose.model('Skill', SkillSchema);
