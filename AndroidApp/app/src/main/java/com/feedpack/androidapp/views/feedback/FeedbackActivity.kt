package com.feedpack.androidapp.views.feedback

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.feedpack.androidapp.views.choosedwp.ChooseDwpAdapter
import com.feedpack.androidapp.R
import com.feedpack.androidapp.models.FeedbackBodyModel
import com.feedpack.androidapp.models.ProductModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        bottom_navigation.setOnNavigationItemSelectedListener(navListener)

        // Default fragment
        addFragment(SendFeedbackFragment())
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

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }

    fun onFeedbackSubmitClicked(view: View) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val product =
            gson.fromJson(this.intent.extras[ChooseDwpAdapter.CHOSEN_PRODUCT].toString(), ProductModel::class.java)

        val che1: CheckBox = findViewById(R.id.checkBox_type_id_1)
        val che2: CheckBox = findViewById(R.id.checkBox_type_id_2)
        val che3: CheckBox = findViewById(R.id.checkBox_type_id_3)
//        val che1: CheckBox = findViewById(R.id.checkBox_type_id_1)
//        val che2: CheckBox = findViewById(R.id.checkBox_type_id_2)
//        val che3: CheckBox = findViewById(R.id.checkBox_type_id_3)
        Log.d("App", "che1: ${che1.isChecked} | che2: ${che2.isChecked} | che3: ${che3.isChecked}")

        val feedbackComment: EditText = findViewById(R.id.feedbackComment)

        val feedbackBody = FeedbackBodyModel(
            productId = product.id,
            userId = 1,
            comment = feedbackComment.text.toString(),
            types = listOf(
                if (che1.isChecked) 1 else 0,
                if (che2.isChecked) 2 else 0,
                if (che3.isChecked) 3 else 0
            ).filter { it > 0 }
        )


        Log.d("App", "feedbackBody: $feedbackBody")
    }

}




