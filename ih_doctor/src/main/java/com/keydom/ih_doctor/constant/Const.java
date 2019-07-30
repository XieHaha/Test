package com.keydom.ih_doctor.constant;

/**
 * @Name：com.kentra.yxyz.constant
 * @Description：静态变量类：定义事件ID、接口地址、接口状态码、标识信息等
 * @Author：song
 * @Date：18/11/2 下午1:44
 * 修改人：xusong
 * 修改时间：18/11/2 下午1:44
 */
public class Const {
    /**
     * true 验收
     * false 测试
     */
    public static final boolean CHECK_AND_ACCEP = false;

    /**
     * 已经被抢单
     */
    public static final int RETURN_ERROR_ORDER_IS_ROB = 100001;
    /**
     * 护士长
     */
    public static final int HEAD_NURSE_CODE = 0;
    /**
     * 默认宽度
     */
    public static final int DEFAULT_WIDTH = 600;

    /**
     * 完成护理页面
     */
    public static final int FINISH_ACTIVITY = 9999;
    /**
     * 修改密码
     */
    public static final int UPDATE_PASSWORD = 306;

    /**
     * 选择医生
     */
    public static final int DOCTOR_SLEECT = 307;
    /**
     * 选择所有科室医生－直接返回结果
     */
    public static final int DOCTOR_SLEECT_ONLY_RESULT = 308;
    /**
     * 选择问诊单－转诊
     */
    public static final int DIAGNOSE_ORDER_SELECT = 309;
    /**
     * 选择本科室的医生－直接返回结果
     */
    public static final int DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT = 313;
    /**
     * 选择患者－直接返回结果
     */
    public static final int PATIENT_SLEECT_ONLY_RESULT = 314;
    /**
     * 选择护士－直接返回结果
     */
    public static final int NURSE_SLEECT_ONLY_RESULT = 315;
    /**
     * 选择护理项目
     */
    public static final int NURSE_SERVICE_ITEM_SELECT = 316;
    /**
     * 选择检验项目
     */
    public static final int TEST_ITEM_SELECT = 318;
    /**
     * 电话号码
     */
    public static final String PHONE_NUM = "phone_num";
    /**
     * 医生角色
     */
    public static final int ROLE_DOCTOR = 1;
    /**
     * 护士角色
     */
    public static final int ROLE_NURSE = 2;
    /**
     * 药师角色
     */
    public static final int ROLE_MEDICINE = 3;
    /**
     * 本地存储XML名称
     */
    public static final String SHAREPREFERENCE_NAME = "hospital_per";
    /**
     * 类型key
     */
    public static final String TYPE = "type";
    /**
     * 字数限制KEY
     */
    public static final String LIMITE = "limite";
    /**
     * 最小长度key
     */
    public static final String LEAST_SIZE = "least_size";
    /**
     * 数据传递key
     */
    public static final String DATA = "data";
    /**
     * 标题
     */
    public static final String TITLE = "title";
    /**
     * 个人信息
     */
    public static final String PERSONAL_INFO = "personal_info";
    /**
     * 团队ID
     */
    public static final String GROUP_ID = "group_id";
    /**
     * 刷新列表
     */
    public static final int TYPE_ACTIVITY_REFRESH = 2002;
    /**
     * 问诊单
     */
    public static final String CONSULTING_BEAN = "consulting_bean";
    /**
     * 用户信息
     */
    public static final String USER_INFO = "user_info";
    /**
     * 接口地址
     */
    public static final String RELEASE_HOST = com.keydom.ih_common.constant.Const.RELEASE_HOST;
    /**
     * 图片显示地址
     */
    public static final String IMAGE_HOST = com.keydom.ih_common.constant.Const.IMAGE_HOST;
    public final static int NURSING_SERVICE_ORDER_STATE_REWRITE = -2;  // 订单修改
    public final static int NURSING_SERVICE_ORDER_STATE_REJECT = -1;   // 退单
    public final static int NURSING_SERVICE_ORDER_STATE_UNACCEPT = 0;  //未接单
    public final static int NURSING_SERVICE_ORDER_STATE_ACCEPT = 1; // 已接单
    public final static int NURSING_SERVICE_ORDER_STATE_ON_WAY = 2; // 在途中
    public final static int NURSING_SERVICE_ORDER_STATE_ON_SERVICE = 3; // 服务中
    public final static int NURSING_SERVICE_ORDER_STATE_FINISH = 4;  // 订单完成
    public final static int NURSING_SERVICE_ORDER_STATE_DOCTORACCEPT_NURSEUNACCEPT = 5;  // 护士长接单，护士未接单

    /**
     * 发布信息
     */
    public static final int ISSUE = 6;
    /**
     * 门诊排班
     */
    public static final int CONSULTING = 4;
    /**
     * 处方审核
     */
    public static final int AUDIT = 7;
    /**
     * 护理服务
     */
    public static final int NURSE_VISIT = 18;

    /**
     * 医生协作
     */
    public static final int DOCTOR_COOPERATE = 5;

    /**
     * 在线接诊
     */
    public static final int INLINE_DIAGNOSE = 3;


    /**
     * 处方ID
     */
    public static final String PRESCRIPTION_ID = "prescription_id";

    /**
     * 分页每一页的大小
     */
    public static final int PAGE_SIZE = 8;

    /**
     * 女人
     */
    public static final int FEMALE = 1;
    /**
     * 男人
     */
    public static final int MALE = 0;

    /**
     * int 默认
     */
    public static final int INT_DEFAULT = -1;


    /**
     * 处方签名
     */
    public static final int SIGN_PRESCRIPTION = 1;

    /**
     * 处方审核签名
     */
    public static final int SIGN_CHECK_PRESCRIPTION = 2;


}
