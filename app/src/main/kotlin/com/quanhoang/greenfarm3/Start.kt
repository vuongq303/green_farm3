package com.quanhoang.greenfarm3

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import javax.microedition.midlet.BaseActivity
import javax.microedition.midlet.MIDlet
import com.quanhoang.greenfarm3.migration.SaveTransferGate
import com.quanhoang.greenfarm3.migration.SaveTransferShortcuts
import com.quanhoang.greenfarm3.migration.SaveTransferActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.quanhoang.greenfarm3.S800x480.BackendCloudController

/** Android launcher configuration recovered from the original Start bytecode. */
open class Start : BaseActivity() {
    private var backendGateOnly = false
    // Original wrapper runnables still access these exact JVM fields.
    @JvmField var bcS: e? = null
    @JvmField var bcT: Int = 0
    @JvmField var bcU: Int = 0
    @JvmField var bcV: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!SaveTransferGate.enterGame(this)) {
            super.onCreate(savedInstanceState)
            Toast.makeText(this, "Đang chuyển bản lưu. Hãy hoàn tất hoặc đóng màn hình chuyển dữ liệu rồi mở lại game.", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if (!BackendCloudController.isPrepared()) {
            backendGateOnly = true
            super.onCreate(savedInstanceState)
            BackendCloudController.prepare(this)
            return
        }
        bcR = this
        MIDlet.cnA = this
        com.gameloft.android.wrapper.d.beG = "sensorLandscape"
        val display = (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
        val displayWidthPixels = display.width
        val displayHeightPixels = display.height
        javax.microedition.lcdui.f.setScale(1.0f, 1.0f)
        com.gameloft.android.wrapper.d.beH = "Multiple_axis_auto_align".compareTo("Single_axis_auto_align") == 0
        com.gameloft.android.wrapper.d.beI = "Multiple_axis_auto_align".compareTo("Multiple_axis_auto_align") == 0
        javax.microedition.lcdui.f.cm(false)
        javax.microedition.lcdui.f.cn(false)
        javax.microedition.lcdui.f.pc(-7)
        javax.microedition.lcdui.f.pd(-8)
        MIDlet.cnD = false
        MIDlet.cor = false
        MIDlet.cnF = false
        MIDlet.cnG = false
        MIDlet.cnK = false
        MIDlet.coq = true
        MIDlet.cnH = true
        MIDlet.cnJ = false
        MIDlet.cnR = false
        MIDlet.coL = 5
        com.gameloft.android.wrapper.d.beL = false
        com.gameloft.android.wrapper.d.beF = false
        com.gameloft.android.wrapper.d.beP = false
        com.gameloft.android.wrapper.d.beQ = false
        com.gameloft.android.wrapper.d.bff = false
        com.gameloft.android.wrapper.d.bfk = false
        com.gameloft.android.wrapper.d.bfl = "sensorLandscape".compareTo("sensorLandscape") == 0
        com.gameloft.android.wrapper.au.biF = 0
        com.gameloft.android.wrapper.au.biG = 0
        com.gameloft.android.wrapper.au.biI = 0
        com.gameloft.android.wrapper.au.biL = 0
        com.gameloft.android.wrapper.au.biM = 0
        MIDlet.cnS = true
        MIDlet.coR = false
        MIDlet.cnY = true
        MIDlet.cnZ = "USE_IAP_GOOGLE_BILLING"
        MIDlet.cnT = false
        MIDlet.cnU = false
        MIDlet.cnV = false
        MIDlet.coh = ""
        MIDlet.cnW = 1
        MIDlet.coa = false
        MIDlet.cob = false
        MIDlet.coc = true
        MIDlet.cod = true
        MIDlet.coe = true
        MIDlet.cof = false
        com.gameloft.android.wrapper.d.beN = false
        com.gameloft.android.wrapper.d.beO = false
        MIDlet.cog = false
        MIDlet.cnO = false
        MIDlet.cnP = true
        com.gameloft.android.wrapper.d.beR = "ANMP"
        com.gameloft.android.wrapper.d.beS = false
        com.gameloft.android.wrapper.d.beT = false
        com.gameloft.android.wrapper.d.beU = "LBCL"
        com.gameloft.android.wrapper.d.beV = "BFLL"
        com.gameloft.android.wrapper.d.beW = "BFL4"
        com.gameloft.android.wrapper.d.beX = false
        com.gameloft.android.wrapper.d.beY = false
        com.gameloft.android.wrapper.d.beZ = "14.0"
        com.gameloft.android.wrapper.d.bfa = true
        MIDlet.col = false
        MIDlet.`com` = false
        MIDlet.cnL = true
        com.gameloft.android.wrapper.d.beR = com.gameloft.android.wrapper.d.beR
        MIDlet.VERSION = "44220"
        MIDlet.cnN = "4.4.2"
        com.gameloft.android.wrapper.d.beJ = 2
        com.gameloft.android.wrapper.d.bfb = false
        com.gameloft.android.wrapper.d.bfc = false
        com.gameloft.android.wrapper.d.bfd = false
        com.gameloft.android.wrapper.d.bfe = false
        com.gameloft.android.wrapper.d.beD = true
        com.gameloft.android.wrapper.d.bfg = 20
        MIDlet.coI = false
        com.gameloft.android.wrapper.d.bfm = 0
        com.gameloft.android.wrapper.d.bfn = 0
        if (com.gameloft.android.wrapper.au.u(this, "")) {
            super.onCreate(savedInstanceState)
            SaveTransferShortcuts.install(this)
            return
        }
        super.onCreate(savedInstanceState)
        SaveTransferShortcuts.install(this)
        if (Build.VERSION.SDK_INT >= 19) com.gameloft.android.wrapper.d.beM = true
        com.gameloft.android.wrapper.z.k(this)
        MIDlet.cos = true
        com.gameloft.android.wrapper.d.beK = false
        com.gameloft.android.wrapper.d.bfi = ""
        com.gameloft.android.wrapper.d.bfj = ""
        com.gameloft.android.wrapper.d.bfh = false
        createStartupSurface()
        Pt.hb().init()
    }

    /** Keep Nh() for the original wrapper's binary contract. */
    @JvmName("Nh")
    fun createStartupSurface() {
        val startupLayout = LinearLayout(this)
        bcW = this
        val startupView = TextView(this)
        bcV = startupView
        val fullScreen = ViewGroup.LayoutParams(-1, -1)
        startupLayout.addView(startupView, FrameLayout.LayoutParams(fullScreen))
        startupLayout.setBackgroundColor(Color.parseColor("#FF000000"))
        startupView.setBackgroundColor(Color.parseColor("#FF000000"))
        startupLayout.orientation = 0
        startupLayout.layoutParams = fullScreen
        setContentView(startupLayout)
        if (Build.VERSION.SDK_INT >= 28) startupLayout.setOnApplyWindowInsetsListener(bs(this))
        startupView.post(bt(this))
    }

    override fun onPause() = super.onPause()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(0, SAVE_TRANSFER_MENU_ID, 0, "Chuyển bản lưu")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == SAVE_TRANSFER_MENU_ID) {
            SaveTransferActivity.launch(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (backendGateOnly) return
        try { Thread.sleep(1000L) } catch (interrupted: Exception) {
            // Preserve the original startup/lifecycle behavior in this conversion.
        }
        if (bcT != 0 && bcU != 0 && MIDlet.coi && !isFinishing) {
            com.gameloft.android.wrapper.ae.bfS = 800
            com.gameloft.android.wrapper.ae.bfT = 480
            com.gameloft.android.wrapper.ae.bfU = "GloftGF2F"
            com.gameloft.android.wrapper.ae.bfZ = true
            com.gameloft.android.wrapper.ae(this, 0.01f, arrayOf(""), arrayOf(""), "com.quanhoang.greenfarm3")
            com.gameloft.android.wrapper.ae.a(packageManager, bcT, bcU)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (backendGateOnly) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        if (MIDlet.coi) {
            if (requestCode == 274) {
                setResult(274)
                finishActivity(274)
            }
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        if (backendGateOnly) {
            super.onDestroy()
            return
        }
        if (MIDlet.coi && MIDlet.cos) {
            setResult(274)
            finishActivity(274)
            bx.ND()?.NF()
            System.exit(0)
        }
        super.onDestroy()
    }

    companion object {
        private const val SAVE_TRANSFER_MENU_ID = 0x474653
        @JvmField var bcR: Start? = null
        @JvmField var bcW: Start? = null
    }
}
