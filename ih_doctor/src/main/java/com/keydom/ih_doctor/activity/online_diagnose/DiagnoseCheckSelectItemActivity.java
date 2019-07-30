package com.keydom.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DiagnoseCheckSelectItemController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseCheckSelectItemView;
import com.keydom.ih_doctor.adapter.DiagnoseOrderSecondaryListRecyclerAdapter;
import com.keydom.ih_doctor.adapter.SecondaryListAdapter;
import com.keydom.ih_doctor.bean.CheckOutItemBean;
import com.keydom.ih_doctor.constant.Const;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：检验项目选择页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseCheckSelectItemActivity extends BaseControllerActivity<DiagnoseCheckSelectItemController> implements DiagnoseCheckSelectItemView {

    private RecyclerView recyclerView;
    private EditText searchEt;
    private TextView searchTv;
    /**
     * 检验项目列表适配器
     */
    DiagnoseOrderSecondaryListRecyclerAdapter adapter;
    /**
     * 检验项目列表数据
     */
    private List<SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean>> datas = new ArrayList<>();
    /**
     * 选中的检验项目列表
     */
    private List<CheckOutItemBean> selectedDatas = new ArrayList<>();
    /**
     * 备份列表（搜索用）
     */
    private List<SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean>> tempDatas = new ArrayList<>();

    /**
     * 启动检验项目选择页面
     *
     * @param context
     * @param selectedDatas 已经选择的检验项目列表
     */
    public static void start(Context context, List<CheckOutItemBean> selectedDatas) {
        Intent starter = new Intent(context, DiagnoseCheckSelectItemActivity.class);
        starter.putExtra(Const.DATA, (Serializable) selectedDatas);
        ((Activity) context).startActivityForResult(starter, Const.TEST_ITEM_SELECT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.test_item_select_list;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("检验申请");
        setRightTxt("确定");
        selectedDatas = (List<CheckOutItemBean>) getIntent().getSerializableExtra(Const.DATA);
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Const.DATA, (Serializable) getSelectList());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        initView();
        getController().checkoutList();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        recyclerView = this.findViewById(R.id.test_item_rv);
        searchEt = this.findViewById(R.id.search_input_ev);
        searchTv = this.findViewById(R.id.search_tv);
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMember(searchEt.getText().toString());
                CommonUtils.hideSoftKeyboard(DiagnoseCheckSelectItemActivity.this);
            }
        });
    }

    /**
     * 关键字搜索
     *
     * @param key 关键字
     */
    private void searchMember(String key) {
        datas.clear();
        datas.addAll(tempDatas);
        Iterator<SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean>> it = datas.iterator();
        while (it.hasNext()) {
            SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean> item = it.next();
            if (!item.getGroupItem().getName().contains(key) && !item.getGroupItem().filter(key)) {
                it.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置列表数据
     *
     * @param list 检验项目列表
     */
    private void initListData(List<CheckOutItemBean> list) {
        for (int i = 0; i < list.size(); i++) {
            SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean> item = null;
            for (CheckOutItemBean selectedItem : selectedDatas) {
                if (list.get(i).getProjectId() == selectedItem.getProjectId()) {
                    item = new SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean>(selectedItem, selectedItem.getItems());
                }
            }
            if (item == null) {
                item = new SecondaryListAdapter.DataTree<CheckOutItemBean, CheckOutItemBean>(list.get(i), list.get(i).getItems());
            }
            datas.add(item);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new DiagnoseOrderSecondaryListRecyclerAdapter(this, false);
        adapter.setData(datas);
        tempDatas.clear();
        tempDatas.addAll(datas);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取选中的项目列表
     *
     * @return
     */
    private List<CheckOutItemBean> getSelectList() {
        List<CheckOutItemBean> resList = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getGroupItem().isSelect()) {
                    resList.add(datas.get(i).getGroupItem());
                }
            }
        }
        return resList;
    }

    @Override
    public void getItemListSuccess(List<CheckOutItemBean> list) {
        initListData(list);
    }

    @Override
    public void getItemListFailed(String errMsg) {

    }

}
