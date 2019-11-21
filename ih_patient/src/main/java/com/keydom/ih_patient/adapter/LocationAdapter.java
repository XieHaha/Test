package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.location_manage.AddLocationActivity;
import com.keydom.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.LocationService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 位置适配器
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.VH> {
    private LocationManageActivity context;
    private List<LocationInfo> infoList;
    private String type;
    /**
     * 构造方法
     */
    public LocationAdapter(LocationManageActivity context, List<LocationInfo> infoList, String type) {
        this.context = context;
        this.infoList = infoList;
        this.type = type;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        holder.addressTv.setText(infoList.get(position).getProvinceName() + infoList.get(position).getCityName() + infoList.get(position).getAreaName() + infoList.get(position).getAddress());
        holder.nameTv.setText(infoList.get(position).getAddressName());
        holder.phoneTv.setText(infoList.get(position).getPhone());
        if (infoList.get(position).getIsdefault() == 1) {
            holder.setDefaultCb.setChecked(true);
        } else {
            holder.setDefaultCb.setChecked(false);
        }

        if (infoList.get(position).getIsdefault() == 1) {
            holder.setDefaultCb.setTextColor(context.getResources().getColor(R.color.register_success_color));
            holder.setDefaultCb.setClickable(false);
            holder.checkedDefaultImg.setVisibility(View.VISIBLE);
        } else {
            holder.setDefaultCb.setTextColor(context.getResources().getColor(R.color.fontColorDirection));
            holder.setDefaultCb.setClickable(true);
            holder.checkedDefaultImg.setVisibility(View.GONE);
        }
        holder.setDefaultCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    /*for (int i = 0; i < infoList.size(); i++) {
                        infoList.get(i).setIsdefault(0);
                    }
                    infoList.get(position).setIsdefault(1);*/
                    if(! holder.setDefaultCb.isPressed())return;
                    uploadDefault(infoList.get(position).getId());
                }

            }
        });
        holder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event(EventType.SENDLOCATIONINFO, infoList.get(position));
                EventBus.getDefault().postSticky(event);
                AddLocationActivity.start(context, "edit_location");
            }
        });
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GeneralDialog(context, "确认要删除该地址？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        uploadDelete(infoList.get(position).getId(), position);
                    }
                }).setTitle("提示").setPositiveButton("确认").show();

            }
        });
        if (Type.STARTLOCATIONWITHRESULT.equals(type) || Type.PAY_SELECT_ADDRESS.equals(type)||Type.WAI_PAY_SELECT_ADDRESS.equals(type)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Type.PAY_SELECT_ADDRESS.equals(type)) {
                        EventBus.getDefault().post(new Event(EventType.PAY_SELECT_ADDRESS, infoList.get(position)));
                    } else if(Type.WAI_PAY_SELECT_ADDRESS.equals(type)){
                        EventBus.getDefault().post(new Event(EventType.WAI_PAY_SELECT_ADDRESS, infoList.get(position)));
                    } else {
                        EventBus.getDefault().post(new Event(EventType.SENDLOCATION, infoList.get(position)));
                    }
                    context.finish();
                }
            });
        }
    }
    /**
     * 删除
     */
    private void uploadDelete(long id, int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).deleteAddress(map), new HttpSubscriber<Object>() {
            @Override
            public void requestComplete(@Nullable Object data) {
                //EventBus.getDefault().post(new Event(EventType.RETURNLOCATIONINFO,null));
                infoList.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }
    /**
     * 设置默认
     */
    private void uploadDefault(long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("id", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).updateDefault(map), new HttpSubscriber<Object>() {
            @Override
            public void requestComplete(@Nullable Object data) {
                EventBus.getDefault().post(new Event(EventType.RETURNLOCATIONINFO, null));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(context, "设置默认地址失败，请稍后重试。");
                notifyDataSetChanged();
                return super.requestError(exception, code, msg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView nameTv, phoneTv, addressTv, editTv, deleteTv;
        private CheckBox setDefaultCb;
        private ImageView checkedDefaultImg;

        public VH(View v) {
            super(v);
            nameTv = v.findViewById(R.id.location_name_tv);
            phoneTv = v.findViewById(R.id.location_phone_tv);
            addressTv = v.findViewById(R.id.location_address_tv);
            editTv = v.findViewById(R.id.location_manage_edit);
            deleteTv = v.findViewById(R.id.location_manage_delete);
            setDefaultCb = v.findViewById(R.id.location_manage_default_cb);
            checkedDefaultImg = v.findViewById(R.id.checked_default_img);
        }
    }
}
