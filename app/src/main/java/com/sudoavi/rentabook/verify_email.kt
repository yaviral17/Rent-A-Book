package com.sudoavi.rentabook

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_take_phone_no.*
import kotlinx.android.synthetic.main.activity_verify_email.*
import kotlinx.android.synthetic.main.activity_verify_email.User_phone_number
import kotlinx.android.synthetic.main.activity_verify_email.submit_btn
import kotlinx.android.synthetic.main.activity_verify_email.submit_otp
import kotlinx.android.synthetic.main.activity_verify_email.user_phone_txt
import kotlinx.android.synthetic.main.activity_verify_email.usr_otp

class verify_email : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)

        val animationDrawable = verify_email_bg.background as AnimationDrawable
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
            startActivity(Intent(this,TakePhoneNo::class.java))
        }
    }
}