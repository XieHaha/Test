package com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ShareUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.view.DoctorOrNurseDetailView;
import com.keydom.mianren.ih_patient.bean.DoctorEvaluateItem;
import com.keydom.mianren.ih_patient.bean.DoctorHeadItem;
import com.keydom.mianren.ih_patient.bean.DoctorMainBean;
import com.keydom.mianren.ih_patient.bean.DoctorTeamItem;
import com.keydom.mianren.ih_patient.bean.DoctorTextItem;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.view.DiagnosesApplyDialog;

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
    @SingleClick(1000)
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
            case R.id.video_inquiry_group:
                //视频问诊
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？",
                            new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    LoginActivity.start(getContext());
                                }
                            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else {
                    if (TextUtils.equals(App.userInfo.getCertification(), "0")) {
                        isCanDiagnose(1, getView().getCode());
                    } else {
                        ToastUtil.showMessage(getContext(), "您还未实名认证，前往个人中心实名认证后才能预约问诊");
                    }
                }

                break;
            case R.id.pic_inquiry_group:
                //图文问诊
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？",
                            new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    LoginActivity.start(getContext());
                                }
                            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else {
                    if (TextUtils.equals(App.userInfo.getCertification(), "0")) {
                        isCanDiagnose(0, getView().getCode());
                    } else {
                        ToastUtil.showMessage(getContext(), "您还未实名认证，前往个人中心实名认证后才能预约问诊");
                    }
                }

                break;
            case R.id.follow:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？",
                            new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    LoginActivity.start(getContext());
                                }
                            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else {
                    DoctorMainBean doctorMainBean = getView().getDoctorMainBean();
                    if (doctorMainBean.getInfo() != null) {
                        int isA = doctorMainBean.getInfo().getIsAttention() == 1 ? 0 : 1;
                        setAttention(isA, doctorMainBean.getInfo().getUuid());
                    }
                }
                break;
            default:
        }
    }

    /**
     * 展示咨询弹框
     */
    private void showApplyDialog(String type) {
        if (getView().getDoctorMainBean() != null && getView().getDoctorMainBean().getInfo() != null && getView().getDoctorMainBean().getInfo().getIsInquiry() == 1) {
            if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
                if (getView().getDoctorMainBean().getInfo().getIsEnabledVideo() == 1) {
                    getView().showApplyDialog(type);
                } else {
                    ToastUtils.showShort("暂时不接受视频咨询");
                }
            } else if (DiagnosesApplyDialog.PHOTODIAGNOSES.equals(type)) {
                if (getView().getDoctorMainBean().getInfo().getIsEnabledImage() == 1) {
                    getView().showApplyDialog(type);
                } else {
                    ToastUtils.showShort("暂时不接受图文咨询");
                }
            }
        } else {
            ToastUtils.showShort("暂时不接受咨询");
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
        map.put("userId", Global.getUserId());
        map.put("isAttention", isAttention);
        map.put("doctorCode", doctorCode);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).attentionDoctor(map), new HttpSubscriber<Object>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().setAttentionSuccess(isAttention);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).doCommentLike(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().doCommentLikeSuccess(position, isLike);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
        //        map.put("type", type);
        map.put("userId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getMyFollowDoctorDetail(map), new HttpSubscriber<DoctorMainBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DoctorMainBean data) {
                List<MultiItemEntity> multiItemEntities = transFormList(data);
                getView().getMainCallBack(multiItemEntities, data);

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 判断是否能问诊
     */
    private void isCanDiagnose(int type, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("doctorCode", code);
        map.put("type", type);
        map.put("userId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getUserIsPlaceOrder(map), new HttpSubscriber<Integer>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Integer data) {
                if (data == 1) {
                    if (type == 0) {
                        showApplyDialog(DiagnosesApplyDialog.PHOTODIAGNOSES);
                    } else {
                        showApplyDialog(DiagnosesApplyDialog.VIDEODIAGNOSES);
                    }
                } else {
                    ToastUtil.showMessage(getContext(), "当前无法对该医生进行问诊服务");
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
       /* if (isDoctor) {
            map.put("commentType", 1);
        }*/
        map.put("currentPage", currentPage);
        map.put("pageSize", "8");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getDoctorEvaluates(map), new HttpSubscriber<List<DoctorEvaluateItem>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DoctorEvaluateItem> data) {
                hideLoading();
                getView().getEvaluates(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                getView().loadMoreError();
                return super.requestError(exception, code, msg);
            }
        });
    }


}
