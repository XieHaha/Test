package com.keydom.ih_patient.callback;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.keydom.ih_patient.activity.function_config.controller.FunctionConfigController;
import com.keydom.ih_patient.adapter.FunctionConfigAdapter;

/**
 * 滑动阻力回调类
 */
public class ItemDragCallback extends ItemTouchHelper.Callback {
    private FunctionConfigAdapter selectedFunctionAdapter;
    private FunctionConfigController functionConfigController;
    private boolean isEditing=false;
    public ItemDragCallback(FunctionConfigAdapter selectedFunctionAdapter) {
        this.selectedFunctionAdapter = selectedFunctionAdapter;
    }
    public void changeState(boolean isEditing){
        this.isEditing=isEditing;
        Log.d("TAG","isEditing haschanged");
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        FunctionConfigAdapter adapter= (FunctionConfigAdapter) recyclerView.getAdapter();
        if (!isEditing) {
            Log.d("TAG","不可编辑");
            return 0;
        }
        int position = viewHolder.getLayoutPosition();
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        int position = viewHolder.getLayoutPosition();
        selectedFunctionAdapter.itemMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (dX != 0 && dY != 0 || isCurrentlyActive) {
            FunctionConfigAdapter adapter= (FunctionConfigAdapter) recyclerView.getAdapter();
            //isEditing = adapter.isEditing;
        }
    }
}
