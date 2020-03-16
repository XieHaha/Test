package com.keydom.mianren.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.PhysicalExaCommentsAdapter;
import com.keydom.mianren.ih_patient.bean.PhysicalExaCommentsInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.controller.PhysicalExaCommentsController;
import com.keydom.mianren.ih_patient.fragment.view.PhysicalExaCommentsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐页面
 */
public class PhysicalExaCommentsFragment extends BaseControllerFragment<PhysicalExaCommentsController> implements PhysicalExaCommentsView {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_physical_exa_comments_layout;
    }
    private SmartRefreshLayout physical_exa_refresh;
    private RecyclerView physical_exa_rv;
    private PhysicalExaCommentsAdapter physicalExaCommentsAdapter;
    private List<PhysicalExaCommentsInfo> dataList=new ArrayList<>();
    private int pageNum=0;
    private String type=Type.PHYSICALEXACOMMENTSRELOAD;
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        physical_exa_refresh=view.findViewById(R.id.physical_exa_refresh);
        physical_exa_rv=view.findViewById(R.id.physical_exa_rv);
        physicalExaCommentsAdapter=new PhysicalExaCommentsAdapter(getContext(),dataList);
        physical_exa_rv.setAdapter(physicalExaCommentsAdapter);
        Map<String,Object> map=new HashMap<>();
        map.put("medicalReservationId",Global.getSelectedPhysicalExa().getId());
        map.put("currentPage",pageNum);
        map.put("pageSize",8);
        map.put("registerUserId",Global.getUserId());
        getController().findCommentsList(map);
        physical_exa_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                type=Type.PHYSICALEXACOMMENTSRELOAD;
                Map<String,Object> map=new HashMap<>();
                map.put("medicalReservationId",Global.getSelectedPhysicalExa().getId());
                map.put("currentPage",pageNum);
                map.put("pageSize",8);
                map.put("registerUserId",Global.getUserId());
                getController().findCommentsList(map);

            }
        });
        physical_exa_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                type=Type.PHYSICALEXACOMMENTSLOADMORE;
                pageNum++;
                Map<String,Object> map=new HashMap<>();
                map.put("medicalReservationId",Global.getSelectedPhysicalExa().getId());
                map.put("currentPage",pageNum);
                map.put("pageSize",8);
                map.put("registerUserId",Global.getUserId());
                getController().findCommentsList(map);
            }
        });
    }

    @Override
    public void updateCommentsListSuccess(List<PhysicalExaCommentsInfo> dataList) {
        if(type.equals(Type.PHYSICALEXACOMMENTSRELOAD)){
            physical_exa_refresh.finishRefresh();
            this.dataList.clear();
            this.dataList.addAll(dataList);
            physicalExaCommentsAdapter.notifyDataSetChanged();

        }else {
            physical_exa_refresh.finishLoadMore();
            this.dataList.addAll(dataList);
            physicalExaCommentsAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void updateCommentsListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),"获取评论失败："+errMsg);
    }
}
