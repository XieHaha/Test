package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.RegisterRecordAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.fragment.controller.RegisterRecordContrller;
import com.keydom.ih_patient.fragment.view.RegisterRecordView;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂号订单页面
 */
public class RegisterRecordFragment extends BaseControllerFragment<RegisterRecordContrller> implements RegisterRecordView {
    private String type;
    private SmartRefreshLayout containt_refresh;
    private RecyclerView containt_rv;
    private List<RegistrationRecordInfo> dataList=new ArrayList<>();
    private RegisterRecordAdapter registerRecordAdapter;
    private int page=1;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        containt_refresh=view.findViewById(R.id.containt_refresh);
        containt_rv=view.findViewById(R.id.containt_rv);
        registerRecordAdapter=new RegisterRecordAdapter(getContext(),dataList);
        containt_rv.setAdapter(registerRecordAdapter);
        Bundle bundle=getArguments();
        type=bundle.getString("type");
        Logger.e("type="+type);

        containt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                if(Type.DONEREGISTRATIONRECORD.equals(type)){
                    getController().queryRegistrationRecordList("1", TypeEnum.REFRESH);
                }else {
                    getController().queryRegistrationRecordList("0", TypeEnum.REFRESH);
                }
            }
        });
        containt_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if(Type.DONEREGISTRATIONRECORD.equals(type)){
                    getController().queryRegistrationRecordList("1", TypeEnum.LOAD_MORE);
                }else {
                    getController().queryRegistrationRecordList("0", TypeEnum.LOAD_MORE);
                }
            }
        });
        EventBus.getDefault().register(this);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void lazyLoad() {
        containt_refresh.autoRefresh();
    }

    @Override
    public void getRegistrarionRecordListSuccess(List<RegistrationRecordInfo> dataList , TypeEnum typeEnum) {
        containt_refresh.finishLoadMore();
        containt_refresh.finishRefresh();
        pageLoadingSuccess();
        if(dataList.size()!=0){

            if (typeEnum == TypeEnum.REFRESH) {
                this.dataList.clear();
            }
            this.dataList.addAll(dataList);
            registerRecordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getRegistrarionRecordListFailed(String errMsg) {
        containt_refresh.finishLoadMore();
        containt_refresh.finishRefresh();
        ToastUtil.shortToast(getContext(),errMsg);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event){
        if(event.getType()==EventType.DOCTORREGISTERORDERPAYED){
            containt_refresh.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
