package com.keydom.ih_common;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @date 20/5/29 15:56
 * @des
 */
public abstract class AbsTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
