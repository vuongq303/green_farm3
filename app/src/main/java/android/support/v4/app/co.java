package android.support.v4.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;

/* JADX INFO: compiled from: NotificationCompatApi24.java */
/* JADX INFO: loaded from: classes.dex */
class co {
    public static void a(bp bpVar, CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list, List<Long> list2, List<CharSequence> list3, List<String> list4, List<Uri> list5) {
        Notification.MessagingStyle conversationTitle = new Notification.MessagingStyle(charSequence).setConversationTitle(charSequence2);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size()) {
                Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(list.get(i2), list2.get(i2).longValue(), list3.get(i2));
                if (list4.get(i2) != null) {
                    message.setData(list4.get(i2), list5.get(i2));
                }
                conversationTitle.addMessage(message);
                i = i2 + 1;
            } else {
                conversationTitle.setBuilder(bpVar.Z());
                return;
            }
        }
    }

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
        builder2.setAllowGeneratedReplies(ctVar.getAllowGeneratedReplies());
        builder.addAction(builder2.build());
    }
}
