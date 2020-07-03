package com.keydom.mianren.ih_patient.activity.order_doctor_register;

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
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.controller.ChooseDoctorController;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.ChooseDoctorView;
import com.keydom.mianren.ih_patient.adapter.DoctorAdapter;
import com.keydom.mianren.ih_patient.adapter.OrderExtDateAdapter;
import com.keydom.mianren.ih_patient.bean.DateInfo;
import com.keydom.mianren.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.mianren.ih_patient.bean.ReserveSelectDepartBean;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;

import org.greenrobot.eventbus.EventBus;
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
    public static final String IS_SELECT = "is_select";

    /**
     * 启动
     */
    public static void start(Context context, long areaId, String areaName, long departmentId,
                             String departmentName, List<HospitaldepartmentsInfo> departmentList) {
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

    public static void start(Context context, ReserveSelectDepartBean bean, String selectedDate,
                             long doctorId,
                             boolean isSelect) {
        Intent intent = new Intent(context, ChooseDoctorActivity.class);
        intent.putExtra(IS_SELECT, isSelect);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("areaId", bean.getSelectedAreaId());
        intent.putExtra("areaName", bean.getSelectedAreaName());
        intent.putExtra("departmentId", bean.getId());
        intent.putExtra("departmentName", bean.getSecondDepartmentName());
        Bundle bundle = new Bundle();
        bundle.putSerializable("departmentList", (Serializable) bean.getDepartmentList());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TextView chosed_department_tv, tvRightComplete, tvSelectComplete,
            selected_hospital_area_tv, search_doctor_tv, emptyTv;
    private LinearLayout llLeftGoBack, dateLayout, llRightComplete;
    private RelativeLayout emptyLayout;
    private RecyclerView order_doctor_date_rv, order_doctor_rv;
    private OrderExtDateAdapter orderExtDateAdapter;
    private List<DateInfo> dateList = new ArrayList<>();
    private List<HospitaldepartmentsInfo> departmentList;
    private DoctorAdapter doctorAdapter;
    private List<DepartmentSchedulingBean> doctorList = new ArrayList<>();
    private long SelectedDepartmentId, areaId, doctorId;
    private String departmentName, areaName, selectedDate;
    private boolean isSelect;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_doctor_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isSelect = getIntent().getBooleanExtra(IS_SELECT, false);
        doctorId = getIntent().getLongExtra("doctorId", -1);
        selectedDate = getIntent().getStringExtra("selectedDate");
        areaId = getIntent().getLongExtra("areaId", -1);
        areaName = getIntent().getStringExtra("areaName");
        SelectedDepartmentId = getIntent().getLongExtra("departmentId", -1);
        departmentName = getIntent().getStringExtra("departmentName");
        departmentList = (List<HospitaldepartmentsInfo>) getIntent().getSerializableExtra(
                "departmentList");
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
        tvSelectComplete = this.findViewById(R.id.tvSelectComplete);

        selected_hospital_area_tv = this.findViewById(R.id.selected_hospital_area_tv);
        if (isSelect) {
            tvRightComplete.setVisibility(View.GONE);
            dateLayout.setVisibility(View.GONE);
            selected_hospital_area_tv.setVisibility(View.GONE);
            tvSelectComplete.setVisibility(View.VISIBLE);
            getController().getDoctorList(selectedDate, SelectedDepartmentId);
        } else {
            tvRightComplete.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.VISIBLE);
            selected_hospital_area_tv.setVisibility(View.VISIBLE);
            tvSelectComplete.setVisibility(View.GONE);
            if (Global.getHospitalAreaInfo() != null) {
                selected_hospital_area_tv.setText("当前为" + Global.getHospitalAreaInfo().getHospitalName() + "-" + areaName);
            } else {
                selected_hospital_area_tv.setText("当前为" + App.hospitalName + "-" + areaName);
            }
            //排班日期
            order_doctor_date_rv = this.findViewById(R.id.order_doctor_date_rv);
            order_doctor_date_rv.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            orderExtDateAdapter = new OrderExtDateAdapter(getContext(), dateList,
                    new GeneralCallback.dateAdapterCallBack() {
                        @Override
                        public void getSelectedDate(int position) {
                            if (dateList.size() != 0 && dateList != null) {
                                selectedDate = dateList.get(position).getDate();
                                getController().getDoctorList(selectedDate, SelectedDepartmentId);
                            }
                        }
                    });
            order_doctor_date_rv.setAdapter(orderExtDateAdapter);
        }
        llLeftGoBack = this.findViewById(R.id.llLeftGoBack);
        llLeftGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_doctor_tv = this.findViewById(R.id.search_doctor_tv);
        search_doctor_tv.setOnClickListener(getController());
        llRightComplete = findViewById(R.id.llRightComplete);
        llRightComplete.setOnClickListener(getController());

        order_doctor_rv = this.findViewById(R.id.order_doctor_rv);
        doctorAdapter = new DoctorAdapter(getContext(), doctorList);
        doctorAdapter.setCurDoctorId(doctorId);
        doctorAdapter.setSelect(isSelect);
        order_doctor_rv.setAdapter(doctorAdapter);
        if (!isSelect) {
            getController().queryDateList(SelectedDepartmentId);
        }
    }

    /**
     * 选择科室弹框
     */
    private void showChooseDepartmentDialog() {
        List<String> parentDepList = new ArrayList<>();
        final List<List<String>> childDepList = new ArrayList<>();
        for (int i = 0; i < departmentList.size(); i++) {
            if (departmentList.get(i).getName().length() > 6) {
                parentDepList.add(departmentList.get(i).getName().substring(0, 2) + "..." + departmentList.get(i).getName().substring(departmentList.get(i).getName().length() - 3, departmentList.get(i).getName().length()));
            } else {
                parentDepList.add(departmentList.get(i).getName());
            }

            List<String> parendChildList = new ArrayList<>();
            for (int j = 0; j < departmentList.get(i).getHdList().size(); j++) {
                if (departmentList.get(i).getHdList().get(j).getName().length() > 10) {
                    parendChildList.add(departmentList.get(i).getHdList().get(j).getName().substring(0, 4) + "..." + departmentList.get(i).getHdList().get(j).getName().substring(departmentList.get(i).getHdList().get(j).getName().length() - 3, departmentList.get(i).getHdList().get(j).getName().length()));
                } else {
                    parendChildList.add(departmentList.get(i).getHdList().get(j).getName());

                }
            }
            childDepList.add(parendChildList);
        }
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getContext(),
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        chosed_department_tv.setText(childDepList.get(options1).get(option2));
                        SelectedDepartmentId =
                                departmentList.get(options1).getHdList().get(option2).getId();
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
                if (isSelect) {
                    dateLayout.setVisibility(View.GONE);
                } else {
                    dateLayout.setVisibility(View.VISIBLE);
                }
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
        ToastUtil.showMessage(getContext(), "获取排班日期失败：" + errMsg);
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
        ToastUtil.showMessage(getContext(), "获取医生列表失败：" + errMsg);
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
            ToastUtil.showMessage(getContext(), "暂无更多科室");
    }

    @Override
    public void getDepartmentFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "获取科室列表失败：" + errMsg);
    }

    @Override
    public boolean isSelect() {
        return isSelect;
    }

    @Override
    public void defineSelect() {
        int position = doctorAdapter.getCurPosition();
        if (position == -1) {
            ToastUtil.showMessage(this, R.string.txt_select_doctor_hint);
        } else {
            DoctorInfo doctorInfo = doctorList.get(position).getHUser();
            EventBus.getDefault().post(new Event(EventType.SELECT_DOCTOR, doctorInfo));
            finish();
        }
    }
}
