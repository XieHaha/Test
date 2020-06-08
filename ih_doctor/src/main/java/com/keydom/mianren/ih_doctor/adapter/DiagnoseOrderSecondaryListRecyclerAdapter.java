package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.MRadioButton;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.m_interface.OnItemChangeListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.ArrayList;
import java.util.List;

public class DiagnoseOrderSecondaryListRecyclerAdapter extends SecondaryListAdapter<DiagnoseOrderSecondaryListRecyclerAdapter.GroupItemViewHolder, DiagnoseOrderSecondaryListRecyclerAdapter.SubItemViewHolder> {
    private Context context;
    private boolean delete;

    private List<DataTree<CheckOutGroupBean, CheckOutGroupBean>> dts = new ArrayList<>();

    public DiagnoseOrderSecondaryListRecyclerAdapter(Context context, boolean delete) {
        this.context = context;
        this.delete = delete;
    }

    public void setData(List datas) {
        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {

        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnose_test_parent_item, parent, false);

        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {

        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnose_test_child_item, parent, false);

        return new SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
        CheckOutGroupBean bean = dts.get(groupItemIndex).getGroupItem();
        ((GroupItemViewHolder) holder).itemName.setText(bean.getName());
        if (delete) {
            ((GroupItemViewHolder) holder).testItemFee.setVisibility(View.VISIBLE);
            ((GroupItemViewHolder) holder).deleteBtn.setVisibility(View.VISIBLE);
            ((GroupItemViewHolder) holder).deptName.setVisibility(View.VISIBLE);
            ((GroupItemViewHolder) holder).deptName.setVisibility(View.VISIBLE);
            ((GroupItemViewHolder) holder).allChooseRb.setVisibility(View.GONE);
        } else {
            ((GroupItemViewHolder) holder).testItemFee.setVisibility(View.GONE);
            ((GroupItemViewHolder) holder).deleteBtn.setVisibility(View.GONE);
            ((GroupItemViewHolder) holder).deptName.setVisibility(View.GONE);
        }
        ((GroupItemViewHolder) holder).deptName.setText(bean.getDeptName());
        ((GroupItemViewHolder) holder).testItemFee.setText(String.valueOf(bean.getTotalFee()));
        ((GroupItemViewHolder) holder).deleteBtn.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {

                new GeneralDialog(context, "确定删除该项目?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        dts.remove(groupItemIndex);
                        notifyDataSetChanged();
                        if (onItemChangeListener != null) {
                            onItemChangeListener.removeItem(groupItemIndex);
                        }
                    }
                }).show();

            }
        });

        if (onItemChangeListener != null) {
            onItemChangeListener.changeItem(groupItemIndex);
        }
        //        ((GroupItemViewHolder) holder).allChooseRb.setFinishEvent(true);
        ((GroupItemViewHolder) holder).allChooseRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CheckOutGroupBean> subBeanList = dts.get(groupItemIndex).getSubItems();
                for (int i = 0; i < subBeanList.size(); i++) {
                    subBeanList.get(i).setSelect(true);
                }
                bean.setSelect(false);
                if (bean.getItems() != null) {
                    for (int i = 0; i < bean.getItems().size(); i++) {
                        if (bean.getItems().get(i).isSelect()) {
                            bean.setSelect(true);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });


    }

    private OnItemChangeListener onItemChangeListener;

    public void setOnItemChangeListener(OnItemChangeListener listener) {
        this.onItemChangeListener = listener;
    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex,
                                        int subItemIndex) {
        CheckOutGroupBean bean = dts.get(groupItemIndex).getGroupItem();
        CheckOutGroupBean subBean = dts.get(groupItemIndex).getSubItems().get(subItemIndex);
        ((SubItemViewHolder) holder).tvSub.setText(TextUtils.isEmpty(subBean.getItemName()) ?
                subBean.getInsCheckItemName() : subBean.getItemName());
        ((SubItemViewHolder) holder).mRb.setChecked(subBean.isSelect());
        if (delete) {
            ((SubItemViewHolder) holder).mRb.setPadding(CommonUtils.px2dip(context,
                    context.getResources().getDimension(R.dimen.dp_30)), 0, 0, 0);
        }
        if (subBean.isSelect()) {
            ((SubItemViewHolder) holder).itemView.setBackgroundColor(context.getResources().getColor(R.color.refreshBg));
        } else {
            ((SubItemViewHolder) holder).itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {
        Drawable rightImg;
        if (isExpand) {
            rightImg = context.getResources().getDrawable(R.mipmap.arrow_bottom_grey);
        } else {
            rightImg = context.getResources().getDrawable(R.mipmap.arrow_top);
        }
        rightImg.setBounds(0, 0, rightImg.getMinimumWidth(), rightImg.getMinimumHeight());
        holder.rightImg.setCompoundDrawables(null, null, rightImg, null);


    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

        CheckOutGroupBean bean = dts.get(groupItemIndex).getGroupItem();
        CheckOutGroupBean subBean = dts.get(groupItemIndex).getSubItems().get(subItemIndex);
        subBean.setSelect(!subBean.isSelect());
        if (bean.getItems() != null) {
            for (int i = 0; i < bean.getItems().size(); i++) {
                if (bean.getItems().get(i).isSelect()) {
                    bean.setSelect(true);
                }
            }
        }
        if (onItemChangeListener != null) {
            onItemChangeListener.changeItem(groupItemIndex);
        }
        notifyDataSetChanged();
    }

    static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, deptName, testItemFee, rightImg;
        ImageButton deleteBtn;
        TextView allChooseRb;

        GroupItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.inspact_item_name);
            deptName = itemView.findViewById(R.id.dept_name);
            testItemFee = itemView.findViewById(R.id.test_item_fee);
            deleteBtn = itemView.findViewById(R.id.sub_item_delete_btn);
            rightImg = itemView.findViewById(R.id.right_img);
            allChooseRb = itemView.findViewById(R.id.all_choose_rb);
        }
    }

    static class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSub;
        MRadioButton mRb;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvSub = itemView.findViewById(R.id.test_child_item_name);
            mRb = itemView.findViewById(R.id.diagnose_rb);
        }
    }


}

