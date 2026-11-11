package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: loaded from: classes.dex */
public abstract class au extends Binder implements at {
    public static at m(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof at)) ? new av(iBinder) : (at) iInterfaceQueryLocalInterface;
    }

    protected void a(int i, aq aqVar, int i2, String str, String str2, String[] strArr, Bundle bundle, IBinder iBinder, String str3, String str4) {
        throw new UnsupportedOperationException();
    }

    protected void a(aq aqVar, ValidateAccountRequest validateAccountRequest) {
        throw new UnsupportedOperationException();
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        String string;
        String string2;
        IBinder strongBinder;
        Bundle bundle;
        String[] strArrCreateStringArray;
        String string3;
        if (i > 16777215) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
        aq aqVarL = ar.l(parcel.readStrongBinder());
        if (i == 46) {
            a(aqVarL, parcel.readInt() != 0 ? GetServiceRequest.CREATOR.createFromParcel(parcel) : null);
        } else if (i == 47) {
            a(aqVarL, parcel.readInt() != 0 ? ValidateAccountRequest.CREATOR.createFromParcel(parcel) : null);
        } else {
            int i3 = parcel.readInt();
            String string4 = i != 4 ? parcel.readString() : null;
            switch (i) {
                case 1:
                    string2 = parcel.readString();
                    strArrCreateStringArray = parcel.createStringArray();
                    string3 = parcel.readString();
                    if (parcel.readInt() == 0) {
                        string = null;
                        strongBinder = null;
                        bundle = null;
                    } else {
                        string = null;
                        strongBinder = null;
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case 2:
                case 5:
                case 6:
                case 7:
                case 8:
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                case 13:
                case 14:
                case 15:
                case 16:
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                case 18:
                case 23:
                case Constants.MAX_TREE_DEPTH /* 25 */:
                case 27:
                case 37:
                case 38:
                case 41:
                case 43:
                    if (parcel.readInt() != 0) {
                        string = null;
                        string2 = null;
                        strongBinder = null;
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        strArrCreateStringArray = null;
                        string3 = null;
                        break;
                    }
                case 3:
                case 4:
                case 21:
                case 22:
                case 24:
                case 26:
                case 28:
                case 29:
                case 31:
                case 32:
                case 33:
                case 35:
                case 36:
                case 39:
                case 40:
                case 42:
                default:
                    string = null;
                    string2 = null;
                    strongBinder = null;
                    bundle = null;
                    strArrCreateStringArray = null;
                    string3 = null;
                    break;
                case 9:
                    string3 = parcel.readString();
                    strArrCreateStringArray = parcel.createStringArray();
                    string2 = parcel.readString();
                    strongBinder = parcel.readStrongBinder();
                    string = parcel.readString();
                    bundle = parcel.readInt() == 0 ? null : (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    string3 = parcel.readString();
                    strArrCreateStringArray = parcel.createStringArray();
                    string = null;
                    string2 = null;
                    strongBinder = null;
                    bundle = null;
                    break;
                case 19:
                    strongBinder = parcel.readStrongBinder();
                    if (parcel.readInt() == 0) {
                        string = null;
                        string2 = null;
                        bundle = null;
                        strArrCreateStringArray = null;
                        string3 = null;
                    } else {
                        string = null;
                        string2 = null;
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        strArrCreateStringArray = null;
                        string3 = null;
                    }
                    break;
                case 20:
                case 30:
                    strArrCreateStringArray = parcel.createStringArray();
                    string3 = parcel.readString();
                    if (parcel.readInt() == 0) {
                        string = null;
                        string2 = null;
                        strongBinder = null;
                        bundle = null;
                    } else {
                        string = null;
                        string2 = null;
                        strongBinder = null;
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    }
                    break;
                case 34:
                    string3 = parcel.readString();
                    string = null;
                    string2 = null;
                    strongBinder = null;
                    bundle = null;
                    strArrCreateStringArray = null;
                    break;
            }
            a(i, aqVarL, i3, string4, string3, strArrCreateStringArray, bundle, strongBinder, string2, string);
        }
        parcel2.writeNoException();
        return true;
    }
}
