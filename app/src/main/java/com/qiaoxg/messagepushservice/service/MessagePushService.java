package com.qiaoxg.messagepushservice.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.qiaoxg.messagepushservice.R;
import com.qiaoxg.messagepushservice.activity.TargetActivity;
import com.qiaoxg.messagepushservice.entity.MyMessageBean;

/**
 * Created by admin on 2017/8/4.
 */

public class MessagePushService extends Service {

    private static final String TAG = "MessagePushService";

    private static final int MSG_REQUEST_MESSAGE = 0;
    private static final int MSG_PUSH_MESSAGE = 1;

    private Notification notification;
    private NotificationManager notificationManager;
    private int messageID = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PUSH_MESSAGE:
                    if (msg.obj != null && msg.obj instanceof MyMessageBean) {
                        MyMessageBean messageStr = (MyMessageBean) msg.obj;
                        pushMessage(messageStr);
                    }
                    break;
                case MSG_REQUEST_MESSAGE:
                    requestMessage();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");

        notification = new Notification();
        notification.defaults = Notification.DEFAULT_SOUND;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (mHandler != null && mHandler.hasMessages(MSG_REQUEST_MESSAGE)) {
            mHandler.removeMessages(MSG_REQUEST_MESSAGE);
        }
        mHandler.sendEmptyMessage(MSG_REQUEST_MESSAGE);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    private void requestMessage() {
        MyMessageBean bean = new MyMessageBean();
        bean.setMessageId(messageID);
        String messageContent = "第" + messageID + "次通知，你有新的消息";
        bean.setContentInfo(messageID + "");
        bean.setContentText(messageContent);
        bean.setContentTitle("新消息");

        //设置点击推送消息后要跳转的页面
        Intent messageIntent = new Intent(this, TargetActivity.class);
        PendingIntent messagePendingIntent = PendingIntent.getActivity(this, 0,
                messageIntent, 0);
        bean.setPendingIntent(messagePendingIntent);


        Message msg = new Message();
        msg.what = MSG_PUSH_MESSAGE;
        msg.obj = bean;
        mHandler.sendMessage(msg);

        mHandler.sendEmptyMessageDelayed(MSG_REQUEST_MESSAGE, 10000);
    }

    private void pushMessage(MyMessageBean serverMessage) {
        Bitmap largeIcon = BitmapFactory.decodeResource(
                getBaseContext().getResources(), R.mipmap.ic_launcher);
        notification = new NotificationCompat.Builder(getBaseContext())
                // 设置大图标
                .setLargeIcon(largeIcon)
                // 设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 设置小图标旁的文本信息
                .setContentInfo(serverMessage.getContentInfo())
                // 设置状态栏文本标题
                .setTicker(serverMessage.getTicker())
                // 设置标题
                .setContentTitle(serverMessage.getContentTitle())
                // 设置内容
                .setContentText(serverMessage.getContentText())
                // 设置ContentIntent
                .setContentIntent(serverMessage.getPendingIntent())
                // 设置Notification提示铃声为系统默认铃声
                .setSound(
                        RingtoneManager.getActualDefaultRingtoneUri(
                                getBaseContext(),
                                RingtoneManager.TYPE_NOTIFICATION))

                // 点击Notification的时候使它自动消失
                .setAutoCancel(true).build();

        notificationManager.notify(serverMessage.getMessageId(), notification);
        messageID++;
    }
}
