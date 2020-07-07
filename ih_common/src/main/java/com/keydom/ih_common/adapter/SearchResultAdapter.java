package com.keydom.ih_common.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.R;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.constant.SearchConst;
import com.keydom.ih_common.minterface.OnSearchListItemClickListener;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;

import java.util.List;


public class SearchResultAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private OnSearchListItemClickListener onItemClickListener;
    private String keyword = null;

    public SearchResultAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(SearchConst.SEARCH_USER, R.layout.search_item_user);
        addItemType(SearchConst.SEARCH_DEPT, R.layout.search_item_dept);
        addItemType(SearchConst.SEARCH_ARTICLE, R.layout.search_item_article);
        addItemType(SearchConst.SEARCH_ORDER, R.layout.search_order_item);
        addItemType(SearchConst.SEARCH_PATIENT, R.layout.search_item_user);
        addItemType(SearchConst.TITLE_ITEM, R.layout.search_item_title);
        addItemType(SearchConst.BOTTOM_ITEM, R.layout.search_item_bottom);
        addItemType(SearchConst.BOTTOM_NO_MORE_DATA, R.layout.search_item_no_more_data);
    }

    public void setKeyWord(String keyWord) {
        this.keyword = keyWord;
    }

    public void setOnItemClickListener(OnSearchListItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MultiItemEntity item) {

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(item);
                }
            }
        });
        switch (helper.getItemViewType()) {
            default:
                break;
            case SearchConst
                    .SEARCH_USER:
                helper.setText(R.id.user_name_tv, CommonUtils.getSearchResultStr(keyword, ((SearchResultBean.UserBean) item).getName()));
                GlideUtils.load((ImageView) helper.getView(R.id.user_icon_cv), ((SearchResultBean.UserBean) item).getImage(), 0, 0, false, null);
                break;
            case SearchConst
                    .SEARCH_DEPT:
                helper.setText(R.id.dept_name_tv, CommonUtils.getSearchResultStr(keyword, ((SearchResultBean.DeptBean) item).getName()));
                GlideUtils.load((ImageView) helper.getView(R.id.dept_icon_cv), ((SearchResultBean.DeptBean) item).getImage(), 0, 0, false, null);
                break;
            case SearchConst
                    .SEARCH_ARTICLE:
                helper.setText(R.id.article_tv, CommonUtils.getSearchResultStr(keyword, ((SearchResultBean.ArticleBean) item).getContent()));
                break;
            case SearchConst.SEARCH_PATIENT:
                helper.setText(R.id.user_name_tv, CommonUtils.getSearchResultStr(keyword, ((SearchResultBean.PatientBean) item).getName()));
                GlideUtils.load((ImageView) helper.getView(R.id.user_icon_cv), ((SearchResultBean.PatientBean) item).getImage(), 0, 0, false, null);
                break;
            case SearchConst.SEARCH_ORDER:
                final SearchResultBean.OrderBean bean = (SearchResultBean.OrderBean) item;
                TextView orderStatus, orderTypeTv;
                CircleImageView circleImageView;
                circleImageView=helper.itemView.findViewById(R.id.user_icon);
                orderTypeTv = helper.itemView.findViewById(R.id.order_type_tv);
                orderStatus = helper.itemView.findViewById(R.id.order_status);
                helper.setText(R.id.user_name, CommonUtils.getSearchResultStr(keyword, bean.getName()));
                helper.setText(R.id.diagnose_dec, CommonUtils.getSearchResultStr(keyword, bean.getConditionDesc()));
                helper.setText(R.id.diagnose_time,bean.getCreateTime()==null?"":bean.getCreateTime() );
                helper.setText(R.id.user_age,bean.getAge());
                helper.setText(R.id.user_sex,CommonUtils.getSex(bean.getSex()));
                GlideUtils.load(circleImageView, Const.IMAGE_HOST+bean.getPatientImage(), 0, 0, false, null);
                Drawable img;
                if (bean.getOrderType() != null && "0".equals(bean.getOrderType())) {
                    Drawable leftimg = mContext.getResources().getDrawable(R.mipmap.diagnose_illustration);
                    leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
                    orderTypeTv.setText("图文问诊");
                    orderTypeTv.setTextColor(mContext.getResources().getColor(R.color.font_order_type_image_with_video));
                    orderTypeTv.setCompoundDrawables(leftimg, null, null, null);
                } else {
                    Drawable leftimg = mContext.getResources().getDrawable(R.mipmap.diagnose_illustration);
                    leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
                    orderTypeTv.setCompoundDrawables(leftimg, null, null, null);
                    orderTypeTv.setText("视频问诊");
                    orderTypeTv.setTextColor(mContext.getResources().getColor(R.color.font_order_type_image_with_video));
                }
                orderStatus.setVisibility(View.VISIBLE);
                /**
                 * 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5 待评价 7完成 -1已取消
                 */
                switch (bean.getState()) {

                    case 0:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatus.setText("未支付");
                        break;
                    case 1:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatus.setText("待接诊");
                        break;
                    case 2:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatus.setText("问诊中");
                        break;
                    case 3:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_red);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_red));
                        orderStatus.setText("审核不通过");
                        break;
                    case 4:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatus.setText("问诊中");
                        break;
                    case 5:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatus.setText("问诊中");
                        break;
                    case 6:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatus.setText("问诊中");
                        break;
                    case 7:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatus.setText("待接诊");
                        break;
                    case 8:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatus.setText("待评价");
                        break;
                    case 9:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatus.setText("已完成");
                        break;
                    case -1:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_red);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatus.setCompoundDrawables(img, null, null, null);
                        orderStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_red));
                        orderStatus.setText("已取消");
                        break;
                    default:
                        orderStatus.setVisibility(View.GONE);
                        break;
                }
                break;
            case SearchConst
                    .TITLE_ITEM:
                helper.setText(R.id.title_tv, ((SearchResultBean.TitleItemBean) item).getName());
                break;
            case SearchConst
                    .BOTTOM_ITEM:
                helper.setText(R.id.bottom_tv, ((SearchResultBean.BottomItemBean) item).getName());
                break;

        }
    }

}
