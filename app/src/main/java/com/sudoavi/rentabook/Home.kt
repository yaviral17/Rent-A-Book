package com.sudoavi.rentabook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    lateinit var Uemail:String
    lateinit var  Upass :String
    var User = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        Uemail = intent.extras?.get("email").toString()

        if (Uemail != "null"){
                db.collection("USERS").document(Uemail).get()
                    .addOnSuccessListener {
                        details.text = "${it.getString("Name")} ${it.getString("Email")} ${it.getString("DOB")}"
                        saveData(Uemail)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show()
                    }
        }
        else{
                val mail = loadData()

                if (mail == "null"){
//                    Toast.makeText(this, "$mail  $Uemail ${loadData()}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,LoginPage::class.java))
                    finish()
                }
                else{
//                    Toast.makeText(this, "$mail  $Uemail ${loadData()}", Toast.LENGTH_SHORT).show()
                    db.collection("USERS").document(mail).get().addOnSuccessListener {
                        details.text = "${it.getString("Name")} ${it.getString("Email")} ${it.getString("DOB")}"
//                        saveData(Uemail)
                    }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to load from firestore !!", Toast.LENGTH_SHORT).show()
                        }
                }
        }



        Logout_btn.setOnClickListener {
            clearSharedpref()
//            Toast.makeText(this, "${loadData()}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginPage::class.java))
            finish()
        }

    }



    private fun loadData(): String {
        val sharedPreferences = getSharedPreferences("Email", Context.MODE_PRIVATE)
        val savedmail = sharedPreferences.getString("Email",null).toString()
        return savedmail
    }

    private fun saveData(Email:String){
        val InsertedEmail = Email
        val sharedPreferences = getSharedPreferences("Email",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("Email",InsertedEmail)
        }.apply()
    }

    private fun clearSharedpref(){
//        val sharedPreferences = getSharedPreferences("Email",Context.MODE_PRIVATE)
        getSharedPreferences("Email",Context.MODE_PRIVATE).edit().clear().apply()
    }
}