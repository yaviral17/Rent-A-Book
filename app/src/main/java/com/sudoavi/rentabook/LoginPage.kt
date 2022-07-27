package com.sudoavi.rentabook

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*


@Suppress("DEPRECATION")
class LoginPage : AppCompatActivity() {
    lateinit var Uemail : String
    lateinit var Upass : String
    private lateinit var auth : FirebaseAuth
   var user =  FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val animationDrawable = Login_page_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        loading_login_pg.visibility = View.GONE
        Login_failed_animation.visibility = View.GONE
        login_success_animation.visibility = View.GONE
        loading_status.visibility = View.GONE
         var hide = false

        CrAc.setOnClickListener {
            startActivity(Intent(this,selectpass::class.java))
        }

        forget_pass.setOnClickListener {
            startActivity(Intent(this,Forgot_pass::class.java))
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
            animation_lg.visibility = View.GONE
            loading_login_pg.visibility = View.VISIBLE
            loading_status.visibility = View.VISIBLE
            Upass = Usr_pass.text.toString().trim()
            Uemail = Usr_nm.text.toString().trim()

            if (check_values(Upass,Uemail)) {
                auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(Uemail, Upass)
                    .addOnSuccessListener {
//                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        user = FirebaseAuth.getInstance().currentUser
                        if (user!!.isEmailVerified){
                            loading_login_pg.visibility = View.GONE
                            loading_status.visibility = View.GONE
                            login_success_animation.visibility = View.VISIBLE
                            login_success_animation.playAnimation()
                            Handler().postDelayed({
                                login_success_animation.visibility = View.GONE
                                animation_lg.visibility = View.VISIBLE
                                startActivity(Intent(this,Home::class.java).putExtra("email",Uemail))
                                finish()
                            },2000)

                        }
                        else{
                            user!!.sendEmailVerification().addOnCompleteListener {
                                loading_login_pg.visibility = View.GONE
                                loading_status.visibility = View.GONE
                                Login_failed_animation.visibility = View.VISIBLE
                                Login_failed_animation.playAnimation()
                                Toast.makeText(this, "Verification mail sent to your mail", Toast.LENGTH_SHORT).show()
                                Handler().postDelayed({
                                    Login_failed_animation.visibility = View.GONE
                                    animation_lg.visibility = View.VISIBLE
                                },2000)

                            }
                                .addOnFailureListener {
                                    loading_login_pg.visibility = View.GONE
                                    loading_status.visibility = View.GONE
                                    Login_failed_animation.visibility = View.VISIBLE
                                    Login_failed_animation.playAnimation()
                                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                                    Handler().postDelayed({
                                        Login_failed_animation.visibility = View.GONE
                                        animation_lg.visibility = View.VISIBLE
                                    },2000)
                                    }
                        }
                    }
                    .addOnFailureListener {
                        loading_login_pg.visibility = View.GONE
                        loading_status.visibility = View.GONE
                        Login_failed_animation.visibility = View.VISIBLE
                        Login_failed_animation.playAnimation()
                        Toast.makeText(this, "Some thing went wrong !!", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            Login_failed_animation.visibility = View.GONE
                            animation_lg.visibility = View.VISIBLE
                        },2000)
                    }
            }
            else{
                Login_failed_animation.visibility = View.VISIBLE
                Login_failed_animation.playAnimation()
                Toast.makeText(this, "Enter the values !!", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    Login_failed_animation.visibility = View.GONE
                    loading_login_pg.visibility = View.GONE
                    loading_status.visibility = View.GONE
                    animation_lg.visibility = View.VISIBLE
                },2000)
            }

        }


    }

    fun check_values(pass :String , Email: String): Boolean {
        return pass.isNotEmpty() && Email.isNotEmpty()
    }
}