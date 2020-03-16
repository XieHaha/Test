package com.keydom.mianren.ih_doctor.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.keydom.mianren.ih_doctor.R;


public class SlipButton extends View implements OnTouchListener {
    private boolean mEnabled = true;
    public boolean flag = false;
    public boolean mNowChoose = true;
    private boolean mOnSlip = false;
    public float DownX = 0f, NowX = 0f;
    private Rect Btn_On, Btn_Off;

    private boolean isChgLsnOn = false;
    private OnChangedListener mListener;
    private Bitmap bg_on, bg_off, slip_btn;
    private int mId;
    private Matrix mMatrix = new Matrix();
    private Paint mPaint = new Paint();

    public SlipButton(Context context) {
        super(context);
        init();
    }

    public SlipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setChecked(boolean fl) {
        if (fl) {
            flag = true;
            mNowChoose = true;
            NowX = bg_on.getWidth() - slip_btn.getWidth() / 2 - 5;
        } else {
            flag = false;
            mNowChoose = false;
            NowX = 5;
        }
        invalidate();
    }

    public void setEnabled(boolean b) {
        mEnabled = b;
    }

    private void init() {
        bg_on = BitmapFactory.decodeResource(getResources(), R.mipmap.consulting_open_bg);
        bg_off = BitmapFactory.decodeResource(getResources(), R.mipmap.consulting_close_bg);
        slip_btn = BitmapFactory.decodeResource(getResources(), R.mipmap.consulting_action_icon);
        Btn_On = new Rect(0, 0, slip_btn.getWidth(), slip_btn.getHeight());
        Btn_Off = new Rect(
                bg_off.getWidth() - slip_btn.getWidth(),
                0,
                bg_off.getWidth(),
                slip_btn.getHeight());
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x;
        if (flag) {
            NowX = bg_on.getWidth() - slip_btn.getWidth() / 2 - 5;
            flag = false;
        }
        if (NowX < (bg_on.getWidth() / 2)) {
            canvas.drawBitmap(bg_off, mMatrix, mPaint);
        } else {
            canvas.drawBitmap(bg_on, mMatrix, mPaint);
        }

        if (mOnSlip) {
            if (NowX >= bg_on.getWidth()) {
                x = bg_on.getWidth() - slip_btn.getWidth() / 2 - 5;
            } else {
                x = NowX - slip_btn.getWidth() / 2;
            }
        } else {
            if (mNowChoose) {
                x = Btn_Off.left - 5;
            } else {
                x = Btn_On.left + 5;
            }
        }
        if (x < 0) {
            x = 5;
        } else if (x > bg_on.getWidth() - slip_btn.getWidth()) {
            x = bg_on.getWidth() - slip_btn.getWidth();
        }
        canvas.drawBitmap(slip_btn, x, 3, mPaint);
    }


    public boolean onTouch(View v, MotionEvent event) {
        if (!mEnabled) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mOnSlip = true;
                NowX = event.getX();
                break;
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > bg_on.getWidth() || event.getY() > bg_on.getHeight()) {
                    return false;
                }
                DownX = event.getX();
                NowX = DownX;
                break;
            case MotionEvent.ACTION_UP:
                mOnSlip = false;
                boolean LastChoose = mNowChoose;
                mNowChoose = event.getX() >= bg_on.getWidth() / 2;
                if (isChgLsnOn && (LastChoose != mNowChoose)) {
                    mListener.onChanged(mId, mNowChoose);
                }
                break;
            default:

        }
        invalidate();
        return true;
    }

    public void setOnChangedListener(int id, OnChangedListener l) {
        mId = id;
        isChgLsnOn = true;
        mListener = l;
    }

    public interface OnChangedListener {
        public void onChanged(int id, boolean checkState);
    }
}
