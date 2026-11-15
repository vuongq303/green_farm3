package com.quanhoang.greenfarm3.S800x480

import com.gameloft.android2d.socialnetwork.l as SocialSdk

/** Original SocialNetwork adapter; no replacement authentication or fabricated results. */
open class bt {
    companion object {
        private fun legacyEnabled() = w.ZR && !LocalDevelopmentConfig.ONLINE_ACCOUNTS

        @JvmStatic fun I(first: String?, second: String?) { if (legacyEnabled()) SocialSdk.I(first, second) }
        @JvmStatic fun ar(first: Int, second: Int) { if (legacyEnabled()) SocialSdk.ar(first, second) }
        @JvmStatic fun nS() { if (legacyEnabled()) bu.clear() }
        @JvmStatic fun eL(index: Int) { if (legacyEnabled()) SocialSdk.eL(index) }
        @JvmStatic fun nT() { if (legacyEnabled()) { SocialSdk.nT(); nS() } }
        @JvmStatic fun nU() { if (legacyEnabled()) { SocialSdk.nU(); bu.clear() } }
        @JvmStatic fun nV() { if (legacyEnabled()) SocialSdk.nV() }
        @JvmStatic fun nW(): Boolean = if (legacyEnabled()) SocialSdk.nW() else false
        @JvmStatic fun nX(): Boolean = if (legacyEnabled()) SocialSdk.nX() else false
        @JvmStatic fun nY(): Boolean = if (legacyEnabled()) SocialSdk.nY() else false
        @JvmStatic fun nZ(): ai {
            if (!legacyEnabled()) return ai()
            val profile = SocialSdk.UR()
            val result = ai()
            result.setName(profile.getName())
            result.gU(profile.getFirstName())
            result.gV(profile.getLastName())
            result.gW(profile.getMiddleName())
            result.fG(profile.getId())
            result.bL(profile.UO())
            result.gX(profile.getCountry())
            result.mT(profile.getScore())
            result.a(profile.UP())
            result.eu(-2)
            return result
        }
        @JvmStatic fun getPassword(): String? = if (legacyEnabled()) SocialSdk.getPassword() else ""
        @JvmStatic fun cD(identifier: String?): ay? {
            if (!legacyEnabled()) return null
            if (!bu.cE(identifier)) {
                val image = SocialSdk.ha(identifier) ?: return null
                val result = ay(image)
                GameRuntime.be("Facebook : [Non-Friends] put to buffer " + identifier)
                bu.a(identifier, result)
                return result
            }
            return bu.oc()
        }
        @JvmStatic fun a(profile: ai?, friends: Array<ai?>?, first: String?, second: String?) {
            if (legacyEnabled()) SocialSdk.a(profile, friends, first, second)
        }
        @JvmStatic fun a(firstProfiles: Array<ai?>?, secondProfiles: Array<ai?>?, first: String?, second: String?) {
            if (legacyEnabled()) SocialSdk.a(firstProfiles, secondProfiles, first, second)
        }
        @JvmStatic fun eM(index: Int): ai? {
            if (oa() == 0) return null
            val profile = SocialSdk.mV(index)
            val result = ai()
            result.setName(profile.getName())
            result.gU(profile.getFirstName())
            result.gV(profile.getLastName())
            result.gW(profile.getMiddleName())
            result.fG(profile.getId())
            result.bL(profile.UO())
            result.gX(profile.getCountry())
            result.mT(profile.getScore())
            result.a(profile.UP())
            result.eu(index)
            return result
        }
        @JvmStatic fun oa(): Int = if (legacyEnabled()) SocialSdk.oa() else 0
        @JvmStatic fun ob(): Array<ai?>? {
            if (legacyEnabled()) {
                try {
                    val profiles = arrayOfNulls<ai>(oa())
                    var index = 0
                    while (index < oa()) { profiles[index] = eM(index); index++ }
                    return profiles
                } catch (_: Exception) { /* Preserve original failure return; no fake social profile. */ }
            }
            return null
        }
        @JvmStatic fun b(a: String?, b: String?, c: String?, d: String?, e: String?, f: String?, g: String?, h: String?, i: String?) {
            if (legacyEnabled()) SocialSdk.b(a, b, c, d, e, f, g, h, i)
        }
        @JvmStatic fun g(a: String?, b: String?, c: String?, d: String?) { if (legacyEnabled()) SocialSdk.g(a, b, c, d) }
    }
}
