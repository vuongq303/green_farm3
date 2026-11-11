package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* JADX INFO: loaded from: classes.dex */
final class zzaae<FieldDescriptorType> extends zzaad<FieldDescriptorType, Object> {
    zzaae(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzaad
    public final void zzqy() {
        if (!isImmutable()) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= zzvb()) {
                    break;
                }
                Map.Entry<FieldDescriptorType, Object> entryZzaj = zzaj(i2);
                if (((zzyt) entryZzaj.getKey()).zzuc()) {
                    entryZzaj.setValue(Collections.unmodifiableList((List) entryZzaj.getValue()));
                }
                i = i2 + 1;
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzvc()) {
                if (((zzyt) entry.getKey()).zzuc()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzqy();
    }
}
