package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/27 10:19
 * @des 儿保预约首页数据
 */
public class ChildHealthRootBean implements Serializable {
    private static final long serialVersionUID = 7173074284625685731L;

    private String lastTimeChild;
    private String age;

    private List<String> childProject;
    private List<ChildHealthDoingBean> futureAndDoing;
    private List<ChildHealthDoingBean> historyRecords;

    public String getLastTimeChild() {
        return lastTimeChild;
    }

    public void setLastTimeChild(String lastTimeChild) {
        this.lastTimeChild = lastTimeChild;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<ChildHealthDoingBean> getFutureAndDoing() {
        return futureAndDoing;
    }

    public void setFutureAndDoing(List<ChildHealthDoingBean> futureAndDoing) {
        this.futureAndDoing = futureAndDoing;
    }

    public List<ChildHealthDoingBean> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<ChildHealthDoingBean> historyRecords) {
        this.historyRecords = historyRecords;
    }
}
