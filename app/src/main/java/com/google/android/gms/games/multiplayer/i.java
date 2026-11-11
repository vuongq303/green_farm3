package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class i implements Parcelable.Creator<InvitationEntity> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: J, reason: merged with bridge method [inline-methods] */
    public InvitationEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        long jF = 0;
        int iD = 0;
        int iD2 = 0;
        ArrayList arrayListC = null;
        ParticipantEntity participantEntity = null;
        int iD3 = 0;
        String strN = null;
        GameEntity gameEntity = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    gameEntity = (GameEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, GameEntity.CREATOR);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 4:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 8:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new InvitationEntity(gameEntity, strN, jF, iD3, participantEntity, arrayListC, iD2, iD);
    }

    @Override // android.os.Parcelable.Creator
    public /* synthetic */ InvitationEntity[] newArray(int i) {
        return new InvitationEntity[i];
    }
}
