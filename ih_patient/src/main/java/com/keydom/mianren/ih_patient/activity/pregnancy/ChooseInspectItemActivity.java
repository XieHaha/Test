package com.keydom.mianren.ih_patient.activity.pregnancy;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.ChooseInspectItemController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.ChooseInspectItemView;
import com.keydom.mianren.ih_patient.adapter.InspectItemSelectRecyclerViewAdapter;
import com.keydom.mianren.ih_patient.bean.CheckProjectSubBean;
import com.keydom.mianren.ih_patient.constant.Const;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class ChooseInspectItemActivity extends BaseControllerActivity<ChooseInspectItemController> implements ChooseInspectItemView {
    /**
     * 回传选中数据code
     */
    public static final int CHOOSE_INSPECT_ITEM = 1001;
    /**
     * 检查项目列表
     */
    private static final String INSPECT_LIST = "inspect_list";
    /**
     * 已经选中的检查项目列表
     */
    private static final String SELECT_LIST = "select_list";
    /**
     * 检查项目列表适配器
     */
    private InspectItemSelectRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    /**
     * 所有检查项目列表
     */
    List<CheckProjectSubBean> mList;
    /**
     * 选择的检查项目列表
     */
    List<CheckProjectSubBean> mSelectList;

    /**
     * 启动检查单选择页面
     *
     * @param mList 检查项目列表
     */
    public static void start(Context context, List<CheckProjectSubBean> mList,
                             List<CheckProjectSubBean> mSelectList) {
        Intent starter = new Intent(context, ChooseInspectItemActivity.class);
        starter.putExtra(SELECT_LIST, (Serializable) mSelectList);
        starter.putExtra(INSPECT_LIST, (Serializable) mList);
        ((Activity) context).startActivityForResult(starter, CHOOSE_INSPECT_ITEM);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_inspect_list_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        recyclerView = this.findViewById(R.id.inspect_list_rv);
        this.mSelectList =
                (List<CheckProjectSubBean>) getIntent().getSerializableExtra(SELECT_LIST);
        this.mList = (List<CheckProjectSubBean>) getIntent().getSerializableExtra(INSPECT_LIST);
        setSelect();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new InspectItemSelectRecyclerViewAdapter(this, mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        setRightTxt("确定");
        setTitle("检验检查项目");
        setRightBtnListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Const.DATA, (Serializable) getSelectItem());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    /**
     * 设置选中项
     */
    private void setSelect() {
        if (mList != null && mSelectList != null) {
            for (CheckProjectSubBean bean : mList) {
                for (CheckProjectSubBean selectBean : mSelectList) {
                    if (bean.getItemCode().equals(selectBean.getItemCode())) {
                        bean.setSelect(true);
                    }
                }
            }
        }
    }

    @Override
    public List<CheckProjectSubBean> getSelectItem() {
        List<CheckProjectSubBean> result = new ArrayList<>();
        for (CheckProjectSubBean bean : mList) {
            if (bean.isSelect()) {
                result.add(bean);
            }
        }
        return result;
    }
}
