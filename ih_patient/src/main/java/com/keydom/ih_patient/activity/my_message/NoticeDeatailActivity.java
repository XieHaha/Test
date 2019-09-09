package com.keydom.ih_patient.activity.my_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_message.controller.NoticeDeatailCOntroller;
import com.keydom.ih_patient.activity.my_message.view.NoticeDeatailView;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.MessageBean;
import com.keydom.ih_patient.bean.NoticeBean;
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
    public static void start(Context context, NoticeBean.Notice notificationsBean){
        Intent intent=new Intent(context,NoticeDeatailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("notice",notificationsBean);
        intent.putExtras(bundle);
        intent.putExtra("type",NOTICETYPE);
        context.startActivity(intent);
    }
    /**
     * 启动
     */
    public static void start(Context context, IndexData.NotificationsBean notificationsBean){
        Intent intent=new Intent(context,NoticeDeatailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("noticBean",notificationsBean);
        intent.putExtras(bundle);
        intent.putExtra("type",NOTICETYPE);
        context.startActivity(intent);
    }
    /**
     * 启动
     */
    public static void start(Context context, MessageBean messageBean){
        Intent intent=new Intent(context,NoticeDeatailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("messageBean",messageBean);
        intent.putExtras(bundle);
        intent.putExtra("type",MESSAGETYPE);
        context.startActivity(intent);
    }
    public static final String NOTICETYPE="notice_type";
    public static final String MESSAGETYPE="message_type";
    private TextView article_title,article_box_rich_text;
    private  IndexData.NotificationsBean notificationsBean;
    private MessageBean messageBean;
    private NoticeBean.Notice notice;
    private SmartRefreshLayout refreshLayout;
    private String type;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_notic_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RichText.initCacheDir(this);

        refreshLayout=findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        notificationsBean= (IndexData.NotificationsBean) getIntent().getSerializableExtra("noticBean");
        messageBean= (MessageBean) getIntent().getSerializableExtra("messageBean");
        notice= (NoticeBean.Notice) getIntent().getSerializableExtra("notice");
        type=getIntent().getStringExtra("type");
        if(NOTICETYPE.equals(type))
            setTitle("公告详情");
        else
            setTitle("消息详情");
        article_title=this.findViewById(R.id.article_title);
        article_box_rich_text=this.findViewById(R.id.article_box_rich_text);
        if(notice!=null){
            article_title.setText(notice.getTitle()!=null&&!"".equals(notice.getTitle())?notice.getTitle():"");
            RichText.from(notice.getContent()).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(article_box_rich_text);
        }
        if(notificationsBean!=null){
            article_title.setText(notificationsBean.getTitle()!=null&&!"".equals(notificationsBean.getTitle())?notificationsBean.getTitle():"");
            RichText.from(notificationsBean.getContent()).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(article_box_rich_text);
        }
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
