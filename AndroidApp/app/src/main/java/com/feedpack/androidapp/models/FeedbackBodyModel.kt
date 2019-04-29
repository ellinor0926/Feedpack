package com.feedpack.androidapp.models

data class FeedbackBodyModel (
    val productId: Int,
    val userId: Int,
    val comment: String,
    val types: List<Int>
)