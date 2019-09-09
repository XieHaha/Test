package com.keydom.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_service.controller.NursingOrderFillInController;
import com.keydom.ih_patient.activity.nursing_service.view.NursingOrderFillInView;
import com.keydom.ih_patient.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_patient.adapter.NursingSelectProjectAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.NurseSavedData;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.constant.Config;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/24 on 19:13
 * des:护理订单填写页面
 */
public class NursingOrderFillInActivity extends BaseControllerActivity<NursingOrderFillInController> implements NursingOrderFillInView {
    public static final String IS_CHANGE = "is_change";
    public static final String CHANGE_BEAN = "change_bean";

    /**
     * 启动
     */
    public static void start(Context context, NursingProjectInfo nursingProjectInfo, String serviceAddress, long hospitalAreaId, boolean isChange, NursingOrderDetailBean bean) {
        Intent intent = new Intent(context, NursingOrderFillInActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("nursingProjectInfo", nursingProjectInfo);
        intent.putExtra("serviceAddress", serviceAddress);
        intent.putExtra(IS_CHANGE, isChange);
        intent.putExtra(CHANGE_BEAN, bean);
        intent.putExtra("hospitalAreaId", hospitalAreaId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TextView mAddress;//address 地址
    private RecyclerView mProjects;//project_flow
    private TextView mChooseProjects;//jump_to_choose_service_tv
    private TextView mTimeChoose;//choose_service_time_tv  上门时间
    private RelativeLayout mReduceBtn;//scaler_minus_layout
    private TextView mServiceNumTv,desc_font_num_tv;//scaler_text_layout  服务次数
    private RelativeLayout mAddBtn;//scaler_add_layout
    private TextView mServiceObj;//jump_to_choose_service_object_tv 服务对象
    private TextView mServiceDepartment;//jump_to_choose_department_tv 服务科室
    private InterceptorEditText mRemark;//remark_edt  //描述
    private TextView mTotalFee;//total_fee_tv
    private TextView mCommit;//commit_nursing_order_tv
    private NursingSelectProjectAdapter nursingSelectProjectAdapter;
    private int mServiceNum = 1;//服务次数 最小为1
    private List<NursingProjectInfo> projectList = new ArrayList<>();
    private NursingProjectInfo nursingProjectInfo;
    private TextView FacialRecognitionTv;
    private String visitTime = null, visitPeriod = null;
    private String deptId = null;
    private String serviceAddress = null;
    private String patientCardNum = null;
    private long hospitalAreaId = -1;
    private BigDecimal allProjectFees = new BigDecimal(0);
    private BigDecimal allProjectPrice = new BigDecimal(0);
    private String type;
    private boolean isFacialRecognition = false;
    private boolean isHaveProfessionalProject = false;
    private boolean isChange;
    private LinearLayout mDeptGroup;
    private long mId;
    private GridViewForScrollView img_gv;
    private GridViewPlusImgAdapter mAdapter;
    public List<String> dataList = new ArrayList<>();
    private boolean isNeedSaveEdit = true;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_order_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("护理服务");
        getView();
        initLib();
        serviceAddress = getIntent().getStringExtra("serviceAddress");
        hospitalAreaId = getIntent().getLongExtra("hospitalAreaId", -1);
      /*  nursingProjectInfo = (NursingProjectInfo) getIntent().getSerializableExtra("nursingProjectInfo");
        if (nursingProjectInfo != null) {
            if (nursingProjectInfo.getCateId() == Global.getProfessionalProjectTypeId())
                isHaveProfessionalProject = true;
            projectList.add(nursingProjectInfo);
            allProjectPrice = nursingProjectInfo.getFee().add(Global.getBaseFee());
        }
        allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));*/
        EventBus.getDefault().register(this);
//        getView();
        mServiceNumTv.setText(String.valueOf(mServiceNum));
        isChange = getIntent().getBooleanExtra(IS_CHANGE, false);
        if (isChange) {
            NursingOrderDetailBean beanOut = (NursingOrderDetailBean) getIntent().getSerializableExtra(CHANGE_BEAN);
            if (beanOut == null || beanOut.getNursingServiceOrderDetailBaseDto() == null) {
                finish();
            }
            NursingOrderDetailBean bean = beanOut.getNursingServiceOrderDetailBaseDto();
            mId = bean.getId();
            serviceAddress = bean.getHospitalAddress();
            hospitalAreaId = bean.getDeptAreaId();
            mReduceBtn.setClickable(false);
            mAddBtn.setClickable(false);
            mChooseProjects.setClickable(false);
            mChooseProjects.setText("");
            if (bean.getServiceName() != null) {
                for (int i = 0; i < bean.getServiceName().size(); i++) {
                    NursingProjectInfo info = new NursingProjectInfo();
                    info.setName(bean.getServiceName().get(i));
                    projectList.add(info);
                }
            }
            mServiceObj.setText(bean.getPatientName());
//            if (StringUtils.isEmpty(bean.getDeptName())) {
//                mServiceDepartment.setVisibility(View.GONE);
//            }
            mServiceDepartment.setText(bean.getDeptName());
            if (bean.getDeptId() != 0) {
                deptId = String.valueOf(bean.getDeptId());
            } else {
                deptId = null;
            }
            long curr = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            long time = System.currentTimeMillis();
            try {
                time = sf.parse(bean.getTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (curr < time) {
                visitTime = bean.getVisitTime();
                visitPeriod = bean.getVisitPeriod();
                mTimeChoose.setText(bean.getVisitTime() + " " + bean.getVisitPeriod());
            } else {
                ToastUtils.showShort("请重新选择预约时间");
            }
            mRemark.setText(bean.getConditionDesciption());
            mTotalFee.setVisibility(View.GONE);
            mServiceNum = bean.getFrequency();
            mServiceNumTv.setText(String.valueOf(mServiceNum));
            mCommit.setText("提交修改");
            if (bean.getConditionImage() != null) {
                String pics = bean.getConditionImage();
                String[] split = pics.replace("，", ",").split(",");
                for (int i = 0; i < split.length; i++) {
                    dataList.add(split[i]);
                }
                mAdapter.notifyDataSetChanged();
            }

        } else {
            getSavedEditData();
            nursingProjectInfo = (NursingProjectInfo) getIntent().getSerializableExtra("nursingProjectInfo");
            if (nursingProjectInfo != null) {
                if (nursingProjectInfo.getCateId() == Global.getProfessionalProjectTypeId())
                    isHaveProfessionalProject = true;
                projectList.add(nursingProjectInfo);
                if (StringUtils.isEmpty(nursingProjectInfo.getHospitalDeptName())) {
//                    mDeptGroup.setVisibility(View.GONE);
                } else {
                    mServiceDepartment.setText(nursingProjectInfo.getHospitalDeptName());
                    deptId = String.valueOf(nursingProjectInfo.getHospitalDeptId());
                }
                allProjectPrice = nursingProjectInfo.getFee().add(Global.getBaseFee());
            }
            allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));
            mServiceNumTv.setText(String.valueOf(mServiceNum));
        }
        nursingSelectProjectAdapter.setNewData(projectList);
        mAddress.setText(serviceAddress);
        mTotalFee.setText("费用合计（￥" + allProjectFees + "元）");
    }

    /**
     * 初始化SDK
     */
    private void initLib() {
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
        FaceConfig faceConfig = FaceSDKManager.getInstance().getFaceConfig();
        List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
        livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadLeft);
        livenessList.add(LivenessTypeEnum.HeadRight);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        livenessList.add(LivenessTypeEnum.HeadUp);
        livenessList.add(LivenessTypeEnum.HeadDown);
        faceConfig.setLivenessTypeList(livenessList);
        faceConfig.setLivenessRandom(true);
        faceConfig.setLivenessRandomCount(2);

        FaceSDKManager.getInstance().setFaceConfig(faceConfig);
    }

    /**
     * 查找控件
     */
    private void getView() {
        desc_font_num_tv=findViewById(R.id.desc_font_num_tv);
        mAddress = findViewById(R.id.address);
        mProjects = findViewById(R.id.project_rv);
        mProjects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nursingSelectProjectAdapter = new NursingSelectProjectAdapter(projectList);
        mProjects.setAdapter(nursingSelectProjectAdapter);
        mChooseProjects = findViewById(R.id.jump_to_choose_service_tv);
        mDeptGroup = findViewById(R.id.jump_to_choose_department_group);
        mChooseProjects.setOnClickListener(getController());
        mTimeChoose = findViewById(R.id.choose_service_time_tv);
        mTimeChoose.setOnClickListener(getController());
        mReduceBtn = findViewById(R.id.scaler_minus_layout);
        mReduceBtn.setOnClickListener(getController());
        mServiceNumTv = findViewById(R.id.scaler_text_layout);
        mAddBtn = findViewById(R.id.scaler_add_layout);
        mAddBtn.setOnClickListener(getController());
        mServiceObj = findViewById(R.id.jump_to_choose_service_object_tv);
        mServiceObj.setOnClickListener(getController());
        mServiceDepartment = findViewById(R.id.jump_to_choose_department_tv);
//        mServiceDepartment.setOnClickListener(getController());
        mRemark = findViewById(R.id.remark_edt);
        mTotalFee = findViewById(R.id.total_fee_tv);
        mCommit = findViewById(R.id.commit_nursing_order_tv);
        mCommit.setOnClickListener(getController());
        FacialRecognitionTv = findViewById(R.id.jump_to_facial_recognition_tv);
        FacialRecognitionTv.setOnClickListener(getController());
        /*pic_first_img = findViewById(R.id.pic_first_img);
        pic_first_img.setOnClickListener(getController());
        pic_second_img = findViewById(R.id.pic_second_img);
        pic_second_img.setOnClickListener(getController());*/
        img_gv = findViewById(R.id.img_gv);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        img_gv.setAdapter(mAdapter);
        img_gv.setOnItemClickListener(getController());

        mRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                desc_font_num_tv.setText(charSequence.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * 选择科室弹框
     */
    private void showChooseDepartmentDialog(List<HospitaldepartmentsInfo> departmentList) {
        if (departmentList != null && departmentList.size() != 0) {
            List<String> parentDepList = new ArrayList<>();
            final List<List<String>> childDepList = new ArrayList<>();
            for (int i = 0; i < departmentList.size(); i++) {
                parentDepList.add(departmentList.get(i).getName());
                List<String> parendChildList = new ArrayList<>();
                for (int j = 0; j < departmentList.get(i).getHdList().size(); j++) {
                    parendChildList.add(departmentList.get(i).getHdList().get(j).getName());
                }
                childDepList.add(parendChildList);
            }
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    deptId = String.valueOf(departmentList.get(options1).getHdList().get(option2).getId());
                    mServiceDepartment.setText(departmentList.get(options1).getHdList().get(option2).getName());
                }
            }).build();
            optionsPickerView.setPicker(parentDepList, childDepList);
            optionsPickerView.show();
        } else {
            ToastUtil.shortToast(getContext(), "没有查询到科室信息");
        }

    }

    @Override
    public void numAdd() {
        mServiceNum += 1;
        mServiceNumTv.setText(String.valueOf(mServiceNum));
        allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));
        mTotalFee.setText("费用合计（￥" + allProjectFees + "元）");
    }

    @Override
    public void numReduce() {
        mServiceNum -= 1;
        if (mServiceNum < 1) {
            mServiceNum = 1;
        }
        mServiceNumTv.setText(String.valueOf(mServiceNum));
        allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));
        mTotalFee.setText("费用合计（￥" + allProjectFees + "元）");
    }

    @Override
    public void timeChoose(String date, String start, String end) {
        //上门时间
        visitTime = date;
        visitPeriod = start + "-" + end;
        mTimeChoose.setText(visitTime + " " + visitPeriod);
    }

    @Override
    public void getDepartmentData(List<HospitaldepartmentsInfo> data) {
        //选择科室
        showChooseDepartmentDialog(data);
    }

    @Override
    public long getHospitalAreaId() {
        return hospitalAreaId;
    }

    @Override
    public List<NursingProjectInfo> getSelectProjectList() {
        return projectList;
    }

    @Override
    public void uploadImgSuccess(String data) {
        dataList.add(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadImgFailed(String msg) {
        ToastUtil.shortToast(getContext(), "图片上传失败" + msg);
    }

    @Override
    public Map<String, Object> getCommitMap() {
        Map<String, Object> map = new HashMap<>();
        /*if (!isFacialRecognition) {
            ToastUtil.shortToast(getContext(), "请完成人脸识别操作");
            return null;
        }*/
        if (serviceAddress != null && !"".equals(serviceAddress)) {
            map.put("serviceAddress", serviceAddress);
        } else {
            ToastUtil.shortToast(getContext(), "未选择服务地址");
            return null;
        }
        if (projectList.size() != 0) {
            String serviceIds = "";
            for (NursingProjectInfo nursingProjectInfo : projectList) {
                serviceIds += (nursingProjectInfo.getId() + ",");
            }
            serviceIds = serviceIds.substring(0, serviceIds.length() - 1);
            Logger.e("serviceIds==" + serviceIds);
            map.put("serviceIds", serviceIds);
        } else {
            ToastUtil.shortToast(getContext(), "请选择服务项目");
            return null;
        }
        if (visitTime != null && !"".equals(visitTime) && visitPeriod != null && !"".equals(visitPeriod)) {
            map.put("visitTime", visitTime);
            map.put("visitPeriod", visitPeriod);
        } else {
            ToastUtil.shortToast(getContext(), "请选择上门时间");
            return null;
        }
        if (patientCardNum != null && !"".equals(patientCardNum)) {
            map.put("serviceObject", patientCardNum);
        } else {
            ToastUtil.shortToast(getContext(), "请选择服务对象");
            return null;
        }
        if (!StringUtils.isEmpty(deptId)) {
            map.put("deptId", deptId);
        }
//        if (isHaveProfessionalProject) {
//            if (deptId != null && !"".equals(deptId)) {
//                map.put("deptId", deptId);
//            } else {
//                ToastUtil.shortToast(getContext(), "请选择科室");
//                return null;
//            }
//        }
        if (!"".equals(getImageStr()))
            map.put("conditionImage", getImageStr());
        map.put("hospitalId", App.hospitalId);
        map.put("userId", Global.getUserId());
        map.put("fee", String.valueOf(allProjectFees));
        if (mRemark.getText().toString().trim() != null && mRemark.getText().toString().trim().length() < 10) {
            ToastUtil.shortToast(getContext(), "请填写病情依据，至少10字");
            return null;
        } else
            map.put("conditionDesc", mRemark.getText().toString().trim());
        map.put("frequency", mServiceNum);

        return map;
    }

    @Override
    public boolean isHaveProfessionalProject() {
        return isHaveProfessionalProject;
    }

    @Override
    public boolean isChange() {
        return isChange;
    }

    @Override
    public Map<String, Object> getChangeMap() {
        Map<String, Object> map = new HashMap<>();
       /* if (!isFacialRecognition) {
            ToastUtil.shortToast(getContext(), "请完成人脸识别操作");
            return null;
        }*/
        if (!StringUtils.isEmpty(patientCardNum)) {
            map.put("serviceObject", patientCardNum);
        }
        map.put("id", mId);
        map.put("deptId", deptId);
        if (visitTime != null && !"".equals(visitTime) && visitPeriod != null && !"".equals(visitPeriod)) {
            map.put("visitTime", visitTime);
            map.put("visitPeriod", visitPeriod);
        } else {
            ToastUtil.shortToast(getContext(), "请重新选择上门时间");
            return null;
        }
        map.put("conditionDesc", mRemark.getText().toString().trim() != null && !"".equals(mRemark.getText().toString().trim()) ? mRemark.getText().toString().trim() : "");
        if (!"".equals(getImageStr()))
            map.put("conditionImage", getImageStr());
        return map;

    }

    /**
     * 获取选择的护理项目
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProjects(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPROJECT) {
            List<NursingProjectInfo> selectList = (List<NursingProjectInfo>) event.getData();
            allProjectPrice = BigDecimal.valueOf(0);
            if (selectList.size() != 0) {
                String deptname = "";
                long deptid = 0;
                for (int i = 0; i < selectList.size(); i++) {
                    if (!StringUtils.isEmpty(selectList.get(i).getHospitalDeptName())) {
                        deptid = selectList.get(i).getHospitalDeptId();
                        deptname = selectList.get(i).getHospitalDeptName();
                        break;
                    }
                }
                if (deptid != 0) {
                    deptId = String.valueOf(deptid);
                }

                mServiceDepartment.setText(deptname);

//                if (StringUtils.isEmpty(deptname)) {
//                    mServiceDepartment.setVisibility(View.GONE);
//                }else{
//                    mServiceDepartment.setVisibility(View.VISIBLE);
//                }
                projectList.clear();
                projectList.addAll(selectList);
                int professionalProjectNum = 0;
                for (NursingProjectInfo nursingProjectInfo : projectList) {
                    allProjectPrice = allProjectPrice.add(nursingProjectInfo.getFee());
//                    Logger.e("ProjectName=="+nursingProjectInfo.getName());
//                    Logger.e("ProjectCateId=="+nursingProjectInfo.getCateId());
                    if (nursingProjectInfo.getCateId() == Global.getProfessionalProjectTypeId())
                        professionalProjectNum++;
                }
//                Logger.e("professionalProjectNum=="+professionalProjectNum);
//                if (professionalProjectNum != 0)
//                    isHaveProfessionalProject = true;
//                else {
//                    isHaveProfessionalProject = false;
//                    deptId = null;
//                    mServiceDepartment.setText("选择服务科室");
//                }


            } else {
                deptId = "0";
                mServiceDepartment.setText("");
//                mServiceDepartment.setVisibility(View.GONE);
                projectList.clear();
            }
            allProjectPrice = allProjectPrice.add(Global.getBaseFee());
            nursingSelectProjectAdapter.setNewData(projectList);
            allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));
            mTotalFee.setText("费用合计（￥" + allProjectFees + "元）");


        }
    }


    /**
     * 接收服务对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatient(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            MedicalCardInfo medicalCardInfo = (MedicalCardInfo) event.getData();
            patientCardNum = medicalCardInfo.getEleCardNumber();
            mServiceObj.setText(medicalCardInfo.getName());
        }
    }

    /**
     * 人脸识别结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getFaceFacialRecognition(Event event) {
        if (event.getType() == EventType.FACIALRECOGNITION) {
            isFacialRecognition = true;
            FacialRecognitionTv.setText("验证通过");
            FacialRecognitionTv.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        getController().upLoadPic(selectList.get(i).getPath());
                    }
                    break;
            }
        }
    }

    @Override
    public boolean getLastItemClick(int position) {
        if (position == dataList.size())
            return true;
        return false;
    }

    @Override
    public String getPicUrl(int position) {
        return Const.IMAGE_HOST + dataList.get(position);
    }

    @Override
    public List<String> getPicList() {
        return dataList;
    }

    @Override
    public void setIsNeedSaveEdit(boolean isNeedSaveEdit) {
        this.isNeedSaveEdit=isNeedSaveEdit;
    }

    @Override
    public int getImgSize() {
        return dataList.size();
    }

    /**
     * 获取图片string
     */
    private String getImageStr() {
        String imageStr = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                imageStr = dataList.get(i);
            } else {
                imageStr += "," + dataList.get(i);
            }
        }
        return imageStr;
    }

    /**
     * 保存页面编辑的数据
     */
    private void saveEditData() {
        NurseSavedData nurseSavedData=new NurseSavedData();
        nurseSavedData.setServiceObjectName(mServiceObj.getText().toString().trim());
        nurseSavedData.setServiceObjectCardNum(patientCardNum);
        nurseSavedData.setProjectList(projectList);
        nurseSavedData.setServiceTime(visitTime);
        nurseSavedData.setVisitPeriod(visitPeriod);
        nurseSavedData.setmServiceNum(mServiceNum);
        nurseSavedData.setNurseDepartmentName(mServiceDepartment.getText().toString().trim());
        nurseSavedData.setDeptId(deptId);
        nurseSavedData.setNurseDescripe(mRemark.getText().toString().trim());
        nurseSavedData.setImgUrlList(dataList);
        String fileName="nurseEditData"+Global.getUserId()+"_"+App.hospitalId;
        LocalizationUtils.fileSave2Local(getContext(),nurseSavedData,fileName);
    }


    private void getSavedEditData() {
        String fileName="nurseEditData"+Global.getUserId()+"_"+App.hospitalId;
        NurseSavedData nurseSavedData= (NurseSavedData) LocalizationUtils.readFileFromLocal(getContext(),fileName);
        if(nurseSavedData!=null){
            new GeneralDialog(getContext(), "存在上次未提交的编辑内容，是否继续编辑？", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    projectList.clear();
                    projectList.addAll(nurseSavedData.getProjectList());
                    nursingSelectProjectAdapter.setNewData(projectList);
                    mServiceObj.setText(nurseSavedData.getServiceObjectName());
                    patientCardNum = nurseSavedData.getServiceObjectCardNum();
                    visitTime = nurseSavedData.getServiceTime();
                    visitPeriod=nurseSavedData.getVisitPeriod();
                    mTimeChoose.setText(visitTime + " " + visitPeriod);
                    mServiceNum = nurseSavedData.getmServiceNum();
                    mServiceNumTv.setText(String.valueOf(mServiceNum));
                    deptId = nurseSavedData.getDeptId();
                    mServiceDepartment.setText(nurseSavedData.getNurseDepartmentName());
                    mRemark.setText(nurseSavedData.getNurseDescripe());
                    dataList.clear();
                    dataList.addAll(nurseSavedData.getImgUrlList());
                    mAdapter.notifyDataSetChanged();
                    allProjectPrice = BigDecimal.valueOf(0);
                    for (NursingProjectInfo nursingProjectInfo : projectList) {
                        allProjectPrice = allProjectPrice.add(nursingProjectInfo.getFee());
                    }
                    allProjectPrice = allProjectPrice.add(Global.getBaseFee());
                    allProjectFees = allProjectPrice.multiply(BigDecimal.valueOf(mServiceNum));
                    mTotalFee.setText("费用合计（￥" + allProjectFees + "元）");
                }
            }, new GeneralDialog.CancelListener() {
                @Override
                public void onCancel() {
                    String fileName="nurseEditData"+Global.getUserId()+"_"+App.hospitalId;
                    LocalizationUtils.deleteFileFromLocal(getContext(),fileName);
                }
            }).setTitle("提示").show();

        }
    }

    private boolean isHaveData(){
        if(dataList.size()>0||projectList.size()>0||patientCardNum!=null||visitTime!=null||!"".equals(mRemark.getText().toString().trim()))
            return true;
        else
            return false;
    }

    @Override
    protected void onDestroy() {
        if (isNeedSaveEdit&&isHaveData())
            saveEditData();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
