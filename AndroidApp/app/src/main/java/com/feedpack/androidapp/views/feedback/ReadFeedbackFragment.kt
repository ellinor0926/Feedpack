package com.feedpack.androidapp.views.feedback

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feedpack.androidapp.R
import com.feedpack.androidapp.models.FeedbackModel

class ReadFeedbackFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_read_feedback, container, false)

}