package com.keydom.ih_common.utils.permissions

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R

/**
 * 简单的通用对话框
 */
class CommonDialog(context: Context, themeResId: Int) : Dialog(context, themeResId), View.OnClickListener {

    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var cancel: TextView
    private lateinit var submit: TextView

    private var titleStr: String? = null
    private var contentStr: String? = null
    private var positiveName: String? = null
    private var negativeName: String? = null
    private var listener: OnCloseListener? = null

    constructor(context: Context) : this(context, R.style.dialog)

    fun setTitle(title: String): CommonDialog {
        this.titleStr = title
        return this
    }

    fun setContent(content: String): CommonDialog {
        this.contentStr = content
        return this
    }

    fun setPositiveButton(name: String): CommonDialog {
        this.positiveName = name
        return this
    }

    fun setNegativeButton(name: String): CommonDialog {
        this.negativeName = name
        return this
    }

    fun setListener(listener: OnCloseListener): CommonDialog {
        this.listener = listener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        setCanceledOnTouchOutside(false)
        initView()
    }

    private fun getContentView(): View {
        val parent = LinearLayout(context)
        parent.orientation = LinearLayout.VERTICAL
        parent.setBackgroundResource(R.drawable.bg_dialog_round_white)
        //标题
        title = TextView(context)
        title.setTextColor(context.getColorCompat(R.color.black))
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        title.setPadding(context.dp2px(10f), context.dp2px(20f), context.dp2px(10f), context.dp2px(10f))
        title.gravity = Gravity.CENTER
        parent.addView(title, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        //内容
        content = TextView(context)
        content.setTextColor(Color.parseColor("#424242"))
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        content.setLineSpacing(context.dp2px(3f).toFloat(), 1f)
        content.setPadding(context.dp2px(40f), context.dp2px(10f), context.dp2px(40f), context.dp2px(20f))
        content.gravity = Gravity.CENTER
        parent.addView(content, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        //垂直向分割线
        val v_divider = View(context)
        v_divider.setBackgroundColor(Color.parseColor("#f3f3f3"))
        parent.addView(v_divider, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.dp2px(1f)))
        //按钮容器
        val btn_layout = LinearLayout(context)
        parent.addView(btn_layout, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.dp2px(50f)))
        //取消按钮
        cancel = TextView(context)
        cancel.setBackgroundResource(R.drawable.bg_dialog_left_white)
        cancel.setTextColor(Color.parseColor("#a1a1a1"))
        cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        cancel.gravity = Gravity.CENTER
        btn_layout.addView(cancel, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
        //垂直向分割线
        val h_divider = View(context)
        h_divider.setBackgroundColor(Color.parseColor("#f3f3f3"))
        btn_layout.addView(h_divider, LinearLayout.LayoutParams(context.dp2px(1f), LinearLayout.LayoutParams.MATCH_PARENT))
        //确认按钮
        submit = TextView(context)
        submit.setBackgroundResource(R.drawable.bg_dialog_right_white)
        submit.setTextColor(Color.parseColor("#42369a"))
        submit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        submit.gravity = Gravity.CENTER
        btn_layout.addView(submit, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))

        return parent
    }

    private fun initView() {
        title.text = titleStr
        content.text = contentStr
        submit.text = positiveName
        submit.setOnClickListener(this)
        cancel.text = negativeName
        cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            submit -> listener?.onClick(this, true)
            cancel -> listener?.onClick(this, false)
        }
        dismiss()
    }

    interface OnCloseListener {
        fun onClick(dialog: Dialog, confirm: Boolean)
    }
}


/**
 * dp转px
 * @param dpVal
 */
fun Context.dp2px(dpVal: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics).toInt()

@ColorInt
fun Context.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)