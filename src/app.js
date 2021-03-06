import express from 'express';
import morgan from 'morgan';
import cors from 'cors';
import { v4 as uuidv4 } from 'uuid';
import log from './api/utils/logger';
import { errorHandler } from './api/middlewares';
import routes from './api/routes';

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
  res.send(
    '<center><h1>LinkedIn Clone</h1><a href="http://trep-lc-backend.herokuapp.com/api/v1/api-docs">Documentation</a></center>',
  );
});

app.use(cors());

app.use('/api/v1/', routes);

app.use(errorHandler);

app.listen(app.get('port'), () => {
  log.info(`App started on port ${app.get('port')}`);
});

export default app;
