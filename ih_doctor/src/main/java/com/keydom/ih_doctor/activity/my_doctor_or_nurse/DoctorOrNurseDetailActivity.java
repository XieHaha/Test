package com.keydom.ih_doctor.activity.my_doctor_or_nurse;

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
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.controller.DoctorOrNurseDetailController;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.view.DoctorOrNurseDetailView;
import com.keydom.ih_doctor.adapter.DoctorOrNurseDetailAdapter;
import com.keydom.ih_doctor.bean.DoctorEvaluateItem;
import com.keydom.ih_doctor.bean.DoctorMainBean;
import com.keydom.ih_doctor.bean.DoctorTeamItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/2 on 14:53
 * des:我的医生详情页面
 */
public class DoctorOrNurseDetailActivity extends BaseControllerActivity<DoctorOrNurseDetailController> implements DoctorOrNurseDetailView {
    //启动类型
    public final static String TYPE = "type";
    //医生编码
    public static final String CODE = "doctorCode";
    //启动类型 0 医生首页  1 护士首页
    public static final int DOCTOR = 1;
    public static final int NURSE = 0;
    private View mHead;

    /**
     * 医生/护士
     */
    private int mType;
    /**
     * 医生id
     */
    private String mCode;

    private TextView mTitle;
    private ImageView mShare;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private DoctorOrNurseDetailAdapter mAdapter;

    private CircleImageView mHeadImg;
    private TextView mName;
    private ImageView mQrCode;
    private TextView mInquiryNum;
    private TextView mGoodsNum;
    private TextView mFollow;
    private TextView mJobTitle;
    private TextView mDepartName;
    private TextView mJobTime;
    private LinearLayout mFollowGroup;
    private TextView mFollowTv;

    /**
     * 当前页数
     */
    private int mCurrPage = 1;

    /**
     * 医生实体
     */
    private DoctorMainBean mDoctorMainBean;

    /**
     * 护士主页启动页
     */
    public static void startNursePage(Context context, String code) {
        Intent starter = new Intent(context, DoctorOrNurseDetailActivity.class);
        starter.putExtra(TYPE, NURSE);
        starter.putExtra(CODE, code);
        context.startActivity(starter);
    }

    /**
     * 医生主页启动页
     */
    public static void startDoctorPage(Context context, String code) {
//        Intent starter = new Intent(context, DoctorOrNurseDetailActivity.class);
//        starter.putExtra(TYPE, DOCTOR);
//        starter.putExtra(CODE, code);
//        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_doctor_or_nurse_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
        StatusBarUtils.setWindowStatusBarColor(this, R.color.mine_color);
        mCode = getIntent().getStringExtra(CODE);

        mAdapter = new DoctorOrNurseDetailAdapter(new ArrayList<>(),null);
//        final GridLayoutManager manager = new GridLayoutManager(this, 3);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getItemViewType(position) == DoctorOrNurseDetailAdapter.TYPE_TEAM ? 1 : manager.getSpanCount();
//            }
//        });
//        mRefreshLayout.setEnableRefresh(false);
        if(MyApplication.userInfo.getUserCode().equals(mCode.toUpperCase())){
            mFollowTv.setVisibility(View.GONE);
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(manager);
        mAdapter.addHeaderView(mHead);
        getController().getDoctorDetail(mType, mCode);
    }

    /**
     * 查找控件
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
        mJobTitle = mHead.findViewById(R.id.job_title);
        mDepartName = mHead.findViewById(R.id.depart_name);
        mJobTime = mHead.findViewById(R.id.time);
        mHead.findViewById(R.id.follow_group).setOnClickListener(getController());
    }

    @Override
    public void finishThis() {
        finish();
    }

    /**
     * 处理不同颜色大小文字，返回string
     */
    private SpannableStringBuilder transFormString(int color1, String str1, int color2, String str2) {
        SpannableStringBuilder spannableStringBuilder = new SpanUtils().append(str1).setForegroundColor(color1).setFontSize(13, true).append(str2).setForegroundColor(color2).setFontSize(13, true).create();
        return spannableStringBuilder;
    }

    /**
     * 获取颜色id
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
                mTitle.setText("医生主页");
            } else if (mType == NURSE) {
                mTitle.setText("护士主页");
            }
            getController().getDoctorEvaluates(mCode, 1,mType == DoctorOrNurseDetailActivity.DOCTOR);
            mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getDoctorEvaluates(getCode(), mCurrPage, mType == DOCTOR));
            mName.setText(info.getName());
            mInquiryNum.setText(transFormString(getTextColor(R.color.title_bar_text_color), "问诊量：", getTextColor(R.color.other_login_color), String.valueOf(info.getInquisitionAmount())));
            mGoodsNum.setText(transFormString(getTextColor(R.color.title_bar_text_color), "好评率：", getTextColor(R.color.goods_num_color), String.valueOf(info.getFeedbackRate())));
            mFollow.setText(transFormString(getTextColor(R.color.title_bar_text_color), "关注数：", getTextColor(R.color.attention_num_color), String.valueOf(info.getAttentionAmount())));
            String head = Const.IMAGE_HOST + info.getAvatar();
            GlideUtils.load(mHeadImg, head, 0, 0, false, null);
            if (info.getJobTitle() != null && !"".equals(info.getJobTitle()))
                mJobTitle.setText(info.getJobTitle());
            else
                mJobTitle.setVisibility(View.GONE);
            mDepartName.setText(info.getDeptName());
            mJobTime.setText(info.getSchedu() == null || "".equals(info.getSchedu()) ? "暂无排班信息" : "今日" + info.getSchedu());
            Bitmap image = CodeUtils.createImage(info.getQrcode(), 400, 400, null);
            mQrCode.setImageBitmap(image);
            if (info.getIsAttention() == 1) {
                mFollowTv.setText("已关注");
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
                    DoctorEvaluateItem evaluateItem = (DoctorEvaluateItem) adapter.getData().get(position);
                    getController().doCommentLike(position, evaluateItem.getId(), evaluateItem.getIsLike() == 0 ? 1 : 0);
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
    public void setAttentionSuccess(int isAttention) {
        int attentiongAmount = mDoctorMainBean.getInfo().getAttentionAmount();
        if (isAttention == 1) {
            mFollowTv.setText("已关注");
            ToastUtils.showShort("关注成功");
            attentiongAmount += 1;
        } else {
            mFollowTv.setText("关注");
            ToastUtils.showShort("取消成功");
            attentiongAmount -= 1;
            if (attentiongAmount < 0) {
                attentiongAmount = 0;
            }
        }
        mDoctorMainBean.getInfo().setAttentionAmount(attentiongAmount);
        mFollow.setText(transFormString(getTextColor(R.color.title_bar_text_color), "关注数：", getTextColor(R.color.attention_num_color), String.valueOf(mDoctorMainBean.getInfo().getAttentionAmount())));
        mDoctorMainBean.getInfo().setIsAttention(isAttention);
    }
}