package com.feedpack.androidapp

data class FeedbackBodyModel (
    val productId: Int,
    val userId: Int,
    val comment: String,
    val types: MutableList<Int>
)