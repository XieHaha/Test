package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.manager.AudioPlayerManager
import com.keydom.ih_common.im.manager.AudioRecorderManager
import com.keydom.ih_common.im.model.ImUIMessage
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallbackWrapper
import com.netease.nimlib.sdk.ResponseCode
import com.netease.nimlib.sdk.media.player.OnPlayListener
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment
import com.netease.nimlib.sdk.msg.model.IMMessage


/**
 * 音频
 */
class ImMessageItemVoiceProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    private val img: ImageView by lazy { findViewById<ImageView>(R.id.im_message_voice_image) }
    private val left: TextView by lazy { findViewById<TextView>(R.id.im_message_voice_time_left) }
    private val right: TextView by lazy { findViewById<TextView>(R.id.im_message_voice_time_right) }

    private lateinit var time: TextView
    private var animationDrawable: AnimationDrawable? = null
    private var rigidDrawable: Drawable? = null

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
        left.visibility = GONE
        right.visibility = VISIBLE
        time = right
        img.scaleType = ImageView.ScaleType.FIT_START
        animationDrawable = ContextCompat.getDrawable(context, R.drawable.im_anim_voice_receive) as AnimationDrawable
        rigidDrawable = ContextCompat.getDrawable(context, R.mipmap.im_voice_receive)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
        left.visibility = VISIBLE
        right.visibility = GONE
        time = left
        img.scaleType = ImageView.ScaleType.FIT_END
        animationDrawable = ContextCompat.getDrawable(context, R.drawable.im_anim_voice_sent) as AnimationDrawable
        rigidDrawable = ContextCompat.getDrawable(context, R.mipmap.im_voice_sent)
    }

    override fun bindView(message: ImUIMessage) {
        val audioAttachment = message.message.attachment as AudioAttachment
        val duration = audioAttachment.duration / 1000
        val minLength = 30
        img.layoutParams.width = ((duration * (150f / AudioRecorderManager.getInstance().maxDuration) + minLength) * context.resources.displayMetrics.density).toInt()
        time.text = String.format("%s\"", duration)
        setLayout(false)
        if (audioAttachment.path == null) {
            downloadAttachment(message.message)
        }
    }

    private fun downloadAttachment(content: IMMessage) {
        NIMClient.getService(MsgService::class.java).downloadAttachment(content, false)
                .setCallback(object : RequestCallbackWrapper<Any>() {
                    override fun onResult(code: Int, result: Any?, exception: Throwable?) {
                        if (code == ResponseCode.RES_SUCCESS.toInt()) {
                            return
                        } else {
                            downloadAttachment(content)
                        }
                    }
                })
    }

    fun play(audioPath: String) {
        val listener = object : OnPlayListener {
            override fun onPrepared() {

            }

            override fun onCompletion() {
                setLayout(false)
            }

            override fun onInterrupt() {
                setLayout(false)
            }

            override fun onError(error: String?) {
                setLayout(false)
            }

            override fun onPlaying(curPosition: Long) {
                setLayout(true)
            }
        }
        AudioPlayerManager.getInstance()
                .setDataSource(audioPath)
                .setOnPlayListener(listener)
        if (AudioPlayerManager.getInstance().isPlaying) {
            AudioPlayerManager.getInstance().stopPlay()
        }
        AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC)
//        val playingUri = AudioPlayManager.getInstance().playingUri
//        if (playingUri != null && playingUri == audioPath) {
//            AudioPlayManager.getInstance().stopPlay()
//        } else {
//            AudioPlayManager.getInstance().startPlay(context, audioPath, object : IAudioPlayListener {
//                override fun onStart(url: Uri) {
//                    setLayout(true)
//                }
//
//                override fun onStop(url: Uri) {
//                    setLayout(false)
//                }
//
//                override fun onComplete(url: Uri) {
//                    setLayout(false)
//                }
//
//            })
//        }
    }

    private fun setLayout(playing: Boolean) {
        if (playing) {
            img.setImageDrawable(animationDrawable)
            animationDrawable?.start()
        } else {
            img.setImageDrawable(rigidDrawable)
            animationDrawable?.stop()
        }
    }
}