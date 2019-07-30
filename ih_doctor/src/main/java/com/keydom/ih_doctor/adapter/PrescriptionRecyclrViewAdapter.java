package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.prescription_check.DiagnosePrescriptionActivity;
import com.keydom.ih_doctor.activity.prescription_check.PrescriptionActivity;
import com.keydom.ih_doctor.bean.PrescriptionBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.ServiceConst;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：处方列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class PrescriptionRecyclrViewAdapter extends BaseEmptyAdapter<PrescriptionBean> {


    /**
     * 和APPLICATION中的roleID一起判断显示的item类型
     */
    private TypeEnum type;

    public PrescriptionRecyclrViewAdapter(Context context, TypeEnum type, List<PrescriptionBean> data) {
        super(data, context);
        this.type = type;

    }


    private void setCommon(PrescriptionBean item, ViewHolder holder) {
        holder.typePoint.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.point_green));
        holder.typeName.setText(item.getCate() == 1 ? "普通" : "儿科");
        holder.timeTv.setText(item.getTime());

        holder.userNameTip.setText("患者:");
        holder.userName.setText(item.getName() + "、" + CommonUtils.getSex(item.getSex()) + "、" + item.getAge() + "岁");
        holder.userDiagnoseTip.setText("诊断:");
        holder.userDiagnose.setText(item.getDiagnosis());

        holder.doctorNameTip.setText("医师");
        holder.doctorName.setText(item.getDoctorName() + "、" + item.getDoctorDept());
        holder.distributionTip.setText("配送:");
        holder.distribution.setText(item.getDelivery());

        holder.checkIdeaTip.setText("审核意见:");
        holder.checkIdea.setText(item.getAuditOpinion());

        if (item.getState() == 0) {
            holder.checkResTagIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.prescription_return));
            holder.checkResTag.setText("已退回");
            holder.checkResTag.setTextColor(mContext.getResources().getColor(R.color.font_red));
        } else if (item.getState() == 1) {
            holder.checkResTagIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.prescription_pass));
            holder.checkResTag.setText("已通过");
            holder.checkResTag.setTextColor(mContext.getResources().getColor(R.color.font_green));
        }

    }

    private void setData(final PrescriptionBean item, ViewHolder holder) {
        setCommon(item, holder);
        if (SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR) {
            switch (type) {
                case CHECK_NOT_FINISH:
//                    holder.twoLin.setVisibility(View.GONE);
                    holder.doctorNameRl.setVisibility(View.GONE);
                    holder.distributionRl.setVisibility(View.GONE);
                    holder.userDiagnose.setLines(2);
                    holder.threeLin.setVisibility(View.GONE);
                    holder.fourLin.setVisibility(View.GONE);
                    break;
                case CHECK_RETURN:
                    holder.doctorNameTip.setText("药师:");
                    holder.doctorName.setText(item.getAuditer());
                    holder.distributionTip.setVisibility(View.GONE);
                    holder.userDiagnose.setLines(2);
                    holder.distribution.setVisibility(View.GONE);
                    holder.checkUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                DiagnosePrescriptionActivity.startUpdate(mContext, item.getId());
                            } else {
                                showNotAccessDialog();
                            }

                        }
                    });
                    break;
                case CHECK_PASS:
                    holder.distributionRl.setVisibility(View.GONE);
                    holder.userDiagnose.setLines(2);
                    holder.threeLin.setVisibility(View.GONE);
                    holder.fourLin.setVisibility(View.GONE);
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type != TypeEnum.CHECK_NOT_FINISH) {
                        PrescriptionActivity.startCommon(mContext, item.getId());
                    } else {
                        PrescriptionActivity.startWithAction(mContext, item.getId());
                    }

                }
            });
        } else {
            switch (type) {
                case CHECK_NOT_FINISH:
                    holder.distributionTip.setVisibility(View.GONE);
                    holder.distribution.setVisibility(View.GONE);
                    holder.threeLin.setVisibility(View.GONE);
                    holder.fourLin.setVisibility(View.GONE);
                    break;
                case CHECK_FINISH:
                    holder.distributionTip.setVisibility(View.GONE);
                    holder.distribution.setVisibility(View.GONE);
                    holder.threeLin.setVisibility(View.GONE);
                    holder.checkUpdate.setVisibility(View.GONE);
                    break;
                case CHECK_SEND:
                    holder.threeLin.setVisibility(View.GONE);
                    holder.fourLin.setVisibility(View.GONE);
                    break;
                case CHECK_TIME_OUT:
                    holder.distributionTip.setVisibility(View.GONE);
                    holder.distribution.setVisibility(View.GONE);
                    holder.threeLin.setVisibility(View.GONE);
                    holder.fourLin.setVisibility(View.GONE);
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type != TypeEnum.CHECK_NOT_FINISH) {
                        PrescriptionActivity.startCommon(mContext, item.getId());
                    } else {
                        PrescriptionActivity.startWithAction(mContext, item.getId());
                    }
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.prescription_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PrescriptionRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout threeLin;
        public RelativeLayout distributionRl, doctorNameRl;
        public RelativeLayout fourLin;
        public ImageView typePoint, checkResTagIcon;
        public TextView timeTv, userNameTip, userName, userDiagnoseTip,
                userDiagnose, doctorNameTip, doctorName, distributionTip,
                distribution, checkIdeaTip, checkIdea, checkUpdate, checkResTag, typeName;


        public ViewHolder(View itemView) {
            super(itemView);
            typePoint = (ImageView) itemView.findViewById(R.id.type_point);
            checkResTagIcon = (ImageView) itemView.findViewById(R.id.check_res_tag_icon);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);
            userNameTip = (TextView) itemView.findViewById(R.id.user_name_tip);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userDiagnoseTip = (TextView) itemView.findViewById(R.id.user_diagnose_tip);
            userDiagnose = (TextView) itemView.findViewById(R.id.user_diagnose);
            doctorNameTip = (TextView) itemView.findViewById(R.id.doctor_name_tip);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            distributionTip = (TextView) itemView.findViewById(R.id.distribution_tip);
            distribution = (TextView) itemView.findViewById(R.id.distribution);
            checkIdeaTip = (TextView) itemView.findViewById(R.id.check_idea_tip);
            checkIdea = (TextView) itemView.findViewById(R.id.check_idea);
            checkUpdate = (TextView) itemView.findViewById(R.id.check_update);
            checkResTag = (TextView) itemView.findViewById(R.id.check_res_tag);
            typeName = (TextView) itemView.findViewById(R.id.type_name);
            doctorNameRl = (RelativeLayout) itemView.findViewById(R.id.doctor_name_rl);
            distributionRl = (RelativeLayout) itemView.findViewById(R.id.distribution_rl);
            threeLin = (LinearLayout) itemView.findViewById(R.id.three_ll);
            fourLin = (RelativeLayout) itemView.findViewById(R.id.four_rl);


        }

        public void bind(final int position) {
            setData(mDatas.get(position), ViewHolder.this);
        }

    }


    public void showNotAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("未开通服务");
        builder.setMessage("您暂未开通该服务，无法使用这个功能！");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}
