const pool = require('../data/config');
const mysql = require('mysql');

exports.postFeedback = (req, res) => {
    let typesArray = req.body.types;
    let feedbackId;

    let q = `
    INSERT INTO feedback (product_id, user_id, comment)
    VALUES (?, ?, ?);
    `;

    let inserts = [req.body.productId, req.body.userId, req.body.comment];

    q = mysql.format(q, inserts)

    let queryTypes = `
    INSERT INTO feedback_has_types (feedback_id, type_id)
    VALUES `;


    pool.query(q, (error, result) => {
        if (error) throw error;

        feedbackId = result.insertId
         
        let types = typesArray.map(type => {
            queryTypes += `(?)`;
            if(typesArray.indexOf(type) === typesArray.length - 1) {
                return [feedbackId, type] 
            } else {
                queryTypes += `, `
                return [feedbackId, type] 
            }
        })

        queryTypes = mysql.format(queryTypes, types)

        pool.query(queryTypes, (error, result) => {
            if (error) throw error

            res.send({message: 'Feedback was successfully posted'})
        })
    })
    



}

exports.getFeedbackOnProduct = (req, res) => {
    let query = `SELECT f.id, ft.feedback_id as type_feedback_id, f.comment, GROUP_CONCAT(t.name) as type, u.first_name, u.last_name, p.item_name, p.item_number, p.dwp_number, s.number as supplier, DATE_FORMAT(f.created_at, "%Y-%m-%d %H:%i") as created_at FROM feedback f
    JOIN products p ON f.product_id = p.id
    JOIN feedback_has_types ft ON f.id = ft.feedback_id
    JOIN types t ON ft.type_id = t.id
    JOIN users u ON f.user_id = u.id
    JOIN products_suppliers ps ON p.id = ps.product_id
    JOIN suppliers s ON ps.supplier_id = s.id
    WHERE f.product_id = ?
    GROUP BY f.id`

    let productId = req.params.id;

    query = mysql.format(query, productId)

    pool.query(query, (error, result) => {
        if (error) throw error;

        let feedback = result.map((r, i) => {

            return {
                id: r.id,
                dwp_number: r.dwp_number,
                item_number: r.item_number,
                item_name: r.item_name,
                supplier: r.supplier,
                sender: {
                    first_name: r.first_name,
                    last_name: r.last_name
                },
                types: r.type.split(','),
                comment: r.comment,
                created_at: r.created_at
            }
        })
        

        res.send(feedback)
    })
}