package com.keydom.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.controller.ChooseDoctorController;
import com.keydom.ih_patient.activity.order_doctor_register.view.ChooseDoctorView;
import com.keydom.ih_patient.adapter.DoctorAdapter;
import com.keydom.ih_patient.adapter.OrderExtDateAdapter;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择医生页面
 */
public class ChooseDoctorActivity extends BaseControllerActivity<ChooseDoctorController> implements ChooseDoctorView {
    /**
     * 启动
     */
    public static void start(Context context, long areaId, String areaName, long departmentId, String departmentName, List<HospitaldepartmentsInfo> departmentList) {
        Intent intent = new Intent(context, ChooseDoctorActivity.class);
        intent.putExtra("areaId", areaId);
        intent.putExtra("areaName", areaName);
        intent.putExtra("departmentId", departmentId);
        intent.putExtra("departmentName", departmentName);
        Bundle bundle = new Bundle();
        bundle.putSerializable("departmentList", (Serializable) departmentList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TextView chosed_department_tv, tvRightComplete, selected_hospital_area_tv, search_doctor_tv, emptyTv;
    private LinearLayout llLeftGoBack, dateLayout,llRightComplete;
    private RelativeLayout emptyLayout;
    private RecyclerView order_doctor_date_rv, order_doctor_rv;
    private OrderExtDateAdapter orderExtDateAdapter;
    private List<DateInfo> dateList = new ArrayList<>();
    private List<HospitaldepartmentsInfo> departmentList;
    private DoctorAdapter doctorAdapter;
    private List<DepartmentSchedulingBean> doctorList = new ArrayList<>();
    private long SelectedDepartmentId, areaId;
    private String departmentName, areaName, selectedDate;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_doctor_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        areaId = getIntent().getLongExtra("areaId", -1);
        areaName = getIntent().getStringExtra("areaName");
        SelectedDepartmentId = getIntent().getLongExtra("departmentId", -1);
        departmentName = getIntent().getStringExtra("departmentName");
        departmentList = (List<HospitaldepartmentsInfo>) getIntent().getSerializableExtra("departmentList");
        dateLayout = this.findViewById(R.id.date_layout);
        emptyLayout = this.findViewById(R.id.state_retry2);
        emptyTv = this.findViewById(R.id.empty_text);
        chosed_department_tv = this.findViewById(R.id.chosed_department_tv);
        chosed_department_tv.setText(departmentName);
        chosed_department_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (departmentList != null) {
                    showChooseDepartmentDialog();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("hospitalId", App.hospitalId);
                    map.put("hospitalAreaId", areaId);
                    getController().QueryDeptList(map);
                }

            }
        });
        tvRightComplete = this.findViewById(R.id.tvRightComplete);
        selected_hospital_area_tv = this.findViewById(R.id.selected_hospital_area_tv);
        if (Global.getHospitalAreaInfo() != null)
            selected_hospital_area_tv.setText("当前为" + Global.getHospitalAreaInfo().getHospitalName() + "-" + areaName);
        else
            selected_hospital_area_tv.setText("当前为" + App.hospitalName + "-" + areaName);
        llLeftGoBack = this.findViewById(R.id.llLeftGoBack);
        llLeftGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_doctor_tv = this.findViewById(R.id.search_doctor_tv);
        search_doctor_tv.setOnClickListener(getController());
        llRightComplete=findViewById(R.id.llRightComplete);
        llRightComplete.setOnClickListener(getController());
        order_doctor_date_rv = this.findViewById(R.id.order_doctor_date_rv);
        order_doctor_date_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        orderExtDateAdapter = new OrderExtDateAdapter(getContext(), dateList, new GeneralCallback.dateAdapterCallBack() {
            @Override
            public void getSelectedDate(int position) {
                if (dateList.size() != 0 && dateList != null) {
                    selectedDate = dateList.get(position).getDate();
                    getController().getDoctorList(selectedDate, SelectedDepartmentId);
                }
            }
        });
        order_doctor_date_rv.setAdapter(orderExtDateAdapter);
        order_doctor_rv = this.findViewById(R.id.order_doctor_rv);
        doctorAdapter = new DoctorAdapter(getContext(), doctorList);
        order_doctor_rv.setAdapter(doctorAdapter);
        getController().queryDateList(SelectedDepartmentId);
    }

    /**
     * 选择科室弹框
     */
    private void showChooseDepartmentDialog() {

        List<String> parentDepList = new ArrayList<>();
        final List<List<String>> childDepList = new ArrayList<>();
        for (int i = 0; i < departmentList.size(); i++) {
            parentDepList.add(departmentList.get(i).getName());
            List<String> parendChildList = new ArrayList<>();
            for (int j = 0; j < departmentList.get(i).getHdList().size(); j++) {
                parendChildList.add(departmentList.get(i).getHdList().get(j).getName());
            }
            childDepList.add(parendChildList);
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                chosed_department_tv.setText(childDepList.get(options1).get(option2));
                SelectedDepartmentId = departmentList.get(options1).getHdList().get(option2).getId();
                getController().queryDateList(SelectedDepartmentId);
            }
        }).build();
        optionsPickerView.setPicker(parentDepList, childDepList);
        optionsPickerView.show();
    }

    @Override
    public void getDateListSuccess(List<DateInfo> dateInfoList) {
        if (dateInfoList != null && dateInfoList.size() != 0) {
            if (dateLayout.getVisibility() == View.GONE) {
                dateLayout.setVisibility(View.VISIBLE);
                order_doctor_rv.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            orderExtDateAdapter.refreshInit();
            this.dateList.clear();
            this.dateList.addAll(dateInfoList);
            orderExtDateAdapter.notifyDataSetChanged();
            // orderExtDateAdapter.defaultQuery();
        } else {
            dateLayout.setVisibility(View.GONE);
            order_doctor_rv.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyTv.setText("该科室下暂无排班");
        }

    }

    @Override
    public void getDateListFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "获取排班日期失败：" + errMsg);
        dateLayout.setVisibility(View.GONE);
        order_doctor_rv.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("数据获取失败，点击重试");
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().queryDateList(SelectedDepartmentId);
            }
        });
    }

    @Override
    public void getDoctorListSuccess(List<DepartmentSchedulingBean> dataList) {

        if (dataList != null && dataList.size() != 0) {
            if (order_doctor_rv.getVisibility() == View.GONE) {
                order_doctor_rv.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            doctorList.clear();
            doctorList.addAll(dataList);
            doctorAdapter.notifyDataSetChanged();
        } else {
            order_doctor_rv.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyTv.setText("当天暂无医生排班");
        }

    }

    @Override
    public void getDoctorListFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "获取医生列表失败：" + errMsg);
        order_doctor_rv.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("数据获取失败，点击重试");
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().queryDateList(SelectedDepartmentId);
            }
        });
    }

    @Override
    public long getSelectedDepartmentId() {
        return SelectedDepartmentId;
    }

    @Override
    public void getDepartmentSuceess(List<HospitaldepartmentsInfo> dataList) {
        departmentList = dataList;
        if (departmentList != null && departmentList.size() != 0)
            showChooseDepartmentDialog();
        else
            ToastUtil.shortToast(getContext(),"暂无更多科室");
    }

    @Override
    public void getDepartmentFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "获取科室列表失败：" + errMsg);
    }
}
