package com.keydom.ih_patient.activity.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.certification.controller.CertificateController;
import com.keydom.ih_patient.activity.certification.view.CertificateView;
import com.keydom.ih_patient.activity.new_card.NewCardActivity;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.IdCardBean;
import com.keydom.ih_patient.bean.event.CertificateSuccess;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.FileUtil;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 短信验证和身份证实名认证页面
*/
public class CertificateActivity extends BaseControllerActivity<CertificateController> implements CertificateView {
    private LinearLayout id_card_certificate_layout,phone_certificate_layout;

    //启动类型 phone_certificate 短信验证  id_card_certificate 实名认证
    private String type;

    private EditText id_card_name_edt,id_card_num_edt,phone_num_edt,message_edt;
    private MButton get_message_bt;


    //身份证上传
    private LinearLayout mUploadRootLl;
    private ImageView pic_positive_img, pic_reverse_img;
    private TextView  upload_certificate_pic_commit, upload_pic_positive_tv, upload_pic_reverse_tv;

    private static final int REQUEST_CODE_CAMERA = 102;
    private IdCardBean mResultBean = new IdCardBean();
    private long timeMillis = 0;
    private long backTimeMillis = 0;
    private String positiveUrl;
    private String backUrl;

    private AlertDialog.Builder alertDialog;


    /**
     * 启动页面
     */
    public static void start(Context context,String type){
        Intent intent=new Intent(context,CertificateActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_certificate_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type=getIntent().getStringExtra("type");
        getController().setType(type);
        getTitleLayout().initViewsVisible(true,true,true);
        getTitleLayout().setRightTitle("提交");
        alertDialog = new AlertDialog.Builder(this);
        mUploadRootLl=this.findViewById(R.id.certificate_layout_photot_upload_root_ll);
        id_card_certificate_layout=this.findViewById(R.id.id_card_certificate_layout);
        phone_certificate_layout=this.findViewById(R.id.phone_certificate_layout);
        id_card_name_edt=this.findViewById(R.id.id_card_name_edt);
        id_card_num_edt=this.findViewById(R.id.id_card_num_edt);
        phone_num_edt=this.findViewById(R.id.phone_num_edt);
        message_edt=this.findViewById(R.id.message_edt);
        get_message_bt=this.findViewById(R.id.get_message_bt);
        pic_positive_img = this.findViewById(R.id.pic_positive_img);
        pic_positive_img.setOnClickListener(getController());
        pic_reverse_img = this.findViewById(R.id.pic_reverse_img);
        pic_reverse_img.setOnClickListener(getController());
        upload_pic_reverse_tv = this.findViewById(R.id.upload_pic_reverse_tv);
        upload_pic_reverse_tv.setOnClickListener(getController());
        upload_certificate_pic_commit = this.findViewById(R.id.upload_certificate_pic_commit);
        upload_certificate_pic_commit.setOnClickListener(getController());
        upload_pic_positive_tv = this.findViewById(R.id.upload_pic_positive_tv);
        upload_pic_positive_tv.setOnClickListener(getController());
        if(type.equals("phone_certificate")){
            setTitle("短信验证");
            mUploadRootLl.setVisibility(View.GONE);
            upload_certificate_pic_commit.setVisibility(View.GONE);
            getTitleLayout().hideRightLl(true);
            getTitleLayout().setOnRightTextClickListener(getController());
        }else {
            setTitle("实名认证");
            upload_pic_positive_tv.setText("[上传正面照]");
            upload_pic_reverse_tv.setText("[上传反面照]");
            upload_certificate_pic_commit.setText("下一步");
            OCR.getInstance(getContext()).initAccessToken(new OnResultListener<AccessToken>() {
                @Override
                public void onResult(AccessToken accessToken) {
                    String token = accessToken.getAccessToken();
                    /**
                     *  初始化本地质量控制模型
                     *  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
                     */
                    CameraNativeHelper.init(CertificateActivity.this, OCR.getInstance(CertificateActivity.this).getLicense(),
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
                                            //ToastUtil.shortToast(getApplicationContext(), "本地质量控制初始化错误，错误原因： " + msg);
                                        }
                                    });

                                }
                            });
                }

                @Override
                public void onError(OCRError error) {
                    error.printStackTrace();
                    alertText("licence方式获取token失败", error.getMessage());
                }
            }, getContext());

            mUploadRootLl.setVisibility(View.VISIBLE);
            upload_certificate_pic_commit.setVisibility(View.VISIBLE);
            getTitleLayout().hideRightLl(false);
        }
        get_message_bt.setOnClickListener(getController());

        EventBus.getDefault().register(getContext());
    }

    @Override
    public void msgInspectSuccess() {
        if(type.equals("phone_certificate")){
            Event event=new Event(EventType.PHONECERTIFICATESUCCESS,phone_num_edt.getText().toString().trim());
            EventBus.getDefault().post(event);
            finish();
        }else{
            if (getUrlList().size() == 2) {
                //getController().inspecteIdCard();
                NewCardActivity.start(getContext(), Const.CARD_ID_CARD, getUrlList(),getResult(),true);
            } else {
                ToastUtil.shortToast(getContext(), "证件图片上传未完成，请检查并完成上传");
            }
        }

    }

    @Override
    public void msgInspectFailed(String msg) {
        ToastUtil.shortToast(this,msg);
    }

    @Override
    public void getMsgCodeSuccess() {
        get_message_bt.startTimer();
        ToastUtil.shortToast(this,"验证码已发送，注意查看");
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.shortToast(this,errMsg);
    }

    @Override
    public void idCardCertificateSuccess() {
        Event event=new Event(EventType.IDCARDCERTIFICATESUCCESS,null);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void idCardCertificateFailed(String errMsg) {
        ToastUtil.shortToast(this,errMsg);
    }

    @Override
    public String getName() {
        return null != mResultBean? mResultBean.getName() : "";
    }

    @Override
    public String getIdCardNum() {
        return null != mResultBean? mResultBean.getIdNumber() : "";
    }

    @Override
    public String getPhoneNum() {
        return phone_num_edt.getText().toString().trim();
    }

    @Override
    public String getMessageCode() {
        return message_edt.getText().toString().trim();
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
    public IdCardBean getResult() {
        return mResultBean;
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
            ToastUtil.shortToast(getContext(), "证件照片一上传失败：" + msg + "请重新上传");
            upload_pic_positive_tv.setText("[上传失败,重新上传]");
        } else {
            ToastUtil.shortToast(getContext(), "证件照片二上传失败：" + msg + "请重新上传");
            upload_pic_reverse_tv.setText("[上传失败,重新上传]");
        }
    }

    @Override
    public boolean isPhoneCertificate() {
        return type.equals("phone_certificate");
    }


    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        EventBus.getDefault().unregister(getContext());
        super.onDestroy();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
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
                            mResultBean.setAddress(result.getAddress().toString());
                        else
                            mResultBean.setAddress("");
                        if (result.getName() != null)
                            mResultBean.setName(result.getName().toString());
                        else
                            mResultBean.setName("");
                        if (result.getIdNumber() != null)
                            mResultBean.setIdNumber(result.getIdNumber().toString());
                        else
                            mResultBean.setIdNumber("");
                        if (result.getGender() != null)
                            mResultBean.setGender(result.getGender().toString());
                        else
                            mResultBean.setGender("");
                        if (result.getEthnic() != null)
                            mResultBean.setEthnic(result.getEthnic().toString());
                        else
                            mResultBean.setEthnic("");
                        if (result.getBirthday() != null)
                            mResultBean.setBirthday(result.getBirthday().toString());
                        else
                            mResultBean.setBirthday("");
                    } else {
                        if (result.getExpiryDate() != null)
                            mResultBean.setExpiryDate(result.getExpiryDate().toString());
                        else
                            mResultBean.setExpiryDate("");

                        if (result.getSignDate() != null)
                            mResultBean.setSignDate(result.getSignDate().toString());
                        else
                            mResultBean.setSignDate("");
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
