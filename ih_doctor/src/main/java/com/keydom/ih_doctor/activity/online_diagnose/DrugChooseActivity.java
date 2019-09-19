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
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DrugChooseController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DrugChooseView;
import com.keydom.ih_doctor.adapter.DrugChooseAdapter;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.DrugListBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：药品选择页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DrugChooseActivity extends BaseControllerActivity<DrugChooseController> implements DrugChooseView {

    private RecyclerView recyclerView;
    /**
     * 药品适配器
     */
    private DrugChooseAdapter drugChooseAdapter;
    private EditText searchEt;
    private TextView searchTv;
    private RefreshLayout refreshLayout;
    /**
     * 药品列表
     */
    private List<DrugBean> mList = new ArrayList<>();
    /**
     * 已经选中的药品列表
     */
    private List<DrugBean> selectList;
    private int position;
	
	private String IsPrescriptionStyle=null;//院内处方标识

    /**
     * 启动药品选择页面
     *
     * @param context
     * @param list    已经选择了的药品
     */
    public static void start(Context context, List<DrugBean> list,int position) {
        Intent starter = new Intent(context, DrugChooseActivity.class);
        starter.putExtra(Const.DATA, (Serializable) list);
        starter.putExtra("position",position);
		starter.putExtra(Const.IsPrescriptionStyle, position + "");
        ((Activity) context).startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_medical_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        selectList = (List<DrugBean>) getIntent().getSerializableExtra(Const.DATA);
		position=getIntent().getIntExtra("position",0);
        IsPrescriptionStyle=getIntent().getStringExtra(Const.IsPrescriptionStyle);
        getController().getIsPrescriptionType(IsPrescriptionStyle);
        Logger.e("IsPrescriptionStyle="+IsPrescriptionStyle);
        setTitle("选择药品");
        setRightTxt("确定");
        initView();
        initList();
        if(IsPrescriptionStyle.equals("0")){
            getController().drugsList(TypeEnum.REFRESH);
        }else if(IsPrescriptionStyle.equals("1")){
            getController().drugsListWaiYan(TypeEnum.REFRESH);
        }


        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (drugChooseAdapter.getSelectList() == null || drugChooseAdapter.getSelectList().size() == 0) {
                    ToastUtil.shortToast(DrugChooseActivity.this, "请选择药品后再提交");
                    return;
                } else {
                    DrugListBean drugListBean=new DrugListBean();
                    drugListBean.setPosition(position);
                    drugListBean.setDrugList(drugChooseAdapter.getSelectList());
                    DrugUseActivity.start(DrugChooseActivity.this,drugListBean);
                }

            }
        });
    }

    /**
     * 设置药品列表
     */
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        drugChooseAdapter = new DrugChooseAdapter(getListWithCompare(mList, selectList));
        recyclerView.setAdapter(drugChooseAdapter);
    }

    private List<DrugBean> getListWithCompare(List<DrugBean> dataList,List<DrugBean> seletedList){
        if(seletedList!=null&&seletedList.size()>0){
            for (int i=0;i<seletedList.size();i++){
                for (int j = 0; j <dataList.size() ; j++) {
                    if(dataList.get(j).getId()==seletedList.get(i).getId())
                        dataList.get(j).setSelecte(true);
                }
            }
            List <DrugBean> finalList=new ArrayList<>();
            for (int i = 0; i <dataList.size() ; i++) {
                if(!dataList.get(i).isSelecte())
                    finalList.add(dataList.get(i));
            }
            return finalList;
        }else
            return dataList;

    }


    /**
     * 初始化页面
     */
    private void initView() {
        recyclerView = this.findViewById(R.id.medical_rv);
        searchEt = this.findViewById(R.id.search_et);
        searchTv = this.findViewById(R.id.search_tv);
        refreshLayout = this.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        searchTv.setOnClickListener(getController());
    }

    @Override
    public Map<String, Object> getDrugListMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }
    @Override
    public Map<String, Object> getDrugListWaiYanMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("param", searchEt.getText().toString().trim());
        return map;
    }
    @Override
    public void getDrugListSuccess(List<DrugBean> list, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        mList.addAll(getListWithCompare(list, selectList));
        drugChooseAdapter.setNewData(mList);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getDrugListFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
