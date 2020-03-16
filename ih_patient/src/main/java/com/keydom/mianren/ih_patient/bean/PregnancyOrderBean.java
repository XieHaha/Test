package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

public class PregnancyOrderBean implements Serializable {
    private static final long serialVersionUID = -7838661712836092152L;


    /**
     * data : [{"projectId":"1","projectName":"评估胎儿体重、胎位检查、、复查血常规、尿常规、宫高、腹围、胎心、血压、体重","appointType":1,"prenatalDate":"2020-01-19","timeInterval":"11:30~12:00"},{"projectId":"1","projectName":"评估胎儿体重、胎位检查、、复查血常规、尿常规宫高、腹围、胎心、血压、体重","appointType":2,"prenatalDate":"2020-01-21","timeInterval":"11:30~12:00"}]
     * type : 12
     */

    private int type;
    private List<PregnancyOrderDetailItem> data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PregnancyOrderDetailItem> getData() {
        return data;
    }

    public void setData(List<PregnancyOrderDetailItem> data) {
        this.data = data;
    }
}
