package com.keydom.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.PrescriptionTempletDetailController;
import com.keydom.ih_doctor.activity.online_diagnose.view.PrescriptionTempletDetailView;
import com.keydom.ih_doctor.adapter.DrugTempletAdapter;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/7 上午10:51
 * <p>
 * Changes (from 19/3/7)
 * <p>
 * 19/3/7 : Create PrescriptionTempletDetailActivity.java (song);
 */
public class PrescriptionTempletDetailActivity extends BaseControllerActivity<PrescriptionTempletDetailController> implements PrescriptionTempletDetailView {
    private TextView templet_detail_name_tv;
    private RecyclerView recyclerView;
    /**
     * 模版ID
     */
    private long templetId;
    /**
     * 模版名称
     */
    private String templetName;
    /**
     * 药品列表
     */
    private List<DrugBean> drugBeans = new ArrayList<>();
    /**
     * 选中的药品列表
     */
    private List<DrugBean> selectDrugList = new ArrayList<>();
    /**
     * 药品列表适配器
     */
    private DrugTempletAdapter drugChooseAdapter;
    /**
     * 处方详情对象
     */
    private PrescriptionDrugDetailBean prescriptionDrugDetailBean;

    /**
     * 启动处方模版详情页面
     *
     * @param context
     * @param templetId   处方ID
     * @param templetName 处方名称
     */
    public static void start(Context context, long templetId, String templetName) {
        Intent intent = new Intent(context, PrescriptionTempletDetailActivity.class);
        intent.putExtra("templetId", templetId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_templet_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("处方模板");
        setRightTxt("确定");
        setRightBtnListener(v -> {
            List<List<DrugBean>> tempList=new ArrayList<>();
            tempList.add(drugChooseAdapter.getSelectList());
            prescriptionDrugDetailBean.setItems(tempList);
            Event event = new Event(EventType.CHOOSE_PRESCRIPTION_TEMPLET, prescriptionDrugDetailBean);
            EventBus.getDefault().post(event);
            ActivityUtils.finishActivity(PrescriptionTempletActivity.class);
            finish();
        });
        templet_detail_name_tv = findViewById(R.id.templet_detail_name_tv);
        recyclerView = findViewById(R.id.templet_detail_rv);
        templetId = getIntent().getLongExtra("templetId", -1);
        getController().getPrescriptionTemplateItemList(templetId);

    }

    /**
     * 初始化列表
     */
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        drugChooseAdapter = new DrugTempletAdapter(drugBeans, selectDrugList);
        recyclerView.setAdapter(drugChooseAdapter);
    }


    @Override
    public void getTempletDetailListSuccess(PrescriptionDrugDetailBean bean) {
        prescriptionDrugDetailBean = bean;
        templet_detail_name_tv.setText(bean.getName());
        drugBeans.clear();
        drugBeans.addAll(bean.getItems().get(0));
        selectDrugList = bean.getItems().get(0);
        initList();
    }

    @Override
    public void getTempletDetailListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }
}
