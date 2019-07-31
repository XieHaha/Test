package com.keydom.ih_common.im.model.event;

import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

public class RecentContactEvent {
    private List<RecentContact> recentContacts;

    public List<RecentContact> getRecentContacts() {
        return recentContacts;
    }

    public void setRecentContacts(List<RecentContact> recentContacts) {
        this.recentContacts = recentContacts;
    }
}
