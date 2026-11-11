package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: TaskStackBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public final class dq implements Iterable<Intent> {
    private static final dt el;
    private final ArrayList<Intent> em = new ArrayList<>();
    private final Context en;

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            el = new ds();
        } else {
            el = new dt();
        }
    }

    private dq(Context context) {
        this.en = context;
    }

    public static dq d(Context context) {
        return new dq(context);
    }

    public dq a(Intent intent) {
        this.em.add(intent);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public dq h(Activity activity) {
        Intent intentAi = null;
        if (activity instanceof dr) {
            intentAi = ((dr) activity).ai();
        }
        Intent intentF = intentAi == null ? bn.f(activity) : intentAi;
        if (intentF != null) {
            ComponentName component = intentF.getComponent();
            if (component == null) {
                component = intentF.resolveActivity(this.en.getPackageManager());
            }
            a(component);
            a(intentF);
        }
        return this;
    }

    public dq a(ComponentName componentName) {
        int size = this.em.size();
        try {
            Intent intentA = bn.a(this.en, componentName);
            while (intentA != null) {
                this.em.add(size, intentA);
                intentA = bn.a(this.en, intentA.getComponent());
            }
            return this;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    @Override // java.lang.Iterable
    @Deprecated
    public Iterator<Intent> iterator() {
        return this.em.iterator();
    }

    public void startActivities() {
        startActivities(null);
    }

    public void startActivities(Bundle bundle) {
        if (this.em.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        Intent[] intentArr = (Intent[]) this.em.toArray(new Intent[this.em.size()]);
        intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
        if (!android.support.v4.content.c.a(this.en, intentArr, bundle)) {
            Intent intent = new Intent(intentArr[intentArr.length - 1]);
            intent.addFlags(268435456);
            this.en.startActivity(intent);
        }
    }
}
