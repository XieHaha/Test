package com.keydom.ih_common.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.listener.IPluginClickListener;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

public class PluginAdapter {
    private LinearLayout mIndicator = null;
    private int currentPage = 0;
    private LayoutInflater mInflater;
    private ViewGroup mPluginPager = null;
    private ArrayList<IPluginModule> mPluginModules = new ArrayList<>();

    private boolean isInitialZed = false;

    public boolean isInitialZed() {
        return isInitialZed;
    }

    private IPluginClickListener mIPluginClickListener = null;
    private HackyViewPager mViewPager = null;
    private PluginPagerAdapter mPagerAdapter = null;
    private int mPluginCountPerPage = 8;

    private int visibility;

    public int getVisibility() {
        return mPluginPager != null ? mPluginPager.getVisibility() : View.GONE;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
        if (mPluginPager != null) {
            mPluginPager.setVisibility(this.visibility);
        }
    }

    public void setOnPluginClickListener(IPluginClickListener clickListener) {
        mIPluginClickListener = clickListener;
    }

    public int getPluginPosition(IPluginModule pluginModule) {
        return mPluginModules.indexOf(pluginModule);
    }

    public IPluginModule getPluginModule(int position) {
        return (position >= 0 && position < mPluginModules.size()) ? mPluginModules.get(position) : null;
    }

    public void addPlugins(List<IPluginModule> plugins) {
        int i = 0;
        while (plugins != null && i < plugins.size()) {
            mPluginModules.add(plugins.get(i));
            ++i;
        }
    }

    public void addPlugin(IPluginModule pluginModule) {
        mPluginModules.add(pluginModule);
        int count = mPluginModules.size();
        if (mPagerAdapter != null) {
            if (count > 0 && mIndicator != null) {
                int rem = count % mPluginCountPerPage;
                if (rem > 0) {
                    rem = 1;
                }
                int pages = count / mPluginCountPerPage + rem;
                mPagerAdapter.setPages(pages);
                mPagerAdapter.setItems(count);
                mPagerAdapter.notifyDataSetChanged();
                initIndicator(pages, mIndicator);
            }
        }
    }

    public void removePlugin(IPluginModule pluginModule) {
        mPluginModules.remove(pluginModule);
        if (mPagerAdapter != null) {
            if (mViewPager != null && mIndicator != null) {
                int count = mPluginModules.size();
                if (count > 0) {
                    int rem = count % mPluginCountPerPage;
                    if (rem > 0) {
                        rem = 1;
                    }
                    int page = count / mPluginCountPerPage + rem;
                    mPagerAdapter.setPages(page);
                    mPagerAdapter.setItems(count);
                    mPagerAdapter.notifyDataSetChanged();
                    removeIndicator(page, mIndicator);
                }
            }
        }

    }

    private void initIndicator(int pages, LinearLayout indicator) {
        for (int i = 0; i < pages; i++) {
            ImageView imageView = (ImageView) mInflater.inflate(R.layout.im_ext_indicator, indicator, false);
            imageView.setImageResource(i == 0 ? R.mipmap.im_ext_indicator_hover : R.mipmap.im_ext_indicator);
            indicator.addView(imageView);
            if (pages <= 1) {
                indicator.setVisibility(View.GONE);
            } else {
                indicator.setVisibility(View.VISIBLE);
            }
        }
    }

    private void removeIndicator(int totalPages, LinearLayout indicator) {
        int index = indicator.getChildCount();
        if (index > totalPages && index - 1 >= 0) {
            indicator.removeViewAt(index - 1);
            onIndicatorChanged(index, index - 1);
            if (totalPages <= 1) {
                indicator.setVerticalGravity(View.GONE);
            }
        }

    }

    private void onIndicatorChanged(int pre, int cur) {
        if (mIndicator != null) {
            int count = mIndicator.getChildCount();
            if (count > 0 && pre < count && cur < count) {
                ImageView curView;
                if (pre >= 0) {
                    curView = (ImageView) mIndicator.getChildAt(pre);
                    curView.setImageResource(R.mipmap.im_ext_indicator);
                }
                if (cur >= 0) {
                    curView = (ImageView) mIndicator.getChildAt(cur);
                    curView.setImageResource(R.mipmap.im_ext_indicator_hover);
                }
            }
        }
    }

    public void bindView(ViewGroup viewGroup) {
        isInitialZed = true;
        initView(viewGroup.getContext(), viewGroup);
    }

    private void initView(Context context, ViewGroup viewGroup) {
        mInflater = LayoutInflater.from(context);
        mPluginPager = (ViewGroup) mInflater.inflate(R.layout.im_ext_plugin_pager, viewGroup, false);
        viewGroup.addView(mPluginPager);
        mViewPager = mPluginPager.findViewById(R.id.im_view_pager);
        mIndicator = mPluginPager.findViewById(R.id.im_indicator);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                onIndicatorChanged(currentPage, position);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        int pages = 0;
        int count = mPluginModules.size();
        if (count > 0) {
            int rem = count % mPluginCountPerPage;
            if (rem > 0) {
                rem = 1;
            }
            pages = count / mPluginCountPerPage + rem;
        }
        mPagerAdapter = new PluginPagerAdapter(pages, count);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        initIndicator(pages, mIndicator);
        onIndicatorChanged(-1, 0);
    }

    class PluginPagerAdapter extends PagerAdapter {

        private int pages;
        private int items;

        public PluginPagerAdapter(int pages, int items) {
            this.pages = pages;
            this.items = items;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            GridView gridView = (GridView) mInflater.inflate(R.layout.im_ext_plugin_grid_view, container, false);
            gridView.setAdapter(new PluginItemAdapter(position * mPluginCountPerPage, items));
            container.addView(gridView);
            if (mViewPager != null) {
                mViewPager.resetHeight(gridView);
            }
            return gridView;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return -2;
        }

        @Override
        public int getCount() {
            return pages;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View layout = (View) object;
            container.removeView(layout);
        }

        void setPages(int value) {
            pages = value;
        }

        void setItems(int value) {
            items = value;
        }
    }

    private class PluginItemAdapter extends BaseAdapter {

        private int index;
        private int count;

        public PluginItemAdapter(int index, int count) {
            this.index = index;
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Context context = parent.getContext();
            ViewHolder holder;
            if (view == null) {
                view = mInflater.inflate(R.layout.im_ext_plugin_item, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IPluginModule plugin = mPluginModules.get(currentPage * mPluginCountPerPage + position);
                    if (mIPluginClickListener != null) {
                        mIPluginClickListener.onClick(plugin, currentPage * mPluginCountPerPage + position);
                    }
                }
            });
            IPluginModule plugin = mPluginModules.get(position + index);
            holder.imageView.setImageDrawable(plugin.obtainDrawable(context));
            holder.textView.setText(plugin.obtainTitle(context));
            return view;
        }

        class ViewHolder {
            View convertView;
            ImageView imageView;
            TextView textView;

            public ViewHolder(View convertView) {
                this.convertView = convertView;
                imageView = convertView.findViewById(R.id.im_ext_plugin_icon);
                textView = convertView.findViewById(R.id.im_ext_plugin_title);
            }
        }
    }
}