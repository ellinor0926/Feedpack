const mysql = require('mysql');

const config = {
    host     : 'localhost',
    user     : 'root',
    password : '5Ht$v6BJp%z!&D',
    database : 'dwp_mock'
}

const pool = mysql.createPool(config);

module.exports = pool;