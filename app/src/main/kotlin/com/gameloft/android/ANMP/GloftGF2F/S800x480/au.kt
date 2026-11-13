package com.gameloft.android.ANMP.GloftGF2F.S800x480

import com.unity3d.ads.metadata.InAppPurchaseMetaData

open class au {
    private var purchaseData: bc? = null
    private var requestStarted = false
    private var legacyModeValue = 0
    open fun mO(): Boolean = requestStarted
    open fun eB(value: Int) { legacyModeValue = value }
    open fun bV(buffer: String?): Int {
        requestStarted = true
        purchaseData = bc(buffer).cd(InAppPurchaseMetaData.IAP_KEY) as bc?
        return if (purchaseData == null) -2147483622 else 0
    }
    companion object {
        private var instance: au? = null
        @JvmStatic fun mN(): au {
            if (instance == null) instance = au()
            return instance!!
        }
    }
}
