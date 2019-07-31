package com.keydom.ih_patient.activity.order_physical_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_physical_examination.controller.PhysicalExaminationDetailController;
import com.keydom.ih_patient.activity.order_physical_examination.view.PhysicalExaminationDetailView;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.bean.PhysicalExaInfo;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.PhysicalExaCommentsFragment;
import com.keydom.ih_patient.fragment.PhysicalExaDetailFragment;
import com.keydom.ih_patient.fragment.PhysicalExaProcessFragment;
import com.keydom.ih_patient.utils.SelectDialogUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 体检详情页面
 */
public class PhysicalExaminationDetailActivity extends BaseControllerActivity<PhysicalExaminationDetailController> implements PhysicalExaminationDetailView {
    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,PhysicalExaminationDetailActivity.class));
    }
    private TabLayout physical_exa_detail_tab;
    private ViewPager physical_exa_detail_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    private PhysicalExaInfo physicalExaInfo;
    private ImageView physical_exa_back_img,physical_project_icon_img;
    private TextView exa_price_tv,exa_sell_num_tv,exa_name_tv,exa_dsc_tv,exa_time_tv,exa_address_tv,exa_buy_tv;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_physical_examination_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().setVisibility(View.GONE);
        exa_price_tv=this.findViewById(R.id.exa_price_tv);
        exa_sell_num_tv=this.findViewById(R.id.exa_sell_num_tv);
        exa_name_tv=this.findViewById(R.id.exa_name_tv);
        exa_dsc_tv=this.findViewById(R.id.exa_dsc_tv);
        exa_time_tv=this.findViewById(R.id.exa_time_tv);
        exa_address_tv=this.findViewById(R.id.exa_address_tv);
        exa_buy_tv=findViewById(R.id.exa_buy_tv);
        exa_buy_tv.setOnClickListener(getController());
        physical_project_icon_img=this.findViewById(R.id.physical_project_icon_img);
        physical_exa_back_img=this.findViewById(R.id.physical_exa_back_img);
        physical_exa_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        physical_exa_detail_tab=this.findViewById(R.id.physical_exa_detail_tab);
        physical_exa_detail_vp=findViewById(R.id.physical_exa_detail_vp);
        physical_exa_detail_vp.setOffscreenPageLimit(3);
        list.add("套餐详情");
        list.add("预约流程");
        list.add("用户评论");
        fm=getSupportFragmentManager();
        fragmentList.add(new PhysicalExaDetailFragment());
        fragmentList.add(new PhysicalExaProcessFragment());
        fragmentList.add(new PhysicalExaCommentsFragment());
        if(viewPagerAdapter==null){
            viewPagerAdapter=new ViewPagerAdapter(fm,fragmentList,list);
        }
        physical_exa_detail_vp.setAdapter(viewPagerAdapter);
        physical_exa_detail_tab.setupWithViewPager(physical_exa_detail_vp);
        physicalExaInfo=Global.getSelectedPhysicalExa();
        exa_name_tv.setText(physicalExaInfo.getName());
        exa_sell_num_tv.setText("已售"+physicalExaInfo.getSold()+"份");
        exa_price_tv.setText("¥"+physicalExaInfo.getFee()+"元");
        exa_dsc_tv.setText(physicalExaInfo.getSummary());
        exa_time_tv.setText(physicalExaInfo.getCheckTimeDesc());
        exa_address_tv.setText(physicalExaInfo.getAddress());
        GlideUtils.load(physical_project_icon_img, physicalExaInfo.getIcon() == null ? "" : Const.IMAGE_HOST+physicalExaInfo.getIcon(), 0, 0, false, null);
    }

    @Override
    public void paySuccess() {
        finish();
    }

    @Override
    public void choosePayWay(SubscribeExaminationBean data) {
        SelectDialogUtils.showPayDialog(getContext(), String.valueOf(data.getFee()), "", new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                int payType = 0;
                if (type.equals(Type.WECHATPAY)){
                    payType = 1;
                }
                if (type.equals(Type.ALIPAY)){
                    payType = 2;
                }
                getController().goPayExaminationOrder(String.valueOf(payType),data);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
