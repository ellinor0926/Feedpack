package com.feedpack.androidapp.models

data class FeedbackModel (
    val id: Int,
    val dwp_number: String,
    val item_number: String,
    val item_name: String,
    val supplier: String,
    val comment: String,
    val sender: SenderModel,
    val types: ArrayList<String>
)