package com.keydom.mianren.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.controller.MyEvaluationController;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyEvaluationView;
import com.keydom.mianren.ih_doctor.adapter.EvaluationListRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.EvaluationBean;
import com.keydom.mianren.ih_doctor.bean.EvaluationRes;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的评论页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyEvaluationActivity extends BaseControllerActivity<MyEvaluationController> implements MyEvaluationView {

    /**
     * 今日评价
     */
    public static final int TODAY_EVALUATION = 1;
    /**
     * 所有评价
     */
    public static final int TOTAL_EVALUATION = 2;
    /**
     * 默认评价
     */
    public static final int DEFAULT_EVALUATION = 3;
    /**
     * 评价列表
     */
    private List<EvaluationBean> mList = new ArrayList<>();
    private RecyclerView evaluationRv;
    private EvaluationListRecyclrViewAdapter evaluationListRecyclrViewAdapter;
    private RefreshLayout refreshLayout;
    private Button diagnose, consult;
    /**
     * 请求类型
     */
    private int reqType;
    /**
     * 问诊单
     */
    private String DIAGNOSE = "1";
    /**
     * 咨询单
     */
    private String CONSULT = "2";
    private String mType = DIAGNOSE;

    /**
     * 启动
     */
    public static void start(Context context, int type) {
        Intent starter = new Intent(context, MyEvaluationActivity.class);
        starter.putExtra(Const.DATA, type);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_evaluation;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        reqType = getIntent().getIntExtra(Const.DATA, -1);
        if (reqType == TODAY_EVALUATION) {
            setTitle("今日好评");
        } else if (reqType == TOTAL_EVALUATION) {
            setTitle("累计好评");
        } else {
            setTitle("患者评价");
        }
        evaluationRv = this.findViewById(R.id.evaluation_rv);
        refreshLayout = this.findViewById(R.id.refresh_layout);
        diagnose = this.findViewById(R.id.diagnose);
        consult = this.findViewById(R.id.consult);
        evaluationListRecyclrViewAdapter = new EvaluationListRecyclrViewAdapter(mList, this);
        evaluationRv.setAdapter(evaluationListRecyclrViewAdapter);
        evaluationRv.setLayoutManager(new LinearLayoutManager(this));
        evaluationRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        diagnose.setOnClickListener(getController());
        consult.setOnClickListener(getController());
        pageLoading();
        getController().getEvaluation(TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getEvaluation(TypeEnum.REFRESH);
            }
        });
    }

    @Override
    public void getEvaluationSuccess(EvaluationRes bean, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        pageLoadingSuccess();
        mList.addAll(bean.getRecords());
        evaluationListRecyclrViewAdapter.notifyDataSetChanged();
        if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
            diagnose.setText("护理(" + bean.getInquiryCount() + ")");
            consult.setText("咨询(" + bean.getConsultCount() + ")");
        }else{
            diagnose.setText("问诊(" + bean.getInquiryCount() + ")");
            consult.setText("咨询(" + bean.getConsultCount() + ")");
        }
        getController().currentPagePlus();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getEvaluationFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void setDiagnose() {
        pageLoading();
        diagnose.setBackgroundResource(R.drawable.evaluation_sel_bg);
        consult.setBackgroundResource(R.drawable.evaluation_nol_bg);
        consult.setTextColor(getResources().getColor(R.color.fontClickEnable));
        diagnose.setTextColor(getResources().getColor(R.color.white));
        mType = DIAGNOSE;
        getController().setDefaultPage();
        getController().getEvaluation(TypeEnum.REFRESH);
    }

    @Override
    public void setConsult() {
        pageLoading();
        consult.setBackgroundResource(R.drawable.evaluation_sel_bg);
        diagnose.setBackgroundResource(R.drawable.evaluation_nol_bg);
        diagnose.setTextColor(getResources().getColor(R.color.fontClickEnable));
        consult.setTextColor(getResources().getColor(R.color.white));
        mType = CONSULT;
        getController().setDefaultPage();
        getController().getEvaluation(TypeEnum.REFRESH);
    }

    @Override
    public int getReqType() {
        return reqType;
    }
}
