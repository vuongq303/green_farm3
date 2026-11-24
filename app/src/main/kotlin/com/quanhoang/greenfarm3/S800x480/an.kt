package com.quanhoang.greenfarm3.S800x480

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

/** Retains the existing HTTP trust-manager contract; not a replacement stub. */
class an : X509TrustManager {
    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
    override fun checkClientTrusted(chain: Array<out X509Certificate?>?, authType: String?) {}
    override fun checkServerTrusted(chain: Array<out X509Certificate?>?, authType: String?) {}
}
