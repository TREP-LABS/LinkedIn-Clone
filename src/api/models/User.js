import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';

const { Schema } = mongoose;

/**
 * Async function to hash user password.
 * @param {Object} password
 */
const hashPassword = async (password) => {
  const salt = await bcrypt.genSalt(10);
  return await bcrypt.hash(password, salt);
};

const options = { timestamps: { createdAt: 'createdAt' } };

const UserSchema = new Schema(
  {
    firstname: { type: String },
    lastname: { type: String },
    email: {
      type: String,
      lowercase: true,
      unique: true,
    },
    password: { type: String },
    resetPasswordToken: String,
    resetPasswordExpire: Date,
  },
  options,
);

UserSchema.pre('save', async function (next) {
  if (this.isModified('password')) this.password = await hashPassword(this.password);

  next();
});

export default mongoose.model('User', UserSchema);
