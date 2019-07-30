package com.keydom.ih_common.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.utils.FloatPermissionManager

/**
 * 悬浮窗授权界面
 */
class FloatPermissionActivity : Activity() {

    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var cancel: TextView
    private lateinit var submit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        content.text = intent.getStringExtra("message")
        cancel.text = "暂不开启"
        cancel.setOnClickListener {
            Log.e("FloatPermissionActivity","false")
            result?.confirmResult(false)
            finish()
        }
        submit.text = "现在去开启"
        submit.setOnClickListener {
            Log.e("FloatPermissionActivity","true")
            result?.confirmResult(true)
            finish()
        }
    }

    private fun getContentView(): View {
        val parent = LinearLayout(this)
        parent.orientation = LinearLayout.VERTICAL
        parent.setBackgroundResource(R.drawable.float_bg_dialog_round_white)
        //标题
        title = TextView(this)
        title.setTextColor(ContextCompat.getColor(this, R.color.black))
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        title.setPadding(this.dp2px(10f), this.dp2px(20f), this.dp2px(10f), this.dp2px(10f))
        title.gravity = Gravity.CENTER
//        parent.addView(title, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        //内容
        content = TextView(this)
        content.setTextColor(Color.parseColor("#424242"))
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        content.setLineSpacing(this.dp2px(3f).toFloat(), 1f)
        content.setPadding(this.dp2px(40f), this.dp2px(10f), this.dp2px(40f), this.dp2px(20f))
        content.gravity = Gravity.CENTER
        parent.addView(content, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        //垂直向分割线
        val v_divider = View(this)
        v_divider.setBackgroundColor(Color.parseColor("#f3f3f3"))
        parent.addView(v_divider, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.dp2px(1f)))
        //按钮容器
        val btn_layout = LinearLayout(this)
        parent.addView(btn_layout, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.dp2px(50f)))
        //取消按钮
        cancel = TextView(this)
        cancel.setBackgroundResource(R.drawable.float_bg_dialog_left_white)
        cancel.setTextColor(Color.parseColor("#a1a1a1"))
        cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        cancel.gravity = Gravity.CENTER
        btn_layout.addView(cancel, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
        //垂直向分割线
        val h_divider = View(this)
        h_divider.setBackgroundColor(Color.parseColor("#f3f3f3"))
        btn_layout.addView(h_divider, LinearLayout.LayoutParams(this.dp2px(1f), LinearLayout.LayoutParams.MATCH_PARENT))
        //确认按钮
        submit = TextView(this)
        submit.setBackgroundResource(R.drawable.float_bg_dialog_right_white)
        submit.setTextColor(Color.parseColor("#42369a"))
        submit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        submit.gravity = Gravity.CENTER
        btn_layout.addView(submit, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))

        return parent
    }

    fun dp2px(dpVal: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics).toInt()

    companion object {
        var result: FloatPermissionManager.OnConfirmResult? = null
    }

}

