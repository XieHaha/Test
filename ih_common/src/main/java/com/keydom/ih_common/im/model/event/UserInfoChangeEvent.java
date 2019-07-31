package com.keydom.ih_common.im.model.event;

import java.util.List;

public class UserInfoChangeEvent {
    private List<String> accounts;

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }
}
