package com.keydom.mianren.ih_doctor.activity.nurse_service;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.nurse_service.controller.CommonNurseServiceWorkingOrderDetailController;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.CommonNurseServiceWorkingOrderDetailView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_doctor.adapter.NurseChildRecordAdapter;
import com.keydom.mianren.ih_doctor.adapter.NurseServiceEquipmentAdapter;
import com.keydom.mianren.ih_doctor.adapter.NurseServiceRecoderAdapter;
import com.keydom.mianren.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.NurseServiceRecoderBean;
import com.keydom.mianren.ih_doctor.bean.NurseSubOrderBean;
import com.keydom.mianren.ih_doctor.bean.NursingPatientEquipmentItem;
import com.keydom.mianren.ih_doctor.bean.NursingProjectInfo;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.OnAddServiceItemDialogListener;
import com.keydom.mianren.ih_doctor.utils.BaiduMapUtil;
import com.keydom.mianren.ih_doctor.view.AddNurseServiceDialog;
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
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class CommonNurseServiceWorkingOrderDetailActivity extends BaseControllerActivity<CommonNurseServiceWorkingOrderDetailController> implements CommonNurseServiceWorkingOrderDetailView {
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
    private RelativeLayout hospitalNameRl, hospitalAddressRl, spreadOutRl;
    private DiagnoseOrderDetailAdapter diagnoseMaterialAdapter;
    private NurseServiceRecoderAdapter nurseServiceRecoderAdapter;
    private NurseServiceEquipmentAdapter nurseServiceEquipmentAdapter;
    private NurseChildRecordAdapter nurseChildRecordAdapter;
    private ImageView navigate, userPhoneIcon;
    private TextView spreadOut, hospitalAddress;
    private Button addServiceBt, finishServiceBt;
    private RecyclerView diagnoseMaterialRv, serviceRecoderRv, serviceMaterialRv,childRecordRv;
    private List<NurseServiceRecoderBean> nurseServiceRecoderBeanList = new ArrayList<>();
    private List<NursingPatientEquipmentItem> nursingPatientEquipmentItemList = new ArrayList<>();
    private List<NurseSubOrderBean> subOrderDetails=new ArrayList<>();
    private HeadNurseServiceOrderDetailBean baseInfo;
    private List<String> diagnoseMaterialList = new ArrayList<>();
    private TextView distanceTv, hospitalName, userPhone, orderNumber, orderUserDept, orderUserName, orderUserPhone, orderUserAddress, orderServiceObject, orderUserContactNumber, orderVisitTime, orderFee, serviceReqExplainTv;
    private List<NursingProjectInfo> addServiceItemList;
    private BaiduMapUtil mapUtil;
    private AddNurseServiceDialog dialog;
    private RelativeLayout child_recoder_layout;
    /**
     * 开启服务中订单详情页面
     *
     * @param context
     * @param type     判断是否已完成订单
     * @param orderNum 订单ID
     */
    public static void start(Context context, TypeEnum type, String orderNum) {
        Intent starter = new Intent(context, CommonNurseServiceWorkingOrderDetailActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.DATA, orderNum);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.common_nurse_service_working_order_detail_layout;
    }

    /**
     * 设置订单详情
     *
     * @param bean 订单数据
     */
    private void setInfo(HeadNurseServiceOrderDetailBean bean) {
        if (bean == null) {
            ToastUtil.showMessage(this, "加载失败");
            finish();
            return;
        }
        hospitalName.setText(bean.getHospital());
        hospitalAddress.setText(bean.getServiceAddress());
        userPhone.setText(bean.getApplyPhone());
        orderNumber.setText(bean.getOrderNumber());
        orderUserName.setText(bean.getUserName());
        orderUserDept.setText(bean.getDeptName());
        orderUserPhone.setText(bean.getApplyPhone());
        orderUserAddress.setText(bean.getServiceAddress());
        orderServiceObject.setText(bean.getServiceObject() + " " + com.keydom.ih_common.utils.CommonUtils.getSex(bean.getPatientSex()) + " " + bean.getPatientAge() );
        orderVisitTime.setText(bean.getTime());
        orderUserContactNumber.setText(bean.getApplyPhone());
        orderFee.setText("¥" + bean.getReservationSet() + "元/次");
        serviceReqExplainTv.setText(bean.getConditionDesciption());
    }


    /**
     * 初始化界面
     */
    private void initView() {
        child_recoder_layout=this.findViewById(R.id.child_recoder_layout);
        mMapView = this.findViewById(R.id.service_map);
        baseInfoLl = this.findViewById(R.id.base_info_ll);
        hospitalAddress = this.findViewById(R.id.hospital_address);
        diagnoseMaterialRv = this.findViewById(R.id.diagnose_material_rv);
        serviceRecoderRv = this.findViewById(R.id.service_recoder_rv);
        serviceMaterialRv = this.findViewById(R.id.service_material_rv);
        childRecordRv=this.findViewById(R.id.child_recoder_rv);
        buttonLl = this.findViewById(R.id.button_ll);
        hospitalNameRl = this.findViewById(R.id.hospital_name_rl);
        hospitalAddressRl = this.findViewById(R.id.hospital_address_rl);
        spreadOutRl = this.findViewById(R.id.spread_out_rl);
        navigate = this.findViewById(R.id.navigate);
        navigate.setOnClickListener(getController());
        spreadOut = this.findViewById(R.id.spread_out);
        addServiceBt = this.findViewById(R.id.add_service_bt);
        finishServiceBt = this.findViewById(R.id.finish_service_bt);

        userPhoneIcon = this.findViewById(R.id.user_phone_icon);
        userPhoneIcon.setOnClickListener(getController());
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
        overlay = new DrivingRouteOverlay(mMapView.getMap());
        spreadOut.setOnClickListener(getController());
        finishServiceBt.setOnClickListener(getController());
        addServiceBt.setOnClickListener(getController());
        if (mType == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER) {
            buttonLl.setVisibility(View.GONE);
        }
        initListAdaper();

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        orderNum = getIntent().getStringExtra(Const.DATA);
        setTitle("订单详情");
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

    /**
     * 初始化适配器
     */
    private void initListAdaper() {

        diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseMaterialList);
        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseMaterialRv.setAdapter(diagnoseMaterialAdapter);
        diagnoseMaterialRv.setLayoutManager(diagnoseMaterialRvLm);

        nurseServiceRecoderAdapter = new NurseServiceRecoderAdapter(this, nurseServiceRecoderBeanList);
        LinearLayoutManager nurseServiceRecoderRvLm = new LinearLayoutManager(this);
        nurseServiceRecoderRvLm.setOrientation(LinearLayoutManager.VERTICAL);
        serviceRecoderRv.setAdapter(nurseServiceRecoderAdapter);
        serviceRecoderRv.setLayoutManager(nurseServiceRecoderRvLm);

        nurseServiceEquipmentAdapter = new NurseServiceEquipmentAdapter(this, nursingPatientEquipmentItemList);
        LinearLayoutManager nurseServiceDrugRvLm = new LinearLayoutManager(this);
        nurseServiceRecoderRvLm.setOrientation(LinearLayoutManager.VERTICAL);
        serviceMaterialRv.setAdapter(nurseServiceEquipmentAdapter);
        serviceMaterialRv.setLayoutManager(nurseServiceDrugRvLm);


        nurseChildRecordAdapter=new NurseChildRecordAdapter(this,subOrderDetails,mType);
        LinearLayoutManager  nurseChildRecordMaterialRvLm = new LinearLayoutManager(this);
        nurseChildRecordMaterialRvLm.setOrientation(LinearLayoutManager.VERTICAL);
        childRecordRv.setAdapter(nurseChildRecordAdapter);
        childRecordRv.setLayoutManager(nurseChildRecordMaterialRvLm);

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
        map.put("nurseId",MyApplication.userInfo.getId());
        return map;
    }

    public void updateOrder(){
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
    }

    @Override
    public void getDetailSuccess(CommonNurseServiceOrderDetailBean bean) {
        pageLoadingSuccess();
        diagnoseMaterialList.clear();
        nurseServiceRecoderBeanList.clear();
        nursingPatientEquipmentItemList.clear();
        nurseServiceRecoderBeanList.addAll(bean.getNurseServiceRecordDetailDtos());
        nurseServiceRecoderAdapter.notifyDataSetChanged();
        nursingPatientEquipmentItemList.addAll(bean.getNursingPatientEquipmentItemDtos());
        nurseServiceEquipmentAdapter.notifyDataSetChanged();
        diagnoseMaterialList.addAll(CommonUtils.getImgList(bean.getNursingServiceOrderDetailBaseDto().getConditionImage()));
        diagnoseMaterialAdapter.notifyDataSetChanged();

        subOrderDetails.clear();
        subOrderDetails.addAll(bean.getSubOrderDetails());
        nurseChildRecordAdapter.setNewData(subOrderDetails);
        if(subOrderDetails.size()==0){
            child_recoder_layout.setVisibility(View.GONE);
            childRecordRv.setVisibility(View.GONE);
        }else {
            child_recoder_layout.setVisibility(View.VISIBLE);
            childRecordRv.setVisibility(View.VISIBLE);
        }

        setInfo(bean.getNursingServiceOrderDetailBaseDto());
        baseInfo = bean.getNursingServiceOrderDetailBaseDto();
        setMapInfo();
    }

    /**
     * 设置地图信息
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
    public DrivingRouteOverlay getOverlay() {
        return overlay;
    }

    @Override
    public void showAndHideBaseInfo() {
        if (baseInfoLl.getVisibility() == View.GONE) {
            baseInfoLl.setVisibility(View.VISIBLE);
            hospitalNameRl.setVisibility(View.VISIBLE);
            hospitalAddressRl.setVisibility(View.VISIBLE);
            navigate.setVisibility(View.VISIBLE);
            spreadOutRl.setVisibility(View.GONE);

        } else {
            baseInfoLl.setVisibility(View.GONE);
            hospitalNameRl.setVisibility(View.GONE);
            hospitalAddressRl.setVisibility(View.GONE);
            navigate.setVisibility(View.GONE);

        }

    }


    @Override
    public HeadNurseServiceOrderDetailBean getBaseInfo() {
        return baseInfo;
    }

    @Override
    public void addServiceItemSuccess(String msg) {
        ToastUtil.showMessage(this, "添加成功");
        if (addServiceItemList != null)
            addServiceItemList.clear();
        getController().getNurseServiceOrderDetail();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
    }

    @Override
    public void addServiceItemFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void editServiceItemSuccess(String msg) {
        if(dialog.isShowing())
            dialog.dismiss();
        getController().getNurseServiceOrderDetail();
    }

    @Override
    public void editServiceItemFailed(String errMsg) {
        if(dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public int getLimit() {
        int limit = baseInfo.getFrequency() - baseInfo.getServiceFrequency();
        return (limit < 0) ? 0 : limit;
    }

    @Override
    public List<NursingProjectInfo> getSelectSubItem() {
        return addServiceItemList;
    }

    @Override
    public BaiduMapUtil getMapUtil() {
        return mapUtil;
    }

    @Override
    public void getSubOrderDetail(List<NursingProjectInfo> data, String subOrderNumber, int frequency) {
        if(dialog==null){
            dialog = new AddNurseServiceDialog(getContext(), getBaseInfo(), getLimit(),frequency,data,true, new OnAddServiceItemDialogListener() {
                @Override
                public void dialogClick(View v, Map<String, Object> value) {
                    switch (v.getId()) {
                        case R.id.service_item_tv:
                            ChooseNursingServiceActivity.start(getContext(),data);
                            break;
                        case R.id.add_btn:
                            value.put("subOrderNumber",subOrderNumber);
                            getController().editSubOrder(value);
                            break;
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(true);
        }else {
            dialog=null;
            dialog = new AddNurseServiceDialog(getContext(), getBaseInfo(), getLimit(), frequency, data,true, new OnAddServiceItemDialogListener() {
                @Override
                public void dialogClick(View v, Map<String, Object> value) {

                    switch (v.getId()) {
                        case R.id.service_item_tv:
                            ChooseNursingServiceActivity.start(getContext(),data);
                            break;
                        case R.id.add_btn:
                            value.put("subOrderNumber",subOrderNumber);
                            getController().editSubOrder(value);
                            break;
                    }

                }
            });
            dialog.setCanceledOnTouchOutside(true);
        }

        dialog.show();
    }

    @Override
    public TextView getDistanceTv() {
        return distanceTv;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.NURSE_SERVICE_ITEM_SELECT:
                    addServiceItemList = (List<NursingProjectInfo>) data.getSerializableExtra(Const.DATA);
                    if (getController().getDialog() != null&&getController().getDialog().isShowing()) {
                        getController().getDialog().nurseServiceItemResult(addServiceItemList);
                    }else if(dialog!=null&&dialog.isShowing()){
                        dialog.nurseServiceItemResult(addServiceItemList);
                    }
                    break;
                case Const.FINISH_ACTIVITY:
                    finish();
                    break;
                default:
            }
        }

    }


}
