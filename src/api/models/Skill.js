import mongoose from 'mongoose';

const { Schema } = mongoose;

const SkillSchema = new Schema({
  name: { type: String, required: true, unique: true },
});

export default mongoose.model('Skill', SkillSchema);
