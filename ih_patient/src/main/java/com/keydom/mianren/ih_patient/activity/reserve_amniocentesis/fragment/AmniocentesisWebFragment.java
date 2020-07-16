package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisWebController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisWebView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

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

    /**
     * 申请提交bean
     */
    private AmniocentesisReserveBean reserveBean;
    /**
     * 羊水穿刺预约评估参数
     */
    private Map<String, Object> evaluateParamsMap;
    /**
     * 协议类型
     */
    private AmniocentesisProtocol protocol;

    private String code;

    /**
     * 网页加载错误
     */
    private boolean isError;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_web;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initPage();
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
        //        initWebViewSetting();
        initPage();

        setReloadListener((v, status) -> {
            isError = false;
            initPage();
        });
    }

    public void setProtocol(AmniocentesisProtocol protocol) {
        this.protocol = protocol;
    }

    public void setReserveBean(AmniocentesisReserveBean reserveBean) {
        this.reserveBean = reserveBean;
    }

    public void setEvaluateParamsMap(Map<String, Object> evaluateParamsMap) {
        this.evaluateParamsMap = evaluateParamsMap;
    }

    private void initPage() {
        switch (protocol) {
            case AMNIOCENTESIS_WEB_RESERVE:
                code = CommonDocumentBean.CODE_16;
                amniocentesisWebNextTv.setText(R.string.txt_next);
                amniocentesisWebProtocolLayout.setVisibility(View.VISIBLE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.GONE);
                break;
            case AMNIOCENTESIS_AGREE_PROTOCOL:
                code = CommonDocumentBean.CODE_17;
                amniocentesisWebNextTv.setText(R.string.txt_next);
                amniocentesisWebProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.VISIBLE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.GONE);
                break;
            case AMNIOCENTESIS_NOTICE:
                code = CommonDocumentBean.CODE_18;
                amniocentesisWebNextTv.setText(R.string.txt_commit_reserve_apply);
                amniocentesisWebProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebAgreeProtocolLayout.setVisibility(View.GONE);
                amniocentesisWebNoticeProtocolLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        pageLoading();

        getController().getOfficialDispatchAllMsgByCode(code);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSetting() {
        //指定的垂直滚动条有叠加样式
        amniocentesisWebView.setVerticalScrollbarOverlay(true);
        WebSettings settings = amniocentesisWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        //设置页面默认缩放密度
        //        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
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
    }

    private void initClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!isError && amniocentesisWebView != null) {
                    amniocentesisWebView.setVisibility(View.VISIBLE);
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

        amniocentesisWebView.setWebViewClient(webViewClient);
        amniocentesisWebView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 加载状态
     */
    private void loadStatus() {
        amniocentesisWebView.setVisibility(View.GONE);
        pageLoadingFail();
    }


    @Override
    public void onWebUrlSuccess(CommonDocumentBean bean) {
        pageLoadingSuccess();
        if (bean != null) {
            String url = bean.getContent();
            if (StringUtils.isEmpty(url)) {
                initWebViewSetting();
                url = bean.getUrl().contains("http://") ? bean.getUrl() : "http://" + bean.getUrl();
                amniocentesisWebView.loadUrl(url);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(getHtmlData(bean.getContent()));
                amniocentesisWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8"
                        , null);
            }
        }

    }

    @Override
    public void onWebUrlFailed() {
        pageLoadingFail();
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

        return "<html><header>" + css + "</header>" + bodyHTML + "</html>";
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

    @Override
    public Map<String, Object> getParamsMap() {
        return evaluateParamsMap;
    }
}
