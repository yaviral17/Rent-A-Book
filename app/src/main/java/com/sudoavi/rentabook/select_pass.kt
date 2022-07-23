package com.sudoavi.rentabook

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_name_dob.*
import kotlinx.android.synthetic.main.activity_select_pass.*

class select_pass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pass)

        val animationDrawable = sel_pass_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        var hide1 = false
        var hide2 = false

        eye_pass1.setOnClickListener {
            if (hide1){
                pass1.transformationMethod = PasswordTransformationMethod.getInstance()
                hide1 = false
            }else{
                pass1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                hide1 = true
            }

        }

        eye_pass2.setOnClickListener {
            if (hide2){
                pass2.transformationMethod = PasswordTransformationMethod.getInstance()
                hide2 = false
            }else{
                pass2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                hide2 = true
            }
        }

        confirm_password.setOnClickListener {
            startActivity(Intent(this,Home::class.java))
        }
    }
}