package com.keydom.mianren.ih_patient.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.TimeSchedualDialogAdapter;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;

import java.util.List;

/**
 * @author 顿顿
 */
public class TimeSchedualDialog extends BottomSheetDialog {
    private Context mContext;
    private TextView titleTv;
    private RecyclerView recyclerView;
    private TimeSchedualDialogAdapter adapter;

    private List<PregnancyOrderTime> dataList;

    private OnSelectListener onSelectListener;

    public TimeSchedualDialog(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context, com.keydom.ih_common.R.style.dialog);
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }

    public void setDataList(List<PregnancyOrderTime> dataList) {
        this.dataList = dataList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_doctor_schedual);
        int screenHeight = getScreenHeight((Activity) mContext);
        int statusBarHeight = getStatusBarHeight(mContext);
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ?
                ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        titleTv = findViewById(R.id.dialog_title_tv);
        titleTv.setText("选择预约时段");
        recyclerView = findViewById(R.id.dialog_doctor_recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new FunctionRvItemDecoration(20, 20));
        adapter = new TimeSchedualDialogAdapter(getContext(), dataList, new OnSelectListener() {
            @Override
            public void onSelected(PregnancyOrderTime bean) {
                if (onSelectListener != null) {
                    onSelectListener.onSelected(bean);
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public interface OnSelectListener {
        void onSelected(PregnancyOrderTime bean);
    }
}
