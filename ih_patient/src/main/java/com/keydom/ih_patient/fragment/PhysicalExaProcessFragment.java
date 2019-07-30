package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.controller.PhysicalExaProcessController;
import com.keydom.ih_patient.fragment.view.PhysicalExaProcessView;
import com.keydom.ih_patient.utils.ToastUtil;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 体检套餐评论页面
 */
public class PhysicalExaProcessFragment extends BaseControllerFragment<PhysicalExaProcessController> implements PhysicalExaProcessView {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_physical_exa_process_layout;
    }
    private TextView exa_buy_tv,physical_exa_process_rich_text;
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        physical_exa_process_rich_text=view.findViewById(R.id.physical_exa_process_rich_text);
        getController().getPhysicalExaProcess(Global.getSelectedPhysicalExa().getId());
        RichText.initCacheDir(getContext());
    }

    @Override
    public void FillPhysicalProcess(String data) {
        RichText.from(data).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(physical_exa_process_rich_text);
    }

    @Override
    public void FillPhysicalProcessFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),"获取体检流程失败："+errMsg);
    }
}
