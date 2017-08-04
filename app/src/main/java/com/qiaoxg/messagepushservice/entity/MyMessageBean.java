package com.qiaoxg.messagepushservice.entity;

import android.app.PendingIntent;

/**
 * Created by admin on 2017/8/4.
 */

public class MyMessageBean {
    int messageId;
    // 设置小图标旁的文本信息
    String contentInfo;
    // 设置状态栏文本标题
    String ticker;
    // 设置标题
    String contentTitle;
    // 设置内容
    String contentText;
    // 设置ContentIntent
    PendingIntent pendingIntent;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }
}
