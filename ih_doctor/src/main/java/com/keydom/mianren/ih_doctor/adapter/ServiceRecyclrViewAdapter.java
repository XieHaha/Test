package com.keydom.mianren.ih_doctor.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ServiceContentBean;
import com.keydom.mianren.ih_doctor.bean.ServiceItemBean;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：我的服务列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ServiceRecyclrViewAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_HEAD = -1;
    public static final int TYPE_CONTENT = 0;

    private int[] icons = {R.mipmap.point_black,R.mipmap.point_blue,R.mipmap.point_green,
            R.mipmap.point_orang_yellow,R.mipmap.point_orange,R.mipmap.point_purple,R.mipmap.point_purple_blue,
            R.mipmap.point_red,R.mipmap.point_yellow};

    public ServiceRecyclrViewAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEAD,R.layout.my_service_item);
        addItemType(TYPE_CONTENT,R.layout.my_service_item_content);
    }

    private String getEnable(Integer state) {
        if (state != null && state == ServiceContentBean.OPEN) {
            return "已开通";
        } else {
            return "";
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()){
            case TYPE_HEAD:
                ServiceItemBean itemBean = (ServiceItemBean) item;
                int x=(int)(Math.random()*8);
                helper.setImageResource(R.id.service_icon,icons[x]).setText(R.id.service_name,itemBean.getName()+"");
                break;
            case TYPE_CONTENT:
                ServiceContentBean contentBean = (ServiceContentBean) item;
                helper.setText(R.id.service_name,contentBean.getSubName()+"")
                        .setText(R.id.service_enable,getEnable(contentBean.getSubState()));
                break;
        }
    }
}
