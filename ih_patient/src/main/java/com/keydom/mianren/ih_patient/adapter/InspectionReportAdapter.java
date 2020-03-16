package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.BodyCheckDetailActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionDetailActivity;
import com.keydom.mianren.ih_patient.bean.BodyCheckRecordInfo;
import com.keydom.mianren.ih_patient.bean.InspectionRecordInfo;
import com.keydom.mianren.ih_patient.utils.DepartmentDataHelper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查报告适配器
 */
public class InspectionReportAdapter extends RecyclerView.Adapter {
    private List<Object> dataList;
    private List<Object> showList = new ArrayList<>();
    private Map<String,Integer> headMap=new HashMap<>();
    private Context context;
    private int HEADER = 1, INSPECTIONBODY = 2, BODYCHECKBODY = 3;
    /**
     * 适配器
     */
    public InspectionReportAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.inspection_report_head_item, parent, false);
            return new InspectionReportAdapter.HeaderViewHolder(view);
        } else if (viewType == INSPECTIONBODY || viewType == BODYCHECKBODY) {
            view = LayoutInflater.from(context).inflate(R.layout.inspection_report_body_item, parent, false);
            return new InspectionReportAdapter.BodyViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof InspectionReportAdapter.HeaderViewHolder) {
            final InspectionReportAdapter.HeaderViewHolder headerViewHolder = (InspectionReportAdapter.HeaderViewHolder) holder;
            final DepartmentDataHelper.InspectionReportGroup inspectionReportGroup = (DepartmentDataHelper.InspectionReportGroup) showList.get(position);
            if (inspectionReportGroup.isExpand()) {
                Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.exa_report_up);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                headerViewHolder.head_tv.setCompoundDrawables(null, null, rightDrawable, null);
            }else {
                Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.exa_report_down);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                headerViewHolder.head_tv.setCompoundDrawables(null, null, rightDrawable, null);
            }
            headerViewHolder.head_tv.setText(inspectionReportGroup.getData());
            headerViewHolder.head_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("click_position==" + position);
                    if (inspectionReportGroup.isExpand()) {
                        Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.exa_report_down);
                        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                        headerViewHolder.head_tv.setCompoundDrawables(null, null, rightDrawable, null);
                        for (int i = position + inspectionReportGroup.getChildSize(); i >= position + 1; i--) {
                            showList.remove(i);
                        }
                    } else {
                        Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.exa_report_up);
                        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                        headerViewHolder.head_tv.setCompoundDrawables(null, null, rightDrawable, null);
                        /*if (position != 0) {
                            if (showList.get(position - 1) instanceof DepartmentDataHelper.InspectionReportGroup) {
                                final DepartmentDataHelper.InspectionReportGroup previousGroup = (DepartmentDataHelper.InspectionReportGroup) showList.get(position - 1);
                                for (int i = position + 1 + previousGroup.getChildSize(); i <= position + inspectionReportGroup.getChildSize() + previousGroup.getChildSize(); i++) {
                                    showList.add(position + 1, dataList.get(i));
                                }
                            } else {
                                for (int i = position + 1; i <= position + inspectionReportGroup.getChildSize(); i++) {
                                    showList.add(position + 1, dataList.get(i));
                                }
                            }
                        } else {
                            for (int i = position + 1; i <= position + inspectionReportGroup.getChildSize(); i++) {
                                showList.add(position + 1, dataList.get(i));
                            }
                        }
*/
                        int tempPosition=position+1;
                        for (int i =headMap.get(inspectionReportGroup.getData())+1; i <= headMap.get(inspectionReportGroup.getData())+ inspectionReportGroup.getChildSize(); i++) {
                            showList.add(tempPosition, dataList.get(i));
                            tempPosition++;
                        }


                    }
                    inspectionReportGroup.setExpand(!inspectionReportGroup.isExpand());
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof InspectionReportAdapter.BodyViewHolder) {
            final InspectionReportAdapter.BodyViewHolder bodyViewHolder = (InspectionReportAdapter.BodyViewHolder) holder;
            if (showList.get(position) instanceof BodyCheckRecordInfo.InspectRecordVOListBean) {
                BodyCheckRecordInfo.InspectRecordVOListBean inspectRecordVOListBean = (BodyCheckRecordInfo.InspectRecordVOListBean) showList.get(position);
                bodyViewHolder.body_tv.setText(inspectRecordVOListBean.getInspectName());
                bodyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BodyCheckDetailActivity.start(context,inspectRecordVOListBean.getApplyNumber(),inspectRecordVOListBean.getInspectName());
                    }
                });
            } else {
                InspectionRecordInfo.CheckoutRecordVOBean checkoutRecordVOBean = (InspectionRecordInfo.CheckoutRecordVOBean) showList.get(position);
                bodyViewHolder.body_tv.setText(checkoutRecordVOBean.getCheckoutName());
                bodyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InspectionDetailActivity.start(context,checkoutRecordVOBean.getApplyNumber(),checkoutRecordVOBean.getCheckoutName());
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showList.get(position) instanceof DepartmentDataHelper.InspectionReportGroup) {
            return HEADER;
        } else if (showList.get(position) instanceof BodyCheckRecordInfo.InspectRecordVOListBean) {
            return INSPECTIONBODY;
        } else {
            return BODYCHECKBODY;
        }


    }

    static class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView body_tv;

        BodyViewHolder(View view) {
            super(view);
            body_tv = view.findViewById(R.id.body_tv);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView head_tv;

        HeaderViewHolder(View view) {
            super(view);
            head_tv = view.findViewById(R.id.head_tv);
        }
    }
    /**
     * 更新展示列表
     */
    public void updateShowList(List<Object> dataList) {
        showList.clear();
        headMap.clear();
        for (int i = 0; i <dataList.size() ; i++) {
            if (dataList.get(i) instanceof DepartmentDataHelper.InspectionReportGroup) {
                showList.add(dataList.get(i));
                headMap.put(((DepartmentDataHelper.InspectionReportGroup) dataList.get(i)).getData(),i);
            }
        }
       /* for (Object object : dataList) {
            if (object instanceof DepartmentDataHelper.InspectionReportGroup) {
                showList.add(object);

            }

        }*/
        Logger.e("->showList.size=" + showList.size());
    }

}
