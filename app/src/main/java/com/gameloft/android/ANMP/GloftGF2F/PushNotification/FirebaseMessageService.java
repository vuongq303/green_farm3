package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.Context;
import android.content.Intent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseMessageService extends FirebaseMessagingService {
    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Context applicationContext = getApplicationContext();
        Intent intent = new Intent();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        String from = remoteMessage.getFrom();
        String messageId = remoteMessage.getMessageId();
        long sentTime = remoteMessage.getSentTime();
        int ttl = remoteMessage.getTtl();
        intent.putExtra("from", from);
        intent.putExtra("message_id", messageId);
        intent.putExtra("sent_time", sentTime);
        intent.putExtra("ttl", ttl);
        if (remoteMessage.getData().size() > 0) {
            if (remoteMessage.getMessageType() == null) {
                com.gameloft.android.ANMP.GloftGF2F.g.b(applicationContext, intent);
                return;
            }
            return;
        }
        e.a(applicationContext, intent, w(applicationContext));
    }

    protected String w(Context context) {
        return x(context);
    }

    static final String x(Context context) {
        return context.getPackageName() + ".FirebaseMessageService";
    }
}
