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
import android.widget.ImageView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.feedpack.androidapp.views.choosedwp.ChooseDwpAdapter
import com.feedpack.androidapp.R
import com.feedpack.androidapp.VolleySingleton
import com.feedpack.androidapp.models.FeedbackBodyModel
import com.feedpack.androidapp.models.ProductModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_feedback.*
import org.json.JSONObject
import java.lang.Exception

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
        val che6: CheckBox = findViewById(R.id.checkBox_type_id_6)
//        val che2: CheckBox = findViewById(R.id.checkBox_type_id_2)
//        val che3: CheckBox = findViewById(R.id.checkBox_type_id_3)

        val feedbackComment: EditText = findViewById(R.id.feedbackComment)

        val feedbackBody = FeedbackBodyModel(
            productId = product.id,
            userId = 1,
            comment = feedbackComment.text.toString(),
            types = listOf(
                if (che1.isChecked) 1 else 0,
                if (che2.isChecked) 2 else 0,
                if (che3.isChecked) 3 else 0,
                if (che6.isChecked) 6 else 0
            ).filter { it > 0 }
        )
        val url = "http://10.0.2.2:3002/postFeedback"
        val body = JSONObject(gson.toJson(feedbackBody))
        val request = JsonObjectRequest(
            Request.Method.POST, url, body,
            Response.Listener { response ->
                try {
                    addFragment(ReadFeedbackFragment())
                    Log.d("App", "Post successful $response")
                } catch (e: Exception) {
                    Log.d("App", "Exception $e")
                }
            }, Response.ErrorListener { Log.d("App", "Volley error $it") })

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        VolleySingleton.getInstance(this).addToRequestQueue(request)



        Log.d("App", "feedbackBody: $feedbackBody")
    }

    fun toggleCheckBox(view: View) {
        if (view is ImageView) {

            when (view.id) {
                R.id.inspiring_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_6)
                    che.isChecked = !che.isChecked
                    
                }
            }
        }
    }

}




