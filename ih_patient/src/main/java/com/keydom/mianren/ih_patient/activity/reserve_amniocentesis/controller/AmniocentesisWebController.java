package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisRecordActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisWebView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * @date 20/3/11 14:26
 * @des 羊水穿刺预约web
 */
public class AmniocentesisWebController extends ControllerImpl<AmniocentesisWebView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {

    @Override
    public void OnRightTextClick(View v) {
        AmniocentesisRecordActivity.start(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_web_agree_layout:
                getView().onProtocolSelect(true);
                break;
            case R.id.amniocentesis_web_disagree_layout:
                getView().onProtocolSelect(false);
                break;
            case R.id.amniocentesis_web_next_tv:
                if (!getView().isSelectProtocol()) {
                    ToastUtil.showMessage(mContext, "请同意以上协议内容");
                    return;
                }
                switch (getView().getProtocol()) {
                    case AMNIOCENTESIS_WEB_RESERVE:
                        EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_APPLY, null));
                        break;
                    case AMNIOCENTESIS_AGREE_PROTOCOL:
                        break;
                    case AMNIOCENTESIS_NOTICE:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
