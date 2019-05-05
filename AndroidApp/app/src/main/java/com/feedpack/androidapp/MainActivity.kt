package com.feedpack.androidapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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

        //Start scan function
        startScanBtn.setOnClickListener {
            run {
                IntentIntegrator(this@MainActivity).initiateScan()
            }
        }

        //Fetch product
        getProductBtn.setOnClickListener {
            val url = "http://10.0.2.2:3002/getProduct"
//            val url = "http://192.168.0.101:3002/getProduct"

            val query = ProductQueryModel(
                itemNumber = itemNumberInput.text.toString(),
                supplier = supplierNumberInput.text.toString(),
                dwp = dwpNumberInput.text.toString()
            )

            val gson = GsonBuilder().setPrettyPrinting().create()

            val body = JSONObject(gson.toJson(query))

            val request = JsonObjectRequest(Request.Method.POST, url, body,
                Response.Listener { response ->
                    try {
                        val intent = ChooseDwpActivity.newIntent(this, response.toString())
                        startActivity(intent)

                    } catch (e:Exception) {
                        Log.d("App", "Exception $e")
                    }
                }, Response.ErrorListener { Log.d("App", "Volley error from main $it") })

            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }

    }

    //Scanner result handler
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                val item = result.contents.substring(0, 6)
                val sup = result.contents.substring(6)

                itemNumberInput.text = Editable.Factory.getInstance().newEditable(item)
                supplierNumberInput.text = Editable.Factory.getInstance().newEditable(sup)

                Log.d("App", "Scan success: ${result.contents}")
            } else {
                Log.d("App", "Scan failed")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
