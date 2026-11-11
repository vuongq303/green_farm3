package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IMarketBillingService extends IInterface {
    Bundle d(Bundle bundle);

    public class Default implements IMarketBillingService {
        @Override // com.android.vending.billing.IMarketBillingService
        public Bundle d(Bundle bundle) {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract class Stub extends Binder implements IMarketBillingService {
        public Stub() {
            attachInterface(this, "com.android.vending.billing.IMarketBillingService");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            Bundle bundle;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.android.vending.billing.IMarketBillingService");
                    if (parcel.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        bundle = null;
                    }
                    Bundle bundleD = d(bundle);
                    parcel2.writeNoException();
                    if (bundleD != null) {
                        parcel2.writeInt(1);
                        bundleD.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    parcel2.writeString("com.android.vending.billing.IMarketBillingService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        class Proxy implements IMarketBillingService {
            public static IMarketBillingService zZ;
            private IBinder F;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.F;
            }

            @Override // com.android.vending.billing.IMarketBillingService
            public Bundle d(Bundle bundle) {
                Bundle bundleD;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IMarketBillingService");
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.F.transact(1, parcelObtain, parcelObtain2, 0) && Stub.fJ() != null) {
                        bundleD = Stub.fJ().d(bundle);
                    } else {
                        parcelObtain2.readException();
                        if (parcelObtain2.readInt() != 0) {
                            bundleD = (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2);
                        } else {
                            bundleD = null;
                        }
                    }
                    return bundleD;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IMarketBillingService fJ() {
            return Proxy.zZ;
        }
    }
}
