package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.PrescriptionTempletDetailActivity;
import com.keydom.mianren.ih_doctor.adapter.PrescriptionTempletAdapter;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTempletBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.PrescriptionTempletFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.PrescriptionTempletFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：问诊订单页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PrescriptionTempletFragment extends BaseControllerFragment<PrescriptionTempletFragmentController> implements PrescriptionTempletFragmentView {

    /**
     * 医院模版类型
     */
    public static final String HOSPITAL = "1";
    /**
     * 个人模版类型
     */
    public static final String PERSONAL = "0";

    private TextView type_tv, search_tv;
    private EditText search_et;
    private RecyclerView recyclerView;
    private PrescriptionTempletAdapter prescriptionTempletAdapter;
    private List<PrescriptionTempletBean> dataList = new ArrayList<>();
    private List<PrescriptionTempletBean> tempList = new ArrayList<>();
    private String mType = PERSONAL;
    private int isOutPrescription = 0;


    public static final PrescriptionTempletFragment newInstance(TypeEnum type) {
        PrescriptionTempletFragment fragment = new PrescriptionTempletFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_prescription_templet_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TypeEnum type = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        if (type == TypeEnum.INSIDE_PRESCRIPTION) {
            isOutPrescription = 0;
        } else if (type == TypeEnum.OUTSIDE_PRESCRIPTION) {
            isOutPrescription = 1;
        }
        type_tv = getView().findViewById(R.id.type_tv);
        search_tv = getView().findViewById(R.id.search_tv);
        search_et = getView().findViewById(R.id.search_et);
        recyclerView = getView().findViewById(R.id.recyclerView);
        type_tv.setOnClickListener(getController());
        prescriptionTempletAdapter = new PrescriptionTempletAdapter(dataList);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                searchTemplet(search_et.getText().toString().trim());
                CommonUtils.hideSoftKeyboard(getActivity());
            }
        });
        prescriptionTempletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PrescriptionTempletDetailActivity.start(getContext(), prescriptionTempletAdapter.getData().get(position).getId(), prescriptionTempletAdapter.getData().get(position).getTemplateName());
            }
        });
        recyclerView.setAdapter(prescriptionTempletAdapter);
        getController().getPrescriptionTempletList();
    }

    /**
     * 关键字搜索模版
     * @param key
     */
    private void searchTemplet(String key) {
        dataList.clear();
        dataList.addAll(tempList);
        Iterator<PrescriptionTempletBean> it = dataList.iterator();
        while (it.hasNext()) {
            if (!it.next().getTemplateName().contains(key)) {
                it.remove();
            }
        }
        prescriptionTempletAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTempletListSuccess(List<PrescriptionTempletBean> data) {
        tempList.addAll(data);
        this.dataList = data;
        prescriptionTempletAdapter.setNewData(dataList);
    }

    @Override
    public void getTempletListFailed(String errMsg) {
        pageLoadingFail();
    }


    @Override
    public void setDept(String dept, String type) {
        type_tv.setText(dept);
        mType = type;
    }

    @Override
    public Map<String,Object> getRequestType() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", mType);
        map.put("isOutPrescription", isOutPrescription);
        return map;
    }
}
