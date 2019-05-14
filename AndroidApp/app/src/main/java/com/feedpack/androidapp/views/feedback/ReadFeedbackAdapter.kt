package com.feedpack.androidapp.views.feedback

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.feedpack.androidapp.models.FeedbackModel

class ReadFeedbackAdapter(private val list: ArrayList<FeedbackModel>)
    : RecyclerView.Adapter<FeedbackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return FeedbackViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback: FeedbackModel = list[position]
        holder.bind(feedback)
    }

}