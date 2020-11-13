package com.keydom.mianren.ih_patient.activity.common_document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.controller.CommonDocumentController;
import com.keydom.mianren.ih_patient.activity.common_document.view.CommonDocumentView;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * des:文书维护公共页面
 *
 * @author 顿顿
 */
public class CommonDocumentActivity extends BaseControllerActivity<CommonDocumentController> implements CommonDocumentView {
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String TITLE = "title";
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.dec_tv)
    TextView decTv;
    @BindView(R.id.document_bottom_yes_layout)
    LinearLayout documentBottomYesLayout;
    @BindView(R.id.document_bottom_no_layout)
    LinearLayout documentBottomNoLayout;
    @BindView(R.id.document_bottom_next_tv)
    TextView documentBottomNextTv;
    @BindView(R.id.document_bottom_layout)
    LinearLayout documentBottomLayout;

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
    public static void start(Context ctx, String title, String url) {
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
        RichText.initCacheDir(this);
        String type = getIntent().getStringExtra(TYPE);
        String url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);
        if (TextUtils.isEmpty(type)) {
            webView.setVisibility(View.VISIBLE);
            decTv.setVisibility(View.GONE);
            setTitle(title);
            if (!url.startsWith("http")) {
                loadUrl(Const.RELEASE_HOST + url);
            } else {
                loadUrl(url);
            }
        } else {
            switch (type) {
                case CommonDocumentBean.CODE_1:
                    setTitle("在线问诊用户协议");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_2:
                    setTitle("护理服务用户协议");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_3:
                    setTitle("入院注意事项");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_4:
                    setTitle("体检中心介绍");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_5:
                    setTitle("体检须知");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_6:
                    setTitle("体检流程");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_8:
                    setTitle("注册用户协议");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_10:
                    setTitle("服务介绍");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_13:
                case CommonDocumentBean.CODE_14:
                    setTitle("保险条款用户协议");
                    getController().getOfficialDispatchAllMsgByCode(type);
                    break;
                case CommonDocumentBean.CODE_15:
                    setTitle("团体体检");
                    webView.setVisibility(View.VISIBLE);
                    decTv.setVisibility(View.GONE);
                    loadUrl("http://www.health580.cn/reservation/public/wap/group_line/logingroup" +
                            "?center_code=610008");
                    break;
                case CommonDocumentBean.CODE_19:
                    setTitle("无痛分娩预约");
                    documentBottomLayout.setVisibility(View.VISIBLE);
                    documentBottomNextTv.setOnClickListener(getController());
                    documentBottomYesLayout.setOnClickListener(getController());
                    documentBottomNoLayout.setOnClickListener(getController());
                    getController().getOfficialDispatchAllMsgByCode(type);

                    showVirusTips(getString(R.string.txt_painless_delivery_title),
                            getString(R.string.txt_painless_delivery_tips));
                    break;
                case CommonDocumentBean.CODE_101:
                    setTitle("体检报表");
                    webView.setVisibility(View.VISIBLE);
                    decTv.setVisibility(View.GONE);
                    loadUrl("http://api.weixin.zkpacs.com" +
                            ".cn/report/jsp/webVersionJsp/report/reportBind" +
                            ".html?center_code=610008");
                    break;
                case CommonDocumentBean.CODE_102:
                    setTitle("个体体检");
                    webView.setVisibility(View.VISIBLE);
                    decTv.setVisibility(View.GONE);
                    loadUrl("http://www.health580.cn/reservation/public/wap/norder_line" +
                            "/nallPackage?center_code=610008");
                    break;
                case CommonDocumentBean.CODE_103:
                    setTitle("个性化体检");
                    webView.setVisibility(View.VISIBLE);
                    decTv.setVisibility(View.GONE);
                    loadUrl("http://center.zkpacs.com" +
                            ".cn/jsp/wxNewServegroup/nquesProcess/selectInfo.html");
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void getData(CommonDocumentBean bean) {
        if (bean != null) {
            if (bean.getTitle() != null) {

            }
            String url = bean.getContent();
            if (StringUtils.isEmpty(bean.getContent())) {
                webView.setVisibility(View.VISIBLE);
                decTv.setVisibility(View.GONE);
                url = bean.getUrl().contains("http://") ? bean.getUrl() : "http://" + bean.getUrl();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setDefaultTextEncodingName("utf-8");
                //webview不能完全加载网页
                webView.getSettings().setDomStorageEnabled(true);
                // 或者不能很好的支持懒加载是添加此设置
                webView.setWebViewClient(new WebViewClient() {
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
                webView.loadUrl(url);
            } else {
                webView.setVisibility(View.VISIBLE);
                decTv.setVisibility(View.GONE);

                StringBuilder sb = new StringBuilder();
                sb.append(getHtmlData(bean.getContent()));
                webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
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

    public void loadUrl(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //webview不能完全加载网页 或者不能很好的支持懒加载是添加此设置
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
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
        webView.loadUrl(url);
    }

    private void showVirusTips(String title, String content) {
        new GeneralDialog(this, content)
                .setTitle(title)
                .setContentGravity(Gravity.LEFT)
                .setNegativeButtonIsGone(true)
                .show();
    }

    @Override
    public void onReserveProtocolSelect(boolean agree) {
        documentBottomYesLayout.setSelected(agree);
        documentBottomNoLayout.setSelected(!agree);
    }

    @Override
    public boolean isSelectReserveProtocol() {
        return documentBottomYesLayout.isSelected();
    }

}
