package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription.PrescriptionGetDetailActivity;

import java.util.List;

public class PrescriptionDetailTitleAdapter extends RecyclerView.Adapter<PrescriptionDetailTitleAdapter.VH> {
  //  private Context context;
    private PrescriptionGetDetailActivity context;
    private List<String> mDatas;
    //选中的角标
    private int selectPosition = 0;

    public PrescriptionDetailTitleAdapter(PrescriptionGetDetailActivity context, List<String> data) {
        this.context = context;
        this.mDatas = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_title, viewGroup, false);
        return new PrescriptionDetailTitleAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
            vh.mTvTitle.setText(mDatas.get(i));
//                if(i==0){
//                    vh.mImageDots.setImageResource(R.drawable.bg_dots_green);
//                }
        if (selectPosition == i) {
            vh.mImageDots.setImageResource(R.drawable.bg_dots_green);
           // vh.view.setBackgroundColor(context.getResources().getColor(R.color.xia_green_line));
        } else {
            vh.mImageDots.setImageResource(R.drawable.bg_dots_gray);
           // vh.view.setBackgroundColor(context.getResources().getColor(R.color.mine_interval_bg));
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.mImageDots.setImageResource(R.drawable.bg_dots_green);
             //   vh.view.setBackgroundColor(context.getResources().getColor(R.color.xia_green_line));
                selectPosition = i;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView mImageDots;
        TextView mTvTitle;
        View view;

        public VH(View v) {
            super(v);
            mImageDots = v.findViewById(R.id.image_dots);
            mTvTitle = v.findViewById(R.id.tv_title);
           // view = v.findViewById(R.id.view_line);
        }
    }
}
