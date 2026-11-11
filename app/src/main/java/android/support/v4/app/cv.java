package android.support.v4.app;

import android.app.Notification;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: NotificationCompatJellybean.java */
/* JADX INFO: loaded from: classes.dex */
class cv {
    private static Field dT;
    private static boolean dU;
    private static final Object dS = new Object();
    private static final Object dV = new Object();

    public static void a(bp bpVar, CharSequence charSequence, boolean z, CharSequence charSequence2, CharSequence charSequence3) {
        Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(bpVar.Z()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (z) {
            bigTextStyleBigText.setSummaryText(charSequence2);
        }
    }

    public static void a(bp bpVar, CharSequence charSequence, boolean z, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean z2) {
        Notification.BigPictureStyle bigPictureStyleBigPicture = new Notification.BigPictureStyle(bpVar.Z()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (z2) {
            bigPictureStyleBigPicture.bigLargeIcon(bitmap2);
        }
        if (z) {
            bigPictureStyleBigPicture.setSummaryText(charSequence2);
        }
    }

    public static void a(bp bpVar, CharSequence charSequence, boolean z, CharSequence charSequence2, ArrayList<CharSequence> arrayList) {
        Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(bpVar.Z()).setBigContentTitle(charSequence);
        if (z) {
            bigContentTitle.setSummaryText(charSequence2);
        }
        Iterator<CharSequence> it = arrayList.iterator();
        while (it.hasNext()) {
            bigContentTitle.addLine(it.next());
        }
    }

    public static SparseArray<Bundle> c(List<Bundle> list) {
        SparseArray<Bundle> sparseArray = null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Bundle bundle = list.get(i);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                sparseArray.put(i, bundle);
            }
        }
        return sparseArray;
    }

    public static Bundle a(Notification notification) {
        synchronized (dS) {
            if (dU) {
                return null;
            }
            try {
                if (dT == null) {
                    Field declaredField = Notification.class.getDeclaredField("extras");
                    if (!Bundle.class.isAssignableFrom(declaredField.getType())) {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        dU = true;
                        return null;
                    }
                    declaredField.setAccessible(true);
                    dT = declaredField;
                }
                Bundle bundle = (Bundle) dT.get(notification);
                if (bundle == null) {
                    bundle = new Bundle();
                    dT.set(notification, bundle);
                }
                return bundle;
            } catch (IllegalAccessException e) {
                Log.e("NotificationCompat", "Unable to access notification extras", e);
                dU = true;
                return null;
            } catch (NoSuchFieldException e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                dU = true;
                return null;
            }
        }
    }

    public static Bundle b(Notification.Builder builder, ct ctVar) {
        builder.addAction(ctVar.getIcon(), ctVar.getTitle(), ctVar.getActionIntent());
        Bundle bundle = new Bundle(ctVar.getExtras());
        if (ctVar.ad() != null) {
            bundle.putParcelableArray("android.support.remoteInputs", dl.b(ctVar.ad()));
        }
        if (ctVar.ac() != null) {
            bundle.putParcelableArray("android.support.dataRemoteInputs", dl.b(ctVar.ac()));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", ctVar.getAllowGeneratedReplies());
        return bundle;
    }
}
