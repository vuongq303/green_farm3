package android.support.v4.app;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class bq {
    static final ci cJ;

    static void a(bo boVar, ArrayList<br> arrayList) {
        Iterator<br> it = arrayList.iterator();
        while (it.hasNext()) {
            boVar.a(it.next());
        }
    }

    static void a(bp bpVar, cj cjVar) {
        if (cjVar != null) {
            if (cjVar instanceof bu) {
                bu buVar = (bu) cjVar;
                cv.a(bpVar, buVar.dN, buVar.dP, buVar.dO, buVar.cR);
            } else if (cjVar instanceof bx) {
                bx bxVar = (bx) cjVar;
                cv.a(bpVar, bxVar.dN, bxVar.dP, bxVar.dO, bxVar.dD);
            } else if (cjVar instanceof bt) {
                bt btVar = (bt) cjVar;
                cv.a(bpVar, btVar.dN, btVar.dP, btVar.dO, btVar.cO, btVar.cP, btVar.cQ);
            }
        }
    }

    static void b(bp bpVar, cj cjVar) {
        if (cjVar != null) {
            if (cjVar instanceof by) {
                by byVar = (by) cjVar;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                for (bz bzVar : byVar.dG) {
                    arrayList.add(bzVar.getText());
                    arrayList2.add(Long.valueOf(bzVar.getTimestamp()));
                    arrayList3.add(bzVar.getSender());
                    arrayList4.add(bzVar.getDataMimeType());
                    arrayList5.add(bzVar.getDataUri());
                }
                co.a(bpVar, byVar.dE, byVar.dF, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
                return;
            }
            a(bpVar, cjVar);
        }
    }

    static {
        if (android.support.v4.c.a.ax()) {
            cJ = new cf();
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            cJ = new ce();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            cJ = new cd();
            return;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            cJ = new cc();
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            cJ = new cb();
        } else if (Build.VERSION.SDK_INT >= 16) {
            cJ = new ca();
        } else {
            cJ = new cg();
        }
    }

    public static Bundle a(Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return cv.a(notification);
        }
        return null;
    }
}
