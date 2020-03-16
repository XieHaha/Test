package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.ChooseCityActivity;
import com.keydom.mianren.ih_patient.bean.CityBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.DepartmentDataHelper;

import java.util.List;

/**
 * 选择城市适配器
 */
public class ChooseCityAdapter extends RecyclerView.Adapter {
    private List<Object> dataList;
    private Context context;
    private int HEADER = 1, BODY = 2;

    /**
     * 构造方法
     */
    public ChooseCityAdapter(List<Object> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.choose_city_list_head_item, parent, false);
            return new ChooseCityAdapter.HeaderViewHolder(view);
        } else if (viewType == BODY) {
            view = LayoutInflater.from(context).inflate(R.layout.choose_city_list_body_iem, parent, false);
            return new ChooseCityAdapter.BodyViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ChooseCityAdapter.HeaderViewHolder) {
            final ChooseCityAdapter.HeaderViewHolder headerViewHolder = (ChooseCityAdapter.HeaderViewHolder) holder;
            final DepartmentDataHelper.CityHeader cityHeader = (DepartmentDataHelper.CityHeader) dataList.get(position);
            headerViewHolder.head_tv.setText(cityHeader.getHeaderName());
        } else if (holder instanceof ChooseCityAdapter.BodyViewHolder) {
            final ChooseCityAdapter.BodyViewHolder bodyViewHolder = (ChooseCityAdapter.BodyViewHolder) holder;
            final CityBean.itemBean itemBean= (CityBean.itemBean) dataList.get(position);
            bodyViewHolder.body_tv.setText(itemBean.getName());
            bodyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.setSelectedCityName(itemBean.getName());
                    Global.setSelectedCityCode(itemBean.getCode());
                    ((ChooseCityActivity)context).finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof DepartmentDataHelper.CityHeader)
            return HEADER;
        else
            return BODY;


    }

    static class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView body_tv;

        BodyViewHolder(View view) {
            super(view);
            body_tv = view.findViewById(R.id.body_tv);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView head_tv;

        HeaderViewHolder(View view) {
            super(view);
            head_tv = view.findViewById(R.id.head_tv);
        }
    }
}
