package com.keydom.mianren.ih_patient.activity.logistic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.LogisticDetailAdapter;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEnum;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.infoEntity;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogisticDetailActivity extends BaseActivity {


    public static final String ID = "id";
    private LogisticDetailAdapter mLogisticDetailAdapter;
    private RecyclerView mRecyclerView;

    private SmartRefreshLayout indexRefresh;
    private String mId = null;
    private TextView mWallCode;
    private TextView mLogicCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWallCode = findViewById(R.id.tv_content);
        mLogicCompany = findViewById(R.id.tv_patient_content);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mId = getIntent().getExtras().getString(ID);
            mWallCode.setText(mId);
            getHttp(mId);
        }
        mRecyclerView = findViewById(R.id.recycler_title);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        mRecyclerView.setLayoutManager(layoutmanager);
        mLogisticDetailAdapter = new LogisticDetailAdapter(this);
        mRecyclerView.setAdapter(mLogisticDetailAdapter);

        indexRefresh = findViewById(R.id.index_refresh);

        indexRefresh.setEnableLoadMore(false);
        indexRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getHttp(mId);
                indexRefresh.finishRefresh();
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_logistic_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("物流详情");

    }

    /**
     * 获取物流列表接口
     *
     * @param id
     */
    public void getHttp(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("waybill", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getLogisticsList(map), new HttpSubscriber<LogisticsEntity>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable LogisticsEntity logisticsEntity) {
                mWallCode.setText(logisticsEntity.getWaybill());
                mLogicCompany.setText(logisticsEntity.getCarrier());


                Logger.e("uuuu=" + logisticsEntity);
                List<LogisticsEntity> logisticsEntityList = new ArrayList<>();

                //todo 物流能查到的节点
                if (!CommUtil.isEmpty(logisticsEntity.getInfoList())) {
                    LogisticsEntity new3 = new LogisticsEntity();

                    //new3.setTitle(getTitleValue(logisticsEntity.getStatus()));
                    new3.setTitle(logisticsEntity.getStatus().getName());
                    new3.setInfoList(logisticsEntity.getInfoList());
                    logisticsEntityList.add(new3);

                    Logger.e("3=" + logisticsEntityList);
                }
                //todo 添加已发货节点
                if (!CommUtil.isEmpty(logisticsEntity.getDeliveryTime())) {
                    infoEntity infoEntity = new infoEntity();
                    infoEntity.setAcceptTime(logisticsEntity.getOrderTime());
                    infoEntity.setAcceptStation("您的订单已从" + logisticsEntity.getDrugstore() + "发货");
                    List<infoEntity> logistics = new ArrayList<>();
                    logistics.add(infoEntity);

                    LogisticsEntity new2 = new LogisticsEntity();
                    new2.setTitle("已发货");
                    new2.setInfoList(logistics);
                    logisticsEntityList.add(new2);

                    Logger.e("2=" + logisticsEntityList);
                }
                //todo 添加已下单节点
                if (!CommUtil.isEmpty(logisticsEntity.getOrderTime())) {

                    infoEntity infoEntity = new infoEntity();
                    infoEntity.setAcceptTime(logisticsEntity.getOrderTime());
                    infoEntity.setAcceptStation("系统已为您匹配了最近药店为您配药、发药");
                    List<infoEntity> logistics = new ArrayList<>();
                    logistics.add(infoEntity);

                    LogisticsEntity new1 = new LogisticsEntity();
                    new1.setTitle("已下单");
                    new1.setInfoList(logistics);

                    logisticsEntityList.add(new1);
                    Logger.e("1=" + logisticsEntityList);
                }
                if (!CommUtil.isEmpty(logisticsEntityList)) {
                    refreshView(logisticsEntityList);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                Logger.e("msg=" + msg);
                Logger.e("code=" + code);
                ToastUtils.showShort(msg);
                // loadingFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    private void refreshView(List<LogisticsEntity> logisticsEntities) {
        Logger.e("4=" + logisticsEntities);
        mLogisticDetailAdapter.setList(logisticsEntities);
    }

    public String getTitleValue(int status) {
        String mTitle = "";
        switch (status) {
            case 0:
                mTitle = LogisticsEnum.ON_WAY.getName();
                break;
            case 1:
                mTitle = LogisticsEnum.TOOK_A.getName();
                break;
            case 2:
                mTitle = LogisticsEnum.QUESTION.getName();
                break;
            case 3:
                mTitle = LogisticsEnum.FINISH.getName();
                break;
            case 4:
                mTitle = LogisticsEnum.BACK_SIGN.getName();
                break;
            case 5:
                mTitle = LogisticsEnum.CITY_SEND.getName();
                break;
            case 6:
                mTitle = LogisticsEnum.BACK_GOODS.getName();

                break;
            case 7:
                mTitle = LogisticsEnum.TURN.getName();

                break;
        }
        return mTitle;
    }
}
