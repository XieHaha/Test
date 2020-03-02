package com.keydom.ih_patient.activity.pregnant_woman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnant_woman.controller.PregnantWomanController;
import com.keydom.ih_patient.activity.pregnant_woman.view.PregnantWomanView;
import com.keydom.ih_patient.adapter.CurriculumTypeAdapter;
import com.keydom.ih_patient.adapter.PregnantWomanAdapter;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.view.ViewPrepared;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date 20/2/28 15:11
 * @des 孕妇学校
 */
public class PregnantWomanActivity extends BaseControllerActivity<PregnantWomanController> implements PregnantWomanView {
    @BindView(R.id.levitate_recycler_view)
    RecyclerView levitateRecyclerView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_online)
    ImageView ivOnline;
    private PregnantWomanAdapter adapter;
    private CurriculumTypeAdapter typeAdapter, levitateAdapter;

    private View headerView;
    private RecyclerView horizontalRecyclerView;

    private ArrayList<String> typeData;
    private ArrayList<String> data;

    /**
     * 计算recyclerView滑动距离
     */
    private int totalDy = 0;
    private int viewHeight, recyHeight;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, PregnantWomanActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnant_woman;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_pregnant_woman_school));

        typeData = new ArrayList<>();
        typeData.add("教程");
        typeData.add("经验/课堂");
        typeData.add("规范");
        typeData.add("资料");
        typeData.add("人文");
        typeData.add("历史");

        //模拟数据
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");

        levitateAdapter = new CurriculumTypeAdapter(R.layout.item_curriculum_type, typeData);
        levitateRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        levitateRecyclerView.setAdapter(levitateAdapter);

        adapter = new PregnantWomanAdapter(R.layout.item_pregnant_woman, data);
        headerView = getLayoutInflater().inflate(R.layout.header_pregnant_woman, null);
        bindHeaderView();
        adapter.addHeaderView(headerView);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(getController());
        ivOnline.setOnClickListener(getController());
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getArticleData(TypeEnum.REFRESH));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getArticleData(TypeEnum.LOAD_MORE));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                Log.i("dun", "dy:" + dy);
                if (totalDy > (viewHeight - recyHeight)) {
                    levitateRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    levitateRecyclerView.setVisibility(View.GONE);

                }
            }
        });

        ivOnline.setOnClickListener(getController());
        getController().getArticleData(TypeEnum.REFRESH);
    }

    /**
     * 绑定头部view
     */
    private void bindHeaderView() {
        horizontalRecyclerView = headerView.findViewById(R.id.horizontal_recycler_view);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        typeAdapter = new CurriculumTypeAdapter(R.layout.item_curriculum_type, typeData);
        horizontalRecyclerView.setAdapter(typeAdapter);
        new ViewPrepared().asyncPrepare(headerView, (w, h) -> viewHeight = h);
        new ViewPrepared().asyncPrepare(horizontalRecyclerView, (w, h) -> recyHeight = h);

    }

    @Override
    public void bindData() {
    }
}
