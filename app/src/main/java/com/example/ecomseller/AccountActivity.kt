package com.example.ecomseller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AccountActivity : AppCompatActivity() {
    private lateinit var Name:EditText
    private lateinit var Phone:EditText
    private lateinit var Email:EditText
    private lateinit var Address:EditText
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title="Account Details"
        setSupportActionBar(toolbar)

//        var toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.title="Shop"

        Name=findViewById(R.id.NameEditText)
        Phone=findViewById(R.id.NumberEditText)
        Email=findViewById(R.id.EmailEditText)
        Address=findViewById(R.id.addressEditText)

        var Firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        if (Firebaseauth.currentUser != null) {
            var userID = Firebaseauth.uid.toString()
            val docRef = db.collection("users").document(userID)
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        Name.setText(document.get("name").toString())
                        Email.setText(document.get("email").toString())
                        if(document.get("phone")==null){

                        }else{
                            Phone.setText(document.get("phone").toString())
                        }
                        if(document.get("address")==null){

                        }else{
                            Address.setText(document.get("address").toString())
                        }
                    } else {
                    }
                } else {
                }
            }
        }

        findViewById<Button>(R.id.saveBtn).setOnClickListener {
            var name = Name.text.toString()
            var phone = Phone.text.toString()
            var email = Email.text.toString()
            var address = Address.text.toString()
            var userID = Firebaseauth.currentUser!!.uid
            val documentReference: DocumentReference = db.collection("users").document(userID)
            val user: MutableMap<String, Any> = HashMap()
            user["name"] = name
            user["phone"] = phone
            user["email"] = email
            user["address"] = address
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            documentReference.update(user).addOnSuccessListener { }
        }

    }
}