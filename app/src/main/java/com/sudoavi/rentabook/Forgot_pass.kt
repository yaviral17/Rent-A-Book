package com.sudoavi.rentabook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class Forgot_pass : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        auth = FirebaseAuth.getInstance()

        button2.setOnClickListener {
            val Umail = USER_mail.text.toString().trim()
            if (Umail.length > 0){
                auth.sendPasswordResetEmail(Umail).addOnSuccessListener {
                    Toast.makeText(this, "Email Sent !!", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show()
                    }
            }
        }




    }
}