package com.keydom.mianren.ih_doctor.activity.common_document;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.common_document.controller.CommonDocumentController;
import com.keydom.mianren.ih_doctor.activity.common_document.view.CommonDocumentView;
import com.keydom.mianren.ih_doctor.bean.AgreementBean;

/**
 * created date: 2019/3/27 on 16:39
 * des:文书维护公共页面
 */
public class CommonDocumentActivity extends BaseControllerActivity<CommonDocumentController> implements CommonDocumentView {
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    private WebView mWebView;

    /**
     * 启动方法
     */
    public static void start(Context ctx, String title,String url) {
        Intent i = new Intent(ctx, CommonDocumentActivity.class);
        i.putExtra(TITLE, title);
        i.putExtra(URL, url);
        ActivityUtils.startActivity(i);
    }

    /**
     * 启动方法
     */
    public static void start(Context ctx, String type) {
        Intent i = new Intent(ctx, CommonDocumentActivity.class);
        i.putExtra(TYPE, type);
        ActivityUtils.startActivity(i);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_document;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        String code = getIntent().getStringExtra(TYPE);
        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);
        if (TextUtils.isEmpty(code)) {
            mWebView.setVisibility(View.VISIBLE);
            setTitle(title);
            if (!url.startsWith("http")) {
                loadUrl(Const.RELEASE_HOST + url);
            } else {
                loadUrl(url);
            }

        } else {
            switch (code) {
                case AgreementBean.CODE_16:
                    setTitle("数字证书服务协议");
                    break;
                default:
                    break;
            }
            getController().getOfficialDispatchAllMsgByCode(code);
        }
    }


    public void loadUrl(String url){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setDomStorageEnabled(true);//webview不能完全加载网页 或者不能很好的支持懒加载是添加此设置
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                //hitTestResult==null解决重定向问题
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        mWebView.loadUrl(url);
    }

    @Override
    public void getData(AgreementBean bean) {
        if (bean != null) {
            String url = bean.getContent();
            if (StringUtils.isEmpty(bean.getContent())) {
                mWebView.setVisibility(View.VISIBLE);
                url = bean.getLinkUrl().contains("http://") ? bean.getLinkUrl() :
                        "http://" + bean.getLinkUrl();
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setSupportZoom(true);
                mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                mWebView.getSettings().setDomStorageEnabled(true);//webview不能完全加载网页
                // 或者不能很好的支持懒加载是添加此设置
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        WebView.HitTestResult hitTestResult = view.getHitTestResult();
                        //hitTestResult==null解决重定向问题
                        if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                            view.loadUrl(url);
                            return true;
                        }
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                });
                mWebView.loadUrl(url);
            } else {
                mWebView.setVisibility(View.VISIBLE);

                StringBuilder sb = new StringBuilder();
                sb.append(getHtmlData(bean.getContent()));
                mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
            }
        }
    }


    private String getHtmlData(String bodyHTML) {
        String css = "<style type=\"text/css\"> img {"
                + "width:100%;" +//限定图片宽度填充屏幕
                "height:auto;" +//限定图片高度自动
                "}" +
                "body {" +
                "word-wrap:break-word;" +//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                "}" +
                "</style>";

        String html = "<html><header>" + css + "</header>" + bodyHTML + "</html>";

        return html;
    }

}
