package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IInAppBillingService extends IInterface {
    int a(int i, String str, String str2);

    Bundle a(int i, String str, String str2, Bundle bundle);

    Bundle a(int i, String str, String str2, String str3);

    Bundle a(int i, String str, String str2, String str3, String str4);

    int b(int i, String str, String str2);

    public class Default implements IInAppBillingService {
        @Override // com.android.vending.billing.IInAppBillingService
        public int a(int i, String str, String str2) {
            return 0;
        }

        @Override // com.android.vending.billing.IInAppBillingService
        public Bundle a(int i, String str, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.android.vending.billing.IInAppBillingService
        public Bundle a(int i, String str, String str2, String str3, String str4) {
            return null;
        }

        @Override // com.android.vending.billing.IInAppBillingService
        public Bundle a(int i, String str, String str2, String str3) {
            return null;
        }

        @Override // com.android.vending.billing.IInAppBillingService
        public int b(int i, String str, String str2) {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract class Stub extends Binder implements IInAppBillingService {
        public Stub() {
            attachInterface(this, "com.android.vending.billing.IInAppBillingService");
        }

        public static IInAppBillingService d(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.vending.billing.IInAppBillingService");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInAppBillingService)) {
                return (IInAppBillingService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            Bundle bundle;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    int iA = a(parcel.readInt(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iA);
                    return true;
                case 2:
                    parcel.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        bundle = null;
                    }
                    Bundle bundleA = a(i3, string, string2, bundle);
                    parcel2.writeNoException();
                    if (bundleA != null) {
                        parcel2.writeInt(1);
                        bundleA.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 3:
                    parcel.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    Bundle bundleA2 = a(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (bundleA2 != null) {
                        parcel2.writeInt(1);
                        bundleA2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 4:
                    parcel.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    Bundle bundleA3 = a(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (bundleA3 != null) {
                        parcel2.writeInt(1);
                        bundleA3.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    int iB = b(parcel.readInt(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iB);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.android.vending.billing.IInAppBillingService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        class Proxy implements IInAppBillingService {
            public static IInAppBillingService zY;
            private IBinder F;

            Proxy(IBinder iBinder) {
                this.F = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.F;
            }

            @Override // com.android.vending.billing.IInAppBillingService
            public int a(int i, String str, String str2) {
                int iA;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (!this.F.transact(1, parcelObtain, parcelObtain2, 0) && Stub.fI() != null) {
                        iA = Stub.fI().a(i, str, str2);
                    } else {
                        parcelObtain2.readException();
                        iA = parcelObtain2.readInt();
                    }
                    return iA;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.vending.billing.IInAppBillingService
            public Bundle a(int i, String str, String str2, Bundle bundle) {
                Bundle bundleA;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.F.transact(2, parcelObtain, parcelObtain2, 0) && Stub.fI() != null) {
                        bundleA = Stub.fI().a(i, str, str2, bundle);
                    } else {
                        parcelObtain2.readException();
                        if (parcelObtain2.readInt() != 0) {
                            bundleA = (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2);
                        } else {
                            bundleA = null;
                        }
                    }
                    return bundleA;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.vending.billing.IInAppBillingService
            public Bundle a(int i, String str, String str2, String str3, String str4) {
                Bundle bundleA;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    if (!this.F.transact(3, parcelObtain, parcelObtain2, 0) && Stub.fI() != null) {
                        bundleA = Stub.fI().a(i, str, str2, str3, str4);
                    } else {
                        parcelObtain2.readException();
                        if (parcelObtain2.readInt() != 0) {
                            bundleA = (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2);
                        } else {
                            bundleA = null;
                        }
                    }
                    return bundleA;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.vending.billing.IInAppBillingService
            public Bundle a(int i, String str, String str2, String str3) {
                Bundle bundleA;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (!this.F.transact(4, parcelObtain, parcelObtain2, 0) && Stub.fI() != null) {
                        bundleA = Stub.fI().a(i, str, str2, str3);
                    } else {
                        parcelObtain2.readException();
                        if (parcelObtain2.readInt() != 0) {
                            bundleA = (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2);
                        } else {
                            bundleA = null;
                        }
                    }
                    return bundleA;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.vending.billing.IInAppBillingService
            public int b(int i, String str, String str2) {
                int iB;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (!this.F.transact(5, parcelObtain, parcelObtain2, 0) && Stub.fI() != null) {
                        iB = Stub.fI().b(i, str, str2);
                    } else {
                        parcelObtain2.readException();
                        iB = parcelObtain2.readInt();
                    }
                    return iB;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IInAppBillingService fI() {
            return Proxy.zY;
        }
    }
}
