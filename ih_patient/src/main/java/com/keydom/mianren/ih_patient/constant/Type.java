package com.keydom.mianren.ih_patient.constant;

/**
 * eventType类
 */
public class Type {
    public static final String ALLFUNCTIONTYPE = "all_function_type";
    public static final String SELECTEDFUNCTIONTYPE = "selected_function_type";

    public static final String ORDERCUREAPPLY = "order_cure_apply";
    public static final String ORDERCUREAUDIT = "order_cure_audit";
    public static final String ORDERCURECONFIRM = "order_cure_confirm";
    public static final String ORDERCURESUCCESS = "order_cure_success";

    public static final String IDCARDVALIDITYPERIOD = "id_card_validity_period";
    public static final String BIRTHDATE = "birth_date";

    public static final String ALIPAY = "aliPay";
    public static final String WECHATPAY = "wechatPay";
    public static final String UNIONPAY = "unionPay";
    public static final String PREPAY = "prepay";

    public static final String PHYSICALEXACOMMENTSRELOAD = "reload";
    public static final String PHYSICALEXACOMMENTSLOADMORE = "load_more";

    public static final String SEARCHDOCTOR = "search_doctor";
    public static final String SEARCHDOCTORANDDEPARTMENTS = "search_doctor_and_departments";

    public static final String DONEREGISTRATIONRECORD = "done_registration_record";
    public static final String DOINGREGISTRATIONRECORD = "doing_registration_record";


    public static final int INSPECTIONTYPE = 1;//检验
    public static final int BODYCHECKTYPE = 2;//检查

    public static final String BASENURSING = "base_nursing";
    public static final String PROFESSIONALNURSING = "professional_nursing";
    public static final String POSTPARTUMNURSING = "postpartum_nursing";
    public static final String ENABLESELCTEDLIST = "enable_select_list";
    public static final String ABLESELCTEDLIST = "able_select_list";

    public static final String STARTLOCATIONWITHRESULT = "start_with_result";
    public static final String STARTLOCATIONWITHOUTRESULT = "start_without_result";
    public static final String PAY_SELECT_ADDRESS = "pay_select_address";
    public static final String WAI_PAY_SELECT_ADDRESS = "wai_pay_select_address";

    public static final String NURSING_ORDER_EVALUATE = "nursing_order_evaluate";//护理订单评价
    public static final String SUBSCRIBE_EXAM_ORDER_EVALUATE = "subscribe_exam_order_EVALUATE";
    //体检预约订单评价
    public static final String DIAGNOSES_ORDER_EVALUATE = "diagnoses_order_EVALUATE";//问诊订单评价

    public static final String NOTICEMESSAGE = "notice_message";
    public static final String MYMESSAGE = "my_message";
    public static final String IMMESSAGE = "im_message";

    public static final int NOTHOSPITALIZED = 0;
    public static final int HOSPITALIZED = 1;

    public static final String NOTMEDICALMAILED = "not_medicalmailed";
    public static final String MEDICALMAILED = "medicalmailed";

    public static final String EXAMINATION = "examination";//检查
    public static final String INSPECT = "inspect";//检验
}
