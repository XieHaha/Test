package com.keydom.mianren.ih_patient.activity.get_drug;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.GetDrugAdapter;
import com.keydom.mianren.ih_patient.bean.entity.GetDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.GetDrugEntity;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.orhanobut.logger.Logger;
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

public class GetDrugActivity extends BaseActivity {
    private EditText mEditText;
    private TextView mTextView, emptyTv;
    private RecyclerView mRecyclerView;
    private GetDrugAdapter mGetDrugAdapter;
    private List<GetDrugEntity> mDatas = new ArrayList<>();
    private String mName = "";
    SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private RelativeLayout emptyLayout;
    private int mPages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //???????????????
        getHttp(mName, page, 5);
        mEditText = findViewById(R.id.search_edt);
        mTextView = findViewById(R.id.search_tv);
        smartRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_get_drug);
        emptyLayout = findViewById(R.id.state_retry2);
        emptyTv = findViewById(R.id.empty_text);

        //??????RecyclerView ??????
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutmanager);
        mGetDrugAdapter = new GetDrugAdapter(this);
        mRecyclerView.setAdapter(mGetDrugAdapter);


        //item????????????
        mGetDrugAdapter.setOnItemClickListener(new GetDrugAdapter.OnItemClickListener() {
            @Override
            public void onClick(GetDrugEntity getDrugEntity) {
                GotoActivityUtil.gotoPrescriptionGetDetailActivity(GetDrugActivity.this, getDrugEntity.getPrescriptionId(),Integer.valueOf(getDrugEntity.getAcquireMedicine()));
            }
        });


        //??????
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mEditText.getText().toString();
                if (!CommUtil.isEmpty(value)) {
                    // TODO: 2019/5/6   ?????????????????????  ??????
                    mName = value;
                    page = 1;
                    getHttp(mName, page, 5);
                } else {
                    Toast.makeText(GetDrugActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //????????????
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getHttp(mName, page, 5);
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.resetNoMoreData();
            }
        });


        //??????????????????
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getHttp(mName, page, Const.PAGE_SIZE);
                Logger.e("eeee=" + mPages);
                Logger.e("yyy=" + mGetDrugAdapter.getItemCount());
                if (mPages == mGetDrugAdapter.getItemCount()) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    smartRefreshLayout.finishLoadMore();
                }

//                if (mDatas.size() < 5) {
//                    refreshLayout.finishLoadMoreWithNoMoreData();
//                } else {
//                    smartRefreshLayout.finishLoadMore();
//                }
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_get_drug;
    }

    @Override
    public void initController() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("????????????");
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param name
     * @param page
     * @param pageSize
     */
    public void getHttp(String name, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("current", page);
        map.put("size", pageSize);
        map.put("userId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getPrescriptionIdList(map), new HttpSubscriber<GetDrugBean>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable GetDrugBean data) {
                Logger.e("??????=" + data);
                data.getPages();
                if (!CommUtil.isEmpty(data.getRecords())) {
                    refreshView(data.getRecords());
                    getPages(data.getTotal());
                    emptyLayout.setVisibility(View.GONE);
                } else {
                    pageEmpty();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                loadingFail(msg);
                Logger.e("??????=" + msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    public void getPages(int pages) {
        mPages = pages;
    }

    /**
     * ??????Adapter?????????
     *
     * @param data
     */
    public void refreshView(List<GetDrugEntity> data) {
        if (!CommUtil.isEmpty(data)) {
            mDatas = data;
            if (page == 1) {
                mGetDrugAdapter.setList(data);
            } else {

                mGetDrugAdapter.addList(data);
            }
            page++;
        }

    }

    /**
     * ??????????????????????????????
     */
    public void loadingFail(String msg) {
        emptyLayout.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(msg)) {
            emptyTv.setText("??????????????????????????????");
        } else {
            emptyTv.setText(msg + "????????????");
        }
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttp(mName, page, 5);
            }
        });
    }
}

