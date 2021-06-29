package com.example.ecomseller

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList

class UploadProductActivity : AppCompatActivity() {

    private lateinit var Title:EditText
    private lateinit var Description:EditText
    private lateinit var Specifications:EditText
    private lateinit var Details:EditText
    private lateinit var Amount:EditText
    private lateinit var ProductImage:ImageView
    private lateinit var selectImage:ImageButton
    private lateinit var categoryBtn:Button
    private var category : String = "NONE"
    private lateinit var imageUri: Uri
    private var bool : Boolean = false
    private lateinit var cod:CheckBox
    private lateinit var netBanking:CheckBox
    private lateinit var creditCard:CheckBox
    private lateinit var debitCard:CheckBox
    private var filename:String=""
    var Firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)
        selectImage=findViewById(R.id.selectImage)
        val user = FirebaseAuth.getInstance().currentUser
        val pu = user!!.photoUrl.toString()



        Title=findViewById(R.id.productTitle)
        Description=findViewById(R.id.descriptionTextView)
        Specifications=findViewById(R.id.Specifications)
        Details=findViewById(R.id.ProductDetails)
        ProductImage=findViewById(R.id.productImage)
        Amount=findViewById(R.id.amountEditText)

        netBanking=findViewById(R.id.netBanking)
        cod=findViewById(R.id.cod)
        creditCard=findViewById(R.id.creditCard)
        debitCard=findViewById(R.id.DebitCard)

        var Firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

      findViewById<Button>(R.id.SellBtn).setOnClickListener {
          var list = ArrayList<String>()
          var payment=false
          if(cod.isChecked){
              list.add("COD")
              payment=true
          }
          if(netBanking.isChecked){
              list.add("netBanking")
              payment=true
          }
          if(creditCard.isChecked){
              list.add("creditCard")
              payment=true
          }
          if(debitCard.isChecked){
              list.add("debitCard")
              payment=true
          }
          uploadImage()
          var title = Title.text.toString()
        var des = Description.text.toString()
        var specs = Specifications.text.toString()
        var details = Details.text.toString()
        var amount = Amount.text.toString()
        var userID = Firebaseauth.currentUser!!.uid
           if(!payment){
               Toast.makeText(applicationContext,"Please select at least one payment method",Toast.LENGTH_LONG)
           }
          if(title.isEmpty()){
              Title.setHintTextColor(Color.RED)
              Title.setHint("cannot be Empty")
          }
          if(amount.isEmpty()){
              Amount.setHintTextColor(Color.RED)
              Amount.setHint("cannot be Empty")
          }
          if(des.isEmpty()){
              Description.setHintTextColor(Color.RED)
              Description.setHint("cannot be Empty")
          }
          if(specs.isEmpty()){
              Specifications.setHintTextColor(Color.RED)
              Specifications.setHint("cannot be Empty")
          }
          if(details.isEmpty()){
              Details.setHintTextColor(Color.RED)
              Details.setHint("cannot be Empty")
          }
          if(!bool){
              Toast.makeText(applicationContext,"Please select an Image to Upload",Toast.LENGTH_LONG)
          }
          else {
              var colName=""
              if(category.equals("NONE")){
                  Toast.makeText(applicationContext,"Please select a Category",Toast.LENGTH_LONG)
              }else {
              if(category.equals("mobiles")){
                  colName="mobiles"
              }
              if(category.equals("laps")){
                  colName="laps"
              }
                  if(category.equals("All")){
                      colName="All"
                  }
              if(category.equals("office")){
                  colName="office"
              }
              if(category.equals("furniture")){
                  colName="furniture"
              }
              if(category.equals("watches")){
                  colName="watches"
              }
              if(category.equals("toys")){
                  colName="toys"
              }
              if(category.equals("sports")){
                  colName="sports"
              }
              if(category.equals("books")){
                  colName="books"
              }
              val documentReference: DocumentReference = db.collection(colName).document()
              val user: MutableMap<String, Any> = HashMap()
              user["title"] = title
              user["des"] = des
              user["specs"] = specs
              user["details"] = details
              user["photoURL"] = filename
                  user["payments"] = list
                  user["amount"] = amount

              val intent = Intent(applicationContext, HomeActivity::class.java)
              startActivity(intent)
              documentReference.set(user).addOnSuccessListener { }
                  if(!colName.equals("All")){
                      val documentReference: DocumentReference = db.collection("All").document()
                      val user: MutableMap<String, Any> = HashMap()
                      user["title"] = title
                      user["des"] = des
                      user["specs"] = specs
                      user["details"] = details
                      user["photoURL"] = filename
                      user["payments"] = list
                      user["amount"] = amount
                      val intent = Intent(applicationContext, HomeActivity::class.java)
                      startActivity(intent)
                      documentReference.set(user).addOnSuccessListener { }
                  }
          }}
      }
        categoryBtn=findViewById(R.id.categoryBtn)
      categoryBtn.setOnClickListener {
          val popup = PopupMenu(applicationContext, categoryBtn)
          popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
          popup.show()
          popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
              when (menuItem.itemId) {
                  R.id.item1 -> {
                      categoryBtn.setText("Mobile Handsets")
                      category="mobiles"
                  }
                  R.id.item2 -> {
                      categoryBtn.setText("Laptops and PC's")
                      category="laps"
                  }
                  R.id.item3 -> {
                      categoryBtn.setText("Office Items")
                      category="office"
                  }
                  R.id.item4 -> {
                      categoryBtn.setText("Furniture")
                      category="furniture"
                  }
                  R.id.item5 -> {
                      categoryBtn.setText("watches")
                      category="watches"
                  }
                  R.id.item6 -> {
                      categoryBtn.setText("Sports")
                      category="sports"
                  }
                  R.id.item7 -> {
                      categoryBtn.setText("Toys")
                      category="toys"
                  }
                  R.id.item8 -> {
                      categoryBtn.setText("Books")
                      category="books"
                  }
                  R.id.item9 -> {
                      categoryBtn.setText("Kitchen wear")
                      category="kitchen"
                  }
                  R.id.item10 -> {
                      categoryBtn.setText("All")
                      category="All"
                  }
              }
              false
          }) }
        selectImage.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }
    private fun uploadImage() {
        if (ProductImage.drawable == null) {
            Toast.makeText(applicationContext, "Please Upload an Image", Toast.LENGTH_LONG)
        } else {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
            filename = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("ProductImages/$filename")
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG)
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        }
    }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode==RESULT_OK){
            imageUri=data?.data!!
            bool=true
            Log.d("checkIMG",imageUri.toString())
            ProductImage.setImageURI(imageUri)
        }
    }
}