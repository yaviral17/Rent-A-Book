package com.sudoavi.rentabook

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_select_pass.*
import kotlinx.android.synthetic.main.activity_select_pass.submit_btn
import kotlinx.android.synthetic.main.activity_take_phone_no.*

class selectpass : AppCompatActivity() {
    private lateinit var Uname:String
    private lateinit var Uemail:String
    private lateinit var Udob:String
    private lateinit var Upass:String
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    companion object{
        var user = Firebase.auth.currentUser
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pass)

        val animationDrawable = sel_pass_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()


        auth = FirebaseAuth.getInstance()
        date_picker_s.visibility = View.GONE
        done_select_date_s.visibility = View.GONE

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

        select_date_s.setOnClickListener {
            date_picker_s.visibility = View.VISIBLE
            done_select_date_s.visibility = View.VISIBLE
            select_date_s.visibility = View.GONE
        }

        done_select_date_s.setOnClickListener {
            val day = date_picker_s.dayOfMonth
            val month = date_picker_s.month+1
            val year = date_picker_s.year

            Udob = if (day<10){
                if (month<10){
                    "0$day-0$month-$year"
                }else{
                    "0$day-$month-$year"
                }
            }else{
                if (month<10){
                    "$day-0$month-$year"
                } else{
                    "$day-$month-$year"
                }
            }
            user_dob.text  = Udob
            done_select_date_s.visibility = View.GONE
            date_picker_s.visibility = View.GONE
            select_date_s.visibility = View.VISIBLE
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

        submit_btn.setOnClickListener {
            Uemail = UsrEmail.text.toString()
            Upass = pass1.text.toString().trim()
            val Upass_ = pass2.text.toString().trim()
            Uname = Usrname.text.toString().trim()
            if (Upass_  == Upass) {
                if (pass1.text.length < 8){
                    Toast.makeText(this, "Password is too short !!", Toast.LENGTH_SHORT).show()
                }else{


                    if (checkValues(Uemail,Upass,Udob,Uname)){
                        auth.createUserWithEmailAndPassword(Uemail,Upass)
                            .addOnSuccessListener {
                                Toast.makeText(this, "user created", Toast.LENGTH_SHORT).show()
                                user = FirebaseAuth.getInstance().currentUser
                                val userData = hashMapOf(
                                    "Email" to Uemail,
                                    "Name" to Uname,
                                    "DOB" to Udob,
                                    "Phone" to User_phone_number
                                )
//                                Toast.makeText(this, "${user?.email.toString()} ${user?.displayName.toString()} ${user?.isEmailVerified.toString()} ${user?.phoneNumber.toString()}", Toast.LENGTH_SHORT).show()
                                db.collection("USERS").document(Uemail).set(userData)
                                    .addOnSuccessListener {
//                                        Toast.makeText(this, "User Details Added!!", Toast.LENGTH_SHORT).show()
                                        user!!.sendEmailVerification()
                                            .addOnCompleteListener {
                                                Toast.makeText(this, "Email Sent !!", Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this, "Verification Email not sent!!", Toast.LENGTH_SHORT).show()
                                            }
                                        startActivity(Intent(this,LoginPage::class.java))
                                    }
                            }
                            .addOnFailureListener{
                                Toast.makeText(this, "User Already exist!!", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }else{
                Toast.makeText(this, "Confirm Password didn't match!!", Toast.LENGTH_SHORT).show()
            }
//            startActivity(Intent(this,Home::class.java))
        }

    }
    private fun checkValues(email:String,pass1:String,dob:String,name:String):Boolean{
        return email.isNotEmpty() && pass1.isNotEmpty() && dob.isNotEmpty() && name.isNotEmpty()
    }
}