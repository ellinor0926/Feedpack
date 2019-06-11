package com.feedpack.androidapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_investigation_list.*

class InvestigationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investigation_list)

        button.setOnClickListener {
            intent = Intent(this, InvestigationActivity::class.java)
            startActivity(intent)
        }
    }
}
