package com.keydom.mianren.ih_patient.activity.chronic_disease.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.chronic_disease.controller.LifestyleDataFragController;
import com.keydom.mianren.ih_patient.activity.chronic_disease.view.LifestyleDataFragView;
import com.keydom.mianren.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.mianren.ih_patient.adapter.PayRecordAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 生活方式数据
 *
 * @author 顿顿
 */
public class LifestyleDataFragment extends BaseControllerFragment<LifestyleDataFragController> implements LifestyleDataFragView {
    @BindView(R.id.frag_lifestyle_data_recycler_view)
    RecyclerView fragLifestyleDataRecyclerView;
    @BindView(R.id.frag_lifestyle_data_refresh_layout)
    SmartRefreshLayout fragLifestyleDataRefreshLayout;
    public static final String STATE = "state";

    private PayRecordAdapter mPayRecordAdapter;
    /**
     * 缴费状态
     */
    private int mState;
    private long patientId;

    /**
     * fragment创建
     */
    public static LifestyleDataFragment newInstance(long patientId, int state) {
        Bundle args = new Bundle();
        args.putInt(STATE, state);
        args.putLong(Const.PATIENT_ID, patientId);
        LifestyleDataFragment fragment = new LifestyleDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_lifestyle_data_layout;
    }

    @Subscribe
    public void paymentSuccess(Event event) {
        if (event.getType() == EventType.PAYMENT_SUCCESS && mState == PaymentRecordActivity.PAYED) {
            fragLifestyleDataRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
    }

}
