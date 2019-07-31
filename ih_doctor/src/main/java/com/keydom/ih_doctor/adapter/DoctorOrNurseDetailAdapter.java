package com.keydom.ih_doctor.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_doctor.bean.DoctorEvaluateItem;
import com.keydom.ih_doctor.bean.DoctorHeadItem;
import com.keydom.ih_doctor.bean.DoctorTeamItem;
import com.keydom.ih_doctor.bean.DoctorTextItem;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.view.RatingBarView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/3 on 14:41
 * des:我的医生适配器
 */
public class DoctorOrNurseDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {
    public final static int TYPE_HEAD = 0;
    public final static int TYPE_TEXT = 1;
    public final static int TYPE_TEAM = 2;
    public final static int TYPE_EVALUATE = 3;
    private List<DoctorTeamItem> mTeams;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DoctorOrNurseDetailAdapter(List<MultiItemEntity> data,List<DoctorTeamItem> teams) {
        super(data);
        if (teams == null){
            teams = new ArrayList<>();
        }
        mTeams = teams;
        addItemType(TYPE_HEAD, R.layout.item_doctor_detail_head);
        addItemType(TYPE_TEXT, R.layout.item_doctor_detail_text);
        addItemType(TYPE_TEAM, R.layout.doctor_team_item);
        addItemType(TYPE_EVALUATE, R.layout.item_doctor_detail_evaluate);
    }

    public void setTeams(List<DoctorTeamItem> teams){
        if (teams == null){
            teams = new ArrayList<>();
        }
        mTeams = teams;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_HEAD:
                DoctorHeadItem headItem = (DoctorHeadItem) item;
                helper.setGone(R.id.expand_icon,headItem.isExpand())
                        .setText(R.id.title,headItem.getTitle()+"");
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    boolean isex = headItem.isExpanded();
                    if (isex) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                helper.setImageResource(R.id.expand_icon, headItem.isExpanded() ? R.mipmap.exa_report_up : R.mipmap.exa_report_down);
                break;

            case TYPE_TEXT:
                DoctorTextItem textItem = (DoctorTextItem) item;
                helper.setText(R.id.content,textItem.getContent()+"");
                break;

            case TYPE_TEAM:
                DoctorTeamItem teamItem = (DoctorTeamItem) item;

                RecyclerView recyclerView = helper.getView(R.id.recyclerView);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                DoctorTeamAdapter adapter = new DoctorTeamAdapter(mTeams);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (view.getId() == R.id.group){
                            DoctorOrNurseDetailActivity.startNursePage(mContext,mTeams.get(position).getUuid());
                        }
                    }
                });
//                DoctorTeamItem teamItem = (DoctorTeamItem) item;
//                helper.setText(R.id.job,teamItem.getJobTitle())
//                        .setText(R.id.name,teamItem.getName())
//                        .addOnClickListener(R.id.group);
//                GlideUtils.load((CircleImageView)helper.getView(R.id.head_img), Const.IMAGE_HOST+teamItem.getAvatar(),0,0,true,null);
                break;

            case TYPE_EVALUATE:
                DoctorEvaluateItem evaluateItem = (DoctorEvaluateItem) item;
                String grade ="";
                switch (evaluateItem.getGrade()){
                    case 1:
                        grade = "不满意";
                        break;
                    case 2:
                        grade = "不太满意";
                        break;
                    case 3:
                        grade = "一般";
                        break;
                    case 4:
                        grade = "比较满意";
                        break;
                    case 5:
                        grade = "很满意";
                        break;
                }
                TagFlowLayout flowLayout = helper.getView(R.id.tags);
                RatingBarView ratingBarView = helper.getView(R.id.evaluate_rb);
                if (evaluateItem.getLabels()==null){
                    evaluateItem.setLabels(new ArrayList<>());
                }
                TagAdapter<String> tagAdapter = new TagAdapter<String>(evaluateItem.getLabels()) {
                    @Override
                    public View getView(FlowLayout parent, int position, String o) {
                        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_doctor_evaluate_tag,
                                flowLayout, false);
                        tv.setText(o);
                        return tv;
                    }
                };
                flowLayout.setAdapter(tagAdapter);
                ratingBarView.setStar(evaluateItem.getGrade());
                ratingBarView.setClickable(false);
                GlideUtils.load(helper.getView(R.id.head_img),Const.IMAGE_HOST+evaluateItem.getAvatar(),0,0,true,null);
                helper.setText(R.id.name,evaluateItem.getName()+"")
                        .setText(R.id.time,evaluateItem.getTime()+"")
                        .setText(R.id.goods_num,evaluateItem.getLikeAmount()+"")
                        .addOnClickListener(R.id.isLike)
                        .setText(R.id.grade,grade);

                    boolean isLike = evaluateItem.getIsLike() == 0;
                    helper.setTextColor(R.id.goods_num,isLike?mContext.getResources().getColor(R.color.pay_unselected):mContext.getResources().getColor(R.color.login_btn_color))
                            .setImageResource(R.id.good_evaluate_icon,isLike?R.mipmap.is_like_no_icon:R.mipmap.good_evaluate_icon);
                break;
        }
    }
}
