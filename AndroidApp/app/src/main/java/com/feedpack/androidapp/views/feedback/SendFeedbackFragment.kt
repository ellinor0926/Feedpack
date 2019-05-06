package com.feedpack.androidapp.views.feedback

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feedpack.androidapp.FragmentStateHelper
import com.feedpack.androidapp.R


class SendFeedbackFragment: Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_send_feedback, container, false)
    }

    override fun onPause() {
        Log.d("App", "$isStateSaved")
        super.onPause()

    }

}

