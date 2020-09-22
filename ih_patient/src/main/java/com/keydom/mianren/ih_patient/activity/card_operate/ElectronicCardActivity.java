package com.keydom.mianren.ih_patient.activity.card_operate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.controller.ElectronicCardController;
import com.keydom.mianren.ih_patient.activity.card_operate.view.ElectronicCardView;
import com.keydom.mianren.ih_patient.adapter.ElectronicCardAdapter;
import com.keydom.mianren.ih_patient.bean.ElectronicCardRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

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
    @BindView(R.id.electronic_card_layout)
    RelativeLayout electronicCardLayout;
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

        cardAdapter = new ElectronicCardAdapter(new ArrayList<>());
        cardAdapter.setOnItemClickListener(getController());
        electronicCardRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        electronicCardRecyclerView.setAdapter(cardAdapter);
        electronicCardLayout.setOnClickListener(getController());

        getController().queryHealthCardList();
    }

    @Override
    public void queryHealthCardSuccess(ElectronicCardRootBean bean) {
        List<MedicalCardInfo> mine = bean.getMine();
        if (mine != null && mine.size() > 0) {
            mineCardInfo = mine.get(0);
            electronicCardName.setText(mineCardInfo.getName());
        }
        othersCardInfoList = bean.getOthers();
        cardAdapter.setNewData(othersCardInfoList);
    }

    @Override
    public void queryHealthCardFailed(String msg) {

    }

    @Override
    public MedicalCardInfo getMineCardInfo() {
        return mineCardInfo;
    }
}
