package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 预约时间
 */
public class SoruInfo {

    /**
     * total : 12
     * list : [{"surplus":12,"timeInterval":"15:00-16:00","id":9}]
     */

    @JSONField(name = "total")
    private int total;
    @JSONField(name = "list")
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * surplus : 12
         * timeInterval : 15:00-16:00
         * id : 9
         */

        @JSONField(name = "surplus")
        private int surplus;
        @JSONField(name = "timeInterval")
        private String timeInterval;
        @JSONField(name = "id")
        private long id;

        public int getSurplus() {
            return surplus;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public String getTimeInterval() {
            return timeInterval;
        }

        public void setTimeInterval(String timeInterval) {
            this.timeInterval = timeInterval;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
