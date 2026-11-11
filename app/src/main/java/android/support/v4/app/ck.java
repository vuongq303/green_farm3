package android.support.v4.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.os.Bundle;

/* JADX INFO: compiled from: NotificationCompatApi20.java */
/* JADX INFO: loaded from: classes.dex */
class ck {
    public static void a(Notification.Builder builder, ct ctVar) {
        Bundle bundle;
        Notification.Action.Builder builder2 = new Notification.Action.Builder(ctVar.getIcon(), ctVar.getTitle(), ctVar.getActionIntent());
        if (ctVar.ad() != null) {
            for (RemoteInput remoteInput : dh.a(ctVar.ad())) {
                builder2.addRemoteInput(remoteInput);
            }
        }
        if (ctVar.getExtras() != null) {
            bundle = new Bundle(ctVar.getExtras());
        } else {
            bundle = new Bundle();
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", ctVar.getAllowGeneratedReplies());
        builder2.addExtras(bundle);
        builder.addAction(builder2.build());
    }
}
