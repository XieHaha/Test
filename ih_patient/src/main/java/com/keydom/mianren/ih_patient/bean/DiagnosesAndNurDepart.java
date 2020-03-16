package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 子科室详情
 */
public class DiagnosesAndNurDepart {

    /**
     * code : neike
     * name : 内科
     * image : im
     * fontColor : #FF0000
     * items : [{"id":47,"name":"科室二"},{"id":48,"name":"科室三"},{"id":59,"name":"放射化疗403科室"},{"id":60,"name":"210科室"},{"id":75,"name":"内科"},{"id":76,"name":"外科"},{"id":77,"name":"神经科"},{"id":155,"name":"葫芦金刚娃1"},{"id":156,"name":"你是我的吗1"},{"id":157,"name":"你是我的优乐美吗"},{"id":179,"name":"心血管1"},{"id":180,"name":"心血管测试导入"},{"id":181,"name":"心血管1345"}]
     */

    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "fontColor")
    private String fontColor;
    @JSONField(name = "items")
    private List<ItemsBean> items;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 47
         * name : 科室二
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "name")
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
