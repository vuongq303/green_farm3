package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.video.VideoCapabilities;
import com.google.android.gms.internal.games.zzb;
import com.google.android.gms.internal.games.zzc;
import com.google.firebase.FirebaseError;

/* JADX INFO: loaded from: classes.dex */
public abstract class dk extends zzb implements dj {
    public dk() {
        super("com.google.android.gms.games.internal.IGamesCallbacks");
    }

    @Override // com.google.android.gms.internal.games.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 5001:
                zza(parcel.readInt(), parcel.readString());
                break;
            case 5002:
                a((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5003:
                zzb(parcel.readInt(), parcel.readString());
                break;
            case 5004:
                c((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5005:
                a((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), (DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5006:
                d((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5007:
                e((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5008:
                f((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5009:
                g((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5010:
                h((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5011:
                i((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5016:
                ZF();
                break;
            case 5017:
                k((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5018:
                s((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5019:
                t((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5020:
                ab(parcel.readInt(), parcel.readString());
                break;
            case 5021:
                u((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5022:
                v((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5023:
                w((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5024:
                x((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5025:
                y((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5026:
                a((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5027:
                b((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5028:
                c((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5029:
                d((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5030:
                e((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5031:
                f((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.createStringArray());
                break;
            case 5032:
                a((RealTimeMessage) zzc.zza(parcel, RealTimeMessage.CREATOR));
                break;
            case 5033:
                b(parcel.readInt(), parcel.readInt(), parcel.readString());
                break;
            case 5034:
                a(parcel.readInt(), parcel.readString(), zzc.zza(parcel));
                break;
            case 5035:
                A((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5036:
                zzc(parcel.readInt());
                break;
            case 5037:
                l((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5038:
                z((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5039:
                B((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 5040:
                no(parcel.readInt());
                break;
            case 6001:
                iu(parcel.readString());
                break;
            case 6002:
                iv(parcel.readString());
                break;
            case 8001:
                C((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8002:
                e(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 8003:
                n((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8004:
                o((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8005:
                p((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8006:
                q((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8007:
                zzc(parcel.readInt(), parcel.readString());
                break;
            case 8008:
                r((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 8009:
                it(parcel.readString());
                break;
            case 8010:
                ir(parcel.readString());
                break;
            case 9001:
                j((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 10001:
                m((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 10002:
                is(parcel.readString());
                break;
            case 10003:
                D((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 10004:
                E((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 10005:
                f(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 10006:
                F((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 11001:
                g(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 12001:
                G((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12003:
                h(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 12004:
                a((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), (Contents) zzc.zza(parcel, Contents.CREATOR));
                break;
            case 12005:
                H((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12006:
                I((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12007:
                J((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12008:
                M((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12011:
                b((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12012:
                ac(parcel.readInt(), parcel.readString());
                break;
            case 12013:
                N((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12014:
                K((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12015:
                i(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 12016:
                L((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 12017:
                a((DataHolder) zzc.zza(parcel, DataHolder.CREATOR), parcel.readString(), (Contents) zzc.zza(parcel, Contents.CREATOR), (Contents) zzc.zza(parcel, Contents.CREATOR), (Contents) zzc.zza(parcel, Contents.CREATOR));
                break;
            case 13001:
                O((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 13002:
                ol(parcel.readInt());
                break;
            case 14001:
                a((DataHolder[]) parcel.createTypedArray(DataHolder.CREATOR));
                break;
            case 15001:
                P((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case FirebaseError.ERROR_CUSTOM_TOKEN_MISMATCH /* 17002 */:
                om(parcel.readInt());
                break;
            case 19001:
                a(parcel.readInt(), (VideoCapabilities) zzc.zza(parcel, VideoCapabilities.CREATOR));
                break;
            case 19002:
                zza(parcel.readInt(), zzc.zza(parcel));
                break;
            case 19006:
                Q((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 19007:
                j(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            case 19008:
                zzg(parcel.readInt());
                break;
            case 19009:
                zzh(parcel.readInt());
                break;
            case 19010:
                zzi(parcel.readInt());
                break;
            case 20001:
                R((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20002:
                S((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20003:
                T((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20004:
                U((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20005:
                V((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20006:
                W((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20007:
                X((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20008:
                Y((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20009:
                Z((DataHolder) zzc.zza(parcel, DataHolder.CREATOR));
                break;
            case 20012:
                zzb((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 20019:
                on(parcel.readInt());
                break;
            case 20020:
                k(parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
