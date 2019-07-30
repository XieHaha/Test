package com.keydom.ih_patient.activity.common_document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.common_document.controller.CommonDocumentController;
import com.keydom.ih_patient.activity.common_document.view.CommonDocumentView;
import com.keydom.ih_patient.bean.CommonDocumentBean;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

/**
 * created date: 2019/3/27 on 16:39
 * des:文书维护公共页面
 */
public class CommonDocumentActivity extends BaseControllerActivity<CommonDocumentController> implements CommonDocumentView {
    public static final String TYPE = "type";
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
        }
        getController().getOfficialDispatchAllMsgByCode(code);
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
                RichText.from(url).bind(this)
                        .showBorder(false)
                        .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                        .into(mDecTv);
            }
        }

    }
}
