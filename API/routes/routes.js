const pool = require('../data/config');
const controller = require('../controllers/productsController');

const router = app => {
    app.get('/', controller.getProducts);
    app.post('/getProduct', controller.getProduct);
}

module.exports = router;