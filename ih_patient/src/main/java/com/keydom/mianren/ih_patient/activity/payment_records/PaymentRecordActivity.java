package com.keydom.mianren.ih_patient.activity.payment_records;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.controller.PaymentRecordController;
import com.keydom.mianren.ih_patient.activity.payment_records.view.PaymentRecordView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 缴费记录主页面
 */
public class PaymentRecordActivity extends BaseControllerActivity<PaymentRecordController> implements PaymentRecordView {
    /**
     * 0表示未缴费  1表示已缴费 2表示已退费
     */
    public static final int NO_PAY = 0;
    public static final int PAYED = 1;
    public static final int RETURNS = 2;

    private List<ManagerUserBean> userBeans;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, PaymentRecordActivity.class));
    }

    private TabLayout paymentTb;
    private ViewPager paymentVp;
    private FragmentManager fm;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    private UnpayRecordFragment unpayRecordFragment;
    private PaiedRecordFragment paiedRecordFragment, paiedRecordFragment1;

    private long patientId;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_payment_record_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getController().getManagerUserList();
        setTitle("缴费记录");
        setRightBtnListener(v -> ChoosePatientActivity.start(PaymentRecordActivity.this,
                Const.PATIENT_TYPE_PERSON, true));
        paymentTb = findViewById(R.id.payment_tb);
        paymentVp = findViewById(R.id.payment_vp);

        list.add("未缴费");
        list.add("已缴费");
        list.add("已退费");
        fm = getSupportFragmentManager();
        fragmentList.add(unpayRecordFragment = UnpayRecordFragment.newInstance(patientId));
        fragmentList.add(paiedRecordFragment = PaiedRecordFragment.newInstance(patientId, PAYED));
        fragmentList.add(paiedRecordFragment1 = PaiedRecordFragment.newInstance(patientId,
                RETURNS));
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        paymentVp.setAdapter(viewPagerAdapter);
        paymentVp.setOffscreenPageLimit(3);
        //        LinearLayout linearLayout = (LinearLayout) paymentVp.getChildAt(0);
        //        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
        //                R.drawable.layout_divider_vertical));

        paymentTb.setTabTextColors(getResources().getColor(R.color.title_bar_text_color),
                getResources().getColor(R.color.list_tab_color));
        paymentTb.setSelectedTabIndicatorColor(getResources().getColor(R.color.list_tab_color));
        paymentTb.setupWithViewPager(paymentVp);
        if (Global.getUserId() == -1) {
            new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？",
                    new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }, new GeneralDialog.CancelListener() {
                @Override
                public void onCancel() {
                    finish();
                }
            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
        }
    }

    private ManagerUserBean managerUserBean = null;

    @Override
    public void getMangerUserList(List<ManagerUserBean> data) {
        if (data != null && data.size() > 0) {
            managerUserBean = data.get(0);
            update();
        }
    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTDIAGNOSESPATIENT) {
            managerUserBean = (ManagerUserBean) event.getData();
            update();
        }
    }

    /**
     * 更新
     */
    private void update() {
        setRightTxt(managerUserBean.getName());
        patientId = managerUserBean.getId();
        if (unpayRecordFragment != null) {
            unpayRecordFragment.setPatientId(patientId);
        }
        if (paiedRecordFragment != null) {
            paiedRecordFragment.setPatientId(patientId);
        }
        if (paiedRecordFragment1 != null) {
            paiedRecordFragment1.setPatientId(patientId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
