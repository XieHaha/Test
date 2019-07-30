package com.keydom.ih_patient.activity.payment_records;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.payment_records.controller.PaymentRecordController;
import com.keydom.ih_patient.activity.payment_records.view.PaymentRecordView;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 缴费记录主页面
 */
public class PaymentRecordActivity extends BaseControllerActivity<PaymentRecordController> implements PaymentRecordView {
    //0表示未缴费  1表示已缴费 2表示已退费
    public static final int NO_PAY =  0;
    public static final int PAYED =  1;
    public static final int RETURNS =  2;

    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,PaymentRecordActivity.class));
    }

    private TabLayout paymentTb;
    private ViewPager paymentVp;
    private FragmentManager fm;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_payment_record_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("缴费记录");
        paymentTb=findViewById(R.id.payment_tb);
        paymentVp=findViewById(R.id.payment_vp);

        list.add("未缴费");
        list.add("已缴费");
        list.add("已退费");
        fm=getSupportFragmentManager();
        fragmentList.add(new UnpayRecordFragment());
        fragmentList.add(PaiedRecordFragment.newInstance(PAYED));
        fragmentList.add(PaiedRecordFragment.newInstance(RETURNS));
        if(viewPagerAdapter==null){
            viewPagerAdapter=new ViewPagerAdapter(fm,fragmentList,list);
        }
        paymentVp.setAdapter(viewPagerAdapter);
        paymentVp.setOffscreenPageLimit(3);
//        LinearLayout linearLayout = (LinearLayout) paymentVp.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
//                R.drawable.layout_divider_vertical));

        paymentTb.setTabTextColors(getResources().getColor(R.color.title_bar_text_color), getResources().getColor(R.color.list_tab_color));
        paymentTb.setSelectedTabIndicatorColor(getResources().getColor(R.color.list_tab_color));
        paymentTb.setupWithViewPager(paymentVp);
    }
}
