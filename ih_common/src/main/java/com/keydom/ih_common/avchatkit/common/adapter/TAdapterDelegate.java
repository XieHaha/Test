package com.keydom.ih_common.avchatkit.common.adapter;

public interface TAdapterDelegate {

    public int getViewTypeCount();

    public Class<? extends TViewHolder> viewHolderAtPosition(int position);

    public boolean enabled(int position);
}