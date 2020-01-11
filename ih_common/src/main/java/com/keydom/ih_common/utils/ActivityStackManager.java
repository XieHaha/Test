package com.keydom.ih_common.utils;

import android.app.Activity;
import java.util.Stack;

/**
 * Activity管理器
 *
 * @author dengyuhan
 *         created 2017/12/7 17:20
 */
public class ActivityStackManager {
    private static ActivityStackManager mInstance;

    private final Stack<Activity> mActivityStack = new Stack<>();

    private ActivityStackManager() {
    }

    public static ActivityStackManager getInstance() {
        synchronized (ActivityStackManager.class) {
            if (mInstance == null) {
                mInstance = new ActivityStackManager();
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        mActivityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * finish对应class的所有activity
     *
     * @param cls 要关闭的Activity Class
     */
    public void finishActivity(Class<? extends Activity>... cls) {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity activity = mActivityStack.get(i);
            for (Class<? extends Activity> c : cls) {
                if (c.getName().equals(activity.getClass().getName())) {
                    finishActivity(activity);
                }
            }
        }
    }


    /**
     * 关闭栈顶的Activity
     */
    public void finishTopActivity() {
        Activity pop = mActivityStack.pop();
        if (!pop.isFinishing()) {
            pop.finish();
        }
    }


    /**
     * finish除白名单以外的所有activity
     *
     * @param activityWhitelist 要保留的activity
     */
    public void finishAllActivityByWhitelist(Class<? extends Activity>... activityWhitelist) {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity activity = mActivityStack.get(i);
            for (Class<? extends Activity> c : activityWhitelist) {
                if (c.getName().equals(activity.getClass().getName())) {
                    break;
                } else {
                    finishActivity(activity);
                }
            }
        }
    }


    /**
     * finish所有activity
     */
    public void finishAllActivity() {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity activity = mActivityStack.get(i);
            finishActivity(activity);
        }
    }


    private void finishActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
                activity.overridePendingTransition(0,0);
                mActivityStack.remove(activity);
            }
        }
    }

    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    /**
     * @param activity
     * @return 是否存在此activity
     */
    public boolean isContainsActivity(Activity activity) {
        return mActivityStack.contains(activity);
    }

    /**
     * @return 已经打开activity的数量
     */
    public int getActivityCount() {
        return mActivityStack.size();
    }


}