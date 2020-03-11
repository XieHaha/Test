package com.keydom.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisRecordController;
import com.keydom.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisRecordView;
import com.keydom.ih_patient.adapter.AmniocentesisRecordAdapter;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date 20/3/11 14:27
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordActivity extends BaseControllerActivity<AmniocentesisRecordController> implements AmniocentesisRecordView {
    @BindView(R.id.amniocentesis_record_search_layout)
    RelativeLayout amniocentesisRecordSearchLayout;
    @BindView(R.id.amniocentesis_record_recycler_view)
    RecyclerView amniocentesisRecordRecyclerView;
    @BindView(R.id.amniocentesis_record_refresh_layout)
    SmartRefreshLayout amniocentesisRecordRefreshLayout;

    private AmniocentesisRecordAdapter recordAdapter;

    private ArrayList<String> recordData;

    public static void start(Context context) {
        context.startActivity(new Intent(context, AmniocentesisRecordActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_amniocentesis_record_cancel));
        amniocentesisRecordRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getAmniocentesisRecord(TypeEnum.REFRESH));
        amniocentesisRecordRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getAmniocentesisRecord(TypeEnum.LOAD_MORE));

        //模拟数据
        recordData = new ArrayList<>();
        recordData.add("");
        recordData.add("");
        recordData.add("");
        recordData.add("");
        recordAdapter = new AmniocentesisRecordAdapter(recordData);
        recordAdapter.setOnItemClickListener(getController());
        recordAdapter.setOnItemChildClickListener(getController());
        amniocentesisRecordRecyclerView.setAdapter(recordAdapter);

        setReloadListener((v, status) -> {
            pageLoading();
            getController().getAmniocentesisRecord(TypeEnum.REFRESH);
        });

        pageLoading();
        getController().getAmniocentesisRecord(TypeEnum.REFRESH);
    }

    @Override
    public void onAmniocentesisRecordSuccess() {
        pageLoadingSuccess();
    }

    @Override
    public void onAmniocentesisRecordFailed() {
        pageLoadingFail();
    }
}
