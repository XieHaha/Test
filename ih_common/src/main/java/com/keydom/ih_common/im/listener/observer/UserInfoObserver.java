package com.keydom.ih_common.im.listener.observer;

import java.util.List;

/**
 * 用户信息变更观察者
 *
 * @author THINKPAD B
 */
public interface UserInfoObserver {

    /**
     * 用户信息变更
     *
     * @param accounts 账号列表
     */
    void onUserInfoChanged(List<String> accounts);
}