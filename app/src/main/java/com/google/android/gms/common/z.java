package com.google.android.gms.common;

/* JADX INFO: loaded from: classes.dex */
final class z extends x {
    private final i bRi;
    private final boolean bRj;
    private final boolean bRk;
    private final String packageName;

    private z(String str, i iVar, boolean z, boolean z2) {
        super(false, null, null);
        this.packageName = str;
        this.bRi = iVar;
        this.bRj = z;
        this.bRk = z2;
    }

    @Override // com.google.android.gms.common.x
    final String getErrorMessage() {
        String str = this.bRk ? "debug cert rejected" : "not whitelisted";
        String str2 = this.packageName;
        String strR = com.google.android.gms.common.d.m.r(com.google.android.gms.common.d.a.getMessageDigest("SHA-1").digest(this.bRi.getBytes()));
        return new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(str2).length() + String.valueOf(strR).length()).append(str).append(": pkg=").append(str2).append(", sha1=").append(strR).append(", atk=").append(this.bRj).append(", ver=12451009.false").toString();
    }
}
