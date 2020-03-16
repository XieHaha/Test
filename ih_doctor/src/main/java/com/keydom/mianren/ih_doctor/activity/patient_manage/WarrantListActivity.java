package com.keydom.mianren.ih_doctor.activity.patient_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.patient_manage.controller.WarrantListController;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.WarrantListView;
import com.keydom.mianren.ih_doctor.adapter.WarrantListAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.WarrantDoctorBean;
import com.keydom.mianren.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WarrantListActivity extends BaseControllerActivity<WarrantListController> implements WarrantListView {

    public static void start(Context context){
        context.startActivity(new Intent(context,WarrantListActivity.class));
    }
   private RecyclerView warrantRv;
   private WarrantListAdapter warrantListAdapter;
   private List<WarrantDoctorBean> list=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_warrant_list_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("授权管理");
        setRightTxt("新增授权");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                TentativeDiagnosisActivity.start(getContext(),TentativeDiagnosisActivity.TYPECREAT,"","","");
            }
        });
        warrantRv=findViewById(R.id.warrant_list_rv);
        warrantListAdapter=new WarrantListAdapter(getContext(),list);
        warrantRv.setAdapter(warrantListAdapter);
        getController().getWarrantList();
        EventBus.getDefault().register(getContext());
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if (messageEvent.getType() == EventType.UPDATEWARRANTLIST) {
            getController().getWarrantList();
        }
    }
    @Override
    public void getWarrantListSuccess(List<WarrantDoctorBean> dataList) {
        list.clear();
        list.addAll(dataList);
        warrantListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWarrantListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),"列表获取失败"+errMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getContext());
    }
}
