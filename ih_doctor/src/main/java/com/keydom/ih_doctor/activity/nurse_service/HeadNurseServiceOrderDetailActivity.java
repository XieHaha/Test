package com.keydom.ih_doctor.activity.nurse_service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.HeadNurseServiceOrderDetailController;
import com.keydom.ih_doctor.activity.nurse_service.view.HeadNurseServiceOrderDetailView;
import com.keydom.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.utils.BaiduMapUtil;
import com.keydom.ih_doctor.view.DrivingRouteOverlay;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class HeadNurseServiceOrderDetailActivity extends BaseControllerActivity<HeadNurseServiceOrderDetailController> implements HeadNurseServiceOrderDetailView {

    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 订单ID
     */
    private String orderNum;
    private TextureMapView mMapView = null;
    private DrivingRouteOverlay overlay;
    private LinearLayout baseInfoLl, buttonLl;
    private ImageView spreadOut;
    private Button receiveBt, updateBt, returnBt;
    private RecyclerView diagnoseMaterialRv;
    private TextView distanceTv, nurseName, hospitalName, hospitalAddress, userPhone, orderNumTV, deptName, userName, userInfoPhone, useInfoAddress, serviceUser, contactPhoneNum, visitTime, serviceFee, serviceReqExplainTv;
    private DiagnoseOrderDetailAdapter diagnoseMaterialAdapter;
    private List<String> dataList = new ArrayList<>();
    private boolean isDept = false;
    private NurseBean selectNurseBean;
    private BaiduMapUtil mapUtil;
    private HeadNurseServiceOrderDetailBean baseInfo;
    private ImageView navigate, userPhoneIcon;

    /**
     * 护士长启动订单详情页面
     *
     * @param context
     * @param isDept   订单是否指定科室
     * @param type     订单类型
     * @param orderNum 订单ID
     */
    public static void start(Context context, boolean isDept, TypeEnum type, String orderNum) {
        Intent starter = new Intent(context, HeadNurseServiceOrderDetailActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.DATA, orderNum);
        starter.putExtra("isDept", isDept);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.service_order_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        orderNum = getIntent().getStringExtra(Const.DATA);
        isDept = getIntent().getBooleanExtra("isDept", false);
        setTitle("订单详情");
        userPhoneIcon = this.findViewById(R.id.user_phone_icon);
        userPhoneIcon.setOnClickListener(getController());
        navigate = this.findViewById(R.id.navigate);
        mMapView = this.findViewById(R.id.service_map);
        baseInfoLl = this.findViewById(R.id.base_info_ll);
        buttonLl = this.findViewById(R.id.button_ll);
        spreadOut = this.findViewById(R.id.head_nurse_spread_out);
        receiveBt = this.findViewById(R.id.receive_bt);
        returnBt = this.findViewById(R.id.return_bt);
        updateBt = this.findViewById(R.id.update_bt);
        diagnoseMaterialRv = this.findViewById(R.id.diagnose_material_rv);
        overlay = new DrivingRouteOverlay(mMapView.getMap());
        spreadOut.setOnClickListener(getController());
        receiveBt.setOnClickListener(getController());
        updateBt.setOnClickListener(getController());
        returnBt.setOnClickListener(getController());
        navigate.setOnClickListener(getController());

        if (mType == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER) {
            buttonLl.setVisibility(View.GONE);
        } else {
            if (!isDept) {
                buttonLl.setVisibility(View.VISIBLE);
            } else {
                buttonLl.setVisibility(View.VISIBLE);
                returnBt.setVisibility(View.VISIBLE);
            }
        }
        initView();
        pageLoading();
        getController().getHeadNurseServiceOrderDetail();
        mapUtil = new BaiduMapUtil(this, getController());
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getHeadNurseServiceOrderDetail();
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        distanceTv = this.findViewById(R.id.distance_tv);
        hospitalName = this.findViewById(R.id.hospital_name);
        hospitalAddress = this.findViewById(R.id.hospital_address);
        userPhone = this.findViewById(R.id.user_phone);
        orderNumTV = this.findViewById(R.id.order_num);
        deptName = this.findViewById(R.id.dept_name);
        userName = this.findViewById(R.id.user_name);
        userInfoPhone = this.findViewById(R.id.phone_num);
        useInfoAddress = this.findViewById(R.id.user_info_address);
        serviceUser = this.findViewById(R.id.service_user_info);
        contactPhoneNum = this.findViewById(R.id.contact_phone_num);
        visitTime = this.findViewById(R.id.visit_time);
        serviceFee = this.findViewById(R.id.service_fee);
        serviceReqExplainTv = this.findViewById(R.id.service_req_explain_tv);

        diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this, dataList);
        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseMaterialRv.setAdapter(diagnoseMaterialAdapter);
        diagnoseMaterialRv.setLayoutManager(diagnoseMaterialRvLm);

    }


    /**
     * 设置订单信息
     *
     * @param bean
     */
    private void setInfo(HeadNurseServiceOrderDetailBean bean) {
        hospitalName.setText(bean.getHospital());
        hospitalAddress.setText(bean.getServiceAddress());
        userPhone.setText(bean.getApplyPhone());
        userInfoPhone.setText(bean.getApplyPhone());
        contactPhoneNum.setText(bean.getApplyPhone());
        orderNumTV.setText(bean.getOrderNumber());
        deptName.setText(bean.getDeptName());
        userName.setText(bean.getUserName());
        useInfoAddress.setText(bean.getServiceAddress());
        serviceUser.setText(bean.getServiceObject() + " " + com.keydom.ih_common.utils.CommonUtils.getSex(bean.getPatientSex()) + " " + bean.getPatientAge() + "岁");
        visitTime.setText(bean.getTime());
        serviceFee.setText("¥" + bean.getReservationSet() + "元/次");
        serviceReqExplainTv.setText(bean.getConditionDesciption());
        if (bean.getConditionImage() != null) {
            String[] icons = bean.getConditionImage().split(",");
            if(icons.length==1){
                if(!"".equals(icons[0])){
                    dataList.add(icons[0]);
                }
            }else {
                for (int i = 0; i < icons.length; i++) {
                    dataList.add(icons[i]);
                }
            }

            diagnoseMaterialAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getDetailSuccess(HeadNurseServiceOrderDetailBean bean) {
        baseInfo = bean;
        setInfo(bean);
        setMapInfo();
        pageLoadingSuccess();
    }


    /**
     * 设置地图数据
     */
    private void setMapInfo() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            mapUtil.init().start();
                        }
                    }
                });
    }

    @Override
    public void getDetailFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public void backOrderSuccess(String msg) {
        ToastUtil.showMessage(this, "操作成功");
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATENURSENUM).build());
        finish();
    }

    @Override
    public void backOrderFailed(int code, String errMsg) {
        if (code == Const.RETURN_ERROR_ORDER_IS_ROB) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATENURSENUM).build());
            finish();
        }
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void receiveSuccess(String msg) {
        ToastUtil.showMessage(this, "操作成功");
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATENURSENUM).build());
        finish();
    }

    @Override
    public void receiveFailed(int code, String errMsg) {

        if (code == Const.RETURN_ERROR_ORDER_IS_ROB) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATENURSENUM).build());
            finish();
        }
        ToastUtil.showMessage(this, errMsg);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stop();
        mMapView.onDestroy();
    }


    @Override
    public TextureMapView getMapView() {
        return mMapView;
    }

    @Override
    public Map<String, Object> getDetailMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderNum);
        return map;
    }

    @Override
    public Map<String, Object> getReceiveMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderNum);
        map.put("nurseId", MyApplication.userInfo.getId());
        map.put("dispatcherId", selectNurseBean.getId());
        return map;
    }

    @Override
    public String getOrderNumber() {
        return orderNum;
    }


    @Override
    public DrivingRouteOverlay getOverlay() {
        return overlay;
    }

    @Override
    public void showAndHideBaseInfo() {
        if (baseInfoLl.getVisibility() == View.GONE) {
            baseInfoLl.setVisibility(View.VISIBLE);
            spreadOut.setImageDrawable(this.getResources().getDrawable(R.mipmap.arrow_top));
        } else {
            baseInfoLl.setVisibility(View.GONE);
            spreadOut.setImageDrawable(this.getResources().getDrawable(R.mipmap.arrow_bottom_grey));
        }

    }

    @Override
    public HeadNurseServiceOrderDetailBean getBaseInfo() {
        return baseInfo;
    }

    @Override
    public TextView getDistanceTv() {
        return distanceTv;
    }

    @Override
    public BaiduMapUtil getMapUtil() {
        return mapUtil;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.NURSE_SLEECT_ONLY_RESULT:
                    selectNurseBean = (NurseBean) data.getSerializableExtra(Const.DATA);
                    getController().setNurseName(selectNurseBean);
                    break;
                default:
            }
        }

    }

}
