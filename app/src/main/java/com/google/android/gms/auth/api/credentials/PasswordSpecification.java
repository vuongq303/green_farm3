package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class PasswordSpecification extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<PasswordSpecification> CREATOR = new l();
    public static final PasswordSpecification bFY = new d().cp(12, 16).hL("abcdefghijkmnopqrstxyzABCDEFGHJKLMNPQRSTXY3456789").J("abcdefghijkmnopqrstxyz", 1).J("ABCDEFGHJKLMNPQRSTXY", 1).J("3456789", 1).XY();
    private static final PasswordSpecification bFZ = new d().cp(12, 16).hL("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").J("abcdefghijklmnopqrstuvwxyz", 1).J("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1).J("1234567890", 1).XY();
    private final String bGa;
    private final List<String> bGb;
    private final List<Integer> bGc;
    private final int bGd;
    private final int bGe;
    private final int[] bGf;
    private final Random bGg;

    PasswordSpecification(String str, List<String> list, List<Integer> list2, int i, int i2) {
        this.bGa = str;
        this.bGb = Collections.unmodifiableList(list);
        this.bGc = Collections.unmodifiableList(list2);
        this.bGd = i;
        this.bGe = i2;
        int[] iArr = new int[95];
        Arrays.fill(iArr, -1);
        Iterator<String> it = this.bGb.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            for (char c : it.next().toCharArray()) {
                iArr[c - ' '] = i3;
            }
            i3++;
        }
        this.bGf = iArr;
        this.bGg = new SecureRandom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(Collection<Character> collection) {
        char[] cArr = new char[collection.size()];
        int i = 0;
        Iterator<Character> it = collection.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return new String(cArr);
            }
            i = i2 + 1;
            cArr[i2] = it.next().charValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean ac(int i, int i2, int i3) {
        return i < 32 || i > 126;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.bGa, false);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 2, this.bGb, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bGc, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, this.bGd);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 5, this.bGe);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
