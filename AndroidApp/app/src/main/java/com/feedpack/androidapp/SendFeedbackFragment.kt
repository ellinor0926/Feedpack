package com.feedpack.androidapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_send_feedback.*

class SendFeedbackFragment: Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_send_feedback, container, false)
    }

//    fun onCheckboxClicked(view: View) {
//        if (view is CheckBox) {
//            val checked: Boolean = view.isChecked
//
//            when (view.id) {
//                R.id.checkBox_type_id_1 -> {
//                    if (checked) {
//                        Log.d("App", "ID 1 is checked")
//                    } else {
//                        Log.d("App", "ID 1 is unchecked")
//                    }
//                }
//                R.id.checkBox_type_id_2 -> {
//                    if (checked) {
//                        Log.d("App", "ID 2 is checked")
//                    }
//                    else {
//                        Log.d("App", "ID 2 is unchecked")
//                    }
//                }
//            }
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        submitFeedbackBtn.setOnClickListener{
//            val resultString = StringBuilder()
//
//            if (checkBox_type_id_1.isChecked) {
//                resultString.append(1)
//            }
//            if (checkBox_type_id_2.isChecked) {
//                resultString.append(2)
//            }
//            if (checkBox_type_id_3.isChecked) {
//                resultString.append(3)
//            }
//            if (checkBox_type_id_4.isChecked) {
//                resultString.append(4)
//            }
//            if (checkBox_type_id_5.isChecked) {
//                resultString.append(5)
//            }
//            if (checkBox_type_id_6.isChecked) {
//                resultString.append(6)
//            }
//
//            Log.d("App", resultString.toString())
//        }
//    }

}