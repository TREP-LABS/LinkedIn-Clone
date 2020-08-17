import bunyan from 'bunyan';
import bformat from 'bunyan-format';
import dotenv from 'dotenv';

dotenv.config();

const formatOut = bformat({ outputMode: 'short' });

export default bunyan.createLogger({
  name: process.env.APP_NAME,
  stream: formatOut,
  level: 'info',
});
