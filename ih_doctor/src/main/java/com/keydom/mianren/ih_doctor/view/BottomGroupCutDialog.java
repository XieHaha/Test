package com.keydom.mianren.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.GroupCutDialogRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;

import java.util.List;


/**
 * [底部弹出dialog]
 **/
public class BottomGroupCutDialog extends Dialog implements View.OnClickListener {

    private ImageView cancelBtn;
    private Context mContext;
    private GroupCutDialogRecyclrViewAdapter groupCutDialogRecyclrViewAdapter;
    private View.OnClickListener cancelListener;
    private RecyclerView recyclerView;
    List<GroupInfoBean> mList;


    /**
     * @param context
     */
    public BottomGroupCutDialog(Context context, List<GroupInfoBean> list) {
        super(context, R.style.dialogFullscreen);
        mContext = context;
        this.mList = list;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_group_cut_layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cancelBtn = (ImageView) findViewById(R.id.dialog_close);
        recyclerView = (RecyclerView) findViewById(R.id.group_list_rv);
        cancelBtn.setOnClickListener(this);
        groupCutDialogRecyclrViewAdapter = new GroupCutDialogRecyclrViewAdapter(mContext, mList, BottomGroupCutDialog.this);
        recyclerView.setAdapter(groupCutDialogRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_close) {
            if (cancelListener != null) {
                cancelListener.onClick(v);
            }
            dismiss();
            return;
        }
    }


    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

}
