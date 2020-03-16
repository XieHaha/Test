package com.keydom.mianren.ih_doctor.activity.common_document;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.common_document.controller.CommonDocumentController;
import com.keydom.mianren.ih_doctor.activity.common_document.view.CommonDocumentView;

/**
 * created date: 2019/3/27 on 16:39
 * des:文书维护公共页面
 */
public class CommonDocumentActivity extends BaseControllerActivity<CommonDocumentController> implements CommonDocumentView {
    public static final String URL = "url";
    public static final String TITLE = "title";
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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_document;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);
        setTitle(title);
        loadUrl(url);
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



}
