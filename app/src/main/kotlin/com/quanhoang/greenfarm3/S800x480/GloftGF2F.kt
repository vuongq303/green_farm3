package com.quanhoang.greenfarm3.S800x480

import android.view.KeyEvent
import javax.microedition.midlet.MIDlet
import com.quanhoang.greenfarm3.migration.SaveTransferActivity
import com.quanhoang.greenfarm3.migration.SaveTransferGate
import com.gameloft.android.wrapper.au as WrapperDeviceContext

/** Original MIDlet entry points; JVM names are required by the wrapper runtime. */
open class GloftGF2F : MIDlet() {
    init { acx = this }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && event?.isLongPress == true) {
            val activity = MIDlet.cnA
            if (activity != null && !activity.isFinishing) {
                SaveTransferActivity.launch(activity)
                return true
            }
        }
        if (keyCode == KeyEvent.KEYCODE_MENU && acw != null && LanguageRestartController.menuKeyDown()) return true
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && acw != null && LanguageRestartController.menuKeyUp()) return true
        return super.onKeyUp(keyCode, event)
    }

    @Synchronized
    public override fun mj() {
        if (acw != null) return
        try {
            val context = MIDlet.cnA ?: WrapperDeviceContext.getContext()
            if (context != null && !SaveTransferGate.enterGame(context)) {
                ProgressSaveController.showStartupError()
                return
            }
            ProgressSaveController.initialize()
        } catch (failure: RuntimeException) {
            ProgressSaveController.showStartupError()
            return
        }
        val game = FarmGameEngine(this, javax.microedition.lcdui.r.b(this))
        acw = game
        game.Init()
        try { acx!!.jf("MIDlet://stayAwake") } catch (unavailableKeepAwake: Exception) {
            // The original wrapper treats this optional command as best effort.
        }
    }

    public override fun mk() { acw?.Pause() }

    public override fun al(unconditional: Boolean) {
        if (acw == null) return
        FarmGameEngine.HH()
        FarmGameEngine.EZ()
        FarmGameEngine.Ms()
        FarmGameEngine.rE()
        if (!ProgressSaveController.flushForLifecycle()) return
        GameRuntime.be("GLLib.quit")
        GameRuntime.Qn = -1
    }

    companion object {
        @JvmField var acw: FarmGameEngine? = null
        @JvmField var acx: GloftGF2F? = null
    }
}
