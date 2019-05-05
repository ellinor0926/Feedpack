package com.feedpack.androidapp.views.feedback

import android.graphics.Color
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
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.feedpack.androidapp.views.choosedwp.ChooseDwpAdapter
import com.feedpack.androidapp.R
import com.feedpack.androidapp.VolleySingleton
import com.feedpack.androidapp.models.FeedbackBodyModel
import com.feedpack.androidapp.models.ProductModel
import com.feedpack.androidapp.CameraFragment
import com.feedpack.androidapp.FragmentStateHelper
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_feedback.*
import org.json.JSONObject
import java.lang.Exception

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

//        Set toolbar title
        val gson = GsonBuilder().setPrettyPrinting().create()
        val product =
            gson.fromJson(this.intent.extras[ChooseDwpAdapter.CHOSEN_PRODUCT].toString(), ProductModel::class.java)
        title = product.item_name

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
        val che4: CheckBox = findViewById(R.id.checkBox_type_id_4)
        val che5: CheckBox = findViewById(R.id.checkBox_type_id_5)
        val che6: CheckBox = findViewById(R.id.checkBox_type_id_6)

        val feedbackComment: EditText = findViewById(R.id.feedbackComment)

        val feedbackBody = FeedbackBodyModel(
            productId = product.id,
            userId = 1,
            comment = feedbackComment.text.toString(),
            types = listOf(
                if (che1.isChecked) 1 else 0,
                if (che2.isChecked) 2 else 0,
                if (che3.isChecked) 3 else 0,
                if (che4.isChecked) 4 else 0,
                if (che5.isChecked) 5 else 0,
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
            }, Response.ErrorListener { Log.d("App", "Volley error from feedback $it") })

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        VolleySingleton.getInstance(this).addToRequestQueue(request)

        Log.d("App", "feedbackBody: $feedbackBody")
    }

    //    ImageView toggles efficiency, ergonomic, inspiring
    fun toggleCheckBox(view: View) {
        if (view is ImageView) {

            when (view.id) {
                R.id.efficiency_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_4)
                    val label: TextView = findViewById(R.id.efficiency_label)

                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        view.setColorFilter(Color.argb(100, 76, 175, 80))
                        label.setTextColor(resources.getColor(R.color.colorBright))
                    } else {
                        view.setColorFilter(Color.argb(100, 216, 216, 216))
                        label.setTextColor(resources.getColor(R.color.colorPrimary))
                    }
                }
                R.id.ergonomic_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_5)
                    val label: TextView = findViewById(R.id.ergonomic_label)

                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        view.setColorFilter(Color.argb(100, 76, 175, 80))
                        label.setTextColor(resources.getColor(R.color.colorBright))
                    } else {
                        view.setColorFilter(Color.argb(100, 216, 216, 216))
                        label.setTextColor(resources.getColor(R.color.colorPrimary))
                    }

                }
                R.id.inspiring_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_6)
                    val label: TextView = findViewById(R.id.inspiring_label)

                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        view.setColorFilter(Color.argb(100, 76, 175, 80))
                        label.setTextColor(resources.getColor(R.color.colorBright))
                    } else {
                        view.setColorFilter(Color.argb(100, 216, 216, 216))
                        label.setTextColor(resources.getColor(R.color.colorPrimary))
                    }

                }
            }
        }
    }

    //    Camera Activity
    fun startCamera(view: View) {
//        FragmentStateHelper()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CameraFragment())
            .addToBackStack(null)
            .commit()
    }

}
