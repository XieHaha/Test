package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisResultController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisResultView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/10 16:57
 * @des 羊水穿刺预约结果页
 */
public class AmniocentesisResultFragment extends BaseControllerFragment<AmniocentesisResultController> implements AmniocentesisResultView {
    @BindView(R.id.amniocentesis_result_help_tv)
    TextView amniocentesisResultHelpTv;
    @BindView(R.id.amniocentesis_result_agree_tv)
    TextView amniocentesisResultAgreeTv;
    @BindView(R.id.amniocentesis_result_notice_tv)
    TextView amniocentesisResultNoticeTv;
    @BindView(R.id.amniocentesis_result_next_tv)
    TextView amniocentesisResultNextTv;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_amniocentesis_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        amniocentesisResultNextTv.setOnClickListener(v -> getActivity().finish());

        amniocentesisResultHelpTv.setOnClickListener(getController());
        amniocentesisResultAgreeTv.setOnClickListener(getController());
        amniocentesisResultNoticeTv.setOnClickListener(getController());
    }
}
