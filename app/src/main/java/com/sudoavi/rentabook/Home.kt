package com.sudoavi.rentabook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

private val homeFrag = Freg_Home()
private val favFrag = Freg_Fav()
private val myCartFrag = Freg_My_Cart()
private val searchFrag = Freg_Search()
private val settingsFrag = Freg_Settings()


@Suppress("DEPRECATION")
class Home :  AppCompatActivity()  {
    
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    lateinit var Uemail:String
//    lateinit var  Upass :String
//    var User = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replacefragment(homeFrag)
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


    bottomNavBar.setOnItemSelectedListener {
        when (it.itemId){
            R.id.RAB_Home -> replacefragment(homeFrag)
            R.id.RAB_Cart -> replacefragment(myCartFrag)
            R.id.RAB_Fav -> replacefragment(favFrag)
            R.id.RAB_Settings -> replacefragment(settingsFrag)
            R.id.RAB_Search -> replacefragment(searchFrag)
        }
        true
    }


    Log_me_out.setOnClickListener {
        clearSharedpref()
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

    fun clearSharedpref(){
//        val sharedPreferences = getSharedPreferences("Email",Context.MODE_PRIVATE)
        getSharedPreferences("Email",Context.MODE_PRIVATE).edit().clear().apply()
    }


    private fun replacefragment (fragment: Fragment) {
        if (fragment != null){
            val transaction =  supportFragmentManager.beginTransaction()
            transaction.replace(R.id.home_page_fragment,fragment)
            transaction.commit()
        }

    }
}