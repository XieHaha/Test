package com.keydom.ih_patient.activity.order_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.controller.ExaminationDateChooseController;
import com.keydom.ih_patient.activity.order_examination.view.ExaminationDateChooseView;
import com.keydom.ih_patient.adapter.ExtOrderAdapter;
import com.keydom.ih_patient.adapter.HospitalAreaPopupWindowAdapter;
import com.keydom.ih_patient.adapter.OrderExtDateAdapter;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.SoruInfo;
import com.keydom.ih_patient.callback.GeneralCallback;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查预约页面
 */
public class ExaminationDateChooseActivity extends BaseControllerActivity<ExaminationDateChooseController> implements ExaminationDateChooseView {
    private LinearLayout date_list_layout;
    private RelativeLayout hospital_area_layout;
    private List<HospitalAreaInfo> areaList = new ArrayList<>();
    private PopupWindow hospitalAreapopupWindow;
    private RecyclerView dateRv, orderExaRv;
    private ExtOrderAdapter extOrderAdapter;
    private OrderExtDateAdapter orderExtDateAdapter;
    /**
     * 项目集合
     */
    private List<SoruInfo.ListBean> SoruListList = new ArrayList<>();
    /**
     * 日期集合
     */
    private List<DateInfo> dateList = new ArrayList<>();
    private String selectedDate,applyNumber;
    private TextView order_hospital_name_name, order_hospital_label_name, order_hospital_address_tv, surplus_of_exa_num_tv;
    private TextView empty_text;
    private RelativeLayout state_retry2;
    /**
     * 选中项目ID，选择地区ID
     */
    private long selectedProjectId, selectedAreaId;

    /**
     * 启动
     */
    public static void start(Context context,long inspectProjectId,String applyNumber){
        Intent intent=new Intent(context,ExaminationDateChooseActivity.class);
        intent.putExtra("inspectProjectId",inspectProjectId);
        intent.putExtra("applyNumber",applyNumber);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_examination_date_choose_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检查预约");
        selectedProjectId=getIntent().getLongExtra("inspectProjectId",-1);
        applyNumber=getIntent().getStringExtra("applyNumber");
        state_retry2=this.findViewById(R.id.state_retry2);
        empty_text=this.findViewById(R.id.empty_text);
        date_list_layout = this.findViewById(R.id.date_list_layout);
        surplus_of_exa_num_tv = this.findViewById(R.id.surplus_of_exa_num_tv);
        order_hospital_name_name = this.findViewById(R.id.order_hospital_name_name);
        order_hospital_label_name = this.findViewById(R.id.order_hospital_label_name);
        order_hospital_address_tv = this.findViewById(R.id.order_hospital_address_tv);

        hospital_area_layout = this.findViewById(R.id.hospital_area_layout);
        hospital_area_layout.setOnClickListener(getController());
        dateRv = this.findViewById(R.id.date_rv);
        dateRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        orderExtDateAdapter = new OrderExtDateAdapter(getContext(), dateList, new GeneralCallback.dateAdapterCallBack() {
            @Override
            public void getSelectedDate(int position) {
                if (dateList != null && dateList.size() != 0) {
                    selectedDate = dateList.get(position).getDate();
                    getController().queryfindSoruInfoList(selectedDate, selectedAreaId);
                }

            }
        });
        dateRv.setAdapter(orderExtDateAdapter);
        orderExaRv = this.findViewById(R.id.order_exa_rv);
        extOrderAdapter = new ExtOrderAdapter(this, SoruListList);
        orderExaRv.setAdapter(extOrderAdapter);
        getController().queryHospitalAreaList();
    }


    @Override
    public void showHospitalAreaPopupWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout, hospital_area_layout, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.popup_rv);
        View backgroudView = view.findViewById(R.id.backgroud_view);
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalAreapopupWindow.dismiss();
            }
        });
        HospitalAreaPopupWindowAdapter hospitalAreaPopupWindowAdapter = new HospitalAreaPopupWindowAdapter(getContext(), areaList, new GeneralCallback.hospitalAreaAdapterCallBack() {
            @Override
            public void commitInfo(Object object) {
                saveSelectArea((HospitalAreaInfo) object);
            }
        });
        recyclerView.setAdapter(hospitalAreaPopupWindowAdapter);
        hospitalAreapopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalAreapopupWindow.setContentView(view);
        hospitalAreapopupWindow.setFocusable(true);
        hospitalAreapopupWindow.setWidth(hospital_area_layout.getWidth());
        hospitalAreapopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroudView.setVisibility(View.INVISIBLE);
            }
        });
        hospitalAreapopupWindow.showAsDropDown(hospital_area_layout);
        backgroudView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getAreaList(List<HospitalAreaInfo> dataList) {
        areaList.clear();
        areaList.addAll(dataList);
        order_hospital_name_name.setText(dataList.get(0).getName());
        order_hospital_address_tv.setText(dataList.get(0).getAddress());
        order_hospital_label_name.setText(dataList.get(0).getLevel() + " | " + dataList.get(0).getNature());
        selectedAreaId = dataList.get(0).getId();
        getController().queryDateList(selectedAreaId);
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
        getController().queryDateList(selectedAreaId);
    }

    @Override
    public void getDateList(List<DateInfo> dateList) {
        if (dateList != null && dateList.size() != 0) {
            if(date_list_layout.getVisibility()==View.GONE){
                date_list_layout.setVisibility(View.VISIBLE);
                surplus_of_exa_num_tv.setVisibility(View.VISIBLE);
                orderExaRv.setVisibility(View.VISIBLE);
                state_retry2.setVisibility(View.GONE);
            }
            orderExtDateAdapter.refreshInit();
            this.dateList.clear();
            this.dateList.addAll(dateList);
            orderExtDateAdapter.notifyDataSetChanged();

        } else {
            date_list_layout.setVisibility(View.GONE);
            surplus_of_exa_num_tv.setVisibility(View.GONE);
            orderExaRv.setVisibility(View.GONE);
            state_retry2.setVisibility(View.VISIBLE);
            empty_text.setText("暂无检查排班数据");
        }

    }

    @Override
    public void getDateListFailed(String errMsg) {
        date_list_layout.setVisibility(View.GONE);
        surplus_of_exa_num_tv.setVisibility(View.GONE);
        orderExaRv.setVisibility(View.GONE);
        state_retry2.setVisibility(View.VISIBLE);
        ToastUtil.showMessage(getContext(), errMsg);
        empty_text.setText("获取检查排班数据失败，点击重试");
        state_retry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().queryDateList(selectedAreaId);
            }
        });
    }

    @Override
    public long getSelectedProjectId() {
        return selectedProjectId;
    }


    @Override
    public void getSoruInfo(SoruInfo soruInfo) {
        if (soruInfo.getList() != null && soruInfo.getList().size() != 0) {
            if (orderExaRv.getVisibility() == View.GONE) {
                surplus_of_exa_num_tv.setVisibility(View.GONE);
                orderExaRv.setVisibility(View.VISIBLE);
                state_retry2.setVisibility(View.GONE);
                surplus_of_exa_num_tv.setVisibility(View.VISIBLE);
            }
            surplus_of_exa_num_tv.setText(selectedDate + "余号：剩余" + soruInfo.getTotal() + "个");
            SoruListList.clear();
            SoruListList.addAll(soruInfo.getList());
            extOrderAdapter.notifyDataSetChanged();
        } else {
            surplus_of_exa_num_tv.setVisibility(View.GONE);
            orderExaRv.setVisibility(View.GONE);
            state_retry2.setVisibility(View.VISIBLE);
            empty_text.setText("当天并无检查项目");
        }

    }

    @Override
    public void getSoruInfoFailed(String errMsg) {
        if (surplus_of_exa_num_tv.getVisibility() == View.VISIBLE) {
            surplus_of_exa_num_tv.setVisibility(View.GONE);
            orderExaRv.setVisibility(View.GONE);
            state_retry2.setVisibility(View.VISIBLE);
            empty_text.setText("数据加载错误，请重试");

        }
    }

    @Override
    public void completeOrder(ExaminationInfo data) {
        String projectDetail="";
        projectDetail=data.getName() + "\n  " + data.getSubclassName() + "\n  " + data.getAppointmentTime() + " " + data.getCheckTime() + " \n  " + data.getNumber() + " \n  " + data.getHospitalAreaName() + " \n  " + data.getCheckSite();
        ExaminationOrderCompleteActivity.start(getContext(),data.getProjectName(),projectDetail);
        finish();
    }

    @Override
    public void completeOrderFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "预约失败" + errMsg);
        getController().queryfindSoruInfoList(selectedDate, selectedAreaId);
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

}
