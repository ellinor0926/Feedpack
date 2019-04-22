package com.feedpack.androidapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_type_id_1 -> {
                    if (checked) {
                        Log.d("App", "ID 1 is checked")
                    } else {
                        Log.d("App", "ID 1 is unchecked")
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