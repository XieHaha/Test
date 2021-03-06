package com.keydom.mianren.ih_doctor.activity.nurse_service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.nurse_service.controller.CommonNurseServiceOrderDetailController;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.CommonNurseServiceOrderDetailView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_doctor.adapter.NurseServiceRecoderAdapter;
import com.keydom.mianren.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.NurseBean;
import com.keydom.mianren.ih_doctor.bean.NurseServiceRecoderBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.utils.BaiduMapUtil;
import com.keydom.mianren.ih_doctor.view.DrivingRouteOverlay;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name???com.keydom.ih_doctor.activity
 * @Author???song
 * @Date???18/11/19 ??????11:34
 * ????????????xusong
 * ???????????????18/11/19 ??????11:34
 */
public class CommonNurseServiceOrderDetailActivity extends BaseControllerActivity<CommonNurseServiceOrderDetailController> implements CommonNurseServiceOrderDetailView {

    /**
     * ??????
     */
    public static final int VISIT_GO = 0;
    /**
     * ??????
     */
    public static final int VISIT_ARRIVE = 1;

    /**
     * ??????????????????????????????
     */
    private List<String> diagnoseMaterialList = new ArrayList<>();
    /**
     * ???????????????
     */
    private NurseBean selectNurseBean;
    /**
     * ??????????????????????????????
     */
    private HeadNurseServiceOrderDetailBean baseInfo;
    /**
     * ??????????????????
     */
    private String orderNum;
    private String orderNumberStr;
    private TextureMapView mMapView = null;
    private DrivingRouteOverlay overlay;
    private LinearLayout baseInfoLl,button_ll,recive_button_ll;
    private RelativeLayout spreadOut;
    private TextView spreadOutTv;
    private Button changeOrderBt, receiveBt, goBt, arriveBt,cancleChangeOrderBt,refuse_change_order_bt,accept_receive_bt;
    private DiagnoseOrderDetailAdapter diagnoseMaterialAdapter;
    private RecyclerView diagnoseMaterialRv, serviceRecoderRv;
    private TextView hospitalAddress, distanceTv, hospitalName, userPhone, orderNumber, orderUserDept, orderUserName, orderUserPhone, orderUserAddress, orderServiceObject, orderUserContactNumber, orderVisitTime, orderFee, serviceReqExplainTv;
    private BaiduMapUtil mapUtil;
    private List<NurseServiceRecoderBean> nurseServiceRecoderBeanList = new ArrayList<>();
    private ImageView navigate, userPhoneIcon;
    private NurseServiceRecoderAdapter nurseServiceRecoderAdapter;

    /**
     * ?????????????????? ????????????????????????????????????????????????
     *
     * @param context
     * @param orderNum
     */
    public static void start(Context context, String orderNum,String orderNumberStr) {
        Intent starter = new Intent(context, CommonNurseServiceOrderDetailActivity.class);
        starter.putExtra(Const.DATA, orderNum);
        starter.putExtra("orderNumberStr",orderNumberStr);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.common_nurse_service_order_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        orderNum = getIntent().getStringExtra(Const.DATA);
        orderNumberStr=getIntent().getStringExtra("orderNumberStr");
        setTitle("????????????");
        initView();
        pageLoading();
        getController().getNurseServiceOrderDetail();
        mapUtil = new BaiduMapUtil(this, getController());
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getNurseServiceOrderDetail();
            }
        });
    }

    private void initView() {
        mMapView = this.findViewById(R.id.service_map);
        changeOrderBt = this.findViewById(R.id.change_order_bt);
        receiveBt = this.findViewById(R.id.receive_bt);
        goBt = this.findViewById(R.id.go_bt);
        arriveBt = this.findViewById(R.id.arrive_bt);
        spreadOut = this.findViewById(R.id.spread_out);
        baseInfoLl = this.findViewById(R.id.base_info_ll);
        spreadOutTv = this.findViewById(R.id.spread_out_tv);
        hospitalAddress = this.findViewById(R.id.hospital_address);
        overlay = new DrivingRouteOverlay(mMapView.getMap());
        diagnoseMaterialRv = this.findViewById(R.id.diagnose_material_rv);

        navigate = this.findViewById(R.id.navigate);
        userPhoneIcon = this.findViewById(R.id.user_phone_icon);
        userPhoneIcon.setOnClickListener(getController());
        navigate.setOnClickListener(getController());
        distanceTv = this.findViewById(R.id.distance_tv);
        hospitalName = this.findViewById(R.id.hospital_name);
        userPhone = this.findViewById(R.id.user_phone);
        orderNumber = this.findViewById(R.id.order_number);
        orderUserDept = this.findViewById(R.id.order_user_dept);
        orderUserName = this.findViewById(R.id.order_user_name);
        orderUserPhone = this.findViewById(R.id.order_user_phone);
        orderUserAddress = this.findViewById(R.id.order_user_address);
        orderServiceObject = this.findViewById(R.id.order_service_object);
        orderUserContactNumber = this.findViewById(R.id.order_user_contact_number);
        orderVisitTime = this.findViewById(R.id.order_visit_time);
        orderFee = this.findViewById(R.id.order_fee);
        serviceReqExplainTv = this.findViewById(R.id.service_req_explain_tv);
        serviceRecoderRv = this.findViewById(R.id.service_recoder_rv);
        nurseServiceRecoderAdapter = new NurseServiceRecoderAdapter(this, nurseServiceRecoderBeanList);
        LinearLayoutManager nurseServiceRecoderRvLm = new LinearLayoutManager(this);
        nurseServiceRecoderRvLm.setOrientation(LinearLayoutManager.VERTICAL);
        serviceRecoderRv.setAdapter(nurseServiceRecoderAdapter);
        serviceRecoderRv.setLayoutManager(nurseServiceRecoderRvLm);
        button_ll=this.findViewById(R.id.button_ll);
        cancleChangeOrderBt=this.findViewById(R.id.cancle_change_order_bt);
        recive_button_ll=this.findViewById(R.id.recive_button_ll);
        refuse_change_order_bt=this.findViewById(R.id.refuse_change_order_bt);
        accept_receive_bt=this.findViewById(R.id.accept_receive_bt);

        receiveBt.setOnClickListener(getController());
        goBt.setOnClickListener(getController());
        arriveBt.setOnClickListener(getController());
        changeOrderBt.setOnClickListener(getController());
        spreadOut.setOnClickListener(getController());
        cancleChangeOrderBt.setOnClickListener(getController());
        refuse_change_order_bt.setOnClickListener(getController());
        accept_receive_bt.setOnClickListener(getController());
        initAdapter();
    }


    @Override
    public TextureMapView getMapView() {
        return mMapView;
    }

    @Override
    public DrivingRouteOverlay getOverlay() {
        return overlay;
    }

    @Override
    public void showAndHideBaseInfo() {
        if (baseInfoLl.getVisibility() == View.GONE) {
            baseInfoLl.setVisibility(View.VISIBLE);
            spreadOutTv.setText("??????");
            Drawable img = this.getResources().getDrawable(R.mipmap.arrow_top_bule);
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            spreadOutTv.setCompoundDrawables(null, null, img, null);
        } else {
            baseInfoLl.setVisibility(View.GONE);
            spreadOutTv.setText("??????????????????");
            Drawable img = this.getResources().getDrawable(R.mipmap.arrow_bottom_bule);
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            spreadOutTv.setCompoundDrawables(null, null, img, null);
        }

    }

    @Override
    public String getOrderId() {
        return orderNum;
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
    public Map<String, Object> getAcceptReciveMap() {
        Map<String, Object> map=new HashMap<>();
        map.put("nurseId",MyApplication.userInfo.getId());
        map.put("orderNumber",orderNumberStr);
        map.put("operator","accept");
        return map;
    }

    @Override
    public Map<String, Object> getRefuseReciveMap() {
        Map<String, Object> map=new HashMap<>();
        map.put("nurseId",MyApplication.userInfo.getId());
        map.put("orderNumber",orderNumberStr);
        map.put("operator","refuse");
        return map;
    }

    @Override
    public Map<String, Object> getCancelChangeMap() {
        Map<String, Object> map=new HashMap<>();
        map.put("nurseId",MyApplication.userInfo.getId());
        map.put("orderNumber",orderNumberStr);
        return map;
    }

    @Override
    public void acceptOrderSuccess(String data) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        getController().getNurseServiceOrderDetail();
    }

    @Override
    public void refuseAcceptSuccess(String data) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        finish();
    }

    @Override
    public void cancelChangeSuccess(String data) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        getController().getNurseServiceOrderDetail();
    }

    private void initAdapter() {
        diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseMaterialList);
        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseMaterialRv.setAdapter(diagnoseMaterialAdapter);
        diagnoseMaterialRv.setLayoutManager(diagnoseMaterialRvLm);
    }

    /**
     * ??????????????????
     *
     * @param bean ??????????????????
     * @param transfered
     * @param needAccept
     */
    private void setInfo(HeadNurseServiceOrderDetailBean bean, boolean transfered, boolean needAccept) {
        if (bean == null) {
            ToastUtil.showMessage(this, "????????????");
            finish();
            return;
        }

        if(needAccept){
            button_ll.setVisibility(View.GONE);
            cancleChangeOrderBt.setVisibility(View.GONE);
            recive_button_ll.setVisibility(View.VISIBLE);
        }else {
            recive_button_ll.setVisibility(View.GONE);
            button_ll.setVisibility(View.VISIBLE);
            cancleChangeOrderBt.setVisibility(View.VISIBLE);

        }
        if(transfered){
            button_ll.setVisibility(View.GONE);
            cancleChangeOrderBt.setVisibility(View.VISIBLE);
        }else {
            button_ll.setVisibility(View.VISIBLE);
            cancleChangeOrderBt.setVisibility(View.GONE);
        }
        if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_ON_WAY) {
            showArriveView();
        } else if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_DOCTORACCEPT_NURSEUNACCEPT) {
            showChangeAndReceiveView();
        } else if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_ACCEPT) {
            showGoView();
        }


        hospitalAddress.setText(bean.getServiceAddress());
        hospitalName.setText(bean.getHospital());
        userPhone.setText(bean.getApplyPhone());
        orderNumber.setText(bean.getOrderNumber());
        orderUserName.setText(bean.getUserName());
        orderUserDept.setText(bean.getDeptName());
        orderUserPhone.setText(bean.getApplyPhone());
        orderUserAddress.setText(bean.getServiceAddress());
        orderServiceObject.setText(bean.getServiceObject() + " " + com.keydom.ih_common.utils.CommonUtils.getSex(bean.getPatientSex()) + " " + bean.getPatientAge() );
        orderVisitTime.setText(bean.getTime());
        orderUserContactNumber.setText(bean.getApplyPhone());
        orderFee.setText("??" + bean.getReservationSet() + "???/???");
        serviceReqExplainTv.setText(bean.getConditionDesciption());
        diagnoseMaterialList.addAll(CommonUtils.getImgList(bean.getConditionImage()));
        diagnoseMaterialAdapter.notifyDataSetChanged();
    }


    @Override
    public Map<String, Object> getDetailMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderNum);
        map.put("nurseId",MyApplication.userInfo.getId());
        return map;
    }

    @Override
    public Map<String, Object> getTransferNurseMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nurseId", selectNurseBean.getId());
        map.put("orderNumber", baseInfo.getOrderNumber());
        return map;
    }

    @Override
    public void getDetailSuccess(CommonNurseServiceOrderDetailBean bean) {
        pageLoadingSuccess();
        baseInfo = bean.getNursingServiceOrderDetailBaseDto();
        setInfo(bean.getNursingServiceOrderDetailBaseDto(),bean.isTransfered(),bean.isNeedAccept());
        nurseServiceRecoderBeanList.clear();
        nurseServiceRecoderBeanList.addAll(bean.getNurseServiceRecordDetailDtos());
        setMapInfo();
    }


    /**
     * ???????????????????????????->???????????????>?????????????????????
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
        if("????????????????????????".equals(errMsg)){
            ToastUtil.showMessage(getContext(),errMsg);
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
            finish();
        }
    }


    @Override
    public void receiveSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        showGoView();
    }

    @Override
    public void receiveFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void goSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        showArriveView();
    }

    @Override
    public void goFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void transferSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        finish();
    }

    @Override
    public void transferFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void arriveSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
        CommonNurseServiceWorkingOrderDetailActivity.start(this, TypeEnum.COMMON_NURSE_FRAGMNET_WORKING_ORDER, orderNum);
        this.finish();
    }

    @Override
    public void arriveFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    private void showChangeAndReceiveView() {
        changeOrderBt.setVisibility(View.VISIBLE);
        receiveBt.setVisibility(View.VISIBLE);
        goBt.setVisibility(View.GONE);
        arriveBt.setVisibility(View.GONE);
    }


    /**
     * ??????????????????
     */
    private void showGoView() {
        changeOrderBt.setVisibility(View.VISIBLE);
        receiveBt.setVisibility(View.GONE);
        goBt.setVisibility(View.VISIBLE);
        arriveBt.setVisibility(View.GONE);
    }

    /**
     * ??????????????????
     */
    private void showArriveView() {
        changeOrderBt.setVisibility(View.GONE);
        receiveBt.setVisibility(View.GONE);
        goBt.setVisibility(View.GONE);
        arriveBt.setVisibility(View.VISIBLE);
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

    public void updateOrder(){
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
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
