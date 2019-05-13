const pool = require('../data/config');
const productController = require('../controllers/productsController');
const feedbackController = require('../controllers/feedbackController');

const router = app => {
    app.get('/', productController.getProducts);
    app.post('/getProduct', productController.getProduct);
    app.post('/postFeedback', feedbackController.postFeedback);
    app.get('/getFeedbackOnProduct/:id', feedbackController.getFeedbackOnProduct);
}

module.exports = router;