package com.quanhoang.greenfarm3.S800x480

import android.opengl.GLES20

open class cb {
    @JvmField var akW: IntArray? = null
    @JvmField var akX = 0
    @JvmField var akY = 0
    @JvmField var akZ = 0
    @JvmField var ala = 0
    @JvmField var alb = 0
    @JvmField var alc = 0
    @JvmField var ald = 0
    @JvmField var ale: ay? = null
    open fun ot(): IntArray? = akW
    open fun ou(): Int = akX
    open fun ov(): Int = akY
    open fun ow(): Int = akZ
    open fun ox(): Int = ala
    open fun oy(): Int = alb
    open fun oz(): Int = alc
    open fun oA(): Int = ald
    open fun oB(): ay? = ale
    open fun b(texture: ay?) { ale = texture }
    open fun Destroy() {
        GLES20.glDeleteBuffers(2, akW, 0)
        akW = null
    }
    override fun toString(): String = "buf: " + akW!![0] + " " + akW!![1] + "\nstride " + akX + "\nindexDataLen " + akY + "\nblendMode " + akZ + "\nrenderMode " + ala + "\ncoordMode " + alb + "\nfxColor " + ald + "\ntexture " + ale
}
