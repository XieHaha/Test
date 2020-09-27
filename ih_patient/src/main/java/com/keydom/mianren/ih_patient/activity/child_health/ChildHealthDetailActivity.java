package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthDetailController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthDetailView;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.view.ChildCareItemLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健详情
 */
public class ChildHealthDetailActivity extends BaseControllerActivity<ChildHealthDetailController> implements ChildHealthDetailView {

    @BindView(R.id.child_health_detail_head_iv)
    ImageView childHealthDetailHeadIv;
    @BindView(R.id.child_health_detail_name_tv)
    TextView childHealthDetailNameTv;
    @BindView(R.id.child_health_detail_sex_tv)
    TextView childHealthDetailSexTv;
    @BindView(R.id.child_health_detail_age_tv)
    TextView childHealthDetailAgeTv;
    @BindView(R.id.child_health_detail_select_time_tv)
    TextView childHealthDetailSelectTimeTv;
    @BindView(R.id.child_health_detail_time_tv)
    TextView childHealthDetailTimeTv;
    @BindView(R.id.child_health_detail_select_project_layout)
    ChildCareItemLayout childHealthDetailSelectProjectLayout;
    @BindView(R.id.child_health_detail_unselect_project_layout)
    ChildCareItemLayout childHealthDetailUnselectProjectLayout;
    @BindView(R.id.child_health_detail_notice_layout)
    ChildCareItemLayout childHealthDetailNoticeLayout;
    @BindView(R.id.child_health_detail_next_tv)
    TextView childHealthDetailNextTv;

    private ChildHealthProjectBean projectBean;

    /**
     * 启动
     */
    public static void start(Context context, ChildHealthProjectBean projectBean) {
        Intent intent = new Intent(context, ChildHealthDetailActivity.class);
        intent.putExtra(Const.DATA, projectBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_child_maintain_detail));
        projectBean = (ChildHealthProjectBean) getIntent().getSerializableExtra(Const.DATA);
    }

    @Override
    public void bindDetailData(String data) {
    }
}
