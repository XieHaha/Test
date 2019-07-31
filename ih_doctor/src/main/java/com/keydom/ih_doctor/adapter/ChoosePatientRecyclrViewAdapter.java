package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.patient_manage.PatientDatumActivity;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ColorUtils;
import com.keydom.ih_doctor.view.ContactTagView;
import com.keydom.ih_doctor.view.TagView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：选择患者列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ChoosePatientRecyclrViewAdapter extends BaseEmptyAdapter<ImPatientInfo> {


    public ChoosePatientRecyclrViewAdapter(Context context, List<ImPatientInfo> data) {
        super(data, context);

    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patient_contact_item, parent, false);
        return new ChoosePatientRecyclrViewAdapter.ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChoosePatientRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView friendIconCiv;
        public TextView friendNameTv, friendAgeTv;
        public ContactTagView friendTag;
        public ImageView selectIv, emphasesAttentionIv, friendSexIv;


        public ViewHolder(View itemView) {
            super(itemView);
            friendIconCiv = itemView.findViewById(R.id.friend_icon_civ);
            friendNameTv = itemView.findViewById(R.id.friend_name_tv);
            friendAgeTv = itemView.findViewById(R.id.friend_age_tv);
            friendTag = itemView.findViewById(R.id.friend_tag);
            selectIv = itemView.findViewById(R.id.chat_iv);
            friendSexIv = itemView.findViewById(R.id.friend_sex_iv);
            emphasesAttentionIv = itemView.findViewById(R.id.emphases_attention_iv);
        }


        public void bind(final int position) {
            final ImPatientInfo bean = mDatas.get(position);
            GlideUtils.load(friendIconCiv, com.keydom.ih_doctor.constant.Const.IMAGE_HOST + bean.getUserImage(), 0, 0, false, null);
            friendNameTv.setText(bean.getUserName());
            friendAgeTv.setText(bean.getAge() + "岁");
            if (bean.isSelect()) {
                selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.check_box_selected));
            } else {
                selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.check_box_un_selected));
            }
            String tagStr = "";
            friendTag.removeAllViews();
            if (bean.getLabelList() != null && bean.getLabelList().size() > 0) {
                for (int i = 0; i < bean.getLabelList().size(); i++) {
                    addTag(friendTag, bean.getLabelList().get(i));
                }
            }
            if ("0".equals(bean.getFocusOn())) {
                emphasesAttentionIv.setVisibility(View.GONE);
            } else {
                emphasesAttentionIv.setVisibility(View.VISIBLE);
            }
            if ("0".equals(bean.getSex())) {
                friendSexIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.patient_cicle_blue));
            } else {
                friendSexIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.patient_cicle_green));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    bean.setSelect(!bean.isSelect());
                    if (bean.isSelect()) {
                        selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.check_box_selected));
                        EventBus.getDefault().post(new MessageEvent.Buidler().setData(bean).setType(EventType.SELECT_PATIENT_RESULT).build());
                    } else {
                        selectIv.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.check_box_un_selected));
                        EventBus.getDefault().post(new MessageEvent.Buidler().setData(bean).setType(EventType.UN_SELECT_PATIENT_RESULT).build());
                    }
                }
            });
            friendIconCiv.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    PatientDatumActivity.start(mContext, String.valueOf(bean.getId()));

                }
            });
        }


        private void addTag(ContactTagView mTagView, String tag) {
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            lp.topMargin = 5;
            lp.bottomMargin = 5;
            TextView view = new TextView(mContext);
            view.setText(tag);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.font_size_bottom_bar));
            view.setTextColor(ColorUtils.getCustomizedColor(mContext));
            view.setGravity(Gravity.CENTER);
            mTagView.addView(view, lp);
        }
    }
}
