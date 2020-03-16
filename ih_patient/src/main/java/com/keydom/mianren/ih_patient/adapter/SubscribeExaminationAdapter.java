package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;

import java.util.List;

/**
 * created date: 2018/12/12 on 17:13
 * des:体检预约适配器
 */
public class SubscribeExaminationAdapter extends BaseQuickAdapter<SubscribeExaminationBean, BaseViewHolder> {
    /**
     * 订单状态
     */
    public static final int WAIT_PAY = 1;
    public static final int WAIT_EXAM = 2;
    public static final int COMPLETE = 3;
    public static final int CANCEL = 4;
    public static final int EXAMINATING = 5;
    public static final int CHARGE_BACK = 6;
    private onItemBtnClickListener iOnItemBtnClickListener;

    /**
     * 构建方法
     */
    public SubscribeExaminationAdapter(@Nullable List<SubscribeExaminationBean> data, onItemBtnClickListener onItemBtnClickListener) {
        super(R.layout.item_subscribe_examination_order, data);
        this.iOnItemBtnClickListener = onItemBtnClickListener;
    }

    /**
     * 点击事件接口
     */
    public interface onItemBtnClickListener {
        void onLeftClick(SubscribeExaminationBean item);

        void onDeleteClick(SubscribeExaminationBean item);

        void onRightClick(SubscribeExaminationBean item);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SubscribeExaminationBean item) {
        ImageView statusIv = helper.getView(R.id.nursing_status_iv);
        TextView statusTv = helper.getView(R.id.nursing_status_tv);
        TextView btnLeft = helper.getView(R.id.btn_left);
        TextView btnRight = helper.getView(R.id.btn_right);
        ImageView cover = helper.getView(R.id.goods_img);
        GlideUtils.load(cover, item.getIcon() == null ? "" : Const.IMAGE_HOST + item.getIcon(), 0, 0, false, null);
        helper.setText(R.id.goods_num, "共" + item.getNumber() + "件商品")
                .setText(R.id.goods_money, "¥ " + item.getFee())
                .setText(R.id.goods_text, StringUtils.isEmpty(item.getSummary()) ? "" : item.getSummary())
                .setText(R.id.goods_title, item.getName() + "")
                .setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iOnItemBtnClickListener.onDeleteClick(item);
                    }
                })
                .setOnClickListener(R.id.btn_left, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iOnItemBtnClickListener.onLeftClick(item);
                    }
                })
                .setOnClickListener(R.id.btn_right, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iOnItemBtnClickListener.onRightClick(item);
                    }
                });

        int color = 0;
        String stateStr = "";
        String leftStr = "";
        String rightStr = "";
        item.setItemState(getState(item));
        boolean canDelete = false;
        switch (item.getItemState()) {
            case WAIT_PAY:
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                stateStr = "待付款";
                leftStr = "取消订单";
                rightStr = "支付";
                break;
            case WAIT_EXAM:
                color = mContext.getResources().getColor(R.color.nursing_status_yellow);
                stateStr = "待体检";
                leftStr = "退单";
                rightStr = "再次购买";
                break;
            case COMPLETE:
                color = mContext.getResources().getColor(R.color.list_tab_color);
                stateStr = "已完成";
                leftStr = "评价";
                rightStr = "再次购买";
                canDelete = true;
                break;

            case EXAMINATING:
                color = mContext.getResources().getColor(R.color.register_success_color);
                stateStr = "体检中";
                leftStr = "";
                rightStr = "再次购买";
                break;
            case CHARGE_BACK:
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                stateStr = "已退单";
                leftStr = "";
                rightStr = "再次购买";
                break;

            case CANCEL:
                color = mContext.getResources().getColor(R.color.tab_nol_color);
                if (item.getRefundState() == 0)
                    stateStr = "已取消";
                else if(item.getRefundState() == 1)
                    stateStr = "退款中";
                else
                    stateStr = "已退款";
                rightStr = "再次购买";
                leftStr = "";
                canDelete = true;
                break;
        }
        statusIv.setColorFilter(color);
        statusTv.setTextColor(color);
        statusTv.setText(stateStr);
        btnRight.setText(rightStr);
        btnLeft.setText(leftStr);
        helper.setGone(R.id.delete, canDelete);
    }

    /**
     * 判断多种状态
     */
    private int getState(SubscribeExaminationBean item) {
        //待付款
        if (item.getPayState() == 0 && item.getCancelState() == 0) {
            return WAIT_PAY;
        }
        //待体检
        if (item.getPayState() == 1 && item.getCompleteState() == 0 && item.getCancelState() == 0) {
            return WAIT_EXAM;
        }
        //已完成
        if (item.getCompleteState() == 1) {
            return COMPLETE;
        }
        //已取消
        if (item.getCancelState() == 1) {
            return CANCEL;
        }
        //体检中
        if (item.getPayState() == 1 && item.getCompleteState() == 2 && item.getCancelState() == 0) {
            return EXAMINATING;
        }
        //已退单
        if (item.getPayState() == -1) {
            return CHARGE_BACK;
        }
        return 0;
    }
}
