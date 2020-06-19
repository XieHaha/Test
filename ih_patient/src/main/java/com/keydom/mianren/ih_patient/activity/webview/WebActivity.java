package com.keydom.mianren.ih_patient.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.webview.controller.WebPageController;
import com.keydom.mianren.ih_patient.activity.webview.view.WebPageView;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/6/19 11:36
 * @des
 */
public class WebActivity extends BaseControllerActivity<WebPageController> implements WebPageView {
    @BindView(R.id.web_view)
    WebView webView;

    /**
     * 网页加载错误
     */
    private boolean isError;
    private String url;

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Const.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String type = getIntent().getStringExtra(Const.TYPE);
        switch (type) {
            case CommonDocumentBean.CODE_15:
                setTitle("团体体检");
                url = "http://www.health580.cn/reservation/public/wap/group_line/logingroup" +
                        "?center_code=610008";
                break;
            case CommonDocumentBean.CODE_16:
                setTitle("体检报表");
                url = "http://api.weixin.zkpacs.com.cn/report/jsp/webVersionJsp/report/reportBind" +
                        ".html?center_code=610008";
                break;
            case CommonDocumentBean.CODE_17:
                setTitle("个体体检");
                url = "http://www.health580.cn/reservation/public/wap/norder_line" +
                        "/nallPackage?center_code=610008";
                break;
            case CommonDocumentBean.CODE_18:
                setTitle("个性化体检");
                url = "http://center.zkpacs.com.cn/jsp/wxNewServegroup/nquesProcess/selectInfo" +
                        ".html";
                break;
            default:
                break;
        }

        //        webView.setOnKeyListener(new View.OnKeyListener() {
        //            @Override
        //            public boolean onKey(View v, int keyCode, KeyEvent event) {
        //                if (event.getAction() == KeyEvent.ACTION_DOWN) {
        //                    //按返回键操作并且能回退网页
        //                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
        //                        //后退
        //                        webView.goBack();
        //                        return true;
        //                    }
        //                }
        //                return false;
        //            }
        //        });
        initWebViewSetting();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSetting() {
        //指定的垂直滚动条有叠加样式
        webView.setVerticalScrollbarOverlay(true);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        //设置页面默认缩放密度
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        //设置默认的文本编码名称，以便在解码html页面时使用
        settings.setDefaultTextEncodingName("UTF-8");
        //启动或禁用WebView内的内容URL访问
        settings.setAllowContentAccess(true);
        //设置是否应该启用应用程序缓存api
        settings.setAppCacheEnabled(false);
        //设置WebView是否应该使用其内置的缩放机制
        settings.setBuiltInZoomControls(false);
        //设置WebView是否应该支持viewport
        settings.setUseWideViewPort(true);
        //不管WebView是否在概述模式中载入页面，将内容放大适合屏幕宽度
        settings.setLoadWithOverviewMode(true);
        //重写缓存的使用方式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //告知js自动打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置WebView是否应该载入图像资源
        settings.setLoadsImagesAutomatically(true);
        //启用或禁用WebView内的文件访问
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(0);
        }
        initClient();

        pageLoading();
        webView.loadUrl(url);
    }

    private void initClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!isError && webView != null) {
                    webView.setVisibility(View.VISIBLE);
                    pageLoadingSuccess();
                }
            }

            // 新版本，只会在Android6及以上调用
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                isError = true;
                loadStatus();
            }

            /**
             * 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
             *
             */
            // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                isError = true;
                loadStatus();
            }
        };

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 加载状态
     */
    private void loadStatus() {
        webView.setVisibility(View.GONE);
        pageLoadingFail();
    }
}
