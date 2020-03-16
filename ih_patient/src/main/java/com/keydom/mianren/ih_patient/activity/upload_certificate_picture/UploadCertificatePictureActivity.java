package com.keydom.mianren.ih_patient.activity.upload_certificate_picture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.upload_certificate_picture.controller.UploadCertificatePictureController;
import com.keydom.mianren.ih_patient.activity.upload_certificate_picture.view.UploadCertificatePictureView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.IdCardBean;
import com.keydom.mianren.ih_patient.bean.event.CertificateSuccess;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.FileUtil;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 身份认证页面
 */
public class UploadCertificatePictureActivity extends BaseControllerActivity<UploadCertificatePictureController> implements UploadCertificatePictureView {
    private String type, positiveUrl, backUrl;
    private TextView upload_certificate_pic_title, upload_certificate_pic_commit, upload_pic_positive_tv, upload_pic_reverse_tv;
    private ImageView pic_positive_img, pic_reverse_img;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;
    private long timeMillis = 0;
    private long backTimeMillis = 0;
    private AlertDialog.Builder alertDialog;
    private IdCardBean ResultBean = new IdCardBean();

    /**
     * 启动
     */
    public static void start(Context context, String type) {
        Intent intent = new Intent(context, UploadCertificatePictureActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_upload_certificate_picture_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("身份认证");
        type = getIntent().getStringExtra("type");
        getController().getType(type);
        alertDialog = new AlertDialog.Builder(this);
        pic_positive_img = this.findViewById(R.id.pic_positive_img);
        pic_positive_img.setOnClickListener(getController());
        pic_reverse_img = this.findViewById(R.id.pic_reverse_img);
        pic_reverse_img.setOnClickListener(getController());
        upload_pic_positive_tv = this.findViewById(R.id.upload_pic_positive_tv);
        upload_pic_positive_tv.setOnClickListener(getController());
        upload_pic_reverse_tv = this.findViewById(R.id.upload_pic_reverse_tv);
        upload_pic_reverse_tv.setOnClickListener(getController());
        upload_certificate_pic_title = this.findViewById(R.id.upload_certificate_pic_title);
        upload_certificate_pic_commit = this.findViewById(R.id.upload_certificate_pic_commit);
        upload_certificate_pic_commit.setOnClickListener(getController());
        switch (type) {
            case "card_id_card":
                upload_pic_positive_tv.setText("[上传正面照]");
                upload_pic_reverse_tv.setText("[上传反面照]");
                upload_certificate_pic_title.setText("请上传您的身份证证件照片");
                upload_certificate_pic_commit.setText("下一步");
                /**
                 *  初始化本地质量控制模型
                 *  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
                 */
                CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                        new CameraNativeHelper.CameraNativeInitCallback() {
                            @Override
                            public void onError(int errorCode, Throwable e) {
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //ToastUtil.showMessage(getApplicationContext(), "本地质量控制初始化错误，错误原因： " + msg);
                                    }
                                });

                            }
                        });
                break;
            case "card_other_certificate":
                upload_pic_positive_tv.setText("[上传户口本主页]");
                upload_pic_reverse_tv.setText("[上传本人信息页]");
                upload_certificate_pic_title.setText("请上传您的户口本证件照片");
                upload_certificate_pic_commit.setText("下一步");
                break;
            case "user_info":
                upload_pic_positive_tv.setText("[上传正面照]");
                upload_pic_reverse_tv.setText("[上传反面照]");
                upload_certificate_pic_title.setText("请上传您的身份证证件照片");
                upload_certificate_pic_commit.setText("提交");
                break;
            default:
                break;
        }

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void sendPicUrl() {
        List<String> urlList = new ArrayList<>();
        if (positiveUrl != null && !"".equals(positiveUrl)) {
            urlList.add(positiveUrl);
        }
        if (backUrl != null && !"".equals(backUrl)) {
            urlList.add(backUrl);
        }
        if (urlList.size() == 2) {
            Event event = new Event(EventType.SENDPICURL, urlList);
            EventBus.getDefault().post(event);
            finish();
        } else {
            ToastUtil.showMessage(getContext(), "证件图片上传未完成，请检查并完成上传");
        }

    }

    @Override
    public void uploadImgSuccess(String data, String type) {
        if (type.equals("positive")) {
            positiveUrl = data;
            upload_pic_positive_tv.setText("[重新上传]");
        } else {
            backUrl = data;
            upload_pic_reverse_tv.setText("[重新上传]");
        }
    }

    @Override
    public void uploadImgFailed(String msg, String type) {
        if (type.equals("positive")) {
            ToastUtil.showMessage(getContext(), "证件照片一上传失败：" + msg + "请重新上传");
            upload_pic_positive_tv.setText("[上传失败,重新上传]");
        } else {
            ToastUtil.showMessage(getContext(), "证件照片二上传失败：" + msg + "请重新上传");
            upload_pic_reverse_tv.setText("[上传失败,重新上传]");
        }
    }

    @Override
    public ArrayList<String> getUrlList() {
        ArrayList<String> urlList = new ArrayList<>();
        if (positiveUrl != null && !"".equals(positiveUrl)) {
            urlList.add(positiveUrl);
        }
        if (backUrl != null && !"".equals(backUrl)) {
            urlList.add(backUrl);
        }
        return urlList;
    }

    @Override
    public void goToIdCardFrontDiscriminate() {
        LocalizationUtils.deletePicFileFromLocal(getContext(), "IdCardFront" + timeMillis);
        timeMillis = System.currentTimeMillis();
        String name = "IdCardFront" + timeMillis;
        Intent intent = new Intent(getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication(), name).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
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
                FileUtil.getSaveFile(getApplication(), name).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public IdCardBean getResult() {
        return ResultBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    pic_positive_img.setPadding(0, 0, 0, 0);
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    Glide.with(getContext()).load(new File(selectList.get(0).getPath())).into(pic_positive_img);
                    getController().upLoadPic(selectList.get(0).getPath(), "positive");
                    break;
                case PictureConfig.REQUEST_CAMERA:
                    pic_reverse_img.setPadding(0, 0, 0, 0);
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    Glide.with(getContext()).load(new File(selectList1.get(0).getPath())).into(pic_reverse_img);
                    getController().upLoadPic(selectList1.get(0).getPath(), "back");
                    break;
                case REQUEST_CODE_CAMERA:
                    if (data != null) {
                        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                        if (!TextUtils.isEmpty(contentType)) {
                            if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                                String filePath = FileUtil.getSaveFile(getApplicationContext(), "IdCardFront" + timeMillis).getAbsolutePath();
                                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                                List<String> selectFrontList = new ArrayList<>();
                                selectFrontList.add(filePath);
                                Glide.with(getContext()).load(filePath).into(pic_positive_img);
                                getController().upLoadPic(filePath, "positive");
                            } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                                String filePath = FileUtil.getSaveFile(getApplicationContext(), "IdCardBack" + backTimeMillis).getAbsolutePath();
                                recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                                List<String> selectBackList = new ArrayList<>();
                                selectBackList.add(filePath);
                                Glide.with(getContext()).load(filePath).into(pic_reverse_img);
                                getController().upLoadPic(filePath, "back");
                            }
                        }
                    }
                    break;
            }
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

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
//                    alertText("", result.toString());
                    if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT)) {
                        if (result.getAddress() != null)
                            ResultBean.setAddress(result.getAddress().toString());
                        else
                            ResultBean.setAddress("");
                        if (result.getName() != null)
                            ResultBean.setName(result.getName().toString());
                        else
                            ResultBean.setName("");
                        if (result.getIdNumber() != null)
                            ResultBean.setIdNumber(result.getIdNumber().toString());
                        else
                            ResultBean.setIdNumber("");
                        if (result.getGender() != null)
                            ResultBean.setGender(result.getGender().toString());
                        else
                            ResultBean.setGender("");
                        if (result.getEthnic() != null)
                            ResultBean.setEthnic(result.getEthnic().toString());
                        else
                            ResultBean.setEthnic("");
                        if (result.getBirthday() != null)
                            ResultBean.setBirthday(result.getBirthday().toString());
                        else
                            ResultBean.setBirthday("");
                    } else {
                        if (result.getExpiryDate() != null)
                            ResultBean.setExpiryDate(result.getExpiryDate().toString());
                        else
                            ResultBean.setExpiryDate("");
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
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }


    /**
     * 认证成功
     *
     * @param success 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCertificateSuccess(CertificateSuccess success) {
        finish();
    }
}
