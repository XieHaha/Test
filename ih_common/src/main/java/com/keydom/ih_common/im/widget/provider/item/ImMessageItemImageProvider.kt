package com.keydom.ih_common.im.widget.provider.item

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.utils.GlideUtils
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment
import java.lang.ref.WeakReference

class ImMessageItemImageProvider(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs), MessageProvider {

    private var mWeakBitmap: WeakReference<Bitmap>? = null
    private var mShardWeakBitmap: WeakReference<Bitmap>? = null

    init {
        this.setPadding(0, 0, 0, 0)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        var bitmap: Bitmap? = mWeakBitmap?.get()
        val drawable = drawable
        val background = background
        if (bitmap != null && !bitmap.isRecycled) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
            getShardImage(background, bitmap, canvas)
        } else {
            val width = width
            val height = height
            if (width <= 0 || height <= 0) {
                return
            }
            try {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                System.gc()
            }
            if (bitmap != null) {
                val rCanvas = Canvas(bitmap)
                if (drawable != null) {
                    drawable.setBounds(0, 0, width, height)
                    drawable.draw(rCanvas)
                    if (background != null && background is NinePatchDrawable) {
                        background.setBounds(0, 0, width, height)
                        val maskPaint = background.paint
                        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
                        background.draw(rCanvas)
                    }

                    mWeakBitmap = WeakReference(bitmap)
                }
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
                getShardImage(background, bitmap, canvas)
            }
        }
    }

    private fun getShardImage(drawable_bg: Drawable, bp: Bitmap, canvas: Canvas) {
        val width = bp.width
        val height = bp.height
        var bitmap: Bitmap? = mShardWeakBitmap?.get()
        if (width > 0 && height > 0) {
            if (bitmap != null && !bitmap.isRecycled) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
            } else {
                try {
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                } catch (var14: OutOfMemoryError) {
                    var14.printStackTrace()
                    System.gc()
                }
                if (bitmap != null) {
                    val rCanvas = Canvas(bitmap)
                    val paint = Paint()
                    paint.isAntiAlias = true
                    val rect = Rect(0, 0, width, height)
                    val rectF = Rect(1, 1, width - 1, height - 1)
                    val drawableIn = BitmapDrawable(resources, bp)
                    drawableIn.bounds = rectF
                    drawableIn.draw(rCanvas)
                    if (drawable_bg is NinePatchDrawable) {
                        drawable_bg.bounds = rect
                        val maskPaint = drawable_bg.paint
                        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
                        drawable_bg.draw(rCanvas)
                    }

                    mShardWeakBitmap = WeakReference(bitmap)
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint)
                }
            }

        }
    }

    override fun onDetachedFromWindow() {
        var bitmap: Bitmap?
        if (mWeakBitmap != null) {
            bitmap = mWeakBitmap?.get()
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            mWeakBitmap = null
        }
        if (mShardWeakBitmap != null) {
            bitmap = mShardWeakBitmap?.get()
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            mShardWeakBitmap = null
        }
        super.onDetachedFromWindow()
    }

    override fun invalidate() {
        var bitmap: Bitmap?
        if (mWeakBitmap != null) {
            bitmap = mWeakBitmap?.get()
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            mWeakBitmap = null
        }
        if (mShardWeakBitmap != null) {
            bitmap = mShardWeakBitmap?.get()
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            mShardWeakBitmap = null
        }
        super.invalidate()
    }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val imageAttachment = message.message.attachment as ImageAttachment
        if (imageAttachment.path!=null) {
            GlideUtils.load(this, imageAttachment.path, 0, 0, false, null)
        } else {
            GlideUtils.load(this, imageAttachment.thumbUrl, 0, 0, false, null)
        }
    }
}
