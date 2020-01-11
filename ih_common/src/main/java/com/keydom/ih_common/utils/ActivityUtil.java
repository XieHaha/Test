package com.keydom.ih_common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Activity跳转工具类
 * 
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class ActivityUtil {

	/**
	 * RequestCode不可用标记
	 */
	private static final int NONE_REQUEST_CODE = -1;

	/**
	 * 跳转到下一个页面（默认方法）
	 * 
	 * @param curActivity
	 * @param nextActivity
	 */
	public static void next(Activity curActivity, Class<?> nextActivity) {
		next(curActivity, nextActivity, null, false, NONE_REQUEST_CODE);
	}

	/**
	 * 跳转到下一个页面（带requestCode）
	 * 
	 * @param curActivity
	 * @param nextActivity
	 * @param requestCode
	 */
	public static void next(Activity curActivity, Class<?> nextActivity, int requestCode) {
		next(curActivity, nextActivity, null, false, requestCode);
	}

	/**
	 * 跳转到下一个页面
	 * 
	 * @param curActivity
	 * @param nextActivity
	 * @param finishCurrentActivity
	 *            是否结束当前Activity
	 */
	public static void next(Activity curActivity, Class<?> nextActivity, boolean finishCurrentActivity) {
		next(curActivity, nextActivity, null, finishCurrentActivity, NONE_REQUEST_CODE);
	}

	/**
	 * 跳转到下一个页面
	 * 
	 * @param curActivity
	 * @param nextActivity
	 * @param extras
	 *            Bundle数据
	 */
	public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras) {
		next(curActivity, nextActivity, extras, false, NONE_REQUEST_CODE);
	}

	/**
	 * 跳转到下一个页面
	 * 
	 * @param curActivity
	 * @param nextActivity
	 * @param extras
	 *            Bundle数据
	 * @param finishCurrentActivity
	 *            是否结束当前Activity
	 */
	public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, boolean finishCurrentActivity) {
		next(curActivity, nextActivity, extras, finishCurrentActivity, NONE_REQUEST_CODE);
	}

	/**
	 * 跳转到下一个页面
	 * 
	 * @param curActivity
	 *            当前Activity
	 * @param nextActivity
	 *            目标Activity
	 * @param extras
	 *            Bundles
	 * @param requestCode
	 *            请求Code
	 * @param finishCurrentActivity
	 *            是否结束当前Activity
	 */
	public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, boolean finishCurrentActivity, int requestCode) {
		Intent intent = new Intent(curActivity, nextActivity);
		if (null != extras) {
			intent.putExtras(extras);
		}
		// 设置返回值模式
		if (requestCode <= NONE_REQUEST_CODE) {
			curActivity.startActivity(intent);
		} else {
			curActivity.startActivityForResult(intent, requestCode);
		}
		// 是否销毁当前activity
		if (finishCurrentActivity) {
			curActivity.finish();
		}
	}

	/**
	 * 直接返回到指定的某个页面并清空Task栈在NextActivity上的页面
	 * 
	 * @param curActivity
	 * @param nextActivity
	 */
	public static void nextWithClearTopFlag(Activity curActivity, Class<?> nextActivity) {
		nextWithClearTopFlag(curActivity, nextActivity, null);
	}

	/**
	 * 直接返回到指定的某个页面并清空Task栈在NextActivity上的页面
	 * 
	 * @param curActivity
	 * @param nextActivity
	 * @param extras
	 *            Bundle数据
	 */
	public static void nextWithClearTopFlag(Activity curActivity, Class<?> nextActivity, Bundle extras) {
		Intent intent = new Intent(curActivity, nextActivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (null != extras) {
			intent.putExtras(extras);
		}
		curActivity.startActivity(intent);
	}

	/**
	 * 直接返回到指定的某个页面
	 * 
	 * @param context
	 * @param nextActivity
	 * @param extras
	 */
	public static void nextActivityWithFlagNewTask(Context context, Class<?> nextActivity, Bundle extras) {
		Intent intent = new Intent(context, nextActivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (null != extras) {
			intent.putExtras(extras);
		}
		context.startActivity(intent);
	}

	/**
	 * 结束当前的Activity
	 * 
	 * @param curActivity
	 */
	public static void finish(Activity curActivity) {
		curActivity.finish();
	}



	/**
	 *
	 * @Description: TODO 判断activity是否在应用的最顶层
	 * @param context 上下文
	 * @return boolean true为在最顶层，false为否
	 */
	public static boolean isTop(Activity context) {
		ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
		return appTask.size() > 0 && appTask.get(0).topActivity.equals(context.getComponentName());
	}

	public static boolean isTop(Context context,Activity activity) {
		ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
		return appTask.size() > 0 && appTask.get(0).topActivity.equals(activity.getComponentName());
	}

}
