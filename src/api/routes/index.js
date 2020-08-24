import express from 'express';
import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';
import authRoutes from './auth.route';

const router = express.Router();

// Setup Swagger for API documentation.
const options = {
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      title: 'Linkedin Clone',
      version: '1.0.0',
      description: 'API documentation for the LinkedIn Clone App',
      license: {
        name: 'MIT',
        url: 'https://choosealicense.com/licenses/mit/',
      },
      contact: {
        name: 'TREP Labs',
        url: 'https://treplabs.co',
        email: 'Info@treplabs.co',
      },
    },
    servers: [
      {
        url: 'http://trep-lc-backend.herokuapp.com/api/v1',
      },
      {
        url: 'http://127.0.0.1:5000/api/v1',
      },
    ],
  },
  apis: ['./src/api/models/*.js', './src/api/routes/*.js'],
};

const specs = swaggerJsdoc(options);
router.use('/api-docs', swaggerUi.serve);

router.get('/api-docs', swaggerUi.setup(specs, { explorer: true }));

router.use('/auth', authRoutes);

router.all('*', (req, res) => {
  res.status(404).json({ success: false, message: 'API endpoint does not exist.' });
});

export default router;
