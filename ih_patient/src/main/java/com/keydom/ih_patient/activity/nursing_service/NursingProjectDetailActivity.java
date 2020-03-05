package com.keydom.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.activity.nursing_service.controller.NursingProjectDetailController;
import com.keydom.ih_patient.activity.nursing_service.view.NursingProjectDetailView;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.constant.Global;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

/**
 * 护理服务详情页页面
 */
public class NursingProjectDetailActivity extends BaseControllerActivity<NursingProjectDetailController> implements NursingProjectDetailView {

    /**
     * 启动
     */
    public static void start(Context context,String nursingProjectId,String nursingProjectName){
        Intent intent=new Intent(context,NursingProjectDetailActivity.class);
        intent.putExtra("nursingProjectId",nursingProjectId);
        intent.putExtra("nursingProjectName",nursingProjectName);
        context.startActivity(intent);
    }
    private String nursingProjectId,nursingProjectName;
    private TextView exa_price_tv,service_desc_tv,service_time_tv,order_service_tv,nursing_project_notice_tv;
    private RelativeLayout emptyLayout;
    private TextView emptyTv;
    private ImageView project_icon_img;
    private NursingProjectInfo nursingProjectInfo;
    private LinearLayout detail_wv_layout;
    private WebView detail_wv=null;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_service_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        nursingProjectId=getIntent().getStringExtra("nursingProjectId");
        nursingProjectName=getIntent().getStringExtra("nursingProjectName");
        setTitle(nursingProjectName);
        exa_price_tv=findViewById(R.id.exa_price_tv);
        service_desc_tv=findViewById(R.id.service_desc_tv);
        service_time_tv=findViewById(R.id.service_time_tv);
        project_icon_img=findViewById(R.id.project_icon_img);
        nursing_project_notice_tv=findViewById(R.id.nursing_project_notice_tv);
        emptyLayout=findViewById(R.id.state_retry2);

        detail_wv_layout=findViewById(R.id.detail_wv_layout);
        initWebView();

        order_service_tv=findViewById(R.id.order_service_tv);
        order_service_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else{
                    if(App.userInfo.getIdCard()!=null&&!"".equals(App.userInfo.getIdCard()))
                        NursingChooseHospitalActivity.start(getContext(),nursingProjectInfo);

                    else
                        ToastUtil.showMessage(getContext(),"您还未实名认证，前往个人中心实名认证后才能预约护理服务");
                }

            }
        });
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().getNurseServiceProjectDetailById(nursingProjectId);
            }
        });
        emptyTv=findViewById(R.id.empty_text);
        getController().getNurseServiceProjectDetailById(nursingProjectId);
        RichText.initCacheDir(this);

    }

    @Override
    public void getNursingProjectDetailSuccess(NursingProjectInfo nursingProjectInfo) {
        this.nursingProjectInfo=nursingProjectInfo;
        if(emptyLayout.getVisibility()==View.VISIBLE){
            emptyLayout.setVisibility(View.GONE);
        }
        exa_price_tv.setText("￥"+nursingProjectInfo.getFee()+"元");
        GlideUtils.load(project_icon_img, nursingProjectInfo.getDetailImg() == null ? "" : Const.IMAGE_HOST+nursingProjectInfo.getDetailImg(), 0, 0, false, null);
        if(nursingProjectInfo.getIntro()!=null&&!"".equals(nursingProjectInfo.getIntro())){
            detail_wv_layout.setVisibility(View.VISIBLE);
            service_desc_tv.setVisibility(View.GONE);
          /*  RichText.from(nursingProjectInfo.getIntro()).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(service_desc_tv);*/


            StringBuilder sb = new StringBuilder();
            sb.append(getHtmlData(nursingProjectInfo.getIntro()));
            detail_wv.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
        }else {
            detail_wv_layout.setVisibility(View.GONE);
            service_desc_tv.setVisibility(View.VISIBLE);
            service_desc_tv.setText("暂无相关描述");
        }
        service_time_tv.setText(nursingProjectInfo.getTimes()!=null&&!"".equals(nursingProjectInfo.getTimes())?nursingProjectInfo.getTimes():"");
        nursing_project_notice_tv.setText(nursingProjectInfo.getReservationDetail()!=null&&!"".equals(nursingProjectInfo.getReservationDetail())?nursingProjectInfo.getReservationDetail():"");
    }
    @Override
    public void getNursingProjectDetailFailed(String errMsg) {
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("护理项目获取失败，点击重试");
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            // 重新测量
            view.measure(w, h);
        }
    }

    private void initWebView(){
        if(detail_wv==null){
            detail_wv = new WebView(this);
            detail_wv.setWebViewClient(new MyWebViewClient());
            WebSettings webSettings = detail_wv.getSettings();
            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            // 设置支持javascript脚本
            webSettings.setJavaScriptEnabled(true);

            // 设置此属性，可任意比例缩放
            webSettings.setUseWideViewPort(true);
            // 设置不出现缩放工具
            webSettings.setBuiltInZoomControls(false);
            // 设置不可以缩放
            webSettings.setSupportZoom(true);
            webSettings.setDisplayZoomControls(false);

            webSettings.setTextSize(WebSettings.TextSize.LARGEST);

            //自适应屏幕
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            // 自适应 屏幕大小界面
            webSettings.setLoadWithOverviewMode(true);
            detail_wv_layout.addView(detail_wv);

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
