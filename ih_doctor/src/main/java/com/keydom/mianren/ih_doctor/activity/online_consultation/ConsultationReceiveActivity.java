package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.im.widget.AutoGridView;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationAdviceAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationDoctorAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ImageAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationReceiveController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationReceiveView;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDoctorBean;
import com.keydom.mianren.ih_doctor.bean.RecordVideoInfoBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.keydom.ih_common.bean.ConsultationStatus.CONSULTATION_NONE;
import static com.keydom.ih_common.bean.ConsultationStatus.CONSULTATION_RECEIVED;
import static com.keydom.ih_common.bean.ConsultationStatus.CONSULTATION_WAIT;
import static com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationRoomActivity.REQUEST_CODE_END;

/**
 * @date 20/3/27 14:38
 * @des ????????????
 */
public class ConsultationReceiveActivity extends BaseControllerActivity<ConsultationReceiveController> implements ConsultationReceiveView {
    @BindView(R.id.consultation_receive_header_iv)
    ImageView consultationReceiveHeaderIv;
    @BindView(R.id.consultation_receive_patient_name_tv)
    TextView consultationReceivePatientNameTv;
    @BindView(R.id.consultation_receive_patient_sex_tv)
    TextView consultationReceivePatientSexTv;
    @BindView(R.id.consultation_receive_patient_age_tv)
    TextView consultationReceivePatientAgeTv;
    @BindView(R.id.consultation_receive_level_tv)
    TextView consultationReceiveLevelTv;
    @BindView(R.id.consultation_receive_card_tv)
    TextView consultationReceiveCardTv;
    @BindView(R.id.consultation_receive_time_text_tv)
    TextView consultationReceiveTimeTextTv;
    @BindView(R.id.consultation_receive_visit_time_tv)
    TextView consultationReceiveVisitTimeTv;
    @BindView(R.id.consultation_receive_apply_doctor_header_iv)
    ImageView consultationReceiveApplyDoctorHeaderIv;
    @BindView(R.id.consultation_receive_apply_doctor_name_tv)
    TextView consultationReceiveApplyDoctorNameTv;
    @BindView(R.id.consultation_receive_consultation_doctor_grid_view)
    AutoGridView consultationReceiveConsultationDoctorGridView;
    @BindView(R.id.consultation_receive_consultation_date_tv)
    TextView consultationReceiveConsultationDateTv;
    @BindView(R.id.consultation_receive_purpose_item)
    DiagnosePrescriptionItemView consultationReceivePurposeItem;
    @BindView(R.id.consultation_receive_summary_item)
    DiagnosePrescriptionItemView consultationReceiveSummaryItem;
    @BindView(R.id.consultation_receive_condition_image_grid)
    GridViewForScrollView consultationReceiveConditionImageGrid;
    @BindView(R.id.consultation_receive_commit_tv)
    TextView consultationReceiveCommitTv;
    @BindView(R.id.consultation_receive_commit_layout)
    LinearLayout consultationReceiveCommitLayout;
    @BindView(R.id.consultation_receive_video_layout)
    LinearLayout consultationReceiveVideoLayout;
    @BindView(R.id.consultation_receive_advice_layout)
    RelativeLayout consultationReceiveAdviceLayout;
    @BindView(R.id.consultation_receive_recycler_view)
    RecyclerView consultationReceiveRecyclerView;

    /**
     * ????????????
     */
    private ConsultationDoctorAdapter doctorAdapter;
    /**
     * ?????????????????????
     */
    private ConsultationAdviceAdapter adviceAdapter;

    private List<ConsultationAdviceBean> adviceBeans;

    /**
     * ??????????????????
     */
    private ConsultationDetailBean detailBean;
    /**
     * ??????????????????
     */
    private AnimationDrawable animationDrawable;

    private String orderId, recordId;

    /**
     * ????????????
     */
    private int receiveStatus;
    /**
     * ??????????????????
     */
    private int orderStatus;

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ConsultationReceiveActivity.class);
        intent.putExtra(Const.ORDER_ID, id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_receive;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_consultation_receive));
        orderId = getIntent().getStringExtra(Const.ORDER_ID);

        animationDrawable = (AnimationDrawable) ContextCompat.getDrawable(this,
                R.drawable.im_anim_voice_sent);

        consultationReceiveCommitTv.setOnClickListener(getController());
        doctorAdapter = new ConsultationDoctorAdapter(this);
        consultationReceiveConsultationDoctorGridView.setAdapter(doctorAdapter);
        //????????????
        consultationReceiveRecyclerView.setNestedScrollingEnabled(false);
        consultationReceiveRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adviceAdapter = new ConsultationAdviceAdapter(this, adviceBeans, animationDrawable);
        consultationReceiveRecyclerView.setAdapter(adviceAdapter);

        pageLoading();
        getController().getConsultationOrderDetail(orderId);

        setReloadListener((v, status) -> {
            pageLoading();
            getController().getConsultationOrderDetail(orderId);
        });
    }

    /**
     * ??????????????????
     */
    private void bindData() {
        if (detailBean != null) {
            recordId = detailBean.getRecordId();
            //????????????????????????
            getController().consultationOrderAdviceList(recordId);
            orderStatus = detailBean.getStatus();
            if (orderStatus == CONSULTATION_WAIT) {
                consultationReceiveTimeTextTv.setText("????????????");
                consultationReceiveVisitTimeTv.setText(DateUtils.timestampToString(detailBean.getApplyTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                receiveStatus = detailBean.getDoctorStatus();
                switch (receiveStatus) {
                    case CONSULTATION_NONE:
                        consultationReceiveCommitTv.setText("?????????");
                        break;
                    case CONSULTATION_WAIT:
                        consultationReceiveCommitTv.setText(R.string.txt_receive);
                        break;
                    case CONSULTATION_RECEIVED:
                        consultationReceiveCommitTv.setText("?????????");
                        break;
                    default:
                        break;
                }
            } else {
                consultationReceiveCommitLayout.setVisibility(View.GONE);
                consultationReceiveAdviceLayout.setVisibility(View.VISIBLE);
                consultationReceiveTimeTextTv.setText("????????????");
                consultationReceiveVisitTimeTv.setText(DateUtils.timestampToString(detailBean.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
            }
            //??????????????????
            doctorAdapter.setData(detailBean.getMdtDoctors());
            //??????????????????
            consultationReceivePatientNameTv.setText(detailBean.getPatientName());
            consultationReceivePatientSexTv.setText(CommonUtils.getPatientSex(detailBean.getPatientGender()));
            consultationReceivePatientAgeTv.setText(detailBean.getPatientAge());
            consultationReceiveCardTv.setText(String.format(getString(R.string.txt_visit_card),
                    detailBean.getEleCardNumber()));
            GlideUtils.load(consultationReceiveHeaderIv,
                    BaseFileUtils.getHeaderUrl(detailBean.getRegisterUserImage()), 0,
                    R.mipmap.im_default_head_image, true, null);
            if (detailBean.getLevel() == 0) {
                consultationReceiveLevelTv.setBackgroundResource(R.drawable.corner5_ff3939_bg);
                consultationReceiveLevelTv.setText("??????");
            } else {
                consultationReceiveLevelTv.setBackgroundResource(R.drawable.corner5_fbd54e_bg);
                consultationReceiveLevelTv.setText("??????");
            }

            //??????????????????
            ConsultationDoctorBean bean = detailBean.getApplyDoctor();
            if (bean != null) {
                GlideUtils.load(consultationReceiveApplyDoctorHeaderIv,
                        BaseFileUtils.getHeaderUrl(bean.getDoctorImage()), 0,
                        R.mipmap.im_default_head_image, true, null);
                consultationReceiveApplyDoctorNameTv.setText(bean.getName());
            }
            //????????????
            if (!TextUtils.isEmpty(detailBean.getMdtTime())) {
                Date date = new Date(Long.valueOf(detailBean.getMdtTime()));
                consultationReceiveConsultationDateTv.setText(String.format(getString(R.string.txt_three_value_space),
                        DateUtils.dateToString(date, DateUtils.MM_DD_CH),
                        DateUtils.getWeekString(date),
                        DateUtils.dateToString(date, DateUtils.HH_MM)));
            }

            consultationReceivePurposeItem.setText(detailBean.getReasonAndAim());
            consultationReceiveSummaryItem.setText(detailBean.getIllnessAbstract());
            //????????????
            //????????????????????????????????????????????????????????????
            ImageAdapter imageAdapter = new ImageAdapter(getContext(),
                    detailBean.getMedicalHistoryImg(), false);
            consultationReceiveConditionImageGrid.setAdapter(imageAdapter);

            //??????????????????
            ArrayList<RecordVideoInfoBean> videoInfoBeans = detailBean.getRecordVideoInfo();
            if (videoInfoBeans != null && videoInfoBeans.size() > 0) {
                consultationReceiveVideoLayout.removeAllViews();
                addVoiceView(videoInfoBeans);
            }
        }
    }

    private RecordVideoInfoBean curVoiceBean;

    private void addVoiceView(ArrayList<RecordVideoInfoBean> videoInfoBeans) {
        for (RecordVideoInfoBean bean : videoInfoBeans) {
            View view = getLayoutInflater().inflate(R.layout.item_consultation_voice, null);

            ImageView imageStart = view.findViewById(R.id.consultation_voice_img);
            TextView voiceTime = view.findViewById(R.id.consultation_voice_time);
            TextView deleteTv = view.findViewById(R.id.consultation_voice_delete);
            deleteTv.setVisibility(View.GONE);
            voiceTime.setText(DateUtils.getMinute(bean.getDuration()));

            view.setOnClickListener(v -> {
                if (AudioPlayerManager.getInstance().isPlaying()) {
                    AudioPlayerManager.getInstance().stopPlay();
                    if (curVoiceBean != null && TextUtils.equals(curVoiceBean.getUrl(),
                            bean.getUrl())) {
                        //????????????
                        setVoiceAnim(imageStart, false);
                    } else {
                        //???????????????
                        AudioPlayerManager.getInstance().setDataSource(BaseFileUtils.getHeaderUrl(bean.getUrl()))
                                .setOnPlayListener(new OnVoicePlayListener(imageStart));
                        AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC);
                    }
                } else {
                    AudioPlayerManager.getInstance().setDataSource(BaseFileUtils.getHeaderUrl(bean.getUrl()))
                            .setOnPlayListener(new OnVoicePlayListener(imageStart));
                    AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC);
                }
                curVoiceBean = bean;
            });
            consultationReceiveVideoLayout.addView(view);
        }
    }

    private class OnVoicePlayListener implements OnPlayListener {
        private ImageView imageView;

        public OnVoicePlayListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onPrepared() {

        }

        @Override
        public void onCompletion() {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onInterrupt() {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onError(String error) {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onPlaying(long curPosition) {
            setVoiceAnim(imageView, true);
        }
    }

    private void setVoiceAnim(ImageView imageView, boolean playing) {
        if (playing) {
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
        } else {
            imageView.setImageResource(R.mipmap.im_voice_sent);
            animationDrawable.stop();
        }
    }

    @Override
    public int getReceiveStatus() {
        return receiveStatus;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public ConsultationDetailBean getDetailBean() {
        return detailBean;
    }

    @Override
    public void requestDetailSuccess(ConsultationDetailBean data) {
        pageLoadingSuccess();
        detailBean = data;
        bindData();
    }

    @Override
    public void requestDetailFailed(String msg) {
        pageLoadingFail();
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public void requestAdviceSuccess(List<ConsultationAdviceBean> data) {
        adviceBeans = data;
        adviceAdapter.setNewData(adviceBeans);
    }

    @Override
    public void requestAdviceFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_END) {
            getController().getConsultationOrderDetail(orderId);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
