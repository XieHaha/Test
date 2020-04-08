package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import com.keydom.ih_common.base.BaseFragment;
import com.keydom.mianren.ih_doctor.R;

/**
 * @date 20/4/8 14:33
 * @des 会诊室-病历资料
 */
public class ConsultationInfoFragment extends BaseFragment {

    public static ConsultationInfoFragment newInstance() {
        return new ConsultationInfoFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_info;
    }
}
