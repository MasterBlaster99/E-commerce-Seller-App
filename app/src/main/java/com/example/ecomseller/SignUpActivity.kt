package com.example.ecomseller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    //Sign Up Parameters Declaration
    private lateinit var name : String
    var password : String = ""
    var passwordConfirm : String = ""
    private lateinit var registerBtn:Button
    private lateinit var map:HashMap<String,String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var Name:EditText=findViewById(R.id.editTextName)
        var Email:EditText=findViewById(R.id.editTextEmail)
        var Password:EditText=findViewById(R.id.editTextPassword)
        var PasswordConfirm:EditText=findViewById(R.id.editTextConfirmPassword)
        registerBtn=findViewById(R.id.registerBtn)
        var firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()


        // Defining


        registerBtn.setOnClickListener {
            val email=Email.text.toString()
            name=Name.text.toString()
            password=Password.text.toString()
            passwordConfirm=PasswordConfirm.text.toString()
            Name.setHintTextColor(Color.RED)
            Name.hint=email
            if(name.isEmpty()){
                Name.setHintTextColor(Color.RED)
                Name.hint="Name cannot be empty"
            }
            if(email.isEmpty()){
                Email.setHintTextColor(Color.RED)
                Email.hint="Email cannot be empty"
            }
            if(!email.contains("@")){
                Email.setHintTextColor(Color.RED)
                Email.hint="Please enter a valid Email"
            }
            if(!email.contains(".com")){
                Email.setHintTextColor(Color.RED)
                Email.hint="Please enter a valid Email"
            }
            if(name.isEmpty()){
                Name.setHintTextColor(Color.RED)
                Name.hint="Name cannot be empty"
            }
            if(password.isEmpty()){
                Password.setHintTextColor(Color.RED)
                Password.hint="Password cannot be empty"
            }
            if(password.length<8){
                Password.setHintTextColor(Color.RED)
                Password.hint="enter at least 8 characters"
            }
            if(passwordConfirm.isEmpty()){
                PasswordConfirm.setHintTextColor(Color.RED)
                PasswordConfirm.hint="Cannot be empty"
            }
            if(passwordConfirm.length<8){
                PasswordConfirm.setHintTextColor(Color.RED)
                PasswordConfirm.hint="enter at least 8 characters"
            }
            if(!passwordConfirm.equals(password)){
                PasswordConfirm.setHintTextColor(Color.RED)
                PasswordConfirm.hint="Does not match"
            }
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(OnCompleteListener {
                var firestoreDB = FirebaseFirestore.getInstance()
                map = HashMap<String,String>()
                map.put("name",name)
                map.put("email",email)
                map.put("password",password)
                var userID = firebaseAuth.uid
                var docRef = firestoreDB.collection("sellers").document(userID.toString())
                docRef.set(map)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            })

        }
    }
}