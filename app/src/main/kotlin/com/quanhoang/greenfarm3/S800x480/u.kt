package com.quanhoang.greenfarm3.S800x480

open class u(statusCode: Int, responseText: String?, responseBytes: ByteArray?, responseTag: String?, errorCode: Int, errorMessage: String?, cause: Exception?) {
    private var federationStatusCode = statusCode
    private var decodedResponseText = responseText
    private var rawResponseBytes = responseBytes
    private var responseErrorCode = errorCode
    private var responseErrorMessage = errorMessage
    private var exception = cause
    private var tag = responseTag
    open fun jw(): Int = federationStatusCode
    open fun jx(): Exception? = exception
    open fun jy(): Int = responseErrorCode
    open fun jz(): String? = responseErrorMessage
    open fun jA(): String? = tag
    open fun jB(): String? {
        if (decodedResponseText != null) return decodedResponseText
        if (rawResponseBytes != null) {
            decodedResponseText = String(rawResponseBytes!!, java.nio.charset.Charset.defaultCharset())
            return decodedResponseText
        }
        return null
    }
}
