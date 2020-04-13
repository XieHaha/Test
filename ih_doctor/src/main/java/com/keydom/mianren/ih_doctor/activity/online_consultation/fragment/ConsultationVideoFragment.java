package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationVideoController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationVideoView;

/**
 * @date 20/4/13 13:34
 * @des 会诊室-视频聊天
 */
public class ConsultationVideoFragment extends BaseControllerFragment<ConsultationVideoController> implements ConsultationVideoView {

    public static ConsultationVideoFragment newInstance() {
        return new ConsultationVideoFragment();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_video;
    }
}
