package com.keydom.ih_common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.keydom.ih_common.utils.CommonUtils;

import java.util.regex.Pattern;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/5 上午9:08
 * 修改人：xusong
 * 修改时间：18/11/5 上午9:08
 */
public class ClearEditText extends EditText implements TextWatcher,
        View.OnFocusChangeListener {

    /**
     * 左右两侧图片资源
     */
    private Drawable left, right;
    /**
     * 是否获取焦点，默认没有焦点
     */
    private boolean hasFocus = false;
    /**
     * 手指抬起时的X坐标
     */
    private int xUp = 0;

    private boolean isEmpty=true;
    private Context context;
    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        this.context=context;
        addInterceptor(context,attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        initWedgits();
        addInterceptor(context,attrs);
    }

    /**
     * 初始化各组件
     * @param
     */
    private void initWedgits() {
        try {
            // 获取drawableLeft图片，如果在布局文件中没有定义drawableLeft属性，则此值为空
            left = getCompoundDrawables()[0];
            // 获取drawableRight图片，如果在布局文件中没有定义drawableRight属性，则此值为空
            right = getCompoundDrawables()[2];
            initDatas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        try {
            // 第一次显示，隐藏删除图标
            setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            addListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加事件监听
     */
    private void addListeners() {
        try {
            setOnFocusChangeListener(this);
            addTextChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (hasFocus) {
            if (TextUtils.isEmpty(s)) {
                // 如果为空，则不显示删除图标
                setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                isEmpty=true;
            } else {
                isEmpty=false;
                // 如果非空，则要显示删除图标
                if (null == right) {
                    right = getCompoundDrawables()[2];

                }
                setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 获取点击时手指抬起的X坐标
                    xUp = (int) event.getX();
                    // 当点击的坐标到当前输入框右侧的距离小于等于getCompoundPaddingRight()的距离时，则认为是点击了删除图标
                    // getCompoundPaddingRight()的说明：Returns the right padding of the view, plus space for the right Drawable if any.
                    if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
                        if (!TextUtils.isEmpty(getText().toString())) {
                            setText("");
                        }
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
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            this.hasFocus = hasFocus;
            if(hasFocus&&!isEmpty){
                setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
            }else{
                setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void addInterceptor(Context context, AttributeSet attrs){

        String namespace = "http://schemas.android.com/apk/res/android";
        int maxLength = attrs.getAttributeIntValue(namespace, "maxLength", -1);
        //如果设置了最大长度，给出相应的处理
        if (maxLength > -1) {
            setFilters(new InputFilter[]{new ClearEditText.MyLengthFilter(maxLength,context),emojiFilter});
        }else {
            setFilters(new InputFilter[]{emojiFilter});
        }
        /*InputFilter[] emojiFilters = {emojiFilter};

        this.setFilters(emojiFilters);*/
    }

    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if(CommonUtils.containsEmoji(source.toString())){
                Toast.makeText(context, "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
          /*  Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                Toast.makeText(context, "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;*/
        }
    };

    private class MyLengthFilter implements InputFilter {

        private final int mMax;
        private Context context;

        public MyLengthFilter(int max, Context context) {
            mMax = max;
            this.context = context;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                //这里，用来给用户提示
                Toast.makeText(context, "字数不能超过" + mMax+"位", Toast.LENGTH_SHORT).show();
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMax;
        }
    }
}
