package com.keydom.ih_doctor.activity.patient_manage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.SwitchButton;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.ih_doctor.activity.patient_manage.controller.TentativeDiagnosisController;
import com.keydom.ih_doctor.activity.patient_manage.view.TentativeDiagnosisView;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.WarrantDetailBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TentativeDiagnosisActivity extends BaseControllerActivity<TentativeDiagnosisController> implements TentativeDiagnosisView {
    public static final int TYPECREAT = 0;
    public static final int TYPEUPDATE = 1;
    private TextView backVisitScalerTextLayout, inquiryScalerTextLayout, chooseTermTv;
    private TextView groupDoctor, patientName;
    private RelativeLayout backVisitScalerAddLayout, backVisitScalerMinusLayout, inquiryScalerMinusLayout, inquiryScalerAddLayout;
    private LinearLayout backVisitSacler, inquirySacler;
    private CheckBox backVisitCk, inquiryCk;
    private SwitchButton authSwitch;
    private LinearLayout doctorLl, patientLl;
    private String doctorId="";
    private int type;
    private String userName="";
    private String userId="";
    /**
     * 添加的医生View列表
     */
    private Stack<View> doctorStack = new Stack<>();
    /**
     * 添加的患者View列表
     */
    private Stack<View> patientStack = new Stack<>();
    private TimePickerView pickerDialog;

    /**
     * 选中的医生
     */
    private List<DeptDoctorBean> selectDoctor;
    /**
     * 选中的患者
     */
    private List<ImPatientInfo> patientList = new ArrayList<>();
    /**
     * 授权时间
     */
    private Date mDate;
    /**
     * 默认顺序
     */
    private int inquiryScalerQuantity = 1;
    /**
     * 0不勾选回访、1勾选回访
     */
    private int backVisitState = 0;
    /**
     * 0不勾选问诊、1勾选问诊
     */
    private int inquiryScalerState = 0;
    /**
     * 0关闭权限，1打开权限
     */
    private int state = 1;

    /**
     * 启动授权管理页面
     *
     * @param context
     */
    public static void start(Context context, int type, String doctorId,String userName,String userId) {
        Intent intent = new Intent(context, TentativeDiagnosisActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("userName", userName);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type == TYPEUPDATE){
            doctorId = getIntent().getStringExtra("doctorId");
            userName=getIntent().getStringExtra("userName");
            userId=getIntent().getStringExtra("userId");
        }
        backVisitScalerTextLayout = this.findViewById(R.id.back_visit_scaler_text_layout);
        inquiryScalerTextLayout = this.findViewById(R.id.inquiry_scaler_text_layout);
        chooseTermTv = this.findViewById(R.id.choose_term_tv);
        authSwitch = this.findViewById(R.id.auth_switch);

        backVisitScalerAddLayout = this.findViewById(R.id.back_visit_scaler_add_layout);
        backVisitScalerMinusLayout = this.findViewById(R.id.back_visit_scaler_minus_layout);
        inquiryScalerMinusLayout = this.findViewById(R.id.inquiry_scaler_minus_layout);
        inquiryScalerAddLayout = this.findViewById(R.id.inquiry_scaler_add_layout);
        backVisitCk = this.findViewById(R.id.back_visit_ck);
        inquiryCk = this.findViewById(R.id.inquiry_ck);
        inquirySacler = this.findViewById(R.id.inquiry_sacler);
        doctorLl = this.findViewById(R.id.doctor_box);
        patientLl = this.findViewById(R.id.patient_box);
        groupDoctor = this.findViewById(R.id.group_doctor);
        patientName = this.findViewById(R.id.patient_name);
        backVisitScalerAddLayout.setOnClickListener(getController());
        backVisitScalerMinusLayout.setOnClickListener(getController());
        inquiryScalerMinusLayout.setOnClickListener(getController());
        inquiryScalerAddLayout.setOnClickListener(getController());
        groupDoctor.setOnClickListener(getController());
        patientName.setOnClickListener(getController());
        chooseTermTv.setOnClickListener(getController());
        authSwitch.setOnCheckedChangeListener(getController());
        backVisitCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    backVisitState = 1;
//                    inquiryCk.setChecked(false);
                } else {
                    backVisitState = 0;
                }
            }
        });

        inquiryCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inquiryScalerState = 1;
//                    backVisitCk.setChecked(false);
                } else {
                    inquiryScalerState = 0;
                }
            }
        });
        if (type == TYPEUPDATE)
            getController().getWarrantDetail(doctorId,userId);


    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_tentative_diagnosis_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("授权管理");
        setRightTxt("授权");
        setRightBtnListener(getController());
        initView();
        initDialog();
    }


    @Override
    public void inquiryScalerAdd() {
        if (inquiryScalerState != 1) {
            return;
        }
        inquiryScalerQuantity++;
        inquiryScalerTextLayout.setText(String.valueOf(inquiryScalerQuantity));
    }

    @Override
    public void inquiryScalerMinus() {
        if (inquiryScalerState != 1) {
            return;
        }
        if (inquiryScalerQuantity > 1) {
            inquiryScalerQuantity--;
        }
        inquiryScalerTextLayout.setText(String.valueOf(inquiryScalerQuantity));
    }

    @Override
    public void showTimePikerDialog() {
        pickerDialog.show();
    }

    @Override
    public void jurisdictionIsOpen(boolean b) {
        if (b) {
            state = 0;
        } else {
            state = 1;
        }
    }

    @Override
    public List<ImPatientInfo> getSelectPatient() {
        return patientList;
    }

    @Override
    public DeptDoctorBean getSelectDoctor() {
        if (selectDoctor != null && selectDoctor.size() == 1) {
            return selectDoctor.get(0);
        }
        return null;
    }

    @Override
    public boolean submitCheck() {
        if (selectDoctor == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请选择医生");
            builder.setMessage("授权医生为空，请选择医生后再授权！");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
            return false;
        }
        if (patientList == null || patientList.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请选择患者");
            builder.setMessage("授权患者列表为空，请选择医生后再授权！");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
            return false;
        }

        return true;
    }

    @Override
    public Map<String, Object> getAuthorizeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userIdList", getPatientList());
        map.put("creatorId",MyApplication.userInfo.getId());
        map.put("doctorId", selectDoctor != null ? selectDoctor.get(0).getId() : "");
        map.put("returnVisitState", backVisitState);
        map.put("interrogationState", inquiryScalerState);
        map.put("interrogationOrder", inquiryScalerQuantity);
        map.put("termValidity", mDate);
        map.put("state", state);
        if(type==TYPECREAT)
            map.put("type",0);
        else
            map.put("type",1);

        return map;
    }

    @Override
    public void authSuccess(String msg) {
        ToastUtil.showMessage(this, "授权成功");
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.PATIENT_UPDATE_USER_LIST).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATEWARRANTLIST).build());
        finish();
    }

    @Override
    public void authFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getWarrantDetailSuccess(WarrantDetailBean warrantDetailBean) {
        //渲染医生数据
        selectDoctor=new ArrayList<>();
        DeptDoctorBean doctorBean=new DeptDoctorBean();
        doctorBean.setName(warrantDetailBean.getDoctorName());
        doctorBean.setId(Long.valueOf(warrantDetailBean.getDoctorId()));
        selectDoctor.add(doctorBean);
        addDoctorView(warrantDetailBean.getDoctorName());
        groupDoctor.setHint("");
        //渲染患者数据
        if(warrantDetailBean.getList()!=null&&warrantDetailBean.getList().size()>0){
            for (int i = 0; i < warrantDetailBean.getList().size(); i++) {
                ImPatientInfo imPatientInfo=new ImPatientInfo();
                imPatientInfo.setId(Long.valueOf(warrantDetailBean.getList().get(i).getUserId()));
                imPatientInfo.setUserName(warrantDetailBean.getList().get(i).getUserName());
                patientList.add(imPatientInfo);
                addPatientView(warrantDetailBean.getList().get(i).getUserName());
            }
            patientName.setHint("");
        }

        //渲染其他数据
        backVisitState=warrantDetailBean.getReturnVisitState();
        if(backVisitState==0)
            backVisitCk.setChecked(false);
        else
            backVisitCk.setChecked(true);
        inquiryScalerState=warrantDetailBean.getInterrogationState();
        if(inquiryScalerState==0)
            inquiryCk.setChecked(false);
        else
            inquiryCk.setChecked(true);
        inquiryScalerQuantity=warrantDetailBean.getInterrogationOrder();
        inquiryScalerTextLayout.setText(String.valueOf(inquiryScalerQuantity));
        state=warrantDetailBean.getState();
        if(state==0)
            authSwitch.setState(true);
        else
            authSwitch.setState(false);
        if(warrantDetailBean.getTermValidity()!=null&&!"".equals(warrantDetailBean.getTermValidity())){

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                mDate=format.parse(warrantDetailBean.getTermValidity());
                chooseTermTv.setText(CalculateTimeUtils.requestDate(mDate));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void getWarrantDetailFailed(String errMsg) {

    }

    @Override
    public int getType() {
        return type;
    }


    public void initDialog() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(new Date());
        endDate.set(2050, 12, 30);
        pickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mDate = date;
                chooseTermTv.setText(CalculateTimeUtils.requestDate(date));
            }
        }).setRangDate(startDate, endDate).setType(new boolean[]{true, true, true, false, false, false}).
                setTitleText("选择有效期").build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SelectDoctorActivity.DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT:
                    selectDoctor = (List<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    if (selectDoctor != null && selectDoctor.size() == 1) {
                        doctorLl.removeAllViews();
                        addDoctorView(selectDoctor.get(0).getName());
                        if (selectDoctor != null) {
                            groupDoctor.setHint("");
                        } else {
                            groupDoctor.setHint("请选择团队成员");
                        }
                    }

                    break;
                case Const.PATIENT_SLEECT_ONLY_RESULT:
                    patientLl.removeAllViews();
                    patientList.clear();
                    List<ImPatientInfo> mList = (List<ImPatientInfo>) data.getSerializableExtra(Const.DATA);
                    if (mList != null && mList.size() > 0) {
                        patientList.addAll(mList);
                        for (int i = 0; i < mList.size(); i++) {
                            addPatientView(mList.get(i).getUserName());
                        }
                        patientName.setHint("");
                    } else {
                        patientName.setHint("请选择患者");
                    }
                    break;
                default:
            }
        }

    }

    /**
     * 获取患者列表请求数据
     *
     * @return
     */
    private List<String> getPatientList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < patientList.size(); i++) {
            list.add(String.valueOf(patientList.get(i).getId()));
        }
        return list;
    }


    /**
     * 添加医生布局
     *
     * @param name 医生姓名
     */
    private void addDoctorView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        doctorLl.addView(view);
        doctorStack.push(view);

    }

    /**
     * 添加患者布局
     *
     * @param name 患者姓名
     */
    private void addPatientView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        patientLl.addView(view);
        patientStack.push(view);

    }


}
