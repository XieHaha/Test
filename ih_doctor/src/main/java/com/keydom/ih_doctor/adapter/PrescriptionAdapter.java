package com.keydom.ih_doctor.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.DrugChooseActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DrugUseActivity;
import com.keydom.ih_doctor.activity.prescription_check.DiagnosePrescriptionActivity;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.DrugListBean;
import com.keydom.ih_doctor.bean.PrescriptionBodyBean;
import com.keydom.ih_doctor.bean.PrescriptionBottomBean;
import com.keydom.ih_doctor.bean.PrescriptionHeadBean;
import com.keydom.ih_doctor.m_interface.OnModelDialogListener;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.DialogUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    public final static int TYPE_HEAD = 0;
    public final static int TYPE_BODY = 1;
    public final static int TYPE_BOTTOM = 2;
    private  int num=0;
    private DiagnosePrescriptionActivity context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PrescriptionAdapter(DiagnosePrescriptionActivity context,List<MultiItemEntity> data) {
        super(data);
        this.context=context;
        addItemType(TYPE_HEAD, R.layout.prescription_item_head);
        addItemType(TYPE_BODY, R.layout.prescription_item_body);
        addItemType(TYPE_BOTTOM, R.layout.prescription_item_bottom);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_HEAD:
                TextView select_save=helper.getView(R.id.select_save);
                ImageView prescription_delete_img=helper.getView(R.id.prescription_delete_img);
                PrescriptionHeadBean prescriptionHeadBean= (PrescriptionHeadBean) item;
                helper.setText(R.id.modal_name,prescriptionHeadBean.getTitleName() +" - " + (prescriptionHeadBean.getIsOutPrescription() == 0 ? "院内" : "外延"));
                select_save.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View view) {
                        DialogUtils.saveModelDialog(context,context.getTemplateList().get(prescriptionHeadBean.getPosition()), new OnModelDialogListener() {
                            @Override
                            public void dialogClick(View v, String modelType, String modelName) {
                                context.getTemplateList().get(prescriptionHeadBean.getPosition()).setSavedAsTemplate(true);
                                context.getTemplateList().get(prescriptionHeadBean.getPosition()).setModelNameTemp(modelName);
                                context.getTemplateList().get(prescriptionHeadBean.getPosition()).setModelTypeTemp(modelType);
                            }
                        }).show();
                    }
                });
                prescription_delete_img.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View view) {
                        new GeneralDialog(context, "确认删除该处方？", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                context.removePrescription(prescriptionHeadBean.getPosition());
                            }
                        }).setTitle("提示").setNegativeButton("取消").setPositiveButton("确认").show();
                    }
                });
                break;

            case TYPE_BODY:
                PrescriptionBodyBean prescriptionBodyBean= (PrescriptionBodyBean) item;
                DrugBean drugBean= prescriptionBodyBean.getDrugBean();
                TextView update_tv = helper.getView(R.id.update_tv);
                TextView delete_tv = helper.getView(R.id.delete_tv);

                if(getData().get(helper.getPosition()-1) instanceof PrescriptionHeadBean){
                    num=helper.getPosition()-1;
                }
                helper.setText(R.id.medicine_num, String.valueOf(helper.getPosition() -num) + "、").setText(R.id.medicine_name, drugBean.getDrugsName())
                        .setText(R.id.medicine_specifications, drugBean.getSpec()).setText(R.id.medicine_amount, String.valueOf(drugBean.getQuantity()) + drugBean.getPackUnit())
                        .setText(R.id.medicine_fee, drugBean.getPrice() == null ? "" : drugBean.getPrice().multiply(new BigDecimal(drugBean.getQuantity())) + "元")
                        .setText(R.id.use_once, "用法：" + drugBean.getSingleDose() + drugBean.getDosageUnit()).setText(R.id.use_method, drugBean.getWay())
                        .setText(R.id.times, String.valueOf(drugBean.getFrequency()));
                update_tv.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View view) {
//                ToastUtils.showShort("点击了修改按钮");
                        List<DrugBean> resultList=new ArrayList<>();
                        resultList.add(drugBean);
                        DrugListBean drugListBean=new DrugListBean();
                        drugListBean.setPosition(prescriptionBodyBean.getPosition());
                        drugListBean.setDrugList(resultList);
                        DrugUseActivity.start(context,drugListBean);
                    }
                });
                delete_tv.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View view) {
                        new GeneralDialog(context, "确认删除该药品？", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                context.removeDrug(prescriptionBodyBean.getPosition(),prescriptionBodyBean.getChildPosition());
                            }
                        }).setTitle("提示").setNegativeButton("取消").setPositiveButton("确认").show();
                    }
                });
                break;

            case TYPE_BOTTOM:
                PrescriptionBottomBean prescriptionBottomBean= (PrescriptionBottomBean) item;
                helper.setText(R.id.fee_count,"¥"+prescriptionBottomBean.getFee());
                CardView add_medicine=helper.getView(R.id.add_medicine);
                add_medicine.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View view) {
                        if (context.getSelectList().get(prescriptionBottomBean.getBottomPosition()).size() >= 5)
                            ToastUtil.showMessage(context, "最多添加五个药品");
                        else{
                            List<DrugBean> list=new ArrayList<>();
                            for (int i = 0; i <context.getSelectList().size() ; i++) {
                                list.addAll(context.getSelectList().get(i));
                            }
                            DrugChooseActivity.start(context,list,prescriptionBottomBean.getBottomPosition(),String.valueOf(context.getIsOutPrescription()));
                        }
                    }
                });
                break;
        }
    }
}
