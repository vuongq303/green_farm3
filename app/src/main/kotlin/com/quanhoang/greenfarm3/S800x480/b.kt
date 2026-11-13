package com.quanhoang.greenfarm3.S800x480

import org.json.JSONObject

/** Retired ad-provider ABI. No SDK dispatch, initialization or reward callback remains. */
open class b {
    private var Jo: JSONObject? = null
    private var Jp = false
    private var Jq = false
    private var Jr = false
    private var Js = false

    open fun CreateSingleAdsManagerInstance() { /* Provider removed; never create native state. */ }
    open fun Init() { /* Provider removed; never set Jn or read account credentials. */ }
    open fun OnAdError(adType: Int, placement: String?) {
        // No provider callback is registered and no availability/reward is armed.
    }
    open fun OnAdFinished(adType: Int, resultCode: Int, placement: String?, auxiliary: String?, campaignId: Int, argument: Int) {
        // Completion of a retired provider cannot create a reward or success state.
    }
    open fun e(placement: String?, availabilityStatus: Int) {
        // Retained signature only: no provider availability can be reported.
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
        @JvmStatic fun ar(placement: String?) { /* Provider unavailable; no SDK query. */ }
        @JvmStatic fun showIncentivized(placement: String?) { /* Provider removed; never open an ad. */ }
        @JvmStatic fun isAdOnScreen(adType: Int): Boolean = false
        @JvmStatic fun hL(): b {
            if (Jm == null) Jm = b()
            return Jm!!
        }
    }
}
