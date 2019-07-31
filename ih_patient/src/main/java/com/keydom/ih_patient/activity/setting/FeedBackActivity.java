package com.keydom.ih_patient.activity.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.setting.controller.FeedBackController;
import com.keydom.ih_patient.activity.setting.view.FeedBackView;
import com.keydom.ih_patient.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：意见反馈页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class FeedBackActivity extends BaseControllerActivity<FeedBackController> implements FeedBackView {
    private GridViewPlusImgAdapter mAdapter;
    public List<String> dataList = new ArrayList<>();
    private InterceptorEditText titleEt, contentEt;
    private GridViewForScrollView mGridView;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, FeedBackActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_feedback_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("意见反馈");
        setRightTxt("提交");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (checkFeedBack()) {
                    getController().feedBack();
                }
            }
        });
        titleEt = this.findViewById(R.id.feed_back_title);
        contentEt = this.findViewById(R.id.feed_back_content);
        mGridView = (GridViewForScrollView) this.findViewById(R.id.img_gv);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(getController());

    }

    @Override
    public void feedBackSuccess(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("反馈提交成功");
        builder.setMessage("提交成功，是否退出该页面？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FeedBackActivity.this.finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void feedBackFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    @Override
    public void uploadSuccess(String msg) {
        dataList.add(msg);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadFailed(String msg) {
        ToastUtil.shortToast(this, "提交失败"+msg);
    }

    @Override
    public boolean getLastItemClick(int position) {
        if (position == dataList.size())
            return true;
        return false;
    }


    @Override
    public Map<String, Object> getFeedBackMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", titleEt.getText().toString());
        map.put("content", contentEt.getText().toString());
        map.put("image", getImageStr());
        map.put("type", 1);
        map.put("userId", Global.getUserId());
        return map;
    }

    @Override
    public boolean checkFeedBack() {
        if (titleEt.getText().toString() == null || "".equals(titleEt.getText().toString())) {
            ToastUtil.shortToast(this, "请输入标题");
            return false;
        }

        if (contentEt.getText().toString() == null || "".equals(contentEt.getText().toString())) {
            ToastUtil.shortToast(this, "请输入反馈内容");
            return false;
        }
        return true;
    }

    @Override
    public int getImgSize() {
        return dataList.size();
    }

    @Override
    public String getPicUrl(int position) {
        return Const.IMAGE_HOST+dataList.get(position);
    }

    private String getImageStr() {
        String imageStr = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                imageStr = dataList.get(i);
            } else {
                imageStr += "," + dataList.get(i);
            }
        }
        return imageStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        getController().uploadFile(selectList.get(i).getPath());
                    }
                    break;
            }
        }
    }
}
