package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyReserveController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyReserveView;
import com.keydom.mianren.ih_patient.bean.CheckProjectBean;
import com.keydom.mianren.ih_patient.bean.CheckProjectRootBean;
import com.keydom.mianren.ih_patient.bean.CheckProjectSubBean;
import com.keydom.mianren.ih_patient.bean.DoctorScheduling;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;
import com.keydom.mianren.ih_patient.bean.event.PregnancyOrderSuccess;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 顿顿
 */
public class PregnancyReverseActivity extends BaseControllerActivity<PregnancyReserveController> implements PregnancyReserveView {

    private TextView mDateTv;
    private TextView mCheckProjectsTv;
    private RelativeLayout mCheckProjectsRootRl;
    private TextView mCommitOrderTv;

    private TextView reserveTypeTv, reserveDoctorNameTv;
    private RelativeLayout layoutReserveDoctor;

    private TextView reserveTimeTv;

    private String mCurrentDate = "", mCurrentTime = "";

    private CheckProjectRootBean rootBean;
    private List<CheckProjectSubBean> subBeans;
    private List<CheckProjectSubBean> selectSubBeans;

    private String mRecordId;
    private String mPrenatalProjectId;
    private String prenatalProjectName;
    private int mPrenancyType;

    private PregnancyOrderTime pregnancyOrderTime;
    private PregnancyRecordItem recordItem;
    /**
     * 时间段
     */
    private List<PregnancyOrderTime> spinnerTimeData = new ArrayList<>();

    /**
     * 排班医生
     */
    private List<DoctorScheduling> doctorSchedulings = new ArrayList<>();


    /**
     * 启动
     */
    public static void start(Context context, PregnancyRecordItem recordItem, int type) {
        Intent intent = new Intent(context, PregnancyReverseActivity.class);
        intent.putExtra(Const.PREGNANCY_ORDER_TYPE, type);
        intent.putExtra(Const.DATA, recordItem);
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

        recordItem = (PregnancyRecordItem) getIntent().getSerializableExtra(Const.DATA);
        mPrenancyType = getIntent().getIntExtra(Const.PREGNANCY_ORDER_TYPE,
                Const.PREGNANCY_ORDER_TYPE_ALL);
        if (recordItem != null) {
            mRecordId = recordItem.getRecordId();
        }

        mDateTv = findViewById(R.id.pregnancy_order_date_tv);
        mCheckProjectsTv = findViewById(R.id.pregnancy_check_projects_tv);
        mCommitOrderTv = findViewById(R.id.pregnancy_detail_order_tv);
        mCheckProjectsRootRl = findViewById(R.id.pregnancy_check_projects_root_rl);

        reserveTimeTv = findViewById(R.id.nice_spinner);
        reserveTimeTv.setOnClickListener(getController());

        reserveTypeTv = findViewById(R.id.reserve_type_tv);
        reserveDoctorNameTv = findViewById(R.id.doctor_name_tv);
        layoutReserveDoctor = findViewById(R.id.reserve_doctor_layout);

        mCommitOrderTv.setOnClickListener(getController());
        findViewById(R.id.pregnancy_order_date_root_rl).setOnClickListener(getController());
        mCheckProjectsRootRl.setOnClickListener(getController());

        showLayoutAndLogic();
    }

    private void showLayoutAndLogic() {
        switch (mPrenancyType) {
            case Const.PREGNANCY_ORDER_TYPE_ALL:
                //                mCheckProjectsRootRl.setVisibility(View.VISIBLE);
                //                getController().getCheckProjects();
                break;
            case Const.PREGNANCY_ORDER_TYPE_CHECK:
                reserveTypeTv.setText("检验检查");
                mCheckProjectsRootRl.setVisibility(View.GONE);
                layoutReserveDoctor.setVisibility(View.GONE);
                if (recordItem != null) {
                    mCurrentDate = recordItem.getPrenatalDate();
                    mCurrentTime = recordItem.getPrenatalTimeInterval();
                    mDateTv.setText(mCurrentDate);
                    mDateTv.setTextColor(getResources().getColor(R.color.black));
                    reserveTimeTv.setText(mCurrentTime);
                }
                break;
            case Const.PREGNANCY_ORDER_TYPE_DIAGNOSE:
                reserveTypeTv.setText("产科门诊");
                //默认全部
                mPrenatalProjectId = "1";
                mCheckProjectsRootRl.setVisibility(View.VISIBLE);
                layoutReserveDoctor.setVisibility(View.VISIBLE);

                if (recordItem != null) {
                    mCurrentDate = recordItem.getAppointDate();
                    mCurrentTime = recordItem.getAppointTimeInterval();
                    mDateTv.setText(mCurrentDate);
                    mDateTv.setTextColor(getResources().getColor(R.color.black));
                    reserveTimeTv.setText(mCurrentTime);
                }
                getController().getDoctorScheduling();
                break;
            default:
                break;
        }
        getController().getCheckProjectsTimes(mCurrentDate, mPrenancyType, false);
    }


    @Override
    public void setOrderDate(Date date) {
        String dateStr = DateUtils.dateToString(date);
        if (!TextUtils.isEmpty(dateStr)) {
            mDateTv.setText(dateStr);
            mDateTv.setTextColor(getResources().getColor(R.color.black));
            mCurrentDate = dateStr;
        }
        getController().getCheckProjectsTimes(dateStr, mPrenancyType, true);
    }

    @Override
    public void setPregnancyOrderTime(PregnancyOrderTime pregnancyOrderTime) {
        this.pregnancyOrderTime = pregnancyOrderTime;
        reserveTimeTv.setText(mCurrentTime = pregnancyOrderTime.getTimeInterval());
        getController().getAntDoctor(mCurrentDate, mCurrentTime);
    }

    @Override
    public String getSelectedDate() {
        return mCurrentDate;
    }

    @Override
    public String getTimeInterval() {
        return mCurrentTime;
    }

    @Override
    public int getmPrenancyType() {
        return mPrenancyType + 1;
    }

    @Override
    public List<DoctorScheduling> getDoctorSchedulings() {
        return doctorSchedulings;
    }

    @Override
    public List<PregnancyOrderTime> getSpinnerTimeData() {
        return spinnerTimeData;
    }

    @Override
    public void requestDoctorSchedulingSuccess(List<DoctorScheduling> data) {
        doctorSchedulings = data;
    }

    @Override
    public void requestDoctorSchedulingFailed(String data) {
        ToastUtil.showMessage(this, data);
    }

    @Override
    public void getCheckProjectsSuccess(CheckProjectRootBean data) {
        rootBean = data;
        subBeans = new ArrayList<>();
        List<CheckProjectBean> projectBeans = rootBean.getItem();
        for (CheckProjectBean bean : projectBeans) {
            if (bean.getDetailItem() != null && bean.getDetailItem().size() > 0) {
                subBeans.addAll(bean.getDetailItem());
            }
        }
    }

    @Override
    public void getCheckProjectsFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getCheckProjectsTimesSuccess(List<PregnancyOrderTime> data, boolean update) {
        spinnerTimeData = data;
        if (update) {
            if (spinnerTimeData != null && spinnerTimeData.size() > 0) {
                mCurrentTime = spinnerTimeData.get(0).getTimeInterval();
            } else {
                mCurrentTime = "";
            }
        }
        reserveTimeTv.setText(mCurrentTime);
        getController().getAntDoctor(mCurrentDate, mCurrentTime);
    }

    @Override
    public void getCheckProjectsTimesFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void requestDoctorSuccess(String name) {
        reserveDoctorNameTv.setText(name);
    }

    @Override
    public List<CheckProjectSubBean> getCheckProjects() {
        return subBeans == null ? new ArrayList<>() : subBeans;
    }

    @Override
    public List<CheckProjectSubBean> getSelectSubBeans() {
        return selectSubBeans;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ChooseInspectItemActivity.CHOOSE_INSPECT_ITEM) {
                selectSubBeans =
                        (List<CheckProjectSubBean>) data.getSerializableExtra(Const.DATA);
                if (null != selectSubBeans && selectSubBeans.size() > 0) {
                    StringBuilder builderId = new StringBuilder();
                    StringBuilder builderName = new StringBuilder();
                    for (int i = 0; i < selectSubBeans.size(); i++) {
                        builderId.append(selectSubBeans.get(i).getItemCode());
                        builderName.append(selectSubBeans.get(i).getItemName());
                        if (selectSubBeans.size() - 1 > i) {
                            builderId.append("、");
                            builderName.append("、");
                        }
                    }
                    mPrenatalProjectId = builderId.toString();
                    prenatalProjectName = builderName.toString();
                    mCheckProjectsTv.setText(prenatalProjectName);
                    mCheckProjectsTv.setTextColor(getResources().getColor(R.color.black));
                    //                    getController().getCheckProjectsTimes("", 0);
                } else {
                    mCheckProjectsTv.setText("请选择检验检查项目");
                    mCheckProjectsTv.setTextColor(getResources().getColor(R.color.tab_nol_color));
                }
            }
        }
    }


}
