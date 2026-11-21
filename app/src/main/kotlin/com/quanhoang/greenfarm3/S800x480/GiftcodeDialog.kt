package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ReplacementSpan
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.quanhoang.greenfarm3.R
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

/** GUI15 supplies the native Settings entry. The modal and its keypad stay inside the game window. */
object GiftcodeDialog {
    private var current: FrameLayout? = null

    @JvmStatic fun title(language: Int): String = labels(language).let {
        it.title.uppercase(Locale(it.locale))
    }

    @JvmStatic fun open(language: Int) {
        val activity = SocialAccountController.hostActivity() ?: return
        activity.runOnUiThread {
            if (activity.isFinishing || current != null) return@runOnUiThread
            show(activity, language)
        }
    }

    private fun show(activity: Activity, language: Int) {
        val words = labels(language)
        val locale = Locale(words.locale)
        val content = activity.findViewById<FrameLayout>(android.R.id.content) ?: return
        val oldFocus = activity.currentFocus
        val overlay = object : FrameLayout(activity) {
            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.action == KeyEvent.ACTION_UP && !event.isCanceled) content.removeView(this)
                    return true
                }
                return super.dispatchKeyEvent(event) || event.keyCode == KeyEvent.KEYCODE_MENU
            }
        }
        // A platform Dialog steals game window focus and pauses the frame
        // boundary used to commit gifts. This modal remains in that window.
        overlay.isClickable = true
        overlay.setBackgroundColor(color("#94000000"))

        val panel = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            isFocusableInTouchMode = true
            setPadding(dp(16), dp(10), dp(16), dp(10))
            background = surface("#FFCD81", "#4B2C54", 4, 8)
        }
        val heading = TextView(activity).apply {
            val caption = title(language)
            text = SpannableString(caption).apply {
                setSpan(HeadingOutline(caption, dp(3).toFloat()), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            textSize = 24f
            typeface = titleFont
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setPadding(dp(8), dp(6), dp(8), dp(6))
            if (android.os.Build.VERSION.SDK_INT >= 28) isAccessibilityHeading = true
        }
        panel.addView(heading, row(activity, 0))

        val editor = EditText(activity).apply {
            hint = words.hint
            setSingleLine(true)
            setTextSize(19f)
            setTextColor(color("#482E4F"))
            setHintTextColor(color("#77617C"))
            background = surface("#FFFDF1", "#B78437", 2, 8)
            setPadding(dp(12), dp(8), dp(12), dp(8))
            minHeight = dp(48)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS or
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            // Preserve hardware input and the EditText clipboard menu while
            // keeping the system IME out of the fullscreen landscape window.
            if (android.os.Build.VERSION.SDK_INT >= 21) showSoftInputOnFocus = false
            else setRawInputType(InputType.TYPE_NULL)
            filters = arrayOf(InputFilter.LengthFilter(128))
        }
        panel.addView(editor, row(activity, 10))

        val keypad = keypad(activity, editor, language, content.height).apply { visibility = View.GONE }
        editor.setOnFocusChangeListener { _, focused ->
            if (focused && editor.isEnabled) keypad.visibility = View.VISIBLE
        }
        editor.setOnClickListener { if (editor.isEnabled) keypad.visibility = View.VISIBLE }

        // Keep the text field and actions above the keypad and outside the
        // result scroller so the claim action stays reachable while typing.
        val actions = LinearLayout(activity).apply { orientation = LinearLayout.HORIZONTAL }
        val close = button(activity, words.close.uppercase(locale), false) { content.removeView(overlay) }
        val submit = button(activity, words.claim.uppercase(locale), true) {
            keypad.visibility = View.GONE
            panel.requestFocus()
            GiftcodeController.redeem(editor.text.toString())
        }
        actions.addView(close, LinearLayout.LayoutParams(0, panel.dp(48), 1f))
        actions.addView(submit, LinearLayout.LayoutParams(0, panel.dp(48), 1f).apply {
            leftMargin = panel.dp(8)
        })
        panel.addView(actions, row(activity, 8))
        panel.addView(keypad, row(activity, 8))

        val results = LinearLayout(activity).apply { orientation = LinearLayout.VERTICAL }
        val message = TextView(activity).apply {
            textSize = 15f
            setTextColor(color("#482E4F"))
            setPadding(dp(2), dp(1), dp(2), dp(2))
            if (android.os.Build.VERSION.SDK_INT >= 19) accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
        }
        results.addView(message, row(activity, 8))

        val coins = RewardRow(activity, R.drawable.giftcode_coins)
        val cash = RewardRow(activity, R.drawable.giftcode_cash)
        results.addView(coins.container, row(activity, 4))
        results.addView(cash.container, row(activity, 4))
        val amounts = NumberFormat.getIntegerInstance(locale)

        val retry = button(activity, words.retry.uppercase(locale), false) { GiftcodeController.recover() }.apply {
            visibility = View.GONE
        }
        results.addView(retry, row(activity, 6))
        editor.setOnEditorActionListener { _, action, event ->
            val enter = event?.keyCode == KeyEvent.KEYCODE_ENTER
            if (submit.isEnabled && (action == EditorInfo.IME_ACTION_DONE || enter)) {
                if (event == null || event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
                    submit.performClick()
                }
                true
            } else false
        }
        val scroll = ScrollView(activity).apply {
            isFillViewport = false
            addView(results)
        }
        panel.addView(scroll, LinearLayout.LayoutParams(-1, -2, 1f))
        val inset = panel.dp(12)
        val windowWidth = minOf(panel.dp(490), activity.resources.displayMetrics.widthPixels - 2 * inset)
            .coerceAtLeast(1)
        val panelParams = FrameLayout.LayoutParams(windowWidth, -2, Gravity.CENTER).apply {
            setMargins(inset, inset, inset, inset)
        }
        overlay.addView(panel, panelParams)
        overlay.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) = Unit
            override fun onViewDetachedFromWindow(view: View) {
                if (current !== overlay) return
                current = null
                GiftcodeController.detach()
                if (oldFocus?.windowToken != null) oldFocus.requestFocus()
            }
        })
        current = overlay
        content.addView(overlay, FrameLayout.LayoutParams(-1, -1))
        panel.requestFocus()
        GiftcodeController.attach { state ->
            val canRedeem = state.message !in unavailable
            editor.isEnabled = !state.busy && canRedeem
            submit.isEnabled = !state.busy && canRedeem
            message.text = statusText(state.message, language == 16)
            message.visibility = if (message.text.isEmpty()) View.GONE else View.VISIBLE
            if (!editor.isEnabled) keypad.visibility = View.GONE
            coins.render(words.coins, state.coins, amounts)
            cash.render(words.cash, state.cash, amounts)
            retry.visibility = if (!state.busy && state.message in recoverable) View.VISIBLE else View.GONE
        }
    }

    private fun keypad(activity: Activity, editor: EditText, language: Int, contentHeight: Int): LinearLayout {
        val display = activity.resources.displayMetrics
        val availableHeight = if (contentHeight > 0) contentHeight else display.heightPixels
        val keyHeight = (availableHeight * 0.073f).roundToInt().coerceIn(
            (20f * display.density).roundToInt(), (36f * display.density).roundToInt())
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            if (android.os.Build.VERSION.SDK_INT >= 17) layoutDirection = View.LAYOUT_DIRECTION_LTR
            for (letters in listOf("1234567890", "QWERTYUIOP", "ASDFGHJKL-", "ZXCVBNM⌫")) {
                val keyRow = LinearLayout(activity)
                for (letter in letters) {
                    val key = button(activity, letter.toString(), false) { editCode(editor, letter) }.apply {
                        minWidth = 0
                        minimumWidth = 0
                        setPadding(0, 0, 0, 0)
                        if (letter == '⌫') contentDescription = if (language == 16) "Xóa ký tự" else "Backspace"
                    }
                    keyRow.addView(key, LinearLayout.LayoutParams(0, keyHeight, 1f).apply {
                        if (keyRow.childCount > 0) leftMargin = key.dp(3)
                    })
                }
                addView(keyRow, row(activity, if (childCount == 0) 0 else 2))
            }
        }
    }

    private fun editCode(editor: EditText, letter: Char) {
        if (!editor.isEnabled) return
        val text = editor.text
        val selectionStart = editor.selectionStart.coerceIn(0, text.length)
        val selectionEnd = editor.selectionEnd.coerceIn(0, text.length)
        val start = minOf(selectionStart, selectionEnd)
        val end = maxOf(selectionStart, selectionEnd)
        if (letter == '⌫') {
            val from = if (start == end && start > 0) Character.offsetByCodePoints(text, start, -1) else start
            text.delete(from, end)
            editor.setSelection(from)
        } else {
            text.replace(start, end, letter.toString())
            editor.setSelection(minOf(start + 1, text.length))
        }
    }

    private class RewardRow(activity: Activity, icon: Int) {
        private val text = TextView(activity).apply {
            textSize = 16f
            typeface = titleFont
            setTextColor(color("#482E4F"))
        }
        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = surface("#FFFDF1", "#B78437", 1, 7)
            setPadding(dp(8), dp(4), dp(8), dp(4))
            visibility = View.GONE
            addView(ImageView(activity).apply {
                setImageResource(icon)
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
            }, LinearLayout.LayoutParams(dp(32), dp(32)))
            addView(text, LinearLayout.LayoutParams(-2, -2).apply { leftMargin = dp(8) })
        }

        fun render(name: String, amount: Long, format: NumberFormat) {
            container.visibility = if (amount > 0) View.VISIBLE else View.GONE
            if (amount > 0) text.text = "$name  +${format.format(amount)}"
        }
    }

    private class HeadingOutline(private val caption: String, private val strokeWidth: Float) : ReplacementSpan() {
        private val outline = Paint(Paint.ANTI_ALIAS_FLAG)

        override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int,
                             fm: Paint.FontMetricsInt?): Int {
            if (fm != null) paint.getFontMetricsInt(fm)
            return paint.measureText(caption).roundToInt()
        }

        override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float,
                          top: Int, y: Int, bottom: Int, paint: Paint) {
            outline.set(paint)
            outline.style = Paint.Style.STROKE
            outline.strokeWidth = strokeWidth
            outline.strokeJoin = Paint.Join.ROUND
            outline.color = color("#FF6212")
            outline.setShadowLayer(1f, 0f, strokeWidth, color("#B77932"))
            canvas.drawText(caption, x, y.toFloat(), outline)
            canvas.drawText(caption, x, y.toFloat(), paint)
        }
    }

    private val titleFont = Typeface.create("sans-serif-black", Typeface.BOLD)
    private val unavailable = setOf("giftcode_offline", "sign_in_required", "invalid_session", "session_expired",
        "invalid_refresh_token", "configuration_required", "auth_not_configured")
    private val recoverable = setOf("save_failed", "farm_not_ready", "balance_limit", "grant_ack_pending",
        "network_error", "timeout", "database_unavailable", "tls_error", "rate_limited", "invalid_response")

    private fun button(activity: Activity, label: String, primary: Boolean, click: () -> Unit): Button =
        Button(activity).apply {
            text = label
            isAllCaps = false
            textSize = 16f
            typeface = titleFont
            setTextColor(color("#482E4F"))
            background = StateListDrawable().apply {
                addState(intArrayOf(-android.R.attr.state_enabled), bevel("#E4D7BC", "#A98B63", 2))
                addState(intArrayOf(android.R.attr.state_pressed), bevel("#FFE4A5", "#9A661F", 1))
                addState(intArrayOf(), bevel(if (primary) "#FFF3CB" else "#FFFDF1", "#BF8A35", 4))
            }
            setOnClickListener { click() }
        }

    private fun row(activity: Activity, top: Int) = LinearLayout.LayoutParams(-1, -2).apply {
        topMargin = (top * activity.resources.displayMetrics.density).roundToInt()
    }

    private fun View.dp(value: Int): Int = (value * resources.displayMetrics.density).roundToInt()
    private fun color(value: String) = Color.parseColor(value)
    private fun View.surface(fill: String, stroke: String, border: Int, corner: Int): GradientDrawable =
        GradientDrawable().apply {
            setColor(color(fill))
            setStroke(dp(border), color(stroke))
            cornerRadius = dp(corner).toFloat()
        }

    private fun View.bevel(fill: String, stroke: String, edge: Int): LayerDrawable =
        LayerDrawable(arrayOf(surface("#843A11", "#843A11", 1, 6), surface(fill, stroke, 2, 6))).apply {
            setLayerInset(1, 0, 0, 0, dp(edge))
        }

    private data class Labels(val locale: String, val title: String, val hint: String,
        val claim: String, val close: String, val retry: String, val coins: String, val cash: String)

    private fun labels(language: Int): Labels = when (language) {
        1 -> Labels("de", "Geschenkcode", "Code eingeben", "Einlösen", "Schließen", "Geschenke abrufen", "Münzen", "Bargeld")
        2 -> Labels("fr", "Code cadeau", "Saisir le code", "Échanger", "Fermer", "Récupérer", "Pièces", "Billets")
        3 -> Labels("it", "Codice regalo", "Inserisci il codice", "Riscatta", "Chiudi", "Recupera", "Monete", "Contanti")
        4, 20 -> Labels("es", "Código de regalo", "Introduce el código", "Canjear", "Cerrar", "Recuperar", "Monedas", "Billetes")
        5 -> Labels("pt", "Código de presente", "Digite o código", "Resgatar", "Fechar", "Recuperar", "Moedas", "Dinheiro")
        7 -> Labels("ja", "ギフトコード", "コードを入力", "受け取る", "閉じる", "報酬を復元", "コイン", "キャッシュ")
        9 -> Labels("ko", "선물 코드", "코드 입력", "받기", "닫기", "보상 복구", "코인", "캐시")
        10 -> Labels("ru", "Подарочный код", "Введите код", "Получить", "Закрыть", "Восстановить", "Монеты", "Купюры")
        11 -> Labels("tr", "Hediye kodu", "Kodu gir", "Kullan", "Kapat", "Hediyeleri al", "Altın", "Nakit")
        12 -> Labels("pl", "Kod podarunkowy", "Wpisz kod", "Odbierz", "Zamknij", "Odzyskaj", "Monety", "Gotówka")
        15 -> Labels("th", "รหัสของขวัญ", "ป้อนรหัส", "รับรางวัล", "ปิด", "กู้คืนรางวัล", "เหรียญ", "เงินสด")
        16 -> Labels("vi", "Nhập giftcode", "Nhập mã quà tặng", "Nhận quà", "Đóng", "Khôi phục quà", "Xu", "Tiền mặt")
        else -> Labels("en", "Gift code", "Enter gift code", "Redeem", "Close", "Recover rewards", "Coins", "Cash")
    }

    private fun statusText(status: String, vi: Boolean): String = when (status) {
        "enter_code" -> ""
        "redeeming" -> if (vi) "Đang đổi mã và lưu phần thưởng…" else "Redeeming and saving your reward…"
        "recovering" -> if (vi) "Đang kiểm tra quà chưa nhận…" else "Checking for pending rewards…"
        "granted" -> if (vi) "Đã nhận và lưu phần thưởng vào nông trại." else "Your reward was saved to this farm."
        "grant_acknowledged" -> if (vi) "Máy chủ đã xác nhận phần thưởng đã lưu." else "The server confirmed your saved reward."
        "grant_ack_pending" -> if (vi) "Đã lưu quà vào nông trại. Hãy thử lại để xác nhận với máy chủ." else "Your reward is saved. Retry to confirm with the server."
        "giftcode_offline" -> if (vi) "Tài khoản trực tuyến đang tắt trong bản này." else "Online accounts are disabled in this build."
        "sign_in_required", "invalid_session", "session_expired", "invalid_refresh_token" ->
            if (vi) "Vui lòng đăng nhập lại trong game để nhận quà." else "Sign in to the game again to redeem a code."
        "invalid_giftcode" -> if (vi) "Mã cần 4–48 ký tự gồm chữ cái, số hoặc dấu gạch ngang." else "Use 4–48 letters, digits or hyphens."
        "giftcode_not_found" -> if (vi) "Không tìm thấy mã quà tặng này." else "This gift code was not found."
        "giftcode_not_started" -> if (vi) "Mã quà tặng chưa có hiệu lực." else "This gift code is not active yet."
        "giftcode_expired" -> if (vi) "Mã quà tặng đã hết hạn." else "This gift code has expired."
        "giftcode_disabled" -> if (vi) "Mã quà tặng đã bị tắt." else "This gift code is disabled."
        "giftcode_exhausted" -> if (vi) "Mã quà tặng đã hết lượt." else "All uses of this gift code have been claimed."
        "giftcode_already_redeemed" -> if (vi) "Tài khoản này đã nhận mã quà tặng." else "This account has already redeemed this code."
        "farm_not_ready" -> if (vi) "Hãy mở nông trại trước khi nhận quà." else "Open your farm before claiming a reward."
        "balance_limit" -> if (vi) "Số dư sẽ vượt giới hạn. Hãy giảm số dư rồi khôi phục quà." else "The reward would exceed your balance limit. Spend some currency and recover it."
        "save_failed", "storage_error" -> if (vi) "Chưa lưu được quà. Hãy kiểm tra bộ nhớ và thử khôi phục." else "Saving failed. Check storage and recover the reward."
        "account_changed" -> if (vi) "Tài khoản đã thay đổi. Hãy thử lại với đúng tài khoản." else "The signed-in account changed. Retry with the original account."
        "timeout" -> if (vi) "Máy chủ phản hồi quá chậm. Hãy thử khôi phục quà." else "The request timed out. Try recovering rewards."
        "tls_error", "https_required" -> if (vi) "Không thể xác minh kết nối HTTPS." else "The HTTPS connection could not be verified."
        "network_error" -> if (vi) "Không kết nối được máy chủ. Hãy thử khôi phục quà." else "Could not contact the server. Try recovering rewards."
        "database_unavailable" -> if (vi) "Máy chủ tạm thời không sẵn sàng." else "The server is temporarily unavailable."
        "configuration_required", "auth_not_configured" -> if (vi) "Máy chủ quà tặng chưa được cấu hình." else "The gift server is not configured."
        "rate_limited" -> if (vi) "Bạn thử quá nhiều lần. Vui lòng đợi rồi thử lại." else "Too many requests. Wait and try again."
        else -> if (vi) "Máy chủ trả về dữ liệu quà tặng không hợp lệ." else "The gift server returned invalid data."
    }
}
