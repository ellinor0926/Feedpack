package com.feedpack.androidapp.views.feedback

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.feedpack.androidapp.views.choosedwp.ChooseDwpAdapter
import com.feedpack.androidapp.R
import com.feedpack.androidapp.VolleySingleton
import com.feedpack.androidapp.models.FeedbackBodyModel
import com.feedpack.androidapp.models.FeedbackModel
import com.feedpack.androidapp.models.ProductModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.fragment_read_feedback.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class FeedbackActivity : AppCompatActivity() {

    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    private val GALLERY = 1
    private val CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

//        Set toolbar title
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

                    val product =
                        gson.fromJson(
                            this@FeedbackActivity.intent.extras[ChooseDwpAdapter.CHOSEN_PRODUCT].toString(),
                            ProductModel::class.java
                        )
                    val url = "http://10.0.2.2:3002/getFeedbackOnProduct/${product.id}"
                    val request = JsonArrayRequest(Request.Method.GET, url, null,
                        Response.Listener<JSONArray> { response ->
                            try {
                                Log.d("App", "$response")
                                val feedbackList: ArrayList<FeedbackModel> = gson.fromJson(
                                    response.toString(),
                                    object : TypeToken<ArrayList<FeedbackModel>>() {}.type
                                )
                                feedbackList.reverse()
                                recyclerview_feedback_list.apply {
                                    layoutManager = LinearLayoutManager(FeedbackActivity())
                                    adapter = ReadFeedbackAdapter(feedbackList)
                                }


                            } catch (e: Exception) {
                                Log.d("App", "Exception in feedback fetch $e")
                            }
                        }, Response.ErrorListener { Log.d("App", "Volley error from feedback $it") })

                    request.retryPolicy = DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        // 0 means no retry
                        0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                        1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )

                    VolleySingleton.getInstance(this@FeedbackActivity).addToRequestQueue(request)

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

        val che1: CheckBox = findViewById(R.id.checkBox_type_id_1)
        val che2: CheckBox = findViewById(R.id.checkBox_type_id_2)
        val che3: CheckBox = findViewById(R.id.checkBox_type_id_3)
        val che4: CheckBox = findViewById(R.id.checkBox_type_id_4)
        val che5: CheckBox = findViewById(R.id.checkBox_type_id_5)
        val che6: CheckBox = findViewById(R.id.checkBox_type_id_6)

        val feedbackComment: EditText = findViewById(R.id.feedbackComment)

        val product =
            gson.fromJson(this.intent.extras[ChooseDwpAdapter.CHOSEN_PRODUCT].toString(), ProductModel::class.java)

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

//                    addFragment(ReadFeedbackFragment())
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

    //    ImageView toggles efficiency, ergonomic, inspiring checkboxes
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
                R.id.consumer_pack_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_1)
                    val checkmark: ImageView = findViewById(R.id.consumer_pack_check)
                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        checkmark.visibility = View.VISIBLE


                    } else {
                        checkmark.visibility = View.INVISIBLE

                    }
                }
                R.id.multi_pack_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_2)
                    val checkmark: ImageView = findViewById(R.id.multi_pack_check)
                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        checkmark.visibility = View.VISIBLE


                    } else {
                        checkmark.visibility = View.INVISIBLE

                    }
                }
                R.id.unit_load_btn -> {
                    val che: CheckBox = findViewById(R.id.checkBox_type_id_3)
                    val checkmark: ImageView = findViewById(R.id.unit_load_check)
                    che.isChecked = !che.isChecked

                    if (che.isChecked) {
                        checkmark.visibility = View.VISIBLE


                    } else {
                        checkmark.visibility = View.INVISIBLE

                    }
                }
            }
        }
    }


    fun choosePhotoFromGallary(view: View) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }


    /**
     *     Camera Activity
     */
    fun startCamera(view: View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA)
            }
        }
    }

    var photos: MutableList<Bitmap> = mutableListOf()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY) {
            val contentURI = data!!.data

            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
            photos.add(imageBitmap)
        }
        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            photos.add(imageBitmap)
        }

        val image1 = findViewById<View>(R.id.img1) as ImageView?
        val image2 = findViewById<View>(R.id.img2) as ImageView?

        image1!!.setImageBitmap(photos.get(0))
        if (photos.size == 2) {
            image2!!.setImageBitmap(photos.get(1))
        }
    }


}































