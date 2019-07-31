package com.keydom.ih_patient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_patient.R;

/**
 * 文章item
 */
public class GeneralArticleItem extends RelativeLayout {
    private ImageView articleIconImg;
    private TextView articleTitleTv,articleLabelTv,articleReaderNumTv;
    private long articleId=0;
    private int readNum;
    private String dateStr;

    /**
     * 构建方法
     * @param context
     */
    public GeneralArticleItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.general_article_item,this,true);
        articleIconImg=findViewById(R.id.article_icon_img);
        articleTitleTv=findViewById(R.id.article_title_tv);
        articleLabelTv=findViewById(R.id.article_label_tv);
        articleReaderNumTv=findViewById(R.id.article_readernum_tv);
    }

    /**
     * 设置图标
     * @param url
     */
    public void setIcon(String url){
        //articleIconImg.setImageResource(ResouceId);
        Glide.with(getContext()).load(Const.IMAGE_HOST+url).into((ImageView) articleIconImg);
    }

    /**
     * 设置标题
     * @param titleStr
     */
    public void setTitle(String titleStr){
        articleTitleTv.setText(titleStr);
    }

    /**
     * 设置label
     * @param labelStr
     */
    public void setLabel(String labelStr){
        articleLabelTv.setText(labelStr);
    }

    /**
     * 设置阅读人数
     * @param readerNumStr
     * @param dateStr
     */
    public void setReaderNum(int readerNumStr,String dateStr){
        articleReaderNumTv.setText("阅读人数："+readerNumStr+"  "+dateStr);
        this.dateStr=dateStr;
        readNum=readerNumStr;
    }

    /**
     * 添加阅读人数
     */
    public void addReadNum(){
        readNum+=1;
        articleReaderNumTv.setText("阅读人数："+readNum+"  "+dateStr);
    }

    /**
     * 获取文章id
     * @return
     */
    public long getArticleId() {
        return articleId;
    }

    /**
     * 设置文章id
     * @param articleId
     */
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
