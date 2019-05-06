package com.feedpack.androidapp.views.choosedwp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feedpack.androidapp.R
import com.feedpack.androidapp.models.ProductModel
import com.feedpack.androidapp.views.feedback.FeedbackActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.dwp_list_row.view.*
import org.json.JSONObject

class ChooseDwpAdapter(chooseDwpActivity: ChooseDwpActivity): RecyclerView.Adapter<CustomViewHolder>() {

    companion object {
        val PRODUCT_TITLE = "PRODUCT_TITLE"
        val CHOSEN_PRODUCT = "CHOSEN_PRODUCT"
    }

    val context : ChooseDwpActivity
    val products: ArrayList<ProductModel>

    init {
        context = chooseDwpActivity
        val gson = GsonBuilder().setPrettyPrinting().create()
        products = gson.fromJson(context.intent.extras[ChooseDwpActivity.INTENT_DWP_LIST].toString(), ChooseDwpActivity.ProductsModel::class.java).products

    }

//    NumberOfItems
    override fun getItemCount(): Int {
       return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.dwp_list_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.dwpNumber.text = "DWP number: ${products[position].dwp_number}"
        holder.view.itemname.text = "Name: ${products[position].item_name}"
        holder.view.suppliernumber.text = "Supplier: ${products[position].supplier}"
        holder.view.itemnumber.text = "Item number: ${products[position].item_number}"

        val product = products[position]
        holder.product = product
    }
}

class CustomViewHolder(val view: View, var product: ProductModel? = null): RecyclerView.ViewHolder(view) {


    init {
        view.setOnClickListener {
            val intent = Intent(view.context, FeedbackActivity::class.java)

            val gson = GsonBuilder().setPrettyPrinting().create()

            val jsonProduct = JSONObject(gson.toJson(product))

            intent.putExtra(ChooseDwpAdapter.PRODUCT_TITLE, product?.item_name)
            intent.putExtra(ChooseDwpAdapter.CHOSEN_PRODUCT, jsonProduct.toString())

            view.context.startActivity(intent)

        }
    }
}