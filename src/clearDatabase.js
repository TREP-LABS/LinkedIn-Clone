/* eslint-disable no-console */
import dotenv from 'dotenv';
import db from './api/models';

dotenv.config();

const { User, Profile } = db;

const clearDatabase = async () => {
  try {
    console.log('Clearing users collection...');
    const deleteUsers = User.deleteMany({});

    console.log('Clearing profiles collection...');
    const deleteProfiles = Profile.deleteMany({});

    Promise.all([deleteUsers, deleteProfiles]).then(() => {
      console.log('\nDatabase cleared successfully.');
      process.exit();
    });
  } catch (error) {
    console.log(error);
  }
};

const env = process.env.NODE_ENV?.trim();

if (env === 'test') {
  clearDatabase();
} else {
  process.exit();
}
