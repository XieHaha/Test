package com.keydom.mianren.ih_patient.bean;


import com.keydom.mianren.ih_patient.constant.EventType;

/**
 * @Description：自定义EventBus事件类
 * @Author：song
 * @Date：18/11/2 下午1:50
 * 修改人：xusong
 * 修改时间：18/11/2 下午1:50
 */
public class Event {
    private EventType type;
    private Object data;
    public Event(EventType type, Object data){
        this.type=type;
        this.data=data;
    }

    public EventType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public static class Buidler{
        private EventType type;
        private Object data;
        public void setType(EventType type) {
            this.type = type;
        }
        public void setData(Object data) {
            this.data = data;
        }

        public Event build(){
            return new Event(type,data);
        }

    }
}
