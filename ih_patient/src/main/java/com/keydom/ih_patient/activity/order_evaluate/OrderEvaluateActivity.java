package com.keydom.ih_patient.activity.order_evaluate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_evaluate.controller.OrderEvaluateController;
import com.keydom.ih_patient.activity.order_evaluate.view.OrderEvaluateView;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.NursingOrderBean;
import com.keydom.ih_patient.bean.OrderEvaluateBean;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.view.RatingBarView;

import org.jetbrains.annotations.Nullable;

/**
 * created date: 2018/12/26 on 18:54
 * des:评价页面
 */
public class OrderEvaluateActivity extends BaseControllerActivity<OrderEvaluateController> implements OrderEvaluateView {
    public static final String EVALUATE_BEAN = "evaluate_bean";

    /**
     * 评价前缀
     */
    private String[] mEvaluatePrefisxs = {"回复速度", "医生态度", "医生医术", "就诊费用"};
    /**
     * 详细评价
     */
    private String[] mEvaluateBottom = {"不满意，急需改善", "不太满意，需改善", "一般，建议改善", "比较满意，仍可改善", "很满意，继续保持"};
    /**
     * 评价一等级
     */
    private String[] mEvaluate1 = {"慢", "较慢", "一般", "快", "很快"};
    /**
     * 评价二等级
     */
    private String[] mEvaluate2 = {"差", "较差", "一般", "好", "很好"};
    /**
     * 评价三等级
     */
    private String[] mEvaluate3 = {"低", "较低", "一般", "高", "高超"};
    private String[] mEvaluate4 = {"高", "较高", "一般", "低", "较低"};
    /**
     * 评价四等级
     */

    private TextView mEvaluateBottomTv;
    private TextView mEvaluate1Tv;
    private TextView mEvaluate2Tv;
    private TextView mEvaluate3Tv;
    private TextView mEvaluate4Tv;
    private RatingBarView mRbView;

    /**
     * 星级
     */
    private int mStar;

    /**
     * 启动
     */
    /*
     * obj需继承序列化
     *  */
    public static void start(Context ctx, String title, String type, Object obj) {
        OrderEvaluateBean orderEvaluateBean = new OrderEvaluateBean();
        orderEvaluateBean.setTitle(title);
        orderEvaluateBean.setType(type);
        orderEvaluateBean.setObj(obj);
        Intent i = new Intent(ctx, OrderEvaluateActivity.class);
        i.putExtra(OrderEvaluateActivity.EVALUATE_BEAN, orderEvaluateBean);
        ActivityUtils.startActivity(i);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_evaluate;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
        OrderEvaluateBean evaluateBean = (OrderEvaluateBean) getIntent().getSerializableExtra(EVALUATE_BEAN);
        setTitle(evaluateBean.getTitle());
        setRightTxt("提交");
        setRightBtnListener(v -> {
            switchEvaluateType(evaluateBean);
        });
        mStar = 1;
        mRbView.setStar(mStar);
        mEvaluateBottomTv.setText(mEvaluateBottom[0]);
        mEvaluate1Tv.setText(mEvaluatePrefisxs[0] + mEvaluate1[0]);
        mEvaluate2Tv.setText(mEvaluatePrefisxs[1] + mEvaluate2[0]);
        mEvaluate3Tv.setText(mEvaluatePrefisxs[2] + mEvaluate3[0]);
        mEvaluate4Tv.setText(mEvaluatePrefisxs[3] + mEvaluate4[0]);
        mRbView.setOnRatingListener((bindObject, RatingScore) -> {
            mStar = RatingScore;
            mEvaluateBottomTv.setText(mEvaluateBottom[RatingScore - 1]);
            mEvaluate1Tv.setText(mEvaluatePrefisxs[0] + mEvaluate1[RatingScore - 1]);
            mEvaluate2Tv.setText(mEvaluatePrefisxs[1] + mEvaluate2[RatingScore - 1]);
            mEvaluate3Tv.setText(mEvaluatePrefisxs[2] + mEvaluate3[RatingScore - 1]);
            mEvaluate4Tv.setText(mEvaluatePrefisxs[3] + mEvaluate4[RatingScore - 1]);
        });
    }

    /**
     * 判断订单类型提交评价
     */
    private void switchEvaluateType(OrderEvaluateBean evaluateBean) {
        if("".equals(getEvaluateContent())){
            ToastUtil.shortToast(getContext(),"请至少从以下标签中选择一个");
        }else {
            switch (evaluateBean.getType()) {
                case Type.SUBSCRIBE_EXAM_ORDER_EVALUATE:
                    getController().submitSubscribeExamEvaluate((SubscribeExaminationBean) evaluateBean.getObj(), mStar, getEvaluateContent());
                    break;
                case Type.NURSING_ORDER_EVALUATE:
                    getController().submitNursingEvaluate((NursingOrderBean) evaluateBean.getObj(), mStar, getEvaluateContent());
                    break;
                case Type.DIAGNOSES_ORDER_EVALUATE:
                    getController().doComment((DiagnosesOrderBean) evaluateBean.getObj(), mStar, getEvaluateContent());
                    break;
            }
        }

    }

    /**
     * 查找控件
     */
    private void getView() {
        mEvaluateBottomTv = findViewById(R.id.evaluate_bottom);
        mEvaluate1Tv = findViewById(R.id.evaluate1);
        mEvaluate1Tv.setOnClickListener(getController());
        mEvaluate2Tv = findViewById(R.id.evaluate2);
        mEvaluate2Tv.setOnClickListener(getController());
        mEvaluate3Tv = findViewById(R.id.evaluate3);
        mEvaluate3Tv.setOnClickListener(getController());
        mEvaluate4Tv = findViewById(R.id.evaluate4);
        mEvaluate4Tv.setOnClickListener(getController());
        mRbView = findViewById(R.id.evaluate_rb);

    }

    @Override
    public void onEvaluate1Click() {
        mEvaluate1Tv.setSelected(!mEvaluate1Tv.isSelected());
    }

    @Override
    public void onEvaluate2Click() {
        mEvaluate2Tv.setSelected(!mEvaluate2Tv.isSelected());
    }

    @Override
    public void onEvaluate3Click() {
        mEvaluate3Tv.setSelected(!mEvaluate3Tv.isSelected());
    }

    @Override
    public void onEvaluate4Click() {
        mEvaluate4Tv.setSelected(!mEvaluate4Tv.isSelected());
    }

    /**
     * 返回提交文字内容
     */
    private String getEvaluateContent() {
        StringBuffer sb = new StringBuffer();
        sb.append(handlerEvaluate(mEvaluate1Tv));
        sb.append(handlerEvaluate(mEvaluate2Tv));
        sb.append(handlerEvaluate(mEvaluate3Tv));
        sb.append(handlerEvaluate(mEvaluate4Tv));
        if (sb.length() > 0 && sb.lastIndexOf(",") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 文字内容用逗号隔开
     */
    private String handlerEvaluate(TextView tv) {
        return tv.isSelected() ? tv.getText().toString() + "," : "";
    }
}
