package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyReserveController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyReserveView;
import com.keydom.mianren.ih_patient.adapter.PrenancyOrderTimeAdapter;
import com.keydom.mianren.ih_patient.bean.CheckProjectsItem;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.bean.event.PregnancyOrderSuccess;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PregnancyReverseActivity extends BaseControllerActivity<PregnancyReserveController> implements PregnancyReserveView {

    private PrenancyOrderTimeAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mDateTv;
    private TextView mCheckProjectsTv;
    private ImageView mCheckProjectsIv;
    private ImageView mDignoseIv;
    private LinearLayout mOrderCheckRootLl;
    private LinearLayout mOrderDiagnoseRootLl;
    private RelativeLayout mCheckProjectsRootRl;
    private TextView mCommitOrderTv;

    private String mCurrentDate = "";

    private List<CheckProjectsItem> checkProjectsItems;

    private boolean isOrderCheck = false;
    private boolean isOrderDiagnose = false;


    private String mRecordId;
    private long mPrenatalProjectId;
    private int mPrenancyType;


    /**
     * 启动
     */
    public static void start(Context context, String recordId, int type) {
        Intent intent = new Intent(context, PregnancyReverseActivity.class);
        intent.putExtra(Const.PREGNANCY_ORDER_TYPE, type);
        intent.putExtra(Const.RECORD_ID, recordId);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_reserve;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        setTitle("产检预约");

        mRecordId = (String) getIntent().getSerializableExtra(Const.RECORD_ID);
        mPrenancyType = getIntent().getIntExtra(Const.PREGNANCY_ORDER_TYPE,
                Const.PREGNANCY_ORDER_TYPE_ALL);

        mRecyclerView = findViewById(R.id.pregnancy_detail_time_rv);
        mDateTv = findViewById(R.id.pregnancy_order_date_tv);
        mCheckProjectsTv = findViewById(R.id.pregnancy_check_projects_tv);
        mOrderCheckRootLl = findViewById(R.id.pregnancy_detail_order_check_root_ll);
        mOrderDiagnoseRootLl = findViewById(R.id.pregnancy_detail_order_diagnose_root_ll);
        mDignoseIv = findViewById(R.id.pregnancy_detail_order_diagnose_iv);
        mCheckProjectsIv = findViewById(R.id.pregnancy_detail_order_check_iv);
        mCommitOrderTv = findViewById(R.id.pregnancy_detail_order_tv);
        mCheckProjectsRootRl = findViewById(R.id.pregnancy_check_projects_root_rl);

        mAdapter = new PrenancyOrderTimeAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);


        mCommitOrderTv.setOnClickListener(getController());
        findViewById(R.id.pregnancy_order_date_root_rl).setOnClickListener(getController());
        mCheckProjectsRootRl.setOnClickListener(getController());
        mOrderCheckRootLl.setOnClickListener(getController());
        mOrderDiagnoseRootLl.setOnClickListener(getController());


        showLayoutAndLogic(mPrenancyType);


    }

    private void showLayoutAndLogic(int type) {
        switch (type) {
            case Const.PREGNANCY_ORDER_TYPE_ALL:
                mOrderCheckRootLl.setVisibility(View.VISIBLE);
                mOrderDiagnoseRootLl.setVisibility(View.VISIBLE);
                mCheckProjectsRootRl.setVisibility(View.VISIBLE);
                getController().getCheckProjects();
                break;
            case Const.PREGNANCY_ORDER_TYPE_CHECK:
                setChecks(true);
                mOrderCheckRootLl.setVisibility(View.VISIBLE);
                mOrderDiagnoseRootLl.setVisibility(View.GONE);
                mCheckProjectsRootRl.setVisibility(View.VISIBLE);
                getController().getCheckProjects();
                break;
            case Const.PREGNANCY_ORDER_TYPE_DIAGNOSE:
                mPrenatalProjectId = 1;
                setOrderDiagnose(true);
                mOrderCheckRootLl.setVisibility(View.GONE);
                mOrderDiagnoseRootLl.setVisibility(View.VISIBLE);
                mCheckProjectsRootRl.setVisibility(View.GONE);
                getController().getCheckProjectsTimes(String.valueOf(mPrenatalProjectId));
                break;
        }
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
        mAdapter.getData().clear();
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PregnancyOrderTime pregnancyOrderTime =
                        (PregnancyOrderTime) adapter.getData().get(position);
                pregnancyOrderTime.setSelected(!pregnancyOrderTime.isSelected());
                for (int i = 0; i < adapter.getData().size(); i++) {
                    PregnancyOrderTime data = (PregnancyOrderTime) adapter.getData().get(i);
                    if (position == i) {
                        continue;
                    } else {
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
    public void commitPregnancySuccess(Object data) {
        EventBus.getDefault().post(new PregnancyOrderSuccess());
        ToastUtils.showShort("预约成功");
        finish();
    }

    @Override
    public String getRecordID() {
        return mRecordId;
    }

    @Override
    public int getAppointType() {

        if (isOrderCheck && isOrderDiagnose) {

            return 12;

        } else if (isOrderCheck && !isOrderDiagnose) {

            return 1;

        } else if (!isOrderCheck && isOrderDiagnose) {

            return 2;

        }

        return 0;
    }

    @Override
    public long getPrenatalProjectId() {
        return mPrenatalProjectId;
    }

    @Override
    public String getTimeInterval() {
        if (null != mAdapter && null != mAdapter.getData() && mAdapter.getData().size() > 0) {
            for (PregnancyOrderTime data : mAdapter.getData()) {
                if (data.isSelected()) {
                    return data.getTimeInterval();
                }
            }
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ChooseInspectItemActivity.CHOOSE_INSPECT_ITEM:
                    List<CheckProjectsItem> projectsItems =
                            (List<CheckProjectsItem>) data.getSerializableExtra(Const.DATA);
                    if (null != projectsItems && projectsItems.size() > 0) {
                        mPrenatalProjectId = projectsItems.get(0).getId();
                        mCheckProjectsTv.setText(projectsItems.get(0).getAntepartumExamProjectName());
                        mCheckProjectsTv.setTextColor(getResources().getColor(R.color.black));
                        getController().getCheckProjectsTimes(String.valueOf(mPrenatalProjectId));
                    } else {
                        mCheckProjectsTv.setText("请选择检验检查项目");
                        mCheckProjectsTv.setTextColor(getResources().getColor(R.color.tab_nol_color));
                    }
                    break;
            }
        }
    }


}
