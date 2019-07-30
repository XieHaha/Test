package com.keydom.ih_patient.activity.diagnose_user_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.controller.AnamnesisController;
import com.keydom.ih_patient.activity.diagnose_user_manager.view.AnamnesisView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HistoryListBean;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.EventType;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * created date: 2018/12/15 on 10:48
 * des:既往病史页面
 */
public class AnamnesisActivity extends BaseControllerActivity<AnamnesisController> implements AnamnesisView {
    public static String MANAGER_USER_BEAN = "manager_user_bean";
    public static String STATUS = "status";
    public static String ISFROMDIAGNOSEAPPLY = "is_from_diagnose_apply";
    private TagFlowLayout mMarryFlow;
    private TagFlowLayout mSurgeryFlow;
    private TagFlowLayout mFamilyFlow;
    private TagFlowLayout mPersonFlow;
    private TagFlowLayout mAllergyFlow;
    private EditText mSurgeryEdit;
    private EditText mFamilyEdit;
    private EditText mAllergyEdit;

    private List<String> mMarryTags;
    private List<String> mSurgeryTags;
    private List<String> mFamilyTags;
    private List<String> mPersonTags;
    private List<String> mAllergyTags;

    private ManagerUserBean mManager;
    private int mStatus;
    private boolean isFromDiagnoseApply = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_anamnesis;
    }

    /**
     * 查找控件
     */
    private void getView() {
        mMarryFlow = findViewById(R.id.married_flow);
        mSurgeryFlow = findViewById(R.id.surgery_flow);
        mFamilyFlow = findViewById(R.id.family_flow);
        mPersonFlow = findViewById(R.id.person_flow);
        mAllergyFlow = findViewById(R.id.allergy_flow);
        mSurgeryEdit = findViewById(R.id.surgery_edit);
        mFamilyEdit = findViewById(R.id.family_edit);
        mAllergyEdit = findViewById(R.id.allergy_edit);
        findViewById(R.id.save).setOnClickListener(getController());

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("既往病史");
        getView();
        getController().getHistoryList();
        mManager = (ManagerUserBean) getIntent().getSerializableExtra(MANAGER_USER_BEAN);
        mStatus = getIntent().getIntExtra(STATUS, 0);
        isFromDiagnoseApply = getIntent().getBooleanExtra(ISFROMDIAGNOSEAPPLY, false);
        mMarryTags = new ArrayList<>();
        mSurgeryTags = new ArrayList<>();
        mFamilyTags = new ArrayList<>();
        mPersonTags = new ArrayList<>();
        mAllergyTags = new ArrayList<>();
    }

    /**
     * 添加选择flowlayout
     */
    private void setFlows(final TagFlowLayout flowLayout, String historyStr, String selectHis) {
        if (historyStr != null) {
            if (selectHis == null) {
                selectHis = "";
            }
            List<Integer> curr = new ArrayList<>();
            Set<Integer> selects = new HashSet<>();
            List marryList = Arrays.asList(historyStr.split(","));

            if (flowLayout == mMarryFlow) {
                mMarryTags = marryList;
            }
            if (flowLayout == mSurgeryFlow) {
                mSurgeryTags = marryList;
            }
            if (flowLayout == mPersonFlow) {
                mPersonTags = marryList;
            }
            if (flowLayout == mFamilyFlow) {
                mFamilyTags = marryList;
            }
            if (flowLayout == mAllergyFlow) {
                mAllergyTags = marryList;
            }
            List<String> adapterList = new ArrayList<>();
            for (int i = 0; i < selectHis.split(",").length; i++) {
                adapterList.add(String.valueOf(selectHis.split(",")[i]));
            }
            if (!StringUtils.isEmpty(selectHis)) {
                for (int i = 0; i < marryList.size(); i++) {
                    for (int j = 0; j < adapterList.size(); j++) {
                        if (adapterList.get(j).equals(marryList.get(i))) {
                            selects.add(i);
                            adapterList.remove(j);
                        }
                    }
                }
            }
            TagAdapter<String> tagAdapter = new TagAdapter<String>(marryList) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.manager_user_history_flow_layout,
                            flowLayout, false);
                    tv.setText(o);
                    return tv;
                }
            };
            flowLayout.setAdapter(tagAdapter);
            if (mStatus == AddManageUserActivity.UPDATE) {
                   tagAdapter.setSelectedList(selects);
                StringBuilder edit = new StringBuilder();
                for (int i = 0; i < adapterList.size(); i++) {
                    edit.append(adapterList.get(i));
                    if (adapterList.size() - 1 != i) {
                        edit.append(",");
                    }
                }
                if (flowLayout == mSurgeryFlow) {
                    mSurgeryEdit.setText(edit);
                }
                if (flowLayout == mFamilyFlow) {
                    mFamilyEdit.setText(edit);
                }
                if (flowLayout == mAllergyFlow) {
                    mAllergyEdit.setText(edit);
                }
            }
        }
    }

    /**
     * 上传添加病史tag
     */
    private StringBuffer jointTags(Set<Integer> flowsSelect, List<String> tags) {
        StringBuffer joint = new StringBuffer();
        for (int i : flowsSelect) {
            joint.append(tags.get(i));
            joint.append(",");
        }
        return joint;
    }

    @Override
    public void getHistorySuccess(HistoryListBean data) {
        if (data != null) {
            setFlows(mMarryFlow, data.getMarriageChildbearingHistory(), mManager.getMaritalHistory());
            setFlows(mSurgeryFlow, data.getHistorySurgeryOrTrauma(), mManager.getSurgeryHistory());
            setFlows(mFamilyFlow, data.getFamilyHistory(), mManager.getFamilyHistory());
            setFlows(mPersonFlow, data.getPersonalHistory(), mManager.getPersonHistory());
            setFlows(mAllergyFlow, data.getAllergyHistory(), mManager.getAllergyHistory());
        }
    }

    @Override
    public void addOrEditSuccess(ManagerUserBean manager) {
        if (isFromDiagnoseApply)
            EventBus.getDefault().post(new Event(EventType.UPDATEPATIENT,null));
        else
            EventBus.getDefault().post(manager);
        this.finish();
    }

    @Override
    public int getStatus() {
        return mStatus;
    }

    @Override
    public ManagerUserBean getManager() {
        StringBuffer marryHis = jointTags(mMarryFlow.getSelectedList(), mMarryTags);
        if (marryHis.lastIndexOf(",") == marryHis.length() - 1 && marryHis.length() > 0) {
            marryHis.deleteCharAt(marryHis.length() - 1);
        }
        if (marryHis.toString().length() > 100){
            ToastUtils.showShort("婚育史标签选择总字数不能超过100字");
            return null;
        }
        mManager.setMaritalHistory(marryHis.toString());

        StringBuffer surgeryHis = jointTags(mSurgeryFlow.getSelectedList(), mSurgeryTags);
        if (mSurgeryEdit.getText().toString().length()+surgeryHis.length() >100){
//            ToastUtils.showShort("手术或外伤史选择和补充内容不能超过"+(100-surgeryHis.length())+"字");
            ToastUtils.showShort("手术或外伤史选择和补充内容不能超过100字");
            return null;
        }
        if (!StringUtils.isEmpty(mSurgeryEdit.getText().toString())) {
            surgeryHis.append(mSurgeryEdit.getText().toString().trim());
        }
        if (surgeryHis.lastIndexOf(",") == surgeryHis.length() - 1 && surgeryHis.length() > 0) {
            surgeryHis.deleteCharAt(surgeryHis.length() - 1);
        }
        mManager.setSurgeryHistory(surgeryHis.toString());

        StringBuffer familyHis = jointTags(mFamilyFlow.getSelectedList(), mFamilyTags);
        if (mFamilyEdit.getText().toString().length()+familyHis.length() >100){
            ToastUtils.showShort("家族史选择和补充内容不能超过100字");
            return null;
        }
        if (!StringUtils.isEmpty(mFamilyEdit.getText().toString())) {
            familyHis.append(mFamilyEdit.getText().toString().trim());
        }
        if (familyHis.lastIndexOf(",") == familyHis.length() - 1 && familyHis.length() > 0) {
            familyHis.deleteCharAt(familyHis.length() - 1);
        }
        mManager.setFamilyHistory(familyHis.toString());

        StringBuffer personHis = jointTags(mPersonFlow.getSelectedList(), mPersonTags);
        if (personHis.lastIndexOf(",") == personHis.length() - 1 && personHis.length() > 0) {
            personHis.deleteCharAt(personHis.length() - 1);
        }
        if (personHis.toString().length() > 100){
            ToastUtils.showShort("个人史标签选择总字数不能超过100字");
            return null;
        }
        mManager.setPersonHistory(personHis.toString());

        StringBuffer allergyHis = jointTags(mAllergyFlow.getSelectedList(), mAllergyTags);
        if (mAllergyEdit.getText().toString().length()+allergyHis.length() >100){
            ToastUtils.showShort("过敏史选择和补充内容不能超过100字");
            return null;
        }
        if (!StringUtils.isEmpty(mAllergyEdit.getText().toString())) {
            allergyHis.append(mAllergyEdit.getText().toString().trim());
        }
        if (allergyHis.lastIndexOf(",") == allergyHis.length() - 1 && allergyHis.length() > 0) {
            allergyHis.deleteCharAt(allergyHis.length() - 1);
        }
        mManager.setAllergyHistory(allergyHis.toString());

        return mManager;
    }


}
