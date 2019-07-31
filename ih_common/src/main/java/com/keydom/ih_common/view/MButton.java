package com.keydom.ih_common.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.keydom.ih_common.R;


/**
 * @Name：com.kentra.yxyz.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/5 上午11:38
 * 修改人：xusong
 * 修改时间：18/11/5 上午11:38
 */
public class MButton extends Button{

        private static String TAG = "MButton";
        /**
         * 按钮的背景色
         */
        private int backColor = 0;
        /**
         * 按钮被按下时的背景色
         */
        private int backColorPress = 0;
        /**
         * 按钮的背景图片
         */
        private Drawable backGroundDrawable = null;
        /**
         * 按钮被按下时显示的背景图片
         */
        private Drawable backGroundDrawablePress = null;
        /**
         * 按钮文字的颜色
         */
        private ColorStateList textColor = null;
        /**
         * 按钮被按下时文字的颜色
         */
        private ColorStateList textColorPress = null;
        private GradientDrawable gradientDrawable = null;
        /**
         * 是否设置圆角或者圆形等样式
         */
        private boolean fillet = false;
        /**
         * 标示onTouch方法的返回值，用来解决onClick和onTouch冲突问题
         */
        private boolean isCost = true;

        private boolean isCanClick=true;

        public MButton(Context context) {
            super(context, null);
        }

        public MButton(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.mbutton, defStyle, 0);
            if (a != null) {
                //设置背景色
                ColorStateList colorList = a.getColorStateList(R.styleable.mbutton_backColor);
                if (colorList != null) {
                    backColor = colorList.getColorForState(getDrawableState(), 0);
                    if (backColor != 0) {
                        setBackgroundColor(backColor);
                    }
                }
                ColorStateList colorListPress = a.getColorStateList(R.styleable.mbutton_backColorPress);
                if (colorListPress != null){
                    backColorPress = colorListPress.getColorForState(getDrawableState(), 0);
                }
                backGroundDrawable = a.getDrawable(R.styleable.mbutton_backGroundImage);
                if (backGroundDrawable != null){
                    setBackgroundDrawable(backGroundDrawable);
                }
                backGroundDrawablePress = a.getDrawable(R.styleable.mbutton_backGroundImagePress);
                textColor = a.getColorStateList(R.styleable.mbutton_textColor);
                if (textColor != null){
                    setTextColor(textColor);
                }
                textColorPress = a.getColorStateList(R.styleable.mbutton_textColorPress);
                fillet = a.getBoolean(R.styleable.mbutton_fillet, false);
                if (fillet){
                    getGradientDrawable();
                    if (backColor != 0) {
                        gradientDrawable.setColor(backColor);
                        setBackgroundDrawable(gradientDrawable);
                    }
                }
                float radius = a.getFloat(R.styleable.mbutton_radius, 0);
                if (fillet && radius != 0){
                    setRadius(radius);
                }
                int shape = a.getInteger(R.styleable.mbutton_shape, 0);
                if (fillet && shape != 0) {
                    setShape(shape);
                }
                a.recycle();
            }
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent event) {
                    return setTouchStyle(event.getAction());
                }
            });
        }

        /**
         * 根据按下或者抬起来改变背景和文字样式
         * @param state
         * @return isCost
         *  为解决onTouch和onClick冲突的问题
         *  根据事件分发机制，如果onTouch返回true，则不响应onClick事件
         *  因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
         */
        private boolean setTouchStyle(int state){
            if (state == MotionEvent.ACTION_DOWN) {
                if (backColorPress != 0) {
                    if (fillet){
                        gradientDrawable.setColor(backColorPress);
                        setBackgroundDrawable(gradientDrawable);
                    }else {
                        setBackgroundColor(backColorPress);
                    }
                }
                if (backGroundDrawablePress != null) {
                    setBackgroundDrawable(backGroundDrawablePress);
                }
                if (textColorPress != null) {
                    setTextColor(textColorPress);
                }
            }
            if (state == MotionEvent.ACTION_UP) {
                if (backColor != 0) {
                    if (fillet){
                        gradientDrawable.setColor(backColor);
                        setBackgroundDrawable(gradientDrawable);
                    }else {
                        setBackgroundColor(backColor);
                    }
                }
                if (backGroundDrawable != null) {
                    setBackgroundDrawable(backGroundDrawable);
                }
                if (textColor != null) {
                    setTextColor(textColor);
                }
            }
            return isCost;
        }

        /**
         * 重写setOnClickListener方法，解决onTouch和onClick冲突问题
         * @param l
         */
        @Override
        public void setOnClickListener(OnClickListener l) {
            super.setOnClickListener(l);
            isCost = false;
        }

        /**
         * 设置按钮的背景色
         * @param backColor
         */
        public void setBackColor(int backColor) {
            this.backColor = backColor;
            if (fillet){
                gradientDrawable.setColor(backColor);
                setBackgroundDrawable(gradientDrawable);
            }else {
                setBackgroundColor(backColor);
            }
        }

        /**
         * 设置按钮被按下时的背景色
         * @param backColorPress
         */
        public void setBackColorPress(int backColorPress) {
            this.backColorPress = backColorPress;
        }

        /**
         * 设置按钮的背景图片
         * @param backGroundDrawable
         */
        public void setBackGroundDrawable(Drawable backGroundDrawable) {
            this.backGroundDrawable = backGroundDrawable;
            setBackgroundDrawable(backGroundDrawable);
        }

        /**
         * 设置按钮被按下时的背景图片
         * @param backGroundDrawablePress
         */
        public void setBackGroundDrawablePress(Drawable backGroundDrawablePress) {
            this.backGroundDrawablePress = backGroundDrawablePress;
        }

        /**
         * 设置文字的颜色
         * @param textColor
         */
        public void setTextColor(int textColor) {
            if (textColor == 0) return;
            this.textColor = ColorStateList.valueOf(textColor);
            //此处应加super关键字，调用父类的setTextColor方法，否则会造成递归导致内存溢出
            super.setTextColor(this.textColor);
        }

        /**
         * 设置按钮被按下时文字的颜色
         * @param textColorPress
         */
        public void setTextColorPress(int textColorPress) {
            if (textColorPress == 0) return;
            this.textColorPress = ColorStateList.valueOf(textColorPress);
        }

        /**
         * 设置按钮是否设置圆角或者圆形等样式
         * @param fillet
         */
        public void setFillet(boolean fillet){
            this.fillet = fillet;
            getGradientDrawable();
        }

        /**
         * 设置圆角按钮的角度
         * @param radius
         */
        public void setRadius(float radius){
            if (!fillet) return;
            getGradientDrawable();
            gradientDrawable.setCornerRadius(radius);
            setBackgroundDrawable(gradientDrawable);
        }

        /**
         * 设置按钮的形状
         * @param shape
         */
        public void setShape(int shape){
            if (!fillet) return;
            getGradientDrawable();
            gradientDrawable.setShape(shape);
            setBackgroundDrawable(gradientDrawable);
        }

        private void getGradientDrawable() {
            if (gradientDrawable == null){
                gradientDrawable = new GradientDrawable();
            }
        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isCanClick){
            return super.onTouchEvent(event);
        }else{
            return false;
        }

    }


    public void startTimer(){
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isCanClick=false;
                MButton.this.setText("已发送(" + millisUntilFinished / 1000 + ")");
                MButton.this.setTextColor(getResources().getColor(R.color.fontColorDirection));
            }

            @Override
            public void onFinish() {
                isCanClick=true;
                MButton.this.setEnabled(true);
                MButton.this.setText("获取验证码");
                MButton.this.setTextColor(getResources().getColor(R.color.fontColorForeground));

            }
        }.start();



    }

}


