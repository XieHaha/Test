package com.keydom.ih_doctor.activity.nurse_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.CategoryBean;

import java.util.List;

public interface ChooseNursingView extends BaseView {

    void getCotegorySuccess(List<CategoryBean> list);

    void getCotegoryFailed(String errMsg);
}
