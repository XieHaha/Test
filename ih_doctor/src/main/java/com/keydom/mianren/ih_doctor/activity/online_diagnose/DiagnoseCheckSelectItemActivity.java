package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.CheckOutParentBean;
import com.keydom.ih_common.bean.CheckOutSubBean;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter.DiagnoseCheckGroupAdapter;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter.DiagnoseCheckItemAdapter;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.DiagnoseCheckSelectItemController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnoseCheckSelectItemView;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：检验项目选择页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseCheckSelectItemActivity extends BaseControllerActivity<DiagnoseCheckSelectItemController> implements DiagnoseCheckSelectItemView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.search_input_ev)
    InterceptorEditText searchInputEv;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.test_group_item_rv)
    RecyclerView groupItemRv;
    @BindView(R.id.test_item_rv)
    RecyclerView itemRv;

    private DiagnoseCheckGroupAdapter checkGroupAdapter;
    private DiagnoseCheckItemAdapter checkItemAdapter;

    /**
     * 一级列表
     */
    private List<CheckOutParentBean> groupData = new ArrayList<>();
    /**
     * 二级项目
     */
    private List<CheckOutSubBean> itemData = new ArrayList<>();

    /**
     * 已选项目
     */
    private List<CheckOutParentBean> selectData;

    /**
     * 判断选择项目互斥
     */
    private String curSelectApplicationCode;
    private String curSelectExecuteDeptCode;

    /**
     * 当前选中的一级菜单
     */
    private CheckOutParentBean curSelectParent;
    private int curPosition = -1;


    /**
     * 启动检验项目选择页面
     *
     * @param selectedData 已经选择的检验项目列表
     */
    public static void start(Context context, List<CheckOutParentBean> selectedData) {
        Intent starter = new Intent(context, DiagnoseCheckSelectItemActivity.class);
        starter.putExtra(Const.DATA, (Serializable) selectedData);
        ((Activity) context).startActivityForResult(starter, Const.TEST_ITEM_SELECT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.test_item_select_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检验申请");
        setRightTxt("确定");
        selectData = (List<CheckOutParentBean>) getIntent().getSerializableExtra(Const.DATA);
        if (selectData == null) {
            selectData = new ArrayList<>();
        }

        checkGroupAdapter = new DiagnoseCheckGroupAdapter(groupData);
        checkGroupAdapter.setOnItemClickListener(this);
        groupItemRv.setAdapter(checkGroupAdapter);
        checkItemAdapter = new DiagnoseCheckItemAdapter(itemData);
        checkItemAdapter.setOnItemClickListener(this);
        itemRv.setAdapter(checkItemAdapter);

        initListener();
        getController().checkoutList();
    }

    private void initListener() {
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (selectData.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, (Serializable) selectData);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.showMessage(DiagnoseCheckSelectItemActivity.this, "请选择项目");
                }
            }
        });
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.hideSoftKeyboard(DiagnoseCheckSelectItemActivity.this);
            }
        });
    }

    @Override
    public void getGroupListSuccess(List<CheckOutParentBean> list) {
        groupData.clear();
        groupData.addAll(list);
        checkGroupAdapter.setSelectData(selectData);

        if (groupData.size() > 0) {
            curPosition = 0;
            curSelectParent = groupData.get(curPosition);
            getController().checkoutItemList(curSelectParent.getInsCheckCateCode());
        }
    }

    @Override
    public void getGroupListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getItemListSuccess(List<CheckOutSubBean> list) {
        itemData.clear();
        itemData.addAll(list);
        checkItemAdapter.setSelectCheck(selectData, selectData.indexOf(curSelectParent));
    }

    @Override
    public void getItemListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof DiagnoseCheckGroupAdapter) {
            if (curPosition == position) {
                return;
            }
            checkGroupAdapter.setCurPosition(position);
            //获取子菜单数据
            curPosition = position;
            curSelectParent = groupData.get(curPosition);
            getController().checkoutItemList(curSelectParent.getInsCheckCateCode());
        } else if (adapter instanceof DiagnoseCheckItemAdapter) {
            if (curSelectParent != null) {
                //项目互斥判断（code）
                CheckOutSubBean subBean = itemData.get(position);
                if (TextUtils.isEmpty(curSelectApplicationCode) || TextUtils.isEmpty(curSelectExecuteDeptCode)) {
                    curSelectApplicationCode = subBean.getApplicationCode();
                    curSelectExecuteDeptCode = subBean.getExecuteDeptCode();
                } else if (!TextUtils.equals(curSelectApplicationCode, subBean.getApplicationCode())
                        || !TextUtils.equals(curSelectExecuteDeptCode,
                        subBean.getExecuteDeptCode())) {
                    //所选项目code必须一致，否则不能选择
                    ToastUtil.showMessageLong(this, "存在执行科室和申请单不一致的项目!");
                    return;
                }

                CheckOutParentBean parentBean;
                ArrayList<CheckOutSubBean> subBeans;
                if (selectData.contains(curSelectParent)) {
                    parentBean = selectData.get(selectData.indexOf(curSelectParent));
                    subBeans = parentBean.getItems();
                } else {
                    parentBean = curSelectParent;
                    subBeans = new ArrayList<>();
                }

                //判断已选未选逻辑
                if (subBeans.contains(subBean)) {
                    subBeans.remove(subBean);
                } else {
                    subBeans.add(subBean);
                }
                if (subBeans.size() > 0) {
                    parentBean.setItems(subBeans);
                    if (!selectData.contains(parentBean)) {
                        selectData.add(parentBean);
                    }
                } else {
                    //无数据删除key
                    selectData.remove(parentBean);
                }

                //项目互斥逻辑 未选中项目时数据初始化
                if (selectData.size() == 0) {
                    curSelectApplicationCode = "";
                    curSelectExecuteDeptCode = "";
                }
                checkItemAdapter.setSelectCheck(selectData, selectData.indexOf(curSelectParent));
                checkGroupAdapter.setSelectData(selectData);
            }
        }
    }
}
