package com.nutdiary.diary.view

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import com.nutdiary.diary.R
import com.nutdiary.diary.component.MyToast
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        println(getInfo())
        click_btn.setOnClickListener { getInfo() }
        get_ver_code_btn.setOnClickListener { getVerCode() }
    }

    fun getVerCode() {
        countDownTimer.start()
    }

     val countDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            get_ver_code_btn.isEnabled = false
            val value = (millisUntilFinished / 1000).toInt().toString()
            get_ver_code_btn.text = value
        }

        override fun onFinish() {
            get_ver_code_btn.isEnabled = true
            get_ver_code_btn.text = "发送验证码"
        }
    }

    fun getInfo(): String {
        MyToast.showToast("1213")

        return "232"
    }

}
