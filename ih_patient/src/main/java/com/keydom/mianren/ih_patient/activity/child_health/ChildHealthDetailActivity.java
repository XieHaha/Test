package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.JustifiedTextView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthDetailController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthDetailView;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectApplyBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectItemBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.view.ChildCareItemLayout;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健详情
 */
public class ChildHealthDetailActivity extends BaseControllerActivity<ChildHealthDetailController> implements ChildHealthDetailView {

    @BindView(R.id.child_health_detail_head_iv)
    ImageView childHealthDetailHeadIv;
    @BindView(R.id.child_health_detail_name_tv)
    TextView childHealthDetailNameTv;
    @BindView(R.id.child_health_detail_sex_tv)
    TextView childHealthDetailSexTv;
    @BindView(R.id.child_health_detail_age_tv)
    TextView childHealthDetailAgeTv;
    @BindView(R.id.child_health_detail_select_time_tv)
    TextView childHealthDetailSelectTimeTv;
    @BindView(R.id.child_health_detail_time_tv)
    TextView childHealthDetailTimeTv;
    @BindView(R.id.child_health_detail_select_project_layout)
    ChildCareItemLayout selectProjectLayout;
    @BindView(R.id.child_health_detail_unselect_project_layout)
    ChildCareItemLayout unSelectProjectLayout;
    @BindView(R.id.child_health_detail_notice_layout)
    ChildCareItemLayout childHealthDetailNoticeLayout;
    @BindView(R.id.child_health_detail_next_tv)
    TextView childHealthDetailNextTv;
    @BindView(R.id.header_child_health_state_tv)
    TextView headerChildHealthStateTv;
    @BindView(R.id.header_child_health_state_info_tv)
    TextView headerChildHealthStateInfoTv;
    @BindView(R.id.header_child_health_pay_tv)
    TextView headerChildHealthPayTv;
    @BindView(R.id.child_health_detail_hint_tv)
    JustifiedTextView childHealthDetailHintTv;
    @BindView(R.id.header_child_health_bottom_layout)
    RelativeLayout headerChildHealthBottomLayout;

    /**
     *
     */
    private ChildHealthProjectBean projectBean;
    /**
     * 即将进行的项目
     */
    private ChildHealthDoingBean doingBean;
    /**
     * 必选项目
     */
    private List<ChildHealthProjectItemBean> mustSelectProjects;
    /**
     * 可选项目
     */
    private List<ChildHealthProjectItemBean> notMustSelectProjects;
    /**
     * 已选项目
     */
    private List<ChildHealthProjectApplyBean> selectList;
    /**
     * 就诊卡
     */
    private MedicalCardInfo cardInfo;

    /**
     * 预约时间
     */
    private String reserveDate;

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo cardInfo,
                             ChildHealthProjectBean projectBean) {
        Intent intent = new Intent(context, ChildHealthDetailActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        intent.putExtra("projectBean", projectBean);
        context.startActivity(intent);
    }

    public static void start(Context context, MedicalCardInfo cardInfo,
                             ChildHealthDoingBean doingBean) {
        Intent intent = new Intent(context, ChildHealthDetailActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        intent.putExtra("doingBean", doingBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_child_maintain_detail));
        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);

        projectBean = (ChildHealthProjectBean) getIntent().getSerializableExtra("projectBean");
        doingBean = (ChildHealthDoingBean) getIntent().getSerializableExtra("doingBean");

        childHealthDetailSelectTimeTv.setOnClickListener(getController());
        childHealthDetailNextTv.setOnClickListener(getController());

        if (cardInfo != null) {
            childHealthDetailNameTv.setText(cardInfo.getName());
            childHealthDetailSexTv.setText(CommonUtils.getSex(cardInfo.getSex()));
            childHealthDetailAgeTv.setText(DateUtils.getMonthDiffer(cardInfo.getBirthday()) + "月龄");
        }

        if (projectBean != null) {
            initReservePage();
        } else {
            initReverseLaterPage();
        }
    }

    /**
     * 儿保预约提交页面
     */
    private void initReservePage() {
        childHealthDetailNoticeLayout.setContent(projectBean.getAttention());

        headerChildHealthBottomLayout.setVisibility(View.GONE);
        selectProjectLayout.setTitle(projectBean.getAge() + "月儿保项目");
        mustSelectProjects = projectBean.getMustFill();
        selectProjectLayout.setContentList(mustSelectProjects, true, false);
        unSelectProjectLayout.setTitle("可选项目");
        notMustSelectProjects = projectBean.getNotMustFill();
        unSelectProjectLayout.setContentList(notMustSelectProjects, false, true);
    }

    /**
     * 预约后
     */
    private void initReverseLaterPage() {
        childHealthDetailNoticeLayout.setContent(doingBean.getAttention());

        childHealthDetailSelectTimeTv.setVisibility(View.GONE);
        childHealthDetailNextTv.setVisibility(View.GONE);
        childHealthDetailTimeTv.setVisibility(View.VISIBLE);
        childHealthDetailTimeTv.setText(doingBean.getAppointmentTime());
        selectProjectLayout.setTitle(doingBean.getAge() + "月儿保项目");
        unSelectProjectLayout.setTitle("未选项目");
        mustSelectProjects = doingBean.getSelect();
        selectProjectLayout.setContentList(mustSelectProjects, true, false);
        notMustSelectProjects = doingBean.getUnSelect();
        if (notMustSelectProjects != null && notMustSelectProjects.size() > 0) {
            unSelectProjectLayout.setVisibility(View.VISIBLE);
            unSelectProjectLayout.setContentList(notMustSelectProjects, false, false);
        } else {
            unSelectProjectLayout.setVisibility(View.GONE);
        }

        if (doingBean.getDoState() > 1) {
            headerChildHealthBottomLayout.setVisibility(View.GONE);
            childHealthDetailHintTv.setVisibility(View.GONE);
            return;
        }

        //0 等待医生开具医嘱  1待支付 2已支付 3已取消
        switch (doingBean.getState()) {
            case 1:
                headerChildHealthStateTv.setText("已开医嘱");
                headerChildHealthStateInfoTv.setText("前往【诊间缴费】完成缴费");
                headerChildHealthPayTv.setVisibility(View.VISIBLE);
                headerChildHealthPayTv.setOnClickListener(getController());
                break;
            case 2:
                headerChildHealthStateTv.setText("已支付");
                unSelectProjectLayout.setVisibility(View.GONE);
                childHealthDetailHintTv.setText("如需取消预约请联系儿保科，到院内办理退款。");
                headerChildHealthStateInfoTv.setText("请准时前往医院儿童保健科室检查！");
                break;
            case 3:
                headerChildHealthStateTv.setText("已取消");
                childHealthDetailHintTv.setVisibility(View.GONE);
                break;
            default:
                headerChildHealthStateTv.setText("已提交预约");
                headerChildHealthStateInfoTv.setText("等待医生开具医嘱后前往【诊间缴费】缴费");
                break;
        }

    }

    @Override
    public void applySuccess() {
        Event event = new Event(EventType.CHILD_HEALTH_APPLY, null);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void setReserveDate(Date date) {
        reserveDate = DateUtils.dateToString(date);
        childHealthDetailSelectTimeTv.setText(reserveDate);
    }

    @Override
    public boolean commitAble() {
        if (TextUtils.isEmpty(reserveDate)) {
            ToastUtil.showMessage(this, "预约时间不能为空");
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("appointmentTime", reserveDate);
        map.put("eleCardNumber", cardInfo.getEleCardNumber());
        map.put("name", cardInfo.getName());
        map.put("items", getProject());
        map.put("sumFee", getProjectAllFee());
        return map;
    }

    /**
     * 所选项目
     */
    private List<ChildHealthProjectApplyBean> getProject() {
        selectList = new ArrayList<>();

        for (ChildHealthProjectItemBean itemBean : mustSelectProjects) {
            ChildHealthProjectApplyBean applyBean = new ChildHealthProjectApplyBean();
            applyBean.setAge(projectBean.getAge());
            applyBean.setChildProjectName(itemBean.getName());
            applyBean.setFee(itemBean.getPrice());
            applyBean.setPrice(itemBean.getPrice());
            selectList.add(applyBean);
        }
        for (ChildHealthProjectItemBean itemBean : notMustSelectProjects) {
            if (itemBean.isSelect()) {
                ChildHealthProjectApplyBean applyBean = new ChildHealthProjectApplyBean();
                applyBean.setAge(projectBean.getAge());
                applyBean.setChildProjectName(itemBean.getName());
                applyBean.setFee(itemBean.getPrice());
                applyBean.setPrice(itemBean.getPrice());
                selectList.add(applyBean);
            }
        }
        return selectList;
    }

    /**
     * 总价格
     */
    private String getProjectAllFee() {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (ChildHealthProjectApplyBean bean : selectList) {
            bigDecimal = bigDecimal.add(BigDecimal.valueOf(bean.getPrice()));
        }
        bigDecimal = bigDecimal.setScale(2, RoundingMode.CEILING);
        return bigDecimal.toString();
    }
}
