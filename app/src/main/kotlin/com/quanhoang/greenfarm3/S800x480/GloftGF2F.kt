package com.quanhoang.greenfarm3.S800x480

import android.view.KeyEvent
import javax.microedition.midlet.MIDlet
import com.quanhoang.greenfarm3.migration.SaveTransferActivity
import com.quanhoang.greenfarm3.migration.SaveTransferGate
import com.gameloft.android.wrapper.au as WrapperDeviceContext

/** Original MIDlet entry points; JVM names are required by the wrapper runtime. */
open class GloftGF2F : MIDlet() {
    @Volatile private var shutdownComplete = false
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
        synchronized(GloftGF2F::class.java) {
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
            try {
                synchronized(FarmGameEngine::class.java) {
                    try {
                        val game = FarmGameEngine(this, javax.microedition.lcdui.r.b(this))
                        // Binary bootstrap reads acw during Init. Frame callbacks
                        // share this monitor, so they cannot see half an Init.
                        acw = game
                        game.Init()
                    } catch (failure: RuntimeException) {
                        // Constructor can fail after the base canvas installed QD.
                        // Stop only that failed instance, before releasing frames.
                        GameRuntime.QD?.markInitializationFailed()
                        acw = null
                        throw failure
                    }
                }
            } catch (failure: RuntimeException) {
                android.util.Log.e("GreenFarmResources", "engine_initialize_failed", failure)
                ProgressSaveController.showEngineStartupError()
                return
            }
            try { acx!!.jf("MIDlet://stayAwake") } catch (unavailableKeepAwake: Exception) {
                // The original wrapper treats this optional command as best effort.
            }
        }
    }

    public override fun mk() { acw?.Pause() }

    public override fun al(unconditional: Boolean) {
        if (shutdownComplete || acx !== this || acw == null) return
        synchronized(FarmGameEngine::class.java) {
            if (shutdownComplete || acx !== this || acw == null) return
            // Capture while actors/commands still exist. Teardown before this
            // barrier can serialize an already-cleared world on normal exit.
            FarmGameEngine.rE()
            if (!ProgressSaveController.flushForLifecycle()) return
            // MIDlet can call destroyApp from both quit and onDestroy. Once
            // committed, no later callback may capture the cleared world.
            ProgressSaveController.worldUnloaded()
            FarmGameEngine.EZ()
            GameRuntime.be("GLLib.quit")
            GameRuntime.Qn = -1
            shutdownComplete = true
            acw = null
        }
    }

    companion object {
        @JvmField var acw: FarmGameEngine? = null
        @JvmField var acx: GloftGF2F? = null
    }
}
