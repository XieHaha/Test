package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.controller.PhysicalExaDetailController;
import com.keydom.ih_patient.fragment.view.PhysicalExaDetailView;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 体检套餐详情页面
 */
public class PhysicalExaDetailFragment extends BaseControllerFragment<PhysicalExaDetailController> implements PhysicalExaDetailView {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_physical_exa_detail_layout;
    }

    private TextView physical_exa_detail_rich_text;
    private WebView mWebview;

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        physical_exa_detail_rich_text=view.findViewById(R.id.physical_exa_detail_rich_text);
        mWebview = view.findViewById(R.id.mWebview);
        mWebview.setHorizontalScrollBarEnabled(false);
        mWebview.setVerticalScrollBarEnabled(false);
        mWebview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(true);


                return false;
            }
        });
        getController().getPhysicalExaDetail(Global.getSelectedPhysicalExa().getId());
        RichText.initCacheDir(getContext());
    }

    @Override
    public void FillPhysicalDetail(String data) {
        /*RichText.from(data).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(physical_exa_detail_rich_text);*/
        StringBuilder sb = new StringBuilder();
        sb.append(getHtmlData(data));
        mWebview.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String css = "<style type=\"text/css\"> img {"
                + "width:100%;" +//限定图片宽度填充屏幕
                "height:auto;" +//限定图片高度自动
                "}" +
                "body {" +
                "margin-right:15px;" +//限定网页中的文字右边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-left:15px;" +//限定网页中的文字左边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-bottom:15px;" +//限定网页中的文字上边距为15px(可根据实际需要进行行管屏幕适配操作)
                "margin-:15px;"+
                "word-wrap:break-word;" +//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                "}" +
                "</style>";

        String html = "<html><header>" + css + "</header>" + bodyHTML + "</html>";

        return html;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
