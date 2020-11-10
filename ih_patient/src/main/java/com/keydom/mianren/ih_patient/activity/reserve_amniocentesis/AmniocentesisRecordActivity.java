package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.RegexUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisRecordController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisRecordView;
import com.keydom.mianren.ih_patient.adapter.AmniocentesisRecordAdapter;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/11 14:27
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordActivity extends BaseControllerActivity<AmniocentesisRecordController> implements AmniocentesisRecordView {
    @BindView(R.id.amniocentesis_record_search_layout)
    RelativeLayout amniocentesisRecordSearchLayout;
    @BindView(R.id.amniocentesis_record_search_tv)
    InterceptorEditText amniocentesisRecordSearchTv;
    @BindView(R.id.amniocentesis_record_recycler_view)
    RecyclerView amniocentesisRecordRecyclerView;
    @BindView(R.id.amniocentesis_record_refresh_layout)
    SmartRefreshLayout amniocentesisRecordRefreshLayout;

    private AmniocentesisRecordAdapter recordAdapter;

    private ArrayList<AmniocentesisBean> recordData;

    private String searchKey;

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
        amniocentesisRecordRefreshLayout.setOnRefreshListener(refreshLayout -> getReserveData());
        amniocentesisRecordRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
                    getController().currentPagePlus();
                    getController().getAmniocentesisRecord(searchKey, TypeEnum.LOAD_MORE);
                }
        );

        //模拟数据
        recordData = new ArrayList<>();
        recordAdapter = new AmniocentesisRecordAdapter(recordData);
        recordAdapter.setOnItemClickListener(getController());
        recordAdapter.setOnItemChildClickListener(getController());
        amniocentesisRecordRecyclerView.setAdapter(recordAdapter);

        amniocentesisRecordSearchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchKey = s.toString();
                if (searchKey.length() < 18) {
                    return;
                }
                if (!RegexUtils.isIDCard18(searchKey)) {
                    ToastUtil.showMessage(AmniocentesisRecordActivity.this, "请输入正确的身份证号");
                    return;
                }
                getReserveData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //        getController().getAmniocentesisRecord("", TypeEnum.REFRESH, true);
    }

    /**
     * 搜索
     */
    private void getReserveData() {
        getController().getAmniocentesisRecord(searchKey, TypeEnum.REFRESH);
    }

    @Override
    public void onAmniocentesisRecordSuccess(TypeEnum typeEnum, List<AmniocentesisBean> records) {
        if (typeEnum == TypeEnum.REFRESH) {
            recordData.clear();
        }
        recordData.addAll(records);
        recordAdapter.notifyDataSetChanged();
        amniocentesisRecordRefreshLayout.finishRefresh();
        if (records.size() >= 8) {
            amniocentesisRecordRefreshLayout.finishLoadMore();
            amniocentesisRecordRefreshLayout.setNoMoreData(false);
        } else {
            amniocentesisRecordRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onAmniocentesisRecordFailed() {
        amniocentesisRecordRefreshLayout.finishRefresh();
        amniocentesisRecordRefreshLayout.finishLoadMore();
    }

    @Override
    public void onAmniocentesisCancelSuccess(int position) {
        ToastUtil.showMessage(this, "操作成功");
        recordData.remove(position);
        recordAdapter.notifyItemRemoved(position);
        recordAdapter.notifyItemRangeChanged(position, recordAdapter.getItemCount() - position);
    }
}
