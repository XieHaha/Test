package com.keydom.ih_common.im.emoji;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.R;

import java.util.List;

/**
 *
 * @link
 *
 * Author: song
 *
 * Create: 19/4/17 下午10:26
 *
 * Changes (from 19/4/17)
 *
 * 19/4/17 : Create EmojiAdapter.java (song);
 *
 *
 *
 */
public class EmojiAdapter extends BaseQuickAdapter< EmojiBean,BaseViewHolder> {


    public EmojiAdapter( @Nullable List<EmojiBean> data, int index, int pageSize) {
         super(R.layout.item_emoji,  data);
     }

    @Override
    protected void convert(BaseViewHolder helper, EmojiBean item) {
        //判断是否为最后一个item
        if (item.getId()==0) {
             helper.setBackgroundRes(R.id.et_emoji,R.mipmap.rc_icon_emoji_delete );
        } else {
             helper.setText(R.id.et_emoji,item.getUnicodeInt() );
        }



    }


}
