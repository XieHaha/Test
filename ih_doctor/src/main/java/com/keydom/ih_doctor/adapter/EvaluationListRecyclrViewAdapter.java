package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.EvaluationBean;
import com.keydom.ih_doctor.view.TagView;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：评价列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class EvaluationListRecyclrViewAdapter extends BaseEmptyAdapter<EvaluationBean> {


    public EvaluationListRecyclrViewAdapter(List<EvaluationBean> mDatas, Context context) {
        super(mDatas, context);
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.evaluation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EvaluationListRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView evaluationValue;
        public RatingBar starRb;
        public TagView evaluationTagView;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            evaluationValue = itemView.findViewById(R.id.evaluation_value);
            starRb = itemView.findViewById(R.id.star_rb);
            evaluationTagView = itemView.findViewById(R.id.evaluation_tag_view);
        }

        public void bind(final int position) {
            EvaluationBean bean = mDatas.get(position);
            userName.setText(bean.getName());
            starRb.setRating(bean.getGrade());
            String evaluation = "";
            switch (bean.getGrade()) {
                case 1:
                    evaluation = "不满意，急需改善";
                    break;
                case 2:
                    evaluation = "不太满意，需改善";
                    break;
                case 3:
                    evaluation = "一般，建议改善";
                    break;
                case 4:
                    evaluation = "比较满意，仍可改善";
                    break;
                case 5:
                    evaluation = "很满意，继续保持";
                    break;
            }
            evaluationValue.setText(evaluation);
            evaluationTagView.removeAllViews();
            if (bean.getLabels() != null && bean.getLabels().size() > 0) {
                for (int i = 0; i < bean.getLabels().size(); i++) {
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = 5;
                    lp.rightMargin = 5;
                    lp.topMargin = 5;
                    lp.bottomMargin = 5;
                    TextView view = new TextView(mContext);
                    view.setText(bean.getLabels().get(i));
                    view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelOffset(R.dimen.font_size_primary));
                    view.setTextColor(mContext.getResources().getColor(R.color.fontColorPrimary));
                    view.setBackgroundDrawable(mContext.getResources().getDrawable(R.mipmap.article_tag_bg));
                    view.setGravity(Gravity.CENTER);
                    evaluationTagView.addView(view, lp);
                }

            }

        }

    }
}
