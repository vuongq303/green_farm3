package com.gameloft.android.ANMP.GloftGF2F.S800x480

import android.os.SystemClock
import java.util.Locale

/** Original CRM lifecycle adapter. */
open class ah {
    companion object {
        @JvmField var Bj = 0
        @JvmField var acr = 0L
        @JvmField var acs = 0L
        @JvmField var act = false
        @JvmField var acu = false
        @JvmField var acv = false
        // The original APK's exact method bytecode is a single return instruction.
        @JvmStatic fun d(configuration: bc?) { }
        @JvmStatic fun et(languageIndex: Int) {
            var language = GloftGF2F.acw!!.dr(languageIndex)
            var country = GloftGF2F.acx!!.bY("microedition.locale.simcountry")
            // Preserve the original debug expression's js() call even though its string was unused.
            val diagnostic = "UpdateDeviceInfo - country " + country + " - " + s.js()
            if (country == null || country == "") country = GloftGF2F.acx!!.bY("microedition.locale.country")
            s.b(language.lowercase(Locale.getDefault()), com.gameloft.android.wrapper.au.Pn(), country, com.gameloft.android.wrapper.au.getDeviceName(), "hestiaSetDeviceInfo")
            if (language.lowercase(Locale.getDefault()) == "es_latam") language = "le"
            if (language.lowercase(Locale.getDefault()) == "kr" || language.lowercase(Locale.getDefault()) == "ko") language = "ko"
            if (language.lowercase(Locale.getDefault()) == "jp" || language.lowercase(Locale.getDefault()) == "ja") language = "ja"
            if (bz.om().fZ()) {
                val configuration = bc()
                configuration.a("language", language.lowercase(Locale.getDefault()))
                configuration.a("country", country)
                bz.om().i(configuration)
            }
        }
        @JvmStatic fun mc() { Bj = 0 }
        @JvmStatic fun mf() {
            when (Bj) {
                0 -> Bj = 1
                1 -> { mh(); Bj = 2 }
                2 -> if (ao.ms().mx() && !act) {
                    val configuration = ao.ms().mu()
                    if (configuration != null) { d(configuration); act = true }
                }
            }
        }
        @JvmStatic fun mh() { ao.ms().m("1001", "/InitialFeed.bin", "en") }
        @JvmStatic fun mi() { ci.qI() }
        @JvmStatic fun md() {
            i.ic().h(GLLib.Qy.toLong())
            val manager = ao.ms()
            if (manager.mw() && !acv) { acv = true; mi() }
            val updateState = manager.my()
            if (updateState == 0) mi()
            if (updateState == 1) {
                if (manager.mv() == 0) {
                    ci.aqB = true
                    mi()
                    val configuration = manager.mu()
                    if (configuration != null) d(configuration)
                } else {
                    val diagnostic = "GameCRMManager.Update =Refresh config get error:" + manager.mv()
                }
            }
            if (s.fZ() && !acu && bz.om().fZ()) {
                acu = true
                acs = nowMillis()
                manager.ev(2)
            }
        }
        @JvmStatic fun me() {
            val currentTimeMillis = nowMillis()
            if (currentTimeMillis - acs <= 1800000L || !acu) return
            acs = nowMillis()
            val diagnostic = "GameCRMManager._UpdateRefreshTimer =" + ao.ms().ev(2)
        }
        @JvmStatic fun mg() {
            acr = nowMillis()
            md()
            me()
            mf()
        }
        private fun nowMillis(): Long = if (w.Vl) (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To else System.currentTimeMillis()
    }
}
