package com.keydom.ih_doctor.activity.my_doctor_or_nurse.controller;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.constant.Global;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ShareUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.view.DoctorOrNurseDetailView;
import com.keydom.ih_doctor.bean.DoctorEvaluateItem;
import com.keydom.ih_doctor.bean.DoctorHeadItem;
import com.keydom.ih_doctor.bean.DoctorMainBean;
import com.keydom.ih_doctor.bean.DoctorTeamItem;
import com.keydom.ih_doctor.bean.DoctorTextItem;
import com.keydom.ih_doctor.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/2 on 14:54
 * des:医生详情控制器
 */
public class DoctorOrNurseDetailController extends ControllerImpl<DoctorOrNurseDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getView().finishThis();
                break;
            case R.id.share:
                //分享
                showShare();
                break;
            case R.id.follow_group:
                //二维码关注
                break;
            case R.id.follow:
                DoctorMainBean doctorMainBean = getView().getDoctorMainBean();
                if (doctorMainBean.getInfo() != null) {
                    int isA = doctorMainBean.getInfo().getIsAttention() == 1 ? 0 : 1;
                    setAttention(isA, doctorMainBean.getInfo().getUuid());
                }
                break;
            default:
        }
    }

    /**
     * 展示分享弹框
     */
    private void showShare() {
        ShareUtils.showShareUtils(getContext(), new ShareUtils.IOnShareCallBack() {
            @Override
            public void onShareSelect(int type) {

            }
        });
    }

    /**
     * 设置关注
     */
    private void setAttention(int isAttention, String doctorCode) {
        Map<String, Object> map = new HashMap<>();
//        map.put("userId", Global.getUserId());
        map.put("isAttention", isAttention);
        map.put("doctorCode", doctorCode);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).attentionDoctor(map), new HttpSubscriber<Object>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().setAttentionSuccess(isAttention);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 跳转聊天页面
     */
    private void goChat(String uuCode) {
        if (uuCode == null) {
            return;
        }
        ImClient.getUserInfoProvider().getUserInfoAsync(uuCode, (success, result, code) -> {
            if (success) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(ImConstants.CHATTING, true);
                if (getView().getType() == DoctorOrNurseDetailActivity.NURSE) {
                    bundle.putBoolean("isNurse", true);
                }
                ImClient.startConversation(getContext(), uuCode, bundle);
            }
        });
    }

    /**
     * 评论点赞
     */
    public void doCommentLike(int position, long commentId, int isLike) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("commentId", commentId);
        map.put("userId", Global.getUserId());
        map.put("isLike", isLike);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).doCommentLike(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>() {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().doCommentLikeSuccess(position, isLike);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取医生详情
     */
    public void getDoctorDetail(int type, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("doctorCode", code);
        map.put("userId", Global.getUserId());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getMyFollowDoctorDetail(map), new HttpSubscriber<DoctorMainBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DoctorMainBean data) {
                hideLoading();
                List<MultiItemEntity> multiItemEntities = transFormList(data);
                getView().getMainCallBack(multiItemEntities, data);

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 处理详情数据
     */
    private List<MultiItemEntity> transFormList(DoctorMainBean data) {
        List<MultiItemEntity> list = new ArrayList<>();
        DoctorMainBean.InfoBean info = data.getInfo();

        DoctorHeadItem headItem1 = new DoctorHeadItem();
        headItem1.setExpand(true);
        headItem1.setTitle("简介");
        DoctorTextItem item1 = new DoctorTextItem();
        if (info != null) {
            item1.setContent(info.getIntro());
            headItem1.addSubItem(item1);
        }
        list.add(headItem1);

        DoctorHeadItem headItem2 = new DoctorHeadItem();
        headItem2.setExpand(true);
        headItem2.setTitle("擅长");
        DoctorTextItem item2 = new DoctorTextItem();
        if (info != null) {
            item2.setContent(info.getAdept());
            headItem2.addSubItem(item2);
        }
        list.add(headItem2);

        DoctorHeadItem headItem3 = new DoctorHeadItem();
        headItem3.setTitle("团队成员");
        list.add(headItem3);
        List<DoctorTeamItem> teamMembers = data.getTeamMembers();
        if (teamMembers != null && teamMembers.size() > 0) {
            list.add(teamMembers.get(0));
        }

        DoctorHeadItem headItem4 = new DoctorHeadItem();
        headItem4.setTitle("患者评价");
        list.add(headItem4);
        return list;
    }

    /**
     * 获取医生评论列表
     */
    public void getDoctorEvaluates(String code, int currentPage, boolean isDoctor) {
        if (currentPage != 1) {
            showLoading();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("doctorCode", code);
        if (isDoctor) {
            map.put("commentType", 1);
        }
        map.put("currentPage", currentPage);
        map.put("pageSize", "8");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getDoctorEvaluates(map), new HttpSubscriber<List<DoctorEvaluateItem>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DoctorEvaluateItem> data) {
                hideLoading();
                getView().getEvaluates(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                getView().loadMoreError();
                return super.requestError(exception, code, msg);
            }
        });
    }


}
