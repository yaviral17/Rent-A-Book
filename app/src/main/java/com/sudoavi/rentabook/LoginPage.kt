package com.sudoavi.rentabook

import android.R.attr.password
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*


class LoginPage : AppCompatActivity() {
    lateinit var Uemail : String
    lateinit var Upass : String
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val animationDrawable = Login_page_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

         var hide: Boolean = false

        CrAc.setOnClickListener {
            startActivity(Intent(this,verify_email::class.java))
        }

        eye_btn.setOnClickListener {

            if (hide){
                Usr_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                hide = false
            }else{
                Usr_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                hide = true
            }
        //            Usr_pass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        login_btn.setOnClickListener{
            Upass = Usr_pass.text.toString().trim()
            Uemail = Usr_nm.text.toString().trim()

            if (check_values(Upass,Uemail)) {

                auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(Uemail, Upass)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,Home::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Some thing went wrong !!", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(this, "Enter the values !!", Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun check_values(pass :String , Email: String): Boolean {
        return pass.isNotEmpty() && Email.isNotEmpty()
    }
}