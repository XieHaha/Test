package com.keydom.mianren.ih_doctor.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.minterface.OnPrivateDialogListener;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.PrescriptionPagerAdapter;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTemplateBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnExtraOptionDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnModelAndCaseDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnModelDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnNurseResultListener;
import com.keydom.mianren.ih_doctor.m_interface.OnSelectRoleListener;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.keydom.mianren.ih_doctor.view.CustomTopBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jaaksi.pickerview.picker.MixedTimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;


/**
 * @Name???com.keydom.ih_common.utils
 * @Description???????????????
 * @Author???song
 * @Date???18/11/14 ??????4:56
 * ????????????xusong
 * ???????????????18/11/14 ??????4:56
 */
public class DialogUtils {

    public static final String SELECT_DATE = "select_date";
    public static final String SELECT_START_TIME = "select_start_time";
    public static final String SELECT_END_TIME = "select_end_time";
    public static final String INPUT_VALUE = "input_value";
    public static final String SELECT_USER = "select_user";

    public static Dialog createUpdateDialog(final Context context, String version, String content
            , final OnPrivateDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update, null);
        dialog.setContentView(view);
        final TextView cancelTv =
                (TextView) view.findViewById(R.id.dialog_update_cancel_tv);
        final TextView commitTv =
                (TextView) view.findViewById(R.id.dialog_update_confirm_tv);
        final TextView titleTv =
                (TextView) view.findViewById(R.id.dialog_update_title_tv);
        final TextView contentTv =
                (TextView) view.findViewById(R.id.dialog_update_content_tv);
        final ImageView closeIv =
                (ImageView) view.findViewById(R.id.dialog_update_close_iv);

        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content);
        }

        if (!TextUtils.isEmpty(version)) {
            titleTv.setText("????????????V " + version);
        }

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (null != listener) {
                    listener.cancel();
                }
            }
        });
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (null != listener) {
                    listener.confirm();
                }
            }
        });

        if (null != listener) {
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cancel();
                }
            });
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * ????????????dialog
     *
     * @param context
     * @param listener
     * @return
     */
    public static Dialog createCheckDialog(final Context context,
                                           final OnCheckDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prescription_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        final EditText checkDialogInput = view.findViewById(R.id.check_dialog_input);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDialogInput.getText().toString() == null || "".equals(checkDialogInput.getText().toString())) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
                listener.commit(v, checkDialogInput.getText().toString());
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    public static Dialog createReceiveDialog(final Context context,
                                             final OnExtraOptionDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.dialog_submit);
        final LinearLayout nurseLl = view.findViewById(R.id.nurse_ll);
        final TextView nurseName = view.findViewById(R.id.nurse_name);
        final Map<String, Object> map = new HashMap<>();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nurseName.getText().toString() == null || "".equals(nurseName.getText().toString())) {
                    ToastUtil.showMessage(context, "????????????????????????");
                    return;
                }
                map.put(SELECT_USER, nurseName.getText().toString());
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
                listener.commit(v, map);
            }
        });
        nurseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.extraClick(v, nurseName.getText().toString(), new OnNurseResultListener() {
                    @Override
                    public void nurseSelect(String msg) {
                        nurseName.setText(msg);
                    }
                });
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    public static Dialog createChangeNurseDialog(final Context context,
                                                 final OnExtraOptionDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.dialog_submit);
        final TextView title = (TextView) view.findViewById(R.id.dialog_tip_tv);
        final LinearLayout nurseLl = view.findViewById(R.id.nurse_ll);
        final TextView nurseName = view.findViewById(R.id.nurse_name);
        final Map<String, Object> map = new HashMap<>();
        title.setText("????????????");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nurseName.getText().toString() == null || "".equals(nurseName.getText().toString())) {
                    ToastUtil.showMessage(context, "????????????????????????");
                    return;
                }
                map.put(SELECT_USER, nurseName.getText().toString());
                listener.commit(v, map);
            }
        });
        nurseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.extraClick(v, nurseName.getText().toString(), new OnNurseResultListener() {
                    @Override
                    public void nurseSelect(String msg) {
                        nurseName.setText(msg);
                    }
                });
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog createReturnDialog(final Context context,
                                            final OnCheckDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_return_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.nurse_return_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.nurse_return_dialog_submit);
        final EditText returnDialogInput =
                (EditText) view.findViewById(R.id.nurse_return_dialog_input);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnDialogInput.getText().toString() == null || "".equals(returnDialogInput.getText().toString())) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                listener.commit(v, returnDialogInput.getText().toString());
                dialog.hide();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog createReturnBackDialog(final Context context,
                                                final OnCheckDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_return_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.nurse_return_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.nurse_return_dialog_submit);
        final TextView title = (TextView) view.findViewById(R.id.dialog_tip_tv);
        final EditText returnDialogInput =
                (EditText) view.findViewById(R.id.nurse_return_dialog_input);
        title.setText("??????");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnDialogInput.getText().toString() == null || "".equals(returnDialogInput.getText().toString())) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                listener.commit(v, returnDialogInput.getText().toString());
                dialog.hide();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog createServiceSureDialog(final Context context,
                                                 final OnExtraOptionDialogListener listener) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_confirm_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.dialog_submit);
        final EditText input = (EditText) view.findViewById(R.id.dialog_input);
        final TextView visitTime = (TextView) view.findViewById(R.id.visit_time);
        final ImageView voiceInputIv =
                (ImageView) view.findViewById(R.id.nurse_service_confirm_dialog_layout_voice_input_iv);
        final Map<String, Object> map = new HashMap<>();

        // ????????????UI
        CustomRecognizerDialog mIatDialog;

        /**
         * ?????????????????????
         */
        InitListener mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {

                if (code != ErrorCode.SUCCESS) {
                    Log.e("xunfei", "??????????????????????????????" + code + ",???????????????https://www.xfyun" +
                            ".cn/document/error-code??????????????????");
                }
            }
        };

        /**
         * ??????UI?????????
         */
        RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                if (null != input) {
                    String text = JsonUtils.handleXunFeiJson(results);
                    if (TextUtils.isEmpty(input.getText().toString())) {
                        input.setText(text);
                        input.setSelection(input.getText().length());
                    } else {
                        input.setText(input.getText().toString() + text);
                        input.setSelection(input.getText().length());
                    }
                }

            }

            /**
             * ??????????????????.
             */
            @Override
            public void onError(SpeechError error) {
                ToastUtil.showMessage(MyApplication.mApplication, error.getPlainDescription(true));

            }

        };

        // ???????????????Dialog?????????????????????UI???????????????????????????SpeechRecognizer
        // ??????UI????????????????????????sdk??????????????????notice.txt,?????????????????????????????????
        mIatDialog = new CustomRecognizerDialog(context, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        voiceInputIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPremissions(appCompatActivity, mIatDialog);
            }
        });


        MixedTimePicker datePicker = new MixedTimePicker.Builder(context,
                MixedTimePicker.TYPE_DATE, new MixedTimePicker.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(MixedTimePicker picker, Date date) {
                map.put(SELECT_DATE, date);
                MixedTimePicker startTimePicker = new MixedTimePicker.Builder(context,
                        MixedTimePicker.TYPE_TIME, new MixedTimePicker.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(MixedTimePicker picker, Date date) {
                        map.put(SELECT_START_TIME, date);
                        MixedTimePicker endTimePicker = new MixedTimePicker.Builder(context,
                                MixedTimePicker.TYPE_TIME,
                                new MixedTimePicker.OnTimeSelectListener() {
                                    @Override
                                    public void onTimeSelect(MixedTimePicker picker, Date date) {
                                        map.put(SELECT_END_TIME, date);
                                        visitTime.setText(CalculateTimeUtils.requestDate((Date) map.get(SELECT_DATE)) + " " + CalculateTimeUtils.getY2mTimeStr((Date) map.get(SELECT_START_TIME)) + "-" + CalculateTimeUtils.getY2mTimeStr((Date) map.get(SELECT_END_TIME)));
                                    }
                                }).setContainsEndDate(false)
                                .setTimeMinuteOffset(60)
                                .setRangDate(((Date) map.get(SELECT_START_TIME)).getTime() + (30 * 60 * 1000), 2524665599000L)
                                .create();
                        final Dialog endPickerDialog = endTimePicker.getPickerDialog();
                        endPickerDialog.setCanceledOnTouchOutside(true);
                        CustomTopBar endTopBar = (CustomTopBar) endTimePicker.getTopBar();
                        endTopBar.getTitleView().setText("???????????????????????????");
                        endPickerDialog.show();

                    }
                }).setContainsEndDate(false)
                        .setTimeMinuteOffset(60)
                        .setRangDate(CalculateTimeUtils.getCurrentTime((Date) map.get(SELECT_DATE)), 2524665599000L)
                        .create();


                final Dialog startPickerDialog = startTimePicker.getPickerDialog();
                startPickerDialog.setCanceledOnTouchOutside(true);
                CustomTopBar startTopBar = (CustomTopBar) startTimePicker.getTopBar();
                startTopBar.getTitleView().setText("???????????????????????????");
                startPickerDialog.show();
            }
        }).setContainsEndDate(false)
                .setTimeMinuteOffset(60)
                .setRangDate(CalculateTimeUtils.getCurrentDayTime(), 2524665599000L)
                .create();


        final Dialog datePickerDialog = datePicker.getPickerDialog();
        datePickerDialog.setCanceledOnTouchOutside(true);
        CustomTopBar topBar = (CustomTopBar) datePicker.getTopBar();
        topBar.getTitleView().setText("?????????????????????");


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map == null || visitTime.getText().toString() == null || "".equals(visitTime.getText().toString())) {
                    ToastUtil.showMessage(context, "????????????????????????");
                    return;
                }
                map.put(INPUT_VALUE, input.getText().toString() == null ? "" :
                        input.getText().toString());
                listener.commit(v, map);
                dialog.hide();
            }
        });
        visitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @SuppressLint("CheckResult")
    public static void initPremissions(FragmentActivity activity, RecognizerDialog mIatDialog) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            if (mIatDialog.isShowing()) {
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(MyApplication.mApplication, "??????????????????");

                        } else {
                            ToastUtil.showMessage(MyApplication.mApplication, "??????????????????????????????");

                        }
                    }
                });

    }


    public static String modelType = "";

    public static Dialog saveModelDialog(final Context context,
                                         final OnModelDialogListener listener) {
        modelType = "";
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.save_prescription_model_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        final TextView modelTypePersonal = (TextView) view.findViewById(R.id.model_type_personal);
        final TextView modelTypeDept = (TextView) view.findViewById(R.id.model_type_dept);
        final TextView modelTypeCommon = (TextView) view.findViewById(R.id.model_type_common);
        final EditText modelNameInput = view.findViewById(R.id.model_name_et);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        modelTypePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "0";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });
        modelTypeDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "1";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });

        modelTypeCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "2";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.white));
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelNameInput.getText().toString() == null || "".equals(modelNameInput.getText().toString())) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                if (modelType == null || "".equals(modelType)) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                listener.dialogClick(v, modelType, modelNameInput.getText().toString());
                dialog.hide();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    /*
     *????????????????????????
     */
    public static Dialog saveModelDialog(final Context context,
                                         PrescriptionTemplateBean prescriptionTemplateBean,
                                         final OnModelDialogListener listener) {
        modelType = "";
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.save_prescription_model_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        final TextView modelTypePersonal = (TextView) view.findViewById(R.id.model_type_personal);
        final TextView modelTypeDept = (TextView) view.findViewById(R.id.model_type_dept);
        final TextView modelTypeCommon = (TextView) view.findViewById(R.id.model_type_common);
        final EditText modelNameInput = view.findViewById(R.id.model_name_et);
        if (prescriptionTemplateBean.isSavedAsTemplate()) {
            if (prescriptionTemplateBean.getModelTypeTemp() != null) {
                modelType = prescriptionTemplateBean.getModelTypeTemp();
                if ("0".equals(modelType)) {

                    modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                    modelTypePersonal.setTextColor(context.getResources().getColor(R.color.white));
                } else if ("1".equals(modelType)) {
                    modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                    modelTypeDept.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                    modelTypeCommon.setTextColor(context.getResources().getColor(R.color.white));
                }
            }
            if (prescriptionTemplateBean.getModelNameTemp() != null) {
                modelNameInput.setText(prescriptionTemplateBean.getModelNameTemp());
            }
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        modelTypePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "0";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });
        modelTypeDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "1";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });

        modelTypeCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "2";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.white));
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelNameInput.getText().toString() == null || "".equals(modelNameInput.getText().toString())) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                if (modelType == null || "".equals(modelType)) {
                    ToastUtil.showMessage(context, "?????????????????????");
                    return;
                }
                dialog.hide();
                dialog.dismiss();
                dialog.cancel();
                listener.dialogClick(v, modelType, modelNameInput.getText().toString());
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * ???????????????????????????
     *
     * @param context
     * @param listener
     * @return
     */
    public static Dialog savePrescriptionAndCaseDialog(final Context context,
                                                       final List<PrescriptionTemplateBean> templateBeanList, final OnModelAndCaseDialogListener listener) {
        modelType = "";
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.save_prescription_and_case_dialog_layout, null);
        dialog.setContentView(view);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < templateBeanList.size(); i++) {
            list.add("??????" + DateUtils.numberToCH(i + 1));
        }
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        final TextView modelTypePersonal = (TextView) view.findViewById(R.id.model_type_personal);
        final TextView modelTypeDept = (TextView) view.findViewById(R.id.model_type_dept);
        final TextView modelTypeCommon = (TextView) view.findViewById(R.id.model_type_common);
        final EditText modelNameInput = view.findViewById(R.id.model_name_et);
        final TabLayout prescription_tab = view.findViewById(R.id.prescription_tab);
        final ViewPager prescription_vp = view.findViewById(R.id.prescription_vp);
        final PrescriptionPagerAdapter prescriptionPagerAdapter =
                new PrescriptionPagerAdapter(context, list, templateBeanList);
        prescription_vp.setAdapter(prescriptionPagerAdapter);
        prescription_tab.setupWithViewPager(prescription_vp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                dialog.dismiss();
                dialog.cancel();
            }
        });

        modelTypePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "0";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });
        modelTypeDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "1";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.white));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
            }
        });

        modelTypeCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelType = "2";
                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_un_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.fontColorNavigate));
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.white));
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelNameInput.getText().toString() == null || "".equals(modelNameInput.getText().toString())) {
                    ToastUtil.showMessage(context, "???????????????????????????");
                    return;
                }
                if (modelType == null || "".equals(modelType)) {
                    ToastUtil.showMessage(context, "???????????????????????????");
                    return;
                }
                for (int i = 0; i < prescriptionPagerAdapter.getModelList().size(); i++) {
                    if ("".equals(prescriptionPagerAdapter.getModelList().get(i).getModelType()) && !"".equals(prescriptionPagerAdapter.getModelList().get(i).getModelName())) {
                        ToastUtil.showMessage(context, "????????????" + DateUtils.numberToCH(i + 1) +
                                "?????????????????????");
                        return;
                    } else if (!"".equals(prescriptionPagerAdapter.getModelList().get(i).getModelType()) && "".equals(prescriptionPagerAdapter.getModelList().get(i).getModelName())) {
                        ToastUtil.showMessage(context, "????????????" + DateUtils.numberToCH(i + 1) +
                                "?????????????????????");
                        return;
                    }

                }
                dialog.hide();
                dialog.dismiss();
                dialog.cancel();
                listener.dialogClick(v, modelType, modelNameInput.getText().toString(),
                        prescriptionPagerAdapter.getModelList());
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private static Dialog alertDialog;

    public static void showSingleAlertDialog(final Context mContext, final List<Integer> list,
                                             final OnSelectRoleListener listener) {
        String[] items = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == Const.ROLE_DOCTOR) {
                items[i] = "??????";
            } else if (list.get(i) == Const.ROLE_NURSE) {
                items[i] = "??????";
            } else {
                items[i] = "??????";
            }
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setTitle("????????????");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharePreferenceManager.setRoleId(list.get(i));
                listener.selectRole(list.get(i));
            }
        });

        alertBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                listener.selectCancel();
            }
        });

        alertBuilder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}
