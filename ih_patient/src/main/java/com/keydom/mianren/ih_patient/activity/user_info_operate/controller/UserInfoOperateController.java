package com.keydom.mianren.ih_patient.activity.user_info_operate.controller;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.certification.CertificateActivity;
import com.keydom.mianren.ih_patient.activity.upload_certificate_picture.UploadCertificatePictureActivity;
import com.keydom.mianren.ih_patient.activity.user_info_operate.view.UserInfoOperateView;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UploadService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.NoFastClickUtils;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人信息控制器
 */
public class UserInfoOperateController extends ControllerImpl<UserInfoOperateView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head_img:
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.user_name_tv:
                showNameEditDialog();
                break;
            case R.id.user_sex_tv:
                final List<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                int defaultPosition = 0;
                if (getView().getSex().equals("0"))
                    defaultPosition = 0;
                else
                    defaultPosition = 1;
                OptionsPickerView sexPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        Map<String, Object> sexMap = new HashMap<>();
                        sexMap.put("id", Global.getUserId());
                        if (sexList.get(options1).equals("男")) {
                            sexMap.put("sex", "0");
                        } else {
                            sexMap.put("sex", "1");
                        }
                        upLoadInfo(sexMap);
                        getView().setSex(sexList.get(options1));
                    }
                }).setSelectOptions(defaultPosition).build();
                sexPickerView.setPicker(sexList);
                sexPickerView.show();
                break;
            case R.id.user_real_name_status_tv:
                CertificateActivity.start(getContext(), "id_card_certificate");
                break;
            case R.id.user_region_tv:
                SelectDialogUtils.showRegionSelectDialog(getContext(), getView().getProvinceName(), getView().getCityName(), getView().getAreaName(), new GeneralCallback.SelectRegionListener() {
                    @Override
                    public void getSelectedRegion(List<PackageData.ProvinceBean> data, int position1, int position2, int position3) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", Global.getUserId());
                        if (data.get(position1).getCity().size() == 0) {
                            Logger.e("省有-->");
                            getView().getRegion(data.get(position1));
                            map.put("provinceCode", data.get(position1).getCode());
                            upLoadInfo(map);
                        } else if (data.get(position1).getCity().get(position2).getArea().size() == 0) {
                            getView().getRegion(data.get(position1), position2);
                            map.put("provinceCode", data.get(position1).getCode());
                            map.put("cityCode", data.get(position1).getCity().get(position2).getCode());
                            upLoadInfo(map);
                        } else {
                            getView().getRegion(data.get(position1), position2, position3);
                            map.put("provinceCode", data.get(position1).getCode());
                            map.put("cityCode", data.get(position1).getCity().get(position2).getCode());
                            map.put("countyCode", data.get(position1).getCity().get(position2).getArea().get(position3).getCode());
                            upLoadInfo(map);
                        }
                    }
                });
                break;
            case R.id.user_phone_tv:
                CertificateActivity.start(getContext(), "phone_certificate");
                break;
            case R.id.user_country_tv:
                if (NoFastClickUtils.isFastClick()) {
                    findAllCountry();
                } else
                    ToastUtil.showMessage(getContext(), "请勿频繁点击");
                break;
            case R.id.user_nation_tv:
                if (NoFastClickUtils.isFastClick()) {
                    SelectDialogUtils.showNationSelectDialog(getContext(), getView().getNation(), new GeneralCallback.SelectNationListener() {
                        @Override
                        public void getSelectedNation(PackageData.NationBean nationBean) {
                            Map<String, Object> nationMap = new HashMap<>();
                            nationMap.put("id", Global.getUserId());
                            nationMap.put("nation", nationBean.getNotionCode());
                            upLoadInfo(nationMap);
                            getView().setNation(nationBean.getNationName());
                        }
                    });
                } else
                    ToastUtil.showMessage(getContext(), "请勿频繁点击");

                break;
            case R.id.user_id_card_pic_tv:
                UploadCertificatePictureActivity.start(getContext(), "user_info");
                break;
            default:

        }
    }

    /**
     * 显示名称编辑弹框
     */
    private void showNameEditDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.name_edit_dialog_bg, null, false);
        bottomSheetDialog.setContentView(view);
        final InterceptorEditText nameEdt = view.findViewById(R.id.name_edt);
        TextView commitTv = view.findViewById(R.id.edt_commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEdt.getText().toString().trim().length() > 64) {
                    ToastUtil.showMessage(getContext(), "姓名最多支持64位长度，请重新输入");
                } else if (nameEdt.getText().toString().trim().length() == 0) {
                    ToastUtil.showMessage(getContext(), "请输入姓名");
                } else {


                    //getView().setName(nameEdt.getText().toString().trim());
                    Map<String, Object> nameMap = new HashMap<>();
                    nameMap.put("id", Global.getUserId());
                    nameMap.put("userName", nameEdt.getText().toString().trim());
                    upLoadInfo(nameMap);
                    bottomSheetDialog.dismiss();
                }

            }
        });
        bottomSheetDialog.show();

    }

    /**
     * 上传所在地
     */
    private void upLoadRegion(String provinceCode, String cityCode, String areaCode) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).upLoadRegion(String.valueOf(Global.getUserId()), provinceCode, cityCode, areaCode), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().reloadData();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 更新信息
     */
    public void upLoadInfo(Map<String, Object> map) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).upLoadInfo(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
//                if (map.containsKey("sex")) {
//                    ImClient.updateUserInfo(UserInfoFieldEnum.GENDER, Integer.valueOf(map.get("sex").toString()), null);
//                } else if (map.containsKey("userName")) {
//                    ImClient.updateUserInfo(UserInfoFieldEnum.Name, map.get("userName").toString(), null);
//                }
                hideLoading();
                getView().reloadData();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtil.showMessage(getContext(),msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 查询所有国籍并打开选择器
     */
    public void findAllCountry() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getCountryList(), new HttpSubscriber<List<PackageData.CountryBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable final List<PackageData.CountryBean> data) {
                int defaultPosition = 0;
                final List<String> countryList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    countryList.add(data.get(i).getNationName());
                    if (getView().getCountry() != null)
                        if (getView().getCountry().equals(data.get(i).getNationName()))
                            defaultPosition = i;
                }
                OptionsPickerView countryPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        Map<String, Object> countryMap = new HashMap<>();
                        countryMap.put("id", Global.getUserId());
                        countryMap.put("country", data.get(options1).getNationCode());
                        upLoadInfo(countryMap);
                        getView().setCountry(countryList.get(options1));
                    }
                }).setSelectOptions(defaultPosition).build();
                countryPickerView.setPicker(countryList);
                countryPickerView.show();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(getContext(), "获取国籍列表失败：" + msg + "，请稍后重试");
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 刷新个人数据
     */
    public void initUserData() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).initUserData(String.valueOf(Global.getUserId())), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                hideLoading();
                getView().initUserData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 上传图片
     */
    public void upLoadPic(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UploadService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().uploadImgSuccess(data);
//                ImClient.updateUserInfo(UserInfoFieldEnum.AVATAR, data, null);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadImgFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
