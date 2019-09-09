package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.DoctorCooperationController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.DoctorCooperationView;
import com.keydom.ih_doctor.bean.GroupInfoBean;
import com.keydom.ih_doctor.bean.GroupInfoRes;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：医生协作主页
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DoctorCooperationActivity extends BaseControllerActivity<DoctorCooperationController> implements DoctorCooperationView {
    /**
     * 可以创建团队
     */
    public static final int CAN_CREATE_GROUP = 1;
    /**
     * 无法创建团队
     */
    public static final int CAN_NOT_CREATE_GROUP = 0;
    private RelativeLayout noGroupRl, groupRl, groupExchangeRl, groupMemberRl, diagnosisRecoderRl, changeDiagnoseRl, receiveDiagnoseRl, groupDiagnoseRl, receiveGroupDiagnoseRl;
    private TextView groupCut, changeDiagnoseRecoder, groupDiagnoseRecoder, doctorCoopreationName, doctorCoopreationDec, noGroupCreate;
    private ImageView groupEdit;
    private CircleImageView doctorCoopreationIcon;
    private View cooperate_redpoint_view;
    /**
     * 团队列表
     */
    private List<GroupInfoBean> groupList = new ArrayList<>();
    /**
     * 判断是否可以创建团队
     */
    private int isCreate = 0;
    /**
     * 当前属于的团队ID
     */
    private int currentGroupId = -1;

    /**
     * 启动医生协作主页
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, DoctorCooperationActivity.class);
        context.startActivity(starter);
    }

    private void initView() {
        cooperate_redpoint_view=this.findViewById(R.id.cooperate_redpoint_view);
        noGroupRl = this.findViewById(R.id.no_group_rl);
        groupRl = this.findViewById(R.id.doctor_coopreation_info_rl);
        groupExchangeRl = this.findViewById(R.id.group_exchange_rl);
        groupMemberRl = this.findViewById(R.id.group_member_rl);
        diagnosisRecoderRl = this.findViewById(R.id.diagnosis_recoder_rl);
        changeDiagnoseRl = this.findViewById(R.id.change_diagnose_rl);
        receiveDiagnoseRl = this.findViewById(R.id.receive_diagnose_rl);
        groupDiagnoseRl = this.findViewById(R.id.group_diagnose_rl);
        receiveGroupDiagnoseRl = this.findViewById(R.id.receive_group_diagnose_rl);
        groupCut = this.findViewById(R.id.group_cut);
        changeDiagnoseRecoder = this.findViewById(R.id.change_diagnose_recoder);
        groupDiagnoseRecoder = this.findViewById(R.id.group_diagnose_recoder);
        doctorCoopreationName = this.findViewById(R.id.doctor_coopreation_name);
        doctorCoopreationDec = this.findViewById(R.id.doctor_coopreation_dec);
        noGroupCreate = this.findViewById(R.id.no_group_create);
        groupEdit = this.findViewById(R.id.group_edit);
        doctorCoopreationIcon = this.findViewById(R.id.doctor_coopreation_icon);
        groupExchangeRl.setOnClickListener(getController());
        groupMemberRl.setOnClickListener(getController());
        diagnosisRecoderRl.setOnClickListener(getController());
        changeDiagnoseRl.setOnClickListener(getController());
        receiveDiagnoseRl.setOnClickListener(getController());
        groupDiagnoseRl.setOnClickListener(getController());
        receiveGroupDiagnoseRl.setOnClickListener(getController());
        groupCut.setOnClickListener(getController());
        changeDiagnoseRecoder.setOnClickListener(getController());
        groupDiagnoseRecoder.setOnClickListener(getController());
        noGroupCreate.setOnClickListener(getController());
        groupEdit.setOnClickListener(getController());
        if(MyApplication.receiveReferral>0)
            cooperate_redpoint_view.setVisibility(View.VISIBLE);
        else
            cooperate_redpoint_view.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.receiveReferral>0)
            cooperate_redpoint_view.setVisibility(View.VISIBLE);
        else
            cooperate_redpoint_view.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_doctor_cooperation_main;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("医生协作");
        setRightTxt("新建团队");
        setRightBtnListener(getController());
        initView();
        pageLoading();
        getController().getGroupInfo();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getGroupInfo();
            }
        });
    }


    @Override
    public void getGroupSuccess(GroupInfoRes res) {
        pageLoadingSuccess();
        groupList.clear();
        groupList.addAll(res.getList());
        isCreate = res.getIsCreate();
        if (isCreate != CAN_CREATE_GROUP) {
            setRightImgVisibility(false);
        }
        showGroupView();
    }

    /**
     * 根据是否有团队决定显示哪个页面
     */
    private void showGroupView() {
        currentGroupId = SharePreferenceManager.getGroup();
        if (groupList.size() > 0) {
            groupRl.setVisibility(View.VISIBLE);
            noGroupRl.setVisibility(View.GONE);
            showGroupInfo(getCurrentGroup());
        } else {
            noGroupRl.setVisibility(View.VISIBLE);
            groupRl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置当前团队信息
     */
    private void showGroupInfo(GroupInfoBean groupInfoBean) {
        doctorCoopreationName.setText(groupInfoBean.getGroupName());
        doctorCoopreationDec.setText(groupInfoBean.getGroupAdept());
        GlideUtils.load(doctorCoopreationIcon, Const.IMAGE_HOST + groupInfoBean.getAvatar(), 0, 0, false, null);
    }

    /**
     * 获取当前显示的团队信息
     *
     * @return
     */
    @Override
    public GroupInfoBean getCurrentGroup() {
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getId() == currentGroupId) {
                return groupList.get(i);
            }
        }
        if (groupList.size() > 0) {
            currentGroupId = groupList.get(0).getId();
            SharePreferenceManager.setGroup(currentGroupId);
            return groupList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int getIsCreate() {
        return isCreate;
    }


    @Override
    public void getGroupFailed(String msg) {
        pageLoadingFail();
    }

    @Override
    public List<GroupInfoBean> getGroup() {
        return groupList;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.GROUP_CUT) {
            showGroupView();
        } else if (messageEvent.getType() == EventType.GROUP_REFRESH) {
            getController().getGroupInfo();
        }
    }
}
