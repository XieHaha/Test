package com.keydom.mianren.ih_doctor.activity.my_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.my_message.controller.NoticeDeatailCOntroller;
import com.keydom.mianren.ih_doctor.activity.my_message.view.NoticeDeatailView;
import com.keydom.mianren.ih_doctor.bean.MessageBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

/**
 * 消息详情页面
 */
public class NoticeDeatailActivity extends BaseControllerActivity<NoticeDeatailCOntroller> implements NoticeDeatailView {


    /**
     * 启动
     */
    public static void start(Context context, MessageBean messageBean){
        Intent intent=new Intent(context,NoticeDeatailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("messageBean",messageBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private TextView article_title,article_box_rich_text;
    private MessageBean messageBean;
    private SmartRefreshLayout refreshLayout;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_notic_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RichText.initCacheDir(this);
        setTitle("公告详情");
        refreshLayout=findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
//        notificationsBean= (IndexData.NotificationsBean) getIntent().getSerializableExtra("noticBean");
        messageBean= (MessageBean) getIntent().getSerializableExtra("messageBean");
        article_title=this.findViewById(R.id.article_title);
        article_box_rich_text=this.findViewById(R.id.article_box_rich_text);
        if(messageBean!=null){
            article_title.setText(messageBean.getTitle()!=null&&!"".equals(messageBean.getTitle())?messageBean.getTitle():"");
            RichText.from(messageBean.toString()).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(article_box_rich_text);
            getController().updateMessageState(messageBean.getId());
        }


    }
}
