package com.google.android.gms.internal.ads;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzha {
    private final int zzaiz;
    private final zzgq zzajb;
    private String zzajj;
    private String zzajk;
    private final boolean zzajl = false;
    private final int zzajm;
    private final int zzajn;

    public zzha(int i, int i2, int i3) {
        this.zzaiz = i;
        if (i2 > 64 || i2 < 0) {
            this.zzajm = 64;
        } else {
            this.zzajm = i2;
        }
        if (i3 <= 0) {
            this.zzajn = 1;
        } else {
            this.zzajn = i3;
        }
        this.zzajb = new zzgz(this.zzajm);
    }

    /* JADX WARN: Code duplicated, block: B:77:0x014c  */
    public final String zza(ArrayList<String> arrayList, ArrayList<zzgp> arrayList2) {
        boolean z;
        String string;
        boolean z2;
        Collections.sort(arrayList2, new zzhb(this));
        HashSet hashSet = new HashSet();
        for (int i = 0; i < arrayList2.size(); i++) {
            String[] strArrSplit = Normalizer.normalize(arrayList.get(arrayList2.get(i).zzhf()), Normalizer.Form.NFKC).toLowerCase(Locale.US).split("\n");
            if (strArrSplit.length == 0) {
                z = true;
                break;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= strArrSplit.length) {
                    z = true;
                    break;
                }
                String str = strArrSplit[i2];
                if (str.indexOf("'") != -1) {
                    StringBuilder sb = new StringBuilder(str);
                    int i3 = 1;
                    boolean z3 = false;
                    while (i3 + 2 <= sb.length()) {
                        if (sb.charAt(i3) == '\'') {
                            if (sb.charAt(i3 - 1) == ' ' || !((sb.charAt(i3 + 1) == 's' || sb.charAt(i3 + 1) == 'S') && (i3 + 2 == sb.length() || sb.charAt(i3 + 2) == ' '))) {
                                sb.setCharAt(i3, ' ');
                            } else {
                                sb.insert(i3, ' ');
                                i3 += 2;
                            }
                            z3 = true;
                        }
                        i3++;
                    }
                    string = z3 ? sb.toString() : null;
                    if (string != null) {
                        this.zzajk = string;
                    } else {
                        string = str;
                    }
                } else {
                    string = str;
                }
                String[] strArrZzb = zzgu.zzb(string, true);
                if (strArrZzb.length >= this.zzajn) {
                    for (int i4 = 0; i4 < strArrZzb.length; i4++) {
                        String strConcat = "";
                        int i5 = 0;
                        while (true) {
                            if (i5 >= this.zzajn) {
                                z2 = true;
                                break;
                            }
                            if (i4 + i5 >= strArrZzb.length) {
                                z2 = false;
                                break;
                            }
                            if (i5 > 0) {
                                strConcat = String.valueOf(strConcat).concat(" ");
                            }
                            String strValueOf = String.valueOf(strConcat);
                            String strValueOf2 = String.valueOf(strArrZzb[i4 + i5]);
                            strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                            i5++;
                        }
                        if (!z2) {
                            break;
                        }
                        hashSet.add(strConcat);
                        if (hashSet.size() >= this.zzaiz) {
                            z = false;
                            break;
                        }
                    }
                    if (hashSet.size() >= this.zzaiz) {
                        z = false;
                        break;
                    }
                }
                i2++;
            }
            if (!z) {
                break;
            }
        }
        zzgt zzgtVar = new zzgt();
        this.zzajj = "";
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            try {
                zzgtVar.write(this.zzajb.zzx((String) it.next()));
            } catch (IOException e) {
                zzakb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzgtVar.toString();
    }
}
