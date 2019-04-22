const pool = require('../data/config');
const mysql = require('mysql');

exports.getProducts = (req, res) => {
    pool.query('SELECT * FROM products', (error, result) => {
        if (error) throw error;

        res.send(result)
    })
}

exports.getProduct = (req, res) => {
    let q = 
    `SELECT p.item_number, p.item_name, p.dwp_number, s.number FROM products p
    JOIN products_suppliers ps ON p.id = ps.product_id
    JOIN suppliers s ON ps.supplier_id = s.id
    WHERE p.item_number = ?
    AND s.number = ?`;
    
    let inserts = [req.body.itemNumber, req.body.supplier];

    if(req.body.dwp) {
     q += ` AND p.dwp_number = ?`;
     inserts.push(req.body.dwp)
    }

    q = mysql.format(q, inserts)

    pool.query(q, (error, result) => {
        if (error) throw error;

        const products = result.map(r => {
            return {
                item_number: r.item_number,
                item_name: r.item_name,
                dwp_number: r.dwp_number,
                supplier: r.number
            }
        })

        res.send({products})
    })
}