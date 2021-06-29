package com.example.ecomseller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val myIntent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(myIntent)
            finish()
        },2500)
    }
}