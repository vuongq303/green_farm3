package android.support.v4.app;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class by extends cj {
    CharSequence dE;
    CharSequence dF;
    List<bz> dG = new ArrayList();

    by() {
    }

    @Override // android.support.v4.app.cj
    public void a(Bundle bundle) {
        super.a(bundle);
        if (this.dE != null) {
            bundle.putCharSequence("android.selfDisplayName", this.dE);
        }
        if (this.dF != null) {
            bundle.putCharSequence("android.conversationTitle", this.dF);
        }
        if (!this.dG.isEmpty()) {
            bundle.putParcelableArray("android.messages", bz.b(this.dG));
        }
    }
}
