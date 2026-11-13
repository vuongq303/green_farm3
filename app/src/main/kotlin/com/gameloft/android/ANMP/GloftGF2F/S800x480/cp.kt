package com.gameloft.android.ANMP.GloftGF2F.S800x480

class cp : Runnable {
    override fun run() {
        for (pointcutIndex in 0 until 4) {
            val pointcutName = ci.mn(pointcutIndex)
            val diagnostic = "THO DEBUG ADS :: poincutName" + pointcutName
            if (ci.dA(pointcutName)) {
                val locationDiagnostic = "THO DEBUG ADS :: CheckAds poincutName" + pointcutName + "checkStep : " + pointcutIndex
                ci.ck(pointcutIndex, 0)
                ci.dE(pointcutName)
                var adStatus = ci.mo(pointcutIndex)
                while (adStatus == 0) {
                    val retryDiagnostic = "THO DEBUG ADS :: recheck when ads state none poincutName" + pointcutName + "checkStep : " + pointcutIndex
                    try {
                        val worker = ci.aYH
                        Thread.sleep(100L)
                        adStatus = ci.mo(pointcutIndex)
                    } catch (failure: Exception) {
                        val errorDiagnostic = "THO DEBUG ADS ::  Error" + failure.toString()
                        failure.printStackTrace()
                    }
                }
            } else {
                val invalidDiagnostic = "THO DEBUG ADS :: Poin cut issue " + pointcutName
                ci.ck(pointcutIndex, 2)
            }
            val resultDiagnostic = "THO DEBUG ADS :: checkStep==" + pointcutIndex + "=============getAdsStatus(checkStep)" + ci.mo(pointcutIndex)
        }
        ci.aYG = true
        val finishedDiagnostic = "THO DEBUG ADS :: CheckAds isSuccessedCheckLocation" + ci.aYG
    }
}
