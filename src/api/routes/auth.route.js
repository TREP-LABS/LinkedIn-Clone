import express from 'express';
import { users } from '../controllers';
import { validateInputs } from '../middlewares';

const router = express.Router();

router.route('/register').post(validateInputs('UserSchemas', 'register'), users.register);

export default router;
