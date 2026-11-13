package com.gameloft.android.ANMP.GloftGF2F.S800x480

import com.gameloft.android2d.adsmanager.AdsManager
import java.util.Locale
import org.json.JSONObject

/** Original rewarded-ad callback adapter; SDK identifiers and public flags are unchanged. */
open class b {
    private var Jo: JSONObject? = null
    private var Jp = false
    private var Jq = false
    private var Jr = false
    private var Js = false
    open fun CreateSingleAdsManagerInstance() { AdsManager.CreateSingleAdsManagerInstance() }
    open fun Init() {
        try { CreateSingleAdsManagerInstance() } catch (failure: Exception) { failure.printStackTrace() }
        com.gameloft.android2d.c.a.setGameLanguage(GloftGF2F.acw!!.dr(ci.aYU).lowercase(Locale.getDefault()))
        AdsManager.SetAge(ci.aTL)
        AdsManager.SetAge(ci.aTL)
        com.gameloft.android2d.c.a.setGender(if (ci.auC == 1) 1 else 2)
        com.gameloft.android2d.c.a.setAnonymousAccount(s.js())
        if (com.gameloft.a.b.a.Wp() != null) {
            val federationIdentifier = com.gameloft.a.b.a.Wp()
            com.gameloft.android2d.c.a.SetFedID(federationIdentifier.substring(0, federationIdentifier.indexOf(",")))
        }
        AdsManager.EnableGLAdsTracking(true)
        com.gameloft.android2d.c.a.init()
        Jn = true
    }
    open fun OnAdError(adType: Int, placement: String?) {
        if (adType == 3) {
            if (placement!!.equals("enter_section_iap_shop")) { JO = 1; JK = true; return }
            if (placement.equals("enter_section_video_bubble")) { JP = 1; JL = true }
            else if (placement.equals("enter_section_video_lucky_wheel")) { JQ = 1; JM = true }
            else if (placement.equals("enter_section_video_daily_stamp")) { JR = 1; JN = true }
        }
    }
    open fun OnAdFinished(adType: Int, resultCode: Int, placement: String?, auxiliary: String?, campaignId: Int, argument: Int) {
        if (adType == 3) {
            if (placement!!.equals("enter_section_iap_shop")) { JO = 1; JK = true; return }
            if (placement.equals("enter_section_video_bubble")) { JP = 1; JL = true }
            else if (placement.equals("enter_section_video_lucky_wheel")) { JQ = 1; JM = true }
            else if (placement.equals("enter_section_video_daily_stamp")) { JR = 1; JN = true }
        }
    }
    open fun e(placement: String?, availabilityStatus: Int) {
        val mappedStatus = when (availabilityStatus) { 0 -> 2; 1 -> 4; 2 -> 3; else -> 0 }
        if (placement!!.equals("enter_section_iap_shop")) { JO = mappedStatus; JK = true }
        else if (placement.equals("enter_section_video_bubble")) { JP = mappedStatus; JL = true }
        else if (placement.equals("enter_section_video_lucky_wheel")) { JQ = mappedStatus; JM = true }
        else if (placement.equals("enter_section_video_daily_stamp")) { JR = mappedStatus; JN = true }
        Kb = true
    }
    companion object {
        @JvmField val Jl: Array<String> = arrayOf("ads_minigame_timer_skip", "ads_gems", "ads_spin", "ads_double_sc", "ads_double_passport", "ads_construction_skip_time", "ads_diamonds", "ads_cash", "ads_free_item", "ads_storage_slot", "ads_product_skip_time", "ads_special_currency", "ads_daily_reward", "ads_product_poi_skip_time", "ads_product_supply_skip_time")
        private var Jm: b? = null
        @JvmField var Jn: Boolean = false
        private var Jt: Boolean = false
        @JvmField var Ju: String = ""
        @JvmField var Jv: Int = -1
        @JvmField var Jw: Boolean = false
        @JvmField var Jx: Boolean = false
        @JvmField var Jy: Boolean = false
        @JvmField var Jz: Boolean = false
        @JvmField var JA: Boolean = false
        @JvmField var JB: Boolean = false
        @JvmField var JC: Boolean = false
        @JvmField var JD: Boolean = false
        @JvmField var JE: Boolean = false
        @JvmField var JF: Boolean = false
        @JvmField var JG: Boolean = false
        @JvmField var JH: Boolean = false
        @JvmField var JI: Boolean = false
        @JvmField var JJ: Boolean = false
        @JvmField var JK: Boolean = false
        @JvmField var JL: Boolean = false
        @JvmField var JM: Boolean = false
        @JvmField var JN: Boolean = false
        @JvmField var JO: Int = 0
        @JvmField var JP: Int = 0
        @JvmField var JQ: Int = 0
        @JvmField var JR: Int = 0
        @JvmField var JS: Int = 0
        @JvmField var JT: Boolean = false
        @JvmField var JU: Boolean = false
        @JvmField var JV: Boolean = false
        @JvmField var JW: Boolean = false
        @JvmField var JX: Boolean = false
        @JvmField var JY: Boolean = false
        @JvmField var JZ: Boolean = false
        @JvmField var Ka: Boolean = false
        @JvmField var Kb: Boolean = true
        @JvmField var Kc: Boolean = false
        @JvmField var Kd: Boolean = false
        @JvmField var Ke: Boolean = false
        @JvmField var Kf: Boolean = false
        @JvmField var Kg: Boolean = false
        @JvmField var Kh: Boolean = false
        @JvmStatic fun ar(placement: String?) { AdsManager.IsIncentivizedAvailable(placement) }
        @JvmStatic fun hL(): b {
            if (Jm == null) Jm = b()
            return Jm!!
        }
    }
}
