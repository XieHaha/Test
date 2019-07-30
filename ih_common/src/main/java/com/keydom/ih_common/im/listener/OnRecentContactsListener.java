package com.keydom.ih_common.im.listener;

import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * @author THINKPAD B
 */
public interface OnRecentContactsListener {
    void onRecentResult(List<RecentContact> recentContacts);
}
