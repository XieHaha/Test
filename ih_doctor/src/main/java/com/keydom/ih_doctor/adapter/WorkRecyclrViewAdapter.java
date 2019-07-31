package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：工作台适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class WorkRecyclrViewAdapter extends RecyclerView.Adapter<WorkRecyclrViewAdapter.ViewHolder>{


        private Context context;
        private List<String> data;

        public WorkRecyclrViewAdapter(Context context,List<String> data){
            this.context = context;
            this.data = data;

        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.work_list_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.listTitle.setText("测试");
            holder.listDec.setText("测试2");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    ToastUtil.shortToast(context,"点击了第"+position);
                }
            });

         }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView listIcon = null;
            public TextView listTitle = null;
            public TextView listDec = null;

            public ViewHolder(View itemView) {
                super(itemView);

            }
        }
}
