package com.keydom.ih_patient.adapter;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.ih_patient.bean.NursingOrderService2Bean;
import com.keydom.ih_patient.bean.NursingOrderService3Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceBean;
import com.keydom.ih_patient.bean.NursingOrderServiceHead;
import com.keydom.ih_patient.bean.NursingOrderServiceItem2Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceItem3Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceItemBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * created date: 2018/12/20 on 15:45
 * des:护理服务适配器
 */
public class NursingOrderServiceAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    /**
     * 多布局type
     */
    public static final int TYPE_HEAD = -1;
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    public static final int TYPE_LEVEL_3 = 3;
    public static final int TYPE_LEVEL_4 = 4;
    public static final int TYPE_LEVEL_5 = 5;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public NursingOrderServiceAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEAD, R.layout.nursing_order_sevice_item_0);//head

        addItemType(TYPE_LEVEL_0, R.layout.nursing_order_sevice_item_1);//服务head
        addItemType(TYPE_LEVEL_1, R.layout.nursing_order_sevice_item_6);//服务item

        addItemType(TYPE_LEVEL_4, R.layout.nursing_order_sevice_item_2);//子订单head
        addItemType(TYPE_LEVEL_2, R.layout.nursing_order_sevice_item_3);//子订单item

        addItemType(TYPE_LEVEL_5, R.layout.nursing_order_sevice_item_5);//器械head
        addItemType(TYPE_LEVEL_3, R.layout.nursing_order_sevice_item_4);//器械item
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case TYPE_HEAD:
                NursingOrderServiceHead head = (NursingOrderServiceHead) item;
                helper.setText(R.id.title, head.getTitle());
                break;
            case TYPE_LEVEL_0:
                NursingOrderServiceBean serviceBean = (NursingOrderServiceBean) item;
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    boolean isex = serviceBean.isExpanded();
                    if (isex) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                helper.setText(R.id.service_no, "第" + serviceBean.getFrequency() + "次服务")
                        .setText(R.id.service_state, serviceBean.getStateString() + "")
                        .setImageResource(R.id.service_icon, serviceBean.isExpanded() ? R.mipmap.exa_report_up : R.mipmap.exa_report_down)
                        .setTextColor(R.id.service_state, serviceBean.getState() == 2 ? mContext.getResources().getColor(R.color.register_success_color) : mContext.getResources().getColor(R.color.nursing_status_red));

                break;
            case TYPE_LEVEL_1:
                NursingOrderServiceItemBean serviceItemBean = (NursingOrderServiceItemBean) item;
                helper.setImageResource(R.id.icon, R.mipmap.nursing_order_unselect_icon);
                if (serviceItemBean.isTop()) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = ConvertUtils.dp2px(mContext.getResources().getDimension(R.dimen.dp_10));
                    helper.itemView.setLayoutParams(params);
                    helper.setImageResource(R.id.icon, R.mipmap.nursing_order_select_icon);
                }
                else if (serviceItemBean.isBottom()) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.bottomMargin = ConvertUtils.dp2px(mContext.getResources().getDimension(R.dimen.dp_10));
                    params.bottomMargin = 0;
                    helper.itemView.setLayoutParams(params);
                }else{
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.bottomMargin = 0;
                    params.topMargin = 0;
                    helper.itemView.setLayoutParams(params);
                }

                SpannableStringBuilder mark = new SpanUtils().append(StringUtils.isEmpty(serviceItemBean.getMark()) ? "" : serviceItemBean.getMark()).setUnderline().create();

                GlideUtils.load(helper.getView(R.id.img), Const.IMAGE_HOST + serviceItemBean.getUserImage(), 0, 0, true, null);
                helper.setText(R.id.mark, mark)
                        .setText(R.id.content, StringUtils.isEmpty(serviceItemBean.getContent())?"":serviceItemBean.getContent())
                        .setText(R.id.time, serviceItemBean.getAcceptTime()+"")
                        .setGone(R.id.img_group, serviceItemBean.getUserImage() != null)
                        .setOnClickListener(R.id.mark, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!StringUtils.isEmpty(serviceItemBean.getQualificationsCard())){
                                    CommonDocumentActivity.start(mContext,StringUtils.isEmpty(serviceItemBean.getMark()) ? "" : serviceItemBean.getMark(),serviceItemBean.getQualificationsCard());
                                }

                            }
                        });
                break;

            case TYPE_LEVEL_2:
                NursingOrderServiceItem2Bean item2Bean = (NursingOrderServiceItem2Bean) item;
                helper.setText(R.id.service, item2Bean.getServerProject())
                        .setText(R.id.order_num, item2Bean.getSubOrderNumber())
                        .setText(R.id.service_cost, "¥" + item2Bean.getFee() + "元")
                        .setText(R.id.service_num, item2Bean.getFrequency() + "次");
                break;

            case TYPE_LEVEL_4:
                NursingOrderService2Bean service2Bean = (NursingOrderService2Bean) item;
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (service2Bean.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                helper.setText(R.id.title, "新增子订单" + service2Bean.getFrequency());
                break;

            case TYPE_LEVEL_5:
                NursingOrderService3Bean service3Bean = (NursingOrderService3Bean) item;
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (service3Bean.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                helper.setText(R.id.service_no, "第" + service3Bean.getServiceFrequency() + "次服务")
                        .setText(R.id.service_state, service3Bean.getStateString() + "")
                        .setImageResource(R.id.service_icon, service3Bean.isExpanded() ? R.mipmap.exa_report_up : R.mipmap.exa_report_down)
                        .setTextColor(R.id.service_state, service3Bean.getState() == 3 ? mContext.getResources().getColor(R.color.list_tab_color) : mContext.getResources().getColor(R.color.nursing_status_red));
                break;

            case TYPE_LEVEL_3:
                FrameLayout top = helper.getView(R.id.top_group);
                RelativeLayout bottom = helper.getView(R.id.bottom_group);
                top.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);

                NursingOrderServiceItem3Bean serviceItem3Bean = (NursingOrderServiceItem3Bean) item;
                if (serviceItem3Bean.isTop()) {
                    top.setVisibility(View.VISIBLE);
                }
                if (serviceItem3Bean.isBottom()) {
                    bottom.setVisibility(View.VISIBLE);
                }


                helper.setText(R.id.namex,serviceItem3Bean.getFrequency()+"、")
                        .setText(R.id.name,StringUtils.isEmpty(serviceItem3Bean.getEquipmentName())?"":serviceItem3Bean.getEquipmentName())
                        .setText(R.id.content,StringUtils.isEmpty(serviceItem3Bean.getDescription())?"":serviceItem3Bean.getDescription())
                        .setText(R.id.num,serviceItem3Bean.getQuantity()+(StringUtils.isEmpty(serviceItem3Bean.getUnit())?"":serviceItem3Bean.getUnit())+"")
                        .setText(R.id.money,serviceItem3Bean.getPrice()+"元")
                        .setText(R.id.total,"¥"+new DecimalFormat("0.00").format(serviceItem3Bean.getTotal()));
                break;
        }

    }
}
