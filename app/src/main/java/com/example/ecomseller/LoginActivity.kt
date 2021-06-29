package com.example.ecomseller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var EmailET : EditText
    private lateinit var PasswordET : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        EmailET = findViewById(R.id.editTextEmail);
        PasswordET = findViewById(R.id.editTextPassword);

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            var email = EmailET.text.toString()
            var password = PasswordET.text.toString()
            if(email.isEmpty()){
                EmailET.setHintTextColor(Color.RED)
                EmailET.hint="Email cannot be empty"
            }
            if(!email.contains("@")){
                EmailET.setHintTextColor(Color.RED)
                EmailET.hint="Please enter a valid Email"
            }
            if(!email.contains(".com")){
                EmailET.setHintTextColor(Color.RED)
                EmailET.hint="Please enter a valid Email"
            }
            if(password.isEmpty()){
                PasswordET.setHintTextColor(Color.RED)
                PasswordET.hint="Password cannot be empty"
            }
            if(password.length<8){
                PasswordET.setHintTextColor(Color.RED)
                PasswordET.hint="enter at least 8 characters"
            } else {
                firebaseAuth.signInWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener(
                    OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {
                            finish()
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                            Toast.makeText(
                                this@LoginActivity,
                                "successfully logged in",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(applicationContext,"Something went Wrong", Toast.LENGTH_LONG);
                        }
                    })
            }
        }

        findViewById<TextView>(R.id.registerBtn).setOnClickListener {
            val myIntent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
    }
}