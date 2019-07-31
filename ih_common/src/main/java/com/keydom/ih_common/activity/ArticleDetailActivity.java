package com.keydom.ih_common.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.controller.ArticleDetailController;
import com.keydom.ih_common.activity.view.ArticleDetailView;
import com.keydom.ih_common.adapter.CommentLikeListRecyclrViewAdapter;
import com.keydom.ih_common.adapter.CommentListRecyclrViewAdapter;
import com.keydom.ih_common.adapter.HealthCommentAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.bean.ArticleLikeBean;
import com.keydom.ih_common.bean.ArticleLikeUserBean;
import com.keydom.ih_common.bean.HealthArticalInfo;
import com.keydom.ih_common.bean.HealthArticleCommentBean;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.constant.Global;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.FlowLayout;
import com.keydom.ih_common.view.IhTitleLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class ArticleDetailActivity extends BaseControllerActivity<ArticleDetailController> implements ArticleDetailView {

    private RecyclerView expandableListView, likeIconRv;
    //    private CommentExpandAdapter adapter;
    private CommentListRecyclrViewAdapter commentListRecyclrViewAdapter;
    private HealthCommentAdapter healthCommentAdapter;
    private CommentLikeListRecyclrViewAdapter commentLikeListRecyclrViewAdapter;
    private List<ArticleCommentBean> commentsList = new ArrayList<>();
    private List<HealthArticleCommentBean> healthCommentsList = new ArrayList<>();
    private HealthArticalInfo healthArticalInfo;
    private List<ArticleLikeUserBean> likeList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private NestedScrollView detailSv;
    private TextView likeUserAmount, articleBoxRichText, commentTip, articleDec, commentInputTv, articleReaded, attentionTv, articleComment, authorCity, authorDepartments, authorPosition, commentTv, authorName, articleTitle, articleAttention;
    private RefreshLayout refreshLayout;
    private View commentLine;
    private ImageView collectIv;
    private FlowLayout tagRv;
    private long articleId;
    private long userId;
    private int type;
    private String userIcon;
    private String userName;
    private Integer isLike;
    private Integer isCollect;
    private Integer isAttention;
    private long hospitalId;
    private Article article;
    int[] location = new int[2];
    public static final int NO_COMMENT = 1000;
    public static final int COMMENT = 1001;
    private RelativeLayout inputRl, likeAmountRl;
    private LinearLayout decLl;
    private LinearLayout linearlayout, detail_page_comment_container;
    private LayoutInflater mLayountInflater;
    private RelativeLayout comment_empty_layout;
    private TextView empty_text;
    private boolean isAudit = false;

    public static void startArticle(Context context, long articleId, long userId, String userName, String userIcon, boolean isAudit) {
        Intent starter = new Intent(context, ArticleDetailActivity.class);
        starter.putExtra(Const.ARTICLE_ID, articleId);
        starter.putExtra(Const.USER_ID, userId);
        starter.putExtra(Const.USER_ICON, userIcon);
        starter.putExtra(Const.USER_NAME, userName);
        starter.putExtra(Const.TYPE, Const.ARTICLE_DETAILS);
        starter.putExtra(Const.AUDIT, isAudit);
        context.startActivity(starter);
    }


    public static void startHealth(Context context, long articleId, long userId, String userName, String userIcon) {
        Intent starter = new Intent(context, ArticleDetailActivity.class);
        starter.putExtra(Const.ARTICLE_ID, articleId);
        starter.putExtra(Const.USER_ID, userId);
        starter.putExtra(Const.USER_ICON, userIcon);
        starter.putExtra(Const.USER_NAME, userName);
        starter.putExtra(Const.TYPE, Const.HEALTH_DETAILS);
        context.startActivity(starter);
    }


    public static void startNotification(Context context, long articleId, long hospitalId) {
        Intent starter = new Intent(context, ArticleDetailActivity.class);
        starter.putExtra(Const.ARTICLE_ID, articleId);
        starter.putExtra(Const.HOSPITAL_ID, hospitalId);
        starter.putExtra(Const.TYPE, Const.NOTIFICATION_DETAILS);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_article_detail_layout;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        articleId = getIntent().getLongExtra(Const.ARTICLE_ID, 0);
        userIcon = getIntent().getStringExtra(Const.USER_ICON);
        userId = getIntent().getLongExtra(Const.USER_ID, 0);
        isAudit = getIntent().getBooleanExtra(Const.AUDIT, false);
        Global.setUserId(userId);
        userName = getIntent().getStringExtra(Const.USER_NAME);
        hospitalId = getIntent().getLongExtra(Const.HOSPITAL_ID, 0);
        type = getIntent().getIntExtra(Const.TYPE, 0);
        getTitleLayout().initViewsVisible(true, true, true);
        getTitleLayout().setRightImg(getResources().getDrawable(R.mipmap.more));
        getTitleLayout().setOnRightTextClickListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                Toast.makeText(ArticleDetailActivity.this, "更多", Toast.LENGTH_SHORT).show();
            }
        });
        detailSv = (NestedScrollView) findViewById(R.id.detail_box_sv);
        likeAmountRl = (RelativeLayout) findViewById(R.id.like_amount_rl);
        expandableListView = (RecyclerView) findViewById(R.id.detail_page_lv_comment);
        likeIconRv = (RecyclerView) findViewById(R.id.like_user_icon);
        articleBoxRichText = (TextView) this.findViewById(R.id.article_box_rich_text);
        commentInputTv = (TextView) this.findViewById(R.id.comment_input_ev);
        articleReaded = (TextView) this.findViewById(R.id.article_readed);
        articleAttention = (TextView) this.findViewById(R.id.article_attention);
        articleComment = (TextView) this.findViewById(R.id.article_comment);
        authorCity = (TextView) this.findViewById(R.id.author_city);
        authorDepartments = (TextView) this.findViewById(R.id.author_departments);
        authorPosition = (TextView) this.findViewById(R.id.author_position);
        attentionTv = (TextView) this.findViewById(R.id.attention_tv);
        commentTv = (TextView) this.findViewById(R.id.comment_tv);
        collectIv = (ImageView) this.findViewById(R.id.collect_iv);
        authorName = (TextView) this.findViewById(R.id.author_name);
        articleTitle = (TextView) this.findViewById(R.id.article_title);
        articleDec = (TextView) this.findViewById(R.id.article_dec);
        likeUserAmount = (TextView) this.findViewById(R.id.like_user_amount);
        commentTip = (TextView) this.findViewById(R.id.comment_tip);
        commentLine = (View) this.findViewById(R.id.comment_line);
        inputRl = (RelativeLayout) this.findViewById(R.id.input_rl);
        tagRv = (FlowLayout) this.findViewById(R.id.tag_rv);
        decLl = (LinearLayout) this.findViewById(R.id.dec_ll);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        commentTv.setOnClickListener(getController());
        collectIv.setOnClickListener(getController());
        attentionTv.setOnClickListener(getController());
        commentInputTv.setOnClickListener(getController());
        comment_empty_layout = findViewById(R.id.comment_empty_layout);
        detail_page_comment_container = findViewById(R.id.detail_page_comment_container);
        empty_text = findViewById(R.id.empty_text);
        pageLoading();
        getData();
        mLayountInflater = LayoutInflater.from(this);
        RichText.initCacheDir(this);
        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setEnableRefresh(false);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getData();
            }
        });

    }

    private void getData() {
        if (type == Const.ARTICLE_DETAILS) {
            setTitle("文章详情");
            if (!isAudit) {
                initNotAuditArticle();
            }
            initExpandableListView();
            getController().getArticleDetails(type, getDetailMap());
            getController().getArticleComments(getCommentMap());
            getController().getArticleLikeList(getLikeInfoMap());
        } else if (type == Const.HEALTH_DETAILS) {
            setTitle("健康知识详情");
            initUi();
            initHealthCommentListVew();
            getController().getHealthDetails(getHealthDetailMap());
            if (userId == -1) {
                detail_page_comment_container.setVisibility(View.GONE);
                comment_empty_layout.setVisibility(View.VISIBLE);
                empty_text.setText("未登录状态无法查看评论和添加评论");
                refreshLayout.setEnableLoadMore(false);
                inputRl.setVisibility(View.GONE);
            } else {
                getController().getHealthArticleComments(getHealthCommentMap());
            }

        } else {
            setTitle("公告详情");
            initNotification();
            getController().getNoticeDetail(getNoticeDetailMap());
        }
    }

    private void initUi() {
        authorName.setVisibility(View.GONE);
        authorCity.setVisibility(View.GONE);
        authorDepartments.setVisibility(View.GONE);
        authorPosition.setVisibility(View.GONE);
        articleAttention.setVisibility(View.GONE);
        attentionTv.setVisibility(View.GONE);
        likeAmountRl.setVisibility(View.GONE);
        collectIv.setVisibility(View.INVISIBLE);
        collectIv.setClickable(false);
    }

    /**
     * 未审核文章
     */
    private void initNotAuditArticle() {
        inputRl.setVisibility(View.GONE);
        articleAttention.setVisibility(View.GONE);
        articleReaded.setVisibility(View.GONE);
        articleComment.setVisibility(View.GONE);
        commentTip.setVisibility(View.GONE);
        commentLine.setVisibility(View.GONE);
        likeAmountRl.setVisibility(View.GONE);
        attentionTv.setVisibility(View.GONE);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
    }


    private void initNotification() {
        inputRl.setVisibility(View.GONE);
        articleAttention.setVisibility(View.GONE);
        articleReaded.setVisibility(View.GONE);
        articleComment.setVisibility(View.GONE);
        decLl.setVisibility(View.GONE);
        tagRv.setVisibility(View.GONE);
        commentTip.setVisibility(View.GONE);
        commentLine.setVisibility(View.GONE);
        likeAmountRl.setVisibility(View.GONE);
        attentionTv.setVisibility(View.GONE);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
    }

    private void initExpandableListView() {
        commentListRecyclrViewAdapter = new CommentListRecyclrViewAdapter(this, commentsList);
        expandableListView.setAdapter(commentListRecyclrViewAdapter);
        expandableListView.setLayoutManager(new LinearLayoutManager(this));
        expandableListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        commentLikeListRecyclrViewAdapter = new CommentLikeListRecyclrViewAdapter(this, likeList);
        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        likeIconRv.setAdapter(commentLikeListRecyclrViewAdapter);
        likeIconRv.setLayoutManager(diagnoseInfoImgRvLm);
    }

    private void initHealthCommentListVew() {
        healthCommentAdapter = new HealthCommentAdapter(this, healthCommentsList, userId);
        expandableListView.setAdapter(healthCommentAdapter);
        expandableListView.setLayoutManager(new LinearLayoutManager(this));
        expandableListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    public void moveToComment() {
        expandableListView.getLocationOnScreen(location);
        detailSv.scrollBy(0, location[1] - (int) (getResources().getDimension(R.dimen.status_and_title_dp)));
    }

    public void showCommentDialog() {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
                    getController().addArticleComment(-1, articleId, userId, userName, userIcon, "", "", commentContent);
                } else {
                    Toast.makeText(ArticleDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setTextColor(getResources().getColor(R.color.fontClickEnable));
                } else {
                    bt_comment.setTextColor(getResources().getColor(R.color.strokeColorDisable));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showKeyboard(commentText);
        dialog.show();
    }

    public void showHealthCommentDialog() {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
                    getController().addHealthArticleComment(articleId, userId, -1, "", commentContent);
                } else {
                    Toast.makeText(ArticleDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setTextColor(getResources().getColor(R.color.fontClickEnable));
                } else {
                    bt_comment.setTextColor(getResources().getColor(R.color.strokeColorDisable));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showKeyboard(commentText);
        dialog.show();
    }

    public void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getCriticsName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    getController().addArticleComment(commentsList.get(position).getCriticsId(), articleId, userId, userName, userIcon, commentsList.get(position).getCriticsName(), commentsList.get(position).getMyCommentContext(), replyContent);
                    dialog.dismiss();
                    Toast.makeText(ArticleDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ArticleDetailActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setTextColor(getResources().getColor(R.color.font_service_enable));
                } else {
                    bt_comment.setTextColor(getResources().getColor(R.color.strokeColorDisable));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showKeyboard(commentText);
        dialog.show();
    }

    public void showHealthReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        InputFilter[] emojiFilters = {emojiFilter};
        commentText.setFilters(emojiFilters);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + healthCommentsList.get(position).getCriticsName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    getController().addHealthArticleComment(articleId, userId, healthCommentsList.get(position).getCriticsId(), healthCommentsList.get(position).getMyCommentContexxt(), replyContent);
                    dialog.dismiss();
                    Toast.makeText(ArticleDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ArticleDetailActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setTextColor(getResources().getColor(R.color.font_service_enable));
                } else {
                    bt_comment.setTextColor(getResources().getColor(R.color.strokeColorDisable));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showKeyboard(commentText);
        dialog.show();
    }

    public void showKeyboard(EditText editText) {
        if (editText != null) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }


    @Override
    public void attentionSuccess(String successMsg) {

        if (isAttention != null && isAttention.equals(1)) {
            isAttention = 0;
            attentionTv.setText("关注");
            CostomToastUtils.getInstance().ToastMessage(getContext(), "取消关注成功");
        } else {
            isAttention = 1;
            attentionTv.setText("已关注");
            CostomToastUtils.getInstance().ToastMessage(getContext(), "关注成功");
        }
    }

    @Override
    public void attentionFailed(String errMsg) {
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 收藏
     *
     * @param successMsg
     */
    @Override
    public void collectSuccess(String successMsg) {
        if (isCollect != null && isCollect.equals(0)) {
//            Toast.makeText(ArticleDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            CostomToastUtils.getInstance().ToastMessage(getContext(), "收藏成功");
            isCollect = 1;
            collectIv.setColorFilter(Color.parseColor("#FF5C5C"));
        } else {
            CostomToastUtils.getInstance().ToastMessage(getContext(), "取消收藏成功");
            isCollect = 0;
            collectIv.setColorFilter(Color.parseColor("#333333"));
        }

    }

    @Override
    public void collectFailed(String errMsg) {
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addArticleCommentSuccess(ArticleCommentBean commentBean) {
        commentsList.add(0, commentBean);
        commentListRecyclrViewAdapter.notifyDataSetChanged();
        moveToComment();
    }

    @Override
    public void addArticleCommentFailed(String errMsg) {
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void articleCommentListSuccess(List<ArticleCommentBean> list) {
        if (list == null || list.size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            getController().currentPagePlus();
        }
        commentsList.addAll(list);
        commentListRecyclrViewAdapter.notifyDataSetChanged();
//        moveToComment();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void articleCommentListFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void articleLikeInfoSuccess(ArticleLikeBean bean) {
        likeList.clear();
        likeList.addAll(bean.getRecords());
        likeUserAmount.setText(bean.getTotal() + "人点赞");
        commentLikeListRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void articleLikeInfoFailed(String errMsg) {
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void articleInfoSuccess(Article article) {
        if (article == null) {
            pageLoadingFail();
            return;
        }
        pageLoadingSuccess();
        this.article = article;
        isLike = (article.getIsLike() == null) ? 0 : article.getIsLike();
        isCollect = (article.getIsCollect() == null) ? 0 : article.getIsCollect();
        isAttention = (article.getIsAttention() == null) ? 0 : article.getIsAttention();
        authorCity.setText(article.getCityName());
        authorDepartments.setText(article.getDeptName());
        authorPosition.setText(article.getJobTitle());
        authorName.setText(article.getSubmiter());
        articleDec.setText(article.getSummary());
        articleTitle.setText(article.getTitle());
//        if (userId == article.getSubmiterId()) {
//            articleAttention.setVisibility(View.GONE);
//        }
        RichText.from(article.getContent()).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(articleBoxRichText);
        if (SharePreferenceManager.getId() == article.getSubmiterId()) {
            attentionTv.setVisibility(View.GONE);
        }
        if (isAttention != null && isAttention.equals(1)) {
            attentionTv.setText("已关注");
        } else {
            attentionTv.setText("关注");
        }

        if (isCollect == null || isCollect == 0) {
            collectIv.setColorFilter(Color.parseColor("#333333"));
        } else {
            collectIv.setColorFilter(Color.parseColor("#FF5C5C"));
        }


        articleReaded.setText(String.valueOf(article.getReadQuantity()));
        articleAttention.setText(String.valueOf(article.getLikeQuantity()));
        articleComment.setText(String.valueOf(article.getCommentQuantity()));
        commentTv.setText(String.valueOf(article.getCommentQuantity()));
        if (article.getLableNames() != null && article.getLableNames().size() > 0) {
            for (int i = 0; i < article.getLableNames().size(); i++) {
                addTag(article.getLableNames().get(i));
            }
        }
    }

    @Override
    public void articleInfoFailed(String msg) {
        pageLoadingFail();
    }

    @Override
    public void healthInfoSuccess(HealthArticalInfo healthArticalInfo) {

        this.healthArticalInfo = healthArticalInfo;
        if (healthArticalInfo == null) {
            pageLoadingFail();
            return;
        }
        pageLoadingSuccess();
        articleDec.setText(healthArticalInfo.getSummary());
        articleTitle.setText(healthArticalInfo.getTitle());
        if (healthArticalInfo.getContent() == null) {
            healthArticalInfo.setContent("文章内容为空");
        }
        RichText.from(healthArticalInfo.getContent()).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(articleBoxRichText);
        articleReaded.setText(String.valueOf(healthArticalInfo.getPageView()));
        articleComment.setText(String.valueOf(healthArticalInfo.getCommentQuantity()));
        commentTv.setText(String.valueOf(healthArticalInfo.getCommentQuantity()));
    }

    @Override
    public void healthInfoFailed(String msg) {
        pageLoadingFail();
    }

    @Override
    public void healthArticleCommentListSuccess(List<HealthArticleCommentBean> list) {

        refreshLayout.finishLoadMore();
        if (list != null && list.size() != 0) {
            if (detail_page_comment_container.getVisibility() == View.GONE) {
                detail_page_comment_container.setVisibility(View.VISIBLE);
                comment_empty_layout.setVisibility(View.GONE);
            }
            healthCommentsList.addAll(list);
            healthCommentAdapter.notifyDataSetChanged();
            getController().currentPagePlus();

        } else {
            if (healthCommentsList.size() == 0) {
                detail_page_comment_container.setVisibility(View.GONE);
                comment_empty_layout.setVisibility(View.VISIBLE);
                empty_text.setText("暂无评论，快来抢占沙发");
            }

        }
    }

    @Override
    public void healthArticleCommentListFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        detail_page_comment_container.setVisibility(View.GONE);
        comment_empty_layout.setVisibility(View.VISIBLE);
        empty_text.setText("评论加载失败，点击重试");
        comment_empty_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().getHealthArticleComments(getHealthCommentMap());
            }
        });
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addHealthArticleCommentSuccess(HealthArticleCommentBean commentBean) {
        if (detail_page_comment_container.getVisibility() == View.GONE) {
            detail_page_comment_container.setVisibility(View.VISIBLE);
            comment_empty_layout.setVisibility(View.GONE);
        }
      /*  healthCommentsList.add(0, commentBean);
        healthCommentAdapter.notifyDataSetChanged();
        moveToComment();*/
        healthCommentsList.clear();
        moveToComment();
        getController().setCurrentPage(1);
        getController().getHealthArticleComments(getHealthCommentMap());
    }

    @Override
    public void addHealthArticleCommentFailed(String errMsg) {
        Toast.makeText(ArticleDetailActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getNoticeInfoSuccess(NoticeInfoBean bean) {
        pageLoadingSuccess();
        authorCity.setText(bean.getDoctorCity());
        authorDepartments.setText(bean.getDoctorDept());
        authorPosition.setText(bean.getDoctorJobTitle());
        authorName.setText(bean.getPublisher());
        articleTitle.setText(bean.getTitle());
        articleReaded.setText(String.valueOf(bean.getPublisher()));
        isAttention = bean.getAttention();
        RichText.from(bean.getContent()).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(articleBoxRichText);
        if (isAttention != null && isAttention.equals(1)) {
            attentionTv.setText("已关注");
        } else {
            attentionTv.setText("关注");
        }

    }

    @Override
    public void getNoticeInfoFailed(String msg) {
        pageLoadingFail();
    }

    @Override
    public Integer getCollect() {
        return isLike;
    }

    @Override
    public HashMap<String, Object> getCollectMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctorId", userId);
        map.put("articleId", articleId);
        map.put("isCollect", (isCollect == null || isCollect == 0) ? 1 : 0);
        return map;
    }

    @Override
    public HashMap<String, Object> getCommentMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("currentPage", getController().getCurrentPage());
        map.put("PageSize", 8);
        return map;
    }

    @Override
    public HashMap<String, Object> getHealthCommentMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("healthKnowledgeId", articleId);
        map.put("currentPage", getController().getCurrentPage());
        map.put("PageSize", 8);
        map.put("registerUserId", userId);
        return map;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public HashMap<String, Object> getDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("id", articleId);
        return map;
    }

    @Override
    public HashMap<String, Object> getHealthDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", articleId);
        return map;
    }

    @Override
    public HashMap<String, Object> getAttentionMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctorId", userId);
        map.put("befocusedDoctorId", article.getSubmiterId());
        map.put("isAttention", ((isAttention != null && isAttention.equals(1)) ? 0 : 1));
        return map;
    }

    @Override
    public HashMap<String, Object> getCommentLikeMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("doctorImage", userIcon);
        return map;
    }

    @Override
    public HashMap<String, Object> getLikeInfoMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("currentPage", 1);
        map.put("pageSize", 10);
        return map;
    }

    @Override
    public HashMap<String, Object> getNoticeDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", articleId);
        map.put("hospitalId", hospitalId);
        return map;
    }


    private void addTag(String tag) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.tag_tv_item, null, true);
        TextView tagTv = view.findViewById(R.id.tag_tv);
        tagTv.setText(tag);
        tagRv.addView(view);
    }

    @Override
    protected void onDestroy() {
        if (healthCommentAdapter != null)
            healthCommentAdapter.deletedDialog();
        super.onDestroy();

        RichText.clear(this);
    }

    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                Toast.makeText(getContext(), "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        }
    };


}

