package com.sudoavi.rentabook

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_name_dob.*

class name_dob : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_dob)

        val animationDrawable = name_dob_bg.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(1)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        date_picker_s.visibility = View.GONE
        done_select_date_s.visibility = View.GONE


        select_date_s.setOnClickListener {
            select_date_s.visibility = View.GONE
            date_picker_s.visibility = View.VISIBLE
            done_select_date_s.visibility = View.VISIBLE


        }

        done_select_date_s.setOnClickListener {

            val day = date_picker_s.dayOfMonth
            val month = date_picker_s.month+1
            val year = date_picker_s.year

            if (day<10){
                if (month<10) {
                    user_dob.text = "0$day-0$month-$year"
                }
                else{
                    user_dob.text = "0$day-$month-$year"
                }
            }else{
                if (month<10){
                    user_dob.text = "$day-0$month-$year"
                }
                else{
                    user_dob.text = "$day $month $year"
                }
            }


            select_date_s.visibility = View.VISIBLE
            date_picker_s.visibility = View.GONE
            done_select_date_s.visibility = View.GONE

        }
        submit_name_dob.setOnClickListener {
            startActivity(Intent(this,selectpass::class.java))
        }


        }
}