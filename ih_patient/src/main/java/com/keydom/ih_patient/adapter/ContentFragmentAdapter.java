package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.ih_patient.fragment.ContentFragment;
import com.keydom.ih_patient.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

public class ContentFragmentAdapter extends RecyclerView.Adapter<ContentFragmentAdapter.VH> {
    private ContentFragment context;
    private List<PrescriptionItemEntity> mDatas = new ArrayList<>();
    private GetDrugAdapter.OnItemClickListener mOnItemClickListener;

    public ContentFragmentAdapter(ContentFragment context) {
        this.context = context;
        //this.mDatas = data;
    }

    @NonNull
    @Override
    public ContentFragmentAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content_fragment, viewGroup, false);
        return new ContentFragmentAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentFragmentAdapter.VH vh, int i) {
        if (!CommUtil.isEmpty(mDatas)) {
            vh.mTvName.setText(i + 1 + "、" + mDatas.get(i).getDrugsName());
            vh.mTvSpec.setText(mDatas.get(i).getSpec() + "/" + mDatas.get(i).getPackUnit());
            vh.mTvQuantity.setText(String.valueOf(mDatas.get(i).getQuantity())+mDatas.get(i).getPackUnit());
            vh.mTvPrice.setText(mDatas.get(i).getPrice()+"元");
            vh.mTvYong.setText("用法：" + mDatas.get(i).getDosage() + "/次");
            vh.mTvKou.setMaxEms(4);
            vh.mTvKou.setText(mDatas.get(i).getInstruction());
            vh.mTvFrequency.setText(mDatas.get(i).getFrequency());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView mTvName, mTvSpec, mTvQuantity, mTvPrice, mTvYong, mTvKou, mTvFrequency;

        public VH(View v) {
            super(v);
            mTvName = v.findViewById(R.id.tv_name);
            mTvSpec = v.findViewById(R.id.tv_spec);
            mTvQuantity = v.findViewById(R.id.tv_quantity);
            mTvPrice = v.findViewById(R.id.tv_price);
            mTvYong = v.findViewById(R.id.tv_yong);
            mTvKou = v.findViewById(R.id.tv_kou);
            mTvFrequency = v.findViewById(R.id.tv_frequency);
        }
    }

    public void setList(List<PrescriptionItemEntity> list) {
        mDatas.clear();
        if (null != list) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
}
