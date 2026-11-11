package com.google.android.gms.auth.api.credentials;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private final TreeSet<Character> bGh = new TreeSet<>();
    private final List<String> bGb = new ArrayList();
    private final List<Integer> bGc = new ArrayList();
    private int bGd = 12;
    private int bGe = 16;

    private static TreeSet<Character> as(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new e(String.valueOf(str2).concat(" cannot be null or empty"));
        }
        TreeSet<Character> treeSet = new TreeSet<>();
        for (char c : str.toCharArray()) {
            if (PasswordSpecification.ac(c, 32, 126)) {
                throw new e(String.valueOf(str2).concat(" must only contain ASCII printable characters"));
            }
            treeSet.add(Character.valueOf(c));
        }
        return treeSet;
    }

    public final d J(String str, int i) {
        this.bGb.add(PasswordSpecification.a(as(str, "requiredChars")));
        this.bGc.add(1);
        return this;
    }

    public final PasswordSpecification XY() {
        if (this.bGh.isEmpty()) {
            throw new e("no allowed characters specified");
        }
        Iterator<Integer> it = this.bGc.iterator();
        int iIntValue = 0;
        while (it.hasNext()) {
            iIntValue = it.next().intValue() + iIntValue;
        }
        if (iIntValue > this.bGe) {
            throw new e("required character count cannot be greater than the max password size");
        }
        boolean[] zArr = new boolean[95];
        Iterator<String> it2 = this.bGb.iterator();
        while (it2.hasNext()) {
            for (char c : it2.next().toCharArray()) {
                if (zArr[c - ' ']) {
                    throw new e(new StringBuilder(58).append("character ").append(c).append(" occurs in more than one required character set").toString());
                }
                zArr[c - ' '] = true;
            }
        }
        return new PasswordSpecification(PasswordSpecification.a(this.bGh), this.bGb, this.bGc, this.bGd, this.bGe);
    }

    public final d cp(int i, int i2) {
        this.bGd = 12;
        this.bGe = 16;
        return this;
    }

    public final d hL(String str) {
        this.bGh.addAll(as(str, "allowedChars"));
        return this;
    }
}
