package com.example.ecomseller

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.SellBtn).setOnClickListener {
            val myIntent = Intent(applicationContext,UploadProductActivity::class.java)
            startActivity(myIntent)
            finish() }
    }
}