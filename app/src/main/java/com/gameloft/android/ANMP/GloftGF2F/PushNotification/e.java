package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: GCMBaseIntentService.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class e extends IntentService {
    private static PowerManager.WakeLock Fg;
    private final String[] Fh;
    private static final Object LOCK = e.class;
    private static int Fi = 0;
    private static final Random Fj = new Random();
    private static final int Fk = (int) TimeUnit.SECONDS.toMillis(3600);
    private static final String Fl = Long.toBinaryString(Fj.nextLong());

    protected abstract void a(Context context, Intent intent);

    protected abstract void f(Context context, String str);

    protected abstract void g(Context context, String str);

    protected abstract void h(Context context, String str);

    protected e() {
        this(getName("DynamicSenderIds"), null);
    }

    private e(String str, String[] strArr) {
        super(str);
        this.Fh = strArr;
    }

    private static String getName(String str) {
        StringBuilder sbAppend = new StringBuilder().append("GCMIntentService-").append(str).append("-");
        int i = Fi + 1;
        Fi = i;
        String string = sbAppend.append(i).toString();
        String str2 = "Intent service name: " + string;
        return string;
    }

    protected String[] y(Context context) {
        if (this.Fh == null) {
            throw new IllegalStateException("sender id not set on constructor");
        }
        return this.Fh;
    }

    protected void r(Context context, int i) {
    }

    protected boolean i(Context context, String str) {
        return true;
    }

    @Override // android.app.IntentService
    public final void onHandleIntent(Intent intent) {
        try {
            Context applicationContext = getApplicationContext();
            String action = intent.getAction();
            if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
                h.B(applicationContext);
                c(applicationContext, intent);
            } else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                String stringExtra = intent.getStringExtra("message_type");
                if (stringExtra == null) {
                    a(applicationContext, intent);
                } else if (stringExtra.equals("deleted_messages")) {
                    String stringExtra2 = intent.getStringExtra("total_deleted");
                    if (stringExtra2 != null) {
                        try {
                            int i = Integer.parseInt(stringExtra2);
                            String str = "Received deleted messages notification: " + i;
                            r(applicationContext, i);
                        } catch (NumberFormatException e) {
                            String str2 = "GCM returned invalid number of deleted messages: " + stringExtra2;
                        }
                    }
                } else {
                    String str3 = "Received unknown special message: " + stringExtra;
                }
            } else if (action.equals("com.google.android.gcm.intent.RETRY")) {
                String stringExtra3 = intent.getStringExtra("token");
                if (!Fl.equals(stringExtra3)) {
                    String str4 = "Received invalid token: " + stringExtra3;
                    synchronized (LOCK) {
                        if (Fg != null) {
                            Fg.release();
                        }
                    }
                    return;
                }
                if (h.D(applicationContext)) {
                    h.A(applicationContext);
                } else {
                    h.a(applicationContext, y(applicationContext));
                }
            }
            synchronized (LOCK) {
                if (Fg != null) {
                    Fg.release();
                }
            }
        } catch (Throwable th) {
            synchronized (LOCK) {
                if (Fg != null) {
                    Fg.release();
                }
                throw th;
            }
        }
    }

    static void a(Context context, Intent intent, String str) {
        synchronized (LOCK) {
            if (Fg == null) {
                Fg = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "GCM_LIB");
            }
        }
        Fg.acquire();
        intent.setClassName(context, str);
        try {
            context.startService(intent);
        } catch (Exception e) {
        }
    }

    private void c(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("registration_id");
        String stringExtra2 = intent.getStringExtra("error");
        String stringExtra3 = intent.getStringExtra("unregistered");
        String str = "handleRegistration: registrationId = " + stringExtra + ", error = " + stringExtra2 + ", unregistered = " + stringExtra3;
        if (stringExtra != null) {
            h.H(context);
            h.k(context, stringExtra);
            f(context, stringExtra);
            return;
        }
        if (stringExtra3 != null) {
            h.H(context);
            g(context, h.E(context));
            return;
        }
        String str2 = "Registration error: " + stringExtra2;
        if ("SERVICE_NOT_AVAILABLE".equals(stringExtra2)) {
            if (i(context, stringExtra2)) {
                int I = h.I(context);
                int iNextInt = Fj.nextInt(I) + (I / 2);
                String str3 = "Scheduling registration retry, backoff = " + iNextInt + " (" + I + ")";
                Intent intent2 = new Intent("com.google.android.gcm.intent.RETRY");
                intent2.putExtra("token", Fl);
                ((AlarmManager) context.getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + ((long) iNextInt), PendingIntent.getBroadcast(context, 0, intent2, 0));
                if (I < Fk) {
                    h.s(context, I * 2);
                    return;
                }
                return;
            }
            return;
        }
        h(context, stringExtra2);
    }
}
