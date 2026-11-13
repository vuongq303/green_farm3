package com.quanhoang.greenfarm3.S800x480

class cp : Runnable {
    override fun run() {
        for (pointcutIndex in 0 until 4) {
            val pointcutName = FarmGameEngine.mn(pointcutIndex)
            val diagnostic = "THO DEBUG ADS :: poincutName" + pointcutName
            if (FarmGameEngine.dA(pointcutName)) {
                val locationDiagnostic = "THO DEBUG ADS :: CheckAds poincutName" + pointcutName + "checkStep : " + pointcutIndex
                FarmGameEngine.ck(pointcutIndex, 0)
                FarmGameEngine.dE(pointcutName)
                var adStatus = FarmGameEngine.mo(pointcutIndex)
                while (adStatus == 0) {
                    val retryDiagnostic = "THO DEBUG ADS :: recheck when ads state none poincutName" + pointcutName + "checkStep : " + pointcutIndex
                    try {
                        val worker = FarmGameEngine.aYH
                        Thread.sleep(100L)
                        adStatus = FarmGameEngine.mo(pointcutIndex)
                    } catch (failure: Exception) {
                        val errorDiagnostic = "THO DEBUG ADS ::  Error" + failure.toString()
                        failure.printStackTrace()
                    }
                }
            } else {
                val invalidDiagnostic = "THO DEBUG ADS :: Poin cut issue " + pointcutName
                FarmGameEngine.ck(pointcutIndex, 2)
            }
            val resultDiagnostic = "THO DEBUG ADS :: checkStep==" + pointcutIndex + "=============getAdsStatus(checkStep)" + FarmGameEngine.mo(pointcutIndex)
        }
        FarmGameEngine.aYG = true
        val finishedDiagnostic = "THO DEBUG ADS :: CheckAds isSuccessedCheckLocation" + FarmGameEngine.aYG
    }
}
