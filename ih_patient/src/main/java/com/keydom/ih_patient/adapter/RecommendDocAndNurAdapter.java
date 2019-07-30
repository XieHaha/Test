package com.keydom.ih_patient.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;

import java.util.List;
/**
 * 医生和护士记录适配器
 */
public class RecommendDocAndNurAdapter  extends BaseQuickAdapter<RecommendDocAndNurBean,BaseViewHolder> {
    /**
     * 构造方法
     */
    public RecommendDocAndNurAdapter(@Nullable List<RecommendDocAndNurBean> data) {
        super(R.layout.recommend_doc_nur_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendDocAndNurBean item) {
        TextView photo_diagnoses_tv=helper.getView(R.id.photo_diagnoses_tv);
        TextView video_diagnoses_tv=helper.getView(R.id.video_diagnoses_tv);
        TextView inquisition_num_tv=helper.getView(R.id.inquisition_num_tv);
        inquisition_num_tv.setText(item.getInquisitionAmount()+"");
        if(item.getIsEnabledImage()==0){
            photo_diagnoses_tv.setBackgroundResource(R.drawable.diagnose_not_open_bg);
            photo_diagnoses_tv.setTextColor(Color.parseColor("#BBBBBB"));

        }else {
            photo_diagnoses_tv.setBackgroundResource(R.drawable.photo_diagnoses_bg);
            photo_diagnoses_tv.setTextColor(Color.parseColor("#3F98F7"));
        }
        if(item.getIsEnabledVideo()==0){
            video_diagnoses_tv.setBackgroundResource(R.drawable.diagnose_not_open_bg);
            video_diagnoses_tv.setTextColor(Color.parseColor("#BBBBBB"));

        }else {
            video_diagnoses_tv.setBackgroundResource(R.drawable.video_diagnoses_bg);
            video_diagnoses_tv.setTextColor(Color.parseColor("#FB643B"));
        }
        helper.setText(R.id.name_tv,item.getName())
                .setText(R.id.job_title_tv,item.getJobTitle())
                .setText(R.id.depart_tv,"科室： "+item.getDeptName())
                .setText(R.id.adept_tv,"擅长："+item.getAdept())
                .setText(R.id.good_evaluate_num_tv,item.getFeedbackRate())
                .setText(R.id.average_reply_time_tv,item.getAverageResponseTime()+"分钟");
        CircleImageView headImg=helper.getView(R.id.head_img);
        GlideUtils.load(headImg, item.getAvatar() == null ? "" : Const.IMAGE_HOST+item.getAvatar(), 0, 0, false, null);
    }
}
