package com.keydom.ih_common.im.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

public class FullImage extends AppCompatImageView {

    private WeakReference<Bitmap> mWeakBitmap;
    private WeakReference<Bitmap> mShardWeakBitmap;

    public FullImage(Context context) {
        this(context, null);
    }

    public FullImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0, 0, 0, 0);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = null;
        if (mWeakBitmap != null) {
            bitmap = mWeakBitmap.get();
        }
        Drawable drawable = getDrawable();
        Drawable background = getBackground();
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
            getShardImage(background, bitmap, canvas);
        } else {
            int width = getWidth();
            int height = getHeight();
            if (width <= 0 || height <= 0) {
                return;
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            if (bitmap != null) {
                Canvas rCanvas = new Canvas(bitmap);
                if (drawable != null) {
                    drawable.setBounds(0, 0, width, height);
                    drawable.draw(rCanvas);
                    if (background instanceof NinePatchDrawable) {
                        Paint maskPaint = ((NinePatchDrawable) background).getPaint();
                        background.setBounds(0, 0, width, height);
                        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                        background.draw(rCanvas);
                    }
                    mWeakBitmap = new WeakReference<>(bitmap);

                }
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                getShardImage(background, bitmap, canvas);
            }
        }
    }

    private void getShardImage(Drawable drawableBg, Bitmap bp, Canvas canvas) {
        int width = bp.getWidth();
        int height = bp.getHeight();
        Bitmap bitmap = null;
        if (mShardWeakBitmap != null) {
            bitmap = mShardWeakBitmap.get();
        }
        if (width > 0 && height > 0) {
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
            } else {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                if (bitmap != null) {
                    Canvas rCanvas = new Canvas(bitmap);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    Rect rect = new Rect(0, 0, width, height);
                    Rect rectF = new Rect(1, 1, width - 1, height - 1);
                    BitmapDrawable drawableIn = new BitmapDrawable(getResources(), bp);
                    drawableIn.setBounds(rectF);
                    drawableIn.draw(rCanvas);
                    if (drawableBg instanceof NinePatchDrawable) {
                        drawableBg.setBounds(rect);
                        Paint maskPaint = ((NinePatchDrawable) drawableBg).getPaint();
                        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                        drawableBg.draw(rCanvas);
                    }
                    mShardWeakBitmap = new WeakReference<>(bitmap);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Bitmap bitmap;
        if (mWeakBitmap != null) {
            bitmap = mWeakBitmap.get();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            mWeakBitmap = null;
        }
        if (mShardWeakBitmap != null) {
            bitmap = mShardWeakBitmap.get();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            mShardWeakBitmap = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void invalidate() {
        Bitmap bitmap;
        if (mWeakBitmap != null) {
            bitmap = mWeakBitmap.get();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            mWeakBitmap = null;
        }
        if (mShardWeakBitmap != null) {
            bitmap = mShardWeakBitmap.get();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            mShardWeakBitmap = null;
        }
        super.invalidate();
    }
}
