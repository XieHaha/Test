package com.keydom.ih_doctor.activity.patient_manage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.patient_manage.controller.NewGroupChatController;
import com.keydom.ih_doctor.activity.patient_manage.view.NewGroupChatView;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.bean.GroupResBean;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class NewGroupChatActivity extends BaseControllerActivity<NewGroupChatController> implements NewGroupChatView {

    /**
     * 创建团队
     */
    public static final int CREATE_GROUP = 1002;
    /**
     * 修改团队
     */
    public static final int UPDATE_GROUP = 1003;
    /**
     * 返回团队名称数据code
     */
    public static final int GROUP_NAME = 500;
    /**
     * 返回团队医生数据code
     */
    public static final int GROUP_DOCTOR = 501;
    /**
     * 返回患者数据code
     */
    public static final int PATIENT_NAME = 502;
    /**
     * 返回头像数据code
     */
    public static final int GROUP_ICON = 503;
    private TextView groupName, groupDoctor, patientName, groupIconTv;
    private CircleImageView groupIcon;
    /**
     * 添加的医生对象列表
     */
    private List<DeptDoctorBean> doctorList = new ArrayList<>();
    /**
     * 添加的患者对象列表
     */
    private List<ImPatientInfo> patientList = new ArrayList<>();

    private GroupResBean groupResBean;
    private LinearLayout doctorLl, patientLl;
    /**
     * 添加的医生view列表
     */
    private Stack<View> doctorStack = new Stack<>();
    /**
     * 添加的患者View列表
     */
    private Stack<View> patientStack = new Stack<>();
    /**
     * 头像地址
     */
    private String iconUrl = "";

    private int mType;
    private String tid;
    private String owner;
    private boolean isUpdate = true;

    /**
     * 启动新建群聊页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, NewGroupChatActivity.class);
        starter.putExtra(Const.TYPE, CREATE_GROUP);
        context.startActivity(starter);
    }

    public static void startForUpdate(Context context, String tid) {
        Intent starter = new Intent(context, NewGroupChatActivity.class);
        starter.putExtra(Const.TYPE, UPDATE_GROUP);
        starter.putExtra(Const.DATA, tid);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_create_patient_group_layout;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        groupName = this.findViewById(R.id.group_name);
        groupDoctor = this.findViewById(R.id.group_doctor);
        patientName = this.findViewById(R.id.patient_name);
        groupIconTv = this.findViewById(R.id.group_icon_tv);
        groupIcon = this.findViewById(R.id.group_icon);
        doctorLl = this.findViewById(R.id.doctor_box);
        patientLl = this.findViewById(R.id.patient_box);
        groupName.setOnClickListener(getController());
        groupDoctor.setOnClickListener(getController());
        patientName.setOnClickListener(getController());
        groupIconTv.setOnClickListener(getController());
        groupIcon.setOnClickListener(getController());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        tid = getIntent().getStringExtra(Const.DATA);
        if (mType == UPDATE_GROUP) {
            setTitle("修改群聊");
            getController().seeGroupChatInfo();
        } else {
            setTitle("新建群聊");
        }
        setRightTxt("确认修改");
        setRightBtnListener(getController());
        initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GROUP_NAME:
                    String groupNameStr = (String) data.getSerializableExtra(Const.DATA);
                    groupName.setText(groupNameStr);
                    break;
                case GROUP_ICON:
                    List<LocalMedia> cardselectList = PictureSelector.obtainMultipleResult(data);
                    if (cardselectList != null && cardselectList.size() == 1) {
                        getController().uploadFile(cardselectList.get(0).getPath());
                    }
                    break;
                case Const.DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT:
                    doctorLl.removeAllViews();
                    doctorList.clear();
                    List<DeptDoctorBean> list = (List<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    if (list != null) {
                        doctorList.addAll(list);
                        for (int i = 0; i < list.size(); i++) {
                            addDoctorView(list.get(i).getName());
                        }
                        groupDoctor.setHint("");
                    }
                    break;
                case Const.PATIENT_SLEECT_ONLY_RESULT:
                    patientLl.removeAllViews();
                    patientList.clear();
                    List<ImPatientInfo> mList = (List<ImPatientInfo>) data.getSerializableExtra(Const.DATA);
                    if (mList != null) {
                        patientList.addAll(mList);
                        for (int i = 0; i < mList.size(); i++) {
                            addPatientView(mList.get(i).getUserName());
                        }
                        patientName.setHint("");
                    }
                    break;
                default:
            }
        }

    }

    @Override
    public String getGroupName() {
        return groupName.getText().toString();
    }

    @Override
    public List<DeptDoctorBean> getSelectDoctors() {
        return doctorList;
    }

    @Override
    public void uploadSuccess(String path) {
        iconUrl = path;
        GlideUtils.load(groupIcon, Const.IMAGE_HOST + path, 0, 0, false, null);
        groupIconTv.setHint("");
        groupIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void uploadFailed(String errMsg) {
        if(errMsg==null||"".equals(errMsg.trim())){
            ToastUtil.shortToast(this, errMsg);
        }else{
            ToastUtil.shortToast(this, "头像上传失败!");
        }
    }

    @Override
    public void createGroupSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_MSG_LIST).build());
        finish();
    }

    @Override
    public void createGroupFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    @Override
    public List<ImPatientInfo> getSelectPatient() {
        return patientList;
    }

    @Override
    public Map<String, Object> getCreateGroupMap() {
        if(getGroupName()==null||"".equals(getGroupName())){
            ToastUtil.shortToast(getContext(),"群名称不能为空");
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("groupName", getGroupName());
        map.put("avatar", iconUrl);
        map.put("creatorId", MyApplication.userInfo.getId());
        map.put("groupSummary", "");
        map.put("updateUser", "");
        map.put("remake", "");
        if (mType == UPDATE_GROUP) {
            map.put("owner", owner);//群主账号
            map.put("tid", tid);//群号
            map.put("deleteRegisterUserIdList", getDelPatientList());//删除的患者
            map.put("deleteHospitalUserIdList", getDelDoctorList());//删除的医生
            map.put("registerUserIdList", getAddPatientList());//新增患者
            map.put("hospitalUserIdList", getAddDoctorList());//新增医生
        } else {
            map.put("registerUserIdList", getPatientList());
            map.put("hospitalUserIdList", getDoctorList());
        }

        return map;
    }

    @Override
    public Map<String, Object> getQueryGroupMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("tid", tid);
        map.put("doctorId", MyApplication.userInfo.getId());
        return map;
    }

    @Override
    public boolean submitCheck() {
//        if (doctorList == null || doctorList.size() <= 0) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("请选择医生");
//            builder.setMessage("医生列表为空，请选择医生后再提交！");
//            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            builder.create().show();
//            return false;
//        }
//
//        if (patientList == null || patientList.size() <= 0) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("请选择患者");
//            builder.setMessage("患者列表为空，请选择患者后再授权！");
//            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            builder.create().show();
//            return false;
//        }

        if (getGroupName() == null || "".equals(getGroupName())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请输入群名称");
            builder.setMessage("群名称为空，请输入群名称后再提交！");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
            return false;
        }

        if (iconUrl == null || "".equals(iconUrl)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请选择群头像");
            builder.setMessage("群头像为空，请选择群头像后再提交！");
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
    public void getGroupInfoSuccess(GroupResBean groupResBean) {
        this.groupResBean = groupResBean;
        groupName.setText(groupResBean.getGroupName());
        iconUrl = groupResBean.getAvatar();
        GlideUtils.load(groupIcon, Const.IMAGE_HOST + groupResBean.getAvatar(), 0, 0, false, null);
        groupIconTv.setHint("");
        groupIcon.setVisibility(View.VISIBLE);
        List<DeptDoctorBean> list = groupResBean.getHospitalUserVoList();
        if (list != null) {
            doctorList.addAll(list);
            for (int i = 0; i < list.size(); i++) {
                addDoctorView(list.get(i).getName());
            }
            groupDoctor.setHint("");
        }
        List<ImPatientInfo> mList = groupResBean.getRegisterUserVoList();
        if (mList != null) {
            patientList.addAll(mList);
            for (int i = 0; i < mList.size(); i++) {
                addPatientView(mList.get(i).getUserName());
            }
            patientName.setHint("");
        }
        owner = groupResBean.getOwner();
        isUpdate = groupResBean.isUpdate();
    }

    @Override
    public int getType() {
        return mType;
    }

    @Override
    public boolean isUpdate() {
        return isUpdate;
    }

    /**
     * 医生列表－拼接上传格式
     *
     * @return
     */
    private List<String> getDoctorList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < doctorList.size(); i++) {
            list.add(String.valueOf(doctorList.get(i).getId()));
        }
        return list;
    }

    private List<String> getDelDoctorList() {
        List<String> list = new ArrayList<>();
        if (groupResBean == null || groupResBean.getHospitalUserVoList() == null || groupResBean.getHospitalUserVoList().size() == 0) {//以前团队没有医生，删除列表应该为空
//            for (int i = 0; i < doctorList.size(); i++) {
//                list.add(String.valueOf(doctorList.get(i).getId()));
//            }
        } else {//以前团队有医生，之前列表存在，选择结果列表不存在的就是需要删除的
            for (DeptDoctorBean item : groupResBean.getHospitalUserVoList()) {
                if (!isDoctorDelete(item)) {
                    list.add(String.valueOf(item.getId()));
                }
            }
        }
        return list;
    }

    private List<String> getAddDoctorList() {
        List<String> list = new ArrayList<>();
        if (groupResBean == null || groupResBean.getHospitalUserVoList() == null || groupResBean.getHospitalUserVoList().size() == 0) {//以前团队没有医生，删除列表应该为空
            for (int i = 0; i < doctorList.size(); i++) {
                list.add(String.valueOf(doctorList.get(i).getId()));
            }
        } else {//以前团队有医生，之前列表存在，选择结果列表不存在的就是需要删除的
            for (DeptDoctorBean item : doctorList) {
                if (isDoctorAdd(item)) {
                    list.add(String.valueOf(item.getId()));
                }
            }
        }
        return list;
    }

    private boolean isDoctorDelete(DeptDoctorBean item) {
        for (DeptDoctorBean selectItem : doctorList) {
            if (item.getId() == selectItem.getId()) {//doctorList中存在
                return true;
            }
        }
        return false;
    }

    private boolean isDoctorAdd(DeptDoctorBean item) {
        for (DeptDoctorBean selectItem : groupResBean.getHospitalUserVoList()) {
            if (item.getId() == selectItem.getId()) {//以前列表存在
                return false;
            }
        }
        return true;
    }


    private List<String> getDelPatientList() {
        List<String> list = new ArrayList<>();
        if (groupResBean == null || groupResBean.getRegisterUserVoList() == null || groupResBean.getRegisterUserVoList().size() == 0) {//以前团队没有医生，删除列表应该为空
//            for (int i = 0; i < patientList.size(); i++) {
//                list.add(String.valueOf(patientList.get(i).getId()));
//            }
        } else {//以前团队有患者，之前列表存在，选择结果列表不存在的就是需要删除的
            for (ImPatientInfo item : groupResBean.getRegisterUserVoList()) {
                if (!isPatientDelete(item)) {
                    list.add(String.valueOf(item.getId()));
                }
            }
        }
        return list;
    }

    private List<String> getAddPatientList() {
        List<String> list = new ArrayList<>();
        if (groupResBean == null || groupResBean.getRegisterUserVoList() == null || groupResBean.getRegisterUserVoList().size() == 0) {
            for (int i = 0; i < patientList.size(); i++) {
                list.add(String.valueOf(patientList.get(i).getId()));
            }
        } else {//以前团队没有这个患者，目前列表有这个患者就是需要新增的
            for (ImPatientInfo item : patientList) {//现在的列表
                if (isPatientAdd(item)) {
                    list.add(String.valueOf(item.getId()));
                }
            }
        }
        return list;
    }

    private boolean isPatientDelete(ImPatientInfo item) {
        for (ImPatientInfo selectItem : patientList) {
            if (item.getId() == selectItem.getId()) {//存在
                return true;
            }
        }
        return false;
    }


    private boolean isPatientAdd(ImPatientInfo item) {
        for (ImPatientInfo selectItem : groupResBean.getRegisterUserVoList()) {
            if (item.getId() == selectItem.getId()) {//以前列表存在
                return false;
            }
        }
        return true;
    }

    /**
     * 患者列表－拼接上传格式
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
     * 添加了医生后增加布局
     *
     * @param name
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
     * 添加了患者后增加布局
     *
     * @param name
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
