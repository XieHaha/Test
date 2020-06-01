package com.keydom.mianren.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.InspactItemSelectRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.m_interface.SelectInspectItemListener;
import com.keydom.mianren.ih_doctor.utils.CloneUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 选择检查项目dialog
 **/
public class DiagnoseInspactItemDialog extends Dialog implements View.OnClickListener {

    private ImageView cancelBtn;
    private TextView dialogSubmit;
    private Context mContext;
    private InspactItemSelectRecyclrViewAdapter inspactItemSelectRecyclrViewAdapter;
    private View.OnClickListener cancelListener;
    private RecyclerView recyclerView;
    List<CheckOutGroupBean> mList;
    private SelectInspectItemListener listener;


    /**
     * @param context
     */
    public DiagnoseInspactItemDialog(Context context, List<CheckOutGroupBean> list, SelectInspectItemListener listener) {
        super(context, R.style.dialogFullscreen);
        mContext = context;
        this.mList = list;
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_inspact_item_select_layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cancelBtn = (ImageView) findViewById(R.id.dialog_close);
        dialogSubmit = (TextView) findViewById(R.id.dialog_submit);
        recyclerView = (RecyclerView) findViewById(R.id.inspact_list_rv);
        cancelBtn.setOnClickListener(this);
        dialogSubmit.setOnClickListener(this);
        inspactItemSelectRecyclrViewAdapter = new InspactItemSelectRecyclrViewAdapter(mContext, mList);
        recyclerView.setAdapter(inspactItemSelectRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (CheckOutGroupBean bean : mList) {
//                    if (bean.isSelect()) {
//                        bean.setSelect(false);
//                    }
                }
            }
        });
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
        } else if (id == R.id.dialog_submit) {
            dismiss();
            if (listener != null) {
                listener.selectItem(getSelectList());
            }
        }
    }

    public List<CheckOutGroupBean> getSelectList() {
        List<CheckOutGroupBean> selectList = new ArrayList<>();
        if (mList == null || mList.size() == 0) {
            return selectList;
        }
        for (CheckOutGroupBean bean : mList) {
            if (bean.isSelect()) {
                try {
                    selectList.add(CloneUtil.clone(bean));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return selectList;
    }

    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

}
