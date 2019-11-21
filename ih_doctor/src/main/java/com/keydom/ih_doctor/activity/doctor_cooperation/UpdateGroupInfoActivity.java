package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.UpdateGroupInfoController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.UpdateGroupInfoView;
import com.keydom.ih_doctor.bean.GroupInfoBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：团队信息修改页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class UpdateGroupInfoActivity extends BaseControllerActivity<UpdateGroupInfoController> implements UpdateGroupInfoView {

    /**
     * 页面type,判断是新建团队还是修改团队
     */
    private TypeEnum mType;
    private TextView groupHospital, groupDep, groupName, groupBeGood;
    private CircleImageView groupIcon;
    private GroupInfoBean mBean;
    private boolean isEdit = false;

    /**
     * 启动修改团队信息页面
     *
     * @param context
     */
    public static void start(Context context, TypeEnum type, GroupInfoBean bean) {
        Intent starter = new Intent(context, UpdateGroupInfoActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.DATA, bean);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_update_group_info_layout;
    }

    private void initView() {
        groupHospital = this.findViewById(R.id.group_hospital);
        groupDep = this.findViewById(R.id.group_dep);
        groupName = this.findViewById(R.id.patient_name);
        groupBeGood = this.findViewById(R.id.group_be_good);
        groupIcon = this.findViewById(R.id.group_icon);
        groupIcon.setOnClickListener(getController());
        groupBeGood.setOnClickListener(getController());
        groupName.setOnClickListener(getController());
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        mBean = (GroupInfoBean) getIntent().getSerializableExtra(Const.DATA);
        setRightTxt("保存");
        setRightBtnListener(getController());
        if (TypeEnum.GROUP_CREATE == mType) {
            setTitle("新建团队");
        } else if (TypeEnum.GROUP_UPDATE == mType) {
            setTitle("修改团队信息");
        } else {
            ToastUtil.showMessage(this, "参数错误");
            finish();
        }
        initView();
        setGroupInfo();
        setLeftBtnListener(new IhTitleLayout.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                if (isEdit) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示");
                    builder.setMessage("是否保存填写的信息！");
                    builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!checkInput()) {
                                return;
                            }
                            if (getType() == TypeEnum.GROUP_UPDATE) {
                                getController().updateGroup();
                            } else {
                                getController().addGroup();
                            }
                        }
                    });
                    builder.create().show();
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * 设置团队信息
     */
    private void setGroupInfo() {
        groupHospital.setText(MyApplication.userInfo.getHospitalName());
        groupDep.setText(MyApplication.userInfo.getDeptName());
        if (mBean != null) {
            groupName.setText(mBean.getGroupName());
            groupBeGood.setText(mBean.getGroupAdept());
            GlideUtils.load(groupIcon, Const.IMAGE_HOST + mBean.getAvatar(), 0, 0, false, null);
        } else {
            mBean = new GroupInfoBean();
        }
    }


    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", mBean.getAvatar());
        map.put("doctorCode", MyApplication.userInfo.getUserCode());
        map.put("groupAdept", mBean.getGroupAdept());
        map.put("groupId", mBean.getId());
        map.put("groupName", mBean.getGroupName());
        return map;
    }


    @Override
    public void uploadSuccess(String url) {
        mBean.setAvatar(url);
        Glide.with(this).load(Const.IMAGE_HOST + url).into(groupIcon);
        editInfo();
    }

    @Override
    public void uploadFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void addSuccess(GroupInfoBean bean) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.GROUP_REFRESH).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_MSG_LIST).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
        this.finish();
    }

    @Override
    public void addFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void updateSuccess(String success) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.GROUP_REFRESH).build());
        this.finish();
    }

    @Override
    public void updateFailed(String errMsg) {
        if(errMsg==null||"".equals(errMsg.trim())){
            ToastUtil.showMessage(this, errMsg);
        }else{
            ToastUtil.showMessage(this, "保存失败!");
        }
    }

    @Override
    public TypeEnum getType() {
        return mType;
    }

    @Override
    public boolean checkInput() {
        mBean.setGroupName(groupName.getText().toString());
        mBean.setGroupAdept(groupBeGood.getText().toString());
        if (mBean.getGroupName() == null || "".equals(mBean.getGroupName())) {
            ToastUtil.showMessage(this, "请输入团队名称");
            return false;
        }

        if (mBean.getGroupAdept() == null || "".equals(mBean.getGroupAdept())) {
            ToastUtil.showMessage(this, "请输入团队擅长");
            return false;
        }

        return true;
    }

    @Override
    public void setGroupName(String str) {
        groupName.setText(str);
    }

    @Override
    public void setGroupAdep(String str) {
        groupBeGood.setText(str);
    }

    @Override
    public String getGroupName() {
        return groupName.getText().toString();
    }

    @Override
    public String getGroupGood() {
        return groupBeGood.getText().toString();
    }

    @Override
    public void editInfo() {
        isEdit = true;
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("提示");
            builder.setMessage("是否保存填写的信息！");
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!checkInput()) {
                        return;
                    }
                    if (getType() == TypeEnum.GROUP_UPDATE) {
                        getController().updateGroup();
                    } else {
                        getController().addGroup();
                    }
                }
            });
            builder.create().show();
        } else {
            super.onBackPressed();
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
                        getController().uploadFile(selectList.get(i).getPath());
                    }
                    break;
                case UpdateGroupInfoController.GROUP_NAME:
                    setGroupName(data.getStringExtra(Const.DATA));
                    editInfo();
                    break;
                case UpdateGroupInfoController.GROUP_GOOD_BE:
                    setGroupAdep(data.getStringExtra(Const.DATA));
                    editInfo();
                    break;
            }
        }
    }

}
