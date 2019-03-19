package com.nutdiary.diary.baselibrary.utils;


import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputUtil {

    public static void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            editText.requestFocus();
            imm.showSoftInput(editText, 0);
        }
    }
}
