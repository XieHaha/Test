package com.keydom.ih_doctor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_doctor.R;

public class PrescriptionModelView extends RelativeLayout {
    private Context context;
    private TextView modelTypePersonal;
    private TextView modelTypeDept;
    private TextView modelTypeCommon;
    private EditText modelNameInput;
    private String modelType="";

    public PrescriptionModelView(Context context) {
        this(context, null);
    }

    public PrescriptionModelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrescriptionModelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.prescription_model_view_layout, this);
        modelTypePersonal = findViewById(R.id.model_type_personal);
        modelTypeDept = findViewById(R.id.model_type_dept);
        modelTypeCommon = findViewById(R.id.model_type_common);
        modelNameInput = findViewById(R.id.model_name_et);

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
    }

    public String getModelType() {
        return modelType;
    }

    public String getModelName() {
        return modelNameInput.getText().toString().trim();
    }

    public void setModelType(String modelType) {
        if(modelType!=null){
            this.modelType=modelType;
            if("0".equals(modelType)){

                modelTypePersonal.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypePersonal.setTextColor(context.getResources().getColor(R.color.white));
            }else  if("1".equals(modelType)){
                modelTypeDept.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeDept.setTextColor(context.getResources().getColor(R.color.white));
            }else {
                modelTypeCommon.setBackground(context.getResources().getDrawable(R.drawable.model_selected));
                modelTypeCommon.setTextColor(context.getResources().getColor(R.color.white));
            }
        }
    }

    public void setModelName(String modelName) {
        if (modelName != null)
            modelNameInput.setText(modelName);
    }
}
