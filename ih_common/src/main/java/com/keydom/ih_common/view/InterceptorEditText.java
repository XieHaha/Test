package com.keydom.ih_common.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.keydom.ih_common.utils.CommonUtils;
import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterceptorEditText extends EditText implements TextWatcher {

    private Context context;

    public InterceptorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        addInterceptor(context, attrs);
        this.context = context;
        addInterceptor(context, attrs);

    }

    public InterceptorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        addInterceptor(context, attrs);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Logger.e("beforeTextChanged:" + charSequence.toString() + "     start=" + i + "     count=" + i1 + "     after=" + i2);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Logger.e("onTextChanged:" + charSequence.toString() + "     start=" + i + "     before=" + i1 + "     count=" + i2);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        Logger.e("afterTextChanged:" + editable.toString());

    }
//
//    @Override
//    public Editable getText() {
//        Editable editable = super.getText();
//        String value = editable.toString();
//        if (value != null || !"".equals(value)) {
//            value=value.trim();
//            editable.clear();
//            editable.append(value);
//        }
//        return super.getText();
//    }

    private void addInterceptor(Context context, AttributeSet attrs) {


        String namespace = "http://schemas.android.com/apk/res/android";
        boolean isSingleLine = attrs.getAttributeBooleanValue(namespace, "singleLine", true);
        if (isSingleLine)
            this.setSingleLine(true);
        int maxLength = attrs.getAttributeIntValue(namespace, "maxLength", -1);
        //如果设置了最大长度，给出相应的处理
        if (maxLength > -1) {
            setFilters(new InputFilter[]{new MyLengthFilter(maxLength, context), emojiFilter});
        } else {
            setFilters(new InputFilter[]{emojiFilter});
        }
        /*InputFilter[] emojiFilters = {emojiFilter};

        this.setFilters(emojiFilters);*/
    }

    InputFilter emojiFilter = new InputFilter() {
        /*Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);*/

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (CommonUtils.containsEmoji(source.toString())) {
                Toast.makeText(context, "不支持输入颜文字或表情", Toast.LENGTH_SHORT).show();
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

    InputFilter yanWordFilter = new InputFilter() {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher = pattern.matcher(charSequence);
            if (!matcher.find()) {
                return null;
            } else {
                Toast.makeText(context, "不支持输入颜文字或表情", Toast.LENGTH_SHORT).show();
                return "";
            }
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
                Toast.makeText(context, "字数不能超过" + mMax + "位", Toast.LENGTH_SHORT).show();
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
