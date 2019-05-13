package com.feedpack.androidapp.views.feedback

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.feedpack.androidapp.views.choosedwp.CustomViewHolder
import com.google.gson.GsonBuilder
import org.json.JSONArray

class ReadFeedbackAdapter(feedbackActivity: FeedbackActivity, feedbackList: JSONArray): RecyclerView.Adapter<CustomViewHolder>() {

    val context: FeedbackActivity = feedbackActivity
    val gson = GsonBuilder().setPrettyPrinting().create()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}