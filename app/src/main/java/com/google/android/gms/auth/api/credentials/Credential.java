package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

/* JADX INFO: loaded from: classes.dex */
public class Credential extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<Credential> CREATOR = new f();
    private final Uri bFD;

    @Nonnull
    private final List<IdToken> bFE;
    private final String bFF;
    private final String bFG;
    private final String bFH;
    private final String bFI;
    private final String mName;
    private final String zzbx;

    @Nonnull
    private final String zzci;
    private final String zzco;

    /* JADX WARN: Code duplicated, block: B:29:0x0084  */
    Credential(String str, String str2, Uri uri, List<IdToken> list, String str3, String str4, String str5, String str6, String str7, String str8) {
        boolean z;
        String strTrim = ((String) bf.d(str, (Object) "credential identifier cannot be null")).trim();
        bf.d(strTrim, (Object) "credential identifier cannot be empty");
        if (str3 != null && TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Password must not be empty if set");
        }
        if (str4 != null) {
            if (TextUtils.isEmpty(str4)) {
                z = false;
            } else {
                Uri uri2 = Uri.parse(str4);
                if (!uri2.isAbsolute() || !uri2.isHierarchical() || TextUtils.isEmpty(uri2.getScheme()) || TextUtils.isEmpty(uri2.getAuthority())) {
                    z = false;
                } else if ("http".equalsIgnoreCase(uri2.getScheme()) || "https".equalsIgnoreCase(uri2.getScheme())) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (!Boolean.valueOf(z).booleanValue()) {
                throw new IllegalArgumentException("Account type must be a valid Http/Https URI");
            }
        }
        if (!TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Password and AccountType are mutually exclusive");
        }
        if (str2 != null && TextUtils.isEmpty(str2.trim())) {
            str2 = null;
        }
        this.mName = str2;
        this.bFD = uri;
        this.bFE = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.zzci = strTrim;
        this.bFF = str3;
        this.zzbx = str4;
        this.bFG = str5;
        this.bFH = str6;
        this.zzco = str7;
        this.bFI = str8;
    }

    public Uri XG() {
        return this.bFD;
    }

    @Nonnull
    public List<IdToken> XH() {
        return this.bFE;
    }

    public String XI() {
        return this.bFG;
    }

    public String XJ() {
        return this.zzbx;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Credential)) {
            return false;
        }
        Credential credential = (Credential) obj;
        return TextUtils.equals(this.zzci, credential.zzci) && TextUtils.equals(this.mName, credential.mName) && bd.b(this.bFD, credential.bFD) && TextUtils.equals(this.bFF, credential.bFF) && TextUtils.equals(this.zzbx, credential.zzbx) && TextUtils.equals(this.bFG, credential.bFG);
    }

    public String getFamilyName() {
        return this.bFI;
    }

    public String getGivenName() {
        return this.zzco;
    }

    @Nonnull
    public String getId() {
        return this.zzci;
    }

    public String getName() {
        return this.mName;
    }

    public String getPassword() {
        return this.bFF;
    }

    public int hashCode() {
        return bd.hashCode(this.zzci, this.mName, this.bFD, this.bFF, this.zzbx, this.bFG);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getId(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) XG(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, XH(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, getPassword(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, XJ(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, XI(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bFH, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, getGivenName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, getFamilyName(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
