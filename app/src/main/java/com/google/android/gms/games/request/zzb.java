package com.google.android.gms.games.request;

import android.os.Parcel;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class zzb extends com.google.android.gms.common.data.e implements GameRequest {
    private final int zzmz;

    public zzb(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzmz = i2;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Game adP() {
        return new GameRef(this.mDataHolder, this.mDataRow);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long aeq() {
        return getLong("creation_timestamp");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Player afl() {
        return new PlayerRef(this.mDataHolder, getDataRow(), "sender_");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.e
    public final boolean equals(Object obj) {
        return GameRequestEntity.a(this, obj);
    }

    @Override // com.google.android.gms.common.data.k
    public final /* synthetic */ GameRequest freeze() {
        return new GameRequestEntity(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final byte[] getData() {
        return getByteArray(ShareConstants.WEB_DIALOG_PARAM_DATA);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getExpirationTimestamp() {
        return getLong("expiration_timestamp");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final List<Player> getRecipients() {
        ArrayList arrayList = new ArrayList(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new PlayerRef(this.mDataHolder, this.mDataRow + i, "recipient_"));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final String getRequestId() {
        return getString("external_request_id");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getStatus() {
        return getInteger(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getType() {
        return getInteger("type");
    }

    @Override // com.google.android.gms.common.data.e
    public final int hashCode() {
        return GameRequestEntity.a(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int ix(String str) {
        for (int i = this.mDataRow; i < this.mDataRow + this.zzmz; i++) {
            int iNF = this.mDataHolder.nF(i);
            if (this.mDataHolder.r("recipient_external_player_id", i, iNF).equals(str)) {
                return this.mDataHolder.q("recipient_status", i, iNF);
            }
        }
        return -1;
    }

    public final String toString() {
        return GameRequestEntity.c(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((GameRequestEntity) ((GameRequest) freeze())).writeToParcel(parcel, i);
    }
}
