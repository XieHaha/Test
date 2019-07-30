package com.keydom.ih_patient.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.ChooseDoctorActivity;
import com.keydom.ih_patient.activity.order_doctor_register.OrderDoctorRegisterActivity;
import com.keydom.ih_patient.utils.DepartmentDataHelper;
import com.keydom.ih_patient.view.FlowLayout;
import com.orhanobut.logger.Logger;

import java.util.List;
/**
 * 医生挂号订单适配器
 */
public class OrderDoctorRegisterAdapter extends RecyclerView.Adapter {
    private List<Object> dataList;
    private OrderDoctorRegisterActivity context;
    private int ITEM_HEADER = 1, ITEM_BODY = 2;
    /**
     * 构造方法
     */
    public OrderDoctorRegisterAdapter(OrderDoctorRegisterActivity context,List<Object>  dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.doctor_register_header_item, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_BODY) {
            view = LayoutInflater.from(context).inflate(R.layout.doctor_register_body_item, parent, false);
            return new BodyViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            DepartmentDataHelper.HeaderInfo headerInfo= (DepartmentDataHelper.HeaderInfo) dataList.get(position);
            headerViewHolder.item_name_tv.setText(headerInfo.getFistDepartmentName());
            headerViewHolder.item_name_tv.setTextColor(Color.parseColor(headerInfo.getFontColor()));
            if(headerInfo.getImage()!=null){
                Glide.with(context).load(Const.IMAGE_HOST+headerInfo.getImage()).into(headerViewHolder.item_head_img);
            }
        }else if(holder instanceof BodyViewHolder){
            final BodyViewHolder bodyViewHolder= (BodyViewHolder) holder;
            final DepartmentDataHelper.BodyInfoList bodyInfo= (DepartmentDataHelper.BodyInfoList) dataList.get(position);
            bodyViewHolder.flowLayout.removeAllViews();
            int size=0;
            final boolean[] isOpen = {false};
            if(bodyInfo.getBodyInfoList().size()<=6){
                size=bodyInfo.getBodyInfoList().size();
            }else {
                size=6;
            }
            for (int i = 0; i < size; i++) {
                TextView textView=new TextView(context);
                textView.setText(bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName());
                textView.setTextColor(context.getResources().getColor(R.color.tab_nol_color));
                textView.setWidth(context.getResources().getDimensionPixelSize(R.dimen.dp_66));
                textView.setLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                final long departmentId=bodyInfo.getBodyInfoList().get(i).getId();
                final String departmentName=bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName();
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChooseDoctorActivity.start(context,context.getSelectedAreaId(),context.getSelectedAreaName(),departmentId,departmentName,context.getDepartmentList());
                    }
                });
                bodyViewHolder.flowLayout.addView(textView);
            }
            if(bodyInfo.isShowMore()){
                bodyViewHolder.item_show_more_tv.setVisibility(View.VISIBLE);
                bodyViewHolder.item_show_more_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!isOpen[0]){
                            for (int i = 6; i <bodyInfo.getBodyInfoList().size() ; i++) {
                                TextView textView=new TextView(context);
                                textView.setText(bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName());
                                textView.setTextColor(context.getResources().getColor(R.color.tab_nol_color));
                                textView.setWidth(context.getResources().getDimensionPixelSize(R.dimen.dp_66));
                                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                                final long departmentId=bodyInfo.getBodyInfoList().get(i).getId();
                                final String departmentName=bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName();
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ChooseDoctorActivity.start(context,context.getSelectedAreaId(),context.getSelectedAreaName(),departmentId,departmentName, context.getDepartmentList());
                                    }
                                });
                                bodyViewHolder.flowLayout.addView(textView);
                            }
                            isOpen[0] =true;
                            bodyViewHolder.item_show_more_tv.setText("折叠");
                        }else {
                            bodyViewHolder.flowLayout.removeAllViews();
                            for (int i = 0; i <6; i++){
                                TextView textView=new TextView(context);
                                textView.setText(bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName());
                                textView.setTextColor(context.getResources().getColor(R.color.tab_nol_color));
                                textView.setWidth(context.getResources().getDimensionPixelSize(R.dimen.dp_66));
                                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                                final long departmentId=bodyInfo.getBodyInfoList().get(i).getId();
                                final String departmentName=bodyInfo.getBodyInfoList().get(i).getSecondDepartmentName();
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Logger.e("departmentId=="+departmentId);
                                        ChooseDoctorActivity.start(context,context.getSelectedAreaId(),context.getSelectedAreaName(),departmentId,departmentName, context.getDepartmentList());
                                    }
                                });
                                bodyViewHolder.flowLayout.addView(textView);
                            }
                            isOpen[0] =false;
                            bodyViewHolder.item_show_more_tv.setText("展开更多");
                        }
                    }
                });
            }else {
                bodyViewHolder.item_show_more_tv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof DepartmentDataHelper.HeaderInfo) {
            return ITEM_HEADER;
        } else if (dataList.get(position) instanceof DepartmentDataHelper.BodyInfoList) {
            return ITEM_BODY;
        }
        return ITEM_BODY;

    }

    static class BodyViewHolder extends RecyclerView.ViewHolder {
        private FlowLayout flowLayout;
        private TextView item_show_more_tv;
        BodyViewHolder(View view) {
            super(view);
            flowLayout=view.findViewById(R.id.flowLayout);
            item_show_more_tv=view.findViewById(R.id.item_show_more_tv);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView item_name_tv;
        private CircleImageView item_head_img;
        HeaderViewHolder(View view) {
            super(view);
            item_head_img=view.findViewById(R.id.item_head_img);
            item_name_tv=view.findViewById(R.id.item_name_tv);
        }
    }

}
