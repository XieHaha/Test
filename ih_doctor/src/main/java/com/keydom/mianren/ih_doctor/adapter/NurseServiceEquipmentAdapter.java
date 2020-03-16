package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DetailEquipment;
import com.keydom.mianren.ih_doctor.bean.NursingPatientEquipmentItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：耗材列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NurseServiceEquipmentAdapter extends RecyclerView.Adapter<NurseServiceEquipmentAdapter.ViewHolder> {


    private Context context;
    private List<NursingPatientEquipmentItem> data;

    public NurseServiceEquipmentAdapter(Context context, List<NursingPatientEquipmentItem> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nurse_service_equipment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final NursingPatientEquipmentItem bean = data.get(position);
        holder.totalFee.setText("￥" + (new BigDecimal(bean.getTotalMoney())).setScale(2, BigDecimal.ROUND_DOWN));
        holder.equipmentTimes.setText("第" + CommonUtils.numberToChinese(bean.getServiceFrequency()) + "次器材/耗材耗用");
        holder.equipmentBox.removeAllViews();
        if (bean.getDetailEquipment() != null) {
            for (int i = 0; i < bean.getDetailEquipment().size(); i++) {
                View view = getDrugView();
                DetailEquipment detailEquipment = bean.getDetailEquipment().get(i);
                TextView drugNum, drugName, drugSpecifications, drugQuantity, drugFee;
                drugNum = view.findViewById(R.id.drug_num);
                drugName = view.findViewById(R.id.drug_name);
                drugSpecifications = view.findViewById(R.id.drug_specifications);
                drugQuantity = view.findViewById(R.id.drug_quantity);
                drugFee = view.findViewById(R.id.drug_fee);
                drugNum.setText((i + 1) + "、");
                drugName.setText(detailEquipment.getEquipmentName());
                drugQuantity.setText(detailEquipment.getQuantity() + (detailEquipment.getUnitName() == null ? "" : detailEquipment.getUnitName()));
                drugSpecifications.setText(detailEquipment.getDescription());
                drugFee.setText(detailEquipment.getPrice() + "元");
                holder.equipmentBox.addView(view);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView equipmentTimes, totalFee;
        public LinearLayout equipmentBox;

        public ViewHolder(View itemView) {
            super(itemView);
            equipmentTimes = itemView.findViewById(R.id.equipment_times);
            totalFee = itemView.findViewById(R.id.total_fee);
            equipmentBox = itemView.findViewById(R.id.equipment_box);
        }
    }

    public View getDrugView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.finish_nurse_service_drug_item, null, true);
        return view;
    }
}
