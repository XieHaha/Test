package com.keydom.mianren.ih_patient.activity.location_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.location_manage.controller.LocationManageController;
import com.keydom.mianren.ih_patient.activity.location_manage.view.LocationManageView;
import com.keydom.mianren.ih_patient.adapter.LocationAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Type;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理页面
 */
public class LocationManageActivity extends BaseControllerActivity<LocationManageController> implements LocationManageView {
    private RecyclerView locationRv;
    private LocationAdapter locationAdapter;
    private BottomSheetDialog locationDialog;

    private DialogClickListener dialogClickListener;
    private List<LocationInfo> dataList=new ArrayList<>();
    private RelativeLayout comment_empty_layout;
    private TextView empty_text;
    //type 地址页面吧启动类型
    //Type.PAY_SELECT_ADDRESS 从支付页面启动
    //Type.STARTLOCATIONWITHRESULT 启动页面需要返回选中地址的
    //Type.STARTLOCATIONWITHOUTRESULT 启动页面不需要返回选中地址的
    //Type.WAI_PAY_SELECT_ADDRESS  外延处方去选择地址
    private String type;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_location_manage_layout;
    }

    /**
     * 启动方法
     */
    public static void start(Context context,String type){
        Intent intent=new Intent(context,LocationManageActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type=getIntent().getStringExtra("type");
        locationRv=this.findViewById(R.id.location_rv);
        locationRv.setLayoutManager(new LinearLayoutManager(getContext()));
        locationAdapter=new LocationAdapter(this,dataList,type);
        locationRv.setAdapter(locationAdapter);
        locationDialog=new BottomSheetDialog(getContext());
        locationDialog.setCancelable(false);
        locationDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.location_manage_add_layout, null);
        locationDialog.setContentView(view);
        comment_empty_layout=this.findViewById(R.id.comment_empty_layout);
        empty_text=this.findViewById(R.id.empty_text);
        setTitle(getString(R.string.location_manage_title));
        getTitleLayout().setRightTitle(getString(R.string.location_manage_add));
        getTitleLayout().initViewsVisible(true,true,true);
        getTitleLayout().setOnRightTextClickListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                AddLocationActivity.start(getContext(),"add_location");
            }
        });
        getController().initLocationList();
        EventBus.getDefault().register(getContext());
    }

    /**
     * 返回地址信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void retrunLocationInfo(Event event){
        if(event.getType()==EventType.RETURNLOCATIONINFO) {
            getController().initLocationList();
        }
    }

    @Override
    protected void onDestroy() {
        if(Type.STARTLOCATIONWITHRESULT.equals(type)){
            //EventBus.getDefault().post(new Event(EventType.CHECKLOCATIONISDELETED,null));
        }
        EventBus.getDefault().unregister(getContext());
        super.onDestroy();
    }

    @Override
    public void getLocationList(List<LocationInfo> dataList) {

        if(dataList!=null&&dataList.size()!=0){
            if(locationRv.getVisibility()==View.GONE){
                locationRv.setVisibility(View.VISIBLE);
                comment_empty_layout.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            locationAdapter.notifyDataSetChanged();
        }else {
            locationRv.setVisibility(View.GONE);
            comment_empty_layout.setVisibility(View.VISIBLE);
            empty_text.setText("还没有添加地址，请新建地址");
        }
    }

    @Override
    public void getLocationListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),errMsg);
        if(locationRv.getVisibility()==View.VISIBLE){
            locationRv.setVisibility(View.GONE);
            comment_empty_layout.setVisibility(View.VISIBLE);
            empty_text.setText("数据加载失败，点击重新加载");
            comment_empty_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getController().initLocationList();
                }
            });
        }
    }

    public interface DialogClickListener {
        void commit(LocationInfo locationInfo);
    }
    public void finishActivity(){
        finish();
    }
}
