package com.keydom.ih_doctor.activity.patient_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.SwitchButton;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.patient_manage.controller.PatientDatumController;
import com.keydom.ih_doctor.activity.patient_manage.view.PatientDatumView;
import com.keydom.ih_doctor.adapter.DiagnosePatientRecordAdapter;
import com.keydom.ih_doctor.bean.DiagnoseRecoderItemBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.PatientInfoBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDatumActivity extends BaseControllerActivity<PatientDatumController> implements PatientDatumView {
    /**
     * 标签requestCode
     */
    public static final int PATIENT_TAG = 6001;
    /**
     * 备注requestCode
     */
    public static final int PATIENT_REMARK = 6002;
    /**
     * 医生requestCode
     */
    public static final int PATIENT_DOCTOR = 6003;

    public List<String> lables = new ArrayList<>();

    /**
     * 启动患者资料页面
     *
     * @param context
     * @param patientId 注册用户ID
     */
    public static void start(Context context, String patientId) {
        Intent starter = new Intent(context, PatientDatumActivity.class);
        starter.putExtra(Const.DATA, patientId);
        context.startActivity(starter);
    }

    private String patientId;
    private CircleImageView headImg;
    private TextView nameTv, patientBasedatumTv, locationTv, remark, chooseDoctorTv, lableTv;
    private LinearLayout groupTv, lableBox;
    private RecyclerView diagnoseRecordRv;
    private DiagnosePatientRecordAdapter diagnosePatientRecordAdapter;
    private List<DiagnoseRecoderItemBean> dataList = new ArrayList<>();
    private SwitchButton isFollowSwbtn, isBlackSwbtn;
    private boolean isFollow = false;
    private boolean isBlack = false;
    private PatientInfoBean patientInfoBean;

    private void initView() {
        headImg = this.findViewById(R.id.head_img);
        nameTv = this.findViewById(R.id.name_tv);
        lableBox = this.findViewById(R.id.lable_box);
        patientBasedatumTv = this.findViewById(R.id.patient_basedatum_tv);
        locationTv = this.findViewById(R.id.location_tv);
        groupTv = this.findViewById(R.id.group_tv);
        remark = this.findViewById(R.id.remark);
        remark.setOnClickListener(getController());
        lableTv = this.findViewById(R.id.lable_tv);
        lableTv.setOnClickListener(getController());
        chooseDoctorTv = this.findViewById(R.id.choose_doctor_tv);
        chooseDoctorTv.setOnClickListener(getController());
        diagnoseRecordRv = this.findViewById(R.id.diagnose_record_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        diagnoseRecordRv.setLayoutManager(layoutManager);
        diagnoseRecordRv.setHasFixedSize(true);
        diagnoseRecordRv.setNestedScrollingEnabled(false);
        diagnosePatientRecordAdapter = new DiagnosePatientRecordAdapter(getContext(), dataList);
        diagnoseRecordRv.setAdapter(diagnosePatientRecordAdapter);
        isFollowSwbtn = findViewById(R.id.is_follow_swbtn);
        isFollowSwbtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                Map<String, Object> map = new HashMap<>();
                map.put("imNumber", patientId);
                map.put("hospitalCode", MyApplication.userInfo.getUserCode());
                map.put("focusOn", isChecked ? "1" : "0");
                getController().updateRegUserInfo(map);
            }
        });
        isBlackSwbtn = findViewById(R.id.is_black_swbtn);
        isBlackSwbtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                Map<String, Object> map = new HashMap<>();
                map.put("imNumber", patientId);
                map.put("hospitalCode", MyApplication.userInfo.getUserCode());
                map.put("blackList", isChecked ? "1" : "0");
                getController().updateRegUserInfo(map);
            }
        });
        diagnoseRecordRv = findViewById(R.id.diagnose_record_rv);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_patient_datum_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        patientId = getIntent().getStringExtra(Const.DATA);
        setTitle("患者资料");
        initView();
        pageLoading();
        getController().getPatientInfo();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getPatientInfo();
            }
        });
    }

    @Override
    public Map<String, Object> getPatientInfoMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", patientId);
        return map;
    }

    @Override
    public void getInfoSuccess(PatientInfoBean bean) {
        if (bean == null) {
            pageEmpty();
            return;
        }
        pageLoadingSuccess();
        groupTv.removeAllViews();
        lableBox.removeAllViews();
        patientInfoBean = bean;
        GlideUtils.load(headImg, Const.IMAGE_HOST + bean.getUserImage(), 0, 0, false, null);
        nameTv.setText(bean.getUserName());
        String infoStr = "";
        infoStr += CommonUtils.getSex(bean.getSex()) + "  ";
        infoStr += (bean.getAge() != 0 && !"".equals(bean.getAge())) ? bean.getAge() + "岁  " : " ";
        infoStr += (bean.getPhoneNumber() != null && !"".equals(bean.getPhoneNumber()) ? bean.getPhoneNumber() : "");
        patientBasedatumTv.setText(infoStr);
        locationTv.setText(bean.getCityAddress());
        chooseDoctorTv.setText(bean.getHospitalUserNames());
        remark.setText(bean.getRemark());
        if (bean.getGroupChatNames() != null && !"".equals(bean.getGroupChatNames())) {
            addTag(groupTv, bean.getGroupChatNames());
        }
        if (bean.getLableList() != null) {
            for (int i = 0; i < bean.getLableList().size(); i++) {
                addTagView(bean.getLableList().get(i));
                lableTv.setHint("");
            }
        }

        if ("0".equals(bean.getFocusOn())) {
            isFollowSwbtn.setState(false);
            isFollow = false;
        } else if ("1".equals(bean.getFocusOn())) {
            isFollowSwbtn.setState(true);
            isFollow = true;
        }
        if ("0".equals(bean.getBlackNameList())) {
            isBlackSwbtn.setState(false);
            isBlack = false;
        } else if ("1".equals(bean.getBlackNameList())) {
            isBlackSwbtn.setState(true);
            isBlack = true;
        }
        diagnosePatientRecordAdapter.setNewData(bean.getConsultRecordVOList());

    }

    @Override
    public void getInfoFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public void updatePatientInfoSuccess() {
        getController().getPatientInfo();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.PATIENT_UPDATE_USER_LIST).build());
    }

    @Override
    public void updatePatientInfoFailed(String errMsg) {
        getController().getPatientInfo();
        ToastUtil.showMessage(this, "修改失败" + errMsg);

    }

    @Override
    public String getRemark() {
        return remark.getText().toString();
    }

    /**
     * 拼接标签
     *
     * @param lable 标签
     * @return
     */
    private String getLableStr(String lable) {
        String str = "";
        if (patientInfoBean.getLableList() != null) {
            for (int i = 0; i < patientInfoBean.getLableList().size(); i++) {
                if (i == 0) {
                    str += patientInfoBean.getLableList().get(i);
                } else {
                    str += ";" + patientInfoBean.getLableList().get(i);
                }
            }
        }
        if (lable != null && !lable.equals("")) {
            if (str.equals("")) {
                str = lable;
            } else {
                str = str + ";" + lable;
            }
        }
        return str;
    }

    /**
     * 添加标签布局
     *
     * @param mTagView 标签控件
     * @param tag      标签名称
     */
    private void addTag(LinearLayout mTagView, String tag) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 15;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        TextView view = new TextView(this);
        view.setText(tag);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                this.getResources().getDimension(R.dimen.font_size_primary));
        view.setTextColor(this.getResources().getColor(R.color.agreement));
        view.setGravity(Gravity.CENTER);
        mTagView.addView(view, lp);
    }

    /**
     * 添加标签布局
     *
     * @param name
     */
    private void addTagView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new GeneralDialog(PatientDatumActivity.this, "是否确定删除该标签?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        patientInfoBean.getLableList().remove(name);
                        Map<String, Object> map = new HashMap<>();
                        map.put("imNumber", patientId);
                        map.put("hospitalCode", MyApplication.userInfo.getUserCode());
                        map.put("label", getLableStr(""));
                        getController().updateRegUserInfo(map);
                    }
                }).show();
                return false;
            }
        });
        lableBox.addView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Map<String, Object> map = new HashMap<>();
        map.put("imNumber", patientId);
        map.put("hospitalCode", MyApplication.userInfo.getUserCode());
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PATIENT_TAG:
                    map.put("label", getLableStr(data.getStringExtra(Const.DATA)));
                    getController().updateRegUserInfo(map);
                    break;
                case PATIENT_REMARK:
                    map.put("remark", data.getStringExtra(Const.DATA));
                    getController().updateRegUserInfo(map);
                    break;
                case PATIENT_DOCTOR:
                    break;
            }
        }
    }
}
