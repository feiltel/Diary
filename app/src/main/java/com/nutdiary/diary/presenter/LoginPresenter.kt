package com.nutdiary.diary.presenter

import com.nutdiary.diary.base.BasePresenter
import com.nutdiary.diary.base.ResultBean
import com.nutdiary.diary.bean.LoginResultBean
import com.nutdiary.diary.contract.LoginContract
import com.nutdiary.diary.localData.UserData
import com.nutdiary.diary.model.LoginModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import io.reactivex.schedulers.Schedulers

class LoginPresenter(private val loginView: LoginContract.LoginView, provider: LifecycleProvider<ActivityEvent>) : BasePresenter(provider) {
    private val loginModel: LoginContract.LoginModel

    init {
        loginModel = LoginModel()
    }

    fun userLogin(phone: String, pass: String) {
        loginModel.userLogin(phone, pass)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(provider.bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(object : DefaultObserver<LoginResultBean>() {  // 订阅
                    override fun onNext(resultBean: LoginResultBean) {
                        if (resultBean.code == 0) {
                            UserData.saveUserUUID(resultBean.data)
                            loginView.jumpMain()
                        }
                        loginView.showToast(resultBean.msg)
                    }

                    override fun onError(e: Throwable) {
                        loginView.showToast(e.toString())
                    }

                    override fun onComplete() {

                    }
                })
    }
}
