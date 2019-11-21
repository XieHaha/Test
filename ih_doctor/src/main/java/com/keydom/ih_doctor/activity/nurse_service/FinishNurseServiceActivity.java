package com.keydom.ih_doctor.activity.nurse_service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.FinishNurseServiceController;
import com.keydom.ih_doctor.activity.nurse_service.view.FinishNurseServiceView;
import com.keydom.ih_doctor.adapter.NurseServiceDrugAdapter;
import com.keydom.ih_doctor.bean.DetailEquipment;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.MaterialBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class FinishNurseServiceActivity extends BaseControllerActivity<FinishNurseServiceController> implements FinishNurseServiceView {
    /**
     * 接口请求的预约时间
     */
    private String nextAppointTimeStr = "";
    /**
     * 显示用的预约时间
     */
    private String nextPeriodAppointTimeStr = "";
    /**
     * 费用
     */
    private BigDecimal fee;
    private RecyclerView finishNurseServiceDragRv;
    private NurseServiceDrugAdapter nurseServiceDrugAdapter;
    private CardView nurseServiceAddMedicine;
    private TextView nextVisitTime, totalFee, finishNurseInfo;
    private EditText currentNurseServiceInput;
    private RelativeLayout nextVisitRl;
    private HeadNurseServiceOrderDetailBean baseInfo;
    private LinearLayout serviceItemBox;
    private Button finishServiceBt;
    private List<DetailEquipment> detailEquipments = new ArrayList<>();
    private List<MaterialBean> materialBeans = new ArrayList<>();
    private Map<String, Boolean> checkMap = new HashMap<>();


    /**
     * 启动完成服务页面
     *
     * @param context
     * @param bean    订单基本信息
     */
    public static void start(Context context, HeadNurseServiceOrderDetailBean bean) {
        Intent starter = new Intent(context, FinishNurseServiceActivity.class);
        starter.putExtra(Const.DATA, (Serializable) bean);
        ((Activity) context).startActivityForResult(starter, Const.FINISH_ACTIVITY);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_finish_nurse_service;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        baseInfo = (HeadNurseServiceOrderDetailBean) getIntent().getSerializableExtra(Const.DATA);
        setTitle("完成服务");
        finishNurseServiceDragRv = this.findViewById(R.id.current_nurse_service_material_rv);
        serviceItemBox = this.findViewById(R.id.service_item_box);
        nurseServiceAddMedicine = this.findViewById(R.id.nurse_service_add_medicine);
        nextVisitTime = this.findViewById(R.id.next_visit_time);
        nextVisitRl = this.findViewById(R.id.next_visit_rl);
        totalFee = this.findViewById(R.id.total_fee);
        finishServiceBt = this.findViewById(R.id.finish_service_bt);
        finishServiceBt.setOnClickListener(getController());
        finishNurseInfo = this.findViewById(R.id.finish_nurse_info);
        currentNurseServiceInput = this.findViewById(R.id.current_nurse_service_input);
        nurseServiceAddMedicine.setOnClickListener(getController());
        nextVisitTime.setOnClickListener(getController());
        initAdapter();
        setInfo();
        if (baseInfo.getFrequency() > baseInfo.getServiceFrequency()) {
            nextVisitRl.setVisibility(View.VISIBLE);
        } else {
            nextVisitRl.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {
        nurseServiceDrugAdapter = new NurseServiceDrugAdapter(materialBeans, this);
        finishNurseServiceDragRv.setAdapter(nurseServiceDrugAdapter);
        finishNurseServiceDragRv.setNestedScrollingEnabled(false);
        finishNurseServiceDragRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void timeChoose(String date, String start, String end) {
        nextAppointTimeStr = date;
        nextPeriodAppointTimeStr = start + "-" + end;
        nextVisitTime.setText(date + " " + start + "-" + end);
    }

    /**
     * 设置基本信息
     */
    private void setInfo() {
        String baseInfoStr = "";
        for (int i = 0; i < baseInfo.getServiceName().size(); i++) {
            checkMap.put(baseInfo.getServiceName().get(i), true);
            addItemView(baseInfo.getServiceName().get(i));
            baseInfoStr = (i == 0) ? baseInfoStr + baseInfo.getServiceName().get(i) : baseInfoStr + "、" + baseInfo.getServiceName().get(i);
        }
        baseInfoStr = baseInfoStr + "(" + baseInfo.getServiceObject() + "  " + CommonUtils.getSex(baseInfo.getPatientSex()) + "  " + baseInfo.getPatientAge() + "岁)";
        finishNurseInfo.setText(baseInfoStr);
    }

    /**
     * 添加服务项目布局
     *
     * @param name
     */
    private void addItemView(final String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.current_service_item_tag, null, true);
        CheckBox tagTv = view.findViewById(R.id.name);
        tagTv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMap.put(name, isChecked);
            }
        });
        tagTv.setText(name);
        serviceItemBox.addView(view);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public Map<String, Object> getFinishMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("detailEquipment", detailEquipments);
        map.put("orderNumber", baseInfo.getOrderNumber());
        map.put("nextAppointTime", nextAppointTimeStr);
        map.put("nextPeriodAppointTime", nextPeriodAppointTimeStr);
        map.put("mark", currentNurseServiceInput.getText().toString());
        map.put("userId", MyApplication.userInfo.getId());
        map.put("fee", fee);
        return map;
    }

    @Override
    public void finishSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void finishFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public List<MaterialBean> getSelectMaterial() {
        return materialBeans;
    }

    @Subscribe
    public void getMaterialList(Event event) {
        if (event.getType() == EventType.CHOOSE_MATERIAL_USE) {
            List<MaterialBean> list = (List<MaterialBean>) event.getData();
            materialBeans.clear();
            materialBeans.addAll(list);
            nurseServiceDrugAdapter.notifyDataSetChanged();
            setFee();
        }
    }

    /**
     * 设置项目费用
     */
    private void setFee() {
        fee = BigDecimal.ZERO;
        detailEquipments.clear();
        for (MaterialBean bean : materialBeans) {
            DetailEquipment detailEquipment = new DetailEquipment();
            detailEquipment.setEquipmentName(bean.getName());
            detailEquipment.setQuantity(bean.getMaterialAmount());
            detailEquipment.setDescription(bean.getSpec());
            detailEquipment.setPrice(bean.getUnivalent());
            detailEquipment.setUnitName(bean.getUnitName());
            detailEquipments.add(detailEquipment);
            fee = fee.add(bean.getUnivalent().multiply(new BigDecimal(bean.getMaterialAmount())));

        }
        totalFee.setText("¥" + fee.toString());
    }

    @Override
    public boolean finishCheck() {
        if (!checkMap.containsValue(true)) {
            ToastUtil.showMessage(this, "请选择本次服务内容！");
            return false;
        }
        if (baseInfo.getFrequency() > baseInfo.getServiceFrequency()) {
            if (nextVisitTime.getText().toString() == null || "".equals(nextVisitTime.getText().toString())) {
                ToastUtil.showMessage(this, "请选择下次预约时间！");
                return false;
            }
        }

        if (currentNurseServiceInput.getText().toString() == null || "".equals(currentNurseServiceInput.getText().toString())) {
            ToastUtil.showMessage(this, "请输入本次护理内容！");
            return false;
        }

        return true;
    }
}
