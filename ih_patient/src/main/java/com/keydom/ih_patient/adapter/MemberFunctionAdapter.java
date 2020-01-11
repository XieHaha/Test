package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.VIPCardInfoListItem;

import java.util.List;

/**
 * 方法角标适配器
 */
public class MemberFunctionAdapter extends RecyclerView.Adapter<MemberFunctionAdapter.VH> {
    private Context mContext;
    private List<VIPCardInfoListItem> mDatas;


    /**
     * 构造方法
     */
    public MemberFunctionAdapter(Context mContext, List<VIPCardInfoListItem> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView funcName;
        public ImageView funcIcon;
        public View redPointView;

        public VH(View v) {
            super(v);
            funcName = (TextView) v.findViewById(R.id.item_func_name);
            funcIcon = (ImageView) v.findViewById(R.id.item_func_icon);
            redPointView = v.findViewById(R.id.item_redpoint_view);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_function_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH vh, final int position) {

        VIPCardInfoListItem data = mDatas.get(position);

        GlideUtils.loadWithBorder(vh.funcIcon, Const.IMAGE_HOST + data.getImgFile());

        vh.funcName.setText(data.getTitle());


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
