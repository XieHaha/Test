package com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.controller.DoctorOrNurseDetailController;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.view.DoctorOrNurseDetailView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.DiagnosesApplyActivity;
import com.keydom.mianren.ih_patient.adapter.DoctorOrNurseDetailAdapter;
import com.keydom.mianren.ih_patient.bean.DoctorEvaluateItem;
import com.keydom.mianren.ih_patient.bean.DoctorMainBean;
import com.keydom.mianren.ih_patient.bean.DoctorTeamItem;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.view.DiagnosesApplyDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/2 on 14:53
 * des:????????????????????????
 */
public class DoctorOrNurseDetailActivity extends BaseControllerActivity<DoctorOrNurseDetailController> implements DoctorOrNurseDetailView {
    //????????????
    public final static String TYPE = "type";
    //????????????
    public static final String CODE = "doctorCode";
    //???????????? 0 ????????????  1 ????????????
    public static final int DOCTOR = 1;
    public static final int NURSE = 0;
    private View mHead;

    /**
     * ??????/??????
     */
    private int mType;
    /**
     * ??????id
     */
    private String mCode;

    private TextView mTitle;
    private ImageView mShare;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private DoctorOrNurseDetailAdapter mAdapter;

    private LinearLayout layoutInquiry;

    private CircleImageView mHeadImg;
    private TextView mName;
    private ImageView mQrCode;
    private TextView mInquiryNum;
    private TextView mGoodsNum;
    private TextView mFollow;
    private TextView mPicInquiry;
    private TextView mVideoInquiry;
    private TextView mJobTitle;
    private TextView mDepartName;
    private TextView mJobTime;
    private LinearLayout mFollowGroup;
    private TextView mFollowTv;

    /**
     * ????????????
     */
    private int mCurrPage = 1;

    /**
     * ????????????
     */
    private DoctorMainBean mDoctorMainBean;

    /**
     * ?????????????????????
     */
    public static void startNursePage(Context context, String code) {
        Intent starter = new Intent(context, DoctorOrNurseDetailActivity.class);
        starter.putExtra(TYPE, NURSE);
        starter.putExtra(CODE, code);
        context.startActivity(starter);
    }

    /**
     * ?????????????????????
     */
    public static void startDoctorPage(Context context, int type, String code) {
        Intent starter = new Intent(context, DoctorOrNurseDetailActivity.class);
        starter.putExtra(TYPE, type);
        starter.putExtra(CODE, code);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_doctor_or_nurse_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getView();
        StatusBarUtils.setWindowStatusBarColor(this, R.color.color_399cf9);
        //        mType = getIntent().getIntExtra(TYPE, 0);
        mCode = getIntent().getStringExtra(CODE);

        mAdapter = new DoctorOrNurseDetailAdapter(new ArrayList<>(), null);
        //        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        //        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        //            @Override
        //            public int getSpanSize(int position) {
        //                return mAdapter.getItemViewType(position) == DoctorOrNurseDetailAdapter
        //                .TYPE_TEAM ? 1 : manager.getSpanCount();
        //            }
        //        });
        mRefreshLayout.setEnableRefresh(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHead);
        getController().getDoctorDetail(mType, mCode);
    }

    /**
     * ????????????
     */
    private void getView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mShare = findViewById(R.id.share);
        mShare.setOnClickListener(getController());
        findViewById(R.id.back).setOnClickListener(getController());
        findViewById(R.id.share).setOnClickListener(getController());
        mTitle = findViewById(R.id.title);
        mHead = LayoutInflater.from(this).inflate(R.layout.doctor_or_nurse_detail_head, null);
        mName = mHead.findViewById(R.id.name);
        mQrCode = mHead.findViewById(R.id.qr_code);
        mInquiryNum = mHead.findViewById(R.id.inquiry_num);
        mHeadImg = mHead.findViewById(R.id.head_img);
        mGoodsNum = mHead.findViewById(R.id.high_opinion);
        mFollow = mHead.findViewById(R.id.follow_num);
        mFollowGroup = mHead.findViewById(R.id.follow_group);
        mFollowTv = mHead.findViewById(R.id.follow);
        mFollowTv.setOnClickListener(getController());
        mPicInquiry = mHead.findViewById(R.id.pic_inquiry_cost);
        mVideoInquiry = mHead.findViewById(R.id.video_inquiry_cost);
        mJobTitle = mHead.findViewById(R.id.job_title);
        mDepartName = mHead.findViewById(R.id.depart_name);
        mJobTime = mHead.findViewById(R.id.time);
        layoutInquiry = mHead.findViewById(R.id.layout_inquiry);
        if (Global.isMember()) {
            layoutInquiry.setVisibility(View.GONE);
        } else {
            layoutInquiry.setVisibility(View.VISIBLE);
        }
        mHead.findViewById(R.id.follow_group).setOnClickListener(getController());
        mHead.findViewById(R.id.video_inquiry_group).setOnClickListener(getController());
        mHead.findViewById(R.id.pic_inquiry_group).setOnClickListener(getController());
    }

    @Override
    public void finishThis() {
        finish();
    }

    /**
     * ???????????????????????????????????????string
     */
    private SpannableStringBuilder transFormString(int color1, String str1, int color2,
                                                   String str2) {
        SpannableStringBuilder spannableStringBuilder =
                new SpanUtils().append(str1).setForegroundColor(color1).setFontSize(13, true).append(str2).setForegroundColor(color2).setFontSize(13, true).create();
        return spannableStringBuilder;
    }

    /**
     * ????????????id
     */
    private int getTextColor(int colorId) {
        return getResources().getColor(colorId);
    }


    @Override
    public void getMainCallBack(List<MultiItemEntity> data, DoctorMainBean doctorMainBean) {
        mDoctorMainBean = doctorMainBean;
        mAdapter.setTeams(doctorMainBean.getTeamMembers());
        DoctorMainBean.InfoBean info = doctorMainBean.getInfo();
        if (info != null) {
            mType = info.getIsDoctor();
            SpannableStringBuilder imgInquiry = null;
            SpannableStringBuilder videoInquiry = null;
            if (mType == DOCTOR) {
                imgInquiry = transFormString(getTextColor(R.color.color_333333), "????????????",
                        getTextColor(R.color.nursing_status_red),
                        "(" + doctorMainBean.getImageFee() + "???/??????");
                videoInquiry = transFormString(getTextColor(R.color.color_333333), "????????????",
                        getTextColor(R.color.nursing_status_red),
                        "(" + doctorMainBean.getVideoFee() + "???/??????");
            } else if (mType == NURSE) {
                imgInquiry = transFormString(getTextColor(R.color.color_333333), "????????????",
                        getTextColor(R.color.nursing_status_red),
                        "(" + doctorMainBean.getImageFee() + "???/??????");
                videoInquiry = transFormString(getTextColor(R.color.color_333333), "????????????",
                        getTextColor(R.color.nursing_status_red),
                        "(" + doctorMainBean.getVideoFee() + "???/??????");
            }
            if (mType == DOCTOR) {
                mTitle.setText("????????????");
            } else if (mType == NURSE) {
                mTitle.setText("????????????");
            }
            getController().getDoctorEvaluates(mCode, 1,
                    mType == DoctorOrNurseDetailActivity.DOCTOR);
            mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getDoctorEvaluates(getCode(), mCurrPage, mType == DOCTOR));
            mPicInquiry.setText(imgInquiry);
            mVideoInquiry.setText(videoInquiry);
            mName.setText(info.getName());
            mInquiryNum.setText(transFormString(getTextColor(R.color.title_bar_text_color),
                    "????????????", getTextColor(R.color.other_login_color),
                    String.valueOf(info.getInquisitionAmount())));
            mGoodsNum.setText(transFormString(getTextColor(R.color.title_bar_text_color), "????????????",
                    getTextColor(R.color.goods_num_color), String.valueOf(info.getFeedbackRate())));
            mFollow.setText(transFormString(getTextColor(R.color.title_bar_text_color), "????????????",
                    getTextColor(R.color.attention_num_color),
                    String.valueOf(info.getAttentionAmount())));
            //            RequestOptions requestOptions = new RequestOptions()
            //                    .error(R.mipmap.im_default_head_image) //??????????????????
            //                    .fallback(R.mipmap.im_default_head_image) //url????????????
            //                    .signature(new ObjectKey(String.valueOf(System
            //                    .currentTimeMillis())))
            //                    .transform(new CircleCrop());
            String head = Const.IMAGE_HOST + info.getAvatar();
            //            Glide.with(this)
            //                    .load(head)
            //                    .apply(requestOptions)
            //                    .apply(bitmapTransform(new CropCircleTransformation()))
            //                    .into(mHeadImg);
            GlideUtils.load(mHeadImg, head, 0, 0, false, null);
            if (info.getJobTitle() != null && !"".equals(info.getJobTitle())) {
                mJobTitle.setText(info.getJobTitle());
            } else {
                mJobTitle.setVisibility(View.GONE);
            }
            mDepartName.setText(info.getDeptName());
            mJobTime.setText(info.getSchedu() == null || "".equals(info.getSchedu()) ? "??????????????????" :
                    "??????" + info.getSchedu());
            Bitmap image = CodeUtils.createImage(info.getQrcode(), 400, 400, null);
            mQrCode.setImageBitmap(image);
            mQrCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GotoActivityUtil.gotoZxingActivity(DoctorOrNurseDetailActivity.this,
                            info.getQrcode());
                }
            });

            if (info.getIsAttention() == 1) {
                mFollowTv.setText("?????????");
            }
        }
        mAdapter.setNewData(data);

        mAdapter.expandAll();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.group:
                    DoctorTeamItem teamItem = (DoctorTeamItem) adapter.getData().get(position);
                    Intent i = new Intent(this, DoctorOrNurseDetailActivity.class);
                    i.putExtra(CODE, teamItem.getUuid());
                    i.putExtra(TYPE, mType);
                    ActivityUtils.startActivity(i);
                    break;
                case R.id.isLike:
                    if (Global.getUserId() != -1) {
                        DoctorEvaluateItem evaluateItem =
                                (DoctorEvaluateItem) adapter.getData().get(position);
                        getController().doCommentLike(position, evaluateItem.getId(),
                                evaluateItem.getIsLike() == 0 ? 1 : 0);
                    } else {
                        new GeneralDialog(getContext(), "?????????????????????????????????????????????????????????",
                                new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        LoginActivity.start(getContext());
                                    }
                                }).setTitle("??????").setCancel(false).setPositiveButton("??????").show();
                    }

                    break;
                default:
            }
        });
    }

    @Override
    public void getEvaluates(List<DoctorEvaluateItem> data) {
        if (mRefreshLayout != null && mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
        mAdapter.addData(data);
        mCurrPage += 1;
    }

    @Override
    public void loadMoreError() {
        if (mRefreshLayout != null && mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public String getCode() {
        if (mDoctorMainBean.getInfo() != null) {
            return mDoctorMainBean.getInfo().getUuid();
        }
        return null;
    }

    @Override
    public DoctorMainBean getDoctorMainBean() {
        return mDoctorMainBean;
    }

    @Override
    public void doCommentLikeSuccess(int pos, int isLike) {
        DoctorEvaluateItem evaluateItem = (DoctorEvaluateItem) mAdapter.getData().get(pos);
        evaluateItem.setIsLike(isLike);
        int likeAmount = evaluateItem.getLikeAmount();
        if (isLike == 0) {
            likeAmount -= 1;
            if (likeAmount < 0) {
                likeAmount = 0;
            }
        } else {
            likeAmount += 1;
        }
        evaluateItem.setLikeAmount(likeAmount);
        mAdapter.refreshNotifyItemChanged(pos);
    }

    @Override
    public int getType() {
        return mType;
    }

    @Override
    public void showApplyDialog(String type) {
        String fee = "";
        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
            fee = String.valueOf(mDoctorMainBean.getVideoFee());
        } else {
            fee = String.valueOf(mDoctorMainBean.getImageFee());
        }
        String doctorName = "";
        if (mDoctorMainBean.getInfo().getJobTitle() != null && !"".equals(mDoctorMainBean.getInfo().getJobTitle())) {
            doctorName =
                    mDoctorMainBean.getInfo().getJobTitle() + "-" + mDoctorMainBean.getInfo().getName();
        } else {
            doctorName = mDoctorMainBean.getInfo().getName();
        }
        new DiagnosesApplyDialog(getContext(), fee, mDoctorMainBean.getInfo().getDiscount(),
                doctorName, mDoctorMainBean.getInfo().getWaitInquiryCount(), type, () -> {
            DiagnosesApplyActivity.start(getContext(), type, mDoctorMainBean.getInfo());
        }).show();
    }

    @Override
    public void setAttentionSuccess(int isAttention) {
        int attentiongAmount = mDoctorMainBean.getInfo().getAttentionAmount();
        if (isAttention == 1) {
            mFollowTv.setText("?????????");
            ToastUtils.showShort("????????????");
            attentiongAmount += 1;
        } else {
            mFollowTv.setText("??????");
            ToastUtils.showShort("????????????");
            attentiongAmount -= 1;
            if (attentiongAmount < 0) {
                attentiongAmount = 0;
            }
        }
        mDoctorMainBean.getInfo().setAttentionAmount(attentiongAmount);
        mFollow.setText(transFormString(getTextColor(R.color.title_bar_text_color), "????????????",
                getTextColor(R.color.attention_num_color),
                String.valueOf(mDoctorMainBean.getInfo().getAttentionAmount())));
        mDoctorMainBean.getInfo().setIsAttention(isAttention);
    }

    /**
     * ??????????????? ????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void idCardCertificateSuccess(Event event) {
        if (event.getType() == EventType.IDCARDCERTIFICATESUCCESS) {
            getController().initUserData();
        }
    }

    @Override
    public void initUserData(UserInfo data) {
        App.userInfo = data;
        LocalizationUtils.fileSave2Local(getContext(), data, "userInfo");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}