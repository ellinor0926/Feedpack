package com.feedpack.androidapp.views.feedback

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.feedpack.androidapp.R
import com.feedpack.androidapp.models.FeedbackModel

class FeedbackViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.read_feedback_row, parent, false)) {

    private var commentView: TextView? = null
    private var firstNameView: TextView? = null
    private var lastNameView: TextView? = null
    private var typesView: TextView? = null

    init {
        commentView = itemView.findViewById(R.id.read_feedback_comment)
        firstNameView = itemView.findViewById(R.id.feedback_first_name)
        lastNameView = itemView.findViewById(R.id.feedback_last_name)
        typesView = itemView.findViewById(R.id.feedback_types)
    }

    fun bind(feedback: FeedbackModel) {
        commentView?.text = feedback.comment
        firstNameView?.text = feedback.sender.first_name
        lastNameView?.text = feedback.sender.last_name
        typesView?.text = feedback.types.toString()
    }

}