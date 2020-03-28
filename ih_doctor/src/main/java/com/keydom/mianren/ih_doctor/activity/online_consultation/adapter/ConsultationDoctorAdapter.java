package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;

import java.util.ArrayList;

/**
 * @date 20/3/28 13:50
 * @des 会诊医生
 */
public class ConsultationDoctorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data = new ArrayList<>();

    public ConsultationDoctorAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_consultation_doctor,
                    parent, false);
            holder = new ViewHolder();
            holder.header = convertView.findViewById(R.id.item_consultation_doctor_header_iv);
            holder.tvName = convertView.findViewById(R.id.item_consultation_doctor_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(data.get(position));
        return convertView;
    }

    class ViewHolder {
        private ImageView header;
        private TextView tvName;
    }

}
