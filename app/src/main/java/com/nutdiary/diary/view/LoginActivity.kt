package com.nutdiary.diary.view

import android.content.Intent
import android.os.Bundle
import com.nutdiary.diary.R
import com.nutdiary.diary.base.BaseActivity
import com.nutdiary.diary.component.MyToast
import com.nutdiary.diary.contract.LoginContract
import com.nutdiary.diary.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    private var loginPresenter: LoginPresenter = LoginPresenter(this, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        click_btn.setOnClickListener { userLogin() }
    }

    fun userLogin() {
        loginPresenter.userLogin(phone_number_et.text.toString(), ver_code_et.text.toString())
    }

    override fun jumpMain() {
        setResult(0)
        super.finish()
    }


    override fun showToast(msg: String?) {
        MyToast.showToast(msg)
    }

    override fun showLoadDialog() {
        myLoadDialog.show()
    }

    override fun hideLoadDialog() {
        myLoadDialog.hide()
    }
}
