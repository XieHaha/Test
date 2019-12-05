package com.keydom.ih_common.im.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.R;
import com.keydom.ih_common.event.VoiceInputEvent;
import com.keydom.ih_common.im.adapter.PluginAdapter;
import com.keydom.ih_common.im.emoji.EmojiAdapter;
import com.keydom.ih_common.im.emoji.EmojiBean;
import com.keydom.ih_common.im.emoji.EmojiDao;
import com.keydom.ih_common.im.emoji.EmojiVpAdapter;
import com.keydom.ih_common.im.listener.IExtensionClickListener;
import com.keydom.ih_common.im.listener.IPluginClickListener;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.im.manager.AudioRecorderManager;
import com.keydom.ih_common.im.widget.plugin.CameraPlugin;
import com.keydom.ih_common.im.widget.plugin.ImagePlugin;
import com.luck.picture.lib.PictureSelector;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImExtension extends LinearLayout {

    private ViewGroup mExtensionBar;
    private LinearLayout mMainBar;

    private ImageView mVoiceToggle;

    private ViewGroup mContainerLayout;
    private EditText mEditText;
    private View mVoiceInputToggle;

    private ImageView mEmoticonToggle;
    private ImageView mPluginToggle;

    private LinearLayout homeEmoji;
    private View mPluginSend;
    private PluginAdapter mPluginAdapter = new PluginAdapter();

    /**
     * 语音
     */
    private float mLastTouchY = 0;
    private boolean mUpDirection = false;
    private float mOffsetLimit = 0;

    private IExtensionClickListener mExtensionClickListener = null;
    private AppCompatActivity mActivity = null;
    private boolean collapsed = true;
    private int originalTop = 0;
    private int originalBottom = 0;


    public String accountId;


    private static final String TAG = "ImExtension";

    private IAudioRecordCallback mCallback;


    public ImExtension(Context context) {
        this(context, null);
    }

    public ImExtension(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImExtension(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initView();
        initListener();
        initData();
    }

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
//        AndroidBug5497Workaround.Companion.assistActivity(activity, 0);
    }

    public void setMessageInfo(String accountId) {
        this.accountId = accountId;
    }

    public void setExtensionClickListener(IExtensionClickListener clickListener) {
        mExtensionClickListener = clickListener;
    }

    public void collapseExtension() {
        hidePluginBoard();
        hideEmoticonBoard();
        hideInputKeyBoard(mEditText);
    }

    public boolean isExtensionExpanded() {
        return mPluginAdapter.getVisibility() == VISIBLE /*|| mEmojiAdapter.visibility == View.VISIBLE*/ || isSoftShowing();
    }

    public void setExtensionBarVisibility(int visibility) {
        if (visibility == GONE) {
            hidePluginBoard();
            hideInputKeyBoard(mEditText);
            hideEmoticonBoard();
        }
        mExtensionBar.setVisibility(visibility);
    }

    public PluginAdapter getPluginAdapter() {
        return mPluginAdapter;
    }

    public void startActivityForPluginResult(Intent intent, int requestCode, IPluginModule pluginModule) {
        if ((requestCode & -256) != 0) {
            throw new IllegalArgumentException("requestCode does not over 255.");
        } else {
            int position = mPluginAdapter.getPluginPosition(pluginModule);
            if (mActivity != null) {
                mActivity.startActivityForResult(intent, (position + 1 << 8) + (requestCode & 256));
            }
        }
    }

    public boolean onActivityPluginResult(int requestCode, int resultCode, Intent data) {
        int position = (requestCode >> 8) - 1;
        int reqCode = requestCode & 255;
        IPluginModule pluginModule = mPluginAdapter.getPluginModule(position);
        if (pluginModule != null) {
            if (mExtensionClickListener != null && resultCode == Activity.RESULT_OK) {
                if (pluginModule instanceof ImagePlugin || pluginModule instanceof CameraPlugin) {
                    mExtensionClickListener.onImageResult(PictureSelector.obtainMultipleResult(data), (pluginModule instanceof ImagePlugin) ? IExtensionClickListener.RESULT_IMAGE : IExtensionClickListener.RESULT_VIDEO);
                    hidePluginBoard();
                }
            }
            pluginModule.onActivityResult(reqCode, resultCode, data);
            return true;
        }
        return false;
    }

    public static final int EVERY_PAGE_SIZE = 21;
    private List<EmojiBean> mListEmoji;

    public void bindEmojiData(ViewGroup viewGroup) {
        mListEmoji = EmojiDao.getInstance().getEmojiBean();
        homeEmoji = (LinearLayout) viewGroup.findViewById(R.id.home_emoji);
        ViewPager vpEmoji = (ViewPager) viewGroup.findViewById(R.id.vp_emoji);
        final IndicatorView indEmoji = (IndicatorView) viewGroup.findViewById(R.id.ind_emoji);
        LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //将RecyclerView放至ViewPager中：
        int pageSize = EVERY_PAGE_SIZE;
        EmojiBean mEmojiBean = new EmojiBean();
        mEmojiBean.setId(0);
        mEmojiBean.setUnicodeInt(000);
        int deleteCount = (int) Math.ceil(mListEmoji.size() * 1.0 / EVERY_PAGE_SIZE);//要显示的删除键的数量
        //添加删除键
        for (int i = 1; i < deleteCount + 1; i++) {
            if (i == deleteCount) {
                mListEmoji.add(mListEmoji.size(), mEmojiBean);
            } else {
                mListEmoji.add(i * EVERY_PAGE_SIZE - 1, mEmojiBean);
            }
        }


        int pageCount = (int) Math.ceil((mListEmoji.size()) * 1.0 / pageSize);//一共的页数
        List<View> viewList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面创建一个recycleview
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.item_emoji_vprecy, vpEmoji, false);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
            EmojiAdapter entranceAdapter;
            if (index == pageCount - 1) {
                //最后一页的数据
                List<EmojiBean> lastPageList = mListEmoji.subList(index * EVERY_PAGE_SIZE, mListEmoji.size());
                entranceAdapter = new EmojiAdapter(lastPageList, index, EVERY_PAGE_SIZE);
            } else {
                entranceAdapter = new EmojiAdapter(mListEmoji.subList(index * EVERY_PAGE_SIZE, (index + 1) * EVERY_PAGE_SIZE), index, EVERY_PAGE_SIZE);
            }
            entranceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    EmojiBean mEmojiBean = (EmojiBean) adapter.getData().get(position);
                    if (mEmojiBean.getId() == 0) {
                        //如果是删除键
                        mEditText.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        mEditText.append(((EmojiBean) adapter.getData().get(position)).getUnicodeInt());
                    }


                }
            });
            recyclerView.setAdapter(entranceAdapter);
            viewList.add(recyclerView);
        }
        EmojiVpAdapter adapter = new EmojiVpAdapter(viewList);
        vpEmoji.setAdapter(adapter);
        indEmoji.setIndicatorCount(vpEmoji.getAdapter().getCount());
        indEmoji.setCurrentIndicator(vpEmoji.getCurrentItem());
        vpEmoji.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indEmoji.setCurrentIndicator(position);
            }
        });
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mExtensionBar = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.im_ext_extension_bar, this, false);
        mMainBar = mExtensionBar.findViewById(R.id.im_ext_main_bar);
        mVoiceToggle = mExtensionBar.findViewById(R.id.im_voice_toggle);
        mContainerLayout = mExtensionBar.findViewById(R.id.im_container_layout);
        mEditText = mExtensionBar.findViewById(R.id.im_edit_text);
        mVoiceInputToggle = mContainerLayout.findViewById(R.id.im_audio_input_toggle);
//        mEmoticonToggle = mExtensionBar.findViewById(R.id.im_emoticon_toggle);
        mPluginToggle = mExtensionBar.findViewById(R.id.im_plugin_toggle);

        mPluginSend = mExtensionBar.findViewById(R.id.im_plugin_send);
        bindEmojiData(mExtensionBar);
        addView(mExtensionBar);
    }

    public void hideEmoticonBoard() {
        if (homeEmoji != null)
            homeEmoji.setVisibility(GONE);
    }

    public void setEmoticonBoard() {
        if (homeEmoji != null) {
            homeEmoji.setVisibility(VISIBLE);
            hidePluginBoard();
        }
    }

    private void initListener() {

        EventBus.getDefault().register(this);

        mVoiceToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVoiceInputToggle();
            }
        });

        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mContainerLayout.setSelected(true);
                    hideEmoticonBoard();
                    hidePluginBoard();
                }
            }
        });
//        mEditText.addTextChangedListener(EmojiTextListener(mEditText));
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (mExtensionClickListener != null) {
                    mExtensionClickListener.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPluginToggle.setVisibility(s.length() == 0 ? VISIBLE : GONE);
                mPluginSend.setVisibility(s.length() == 0 ? GONE : VISIBLE);
                if (mExtensionClickListener != null) {
                    mExtensionClickListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mExtensionClickListener != null) {
                    mExtensionClickListener.afterTextChanged(s);
                }
            }
        });
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mPluginSend.performClick();
                }
                return false;
            }
        });

        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (mExtensionClickListener != null) {
                    return mExtensionClickListener.onKey(v, keyCode, event);
                }
                return false;
            }
        });

        mVoiceInputToggle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (AudioPlayerManager.getInstance().isPlaying()) {
                            AudioPlayerManager.getInstance().stopPlay();
                        }
                        initCallback();
                        AudioRecorderManager.getInstance().init(mActivity, mCallback);
                        AudioRecorderManager.getInstance().setTouched(true);
                        AudioRecorderManager.getInstance().onStartAudioRecord();
                        mLastTouchY = event.getY();
                        mUpDirection = false;
                        ((Button) v).setText(R.string.im_ext_extension_bar_audio_input_hover);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mLastTouchY - event.getY() > mOffsetLimit && !mUpDirection) {
                            Log.e(TAG, "mLastTouchY - event.getY()=" + (mLastTouchY - event.getY()));
                            AudioRecorderManager.getInstance().cancelAudioRecord(true);
                            mUpDirection = true;
                            ((Button) v).setText(R.string.im_ext_extension_bar_audio_input);
                            AudioRecorderManager.getInstance().setUpDirection(mUpDirection);
                        } else if (event.getY() - mLastTouchY > -mOffsetLimit && mUpDirection) {
                            Log.e(TAG, "event.getY() - mLastTouchY=" + (event.getY() - mLastTouchY));
                            mUpDirection = false;
                            ((Button) v).setText(R.string.im_ext_extension_bar_audio_input_hover);
                            AudioRecorderManager.getInstance().setUpDirection(mUpDirection);
                            AudioRecorderManager.getInstance().cancelAudioRecord(false);
                        }
                        AudioRecorderManager.getInstance().setTouched(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (mLastTouchY - event.getY() > mOffsetLimit) {
                            AudioRecorderManager.getInstance().onEndAudioRecord(true);
                        } else /*if (event.getY() - mLastTouchY > -mOffsetLimit)*/ {
                            AudioRecorderManager.getInstance().onEndAudioRecord(false);
                        }
                        ((Button) v).setText(R.string.im_ext_extension_bar_audio_input);
                        AudioRecorderManager.getInstance().setTouched(false);
                        break;
                    default:
                }
                return false;
            }
        });
/*        mEmoticonToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                setEmoticonBoard();
            }
        });*/
        mPluginToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPluginBoard();
            }
        });

        mPluginSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                mEditText.getText().clear();
                mEditText.setText("");
                if (mExtensionClickListener != null) {
                    mExtensionClickListener.onSendToggleClick(mPluginSend, text);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(VoiceInputEvent voiceInputEvent) {
        if(null != voiceInputEvent && !TextUtils.isEmpty(voiceInputEvent.getVoiceStr())){
            if(TextUtils.isEmpty(mEditText.getText().toString())){
                mEditText.setText(voiceInputEvent.getVoiceStr());
                mEditText.setSelection(mEditText.getText().length());
            }else{
                mEditText.setText(mEditText.getText().toString() + voiceInputEvent.getVoiceStr());
                mEditText.setSelection(mEditText.getText().length());
            }

        }
    }

    private void initCallback() {
        mCallback = new IAudioRecordCallback() {
            @Override
            public void onRecordReady() {
                Log.e(TAG, "onRecordReady");
            }

            @Override
            public void onRecordStart(File audioFile, RecordType recordType) {
                Log.e(TAG, "onRecordStart");
                AudioRecorderManager.getInstance().setStarted(true);
                if (!AudioRecorderManager.getInstance().isTouched()) {
                    return;
                }
                AudioRecorderManager.getInstance().initAudioPopView(mVoiceToggle.getRootView());
            }

            @Override
            public void onRecordSuccess(File audioFile, long audioLength, RecordType recordType) {
                Log.e(TAG, "onRecordSuccess");
                if (audioLength < 1000) {
                    AudioRecorderManager.getInstance().setTimeShortView();
                } else {
                    if (mExtensionClickListener != null) {
                        mExtensionClickListener.onVoiceResult(audioLength, audioFile.getPath());
                    }
                }
            }

            @Override
            public void onRecordFail() {
                Log.e(TAG, "onRecordFail");
            }

            @Override
            public void onRecordCancel() {
                Log.e(TAG, "onRecordCancel");
            }

            @Override
            public void onRecordReachedMaxTime(int maxTime) {
                Log.e(TAG, "onRecordReachedMaxTime");
                AudioRecorderManager.getInstance().setTimeoutView(-1);
                AudioRecorderManager.getInstance().getAudioRecorder().handleEndRecord(true, maxTime);

            }
        };
    }

    private void initData() {
        /*mEmojiAdapter.setOnItemClickListener(object : IEmojiItemClickListener {
            @SuppressLint("SetTextI18n")
            override fun onEmojiClick(str: String) {
                mEditText.setText("${mEditText.text}$str")
            }

            override fun onDeleteClick() {
                val keyCode = KeyEvent.KEYCODE_DEL
                val keyEventDown = KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
                val keyEventUp = KeyEvent(KeyEvent.ACTION_UP, keyCode)
                mEditText.onKeyDown(keyCode, keyEventDown)
                mEditText.onKeyUp(keyCode, keyEventUp)
            }
        })*/

        mPluginAdapter.setOnPluginClickListener(new IPluginClickListener() {
            @Override
            public void onClick(IPluginModule pluginModule, int position) {
                pluginModule.onClick(mActivity, ImExtension.this);
            }
        });
        mOffsetLimit = 70f * getResources().getDisplayMetrics().density;
        addPlugin(new ImagePlugin());
    }

    public void addPlugin(IPluginModule pluginModule) {
        mPluginAdapter.addPlugin(pluginModule);
    }

    public void removePlugin(IPluginModule pluginModule) {
        mPluginAdapter.removePlugin(pluginModule);
    }

    /**
     * 加号菜单相关
     */
    private void setPluginBoard() {
        if (mPluginAdapter.isInitialZed()) {
            if (mPluginAdapter.getVisibility() == VISIBLE) {
                hidePluginBoard();
//                showInputKeyBoard(mEditText);
            } else {
                showPluginBoard();
            }
        } else {
            mPluginAdapter.bindView(this);
            showPluginBoard();
        }
        mEditText.setVisibility(VISIBLE);
    }

    private void showPluginBoard() {
        hideVoiceInputToggle();
        hideInputKeyBoard(mEditText);
        hideEmoticonBoard();
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPluginAdapter.setVisibility(View.VISIBLE);
            }
        }, isSoftShowing() ? 200 : 0);
        mContainerLayout.setSelected(false);
    }

    private boolean isSoftShowing() {
        if (mActivity != null) {
            int screenHeight = mActivity.getWindow().getDecorView().getHeight();
            Rect rect = new Rect();
            mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return screenHeight * 2 / 3 > rect.bottom;
        }
        return false;
    }

    /**
     * 语音相关
     */
    private void setVoiceInputToggle() {
        if (mVoiceInputToggle.getVisibility() == GONE) {
            showVoiceInputToggle();
        } else {
            hideVoiceInputToggle();
            showInputKeyBoard(mEditText);
        }
    }


    private void hideVoiceInputToggle() {
        mVoiceInputToggle.setVisibility(GONE);
        mEditText.setVisibility(VISIBLE);
        mVoiceToggle.setImageResource(R.drawable.im_voice_toggle_selector);
        mContainerLayout.setClickable(false);
        mContainerLayout.setSelected(true);
    }

    private void showVoiceInputToggle() {
        hideInputKeyBoard(mEditText);
        hidePluginBoard();
        hideEmoticonBoard();
        mEditText.setVisibility(GONE);
        mVoiceInputToggle.setVisibility(VISIBLE);
        mVoiceToggle.setImageResource(R.drawable.im_keyboard_selector);
        mContainerLayout.setClickable(true);
        mContainerLayout.setSelected(false);
    }

    private void hidePluginBoard() {
        mPluginAdapter.setVisibility(GONE);
        mContainerLayout.setSelected(true);
    }

    private void showInputKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideInputKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        mEditText.clearFocus();
    }

    private boolean isFristLayout = true;

    @Override
    protected void onLayout(boolean changed, int l, final int t, int r, final int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isFristLayout) {
            if (originalTop != 0) {
                if (originalTop > t) {
                    if (originalBottom > b && mExtensionClickListener != null && collapsed) {
                        collapsed = false;
                        mExtensionClickListener.onExtensionExpanded(originalBottom - t);
                    } else if (collapsed && mExtensionClickListener != null) {
                        collapsed = false;
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                mExtensionClickListener.onExtensionExpanded(b - t);
                            }
                        });
                    }
                } else if (!collapsed && mExtensionClickListener != null) {
                    collapsed = true;
                    mExtensionClickListener.onExtensionCollapsed();
                }
            } else {
                originalTop = t;
                originalBottom = b;
            }
        } else {
            isFristLayout = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mExtensionClickListener = null;
        hideInputKeyBoard(mEditText);
        AudioPlayerManager.getInstance().stopPlay();
        super.onDetachedFromWindow();
    }

    public void onPause() {
        if (AudioRecorderManager.getInstance().getAudioRecorder() != null) {
            AudioRecorderManager.getInstance().onEndAudioRecord(true);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (AudioRecorderManager.getInstance().getAudioRecorder() != null) {
            AudioRecorderManager.getInstance().getAudioRecorder().destroyAudioRecorder();
        }
    }
}
