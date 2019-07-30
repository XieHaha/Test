package com.keydom.ih_common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.keydom.ih_common.R;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/5 上午9:08
 * 修改人：xusong
 * 修改时间：18/11/5 上午9:08
 */
public class PasswordEditText extends EditText implements TextWatcher{

    /**
     * 左右两侧图片资源
     */
    private Drawable hidePassword, showPassword;

    /**
     * 手指抬起时的X坐标
     */
    private int xUp = 0;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWedgits();
    }

    /**
     * 初始化各组件
     * @param
     */
    private void initWedgits() {
        try {
            hidePassword = getResources().getDrawable(R.mipmap.hide_password);
            showPassword = getResources().getDrawable(R.mipmap.show_password);
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setCompoundDrawablesWithIntrinsicBounds(null, null, showPassword, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 获取点击时手指抬起的X坐标
                    xUp = (int) event.getX();
                    if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
                            setHideAndShow();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
    private void setHideAndShow(){
        int pos = getSelectionStart();
        if(getInputType()!= (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)){//隐藏密码
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setCompoundDrawablesWithIntrinsicBounds(null, null, showPassword, null);
        }else{//显示密码
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            setCompoundDrawablesWithIntrinsicBounds(null, null, hidePassword, null);
        }
        setSelection(pos);

    }

}
