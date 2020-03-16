package com.keydom.mianren.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.controller.OrderDoctorRegisterController;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.OrderDoctorRegisterView;
import com.keydom.mianren.ih_patient.adapter.HospitalAreaPopupWindowAdapter;
import com.keydom.mianren.ih_patient.adapter.OrderDoctorRegisterAdapter;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.Global;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约订单页面
 */
public class OrderDoctorRegisterActivity extends BaseControllerActivity<OrderDoctorRegisterController> implements OrderDoctorRegisterView {
    public static final String IS_SELECT = "is_select";

    /**
     * 启动
     */
    public static void start(Context context, boolean isSelect) {
        Intent intent = new Intent(context, OrderDoctorRegisterActivity.class);
        intent.putExtra(IS_SELECT, isSelect);
        context.startActivity(intent);
    }

    private RecyclerView order_doctor_nurses_rv;
    private OrderDoctorRegisterAdapter orderDoctorRegisterAdapter;
    private List<Object> dataList = new ArrayList<>();
    private List<HospitalAreaInfo> areaList = new ArrayList<>();
    private List<HospitaldepartmentsInfo> departmentList = new ArrayList<>();
    private long selectedAreaId;
    private String selectedAreaName;
    private RelativeLayout top_layout, emptyLayout, hospital_area_layout, maxSearchLayout;
    private LinearLayout search_layout, llLeftGoBack, llRightComplete;
    private TextView order_hospital_name_name, order_hospital_label_name,
            order_hospital_address_tv, emptyTv;
    private PopupWindow hospitalAreapopupWindow;

    private boolean isSelect;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_doctor_register_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            isSelect = getIntent().getBooleanExtra(IS_SELECT, false);
        }
        order_hospital_name_name = this.findViewById(R.id.order_hospital_name_name);
        order_hospital_label_name = this.findViewById(R.id.order_hospital_label_name);
        order_hospital_address_tv = this.findViewById(R.id.order_hospital_address_tv);
        hospital_area_layout = findViewById(R.id.hospital_area_layout);
        emptyLayout = findViewById(R.id.state_retry2);
        emptyTv = findViewById(R.id.empty_text);
        top_layout = this.findViewById(R.id.top_layout);
        top_layout.setOnClickListener(getController());
        order_doctor_nurses_rv = this.findViewById(R.id.order_doctor_nurses_rv);
        orderDoctorRegisterAdapter = new OrderDoctorRegisterAdapter(this, dataList);
        orderDoctorRegisterAdapter.setSelect(isSelect);
        order_doctor_nurses_rv.setAdapter(orderDoctorRegisterAdapter);
        maxSearchLayout = this.findViewById(R.id.max_search_layout);
        maxSearchLayout.setOnClickListener(getController());
        search_layout = this.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(getController());
        llRightComplete = findViewById(R.id.llRightComplete);
        llRightComplete.setOnClickListener(getController());
        llLeftGoBack = this.findViewById(R.id.llLeftGoBack);
        llLeftGoBack.setOnClickListener(view -> finish());
        findViewById(R.id.order_doctor_register_ai_consulting).setOnClickListener(v -> CommonDocumentActivity.start(getContext(), "智能导诊", "https://robot-lib-achieve.zuoshouyisheng.com/?app_id=5daebc4a9ea2ea355d2505d3"));

        initView();
        getController().queryHospitalAreaList();
    }

    /**
     * 不同入口 界面显示处理
     */
    private void initView() {
        if (isSelect) {
            hospital_area_layout.setVisibility(View.GONE);
            maxSearchLayout.setVisibility(View.VISIBLE);
        } else {
            maxSearchLayout.setVisibility(View.GONE);
            hospital_area_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void upLoadData(List<Object> dataList, List<HospitaldepartmentsInfo> departmentList) {
        if (departmentList.size() != 0) {
            if (order_doctor_nurses_rv.getVisibility() == View.GONE) {
                order_doctor_nurses_rv.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            Logger.e(dataList.size() + "");
            this.dataList.clear();
            this.dataList.addAll(dataList);
            orderDoctorRegisterAdapter.notifyDataSetChanged();
            this.departmentList.clear();
            this.departmentList.addAll(departmentList);
        } else {
            order_doctor_nurses_rv.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyTv.setText("查询不到数据");
        }

    }

    @Override
    public void getAreaList(List<HospitalAreaInfo> dataList) {
        if (dataList.size() != 0) {
            if (hospital_area_layout.getVisibility() == View.GONE) {
                if (isSelect) {
                    hospital_area_layout.setVisibility(View.GONE);
                } else {
                    hospital_area_layout.setVisibility(View.VISIBLE);
                }
                order_doctor_nurses_rv.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            top_layout.setClickable(true);
            areaList.clear();
            areaList.addAll(dataList);
            order_hospital_name_name.setText(dataList.get(0).getName());
            order_hospital_address_tv.setText(dataList.get(0).getAddress());
            order_hospital_label_name.setText(dataList.get(0).getLevel() + " | " + dataList.get(0).getNature());
            selectedAreaId = dataList.get(0).getId();
            selectedAreaName = dataList.get(0).getName();
            Global.setHospitalAreaInfo(dataList.get(0));
            getController().QueryDataList(getQueryAreaMap());
        } else {
            top_layout.setClickable(false);
            hospital_area_layout.setVisibility(View.GONE);
            order_doctor_nurses_rv.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyTv.setText("查询不到数据");
        }

    }

    @Override
    public void getAreaListFailed(String msg) {
        top_layout.setClickable(false);
        hospital_area_layout.setVisibility(View.GONE);
        order_doctor_nurses_rv.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("数据获取失败，点击重试");
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().queryHospitalAreaList();
            }
        });
    }

    @Override
    public void showHospitalAreaPopupWindow() {
        if (areaList != null && areaList.size() > 1) {
            View view =
                    LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout
                            , top_layout, false);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.popup_rv);
            View backgroudView = view.findViewById(R.id.backgroud_view);
            backgroudView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hospitalAreapopupWindow.dismiss();
                }
            });
            HospitalAreaPopupWindowAdapter hospitalAreaPopupWindowAdapter =
                    new HospitalAreaPopupWindowAdapter(getContext(), areaList,
                            new GeneralCallback.hospitalAreaAdapterCallBack() {
                                @Override
                                public void commitInfo(Object object) {
                                    saveSelectArea((HospitalAreaInfo) object);
                                }
                            });
            recyclerView.setAdapter(hospitalAreaPopupWindowAdapter);
            hospitalAreapopupWindow = new PopupWindow(getContext(), null,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            hospitalAreapopupWindow.setContentView(view);
            hospitalAreapopupWindow.setFocusable(true);
            hospitalAreapopupWindow.setWidth(top_layout.getWidth());
            hospitalAreapopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    backgroudView.setVisibility(View.INVISIBLE);
                }
            });
            hospitalAreapopupWindow.showAsDropDown(top_layout);
            backgroudView.setVisibility(View.VISIBLE);
        } else
            ToastUtil.showMessage(getContext(), "该医院只有一个院区，无法切换");

    }

    /**
     * 保存选择地区
     */
    public void saveSelectArea(HospitalAreaInfo hospitalAreaInfo) {
        order_hospital_name_name.setText(hospitalAreaInfo.getName());
        order_hospital_address_tv.setText(hospitalAreaInfo.getAddress());
        order_hospital_label_name.setText(hospitalAreaInfo.getLevel() + " | " + hospitalAreaInfo.getNature());
        selectedAreaId = hospitalAreaInfo.getId();
        hospitalAreapopupWindow.dismiss();
        Global.setHospitalAreaInfo(hospitalAreaInfo);
        getController().QueryDataList(getQueryAreaMap());
    }

    /**
     * 获取地区请求map
     */
    public Map<String, Object> getQueryAreaMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("hospitalAreaId", selectedAreaId);
        return map;
    }

    public long getSelectedAreaId() {
        return selectedAreaId;
    }

    public String getSelectedAreaName() {
        return selectedAreaName;
    }

    @Override
    public List<HospitaldepartmentsInfo> getDepartmentList() {
        return departmentList;
    }

    @Override
    public void getDepartmentFailed(String msg) {
        order_doctor_nurses_rv.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("数据获取失败，点击重试");
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().QueryDataList(getQueryAreaMap());
            }
        });
    }
}
