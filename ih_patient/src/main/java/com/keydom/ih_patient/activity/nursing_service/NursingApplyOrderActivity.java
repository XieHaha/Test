package com.keydom.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_service.controller.NursingApplyOrderController;
import com.keydom.ih_patient.activity.nursing_service.view.NursingApplyOrderView;
import com.keydom.ih_patient.adapter.GridViewImageShowAdapter;
import com.keydom.ih_patient.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_patient.adapter.NursingServicePriceAdapter;
import com.keydom.ih_patient.bean.NursingServiceOrderInfo;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.view.FlowLayout;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/25 on 15:52
 * des:护理申请订单
 */
public class NursingApplyOrderActivity extends BaseControllerActivity<NursingApplyOrderController> implements NursingApplyOrderView {
    private TextView mOrderName;//order_name
    private FlowLayout mOrderNameFlow;
    private TextView mOrderNum;//number_content 订单号
    private TextView mName;//name_content 姓名
    private TextView mPhone;//phone_content 电话
    private TextView mAddress;//address_content 服务地址
    private TextView mTime;//time_content 上门时间
    private TextView mServiceObj;//service_object_content 服务对象
    private TextView mHosPital; //hospital_content 医院
    private TextView mDepartment;//project_content 科室
    private TextView mDemand;//demand_content 需求描述
    private RecyclerView mServiceCost;//service_cost_content  服务费
    private TextView mServiceTotal;//money  总计
    private TextView mCancel;//取消
    private TextView mPay;//支付
    private TextView noPicTv;
    private LinearLayout picLayout;
    private NursingServiceOrderInfo orderInfo;
    private NursingServicePriceAdapter nursingServicePriceAdapter;
    private List<NursingServiceOrderInfo.NursingServiceOrderDetailBaseDtoBean.OrderDetailItemsBean> priceList=new ArrayList<>();
    private String payType = Type.ALIPAY;
    private BigDecimal allFee= BigDecimal.valueOf(0);
    private GridViewForScrollView img_gv;
    private GridViewImageShowAdapter mAdapter;
    public List<String> dataList = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context, NursingServiceOrderInfo data) {
        Intent intent = new Intent(context, NursingApplyOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_apply_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("护理申请单");
        orderInfo = (NursingServiceOrderInfo) getIntent().getSerializableExtra("orderInfo");
        getView();
        fillData();
    }


    /**
     * 查找控件
     */
    private void getView() {
        mOrderName = findViewById(R.id.order_name);
        mOrderNameFlow=findViewById(R.id.order_name_fl);
        mOrderNum = findViewById(R.id.number_content);
        mName = findViewById(R.id.name_content);
        mPhone = findViewById(R.id.phone_content);
        mAddress = findViewById(R.id.address_content);
        mTime = findViewById(R.id.time_content);
        mServiceObj = findViewById(R.id.service_object_content);
        mHosPital = findViewById(R.id.hospital_content);
        mDemand = findViewById(R.id.demand_content);
        mServiceCost = findViewById(R.id.service_cost_content);
        nursingServicePriceAdapter=new NursingServicePriceAdapter(priceList);
        mServiceCost.setAdapter(nursingServicePriceAdapter);
        mDepartment = findViewById(R.id.project_content);
        mServiceTotal = findViewById(R.id.money);
        mCancel = findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPay = findViewById(R.id.pay);
        mPay.setOnClickListener(getController());
        noPicTv = findViewById(R.id.no_pic_tv);
        picLayout = findViewById(R.id.pic_layout);
        img_gv = findViewById(R.id.img_gv);
        mAdapter = new GridViewImageShowAdapter(this, dataList);
        img_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.previewImage(getContext(), Const.IMAGE_HOST+dataList.get(position));
            }
        });
        img_gv.setAdapter(mAdapter);
    }

    /**
     * 查找全部数据
     */
    private void fillData() {
        for (int i = 0; i <orderInfo.getNursingServiceOrderDetailBaseDto().getOrderDetailItems().size() ; i++) {
            allFee=allFee.add(orderInfo.getNursingServiceOrderDetailBaseDto().getOrderDetailItems().get(i).getTotalPrice());
        }
        String serviceName = "";
        if (orderInfo.getNursingServiceOrderDetailBaseDto() != null) {
            if (orderInfo.getNursingServiceOrderDetailBaseDto().getServiceName() != null) {
                for (int i = 0; i < orderInfo.getNursingServiceOrderDetailBaseDto().getServiceName().size(); i++) {
                    serviceName += (orderInfo.getNursingServiceOrderDetailBaseDto().getServiceName().get(i) + " ");
                    TextView textView=new TextView(getContext());
                    textView.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getServiceName().get(i));
                    textView.setTextColor(Color.parseColor("#BBBBBB"));
                    textView.setPadding(getResources().getDimensionPixelSize(R.dimen.dp_5),getResources().getDimensionPixelSize(R.dimen.dp_3),getResources().getDimensionPixelSize(R.dimen.dp_5),getResources().getDimensionPixelSize(R.dimen.dp_5));
                    mOrderNameFlow.addView(textView);
                }
                //mOrderName.setText(serviceName);
            }
            mOrderNum.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getOrderNumber() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getOrderNumber() : "");
            mName.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getUserName() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getUserName() : "");
            mPhone.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getApplyPhone() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getApplyPhone() : "");
            mAddress.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getServiceAddress() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getServiceAddress() : "");
            mTime.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getTime() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getTime() : "");
            String objName = "";
            String objSex = "";
            String objAge = "";
            if (orderInfo.getNursingServiceOrderDetailBaseDto().getPatientName() != null) {
                objName = orderInfo.getNursingServiceOrderDetailBaseDto().getPatientName();
            }
            if ("0".equals(orderInfo.getNursingServiceOrderDetailBaseDto().getPatientSex())) {
                objSex = "男";
            } else if ("1".equals(orderInfo.getNursingServiceOrderDetailBaseDto().getPatientSex())) {
                objSex = "女";
            } else if ("2".equals(orderInfo.getNursingServiceOrderDetailBaseDto().getPatientSex())) {
                objSex = "未知";
            }
            objAge=orderInfo.getNursingServiceOrderDetailBaseDto().getPatientAge()+"岁";
            mServiceObj.setText(objName+"  "+objSex+"  "+objAge);
            mHosPital.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getHospital() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getHospital() : "");
            mDepartment.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getDeptName() != null ? orderInfo.getNursingServiceOrderDetailBaseDto().getDeptName() : "");
            mDemand.setText(orderInfo.getNursingServiceOrderDetailBaseDto().getConditionDesciption() != null &&!"".equals(orderInfo.getNursingServiceOrderDetailBaseDto().getConditionDesciption())? orderInfo.getNursingServiceOrderDetailBaseDto().getConditionDesciption() : "无");
            RecyclerView mServiceCost;//service_cost_content  服务费
            mServiceTotal.setText("¥"+allFee+"元");
            if(orderInfo.getNursingServiceOrderDetailBaseDto().getConditionImage()!=null&&!"".equals(orderInfo.getNursingServiceOrderDetailBaseDto().getConditionImage())){
                String[]url=orderInfo.getNursingServiceOrderDetailBaseDto().getConditionImage().split(",");
                for (int i = 0; i <url.length ; i++) {
                    dataList.add(url[i]);
                }
                mAdapter.notifyDataSetChanged();

            }else {
                picLayout.setVisibility(View.GONE);
                noPicTv.setVisibility(View.VISIBLE);
            }
            mPay.setText("立即支付 ¥"+allFee);
            nursingServicePriceAdapter.setNewData(orderInfo.getNursingServiceOrderDetailBaseDto().getOrderDetailItems());
        }

    }

    @Override
    public BigDecimal getAllFee() {
        return allFee;
    }

    @Override
    public String getOrderNum() {
        return orderInfo.getNursingServiceOrderDetailBaseDto().getOrderNumber();
    }


}
