package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.JustifiedTextView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthConsultantDetailController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthConsultantDetailView;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/4/8 15:37
 * @des 健康管理咨询师
 */
public class HealthConsultantDetailActivity extends BaseControllerActivity<HealthConsultantDetailController> implements HealthConsultantDetailView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.consultant_detail_name_tv)
    TextView consultantDetailNameTv;
    @BindView(R.id.consultant_detail_depart_tv)
    TextView consultantDetailDepartTv;
    @BindView(R.id.consultant_detail_title_tv)
    TextView consultantDetailTitleTv;
    @BindView(R.id.consultant_detail_header_tv)
    ImageView consultantDetailHeaderTv;
    @BindView(R.id.consultant_detail_hospital_tv)
    TextView consultantDetailHospitalTv;
    @BindView(R.id.consultant_detail_grade_tv)
    TextView consultantDetailGradeTv;
    @BindView(R.id.consultant_detail_good_tv)
    JustifiedTextView consultantDetailGoodTv;
    @BindView(R.id.consultant_detail_info_tv)
    JustifiedTextView consultantDetailInfoTv;
    @BindView(R.id.consultant_detail_next_tv)
    TextView consultantDetailNextTv;

    private HealthConsultantBean consultantBean;

    /**
     * 启动
     */
    public static void start(Context context, HealthConsultantBean bean) {
        Intent intent = new Intent(context, HealthConsultantDetailActivity.class);
        intent.putExtra(Const.DATA, bean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_consultant_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStateBarHeight(this)));
        StatusBarUtils.setStatusBarTranslucent(this);
        tvTitle.setText(R.string.txt_child_maintain);
        layoutBg.setAlpha(0);
        statusBar.setAlpha(0);
        StatusBarUtils.setStatusBarColor(this, true);
        ivBack.setOnClickListener(v -> finish());
        consultantDetailNextTv.setOnClickListener(getController());

        consultantBean = (HealthConsultantBean) getIntent().getSerializableExtra(Const.DATA);
        bindData();
    }

    private void bindData() {
        if (consultantBean != null) {
            consultantDetailNameTv.setText(consultantBean.getName());
            consultantDetailDepartTv.setText(consultantBean.getDeptName());
            consultantDetailTitleTv.setText(consultantBean.getJobTitleName());
            consultantDetailHospitalTv.setText(consultantBean.getHospitalName());
            consultantDetailGradeTv.setText(consultantBean.getName());
            consultantDetailGoodTv.setText(consultantBean.getAdept());
            consultantDetailInfoTv.setText(consultantBean.getIntro());
            GlideUtils.load(consultantDetailHeaderTv,
                    BaseFileUtils.getHeaderUrl(consultantBean.getImage()), -1,
                    R.mipmap.user_icon, true, null);
        }
    }

    @Override
    public HealthConsultantBean getConsultantBean() {
        return consultantBean;
    }
}
