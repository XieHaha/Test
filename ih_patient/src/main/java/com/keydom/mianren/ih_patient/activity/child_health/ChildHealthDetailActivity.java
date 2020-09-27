package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthDetailController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthDetailView;
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

    private ChildHealthProjectBean projectBean;
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
        intent.putExtra("cardInfo", cardInfo);
        intent.putExtra(Const.DATA, projectBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_child_maintain_detail));
        projectBean = (ChildHealthProjectBean) getIntent().getSerializableExtra(Const.DATA);
        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra("cardInfo");

        childHealthDetailSelectTimeTv.setOnClickListener(getController());
        childHealthDetailNextTv.setOnClickListener(getController());

        if (cardInfo != null) {
            childHealthDetailNameTv.setText(cardInfo.getName());
            childHealthDetailSexTv.setText(CommonUtils.getSex(cardInfo.getSex()));
            childHealthDetailAgeTv.setText(DateUtils.getMonthDiffer(cardInfo.getBirthday()) + "月龄");
        }

        if (projectBean != null) {
            childHealthDetailNoticeLayout.setContent(projectBean.getAttention());

            selectProjectLayout.setTitle("儿保项目");
            mustSelectProjects = projectBean.getMustFill();
            selectProjectLayout.setContentList(mustSelectProjects, true);
            unSelectProjectLayout.setTitle("可选项目");
            notMustSelectProjects = projectBean.getNotMustFill();
            unSelectProjectLayout.setContentList(notMustSelectProjects, false);
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
