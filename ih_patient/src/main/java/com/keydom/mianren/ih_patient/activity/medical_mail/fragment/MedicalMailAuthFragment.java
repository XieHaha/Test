package com.keydom.mianren.ih_patient.activity.medical_mail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_mail.controller.MedicalMailAuthController;
import com.keydom.mianren.ih_patient.activity.medical_mail.view.MedicalMailAuthView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.IdCardBean;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.FileUtil;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.view.MedicalAuthLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 病案邮寄-身份认证
 */
public class MedicalMailAuthFragment extends BaseControllerFragment<MedicalMailAuthController> implements MedicalMailAuthView {
    @BindView(R.id.tv_select_visit)
    TextView tvSelectVisit;
    @BindView(R.id.layout_front)
    MedicalAuthLayout layoutFront;
    @BindView(R.id.layout_back)
    MedicalAuthLayout layoutBack;
    @BindView(R.id.layout_hand)
    MedicalAuthLayout layoutHand;
    @BindView(R.id.et_name)
    InterceptorEditText etName;
    @BindView(R.id.et_id_card)
    InterceptorEditText etIdCard;
    @BindView(R.id.et_phone)
    InterceptorEditText etPhone;
    @BindView(R.id.tv_next)
    TextView tvNext;

    /**
     * 输入数据
     */
    private MedicalMailApplyBean applyBean;
    /**
     * 所选就诊人信息
     */
    private ManagerUserBean userBean;
    private IdCardBean mResultBean = new IdCardBean();

    private AlertDialog.Builder alertDialog;

    private String frontUrl, backUrl, handUrl;

    private long timeMillis = 0;
    private long backTimeMillis = 0;

    private static final int REQUEST_CODE_CAMERA = 102;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_auth;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initOCR();
        alertDialog = new AlertDialog.Builder(getContext());
        tvSelectVisit.setOnClickListener(getController());
        layoutFront.setOnClickListener(getController());
        layoutBack.setOnClickListener(getController());
        layoutHand.setOnClickListener(getController());
        tvNext.setOnClickListener(getController());
    }

    @Override
    public String getFrontUrl() {
        return frontUrl;
    }

    @Override
    public String getBackUrl() {
        return backUrl;
    }

    @Override
    public String getHandUrl() {
        return handUrl;
    }

    @Override
    public String getName() {
        return etName.getText().toString();
    }

    @Override
    public String getIdCard() {
        return etIdCard.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public MedicalMailApplyBean getApplyData() {
        return applyBean;
    }

    @Override
    public void goToIdCardFrontDiscriminate() {
        LocalizationUtils.deletePicFileFromLocal(getContext(), "IdCardFront" + timeMillis);
        timeMillis = System.currentTimeMillis();
        String name = "IdCardFront" + timeMillis;
        Intent intent = new Intent(getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getContext(), name).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void goToIdCardBackDiscriminate() {
        LocalizationUtils.deletePicFileFromLocal(getContext(), "IdCardBack" + backTimeMillis);
        backTimeMillis = System.currentTimeMillis();
        String name = "IdCardBack" + backTimeMillis;
        Intent intent = new Intent(getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getContext(), name).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void goToIdCardHandDiscriminate() {
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage()).forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisitPeopleSelect(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            if (event.getData() != null) {
                userBean = (ManagerUserBean) event.getData();
                bindUserData();
            }
        }
    }

    private void bindUserData() {
        etName.setText(userBean.getName());
        etIdCard.setText(userBean.getCardId());
        etPhone.setText(userBean.getPhone());
    }

    @Override
    public void uploadImgSuccess(String data, String type) {
        if (TextUtils.isEmpty(type)) {
            handUrl = data;
            layoutHand.setContent(R.string.txt_id_card_reload);
        } else if (type.equals("positive")) {
            frontUrl = data;
            layoutFront.setContent(R.string.txt_id_card_reload);
        } else {
            backUrl = data;
            layoutBack.setContent(R.string.txt_id_card_reload);
        }
    }

    @Override
    public void uploadImgFailed(String msg, String type) {
        if (TextUtils.isEmpty(type)) {
            ToastUtil.showMessage(getContext(), "证件照片三上传失败：" + msg + "请重新上传");
            layoutHand.setContent("[上传失败,重新上传]");
        } else if (type.equals("positive")) {
            ToastUtil.showMessage(getContext(), "证件照片一上传失败：" + msg + "请重新上传");
            layoutFront.setContent("[上传失败,重新上传]");
        } else {
            ToastUtil.showMessage(getContext(), "证件照片二上传失败：" + msg + "请重新上传");
            layoutBack.setContent("[上传失败,重新上传]");
        }
    }

    @Override
    public long getCurUserId() {
        return userBean == null ? -1 : userBean.getId();
    }

    /**
     * 身份证识别
     */
    private void initOCR() {
        OCR.getInstance(getContext()).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                /**
                 *  初始化本地质量控制模型
                 *  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
                 *  关闭自动初始化和释放本地模型
                 */
                CameraNativeHelper.init(getContext(), OCR.getInstance(getContext()).getLicense(),
                        (errorCode, e) -> {
                            String msg;
                            switch (errorCode) {
                                case CameraView.NATIVE_SOLOAD_FAIL:
                                    msg = "加载so失败，请确保apk中存在ui部分的so";
                                    break;
                                case CameraView.NATIVE_AUTH_FAIL:
                                    msg = "授权本地质量控制token获取失败";
                                    break;
                                case CameraView.NATIVE_INIT_FAIL:
                                    msg = "本地质量控制";
                                    break;
                                default:
                                    msg = String.valueOf(errorCode);
                            }
                        });
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getContext());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        String filePath = FileUtil.getSaveFile(getContext(), "IdCardFront"
                                + timeMillis).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        List<String> selectFrontList = new ArrayList<>();
                        selectFrontList.add(filePath);
                        layoutFront.setImage(filePath);
                        getController().upLoadPic(filePath, "positive");
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        String filePath = FileUtil.getSaveFile(getContext(),
                                "IdCardBack" + backTimeMillis).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        List<String> selectBackList = new ArrayList<>();
                        selectBackList.add(filePath);
                        layoutBack.setImage(filePath);
                        getController().upLoadPic(filePath, "back");
                    }
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                String filePath = selectList.get(0).getPath();
                layoutHand.setImage(filePath);
                getController().upLoadPic(filePath, null);
                break;
            default:
                break;
        }
    }


    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        OCR.getInstance(getContext()).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT)) {
                        if (result.getAddress() != null) {
                            mResultBean.setAddress(result.getAddress().toString());
                        } else {
                            mResultBean.setAddress("");
                        }
                        if (result.getName() != null) {
                            mResultBean.setName(result.getName().toString());
                            etName.setText(result.getName().toString());
                        } else {
                            mResultBean.setName("");
                        }
                        if (result.getIdNumber() != null) {
                            mResultBean.setIdNumber(result.getIdNumber().toString());
                            etIdCard.setText(result.getIdNumber().toString());
                        } else {
                            mResultBean.setIdNumber("");
                        }
                        if (result.getGender() != null) {
                            mResultBean.setGender(result.getGender().toString());
                        } else {
                            mResultBean.setGender("");
                        }
                        if (result.getEthnic() != null) {
                            mResultBean.setEthnic(result.getEthnic().toString());
                        } else {
                            mResultBean.setEthnic("");
                        }
                        if (result.getBirthday() != null) {
                            mResultBean.setBirthday(result.getBirthday().toString());
                        } else {
                            mResultBean.setBirthday("");
                        }
                    } else {
                        if (result.getExpiryDate() != null)
                            mResultBean.setExpiryDate(result.getExpiryDate().toString());
                        else {
                            mResultBean.setExpiryDate("");
                        }
                        if (result.getSignDate() != null) {
                            mResultBean.setSignDate(result.getSignDate().toString());
                        } else {
                            mResultBean.setSignDate("");
                        }
                    }
                }
            }

            @Override

            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }

    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(() -> alertDialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
