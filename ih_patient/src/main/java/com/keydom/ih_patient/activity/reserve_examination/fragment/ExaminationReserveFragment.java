package com.keydom.ih_patient.activity.reserve_examination.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_examination.controller.ExaminationReserveController;
import com.keydom.ih_patient.activity.reserve_examination.view.ExaminationReserveView;
import com.keydom.ih_patient.adapter.ExaminationReserveAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.constant.TypeEnum;
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
 * 产科住院
 */
public class ExaminationReserveFragment extends BaseControllerFragment<ExaminationReserveController> implements ExaminationReserveView {
    private String type;
    private SmartRefreshLayout containt_refresh;
    private RecyclerView containRv;
    private List<String> dataList = new ArrayList<>();
    private ExaminationReserveAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_examination_reserve;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        containt_refresh = view.findViewById(R.id.containt_refresh);
        containRv = view.findViewById(R.id.containt_rv);
        adapter = new ExaminationReserveAdapter(getContext(), dataList);
        containRv.setAdapter(adapter);
        Bundle bundle = getArguments();
        type = bundle.getString("type");

        containt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (Type.INSPECT.equals(type)) {
                    getController().queryExaminationList("0", TypeEnum.REFRESH);
                } else {
                    getController().queryExaminationList("1", TypeEnum.REFRESH);
                }
            }
        });
        containt_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (Type.INSPECT.equals(type)) {
                    getController().queryExaminationList("0", TypeEnum.LOAD_MORE);
                } else {
                    getController().queryExaminationList("1", TypeEnum.LOAD_MORE);
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
    public void requestSuccess(List<String> dataList, TypeEnum typeEnum) {
        containt_refresh.finishLoadMore();
        containt_refresh.finishRefresh();
        pageLoadingSuccess();
        if (dataList.size() != 0) {

            if (typeEnum == TypeEnum.REFRESH) {
                this.dataList.clear();
            }
            this.dataList.addAll(dataList);
            adapter.notifyDataSetChanged();
            getController().currentPagePlus();
        }
    }

    @Override
    public void requestFailed(String errMsg) {
        containt_refresh.finishLoadMore();
        containt_refresh.finishRefresh();
        ToastUtil.showMessage(getContext(), errMsg);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event) {
        if (event.getType() == EventType.DOCTORREGISTERORDERPAYED) {
            containt_refresh.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
