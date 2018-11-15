package com.example.android.foodtinder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private Drawable deleteIcon;
    private ColorDrawable background;
    private int intrinsicWidth, intrinsicHeight,backgroundColor;
    private Paint clearPaint;

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs,Context context) {
        super(dragDirs, swipeDirs);
        deleteIcon = ContextCompat.getDrawable(context,R.drawable.ic_delete_black_24dp);
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        background = new ColorDrawable();
        backgroundColor = context.getResources().getColor(R.color.bg_row_background);
        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    //don't move up or down
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    //todo: bug--->when swipe cancelled, the delete icon will be there forever:(
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();
        boolean isCancelled = dX == 0f && !isCurrentlyActive;

        if(isCancelled){
            clearCanvas(c,itemView.getRight() + dX,(float)itemView.getTop(),(float)itemView.getRight(),(float)itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        //draw the red delete background
        background.setColor(backgroundColor);
        background.setBounds(itemView.getRight() + (int)dX,itemView.getTop(),itemView.getRight(),itemView.getBottom());
        background.draw(c);

        // Calculate position of delete icon
        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        //draw the delete icon
        deleteIcon.setBounds(deleteIconLeft,deleteIconTop,deleteIconRight,deleteIconBottom);
        deleteIcon.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }

    private void clearCanvas(Canvas c, Float left, Float top,Float right,Float bottom){
        c.drawRect(left,top,right,bottom,clearPaint);
    }


}


























