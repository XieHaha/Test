package com.keydom.ih_patient.activity.order_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.controller.OrderExaminationController;
import com.keydom.ih_patient.activity.order_examination.view.OrderExaminationView;
import com.keydom.ih_patient.adapter.CardPopupWindowAdapter;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.OrderExaFragment;
import com.keydom.ih_patient.fragment.UnOrderExaFragment;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约订单页面
 */
public class OrderExaminationActivity extends BaseControllerActivity<OrderExaminationController> implements OrderExaminationView {
    private LinearLayout order_exa_base_layout;
    private TabLayout examination_order_tab;
    private ViewPager examination_order_vp;
    private FragmentManager fm;
    private ViewPagerAdapter viewPagerAdapter;
    /**
     * fragment集合
     */
    private List<android.support.v4.app.Fragment> fragmentList=new ArrayList<>();
    /**
     * 标题集合
     */
    private List<String> list=new ArrayList<>();

    private RelativeLayout card_layout;
    private ImageView order_doc_down_img;
    /**
     * 就诊卡集合
     */
    private List<MedicalCardInfo> cardList=new ArrayList<>();

    private TextView order_doc_name,order_card_num;
    /**
     * 就诊卡选择框
     */
    private PopupWindow cardpopupWindow;


    private TextView no_card_tv;

    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,OrderExaminationActivity.class));
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_examination_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检查预约");

        no_card_tv=this.findViewById(R.id.no_card_tv);

        order_exa_base_layout=this.findViewById(R.id.order_exa_base_layout);


        order_doc_name=this.findViewById(R.id.order_doc_name);
        order_card_num=this.findViewById(R.id.order_card_num);
        card_layout=this.findViewById(R.id.card_layout);
        card_layout.setOnClickListener(getController());

        examination_order_tab=this.findViewById(R.id.examination_order_tab);
        examination_order_vp=this.findViewById(R.id.examination_order_vp);

        list.add("未预约");
        list.add("已预约");
        fm=getSupportFragmentManager();
        fragmentList.add(new UnOrderExaFragment());
        fragmentList.add(new OrderExaFragment());
        if(viewPagerAdapter==null){
            viewPagerAdapter=new ViewPagerAdapter(fm,fragmentList,list);
        }
        examination_order_vp.setAdapter(viewPagerAdapter);
        examination_order_tab.setupWithViewPager(examination_order_vp);
        order_doc_down_img=this.findViewById(R.id.order_doc_down_img);
        getController().queryAllCard();
    }

    @Override
    public void toDoOrder(long inspectProjectId,String applyNumber) {
        ExaminationDateChooseActivity.start(getContext(),inspectProjectId,applyNumber);
    }


    @Override
    public void showCardPopupWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout,card_layout,false);
        View backgroudView = view.findViewById(R.id.backgroud_view);
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardpopupWindow.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.popup_rv);
        CardPopupWindowAdapter cardPopupWindowAdapter = new CardPopupWindowAdapter(this,cardList);
        recyclerView.setAdapter(cardPopupWindowAdapter);
        cardpopupWindow = new PopupWindow(getContext(), null,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardpopupWindow.setContentView(view);
        cardpopupWindow.setFocusable(true);
        cardpopupWindow.setWidth(card_layout.getWidth());
        cardpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroudView.setVisibility(View.INVISIBLE);
            }
        });
        cardpopupWindow.showAsDropDown(card_layout);
        backgroudView.setVisibility(View.VISIBLE);
    }

    /**
     * 保存选中就诊卡
     */
    public void saveSelectCard(MedicalCardInfo medicalCardInfo){
        order_doc_name.setText(medicalCardInfo.getName());
        order_card_num.setText("".equals(medicalCardInfo.getEleCardNumber())||medicalCardInfo.getEleCardNumber()==null?"就诊卡号："+medicalCardInfo.getEntCardNumber():"就诊卡号："+medicalCardInfo.getEleCardNumber());
        if("".equals(medicalCardInfo.getEleCardNumber())||medicalCardInfo.getEleCardNumber()==null){
            Global.setSelectedCardNum(medicalCardInfo.getEntCardNumber());
        }else {
            Global.setSelectedCardNum(medicalCardInfo.getEleCardNumber());
        }
        Logger.e("从saveSelectCard发送通知");
        EventBus.getDefault().post(new Event(EventType.UPLOADEXAMINATION,null));
        cardpopupWindow.dismiss();
    }



    @Override
    public void getAllCard(List<MedicalCardInfo> dataList) {
        if(dataList!=null&&dataList.size()!=0){
            cardList.clear();
            cardList.addAll(dataList);
            order_doc_name.setText(dataList.get(0).getName());
            order_card_num.setText("".equals(dataList.get(0).getEleCardNumber())||dataList.get(0).getEleCardNumber()==null?"就诊卡号：":"就诊卡号："+dataList.get(0).getEleCardNumber());
            Global.setSelectedCardNum(dataList.get(0).getEleCardNumber());
            EventBus.getDefault().post(new Event(EventType.UPLOADEXAMINATION,null));
        }else {
            no_card_tv.setVisibility(View.VISIBLE);
        }

    }











}
