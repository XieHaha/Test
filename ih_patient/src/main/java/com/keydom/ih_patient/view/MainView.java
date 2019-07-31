package com.keydom.ih_patient.view;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.keydom.ih_common.view.ScrollControlViewPager;
import com.keydom.ih_patient.R;


/**
 * 主页控件
 * @link
 * Author: song
 * Create: 18/11/5 下午5:14
 * Changes (from 18/11/5)
 * 18/11/5 : Create MainView.java (song);
 */
public class MainView extends RelativeLayout {



    private Button[] mBtnList;
    private int[] mBtnListID;
    private ScrollControlViewPager mViewContainer;

    /**
     * 构建方法
     * @param context
     * @param attrs
     */
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 初始化
     */
    public void initModule() {
        mBtnListID = new int[] {
                R.id.tab_index, R.id.tab_diagnose, R.id.tab_nurse, R.id.tab_mine
        };
        mBtnList = new Button[4];
        for (int i = 0; i < 4; i++) {
            mBtnList[i] = (Button) findViewById(mBtnListID[i]);
        }
        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
        mBtnList[0].setTextColor(getResources().getColor(R.color.tab_sel_color));
        mBtnList[0].setSelected(true);
    }

    /**
     * 设置监听
     * @param onclickListener
     */
    public void setOnClickListener(OnClickListener onclickListener) {
        for (int i = 0; i < mBtnListID.length; i++) {
            mBtnList[i].setOnClickListener(onclickListener);
        }
    }

    /**
     * 页面改变监听
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewContainer.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
        mViewContainer.setAdapter(adapter);
        mViewContainer.setOffscreenPageLimit(4);
    }

    /**
     * 设置当前item
     * @param index
     * @param scroll
     */
    public void setCurrentItem(int index, boolean scroll) {
        mViewContainer.setCurrentItem(index, scroll);
    }

    /**
     * 设置按钮颜色
     * @param index
     */
    public void setButtonColor(int index) {
        for (int i = 0; i < 4; i++) {
            if (index == i) {
                mBtnList[i].setSelected(true);
                mBtnList[i].setTextColor(getResources().getColor(R.color.tab_sel_color));
            } else {
                mBtnList[i].setSelected(false);
                mBtnList[i].setTextColor(getResources().getColor(R.color.tab_nol_color));
            }
        }
    }

}
