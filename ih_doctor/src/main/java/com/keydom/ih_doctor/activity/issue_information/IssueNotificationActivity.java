package com.keydom.ih_doctor.activity.issue_information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.issue_information.controller.IssueNotificationController;
import com.keydom.ih_doctor.activity.issue_information.view.IssueNotificationView;
import com.keydom.ih_doctor.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class IssueNotificationActivity extends BaseControllerActivity<IssueNotificationController> implements IssueNotificationView {
    public List<String> dataList = new ArrayList<>();
    private TextView articleTitle, articleContent;
    /**
     * 发布通知公告限制最大图片数量
     */
    private static int MAX_IMAGE = 9;
    /**
     * 默认ID
     */
    private static int DEFAULT_ID = -1;
    private GridViewForScrollView mGridView;
    /**
     * 图片适配器
     */
    private GridViewPlusImgAdapter mAdapter;
    /**
     * 通知公告ID
     */
    private int notificationId = DEFAULT_ID;
    /**
     * 将所有图片地址拼接成字符串
     */
    private String imgStr = "";
    /**
     * 上传的图片
     */
    private String articleIcon = "";

    /**
     * 启动发布通知公告页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, IssueNotificationActivity.class);
        context.startActivity(starter);
    }

    /**
     * 启动修改通知公告页面
     *
     * @param context
     * @param id
     */
    public static void startWithUpdate(Context context, int id) {
        Intent starter = new Intent(context, IssueNotificationActivity.class);
        starter.putExtra(com.keydom.ih_doctor.constant.Const.DATA, id);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_issue_notification;
    }


    @Override
    public void issueSuccess(String successMsg) {
        if (successMsg != null && !successMsg.equals("")) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_NOTIFATION).build());
            ArticleDetailActivity.startNotification(IssueNotificationActivity.this, Long.parseLong(successMsg), MyApplication.userInfo.getHospitalId());
            finish();
        } else {
            ToastUtil.shortToast(getContext(), "数据错误");
        }
    }

    @Override
    public void issueFailed(String errMsg) {
        ToastUtils.showShort(errMsg);
    }

    @Override
    public boolean getLastItemClick(int position) {
        if (position == dataList.size())
            return true;
        return false;
    }

    @Override
    public HashMap<String, Object> getNotificationMap() {
        String title = articleTitle.getText().toString();
        String content = articleContent.getText().toString();
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            return null;
        }
        for (int i = 0; i < dataList.size(); i++) {
            imgStr += "<img src=\"" + Const.IMAGE_HOST + dataList.get(i) + "\"  alt=\"\" />";
            if (i == 0) {
                articleIcon = dataList.get(i);
            } else {
                articleIcon += "," + dataList.get(i);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("noticeType", "1");
        map.put("title", title);
        map.put("images", articleIcon);
        map.put("content", imgStr + "<p>" + content + "</p>");
        if (notificationId != DEFAULT_ID) {
            map.put("id", notificationId);
        }
        return map;
    }


    @Override
    public void uploadImgSuccess(String path) {
        dataList.add(path);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadImgFailed(String errMsg) {
        if (errMsg == null || "".equals(errMsg.trim())) {
            ToastUtil.shortToast(this, errMsg);
        } else {
            ToastUtil.shortToast(this, "图片上传失败!");
        }
    }

    @Override
    public void getNotificationSuccess(NoticeInfoBean bean) {
        pageLoadingSuccess();
        articleTitle.setText(bean.getTitle());
        articleContent.setText(CommonUtils.getContent(bean.getContent()));
        if (bean.getImages() != null) {
            String[] icons = bean.getImages().split(",");
            for (int i = 0; i < icons.length; i++) {
                dataList.add(icons[i]);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNotificationFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public int getImageLimit() {
        return MAX_IMAGE - dataList.size();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        notificationId = getIntent().getIntExtra(com.keydom.ih_doctor.constant.Const.DATA, DEFAULT_ID);
        getTitleLayout().initViewsVisible(true, true, true);
        setTitle("发布公告");
        setRightTxt("发布");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                getController().issueArticle(getNotificationMap());
            }
        });
        mGridView = (GridViewForScrollView) this.findViewById(R.id.img_gv);
        articleTitle = (TextView) this.findViewById(R.id.article_title);
        articleContent = (TextView) this.findViewById(R.id.article_content);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(getController());
        pageLoadingSuccess();
        if (notificationId != DEFAULT_ID) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", notificationId);
            map.put("hospitalId", MyApplication.userInfo.getHospitalId());
            pageLoading();
            getController().getNoticeDetail(map);
            setReloadListener(new LoadDataLayout.OnReloadListener() {
                @Override
                public void onReload(View v, int status) {
                    pageLoading();
                    getController().getNoticeDetail(map);
                }
            });
        }
    }


    /**
     * media.getPath(); 为原图path
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
