package com.keydom.mianren.ih_doctor.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationReceiveActivity;
import com.keydom.mianren.ih_doctor.bean.LoginBean;
import com.keydom.mianren.ih_doctor.bean.UserInfo;
import com.keydom.mianren.ih_doctor.net.LoginApiService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WelcomeActivity extends AppCompatActivity {
    public static void startFromJpush(Context context, boolean isNeedJump) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isNeedJump", isNeedJump);
        context.startActivity(intent);
    }

    public ViewPager mViewPager;
    public RelativeLayout rootView;
    public TextView skip;
    public TextView inBtn;
    private boolean isNeedJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        mViewPager = findViewById(R.id.welcome_vp);
        rootView = findViewById(R.id.welcome_rootview);
        skip = findViewById(R.id.welcome_skip);
        inBtn = findViewById(R.id.welcome_in);
        isNeedJump = getIntent().getBooleanExtra("isNeedJump", false);
        setPage();
    }

    /**
     * 首次显示引导页面，否则直接初始化数据
     */
    private void setPage() {
        if (isFirst()) {
            initPage();
        } else {
            initData();
        }
    }

    /**
     * 初始化数据，判断是否登陆，已经登陆过的直接进入首页，否则进入登陆页
     */
    private void initData() {
        if (!SharePreferenceManager.getUserId().equals("") && !SharePreferenceManager.getUserPwd().equals("")) {
            final String userCode = SharePreferenceManager.getUserCode();
            final String imToken = SharePreferenceManager.getImToken();
            if (userCode != null && !"".equals(userCode) && imToken != null && !"".equals(imToken)) {
                ImClient.loginIM(userCode, imToken, new OnLoginListener() {
                    @Override
                    public void success(String msg) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("accountMobile", SharePreferenceManager.getUserId());
                        map.put("password", SharePreferenceManager.getUserPwd());
                        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).login(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<LoginBean>() {
                            @Override
                            public void requestComplete(@org.jetbrains.annotations.Nullable final LoginBean data) {
                                if (data.getUserCode() == null || data.getImToken() == null || "".equals(data.getUserCode()) || "".equals(data.getImToken())) {
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(ActivityUtils.getTopActivity());
                                    builder.setTitle("无法登录");
                                    builder.setMessage("帐号错误，请检查后重试！");
                                    builder.setNegativeButton("确定",
                                            new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            goToRegisterAndLoginActivity();
                                        }
                                    });
                                    builder.setCancelable(false);
                                    builder.create().show();
                                } else {
                                    ImClient.getUserInfoProvider().setAccount(data.getUserCode());
                                    NimUserInfoCache.getInstance().buildCache();
                                    TeamDataCache.getInstance().buildCache();
                                    ImPreferences.saveUserAccount(data.getUserCode());
                                    ImPreferences.saveUserToken(data.getImToken());
                                    MyApplication.userInfo = new UserInfo();
                                    SharePreferenceManager.setToken("Bearer " + data.getToken());
                                    SharePreferenceManager.setImToken(data.getImToken());
                                    SharePreferenceManager.setUserCode(data.getUserCode());
                                    SharePreferenceManager.setPhoneNumber(data.getPhoneNumber());
                                    SharePreferenceManager.setHospitalId(data.getHospitalId());
                                    SharePreferenceManager.setIdCard(data.getIdCard());
                                    SharePreferenceManager.setAutonyState(data.getAutonymState());
                                    if (data.getRoleIds() != null && data.getRoleIds().size() > 0) {
                                        SharePreferenceManager.setRoleId(data.getRoleIds().get(0));
                                        SharePreferenceManager.setPositionId(data.getNurseMonitorState());
                                        MyApplication.isNeedInit = false;
                                        goToMainActivity();
                                    } else {
                                        SharePreferenceManager.setRoleId(-1);
                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(ActivityUtils.getTopActivity());
                                        builder.setTitle("无角色权限");
                                        builder.setMessage("没有分配角色，请联系管理员分配角色后重新登录！");
                                        builder.setNegativeButton("确定",
                                                new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                goToRegisterAndLoginActivity();
                                            }
                                        });
                                        builder.setCancelable(false);
                                        builder.create().show();
                                        return;
                                    }
                                }
                            }

                            @Override
                            public boolean requestError(@NotNull ApiException exception, int code
                                    , @NotNull String msg) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(ActivityUtils.getTopActivity());
                                builder.setTitle("登录失败");
                                builder.setMessage("帐号错误，请检查后重试！");
                                builder.setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        goToRegisterAndLoginActivity();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().show();
                                return super.requestError(exception, code, msg);
                            }
                        });

                    }

                    @Override
                    public void failed(String errMsg) {
                        goToRegisterAndLoginActivity();
                    }
                });
            } else {
                goToRegisterAndLoginActivity();
            }
        } else {
            goToRegisterAndLoginActivity();
        }
    }

    /**
     * 跳转到首页，并结束当前页面
     */
    private void goToMainActivity() {
        MainActivity.start(this, isNeedJump, false);
        finish();
    }

    /**
     * 跳转到登陆页面，并结束当前页面
     */
    private void goToRegisterAndLoginActivity() {
        //        LoginActivity.start(this);
        ConsultationReceiveActivity.start(this);
        finish();
    }

    /**
     * 判断是否首次开启应用；<br/>
     * true：首次登陆  false：非首次登陆；<br/>
     *
     * @return
     */
    private boolean isFirst() {
        return SharePreferenceManager.getIsFirst();
    }

    /**
     * 初始化引导页面，设置引导图片；<br/>
     */
    private void initPage() {
        rootView.setVisibility(View.VISIBLE);
        LinearLayout linear1 = new LinearLayout(this);
        linear1.setBackgroundResource(R.mipmap.wel_1);
        LinearLayout linear2 = new LinearLayout(this);
        linear2.setBackgroundResource(R.mipmap.wel_2);
        LinearLayout linear3 = new LinearLayout(this);
        linear3.setBackgroundResource(R.mipmap.wel_3);
        List<View> linears = new ArrayList<View>();
        linears.add(linear1);
        linears.add(linear2);
        linears.add(linear3);
        MyPageAdapter myPageAdapter = new MyPageAdapter(linears);
        mViewPager.setAdapter(myPageAdapter);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceManager.setIsFirst(false);
                initData();
            }
        });
        inBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceManager.setIsFirst(false);
                initData();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    inBtn.setVisibility(View.VISIBLE);
                } else {
                    inBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == 1) {
                    inBtn.setAlpha(0.0f);
                } else {
                    inBtn.setAlpha(1.0f);
                }

            }
        });


    }

    /**
     * 设置引导图片的适配器
     */
    private class MyPageAdapter extends PagerAdapter {
        private List<View> linears;

        public MyPageAdapter(List<View> linears) {
            this.linears = linears;
        }

        @Override
        public int getCount() {
            return linears.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        /**
         * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
         *
         * @param view
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(linears.get(position));
        }

        /**
         * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
         *
         * @param view
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(linears.get(position));
            return linears.get(position);
        }

    }


}
