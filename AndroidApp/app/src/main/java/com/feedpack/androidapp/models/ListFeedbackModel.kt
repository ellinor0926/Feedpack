package com.feedpack.androidapp.models

data class ListFeedbackModel (
    val id: Int,
    val dwpNumber: String,
    val itemNumber: String,
    val itemName: String,
    val supplier: String,
    val comment: String
)