package com.sudoavi.rentabook

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_take_phone_no.*
import kotlinx.android.synthetic.main.activity_verify_email.*
import kotlinx.android.synthetic.main.activity_verify_email.text_hint1


class verify_email : AppCompatActivity() {

    var Usr_email = selectpass.user?.email.toString()
   private lateinit var firebaseAuth : FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)

        val animationDrawable = verify_email_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

//        user = Firebase.auth.currentUser

        text_hint1.text = "${text_hint1.text} $Usr_email"
        sendVerificationMain()

        Resend_otp.setOnClickListener {
            sendVerificationMain()
        }

        submit_otp.setOnClickListener {
            startActivity(Intent(this,TakePhoneNo::class.java))
        }


    }



    fun sendVerificationMain(){
        selectpass.user!!.sendEmailVerification()
            .addOnCompleteListener {
                Toast.makeText(this, "Email Sent!!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Email Not Sent!!", Toast.LENGTH_SHORT).show()
            }
    }




}