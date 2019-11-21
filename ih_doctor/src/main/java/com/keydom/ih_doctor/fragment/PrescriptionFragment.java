package com.keydom.ih_doctor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.PrescriptionRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.PrescriptionBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.fragment.controller.PrescriptionFragmentController;
import com.keydom.ih_doctor.fragment.view.PrescriptionFragmentView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PrescriptionFragment extends BaseControllerFragment<PrescriptionFragmentController> implements PrescriptionFragmentView {

    private RecyclerView communityAttentionRv;
    /**
     * 处方列表适配器
     */
    private PrescriptionRecyclrViewAdapter mAdapter;
    private RefreshLayout refreshLayout;
    /**
     * 处方列表数据
     */
    private List<PrescriptionBean> dataList = new ArrayList<>();
    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 搜索关键字
     */
    private String keyword = "";
    private String startCode;

    public static final PrescriptionFragment newInstance(TypeEnum type,String startCode) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        bundle.putString("startCode",startCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        startCode=getArguments().getString("startCode");

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_prescription;
    }


    @Override
    public void getDataSuccess(TypeEnum type, List<PrescriptionBean> prescriptions) {

        if (type == TypeEnum.REFRESH) {
            dataList.clear();
        }
        dataList.addAll(prescriptions);
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

    }

    @Override
    public void getDataFailed(String errMsg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        ToastUtil.showMessage(getContext(), errMsg);

    }

    @Override
    public TypeEnum getType() {
        return mType;
    }

    @Override
    public Map<String, Object> getListMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("state", getState());
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("keyword", keyword);
        return map;
    }

    @Override
    public String getStartCod() {
        return startCode;
    }

    private int getState() {
        int state = -2;
        switch (mType) {
            case CHECK_NOT_FINISH:
                state = -1;
                break;
            case CHECK_FINISH:
                state = 0;
                break;
            case CHECK_SEND:
               /* if (SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR) {
                    state = 1;
                } else {
                    state = 2;
                }*/
                state = 2;
                break;
            case CHECK_TIME_OUT:
                state = 4;
                break;
            case CHECK_RETURN:
                state = 0;
                break;
            case CHECK_PASS:
                state = 1;
                break;
        }
        return state;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mAdapter = new PrescriptionRecyclrViewAdapter(getContext(), mType, dataList,startCode);
        communityAttentionRv = (RecyclerView) getView().findViewById(R.id.community_attention_rv);
        refreshLayout = (RefreshLayout) getView().findViewById(R.id.refreshLayout);
        communityAttentionRv.setAdapter(mAdapter);
        communityAttentionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setOnRefreshListener(getController());
    }

    @Override
    public void lazyLoad() {
        getController().getData(TypeEnum.REFRESH);
//        refreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
//        getController().getData(TypeEnum.REFRESH);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.PRESCRIPTION_UPDATE) {
            keyword = messageEvent.getData() == null ? "" : (String) messageEvent.getData();
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
        } else if (messageEvent.getType() == EventType.SEARCH_PRESCRIPTION) {
            keyword = messageEvent.getData() == null ? "" : (String) messageEvent.getData();
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
        }
    }
}
