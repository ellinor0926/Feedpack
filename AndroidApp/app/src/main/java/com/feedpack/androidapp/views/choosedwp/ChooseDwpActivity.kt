package com.feedpack.androidapp.views.choosedwp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.feedpack.androidapp.R
import com.feedpack.androidapp.models.ProductModel
import kotlinx.android.synthetic.main.activity_choose_dwp.*

class ChooseDwpActivity : AppCompatActivity() {

    data class ProductsModel(
        val products: ArrayList<ProductModel>
    )

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_choose_dwp)

        recyclerview_dwplist.layoutManager = LinearLayoutManager(this)
        recyclerview_dwplist.adapter = ChooseDwpAdapter(this)
    }

    companion object {
        val INTENT_DWP_LIST = "dwp_list"

        fun newIntent(context: Context, jsonString: String): Intent {
            val intent = Intent(context, ChooseDwpActivity::class.java)
            intent.putExtra(INTENT_DWP_LIST, jsonString)
            return intent
        }
    }

}