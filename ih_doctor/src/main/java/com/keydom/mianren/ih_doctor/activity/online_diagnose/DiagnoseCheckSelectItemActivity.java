package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter.DiagnoseCheckGroupAdapter;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter.DiagnoseCheckItemAdapter;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.DiagnoseCheckSelectItemController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnoseCheckSelectItemView;
import com.keydom.mianren.ih_doctor.bean.CheckOutGroupBean;
import com.keydom.ih_common.bean.CheckOutParentBean;
import com.keydom.ih_common.bean.CheckOutSubBean;
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
    private List<CheckOutParentBean> selectData = new ArrayList<>();
    /**
     * 选择的项目（未数据处理）
     */
    private SparseArray<ArrayList<CheckOutSubBean>> selectCheck = new SparseArray<>();

    /**
     * 判断选择项目互斥
     */
    private String curSelectApplicationCode;
    private String curSelectExecuteDeptCode;

    /**
     * 当前选中的一级菜单
     */
    private CheckOutParentBean curSelectGroup;
    private int curPosition = -1;


    /**
     * 启动检验项目选择页面
     *
     * @param selectedData 已经选择的检验项目列表
     */
    public static void start(Context context, List<CheckOutGroupBean> selectedData) {
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
        //        selectedDatas = (List<CheckOutGroupBean>) getIntent().getSerializableExtra(Const
        //        .DATA);

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
                dealSelectList();
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

    /**
     * 已选数据
     */
    private void dealSelectList() {
        selectData.clear();
        int size = selectCheck.size();
        for (int i = 0; i < size; i++) {
            //得到key
            int key = selectCheck.keyAt(i);
            //得到父级项目
            CheckOutParentBean parentBean = groupData.get(key);
            //得到子级项目
            ArrayList<CheckOutSubBean> subBeans = selectCheck.get(key);
            if (parentBean != null && subBeans != null && subBeans.size() > 0) {
                parentBean.setItems(subBeans);
                selectData.add(parentBean);
            }
        }
    }

    @Override
    public void getItemListSuccess(List<CheckOutSubBean> list) {
        itemData.clear();
        itemData.addAll(list);
        checkItemAdapter.setSelectCheck(selectCheck, curPosition);
    }

    @Override
    public void getItemListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getGroupListSuccess(List<CheckOutParentBean> list) {
        groupData.clear();
        groupData.addAll(list);
        checkGroupAdapter.notifyDataSetChanged();

        if (groupData.size() > 0) {
            curPosition = 0;
            curSelectGroup = groupData.get(curPosition);
            getController().checkoutItemList(curSelectGroup.getInsCheckCateCode());
        }
    }

    @Override
    public void getGroupListFailed(String errMsg) {
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
            curSelectGroup = groupData.get(curPosition);
            getController().checkoutItemList(curSelectGroup.getInsCheckCateCode());
        } else if (adapter instanceof DiagnoseCheckItemAdapter) {
            if (curSelectGroup != null) {
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

                ArrayList<CheckOutSubBean> checkOutSubBeans = selectCheck.get(curPosition);
                if (checkOutSubBeans == null) {
                    checkOutSubBeans = new ArrayList<>();
                }
                //判断已选未选逻辑
                if (checkOutSubBeans.contains(subBean)) {
                    checkOutSubBeans.remove(subBean);
                } else {
                    checkOutSubBeans.add(subBean);
                }
                if (checkOutSubBeans.size() > 0) {
                    selectCheck.put(curPosition, checkOutSubBeans);
                } else {
                    //无数据删除key
                    selectCheck.delete(curPosition);
                }

                //项目互斥逻辑 未选中项目时数据初始化
                if (selectCheck.size() == 0) {
                    curSelectApplicationCode = "";
                    curSelectExecuteDeptCode = "";
                }
                checkItemAdapter.setSelectCheck(selectCheck, curPosition);
                checkGroupAdapter.setSelectCheck(selectCheck);
            }
        }
    }
}
