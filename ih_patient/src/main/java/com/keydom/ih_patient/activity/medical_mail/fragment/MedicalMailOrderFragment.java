package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalOrderController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalOrderView;
import com.keydom.ih_patient.adapter.MedicalMailOrderAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.constant.TypeEnum;
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
 * 病案邮寄
 */
public class MedicalMailOrderFragment extends BaseControllerFragment<MedicalOrderController> implements MedicalOrderView {
    private String type;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<>();
    private MedicalMailOrderAdapter medicalMailOrderAdapter;
    private int page = 1;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        refreshLayout = view.findViewById(R.id.containt_refresh);
        recyclerView = view.findViewById(R.id.containt_rv);
        medicalMailOrderAdapter = new MedicalMailOrderAdapter(getContext(), dataList);
        recyclerView.setAdapter(medicalMailOrderAdapter);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        Logger.e("type=" + type);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                if (Type.NOTMEDICALMAILED.equals(type)) {
                    getController().queryMedicalNotMailed("0", TypeEnum.REFRESH);
                } else {
                    getController().queryMedicalMailed("1", TypeEnum.REFRESH);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (Type.NOTMEDICALMAILED.equals(type)) {
                    getController().queryMedicalNotMailed("0", TypeEnum.LOAD_MORE);
                } else {
                    getController().queryMedicalMailed("1", TypeEnum.LOAD_MORE);
                }
            }
        });
        EventBus.getDefault().register(this);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void lazyLoad() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void getMedicalMailedSuccess(List<String> dataList, TypeEnum typeEnum) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (dataList.size() != 0) {

            if (typeEnum == TypeEnum.REFRESH) {
                this.dataList.clear();
            }
            this.dataList.addAll(dataList);
            medicalMailOrderAdapter.notifyDataSetChanged();
            getController().currentPagePlus();
        }
    }

    @Override
    public void getMedicalMailedFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void getMedicalNotMailedSuccess(List<String> dataList, TypeEnum typeEnum) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getMedicalNotMailedFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event) {
        if (event.getType() == EventType.DOCTORREGISTERORDERPAYED) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
