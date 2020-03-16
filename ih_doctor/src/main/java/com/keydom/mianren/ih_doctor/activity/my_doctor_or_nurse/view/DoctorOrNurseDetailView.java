package com.keydom.mianren.ih_doctor.activity.my_doctor_or_nurse.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DoctorEvaluateItem;
import com.keydom.mianren.ih_doctor.bean.DoctorMainBean;

import java.util.List;

/**
 * created date: 2019/1/2 on 14:54
 * des:我的医生详情view
 */
public interface DoctorOrNurseDetailView extends BaseView {
    /**
     * 关闭当前
     */
    void finishThis();

    /**
     * 获取主页回调
     */
    void getMainCallBack(List<MultiItemEntity> data, DoctorMainBean doctorMainBean);

    /**
     * 获取评论回调
     */
    void getEvaluates(List<DoctorEvaluateItem> data);

    /**
     * 加载更多失败回调
     */
    void loadMoreError();

    /**
     * 获取医生code
     */
    String getCode();

    /**
     * 获取当前医生实体
     */
    DoctorMainBean getDoctorMainBean();

    /**
     * 点赞评论成功
     */
    void doCommentLikeSuccess(int pos, int isLike);

    /**
     * 获取医生type
     */
    int getType();

    /**
     * 设置关注成功
     */
    void setAttentionSuccess(int isAttention);
}
