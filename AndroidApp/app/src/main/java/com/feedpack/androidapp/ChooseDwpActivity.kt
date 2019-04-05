package com.feedpack.androidapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
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
            products = gson.fromJson(context.intent.extras[INTENT_DWP_LIST].toString(), ProductsModel::class.java).products

        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return products.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(context)
            val row = layoutInflater.inflate(R.layout.dwp_list_row, null, false)

            val dwpNumber = row.findViewById<TextView>(R.id.dwpNumber)
            dwpNumber.text = "DWP number: ${products[position].dwp_number}"

            val itemName = row.findViewById<TextView>(R.id.itemname)
            itemName.text = "Name: ${products[position].item_name}"

            val supplierNumber = row.findViewById<TextView>(R.id.suppliernumber)
            supplierNumber.text = "Supplier: ${products[position].supplier}"

            val itemNumber = row.findViewById<TextView>(R.id.itemnumber)
            itemNumber.text = "Item number: ${products[position].item_number}"

            return row
        }

    }


}