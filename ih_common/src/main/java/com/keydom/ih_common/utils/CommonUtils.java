/*
    ShengDao Android Client, CommonUtils
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.keydom.ih_common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.keydom.ih_common.R;
import com.keydom.ih_common.constant.Const;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * [公共工具类，与Android API相关的辅助类]
 **/
public class CommonUtils {

    @SuppressWarnings("unused")
    private static final String tag = CommonUtils.class.getSimpleName();

    /**
     * 网络类型
     **/
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;


    private static String nums[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    private static String pos_units[] = {"", "十", "百", "千"};

    private static String weight_units[] = {"", "万", "亿"};

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }


    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕显示信息对象
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * dp转pixel
     */
    public static float dpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * pixel转dp
     */
    public static float pixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }


    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param activity
     */
    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 是否横屏
     *
     * @param context
     * @return true为横屏，false为竖屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否是平板
     * 这个方法是从 Google I/O CommonApp for Android 的源码里找来的，非常准确。
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getNewContent(String htmltext) {
        System.out.print("1111111111" + new Date());
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        System.out.print("2222222222" + new Date());
        return doc.toString();
    }

    public static String getSex(String sex) {
        if ("0".equals(sex)) {
            return "男";
        }
        if ("1".equals(sex)) {
            return "女";
        }
        return "未知";
    }

    public static String getPatientSex(String sex) {
        if ("0".equals(sex)) {
            return "男";
        }
        if ("1".equals(sex)) {
            return "女";
        }
        return "未知";
    }

    public static String getSex(int sex) {
        if (sex == 0) {
            return "男";
        }
        if (sex == 1) {
            return "女";
        }
        return "未知";
    }

    public static String getPatientSex(int sex) {
        if (sex == 0) {
            return "男";
        }
        if (sex == 1) {
            return "女";
        }
        return "未知";
    }


    /**
     * 判别是否包含Emoji表情
     *
     * @param str
     * @return
     */
    public static boolean containsEmoji2(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkStr(String value) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()" +
                "<>{}/[/]()<>{}\\[\\]=＝×%&$|\\/#\"` ，\\n \\s " +
                "。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］［］｛｝｛｝％／＼｀．]");
        //        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;
        //        …~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=＝×%&$|\\/#¥£¢€\"` ，\\n \\s
        //        。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛【】【】％〖〗〖〗／〔〕〔〕＼『』『』「」「」｜﹁﹂｀．]");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean containsEmoji(String value) {
        Log.e("Emoji", value);
        boolean flag = false;
        if (value == null || "".equals(value) || "\n".equals(value) || " ".equals(value)) {
            return false;
        }
        return checkStr(value);
        //        try {
        //            Pattern p = Pattern
        //                    .compile("[^\\u0000-\\uFFFF]");
        //            Matcher m = p.matcher(value);
        //            flag = m.find();
        //        } catch (Exception e) {
        //            flag = false;
        //        }
        //        if (containsEmoji2(value)) {
        //            return true;
        //        }
        //        if (!containsEmoji3(value)) {
        //            return true;
        //        }
        //        return flag;
    }

    public static boolean containsEmoji3(String value) {
        boolean flag = false;
        try {
            Pattern p = Pattern
                    .compile("[\\p{Han}\\p{P}A-Za-z0-9]");
            Matcher m = p.matcher(value);
            flag = m.find();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 是否是emoji字符
     *
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));

    }


    /**
     * 将含有EMOJI表情的文字转换
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;
        }

        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } else {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append("～");
                //                buf.append(stringToUnicode(codePoint));

            }
        }
        if (buf == null) {
            return "";
        } else {
            //            if (buf.length() == len) {
            //                buf = null;
            //                return source;
            //            } else {
            return buf.toString();
            //            }
        }
    }

    public static String getIcon(String str) {
        if (str == null) {
            return "";
        }
        String[] paths = str.split(",");
        if (paths != null && paths.length > 0) {
            return paths[0];
        }
        return "";
    }

    public static String getContent(String str) {
        if (str == null) {
            return "";
        }
        int startPos = str.indexOf("<p>") + 3;
        int endPos = str.indexOf("</p>");
        if (startPos >= 0 && endPos >= 0 && startPos <= endPos) {
            return str.substring(startPos, endPos);
        }

        if (startPos == 2 || endPos == -1) {
            return str;
        }
        return "";
    }


    public static String stringToUnicode(char ch) {
        String str = "";
        if (ch > 255)
            str += "\\u" + Integer.toHexString(ch);
        else
            str += "\\" + Integer.toHexString(ch);

        return str;
    }

    public static List<String> getImgList(String str) {
        List<String> strList = new ArrayList<>();
        if (str != null && !"".equals(str)) {
            String[] icons = str.split(",");
            for (int i = 0; i < icons.length; i++) {
                strList.add(icons[i]);
            }
        }
        return strList;
    }

    public static List<String> getFullImgList(String str) {
        List<String> strList = new ArrayList<>();
        if (str != null) {
            String[] icons = str.split(",");
            for (int i = 0; i < icons.length; i++) {
                strList.add(Const.IMAGE_HOST + icons[i]);
            }
        }
        return strList;
    }

    public static String numberToChinese(int num) {
        if (num == 0) {
            return "零";
        }

        int weigth = 0;
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;
        while (num > 0) {
            int section = num % 10000;
            if (setZero) {
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            if (section != 0) {
                chinese_section = chinese_section + weight_units[weigth];
            }
            chinese = chinese_section + chinese;
            chinese_section = "";
            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("一十") == 0) {
            chinese = chinese.replaceFirst("一十", "十");
        }

        return chinese;
    }


    public static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;
        boolean zero = true;
        while (section > 0) {
            int v = section % 10;
            if (v == 0) {
                if (!zero) {
                    zero = true;
                    section_chinese.insert(0, nums[0]);
                }
            } else {
                zero = false;
                section_chinese.insert(0, pos_units[pos]);
                section_chinese.insert(0, nums[v]);
            }
            pos++;
            section = section / 10;
        }

        return section_chinese.toString();
    }


    public static boolean isInstalled(Context mContext, String packageName) {
        PackageManager manager = mContext.getPackageManager();
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * 获取包名
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void previewImage(Context context, String path) {
        if (path == null || "".equals(path)) {
            return;
        }
        LocalMedia media = new LocalMedia();
        media.setPath(path);
        List<LocalMedia> mediaList = new ArrayList<>();
        mediaList.add(media);
        PictureSelector.create((Activity) context).themeStyle(R.style.picture_default_style).openExternalPreview(0, mediaList);
    }

    public static void previewImageList(Context context, List<String> pathList, int position,
                                        boolean isNeedAddhead) {
        if (pathList == null || pathList.size() == 0) {
            return;
        }
        List<LocalMedia> mediaList = new ArrayList<>();
        for (int i = 0; i < pathList.size(); i++) {
            LocalMedia media = new LocalMedia();
            if (isNeedAddhead)
                media.setPath(Const.IMAGE_HOST + pathList.get(i));
            else
                media.setPath(pathList.get(i));
            mediaList.add(media);
        }

        PictureSelector.create((Activity) context).themeStyle(R.style.picture_default_style).openExternalPreview(position, mediaList);
    }

    public static boolean checkPassword(String password) {
        //        String pattern = "[A-Za-z0-9]{6,20}";
        String pattern = "^[a-zA-Z0-9]{6,20}$";
        return Pattern.matches(pattern, password);
    }


    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight();
        final int screenWidth = ScreenUtils.getScreenWidth();
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static String getFromRaw(Context context, int resource) {
        String result = "";
        try {
            InputStream in = context.getResources().openRawResource(resource);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            result = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static SpannableString getSearchResultStr(String keyword, String content) {
        if (keyword == null) {
            return new SpannableString(content);
        }
        char[] keywords = keyword.toCharArray();
        SpannableString spannableString = new SpannableString(content);
        for (int i = 0; i < keywords.length; i++) {
            int index = content.indexOf(keywords[i]);
            while (index >= 0) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3F98F7")),
                        index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                index = content.indexOf(keywords[i], index + 1);
            }
        }

        return spannableString;
    }
}
