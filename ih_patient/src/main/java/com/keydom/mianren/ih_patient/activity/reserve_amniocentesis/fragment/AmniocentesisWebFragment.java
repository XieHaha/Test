package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisWebController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisWebView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/11 14:27
 * @des 羊水穿刺预约web
 */
public class AmniocentesisWebFragment extends BaseControllerFragment<AmniocentesisWebController> implements AmniocentesisWebView {
    @BindView(R.id.amniocentesis_web_view)
    WebView amniocentesisWebView;
    @BindView(R.id.amniocentesis_web_next_tv)
    TextView amniocentesisWebNextTv;
    @BindView(R.id.amniocentesis_web_agree_layout)
    LinearLayout amniocentesisWebAgreeLayout;
    @BindView(R.id.amniocentesis_web_disagree_layout)
    LinearLayout amniocentesisWebDisagreeLayout;
    @BindView(R.id.amniocentesis_web_protocol_layout)
    LinearLayout amniocentesisWebProtocolLayout;
    @BindView(R.id.amniocentesis_web_agree_protocol_layout)
    LinearLayout amniocentesisWebAgreeProtocolLayout;
    @BindView(R.id.amniocentesis_web_notice_protocol_layout)
    LinearLayout amniocentesisWebNoticeProtocolLayout;
    @BindView(R.id.amniocentesis_web_agree_protocol_layout1)
    LinearLayout amniocentesisWebAgreeProtocolLayout1;
    @BindView(R.id.amniocentesis_web_notice_layout)
    LinearLayout amniocentesisWebNoticeLayout;

    private AmniocentesisReserveBean reserveBean;
    private AmniocentesisProtocol protocol;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_web;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initPage();
            amniocentesisWebView.loadUrl(protocol.getUrl());
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        amniocentesisWebNextTv.setOnClickListener(getController());
        amniocentesisWebAgreeLayout.setOnClickListener(getController());
        amniocentesisWebDisagreeLayout.setOnClickListener(getController());
        amniocentesisWebAgreeProtocolLayout.setOnClickListener(getController());
        amniocentesisWebAgreeProtocolLayout1.setOnClickListener(getController());
        amniocentesisWebNoticeLayout.setOnClickListener(getController());
        initPage();

        initWebViewSetting();
    }

    public void setProtocol(AmniocentesisProtocol protocol) {
        this.protocol = protocol;
    }

    public void setReserveBean(AmniocentesisReserveBean reserveBean) {
        this.reserveBean = reserveBean;
    }

    private void initPage() {
        switch (protocol) {
            case AMNIOCENTESIS_WEB_RESERVE:
                amniocentesisWebProtocolLayout.setVisibility(View.VISIBLE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.GONE);
                break;
            case AMNIOCENTESIS_AGREE_PROTOCOL:
                amniocentesisWebProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.VISIBLE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.GONE);
                break;
            case AMNIOCENTESIS_NOTICE:
                amniocentesisWebProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSetting() {
        //指定的垂直滚动条有叠加样式
        amniocentesisWebView.setVerticalScrollbarOverlay(true);
        WebSettings settings = amniocentesisWebView.getSettings();
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
        amniocentesisWebView.setWebViewClient(new WebViewClient());
        amniocentesisWebView.setWebChromeClient(new WebChromeClient());

        amniocentesisWebView.loadUrl(protocol.getUrl());
    }

    @Override
    public AmniocentesisProtocol getProtocol() {
        return protocol;
    }

    @Override
    public AmniocentesisReserveBean getReserveBean() {
        return reserveBean;
    }

    @Override
    public void onReserveProtocolSelect(boolean agree) {
        amniocentesisWebAgreeLayout.setSelected(agree);
        amniocentesisWebDisagreeLayout.setSelected(!agree);
    }

    @Override
    public boolean isSelectReserveProtocol() {
        return amniocentesisWebAgreeLayout.isSelected();
    }

    @Override
    public void onAgreeProtocolSelect() {
        amniocentesisWebAgreeProtocolLayout.setSelected(!amniocentesisWebAgreeProtocolLayout.isSelected());
    }

    @Override
    public boolean isSelectAgreeProtocol() {
        return amniocentesisWebAgreeProtocolLayout.isSelected();
    }

    @Override
    public void onNoticeProtocolSelect(int type) {
        if (type == 1) {
            amniocentesisWebAgreeProtocolLayout1.setSelected(!amniocentesisWebAgreeProtocolLayout1.isSelected());
        } else {
            amniocentesisWebNoticeLayout.setSelected(!amniocentesisWebNoticeLayout.isSelected());
        }
    }

    @Override
    public boolean isSelectNoticeProtocol() {
        return amniocentesisWebAgreeProtocolLayout1.isSelected() && amniocentesisWebNoticeLayout.isSelected();
    }
}
