package com.gameloft.android.ANMP.GloftGF2F.S800x480

import com.gameloft.android2d.socialnetwork.l as SocialSdk

/** Original SocialNetwork adapter; no replacement authentication or fabricated results. */
open class bt {
    companion object {
        @JvmStatic fun I(first: String?, second: String?) { if (w.ZR) SocialSdk.I(first, second) }
        @JvmStatic fun ar(first: Int, second: Int) { if (w.ZR) SocialSdk.ar(first, second) }
        @JvmStatic fun nS() { if (w.ZR) bu.clear() }
        @JvmStatic fun eL(index: Int) { if (w.ZR) SocialSdk.eL(index) }
        @JvmStatic fun nT() { if (w.ZR) { SocialSdk.nT(); nS() } }
        @JvmStatic fun nU() { if (w.ZR) { SocialSdk.nU(); bu.clear() } }
        @JvmStatic fun nV() { if (w.ZR) SocialSdk.nV() }
        @JvmStatic fun nW(): Boolean = if (w.ZR) SocialSdk.nW() else false
        @JvmStatic fun nX(): Boolean = if (w.ZR) SocialSdk.nX() else false
        @JvmStatic fun nY(): Boolean = if (w.ZR) SocialSdk.nY() else false
        @JvmStatic fun nZ(): ai {
            if (!w.ZR) return ai()
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
        @JvmStatic fun getPassword(): String? = if (w.ZR) SocialSdk.getPassword() else ""
        @JvmStatic fun cD(identifier: String?): ay? {
            if (!w.ZR) return null
            if (!bu.cE(identifier)) {
                val image = SocialSdk.ha(identifier) ?: return null
                val result = ay(image)
                GLLib.be("Facebook : [Non-Friends] put to buffer " + identifier)
                bu.a(identifier, result)
                return result
            }
            return bu.oc()
        }
        @JvmStatic fun a(profile: ai?, friends: Array<ai?>?, first: String?, second: String?) {
            if (w.ZR) SocialSdk.a(profile, friends, first, second)
        }
        @JvmStatic fun a(firstProfiles: Array<ai?>?, secondProfiles: Array<ai?>?, first: String?, second: String?) {
            if (w.ZR) SocialSdk.a(firstProfiles, secondProfiles, first, second)
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
        @JvmStatic fun oa(): Int = SocialSdk.oa()
        @JvmStatic fun ob(): Array<ai?>? {
            if (w.ZR) {
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
            if (w.ZR) SocialSdk.b(a, b, c, d, e, f, g, h, i)
        }
        @JvmStatic fun g(a: String?, b: String?, c: String?, d: String?) { if (w.ZR) SocialSdk.g(a, b, c, d) }
    }
}
