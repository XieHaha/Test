package com.keydom.ih_patient.activity.my_medical_card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.card_operate.CardoperateActivity;
import com.keydom.ih_patient.activity.my_medical_card.controller.MyMedicalCardController;
import com.keydom.ih_patient.activity.my_medical_card.view.MyMedicalCardView;
import com.keydom.ih_patient.adapter.MyMedicalCardAdapter;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.view.FunctionRvItemDecoration;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的就诊卡页面
 */
public class MyMedicalCardActivity extends BaseControllerActivity<MyMedicalCardController> implements MyMedicalCardView {
    private RecyclerView myMedicalCardRv;
    private List<MedicalCardInfo> dataList=new ArrayList<>();
    private MyMedicalCardAdapter myMedicalCardAdapter;
    private FunctionRvItemDecoration RvItemDecoration;
    private RelativeLayout comment_empty_layout;
    private TextView empty_text;

    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,MyMedicalCardActivity.class));
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_medical_card_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.my_medical_card_title));
        setRightTxt("办卡/绑卡");
        getTitleLayout().initViewsVisible(true,true,true);
        getTitleLayout().setOnRightTextClickListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                CardoperateActivity.start(getContext());
            }
        });
        myMedicalCardRv=this.findViewById(R.id.my_medical_card_rv);
        RvItemDecoration=new FunctionRvItemDecoration(0,80);
        myMedicalCardRv.addItemDecoration(RvItemDecoration);
        myMedicalCardAdapter=new MyMedicalCardAdapter(this,dataList);
        myMedicalCardRv.setAdapter(myMedicalCardAdapter);
        comment_empty_layout=this.findViewById(R.id.comment_empty_layout);
        empty_text=this.findViewById(R.id.empty_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e("onPause");
        getController().fillData();
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    @Override
    public void fillDataList(List<MedicalCardInfo> dataList) {
        if(dataList!=null&&dataList.size()!=0){
            if(myMedicalCardRv.getVisibility()==View.GONE){
                myMedicalCardRv.setVisibility(View.VISIBLE);
                comment_empty_layout.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            myMedicalCardAdapter.notifyDataSetChanged();
        }else {
            myMedicalCardRv.setVisibility(View.GONE);
            comment_empty_layout.setVisibility(View.VISIBLE);
            empty_text.setText("暂无就诊卡，请通过办卡绑卡添加");
        }

    }

    @Override
    public void fillDataListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),errMsg);
        if(myMedicalCardRv.getVisibility()==View.VISIBLE){
            myMedicalCardRv.setVisibility(View.GONE);
            comment_empty_layout.setVisibility(View.VISIBLE);
            empty_text.setText("数据加载失败，点击重新加载");
            comment_empty_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getController().fillData();
                }
            });
        }
    }
}
