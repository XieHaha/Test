package com.keydom.ih_common.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/5 下午9:44
 * 修改人：xusong
 * 修改时间：18/11/5 下午9:44
 */
public class MRadioButton extends RadioButton {

    public MRadioButton(Context context) {
        super(context);
    }

    public MRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            setChecked(isChecked()?false:true);
            return false;
        }
        return super.onTouchEvent(event);
    }
}
