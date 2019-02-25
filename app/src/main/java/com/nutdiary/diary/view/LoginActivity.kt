package com.nutdiary.diary.view

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import com.nutdiary.diary.R
import com.nutdiary.diary.contract.LoginContract
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),LoginContract.LoginView {
    override fun showToast(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        click_btn.setOnClickListener { userLogin() }
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

    fun userLogin() {

    }

}
