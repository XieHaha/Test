package com.keydom.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.child_health.controller.ChildHealthDetailController;
import com.keydom.ih_patient.activity.child_health.view.ChildHealthDetailView;
import com.keydom.ih_patient.view.ChildCareItemLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/2/27 11:37
 * @des 儿童保健详情
 */
public class ChildHealthDetailActivity extends BaseControllerActivity<ChildHealthDetailController> implements ChildHealthDetailView {
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;
    @BindView(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex_age)
    TextView tvSexAge;
    @BindView(R.id.layout_one)
    ChildCareItemLayout layoutOne;
    @BindView(R.id.layout_two)
    ChildCareItemLayout layoutTwo;
    @BindView(R.id.layout_three)
    ChildCareItemLayout layoutThree;
    @BindView(R.id.layout_four)
    ChildCareItemLayout layoutFour;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ChildHealthDetailActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getController().getChildHealthDetail();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_child_maintain));
        setReloadListener((v, status) -> getController().getChildHealthDetail());
    }

    @Override
    public void bindDetailData(String data) {
        layoutOne.setContent("测试数据");
        layoutTwo.setContent("测试数据1111");
        layoutThree.setContent("测试数据22222");
        layoutFour.setContent("测试数据333333");
    }
}
