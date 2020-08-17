import mongoose from 'mongoose';
import config from '../config/config';

// Import models.
import User from './User';

const env = process.env.NODE_ENV?.trim() || 'development';

const presetConfig = config[env];

mongoose.connect(presetConfig.dbUrl, {
  useNewUrlParser: true,
  useCreateIndex: true,
  useUnifiedTopology: true,
});

export default {
  User,
};
