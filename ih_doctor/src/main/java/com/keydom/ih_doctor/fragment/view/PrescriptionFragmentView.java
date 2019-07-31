package com.keydom.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.PrescriptionBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface PrescriptionFragmentView extends BaseView {
    void getDataSuccess(TypeEnum type, List<PrescriptionBean> prescriptions);
    void getDataFailed(String errMsg);

    /**
     * 判断是已审核还是未审核
     * @return
     */
    TypeEnum getType();

    Map<String ,Object> getListMap();

    String getStartCod();
}
