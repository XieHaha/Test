package com.keydom.ih_patient.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;


/**
 * 方法配置适配器
 */
public class FunctionConfigAdapter extends RecyclerView.Adapter<FunctionConfigAdapter.VH> {
    private Activity context;
    private List<IndexFunction> dataList;
    public boolean isEditing = false;
    private String type;
    private int selectedCount=0;
    /**
     * 构造方法
     */
    public FunctionConfigAdapter(Activity context, List<IndexFunction> dataList,String type) {
        this.context = context;
        this.dataList = dataList;
        this.type=type;

    }

    public class VH extends RecyclerView.ViewHolder{
        public TextView funcName;
        public ImageView funcIcon;
        public ImageView funcAddOrDelImg;
        public VH(View v) {
            super(v);
            funcName = (TextView) v.findViewById(R.id.item_func_name);
            funcIcon= (ImageView) v.findViewById(R.id.item_func_icon);
            funcAddOrDelImg=(ImageView)v.findViewById(R.id.function_addordel_img);
        }
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.function_config_item, parent, false);
        return new FunctionConfigAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        holder.funcIcon.setImageResource(dataList.get(position).getFunctionIcon());
        holder.funcName.setText(dataList.get(position).getName());
        if(dataList.get(position).isSelected()){
            holder.funcAddOrDelImg.setImageResource(R.mipmap.function_del);
        }else {
            holder.funcAddOrDelImg.setImageResource(R.mipmap.function_add);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEditing){

                    if(dataList.get(position).isSelected()){
                        dataList.get(position).setSelected(false);
                        selectedCount--;
                        holder.funcAddOrDelImg.setImageResource(R.mipmap.function_add);
                    }else {
                        if(selectedCount<7){
                            selectedCount++;
                            dataList.get(position).setSelected(true);
                            holder.funcAddOrDelImg.setImageResource(R.mipmap.function_del);
                        }else
                            ToastUtil.shortToast(context,"最多可以在首页配置七个菜单");

                    }
                    EventBus.getDefault().post(dataList.get(position));

                }
            }
        });
        if(isEditing){
            holder.funcAddOrDelImg.setVisibility(View.VISIBLE);
        }else {
            holder.funcAddOrDelImg.setVisibility(View.GONE);
        }
    }
    /**
     *  改变状态
     */
    public void ChangeState(boolean isEditing){
        this.isEditing=isEditing;
        selectedCount=0;
        for (int i = 0; i <dataList.size() ; i++) {
            if(dataList.get(i).isSelected())
                selectedCount++;
        }
        notifyDataSetChanged();
    }
    /**
     * 对拖拽的元素进行排序
     */
    public void itemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void refreshSelectedCount(){
        selectedCount=0;
        for (int i = 0; i <dataList.size() ; i++) {
            if(dataList.get(i).isSelected())
                selectedCount++;
        }
    }
}
