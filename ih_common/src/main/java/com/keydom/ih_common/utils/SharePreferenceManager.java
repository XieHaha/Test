package com.keydom.ih_common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {
    private static final String KEY_CACHED_USERNAME = "internetHospital";
    static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static final String FIRST_LOGIN = "firstLogin";
    private static final String USER_ID = "userid";
    private static final String USER_PWD = "password";
    private static final String CURRENT_GROUP = "current_group";
    private static final String TOKEN = "token";
    private static final String ID = "id";
    private static final String IM_TOKEN = "im_token";
    private static final String USER_CODE = "user_code";
    private static final String ROLE_ID = "role_id";
    private static final String POSITION_ID = "position_id";
    private static final String FINISH_INFO = "finish_info";
    private static final String IS_AGREEMENT = "is_agreement";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String SIGN_ID = "sign_id";
    private static final String HOSPITAL_ID = "hospital_id";
    private static final String GESTURELOCKEDTIME = "gesture_locked_time";

    public static void setLockTime(long timeMillis) {
        if (null != sp) {
            sp.edit().putLong(GESTURELOCKEDTIME, timeMillis).apply();
        }
    }

    public static long getLockTime() {
        if (null != sp) {
            return sp.getLong(GESTURELOCKEDTIME, 0);
        }
        return 0;
    }

    public static void setFirstFinishInfo(boolean first) {
        if (null != sp) {
            sp.edit().putBoolean(FINISH_INFO, first).apply();
        }
    }

    public static boolean getFirstFinishInfo() {
        if (null != sp) {
            return sp.getBoolean(FINISH_INFO, false);
        }
        return true;
    }

    public static void setIsAgreement(boolean first) {
        if (null != sp) {
            sp.edit().putBoolean(IS_AGREEMENT, first).apply();
        }
    }

    public static boolean getIsAgreement() {
        /*if (null != sp) {
            return sp.getBoolean(IS_AGREEMENT, false);
        }*/
        return false;
    }

    public static void setIsFirst(boolean first) {
        if (null != sp) {
            sp.edit().putBoolean(FIRST_LOGIN, first).apply();
        }
    }

    public static boolean getIsFirst() {
        if (null != sp) {
            return sp.getBoolean(FIRST_LOGIN, true);
        }
        return true;
    }

    public static void setUserId(String userId) {
        if (null != sp) {
            sp.edit().putString(USER_ID, userId).apply();
        }
    }

    public static String getUserId() {
        if (null != sp) {
            return sp.getString(USER_ID, "");
        }
        return "";
    }

    public static void setUserPwd(String password) {
        if (null != sp) {
            sp.edit().putString(USER_PWD, password).apply();
        }
    }

    public static String getUserPwd() {
        if (null != sp) {
            return sp.getString(USER_PWD, "");
        }
        return "";
    }


    public static void setGroup(int password) {
        if (null != sp) {
            sp.edit().putInt(CURRENT_GROUP, password).apply();
        }
    }

    public static int getGroup() {
        if (null != sp) {
            return sp.getInt(CURRENT_GROUP, -1);
        }
        return -1;
    }


    public static void setToken(String token) {
        if (null != sp) {
            sp.edit().putString(TOKEN, token).apply();
        }
    }

    public static String getToken() {
        if (null != sp) {
            return sp.getString(TOKEN, "");
        }
        return "";
    }


    public static void setImToken(String token) {
        if (null != sp) {
            sp.edit().putString(IM_TOKEN, token).apply();
        }
    }

    public static String getImToken() {
        if (null != sp) {
            return sp.getString(IM_TOKEN, "");
        }
        return "";
    }


    public static void setUserCode(String code) {
        if (null != sp) {
            sp.edit().putString(USER_CODE, code).apply();
        }
    }

    public static String getUserCode() {
        if (null != sp) {
            return sp.getString(USER_CODE, "");
        }
        return "";
    }

    public static void setPhoneNumber(String code) {
        if (null != sp) {
            sp.edit().putString(PHONE_NUMBER, code).apply();
        }
    }

    public static String getPhoneNumber() {
        if (null != sp) {
            return sp.getString(PHONE_NUMBER, "");
        }
        return "";
    }


    public static void setSignId(String signId) {
        if (null != sp) {
            sp.edit().putString(SIGN_ID, signId).apply();
        }
    }

    public static String getSignId() {
        if (null != sp) {
            return sp.getString(SIGN_ID, "");
        }
        return "";
    }

    public static void setId(Long id) {
        if (null != sp) {
            sp.edit().putLong(ID, id).apply();
        }
    }

    public static Long getId() {
        if (null != sp) {
            return sp.getLong(ID, -1);
        }
        return new Long(-1);
    }


    public static void setRoleId(int roleId) {
        if (null != sp) {
            sp.edit().putInt(ROLE_ID, roleId).apply();
        }
    }

    public static int getRoleId() {
        if (null != sp) {
            return sp.getInt(ROLE_ID, -1);

        }
        return -1;
    }


    public static void setPositionId(int positionId) {
        if (null != sp) {
            sp.edit().putInt(POSITION_ID, positionId).apply();
        }
    }

    public static int getPositionId() {
        if (null != sp) {
            return sp.getInt(POSITION_ID, -1);

        }
        return -1;
    }


    public static void setHospitalId(String hospitalId) {
        if (null != sp) {
            sp.edit().putString(HOSPITAL_ID, hospitalId).apply();
        }
    }

    public static String getHospitalId() {
        if (null != sp) {
            return sp.getString(HOSPITAL_ID, "");
        }
        return "";
    }


    public static void clearData() {
        setToken("");
        setImToken("");
        setUserCode("");
        setPhoneNumber("");
        setSignId("");
        setRoleId(0);
        setPositionId(0);
    }


}
