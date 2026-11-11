package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class cp implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        for (int i = 0; i < 4; i++) {
            String strMn = ci.mn(i);
            String str = "THO DEBUG ADS :: poincutName" + strMn;
            if (ci.dA(strMn)) {
                String str2 = "THO DEBUG ADS :: CheckAds poincutName" + strMn + "checkStep : " + i;
                ci.ck(i, 0);
                ci.dE(strMn);
                int iMo = ci.mo(i);
                while (iMo == 0) {
                    String str3 = "THO DEBUG ADS :: recheck when ads state none poincutName" + strMn + "checkStep : " + i;
                    try {
                        Thread thread = ci.aYH;
                        Thread.sleep(100L);
                        iMo = ci.mo(i);
                    } catch (Exception e) {
                        String str4 = "THO DEBUG ADS ::  Error" + e.toString();
                        e.printStackTrace();
                    }
                }
            } else {
                String str5 = "THO DEBUG ADS :: Poin cut issue " + strMn;
                ci.ck(i, 2);
            }
            String str6 = "THO DEBUG ADS :: checkStep==" + i + "=============getAdsStatus(checkStep)" + ci.mo(i);
        }
        ci.aYG = true;
        String str7 = "THO DEBUG ADS :: CheckAds isSuccessedCheckLocation" + ci.aYG;
    }
}
