import express from 'express';
import authRoutes from './auth.route';

const router = express.Router();

router.use('/auth', authRoutes);

router.all('*', (req, res) => {
  res.status(404).json({ success: false, message: 'API endpoint does not exist.' });
});

export default router;
