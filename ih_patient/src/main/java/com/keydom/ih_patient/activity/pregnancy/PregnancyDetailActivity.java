package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyDetailController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyDetailView;
import com.keydom.ih_patient.adapter.PrenancyOrderTimeAdapter;
import com.keydom.ih_patient.bean.CheckProjectsItem;
import com.keydom.ih_patient.bean.PregnancyDetailBean;
import com.keydom.ih_patient.bean.PregnancyOrderTime;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PregnancyDetailActivity extends BaseControllerActivity<PregnancyDetailController> implements PregnancyDetailView {


    private PrenancyOrderTimeAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mDateTv;
    private TextView mWeeksTv;
    private TextView mDescTv;
    private TextView mCheckProjectsTv;
    private ImageView mCheckProjectsIv;
    private ImageView mDignoseIv;
    private LinearLayout mOrderCheckRootLl;
    private LinearLayout mOrderDiagnoseRootLl;

    private String mCurrentDate = "";

    private List<CheckProjectsItem> checkProjectsItems;

    private boolean isOrderCheck = false;
    private boolean isOrderDiagnose = false;


    public static String pregnancyDetailStr = "PregnancyDetailBean";

    private PregnancyDetailBean mPregnancyDetailBean;


    /**
     * 启动
     */
    public static void start(Context context, PregnancyDetailBean pregnancyDetailBean) {
        Intent intent = new Intent(context, PregnancyDetailActivity.class);
        intent.putExtra(pregnancyDetailStr, pregnancyDetailBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_detail;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        getTitleLayout().setWhiteBar();
        getTitleLayout().setBackgroundColor(getResources().getColor(R.color.vip_pregnancy_detail_tool_bar_bg));
        setTitle("产检预约");

        mPregnancyDetailBean = (PregnancyDetailBean) getIntent().getSerializableExtra(pregnancyDetailStr);

        mWeeksTv = findViewById(R.id.pregnancy_detail_weeks_tv);
        mDescTv = findViewById(R.id.pregnancy_detail_desc_tv);
        mRecyclerView = findViewById(R.id.pregnancy_detail_time_rv);
        mDateTv = findViewById(R.id.pregnancy_order_date_tv);
        mCheckProjectsTv = findViewById(R.id.pregnancy_check_projects_tv);
        mOrderCheckRootLl = findViewById(R.id.pregnancy_detail_order_check_root_ll);
        mOrderDiagnoseRootLl = findViewById(R.id.pregnancy_detail_order_diagnose_root_ll);
        mDignoseIv = findViewById(R.id.pregnancy_detail_order_diagnose_iv);
        mCheckProjectsIv = findViewById(R.id.pregnancy_detail_order_check_iv);

        mAdapter = new PrenancyOrderTimeAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);


        findViewById(R.id.pregnancy_order_date_root_rl).setOnClickListener(getController());
        findViewById(R.id.pregnancy_check_projects_root_rl).setOnClickListener(getController());
        mOrderCheckRootLl.setOnClickListener(getController());
        mOrderDiagnoseRootLl.setOnClickListener(getController());

        if (null != mPregnancyDetailBean) {
            mWeeksTv.setText(mPregnancyDetailBean.getShowDate());
            mDescTv.setText(mPregnancyDetailBean.getContext());
        }

        getController().getCheckProjects();
    }


    @Override
    public void setOrderDate(Date date) {
        String dateStr = DateUtils.dateToString(date);
        if (!TextUtils.isEmpty(dateStr)) {
            mDateTv.setText(dateStr);
            mDateTv.setTextColor(getResources().getColor(R.color.black));
            mCurrentDate = dateStr;
        }
    }

    @Override
    public String getSelectedDate() {
        return mCurrentDate;
    }


    @Override
    public void getCheckProjectsSuccess(List<CheckProjectsItem> data) {
        checkProjectsItems = data;
    }

    @Override
    public void getCheckProjectsFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getCheckProjectsTimesSuccess(List<PregnancyOrderTime> data) {
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PregnancyOrderTime pregnancyOrderTime = (PregnancyOrderTime) adapter.getData().get(position);
                pregnancyOrderTime.setSelected(!pregnancyOrderTime.isSelected());
                for (int i = 0; i < adapter.getData().size(); i++) {
                    PregnancyOrderTime data = (PregnancyOrderTime) adapter.getData().get(i);
                    if(position == i){
                        continue;
                    }else{
                        data.setSelected(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getCheckProjectsTimesFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public List<CheckProjectsItem> getCheckProjects() {
        return checkProjectsItems != null ? checkProjectsItems : new ArrayList<CheckProjectsItem>();
    }

    @Override
    public boolean isOrderChecks() {
        return isOrderCheck;
    }

    @Override
    public void setChecks(boolean isOrderChecks) {
        this.isOrderCheck = isOrderChecks;
        mCheckProjectsIv.setSelected(isOrderChecks);
    }

    @Override
    public boolean isOrderDiagnose() {
        return isOrderDiagnose;
    }

    @Override
    public void setOrderDiagnose(boolean isOrderDiagnose) {
        this.isOrderDiagnose = isOrderDiagnose;
        mDignoseIv.setSelected(isOrderDiagnose);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ChooseInspectItemActivity.CHOOSE_INSPECT_ITEM:
                    List<CheckProjectsItem> projectsItems = (List<CheckProjectsItem>) data.getSerializableExtra(Const.DATA);
                    if (null != projectsItems && projectsItems.size() > 0) {
                        mCheckProjectsTv.setText(projectsItems.get(0).getAntepartumExamProjectName());
                        mCheckProjectsTv.setTextColor(getResources().getColor(R.color.black));
                        getController().getCheckProjectsTimes(String.valueOf(projectsItems.get(0).getId()));
                    } else {
                        mCheckProjectsTv.setText("请选择检验检查项目");
                        mCheckProjectsTv.setTextColor(getResources().getColor(R.color.tab_nol_color));
                    }
                    break;
            }
        }
    }


}
