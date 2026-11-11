package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class a {
    private static boolean b = false;
    private static Application c;
    static WeakReference<Activity> ckN;

    /* JADX INFO: renamed from: com.moat.analytics.mobile.glft.a$a, reason: collision with other inner class name */
    class C0005a implements Application.ActivityLifecycleCallbacks {
        C0005a() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            try {
                n.a(3, "MoatActivityState", this, "Activity destroyed: " + activity.getClass() + "@" + activity.hashCode());
                if (a.b(activity)) {
                    a.ckN = new WeakReference<>(null);
                }
            } catch (Exception e) {
                l.j(e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            try {
                if (a.b(activity)) {
                    a.ckN = new WeakReference<>(null);
                }
                n.a(3, "MoatActivityState", this, "Activity paused: " + activity.getClass() + "@" + activity.hashCode());
            } catch (Exception e) {
                l.j(e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            try {
                a.ckN = new WeakReference<>(activity);
                u.ajL().f();
                n.a(3, "MoatActivityState", this, "Activity resumed: " + activity.getClass() + "@" + activity.hashCode());
            } catch (Exception e) {
                l.j(e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            try {
                a.ckN = new WeakReference<>(activity);
                n.a(3, "MoatActivityState", this, "Activity started: " + activity.getClass() + "@" + activity.hashCode());
            } catch (Exception e) {
                l.j(e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            try {
                if (a.b(activity)) {
                    a.ckN = new WeakReference<>(null);
                }
                n.a(3, "MoatActivityState", this, "Activity stopped: " + activity.getClass() + "@" + activity.hashCode());
            } catch (Exception e) {
                l.j(e);
            }
        }
    }

    a() {
    }

    static Application ajG() {
        return c;
    }

    static void b(Application application) {
        c = application;
        if (b) {
            return;
        }
        b = true;
        c.registerActivityLifecycleCallbacks(new C0005a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(Activity activity) {
        return ckN != null && ckN.get() == activity;
    }
}
