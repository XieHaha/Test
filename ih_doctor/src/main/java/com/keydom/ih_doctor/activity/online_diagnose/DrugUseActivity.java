package com.keydom.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.DrugUseAdapter;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.DrugListBean;
import com.keydom.ih_doctor.bean.DrugUseConfigBean;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.PrescriptionService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/7 上午10:46
 * <p>
 * Changes (from 19/3/7)
 * <p>
 * 19/3/7 : Create DrugUseActivity.java (song);
 */
public class DrugUseActivity extends BaseActivity {

    /**
     * 药品列表
     */
    private List<DrugBean> selectList;
    private DrugListBean drugListBean;
    private RecyclerView recyclerView;
    /**
     * 药品用法适配器
     */
    private DrugUseAdapter drugUseAdapter;
    private TextView saveDrug;

    /**
     * 启动药品规格、用法设置页面
     *
     * @param context
     * @param drugListBean    选中的药品
     */
    public static void start(Context context, DrugListBean drugListBean) {
        Intent intent = new Intent(context, DrugUseActivity.class);
        intent.putExtra(Const.DATA, (Serializable) drugListBean);
        context.startActivity(intent);
    }



    @Override
    public int getLayoutRes() {
        return R.layout.activity_medicine_dosage_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        saveDrug = this.findViewById(R.id.save_drug);
        drugListBean = (DrugListBean) getIntent().getSerializableExtra(Const.DATA);
        selectList=drugListBean.getDrugList();
        setTitle("用法用量");
        recyclerView = findViewById(R.id.medicine_rv);
        getDrugUseConfit();

    }

    /**
     * 判断是否可以提交
     *
     * @return true 可以提交，false 不能提交
     */
    private boolean checkSubmit() {
        for (DrugBean bean : selectList) {
            if (bean.getDays() == 0 || bean.getQuantity() == 0 || bean.getSingleDose() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取药品用法等配置信息
     */
    private void getDrugUseConfit() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).drugsFrequencyList(), new HttpSubscriber<DrugUseConfigBean>(this, getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DrugUseConfigBean data) {
                setDrugList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);

            }
        });
    }

    /**
     * 设置药品配置列表
     *
     * @param bean 药品配置信息
     */
    private void setDrugList(DrugUseConfigBean bean) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        drugUseAdapter = new DrugUseAdapter(selectList, bean);
        recyclerView.setAdapter(drugUseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        saveDrug.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (checkSubmit()) {
                    Event event = new Event(EventType.CHOOSE_DRUG_LIST, drugListBean);
                    EventBus.getDefault().post(event);
                    ActivityUtils.finishActivity(DrugChooseActivity.class);
                    finish();
                } else {
                    ToastUtil.showMessage(DrugUseActivity.this, "请完善药品信息");
                }
            }
        });
    }

}
