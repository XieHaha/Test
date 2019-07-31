package com.keydom.ih_common.im.emoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
/**
 *
 * @link
 *
 * Author: song
 *
 * Create: 19/4/17 下午11:02
 *
 * Changes (from 19/4/17)
 *
 * 19/4/17 : Create EmojiVpAdapter.java (song);
 *
 *
 *
 */
public class EmojiVpAdapter extends PagerAdapter {

    private List<View> mViewList;
    public EmojiVpAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return (mViewList.get(position));
    }

    @Override
    public int getCount() {
        if (mViewList == null)
            return 0;
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
