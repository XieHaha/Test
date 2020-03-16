package com.keydom.mianren.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.controller.RegisterSearchController;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.RegisterSearchView;
import com.keydom.mianren.ih_patient.adapter.RegisterDoctorSearchAdapter;
import com.keydom.mianren.ih_patient.bean.DoctorInfo;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索页面
 */
public class RegisterSearchActivity extends BaseControllerActivity<RegisterSearchController> implements RegisterSearchView {

    /**
     * 启动
     */
    public static void start(Context context, String type, long departmentId, String departmentName,List<HospitaldepartmentsInfo> departmentList) {
        Intent intent = new Intent(context, RegisterSearchActivity.class);
        intent.putExtra("departmentId", departmentId);
        intent.putExtra("departmentName", departmentName);
        intent.putExtra("type", type);
        Bundle bundle=new Bundle();
        bundle.putSerializable("departmentList", (Serializable) departmentList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private EditText search_edt;
    private TextView search_close_tv,search_empty_tv;
    private RecyclerView doctor_or_department_rv;
    private RegisterDoctorSearchAdapter registerDoctorSearchAdapter;
    private List<Object> dataList = new ArrayList<>();
    private String type;
    private long departmentId;
    private List<HospitaldepartmentsInfo> departmentList;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register_search_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().setVisibility(View.GONE);
        type = getIntent().getStringExtra("type");
        search_edt = this.findViewById(R.id.search_edt);
        search_close_tv = this.findViewById(R.id.search_close_tv);
        search_empty_tv=findViewById(R.id.search_empty_tv);
        search_close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doctor_or_department_rv = this.findViewById(R.id.doctor_or_department_rv);
        registerDoctorSearchAdapter = new RegisterDoctorSearchAdapter(dataList, this);
        doctor_or_department_rv.setAdapter(registerDoctorSearchAdapter);
        if (type.equals(Type.SEARCHDOCTOR)) {
            search_edt.setHint("搜索当前科室下医生");
            departmentId = getIntent().getLongExtra("departmentId", -1);
        } else {
            search_edt.setHint("搜索科室、医生");
            departmentId = getIntent().getLongExtra("departmentId", -1);
            departmentList= (List<HospitaldepartmentsInfo>) getIntent().getSerializableExtra("departmentList");
        }
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (type.equals(Type.SEARCHDOCTOR)) {
                    if (charSequence.toString() != null && !"".equals(charSequence.toString())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("hospitalId", App.hospitalId);
                        map.put("hospitalUserName", charSequence.toString());
                        map.put("deptId",departmentId);
                        getController().searchDoctor(map);
                    }
                } else {
                    if (charSequence.toString() != null && !"".equals(charSequence.toString())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("hospitalId", App.hospitalId);
                        map.put("areaId",Global.getHospitalAreaInfo().getId());
                        map.put("departmentOrDoctor", charSequence.toString());
                        getController().searchDoctorOrDepartments(map);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void getSearchDocAndDepSuccess(List<Object> dataList) {
        if(dataList!=null&&dataList.size()!=0){
            if(doctor_or_department_rv.getVisibility()==View.GONE){
                doctor_or_department_rv.setVisibility(View.VISIBLE);
                search_empty_tv.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            registerDoctorSearchAdapter.notifyDataSetChanged();
        }else {
            doctor_or_department_rv.setVisibility(View.GONE);
            search_empty_tv.setVisibility(View.VISIBLE);
            search_empty_tv.setText("未能查询到相应科室或者医生");
        }

    }

    @Override
    public void getSearchDocAndDepFailed(String Msg) {
        this.dataList.clear();
        registerDoctorSearchAdapter.notifyDataSetChanged();
        doctor_or_department_rv.setVisibility(View.GONE);
        search_empty_tv.setVisibility(View.VISIBLE);
        search_empty_tv.setText("查询失败");
        ToastUtil.showMessage(getContext(), "查询失败:"+Msg);
    }

    @Override
    public void getSearchDoctorSuccess(List<DoctorInfo> dataList) {
        if(dataList!=null&&dataList.size()!=0){
            if(doctor_or_department_rv.getVisibility()==View.GONE){
                doctor_or_department_rv.setVisibility(View.VISIBLE);
                search_empty_tv.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            registerDoctorSearchAdapter.notifyDataSetChanged();
        }else {
            doctor_or_department_rv.setVisibility(View.GONE);
            search_empty_tv.setVisibility(View.VISIBLE);
            search_empty_tv.setText("该科室下未能查询相应医生");
        }
    }

    @Override
    public void getSearchDoctorFailed(String Msg) {
        this.dataList.clear();
        registerDoctorSearchAdapter.notifyDataSetChanged();
        ToastUtil.showMessage(getContext(), "查询失败:"+Msg);
        search_empty_tv.setVisibility(View.VISIBLE);
        search_empty_tv.setText("查询失败");
        ToastUtil.showMessage(getContext(), "查询失败:"+Msg);
    }
    public List<HospitaldepartmentsInfo> getDepartmentList(){
        return departmentList;
    }
}
