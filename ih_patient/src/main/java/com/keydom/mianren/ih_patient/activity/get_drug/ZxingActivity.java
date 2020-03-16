package com.keydom.mianren.ih_patient.activity.get_drug;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.widget.CommonProgressDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZxingActivity extends BaseActivity {

    private ImageView imageView;
    public static final String ENTITY = "ENTITY";
    private String mAcquireMedicineCode = "";
    private String mImageUrl = null;
    CountDownTimer timer;
    private String mPrescriptionId = "201906250003";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("二维码");

        imageView = findViewById(R.id.image_zxing);
        //getHttpPrescriptionCreateQRCode();
        if (getIntent() != null && getIntent().getExtras() != null) {
            mAcquireMedicineCode =  getIntent().getExtras().getString(ENTITY);

        }

        CommonProgressDialog.showLoading(ZxingActivity.this,"加载中...",false,null);

        new AsyncTask<Integer,Integer,Bitmap>(){

            @Override
            protected Bitmap doInBackground(Integer... integers) {
                Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_tfzl)).getBitmap();
                Bitmap image = (CodeUtils.createImage(mAcquireMedicineCode, 400, 400, bitmap));
                return image;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
                CommonProgressDialog.loadingDismiss(ZxingActivity.this);
            }
        }.execute();

    }

    private void GetTime() {
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //在计时器中轮询支付结果：每秒查询一次支付结果


            }

            @Override
            public void onFinish() {
                //倒数到0时的操作，一般认为倒数到0仍未收到支付结果，则认为支付失败，页面跳转
            }
        };
        timer.start();
    }

    /**
     * 获得处方具体药品
     */
    private void getHttpPrescriptionCreateQRCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", mPrescriptionId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getprescriptionCreateQRCode(map), new HttpSubscriber<List<PharmacyBean>>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable List<PharmacyBean> data) {
                if (!CommUtil.isEmpty(data.get(0).getQrcodeUrl())) {
                    String mImageUrl = new StringBuilder().append(Const.RELEASE_HOST).append(data.get(0).getQrcodeUrl()).toString();
                    GlideUtils.load(imageView, mImageUrl, R.mipmap.icon_zxing, R.mipmap.icon_zxing, false, null);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }

}
