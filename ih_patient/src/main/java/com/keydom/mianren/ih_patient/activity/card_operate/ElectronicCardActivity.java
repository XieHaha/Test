package com.keydom.mianren.ih_patient.activity.card_operate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.controller.ElectronicCardController;
import com.keydom.mianren.ih_patient.activity.card_operate.view.ElectronicCardView;
import com.keydom.mianren.ih_patient.adapter.ElectronicCardAdapter;
import com.keydom.mianren.ih_patient.bean.ElectronicCardRootBean;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author 顿顿
 * @date 20/9/22 15:17
 * @des 电子健康卡
 */
public class ElectronicCardActivity extends BaseControllerActivity<ElectronicCardController> implements ElectronicCardView {
    @BindView(R.id.electronic_card_name)
    TextView electronicCardName;
    @BindView(R.id.electronic_card_add_tv)
    TextView electronicCardAddTv;
    @BindView(R.id.mine_card_title_tv)
    TextView mineCardTitleTv;
    @BindView(R.id.mine_card_other_tv)
    TextView mineCardOtherTv;
    @BindView(R.id.electronic_card_layout)
    RelativeLayout electronicCardLayout;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.electronic_card_recycler_view)
    RecyclerView electronicCardRecyclerView;

    private ElectronicCardAdapter cardAdapter;

    private MedicalCardInfo mineCardInfo;
    private List<MedicalCardInfo> othersCardInfoList;

    /**
     * 启动方法
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ElectronicCardActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_electronic_card;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子健康卡");
        EventBus.getDefault().register(this);

        cardAdapter = new ElectronicCardAdapter(new ArrayList<>());
        cardAdapter.setOnItemClickListener(getController());
        electronicCardRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        electronicCardRecyclerView.setAdapter(cardAdapter);
        electronicCardAddTv.setOnClickListener(getController());
        electronicCardLayout.setOnClickListener(getController());

        getController().queryHealthCardList();
    }

    @Override
    public void queryHealthCardSuccess(ElectronicCardRootBean bean) {
        List<MedicalCardInfo> mine = bean.getMine();
        if (mine != null && mine.size() > 0) {
            layoutRoot.setVisibility(View.VISIBLE);
            mineCardInfo = mine.get(0);
            electronicCardName.setText(mineCardInfo.getName());
            mineCardTitleTv.setVisibility(View.VISIBLE);
            electronicCardLayout.setVisibility(View.VISIBLE);
        } else {
            mineCardTitleTv.setVisibility(View.GONE);
            electronicCardLayout.setVisibility(View.GONE);
        }

        othersCardInfoList = bean.getOthers();
        if (othersCardInfoList != null && othersCardInfoList.size() > 0) {
            layoutRoot.setVisibility(View.VISIBLE);
            mineCardOtherTv.setVisibility(View.VISIBLE);
        } else {
            mineCardOtherTv.setVisibility(View.GONE);
        }
        cardAdapter.setNewData(othersCardInfoList);
    }

    @Override
    public void queryHealthCardFailed(String msg) {

    }

    @Override
    public MedicalCardInfo getMineCardInfo() {
        return mineCardInfo;
    }

    /**
     * 保存就诊人成功事件监听
     */
    @Subscribe
    public void onAddOrEditResult(ManagerUserBean managerUserBean) {
        getController().queryHealthCardList();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
