import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';
import slugify from 'slugify';

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

/**
 * @swagger
 *  components:
 *    schemas:
 *      User:
 *        type: object
 *        required:
 *          - id
 *          - firstname
 *          - lastname
 *          - email
 *          - password
 *        properties:
 *          id:
 *            type: string
 *          firstname:
 *            type: string
 *          lastname:
 *            type: string
 *          email:
 *            type: string
 *          password:
 *            type: string
 *        example:
 *          id: 537e1f77bcf86cd799439011
 *          firstname: John
 *          lastname: Doe
 *          email: johndoe@gmail.com
 *          password: Password123
 */
const UserSchema = new Schema(
  {
    firstname: { type: String },
    lastname: { type: String },
    email: {
      type: String,
      lowercase: true,
      unique: true,
    },
    slug: { type: String },
    password: { type: String },
    resetPasswordToken: String,
    resetPasswordExpire: Date,
  },
  options,
);

UserSchema.pre('save', async function (next) {
  if (this.isModified('password')) this.password = await hashPassword(this.password);

  if (this.isModified('firstname') || this.isModified('lastname')) {
    let slugExists = true;

    let slug = createSlug(this.firstname, this.lastname);

    let counter = 1;

    while (slugExists) {
      const dbSlug = await this.constructor.findOne({ slug });

      if (dbSlug) {
        if (counter == 1) {
          slug = slug.concat(`-${counter}`);
        } else {
          slug = incrementSlugNumber(slug);
        }

        slugExists = true;
      } else {
        this.slug = slug;
        slugExists = false;
      }

      counter++;
    }
  }

  next();
});

/**
 * Create slug from user's firstname and lastname.
 * @param {String} firstname The user's firstname.
 * @param {String} lastname The user's lastname.
 */
const createSlug = (firstname, lastname) => {
  const name = `${firstname} ${lastname}`;

  const slug = slugify(name, { lower: true });
  return slug;
};

/**
 * Increment number at the end of slug.
 * @param {String} slug The user's firstname and lastname that has been converted to a slug.
 */
const incrementSlugNumber = (slug) => {
  let slugArray = slug.split('-');
  let slugNumber = parseInt(slugArray.pop(), 10);
  slugNumber++;
  slugArray.push(slugNumber);
  slug = slugArray.join('-');

  return slug;
};

export default mongoose.model('User', UserSchema);
