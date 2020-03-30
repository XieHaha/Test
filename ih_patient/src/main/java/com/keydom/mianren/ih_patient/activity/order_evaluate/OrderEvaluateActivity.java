package com.keydom.mianren.ih_patient.activity.order_evaluate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_evaluate.controller.OrderEvaluateController;
import com.keydom.mianren.ih_patient.activity.order_evaluate.view.OrderEvaluateView;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderBean;
import com.keydom.mianren.ih_patient.bean.OrderEvaluateBean;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.view.RatingBarView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * created date: 2018/12/26 on 18:54
 * des:评价页面
 */
public class OrderEvaluateActivity extends BaseControllerActivity<OrderEvaluateController> implements OrderEvaluateView {
    public static final String EVALUATE_BEAN = "evaluate_bean";
    @BindView(R.id.evaluate_rb)
    RatingBarView evaluateRb;
    @BindView(R.id.evaluate_bottom)
    TextView evaluateBottom;
    @BindView(R.id.evaluate1)
    TextView evaluate1;
    @BindView(R.id.evaluate2)
    TextView evaluate2;
    @BindView(R.id.evaluate3)
    TextView evaluate3;
    @BindView(R.id.evaluate4)
    TextView evaluate4;

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
     * 星级
     */
    private int mStar;

    /**
     * 启动
     */
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
        OrderEvaluateBean evaluateBean =
                (OrderEvaluateBean) getIntent().getSerializableExtra(EVALUATE_BEAN);
        setTitle(evaluateBean.getTitle());
        setRightTxt("提交");
        setRightBtnListener(v -> switchEvaluateType(evaluateBean));
        mStar = 1;
        evaluateRb.setStar(mStar);
        evaluateBottom.setText(mEvaluateBottom[0]);
        evaluate1.setText(mEvaluatePrefisxs[0] + mEvaluate1[0]);
        evaluate2.setText(mEvaluatePrefisxs[1] + mEvaluate2[0]);
        evaluate3.setText(mEvaluatePrefisxs[2] + mEvaluate3[0]);
        evaluate4.setText(mEvaluatePrefisxs[3] + mEvaluate4[0]);
        evaluateRb.setOnRatingListener((bindObject, RatingScore) -> {
            mStar = RatingScore;
            evaluateBottom.setText(mEvaluateBottom[RatingScore - 1]);
            evaluate1.setText(mEvaluatePrefisxs[0] + mEvaluate1[RatingScore - 1]);
            evaluate2.setText(mEvaluatePrefisxs[1] + mEvaluate2[RatingScore - 1]);
            evaluate3.setText(mEvaluatePrefisxs[2] + mEvaluate3[RatingScore - 1]);
            evaluate4.setText(mEvaluatePrefisxs[3] + mEvaluate4[RatingScore - 1]);
        });

        evaluate1.setOnClickListener(getController());
        evaluate2.setOnClickListener(getController());
        evaluate3.setOnClickListener(getController());
        evaluate4.setOnClickListener(getController());
    }

    /**
     * 判断订单类型提交评价
     */
    private void switchEvaluateType(OrderEvaluateBean evaluateBean) {
        if ("".equals(getEvaluateContent())) {
            ToastUtil.showMessage(getContext(), "请至少从以下标签中选择一个");
        } else {
            switch (evaluateBean.getType()) {
                case Type.SUBSCRIBE_EXAM_ORDER_EVALUATE:
                    getController().submitSubscribeExamEvaluate((SubscribeExaminationBean) evaluateBean.getObj(), mStar, getEvaluateContent());
                    break;
                case Type.NURSING_ORDER_EVALUATE:
                    getController().submitNursingEvaluate((NursingOrderBean) evaluateBean.getObj(), mStar, getEvaluateContent());
                    break;
                case Type.DIAGNOSES_ORDER_EVALUATE:
                    getController().doComment((DiagnosesOrderBean) evaluateBean.getObj(), mStar,
                            getEvaluateContent());
                    break;
            }
        }

    }

    @Override
    public void onEvaluate1Click() {
        evaluate1.setSelected(!evaluate1.isSelected());
    }

    @Override
    public void onEvaluate2Click() {
        evaluate2.setSelected(!evaluate2.isSelected());
    }

    @Override
    public void onEvaluate3Click() {
        evaluate3.setSelected(!evaluate3.isSelected());
    }

    @Override
    public void onEvaluate4Click() {
        evaluate4.setSelected(!evaluate4.isSelected());
    }

    /**
     * 返回提交文字内容
     */
    private String getEvaluateContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(handlerEvaluate(evaluate1));
        sb.append(handlerEvaluate(evaluate2));
        sb.append(handlerEvaluate(evaluate3));
        sb.append(handlerEvaluate(evaluate4));
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
