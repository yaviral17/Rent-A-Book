package com.sudoavi.rentabook

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_take_phone_no.*

class TakePhoneNo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_phone_no)

        val animationDrawable = take_phone_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        usr_otp.visibility = View.GONE
        user_phone_txt.visibility = View.INVISIBLE
        submit_otp.visibility = View.GONE
        submit_btn.setOnClickListener {
            usr_otp.visibility = View.VISIBLE
            User_phone_number.visibility = View.INVISIBLE
            user_phone_txt.visibility = View.VISIBLE
            submit_otp.visibility = View.VISIBLE
             user_phone_txt.text = User_phone_number.text
        }

        user_phone_txt.setOnClickListener {
            user_phone_txt.visibility = View.INVISIBLE
            User_phone_number.visibility = View.VISIBLE
            usr_otp.visibility = View.GONE
            submit_otp.visibility = View.GONE

        }

        submit_otp.setOnClickListener {
            startActivity(Intent(this,name_dob::class.java))
        }

    }
}