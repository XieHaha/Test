package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ConsultationDoctorBean;

import java.util.ArrayList;

/**
 * @date 20/3/28 13:50
 * @des 会诊医生
 */
public class ConsultationDoctorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ConsultationDoctorBean> data = new ArrayList<>();

    public ConsultationDoctorAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ConsultationDoctorBean> data) {
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

        holder.tvName.setText(data.get(position).getName());
        GlideUtils.load(holder.header,
                BaseFileUtils.getHeaderUrl(data.get(position).getDoctorImage()), 0,
                R.mipmap.im_default_head_image, true, null);
        return convertView;
    }

    class ViewHolder {
        private ImageView header;
        private TextView tvName;
    }

}
