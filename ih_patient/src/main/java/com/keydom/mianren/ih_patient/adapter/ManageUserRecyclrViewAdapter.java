package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;

import java.util.List;

/**
 * @Description：就诊人适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ManageUserRecyclrViewAdapter extends BaseQuickAdapter<ManagerUserBean,BaseViewHolder>{
    public interface IOnManagerItemClickListener{
        void onDelete(ManagerUserBean item,int pos);
        void onEdit(ManagerUserBean item,int pos);
    }

    private IOnManagerItemClickListener iOnManagerItemClickListener;
    /**
     * 构造方法
     */
    public ManageUserRecyclrViewAdapter(@Nullable List<ManagerUserBean> data,IOnManagerItemClickListener itemClickListener) {
        super(R.layout.activity_manage_user_item_layout,data);
        this.iOnManagerItemClickListener = itemClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ManagerUserBean item) {
        String area = "";
        if (!StringUtils.isEmpty(item.getArea())){
            try{
                String[] split = item.getArea().split("-");
                if (split.length > 2){
                    area = split[0]+"-"+split[1];
                }else{
                    area = split[0];
                }
            }
            catch (Exception e){
                area = item.getArea();
            }

        }
        helper.setText(R.id.user_name, StringUtils.isEmpty(item.    getName())?"":item.getName())
                .setText(R.id.user_sex,"0".equals(item.getSex())?"男":"女")
                .setText(R.id.id_card,StringUtils.isEmpty(item.getCardId())?"":item.getCardId())
                .setText(R.id.city,area)
                .setText(R.id.phone,StringUtils.isEmpty(item.getPhone())?"":item.getPhone())
                .setText(R.id.history_disease,StringUtils.isEmpty(item.getPastMedicalHistory())?"":item.getPastMedicalHistory())
                .setOnClickListener(R.id.delete, v -> iOnManagerItemClickListener.onDelete(item,helper.getAdapterPosition()))
                .setOnClickListener(R.id.edit, v -> iOnManagerItemClickListener.onEdit(item,helper.getAdapterPosition()));

        GlideUtils.load((CircleImageView)helper.getView(R.id.user_img), Const.IMAGE_HOST+item.getUserImage(),0,0,true,null);


    }
}
