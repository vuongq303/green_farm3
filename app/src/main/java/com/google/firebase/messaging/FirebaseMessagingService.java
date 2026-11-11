package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.f.f;
import com.google.android.gms.f.j;
import com.google.firebase.iid.zzk;
import com.google.firebase.iid.zzz;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseMessagingService extends com.google.firebase.iid.zzb {
    private static final Queue<String> zzbss = new ArrayDeque(10);

    static void zzp(Bundle bundle) {
        Iterator<String> it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next != null && next.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    static boolean zzq(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(bundle.getString("google.c.a.e"));
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    public void onMessageSent(String str) {
    }

    public void onSendError(String str, Exception exc) {
    }

    @Override // com.google.firebase.iid.zzb
    protected final Intent zzf(Intent intent) {
        return zzz.zzta().zztb();
    }

    @Override // com.google.firebase.iid.zzb
    public final boolean zzg(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (zzq(intent.getExtras())) {
            zzd.zzd(this, intent);
        }
        return true;
    }

    @Override // com.google.firebase.iid.zzb
    public final void zzh(Intent intent) {
        f<Void> fVarZza;
        boolean z;
        byte b = 0;
        String action = intent.getAction();
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "com.google.android.c2dm.intent.RECEIVE":
                String stringExtra = intent.getStringExtra("google.message_id");
                if (TextUtils.isEmpty(stringExtra)) {
                    fVarZza = j.G(null);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("google.message_id", stringExtra);
                    fVarZza = zzk.zzv(this).zza(2, bundle);
                }
                if (TextUtils.isEmpty(stringExtra)) {
                    z = false;
                } else if (zzbss.contains(stringExtra)) {
                    if (Log.isLoggable("FirebaseMessaging", 3)) {
                        String strValueOf = String.valueOf(stringExtra);
                        Log.d("FirebaseMessaging", strValueOf.length() != 0 ? "Received duplicate message: ".concat(strValueOf) : new String("Received duplicate message: "));
                    }
                    z = true;
                } else {
                    if (zzbss.size() >= 10) {
                        zzbss.remove();
                    }
                    zzbss.add(stringExtra);
                    z = false;
                }
                if (!z) {
                    String stringExtra2 = intent.getStringExtra("message_type");
                    if (stringExtra2 == null) {
                        stringExtra2 = "gcm";
                    }
                    switch (stringExtra2.hashCode()) {
                        case -2062414158:
                            b = !stringExtra2.equals("deleted_messages") ? (byte) -1 : (byte) 1;
                            break;
                        case 102161:
                            if (!stringExtra2.equals("gcm")) {
                                b = -1;
                            }
                            break;
                        case 814694033:
                            b = !stringExtra2.equals("send_error") ? (byte) -1 : (byte) 3;
                            break;
                        case 814800675:
                            b = !stringExtra2.equals("send_event") ? (byte) -1 : (byte) 2;
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    switch (b) {
                        case 0:
                            if (zzq(intent.getExtras())) {
                                zzd.zzc(this, intent);
                            }
                            Bundle extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("android.support.content.wakelockid");
                            if (!zza.zzl(extras)) {
                                onMessageReceived(new RemoteMessage(extras));
                            } else if (!zza.zzw(this).zzn(extras)) {
                                if (zzq(extras)) {
                                    zzd.zzf(this, intent);
                                }
                                onMessageReceived(new RemoteMessage(extras));
                            }
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            String stringExtra3 = intent.getStringExtra("google.message_id");
                            if (stringExtra3 == null) {
                                stringExtra3 = intent.getStringExtra("message_id");
                            }
                            onSendError(stringExtra3, new SendException(intent.getStringExtra("error")));
                            break;
                        default:
                            String strValueOf2 = String.valueOf(stringExtra2);
                            Log.w("FirebaseMessaging", strValueOf2.length() != 0 ? "Received message with unknown type: ".concat(strValueOf2) : new String("Received message with unknown type: "));
                            break;
                    }
                }
                try {
                    j.a(fVarZza, 1L, TimeUnit.SECONDS);
                    break;
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    String strValueOf3 = String.valueOf(e);
                    Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strValueOf3).length() + 20).append("Message ack failed: ").append(strValueOf3).toString());
                    return;
                }
                break;
            case "com.google.firebase.messaging.NOTIFICATION_DISMISS":
                if (zzq(intent.getExtras())) {
                    zzd.zze(this, intent);
                    break;
                }
                break;
            default:
                String strValueOf4 = String.valueOf(intent.getAction());
                Log.d("FirebaseMessaging", strValueOf4.length() != 0 ? "Unknown intent action: ".concat(strValueOf4) : new String("Unknown intent action: "));
                break;
        }
    }
}
