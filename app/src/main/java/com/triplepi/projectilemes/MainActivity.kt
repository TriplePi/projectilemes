package com.triplepi.projectilemes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val tv: TextView by lazy { findViewById<TextView>(R.id.log) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            val product = (application as App).api.getProduct(1).execute()
            runOnUiThread {
                tv.text = product.body()?.toString() ?: product.code().toString()
            }
        }
    }
}
