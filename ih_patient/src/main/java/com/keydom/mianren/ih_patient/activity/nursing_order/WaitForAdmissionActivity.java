package com.keydom.mianren.ih_patient.activity.nursing_order;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_order.controller.WaitForAdmissionController;
import com.keydom.mianren.ih_patient.activity.nursing_order.view.WaitForAdmissionView;
import com.keydom.mianren.ih_patient.adapter.NursingChargeBackImgAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created date: 2018/12/19 on 14:54
 * des: 待接诊订单详情
 */
public class WaitForAdmissionActivity extends BaseControllerActivity<WaitForAdmissionController> implements WaitForAdmissionView {
    public final static String ID = "id";
    public final static String STATE = "state";

    private ImageView mCircle;
    private TextView mOrderType;
    private TextView mOrderNum;
    private TextView mName;
    private TextView mPhone;
    private TextView mAddress;
    private TextView mTime;
    private TextView mServiceObj;
    private TextView mHospital;
    private TextView mProject;
    private TextView mProjectContent;
    private RelativeLayout mLaunchInfo;
    private ImageView mBottomLine;
    private TextView mDemandDes;
    private RecyclerView mImgRv;
    private TextView mServiceDes;
    private TextView mServiceCost;
    private TextView mGoPay;

    /**
     * 订单id
     */
    private long mId;
    private NursingChargeBackImgAdapter mImgAdapter;
    /**
     * 订单详情实体
     */
    private NursingOrderDetailBean mDetailBean;



    @Override
    public int getLayoutRes() {
        return R.layout.activity_wait_for_admission;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("订单详情");
        getView();

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setCornerRadius(ConvertUtils.dp2px(getResources().getDimension(R.dimen.dp_7)));
        drawable.setColor(getResources().getColor(R.color.white));
        drawable.setStroke(ConvertUtils.dp2px(getResources().getDimension(R.dimen.dp_3)), getResources().getColor(R.color.nursing_status_red));
        mCircle.setImageDrawable(drawable);

        mOrderType.setTextColor(getResources().getColor(R.color.nursing_status_red));

        mImgAdapter = new NursingChargeBackImgAdapter(new ArrayList<>());
        mImgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*String url = Const.IMAGE_HOST + (String) adapter.getData().get(position);
                CommonUtils.previewImage(getContext(),url);*/
                CommonUtils.previewImageList(getContext(),adapter.getData(),position,true);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mImgRv.setLayoutManager(linearLayoutManager);
        mImgRv.setAdapter(mImgAdapter);
        mId = getIntent().getLongExtra(ID, 0);
        getController().getWaitForAdmission(mId, getIntent().getIntExtra(STATE,0));
    }

    /**
     * 查找控件
     */
    private void getView() {
        mCircle = findViewById(R.id.circle);
        mOrderType = findViewById(R.id.order_type);
        mOrderNum = findViewById(R.id.number_content);
        mName = findViewById(R.id.name_content);
        mPhone = findViewById(R.id.phone_content);
        mAddress = findViewById(R.id.address_content);
        mTime = findViewById(R.id.time_content);
        mServiceObj = findViewById(R.id.service_object_content);
        mHospital = findViewById(R.id.hospital_content);
        mProject = findViewById(R.id.project);
        mProjectContent = findViewById(R.id.project_content);
        mLaunchInfo = findViewById(R.id.launch_info);
        mLaunchInfo.setOnClickListener(getController());
        mBottomLine = findViewById(R.id.bottom_line);
        mDemandDes = findViewById(R.id.demand_content);
        mImgRv = findViewById(R.id.img_rv);
        mServiceDes = findViewById(R.id.service_cost_content);
        mServiceCost = findViewById(R.id.money);
        mGoPay = findViewById(R.id.go_pay);
        findViewById(R.id.charge_back).setOnClickListener(getController());
        mGoPay.setOnClickListener(getController());
    }

    @Override
    public void launchInfo() {
        mLaunchInfo.setVisibility(View.GONE);
        mProject.setVisibility(View.VISIBLE);
        mProjectContent.setVisibility(View.VISIBLE);
        mBottomLine.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void getData(NursingOrderDetailBean data) {
        if (data.getNursingServiceOrderDetailBaseDto() != null) {
            NursingOrderDetailBean baseDto = data.getNursingServiceOrderDetailBaseDto();
            mDetailBean = baseDto;
            if (baseDto.getState() == NursingOrderDetailBean.STATE5){
                mGoPay.setVisibility(View.VISIBLE);
                mGoPay.setText("确认付款（¥" + baseDto.getReservationSet() + "元）");
                mOrderType.setText("未支付");
            }
            if (baseDto.getState() == NursingOrderDetailBean.STATE0){
                mOrderType.setText("待接单");
            }
            if (baseDto.getState() == NursingOrderDetailBean.STATE6){
                mOrderType.setText("待护士接单");
            }
            mName.setText(baseDto.getUserName());
            mOrderNum.setText(baseDto.getOrderNumber());
            mPhone.setText(baseDto.getApplyPhone());
            mAddress.setText(baseDto.getServiceAddress());
            String sex = "";
            if ("1".equals(baseDto.getPatientSex())){
                sex = "女";
            }
            if ("0".equals(baseDto.getPatientSex())){
                sex = "男";
            }
            mServiceObj.setText(baseDto.getPatientName()+" "+sex+" "+baseDto.getPatientAge()+"岁");
            mHospital.setText(baseDto.getHospital());
            mDemandDes.setText(baseDto.getConditionDesciption());
            mTime.setText(baseDto.getTime());
            mProjectContent.setText("¥"+baseDto.getReservationSet()+"元");

            if (baseDto.getConditionImage() != null) {
                List<String> imgs = Arrays.asList(baseDto.getConditionImage().replace("，", ",").split(","));
                if(imgs!=null&&"".equals(imgs.get(0))){
                    mImgAdapter.setNewData(new ArrayList<>());
                }else {
                    mImgAdapter.setNewData(imgs);
                }
            }
            if (baseDto.getOrderDetailItems() != null) {
                int j = 1;
                StringBuffer str = new StringBuffer();
                for (int i = 0; i < baseDto.getOrderDetailItems().size(); i++) {
                    NursingOrderDetailBean.OrderDetailItemsBean orderDetailItemsBean = baseDto.getOrderDetailItems().get(i);
                    str.append(j);
                    j += 1;
                    str.append("、");
                    str.append(orderDetailItemsBean.getServiceName());
                    str.append("服务费¥");
                    str.append(orderDetailItemsBean.getTotalPrice());
                    str.append("元");
                    str.append("/");
                    str.append(orderDetailItemsBean.getFrequency());
                    str.append("次");
                    str.append("\n");
                }
                mServiceDes.setText(str);
                mServiceCost.setText("¥" + baseDto.getReservationSet() + "元");
            }
        }
    }

    @Override
    public NursingOrderDetailBean getOrder(){
        mDetailBean.setId(mId);
        return mDetailBean;
    }

    @Override
    public void paySuccess() {
        EventBus.getDefault().post(new Event(EventType.NURSING_PAY_SUCCESS,null));
        finish();
    }

}
