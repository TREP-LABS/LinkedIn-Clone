import dotenv from 'dotenv';

dotenv.config();

export default {
  development: {
    dbUrl: process.env.DB_DEV_URL,
  },
  test: {
    dbUrl: process.env.DB_TEST_URL,
  },
  production: {
    dbUrl: process.env.DB_PROD_URL,
  },
};
