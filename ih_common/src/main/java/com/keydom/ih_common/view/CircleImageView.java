package com.keydom.ih_common.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

        private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Bitmap mRawBitmap;
        private BitmapShader mShader;
        private Matrix mMatrix = new Matrix();
    public CircleImageView(Context context){
        super(context);
    }
        public CircleImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public CircleImageView(Context context, AttributeSet attrs, int defStyle){
            super(context,attrs,defStyle);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            Bitmap rawBitmap = getBitmap(getDrawable());
            if (rawBitmap != null){
                int viewWidth = getWidth();
                int viewHeight = getHeight();
                int viewMinSize = Math.min(viewWidth, viewHeight);
                float dstWidth = viewMinSize-2;
                float dstHeight = viewMinSize-2;
                if (mShader == null || !rawBitmap.equals(mRawBitmap)){
                    mRawBitmap = rawBitmap;
                    mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                }
                if (mShader != null){
                    mMatrix.setScale(dstWidth / rawBitmap.getWidth(), dstHeight / rawBitmap.getHeight());
                    mShader.setLocalMatrix(mMatrix);
                }
                mPaintBitmap.setShader(mShader);
                mPaintBitmap.setAntiAlias(true);
                float radius = viewMinSize / 2.0f-2;
                canvas.drawCircle(radius, radius, radius-4, mPaintBitmap);
            } else {
                super.onDraw(canvas);
            }
        }

        private Bitmap getBitmap(Drawable drawable){
            if (drawable instanceof BitmapDrawable){
                return ((BitmapDrawable)drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable){
                Rect rect = drawable.getBounds();
                int width = rect.right - rect.left-20;
                int height = rect.bottom - rect.top-20;
                int color = ((ColorDrawable)drawable).getColor();
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
                return bitmap;
            } else {
                return null;
            }
        }
    }
