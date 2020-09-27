package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthView;
import com.keydom.mianren.ih_patient.adapter.ChildHealthAdapter;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectItemBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthRootBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;
import com.keydom.mianren.ih_patient.view.MyNestedScollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健首页
 */
public class ChildHealthActivity extends BaseControllerActivity<ChildHealthController> implements ChildHealthView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.right_tv)
    TextView tvRight;
    @BindView(R.id.nested_scroll_view)
    MyNestedScollView scrollView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.header_child_health_age_tv)
    TextView headerChildHealthAgeTv;
    @BindView(R.id.header_child_health_sex_tv)
    TextView headerChildHealthSexTv;
    @BindView(R.id.header_child_health_last_date_tv)
    TextView headerChildHealthLastDateTv;
    @BindView(R.id.header_child_health_info_layout)
    RelativeLayout headerChildHealthInfoLayout;
    @BindView(R.id.header_child_health_root_layout)
    LinearLayout headerChildHealthRootLayout;
    @BindView(R.id.header_child_health_look_tv)
    TextView headerChildHealthLookTv;
    @BindView(R.id.header_child_health_doing_date_tv)
    TextView headerChildHealthDoingDateTv;
    @BindView(R.id.header_child_health_project_tv)
    TextView headerChildHealthProjectTv;
    @BindView(R.id.header_child_health_all_project_layout)
    LinearLayout headerChildHealthAllProjectLayout;
    @BindView(R.id.header_child_health_doing_layout)
    LinearLayout headerChildHealthDoingLayout;
    @BindView(R.id.mine_user_head_img)
    ImageView mineUserHeadImg;
    @BindView(R.id.mine_user_name)
    TextView mineUserName;
    @BindView(R.id.mine_user_card)
    TextView mineUserCard;

    private ChildHealthAdapter adapter;

    private ArrayList<String> data;

    /**
     * 首页数据集合
     */
    private ChildHealthRootBean rootBean;
    /**
     * 历史记录
     */
    private List<ChildHealthDoingBean> healthHistoryBeans;
    /**
     * 即将进行的数据
     */
    private ChildHealthDoingBean healthDoingBean;
    /**
     * 就诊卡
     */
    private MedicalCardInfo medicalCardInfo;
    private String eleCardNumber;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ChildHealthActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStateBarHeight(this)));
        StatusBarUtils.setStatusBarTranslucent(this);
        tvTitle.setText(R.string.txt_child_maintain);
        tvRight.setText(R.string.txt_select_visit_people);
        layoutBg.setAlpha(0);
        statusBar.setAlpha(0);
        StatusBarUtils.setStatusBarColor(this, true);
        adapter = new ChildHealthAdapter(data);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(getController());
        ivBack.setOnClickListener(getController());
        tvRight.setOnClickListener(getController());
        headerChildHealthInfoLayout.setOnClickListener(getController());
        headerChildHealthLookTv.setOnClickListener(getController());
        headerChildHealthAllProjectLayout.setOnClickListener(getController());
        scrollView.setScrollViewListener((scrollView, x, y, oldX, oldY) -> getController().transTitleBar(y));
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            if (!TextUtils.isEmpty(eleCardNumber)) {
                getController().getChildHistory();
            }
        });
    }

    /**
     * 用户信息
     */
    private void bindUserInfo() {
        //        GlideUtils.load(mineUserHeadImg, Const.IMAGE_HOST + medicalCardInfo.get(), 0,
        //        0, false, null);
        mineUserName.setText(medicalCardInfo.getName());
        mineUserCard.setText("卡号：" + medicalCardInfo.getEleCardNumber());
        headerChildHealthAgeTv.setText(DateUtils.getMonthDiffer(medicalCardInfo.getBirthday()) +
                "月龄");
        headerChildHealthSexTv.setText(CommonUtils.getSex(medicalCardInfo.getSex()));
    }

    /**
     * 儿保项目数据
     */
    private void bindData() {
        headerChildHealthLastDateTv.setText(rootBean.getLastTimeChild());
        healthHistoryBeans = rootBean.getHistoryRecords();
        if (rootBean.getFutureAndDoing() != null && rootBean.getFutureAndDoing().size() > 0) {
            headerChildHealthDoingLayout.setVisibility(View.VISIBLE);
            //默认取第一条
            healthDoingBean = rootBean.getFutureAndDoing().get(0);
            headerChildHealthDoingDateTv.setText(healthDoingBean.getAppointmentTime());
            headerChildHealthProjectTv.setText(getProject(healthDoingBean.getSelect()));
        } else {
            headerChildHealthDoingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void requestSuccess(ChildHealthRootBean data) {
        swipeRefreshLayout.finishRefresh();
        rootBean = data;
        bindData();
    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            tvRight.setText(R.string.txt_change_visit_people);
            bindUserInfo();
            eleCardNumber = medicalCardInfo.getEleCardNumber();
            headerChildHealthRootLayout.setVisibility(View.VISIBLE);
            getController().getChildHistory();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChileHealthUpdate(Event event) {
        if (event.getType() == EventType.CHILD_HEALTH_APPLY) {
            getController().getChildHistory();
        }
    }

    @Override
    public List<ChildHealthDoingBean> getHealthHistoryBeans() {
        return healthHistoryBeans;
    }

    @Override
    public String getEleCardNumber() {
        return eleCardNumber;
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }

    @Override
    public ChildHealthDoingBean getHealthDoingBean() {
        return healthDoingBean;
    }

    private String getProject(List<ChildHealthProjectItemBean> select) {
        StringBuilder builder = new StringBuilder();
        if (select != null) {
            int count = select.size();
            for (int i = 0; i < count; i++) {
                builder.append(select.get(i).getName());
                if (select.size() - 1 != i) {
                    builder.append("、");
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void transTitleBar(boolean direction, float scale) {
        tvTitle.setSelected(!direction);
        tvRight.setSelected(!direction);
        ivBack.setSelected(!direction);
        layoutBg.setAlpha(scale);
        statusBar.setAlpha(scale);
        StatusBarUtils.setStatusBarColor(this, direction);
    }

    @Override
    public void finishPage() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
