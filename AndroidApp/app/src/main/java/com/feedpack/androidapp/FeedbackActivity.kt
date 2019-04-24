package com.feedpack.androidapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : AppCompatActivity() {

    var product : ProductModel?
    var feedbackBody : FeedbackBodyModel?

    init {
        product = null
        feedbackBody = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = GsonBuilder().setPrettyPrinting().create()
        product = gson.fromJson(this.intent.extras[ChooseDwpAdapter.CHOSEN_PRODUCT].toString(), ProductModel::class.java)


//        feedbackBody = FeedbackBodyModel(
//            productId =
//
//        )

        setContentView(R.layout.activity_feedback)

        bottom_navigation.setOnNavigationItemSelectedListener(navListener)

        addFragment(SendFeedbackFragment())
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }
// Bottom Menu Navigation
    private val navListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.nav_comment -> {
                    addFragment(SendFeedbackFragment())
                    return true
                }
                R.id.nav_announcements -> {
                    addFragment(AnnouncementsFragment())
                    return true
                }
                R.id.nav_read_feedback -> {
                    addFragment(ReadFeedbackFragment())
                    return true
                }
            }
            return false
        }
    }

    fun onCheckboxClicked(view: View) {
        Log.d("App", "YOU HAVE PRoDUCT ID: ${product?.id}")
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_type_id_1 -> {
                    if (checked) {
                        feedbackBody?.types?.add(1)
                        Log.d("App", feedbackBody?.types.toString())
                    } else {
                        feedbackBody?.types?.remove(1)
                        Log.d("App", feedbackBody?.types.toString())
                    }
                }
                R.id.checkBox_type_id_2 -> {
                    if (checked) {
                        Log.d("App", "ID 2 is checked")
                    }
                    else {
                        Log.d("App", "ID 2 is unchecked")
                    }
                }
                R.id.checkBox_type_id_3 -> {
                    if (checked) {
                        Log.d("App", "ID 3 is checked")
                    } else {
                        Log.d("App", "ID 3 is unchecked")
                    }
                }
                R.id.checkBox_type_id_4 -> {
                    if (checked) {
                        Log.d("App", "ID 4 is checked")
                    }
                    else {
                        Log.d("App", "ID 4 is unchecked")
                    }
                }
                R.id.checkBox_type_id_5 -> {
                    if (checked) {
                        Log.d("App", "ID 5 is checked")
                    } else {
                        Log.d("App", "ID 5 is unchecked")
                    }
                }
                R.id.checkBox_type_id_6 -> {
                    if (checked) {
                        Log.d("App", "ID 6 is checked")
                    }
                    else {
                        Log.d("App", "ID 6 is unchecked")
                    }
                }
            }
        }
    }

}