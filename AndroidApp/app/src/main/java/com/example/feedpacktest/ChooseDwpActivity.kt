package com.example.feedpacktest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_choose_dwp.*

class ChooseDwpActivity : Activity() {

    data class ProductsModel(
        val products: ArrayList<ProductModel>
    )

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_choose_dwp)

        chooseDwpList.adapter = DwpAdapter(this)
    }

    companion object {
        private val INTENT_DWP_LIST = "dwp_list"

        fun newIntent(context: Context, jsonString: String): Intent {
            val intent = Intent(context, ChooseDwpActivity::class.java)
            intent.putExtra(INTENT_DWP_LIST, jsonString)
            return intent
        }
    }

    private class DwpAdapter(chooseDwpActivity: ChooseDwpActivity) : BaseAdapter() {

        val context : ChooseDwpActivity
        val products: ArrayList<ProductModel>

        init {
            context = chooseDwpActivity
            val gson = GsonBuilder().setPrettyPrinting().create()
            products = gson.fromJson(context.intent.extras.toString(), ProductsModel::class.java).products
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView = TextView(context)
            textView.text = "WOOO HOOOOO"

            Log.d("App", products.toString())

            return textView
        }

    }


}