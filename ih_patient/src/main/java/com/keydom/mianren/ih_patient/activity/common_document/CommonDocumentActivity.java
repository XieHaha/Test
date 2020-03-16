package com.keydom.mianren.ih_patient.activity.common_document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.controller.CommonDocumentController;
import com.keydom.mianren.ih_patient.activity.common_document.view.CommonDocumentView;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

/**
 * created date: 2019/3/27 on 16:39
 * des:文书维护公共页面
 */
public class CommonDocumentActivity extends BaseControllerActivity<CommonDocumentController> implements CommonDocumentView {
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String TITLE = "title";
    private TextView mDecTv;
    private WebView mWebView;

    /**
     * 启动方法
     */
    public static void start(Context ctx, String type) {
        Intent i = new Intent(ctx, CommonDocumentActivity.class);
        i.putExtra(TYPE, type);
        ActivityUtils.startActivity(i);
    }

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
        mDecTv = findViewById(R.id.dec_tv);
        mWebView = findViewById(R.id.webView);
        RichText.initCacheDir(this);
        String code = getIntent().getStringExtra(TYPE);
        String url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);
        if(TextUtils.isEmpty(code)){
            mWebView.setVisibility(View.VISIBLE);
            mDecTv.setVisibility(View.GONE);
            setTitle(title);
            if(!url.startsWith("http")){
                loadUrl(Const.RELEASE_HOST + url);
            }else{
                loadUrl(url);
            }

        }else{
            switch (code){
                case CommonDocumentBean.CODE_1:
                    setTitle("在线问诊用户协议");
                    break;
                case CommonDocumentBean.CODE_2:
                    setTitle("护理服务用户协议");
                    break;
                case CommonDocumentBean.CODE_3:
                    setTitle("入院注意事项");
                    break;
                case CommonDocumentBean.CODE_4:
                    setTitle("体检中心介绍");
                    break;
                case CommonDocumentBean.CODE_5:
                    setTitle("体检须知");
                    break;
                case CommonDocumentBean.CODE_6:
                    setTitle("体检流程");
                    break;
                case CommonDocumentBean.CODE_8:
                    setTitle("注册用户协议");
                    break;
                case CommonDocumentBean.CODE_10:
                    setTitle("服务介绍");
                    break;
                case  CommonDocumentBean.CODE_13:
                    setTitle("保险条款用户协议");
                case CommonDocumentBean.CODE_14:
                    setTitle("保险条款用户协议");
                    break;
            }
            getController().getOfficialDispatchAllMsgByCode(code);
        }

    }

    @Override
    public void getData(CommonDocumentBean bean) {
        if(bean!=null){
            if (bean.getTitle() != null) {

            }
            String url = bean.getContent();
            if (StringUtils.isEmpty(bean.getContent())) {
                mWebView.setVisibility(View.VISIBLE);
                mDecTv.setVisibility(View.GONE);
                url = bean.getUrl().contains("http://") ? bean.getUrl() : "http://" + bean.getUrl();
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
            } else {
                mWebView.setVisibility(View.VISIBLE);
                mDecTv.setVisibility(View.GONE);

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
