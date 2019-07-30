package com.keydom.ih_common.utils.permissions

/**
 * 选择对话框（两个按钮）
 */
//class CommonChooseDialog(context: Context,
//                         private val titleStr: CharSequence,
//                         private val messageStr: CharSequence,
//                         private val leftStr: CharSequence,
//                         private val rightStr: CharSequence,
//                         private var listener: OnClickListener?) : Dialog(context, R.style.Dialog_Common) {
//
//    private val content: TextView by lazy { findViewById<TextView>(R.id.content_tv) }
//    private val left: TextView by bindView(R.id.left_tv)
//    private val right: TextView by bindView(R.id.right_tv)
//    private val title: TextView by bindView(R.id.tv_title)
//
//    init {
//        setCancelable(false)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.common_dialog_choose)
//        content.text = messageStr
//        left.text = leftStr
//        if (titleStr.isNotEmpty()) {
//            title.text = titleStr
//            title.visibility = View.VISIBLE
//        }
//        left.setOnClickListener {
//            listener?.onClick(true)
//            dismiss()
//        }
//        right.text = rightStr
//        right.setOnClickListener {
//            listener?.onClick(false)
//            dismiss()
//        }
//    }
//
//    companion object {
//        interface OnClickListener {
//            fun onClick(isLeft: Boolean)
//        }
//
//        fun show(context: Context, message: CharSequence,
//                 left: CharSequence = context.getString(R.string.common_dialog_choose_left_button_text),
//                 right: CharSequence = context.getString(R.string.common_dialog_choose_right_button_text),
//                 listener: OnClickListener? = null) {
//            CommonChooseDialog(context, "", message, left, right, listener).show()
//        }
//
//        fun show(context: Context, message: CharSequence,
//                 left: CharSequence = context.getString(R.string.common_dialog_choose_left_button_text),
//                 right: CharSequence = context.getString(R.string.common_dialog_choose_right_button_text),
//                 listener: (isLeft: Boolean) -> Unit) {
//            val dialog = CommonChooseDialog(context, "", message, left, right, null)
//            val clickListener: OnClickListener = object : OnClickListener {
//                override fun onClick(isLeft: Boolean) {
//                    dialog.dismiss()
//                    listener(isLeft)
//                }
//            }
//            dialog.listener = clickListener
//            dialog.show()
//        }
//
//        fun show(context: Context, titleStr: CharSequence, message: CharSequence,
//                 left: CharSequence = context.getString(R.string.common_dialog_choose_left_button_text),
//                 right: CharSequence = context.getString(R.string.common_dialog_choose_right_button_text),
//                 listener: (isLeft: Boolean) -> Unit) {
//            val clickListener: OnClickListener = object : OnClickListener {
//                override fun onClick(isLeft: Boolean) = listener(isLeft)
//            }
//            CommonChooseDialog(context, titleStr, message, left, right, clickListener).show()
//        }
//    }
//}