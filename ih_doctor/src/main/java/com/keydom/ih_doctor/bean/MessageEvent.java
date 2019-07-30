package com.keydom.ih_doctor.bean;

import com.keydom.ih_doctor.constant.EventType;

/**
 * @Name：com.kentra.yxyz.bean
 * @Description：自定义EventBus事件类
 * @Author：song
 * @Date：18/11/2 下午1:50
 * 修改人：xusong
 * 修改时间：18/11/2 下午1:50
 */
public class MessageEvent {
    private EventType type;
    private Object data;
    private MessageEvent(EventType type, Object data){
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
        public Buidler setType(EventType type) {
            this.type = type;
            return this;
        }
        public Buidler setData(Object data) {
            this.data = data;
            return this;
        }

        public MessageEvent build(){
            return new MessageEvent(type,data);
        }

    }
}
