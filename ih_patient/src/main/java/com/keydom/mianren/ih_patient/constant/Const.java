package com.keydom.mianren.ih_patient.constant;

/**
 * @Description：静态变量类：定义事件ID、接口地址、接口状态码、标识信息等
 * @Author：song
 * @Date：18/11/2 下午1:44
 */
public class Const {
    public static String TOKEN = "";
    public static final int YXYZ = 613;
    /**
     * 数据传递key
     */
    public static final String DATA = "data";
    public static final String POSITION = "position";
    public static final String PATIENT_ID = "patient_id";
    /**
     * 类型key
     */
    public static final String TYPE = "type";

    /**
     * 分页每一页的大小
     */
    public static final int PAGE_SIZE = 8;

    /**
     * 实名认证type
     */
    public static final String CERTIFICATE_TYPE = "certificate_type";
    public static final String CARD_ID_CARD = "card_id_card";
    public static final String CARD_OTHER_CERTIFICATE = "card_other_certificate";
    public static final String CARD_GET_INFO = "card_get_info";
    public static final String IS_NEED_TO_PREGNANCY = "is_need_to_pregnancy";
    public static final String MEDICAL_CARD_INFO = "medical_card_info";


    /**
     * 产检预约
     */
    public static final String PREGNANCY_DETAIL = "pregnancy_detail";
    public static final String RECORD_ID = "record_id";
    public static String PREGNANCY_ORDER_TYPE = "pregnancy_order_type"; //1：检验检查；2：产检门诊；12：检验检查和产检门诊
    public static final int PREGNANCY_ORDER_TYPE_ALL = 12;//12：检验检查和产检门诊
    public static final int PREGNANCY_ORDER_TYPE_CHECK = 0;//1：检验检查
    public static final int PREGNANCY_ORDER_TYPE_DIAGNOSE = 1;//2：产检门诊


    /**
     * 支付
     *
     * */
    public static final int ALI_PAY = 2;//支付宝支付
    public static final int WECHAT_PAY = 1;//微信支付



    /**
     * 选择就诊卡或就诊人
     *
     * */
    public static final int PATIENT_TYPE_ALL = 1;//就诊卡和就诊人
    public static final int PATIENT_TYPE_CARD = -1;//就诊卡
    public static final int PATIENT_TYPE_PERSON = 0;//就诊人
}
