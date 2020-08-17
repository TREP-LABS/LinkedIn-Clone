import express from 'express';
import morgan from 'morgan';
import cors from 'cors';
import { v4 as uuidv4 } from 'uuid';
import log from './api/utils/logger';

const app = express();

if (process.env.NODE_ENV?.trim() !== 'test') app.use(morgan('dev'));

app.use(express.json());

app.set('port', process.env.PORT || 5000);

app.use((req, res, next) => {
  const reqId = uuidv4();
  res.locals.log = log.child({ reqId });

  next();
});

app.get('/', (req, res) => {
  res.send('Hello, world!');
});

app.use(cors());

app.listen(app.get('port'), () => {
  log.info(`App started on port ${app.get('port')}`);
});
