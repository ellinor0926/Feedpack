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