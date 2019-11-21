package com.keydom.ih_doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.controller.IssueArticleController;
import com.keydom.ih_doctor.activity.view.IssueArticleView;
import com.keydom.ih_doctor.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.ih_doctor.view.InputFlagDialog;
import com.keydom.ih_doctor.view.TagView;
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
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class IssueArticleActivity extends BaseControllerActivity<IssueArticleController> implements IssueArticleView {
    /**
     * 文章发布最大图片上传数量
     */
    private static int MAX_IMAGE = 9;
    private GridViewForScrollView mGridView;
    /**
     * 图片适配器
     */
    private GridViewPlusImgAdapter mAdapter;
    private TagView mTagView;
    private InputFlagDialog inputFlagDialog;
    /**
     * 图片地址列表
     */
    public List<String> dataList = new ArrayList<>();
    private TextView addTagTv, articleTitle, articleDec, articleContent;


    /**
     * 标签列表
     */
    private List<String> tags = new ArrayList<>();
    /**
     * 文章ID，修改文章的时候才会有值
     */
    private long articleId;

    /**
     * 启动发布文章页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, IssueArticleActivity.class);
        context.startActivity(starter);
    }

    /**
     * 启动修改文章详情页面<br/>
     * 进入页面后用articleId查询出文章详情，填充到界面，再进行修改<br/>
     *
     * @param context
     * @param articleId 文章ID
     */
    public static void startForUpdate(Context context, long articleId) {
        Intent starter = new Intent(context, IssueArticleActivity.class);
        starter.putExtra(Const.DATA, articleId);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_issue_article;
    }


    @Override
    public void issueSuccess(String successMsg) {
        if (successMsg != null && !successMsg.equals("")) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_ARTICLE).build());
            ArticleDetailActivity.startArticle(IssueArticleActivity.this, Long.parseLong(successMsg), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(), false);
            finish();
        } else {
            ToastUtil.showMessage(getContext(), "数据错误");
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
    public HashMap<String, Object> getArticleMap() {
        String title = articleTitle.getText().toString();
        String dec = articleDec.getText().toString();
        String content = articleContent.getText().toString();
        String mTagStr = "";
        String imgStr = "";
        String articleIcon = "";
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(dec) || StringUtils.isEmpty(content)) {
            return null;
        }
        for (int i = 0; i < tags.size(); i++) {
            if (i == 0) {
                mTagStr = tags.get(i);
            } else {
                mTagStr += "," + tags.get(i);
            }
        }

        for (int i = 0; i < dataList.size(); i++) {
            imgStr += "<img src=\"" + Const.IMAGE_HOST + dataList.get(i) + "\"  alt=\"\" />";
            if (i == 0) {
                articleIcon += dataList.get(i);
            } else {
                articleIcon += "," + dataList.get(i);
            }

        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("submiterId", MyApplication.userInfo.getId());
        map.put("submiter", MyApplication.userInfo.getName());
        map.put("summary", dec);
        map.put("title", title);
        map.put("image", articleIcon);
        map.put("content", imgStr + "<p>" + content + "</p>");
        map.put("lables", mTagStr);
        if (articleId != -1) {
            map.put("articleId", articleId);
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
        if(errMsg==null||"".equals(errMsg.trim())){
            ToastUtil.showMessage(this, errMsg);
        }else{
            ToastUtil.showMessage(this, "图片上传失败!");
        }
    }

    @Override
    public void articleInfoSuccess(Article article) {
        if (article.getArticleImage() != null) {
            String[] icons = article.getArticleImage().split(",");
            for (int i = 0; i < icons.length; i++) {
                dataList.add(icons[i]);
            }
        }
        if (article.getLableNames() != null) {
            for (int i = 0; i < article.getLableNames().size(); i++) {
                addTag(article.getLableNames().get(i));
            }
        }
        articleTitle.setText(article.getTitle());
        articleDec.setText(article.getSummary());
        articleContent.setText(CommonUtils.getContent(article.getContent()));
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void articleInfoFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public int getImageLimit() {
        return MAX_IMAGE - dataList.size();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, true);
        articleId = getIntent().getLongExtra(Const.DATA, -1);
        if (articleId != -1) {
            setTitle("修改文章");
            getController().getArticleDetails(getDetailMap());
        } else {
            setTitle("发布文章");
        }
        setRightTxt("发布");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (dataList.size() == 0) {
                    ToastUtil.showMessage(IssueArticleActivity.this, "至少添加一张图片");
                    return;
                }
                getController().issueArticle(getArticleMap());
            }
        });
        mGridView = (GridViewForScrollView) this.findViewById(R.id.img_gv);
        mTagView = (TagView) this.findViewById(R.id.my_tag_view);
        addTagTv = (TextView) this.findViewById(R.id.add_tag_tv);
        articleTitle = (TextView) this.findViewById(R.id.article_title);
        articleDec = (TextView) this.findViewById(R.id.article_dec);
        articleContent = (TextView) this.findViewById(R.id.article_content);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(getController());
        addTagTv.setOnClickListener(getController());
    }


    private void addTag(String tag) {
        if (tagIsExist(tag)) {
            ToastUtil.showMessage(this, "标签已存在");
            return;
        }
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;

        TextView view = new TextView(this);
        view.setText(tag);
        view.setTextColor(getResources().getColor(R.color.fontColorPrimary));
//        view.setTextSize(getResources().getDimensionPixelSize(R.dimen.font_size_primary));
        view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.article_tag_bg));
        view.setGravity(Gravity.CENTER);
        mTagView.addView(view, lp);
        tags.add(tag);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new GeneralDialog(IssueArticleActivity.this, "是否删除标签?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        mTagView.removeView(view);
                        tags.remove(tag);
                    }
                }).show();
                return false;
            }
        });
    }

    private boolean tagIsExist(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public void showInputDialog() {
        if (inputFlagDialog == null) {
            inputFlagDialog = new InputFlagDialog(this, new OnCheckDialogListener() {
                @Override
                public void commit(View v, String value) {
                    value = value.trim();
                    if (value != null && !value.equals("")) {
                        addTag(value);
                        inputFlagDialog.dismiss();
                    } else {
                        ToastUtil.showMessage(getContext(), "请输入标签");
                    }
                }
            });
        }
        inputFlagDialog.show();
    }


    @Override
    public HashMap<String, Object> getDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", SharePreferenceManager.getId());
        map.put("id", articleId);
        return map;
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
