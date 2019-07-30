package com.keydom.ih_doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：选择医生适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DoctorSelectRecyclrViewAdapter extends BaseEmptyAdapter<DeptDoctorBean> {


    private List<DeptDoctorBean> selectList;
    private List<DeptDoctorBean> aleardExistList = new ArrayList<>();
    private boolean selectOne = false;
    private int orderType;

    public DoctorSelectRecyclrViewAdapter(Context context, List<DeptDoctorBean> data, List<DeptDoctorBean> selectList, boolean selectOne, boolean isCancelSelected) {
        super(data, context);
        this.selectList = selectList;
        this.selectOne = selectOne;
        if (!isCancelSelected) {
            this.aleardExistList.addAll(selectList);
        }

    }


    public DoctorSelectRecyclrViewAdapter(Context context, int orderType, List<DeptDoctorBean> data, List<DeptDoctorBean> selectList, boolean selectOne, boolean isCancelSelected) {
        super(data, context);
        this.selectList = selectList;
        this.selectOne = selectOne;
        this.orderType = orderType;
        if (!isCancelSelected) {
            this.aleardExistList.addAll(selectList);
        }

    }

    private int isExist(long id) {

        for (int i = 0; i < selectList.size(); i++) {
            if (id == selectList.get(i).getId()) {
                return i;
            }
        }
        return -1;

    }

    private int isAreardExistGroup(long id) {
        for (int i = 0; i < aleardExistList.size(); i++) {
            if (id == aleardExistList.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DoctorSelectRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView doctorIcon;
        public TextView doctorName, doctorJob, selectTv;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorIcon = itemView.findViewById(R.id.doctor_icon);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorJob = itemView.findViewById(R.id.doctor_job);
            selectTv = itemView.findViewById(R.id.select_tv);

        }

        public void bind(final int position) {

            final DeptDoctorBean bean = mDatas.get(position);
            final int selectPos = isExist(bean.getId());
            final int aleardExist = isAreardExistGroup(bean.getId());
            GlideUtils.load(doctorIcon, Const.IMAGE_HOST + bean.getAvatar(), 0, 0, false, null);

            doctorJob.setText(bean.getJobTitle());
            doctorName.setText(bean.getName());
            if (selectPos != -1) {
                selectTv.setText("已选择");
                selectTv.setBackground(mContext.getResources().getDrawable(R.drawable.doctor_select_bg));
                selectTv.setTextColor(mContext.getResources().getColor(R.color.fontColorDirection));
            } else {
                selectTv.setText("选择");
                selectTv.setBackground(mContext.getResources().getDrawable(R.drawable.doctor_unselect_bg));
                selectTv.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            selectTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderType!=0&&bean.getProjectStatus() != 3 && bean.getProjectStatus() != orderType) {
                        ToastUtil.shortToast(mContext, "该医生没有开通此类型问诊服务！");
                        return;
                    }
                    if (selectOne) {
                        selectList.add(bean);
                        Intent intent = new Intent();
                        intent.putExtra(Const.DATA, (Serializable) selectList);
                        ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                        ((Activity) mContext).finish();
                    } else {
                        if (selectPos != -1) {
                            if (aleardExist != -1) {
                                ToastUtil.shortToast(mContext, "已经存在于团队的人员不可取消");
                                return;
                            }
                            selectList.remove(selectPos);
                        } else {
                            selectList.add(bean);
                        }
                        notifyDataSetChanged();
                    }


                }
            });
        }
    }
}
