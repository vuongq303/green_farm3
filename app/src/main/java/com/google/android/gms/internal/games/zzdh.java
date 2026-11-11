package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.multiplayer.ParticipantResult;

/* JADX INFO: loaded from: classes.dex */
final class zzdh extends zzdw {
    private final /* synthetic */ String zzew;
    private final /* synthetic */ byte[] zzkj;
    private final /* synthetic */ ParticipantResult[] zzkl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdh(zzdb zzdbVar, w wVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        super(wVar, null);
        this.zzew = str;
        this.zzkj = bArr;
        this.zzkl = participantResultArr;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).a(this, this.zzew, this.zzkj, this.zzkl);
    }
}
