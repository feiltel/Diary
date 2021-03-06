package com.nutdiary.diary.login.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nutdiary.diary.baselibrary.base.BaseActivity;
import com.nutdiary.diary.baselibrary.base.TransparentActivity;
import com.nutdiary.diary.login.R;
import com.nutdiary.diary.baselibrary.base.UserData;

@Route(path = "/login/LoginMainActivity")
public class LoginMainActivity extends TransparentActivity implements LoginContract.LoginView {
    private LoginPresenter loginPresenter;
    private EditText userNameEt, passEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
        loginPresenter = new LoginPresenter(this, this);
        initView();
    }

    private void initView() {
        userNameEt = findViewById(R.id.phone_number_et);
        passEt = findViewById(R.id.ver_code_et);
        findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.userLogin1(userNameEt.getText().toString(), passEt.getText().toString());
            }
        });

    }


    @Override
    public void showToast(String msg) {
        showTip(msg);
    }

    @Override
    public void showLoadDialog() {
        myLoadDialog.show();
    }

    @Override
    public void hideLoadDialog() {
        myLoadDialog.hide();
    }

    @Override
    public void jumpMain(LoginResultBean bean) {
        setResult(Activity.RESULT_OK);
        UserData.saveUserUUID(this, bean.getData());
        super.finish();
    }
}
