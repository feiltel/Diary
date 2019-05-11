package com.nutdiary.diary.baselibrary.component;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class MyLoadDialog {
    private KProgressHUD hud;

    public MyLoadDialog(Context context) {
        this.context = context;
    }

    private Context context;


    public void show() {
        if (context != null) {
            if (hud == null) {
                hud = KProgressHUD.create(context)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            }
            if (hud!=null){
                hud.show();
            }

        }

    }

    public void hide() {
        if (hud != null) {
            hud.dismiss();
        }
    }

}
