package com.keydom.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.MRadioButton;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.im.ConversationActivity;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DiagnoseHandleProposalController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseHandleProposalView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：在线问诊处置建议页面（无用，处置建议功能集成到诊断与处方页面）
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseHandleProposalActivity extends BaseControllerActivity<DiagnoseHandleProposalController> implements DiagnoseHandleProposalView {
    /**
     * 问诊单对象
     */
    private InquiryBean orderBean;
    private EditText sub_item_entrust_et;
    private Button submit_btn;
    private MRadioButton re_diagnose_rb;
    private RelativeLayout rediagnoseRl;

    /**
     * 启动处置建议页面
     *
     * @param context
     * @param bean    问诊单对象
     */
    public static void start(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, DiagnoseHandleProposalActivity.class);
        starter.putExtra(Const.DATA, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_handle_proposal_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        orderBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
        initView();
        setTitle("处置建议");

    }


    /**
     * 初始化界面
     */
    private void initView() {
        rediagnoseRl = this.findViewById(R.id.rediagnose_rl);
        re_diagnose_rb = this.findViewById(R.id.re_diagnose_rb);
        submit_btn = this.findViewById(R.id.submit_btn);
        sub_item_entrust_et = this.findViewById(R.id.sub_item_entrust_et);
        submit_btn.setOnClickListener(getController());
        if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE || SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
            rediagnoseRl.setVisibility(View.GONE);
        }
    }

    @Override
    public Map<String, Object> getHandleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("inquiryId", orderBean.getId());
        map.put("suggest", sub_item_entrust_et.getText().toString());
        return map;
    }

    @Override
    public void handleSuccess(DiagnoseHandleBean bean) {
        List<IMMessage> messageList = new ArrayList<>();
        DisposalAdviceAttachment attachment = new DisposalAdviceAttachment();
        attachment.setId(bean.getId());
        attachment.setContent(bean.getSuggest());
        messageList.add(ImClient.createDisposalAdviceMessage(orderBean.getUserCode(), SessionTypeEnum.P2P, "处置建议", attachment));
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) messageList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void handleFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    @Override
    public boolean checkSubmit() {
        if ("".equals(sub_item_entrust_et.getText().toString())) {
            return false;
        }
        return true;
    }
}
