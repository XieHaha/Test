package com.keydom.ih_doctor.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.ih_doctor.bean.CheckOutItemBean;
import com.keydom.ih_doctor.m_interface.SelectInspectItemListener;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.view.DiagnoseInspactItemDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class InspectItemListAdapter extends BaseQuickAdapter<CheckOutItemBean, BaseViewHolder> {
    boolean ischange;

    public InspectItemListAdapter(@Nullable List<CheckOutItemBean> data) {
        super(R.layout.add_inspect_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutItemBean item) {
        if (item == null) {
            return;
        }
        ischange = true;
        helper.setText(R.id.inspact_item_name, item.getName());
        TextView inspactItemName = helper.getView(R.id.inspact_item_name);
        TextView subItemName = helper.getView(R.id.sub_item_name);
        TextView subItemPart = helper.getView(R.id.sub_item_part);
        TextView subItemDept = helper.getView(R.id.sub_item_dept);
        TextView subItemFee = helper.getView(R.id.sub_item_fee);
        EditText subItemEntrustEt = helper.getView(R.id.sub_item_entrust_et);
        ImageButton subItemDeleteBtn = helper.getView(R.id.sub_item_delete_btn);
        LinearLayout inspactDetailLl = helper.getView(R.id.inspact_detail_ll);
        subItemEntrustEt.setText(item.getRemark());
        subItemName.setText("");
        subItemDept.setText("");
        subItemPart.setText("");
        subItemFee.setText("金额：¥0.0");
        if (item.selectedItem() != null) {
            subItemName.setText(item.selectedItem().getName());
            subItemDept.setText(item.selectedItem().getDeptName());
            String selectItemStr = "";
            BigDecimal totalFee = BigDecimal.ZERO;
            if (item.selectedItem().selectedItems() != null && item.selectedItem().selectedItems().size() > 0) {
                for (CheckOutItemBean selectItem : item.selectedItem().selectedItems()) {
                    if ("".equals(selectItemStr)) {
                        selectItemStr = selectItem.getName();
                    } else {
                        selectItemStr = selectItemStr + "," + selectItem.getName();
                    }
                    totalFee = totalFee.add((selectItem.getPrice() == null ? BigDecimal.ZERO : selectItem.getPrice()));
                }
            }
            subItemPart.setText(selectItemStr);
            subItemFee.setText(totalFee.toString());
        }
        ischange = false;
        subItemEntrustEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ischange) {
                    item.setRemark(s.toString());
                    if (item.selectedItem() != null) {
                        item.selectedItem().setRemark(s.toString());
                    }
                }
            }
        });
        inspactItemName.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                Drawable rightImg, leftImg;
                if (inspactDetailLl.getVisibility() == View.GONE) {
                    inspactDetailLl.setVisibility(View.VISIBLE);
                    rightImg = mContext.getResources().getDrawable(R.mipmap.arrow_top);
                    leftImg = mContext.getResources().getDrawable(R.mipmap.point_purple_blue);
                } else {
                    rightImg = mContext.getResources().getDrawable(R.mipmap.arrow_bottom_grey);
                    leftImg = mContext.getResources().getDrawable(R.mipmap.point_purple_blue);
                    inspactDetailLl.setVisibility(View.GONE);
                }
                rightImg.setBounds(0, 0, rightImg.getMinimumWidth(), rightImg.getMinimumHeight());
                leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
                inspactItemName.setCompoundDrawables(leftImg, null, rightImg, null);
            }
        });

        subItemName.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (item != null && item.getItems() != null) {
                    final List<String> list = new ArrayList<>();
                    for (CheckOutItemBean bean : item.getItems()) {
                        list.add(bean.getName());
                    }
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            for (CheckOutItemBean item : item.getItems()) {
                                item.setSelect(false);
                            }
                            item.getItems().get(options1).setSelect(true);
                            subItemName.setText(item.getItems().get(options1).getName());
                            subItemDept.setText(item.getItems().get(options1).getDeptName());
                            subItemPart.setText("");
                        }
                    }).build();
                    pvOptions.setPicker(list);
                    pvOptions.show();
                }
            }
        });
        subItemPart.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (item != null && item.selectedItem() != null && item.selectedItem().getItems() != null && item.selectedItem().getItems().size() > 0) {
                    final List<String> list = new ArrayList<>();
                    for (CheckOutItemBean bean : item.selectedItem().getItems()) {
                        list.add(bean.getName());
                    }
                    List<CheckOutItemBean> partList = new ArrayList<>();
                    partList.addAll(item.selectedItem().getItems());
                    DiagnoseInspactItemDialog dialog = new DiagnoseInspactItemDialog(mContext, partList, new SelectInspectItemListener() {
                        @Override
                        public void selectItem(List<CheckOutItemBean> list) {
                            String partStr = "";
                            BigDecimal totalFee = BigDecimal.ZERO;
                            for (CheckOutItemBean selectBean : list) {
                                partStr += "".equals(partStr) ? selectBean.getName() : ("、" + selectBean.getName());
                                totalFee = totalFee.add(selectBean.getPrice());
                                for (int i = 0; i < item.selectedItem().getItems().size(); i++) {
                                    if (item.selectedItem().getItems().get(i).getProjectId() == selectBean.getProjectId()) {
                                        item.selectedItem().getItems().get(i).setSelect(true);
                                    }
                                }
                            }
                            subItemPart.setText(partStr);
                            subItemFee.setText(totalFee.toString());
                            ((ApplyForCheckActivity) mContext).setInspactFee();
                        }
                    });
                    dialog.show();
                }
            }
        });

        subItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                new GeneralDialog(mContext, "确定删除该检查项目?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        item.reset();
                        mData.remove(item);
                        ((ApplyForCheckActivity) mContext).setInspactFee();
                        notifyDataSetChanged();
                    }
                }).show();
            }
        });
    }
}
