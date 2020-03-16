package com.keydom.mianren.ih_patient.activity.postpartum_rehabilitation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.mianren.ih_patient.activity.postpartum_rehabilitation.controller.RehabilitationRecordController;
import com.keydom.mianren.ih_patient.activity.postpartum_rehabilitation.view.RehabilitationRecordView;
import com.keydom.mianren.ih_patient.adapter.RehabilitationRecordAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/2/25 17:01
 * @des 产后康复
 */
public class RehabilitationRecordActivity extends BaseControllerActivity<RehabilitationRecordController> implements RehabilitationRecordView, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_online)
    ImageView ivOnline;
    private ImageView imageView;
    private TextView tvTitle, tvTime, tvComment, tvAccolade;
    private RehabilitationRecordAdapter adapter;

    private ArrayList<String> data;
    private View headerView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_rehabilitation_list;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, RehabilitationRecordActivity.class));
    }

    @Override
    protected void onResume() {
        getController().getRehabilitationRecord();
        super.onResume();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("产后康复");
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getRehabilitationRecord());
        //模拟数据
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        adapter = new RehabilitationRecordAdapter(R.layout.item_rehabilitation_record, data);
        headerView = getLayoutInflater().inflate(R.layout.header_rehabilitation_record, null);
        bindHeaderView();
        adapter.addHeaderView(headerView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(adapter);

        ivOnline.setOnClickListener(this);

        pageLoading();
        setReloadListener((v, status) -> getController().getRehabilitationRecord());
    }

    /**
     * 置顶
     */
    private void bindHeaderView() {
        imageView = headerView.findViewById(R.id.iv_picture);
        tvTitle = headerView.findViewById(R.id.tv_title);
        tvTime = headerView.findViewById(R.id.tv_time);
        tvComment = headerView.findViewById(R.id.tv_comment);
        tvAccolade = headerView.findViewById(R.id.tv_accolade);
        tvComment.setOnClickListener(this);
        tvAccolade.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment:
                break;
            case R.id.tv_accolade:
                getController().getAccolade();
                break;
            case R.id.iv_online:
                DiagnoseMainActivity.start(this);
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_comment:
                //评论
                break;
            case R.id.tv_accolade:
                getController().getAccolade();
                break;
            case R.id.iv_follow:
                getController().getFollow();
                break;
            default:
                break;
        }
    }

    @Override
    public void fillRehabilitationRecordData(List<String> data) {
        pageLoadingSuccess();
    }

    @Override
    public void executeFollow() {
        ToastUtil.showMessage(this, "关注");
    }

    @Override
    public void executeAccolade() {
        ToastUtil.showMessage(this, "点赞");
    }
}
