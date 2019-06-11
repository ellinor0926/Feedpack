package com.feedpack.androidapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.feedpack.androidapp.models.ProductQueryModel
import com.feedpack.androidapp.views.choosedwp.ChooseDwpActivity
import com.google.gson.GsonBuilder
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_feedpack_btn.setOnClickListener {

            val intent = Intent(this, FeedpackActivity::class.java)
            startActivity(intent)
        }

        investigation_btn.setOnClickListener {
            val intent = Intent(this, InvestigationListActivity::class.java)
            startActivity(intent)
        }

        casy_btn.setOnClickListener {
            val intent = Intent(this, CasyActivity::class.java)
            startActivity(intent)
        }

        sales_btn.setOnClickListener {
            val intent = Intent(this, SalesActivity::class.java)
            startActivity(intent)
        }

    }





}
