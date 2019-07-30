package com.keydom.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.activity.nursing_service.controller.NursingProjectDetailController;
import com.keydom.ih_patient.activity.nursing_service.view.NursingProjectDetailView;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.ToastUtil;
import com.zzhoujay.richtext.ImageHolder;
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
        order_service_tv=findViewById(R.id.order_service_tv);
        order_service_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else{
                    if(App.userInfo.getIdCard()!=null&&!"".equals(App.userInfo.getIdCard()))
                        NursingChooseHospitalActivity.start(getContext(),nursingProjectInfo);

                    else
                        ToastUtil.shortToast(getContext(),"您还未实名认证，前往个人中心实名认证后才能预约护理服务");
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
        GlideUtils.load(project_icon_img, nursingProjectInfo.getIcon() == null ? "" : Const.IMAGE_HOST+nursingProjectInfo.getIcon(), 0, 0, false, null);
        if(nursingProjectInfo.getIntro()!=null&&!"".equals(nursingProjectInfo.getIntro())){
            RichText.from(nursingProjectInfo.getIntro()).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(service_desc_tv);
        }else {
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
}
