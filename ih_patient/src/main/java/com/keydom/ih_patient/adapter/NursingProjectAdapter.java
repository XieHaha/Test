package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.constant.Type;

import java.util.List;

/**
 * 护理项目适配器
 */
public class NursingProjectAdapter extends BaseQuickAdapter<NursingProjectInfo, BaseViewHolder> {
    private onItemBtnClickListener itemBtnClickListener;
    private String type;

    /**
     * 构建方法
     */
    public NursingProjectAdapter( @Nullable String type,List<NursingProjectInfo> data,onItemBtnClickListener itemBtnClickListener) {
        super(R.layout.nursing_service_item,data);
        this.type=type;
        this.itemBtnClickListener=itemBtnClickListener;
    }

    public interface onItemBtnClickListener {
        void onRightSelectListener();

        void onClickListener(NursingProjectInfo nursingProjectInfo);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingProjectInfo item) {
        ImageView selectImg=helper.getView(R.id.service_selected_img);
        if(Type.ENABLESELCTEDLIST.equals(type)){
            selectImg.setVisibility(View.GONE);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemBtnClickListener.onClickListener(item);
                }
            });
        }else {
            selectImg.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.service_name_tv,item.getName())
                .setText(R.id.service_desc_tv,item.getSummary())
                .setText(R.id.servie_price_tv,"¥"+item.getFee()+"元")
                .setVisible(R.id.service_dep_tv, !StringUtils.isEmpty(item.getHospitalDeptName()))
                .setText(R.id.service_dep_tv,item.getHospitalDeptName()+"");
        ImageView headImg=helper.getView(R.id.service_icon_img);
        GlideUtils.load(headImg, item.getIcon() == null ? "" :Const.IMAGE_HOST+item.getIcon(), 0, 0, false, null);

    }
}
