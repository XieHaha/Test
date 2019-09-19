package com.keydom.ih_patient.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.ContentFragmentAdapter;
import com.keydom.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.ih_patient.view.MyViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment {
    private View view;
    private TextView textView;
    private static final String KEY = "title";
    private static final String POS = "pos";
    private static final String MY = "myViewPager";
    private RecyclerView mRecyclerView;
    private ContentFragmentAdapter mContentFragmentAdapter;
    private List<PrescriptionItemEntity> prescriptionItemEntities = new ArrayList<>();
    private String mPrice = null;
    private TextView mTvPrice;

    public MyViewPager getmYViewPager() {
        return mYViewPager;
    }

    public void setmYViewPager(MyViewPager mYViewPager) {
        this.mYViewPager = mYViewPager;
    }

    private MyViewPager mYViewPager;

    @SuppressLint("ValidFragment")
    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(List<PrescriptionItemEntity> data, int pos,MyViewPager vp) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, (Serializable) data);
        bundle.putInt(POS, pos);
        fragment.setArguments(bundle);
        fragment.setmYViewPager(vp);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_content, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_title);
        mTvPrice = view.findViewById(R.id.tv_subtotal_price);
        Bundle arguments = getArguments();
        if (arguments != null) {
            prescriptionItemEntities = (List<PrescriptionItemEntity>) arguments.getSerializable(KEY);
            int position = arguments.getInt(POS);
            double sumFee = 0;
            for(PrescriptionItemEntity prescriptionItemEntity : prescriptionItemEntities){
                sumFee += Double.valueOf(prescriptionItemEntity.getFee());
            }
            mTvPrice.setText("￥" + String.format("%.2f", sumFee));

         mYViewPager.setObjectForPosition(view,position);
        }

        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        mRecyclerView.setLayoutManager(layoutmanager);
        mContentFragmentAdapter = new ContentFragmentAdapter(this);
        mRecyclerView.setAdapter(mContentFragmentAdapter);
        mContentFragmentAdapter.setList(prescriptionItemEntities);
        return view;

    }

}
