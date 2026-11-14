package com.quanhoang.greenfarm3.S800x480

/** Uses engine20/GUI4: original frame, font, dimming, buttons and input dispatch. */
object GameNotification {
    @Volatile private var decision: ((Boolean) -> Unit)? = null
    private var labels = arrayOf("", "")
    private var dismissAllowed = true

    @JvmStatic fun show(title: String, message: String, positive: String, negative: String,
                        canDismiss: Boolean = true, onDecision: (Boolean) -> Unit) {
        check(decision == null) { "A game notification is already open" }
        // A writer completion can request its next prompt in the same frame
        // as the previous button tap. Finish that pending GUI4 pop first.
        if (FarmGameEngine.currentEngineState() == 20 && FarmGameEngine.apA == 1) FarmGameEngine.qG()
        labels = arrayOf(positive, negative)
        dismissAllowed = canDismiss
        decision = onDecision
        try { FarmGameEngine.showGameNotification(title, message) }
        catch (failure: RuntimeException) { decision = null; throw failure }
    }

    @JvmStatic fun isVisible(): Boolean = decision != null
    @JvmStatic fun positiveLabel(): String = labels[0]
    @JvmStatic fun negativeLabel(): String = labels[1]
    @JvmStatic fun canDismiss(): Boolean = dismissAllowed

    /** Same fourteen locale IDs as the shipped text packs. */
    @JvmStatic fun storageFailure(language: Int): Array<String> = when (language) {
        1 -> arrayOf("Speichern fehlgeschlagen", "Der Fortschritt ist noch nicht gespeichert. Änderungen bleiben im Speicher. Speicherplatz prüfen und erneut versuchen.")
        2 -> arrayOf("Échec de sauvegarde", "La progression n’est pas encore sauvegardée. Les changements restent en mémoire. Vérifiez l’espace libre et réessayez.")
        3 -> arrayOf("Salvataggio non riuscito", "I progressi non sono ancora salvati. Le modifiche restano in memoria. Controlla lo spazio libero e riprova.")
        4, 20 -> arrayOf("Error al guardar", "El progreso aún no está guardado. Los cambios siguen en memoria. Comprueba el espacio libre e inténtalo de nuevo.")
        5 -> arrayOf("Falha ao salvar", "O progresso ainda não foi salvo. As alterações estão na memória. Verifique o espaço livre e tente novamente.")
        7 -> arrayOf("保存できません", "進行状況はまだ保存されていません。変更はメモリに保持されています。空き容量を確認して再試行してください。")
        9 -> arrayOf("저장 실패", "진행 상황이 아직 저장되지 않았습니다. 변경 사항은 메모리에 유지됩니다. 저장 공간을 확인하고 다시 시도하세요.")
        10 -> arrayOf("Ошибка сохранения", "Прогресс ещё не сохранён. Изменения остаются в памяти. Проверьте свободное место и повторите попытку.")
        11 -> arrayOf("Kaydedilemedi", "İlerleme henüz kaydedilmedi. Değişiklikler bellekte tutuluyor. Boş alanı kontrol edip tekrar deneyin.")
        12 -> arrayOf("Błąd zapisu", "Postęp nie został jeszcze zapisany. Zmiany pozostają w pamięci. Sprawdź wolne miejsce i spróbuj ponownie.")
        15 -> arrayOf("บันทึกไม่สำเร็จ", "ยังไม่ได้บันทึกความคืบหน้า การเปลี่ยนแปลงยังอยู่ในหน่วยความจำ โปรดตรวจสอบพื้นที่ว่างแล้วลองอีกครั้ง")
        16 -> arrayOf("Không thể lưu tiến độ", "Tiến độ chưa được lưu. Thay đổi vẫn được giữ trong bộ nhớ. Hãy kiểm tra dung lượng trống và thử lại.")
        else -> arrayOf("Could not save progress", "Progress is not saved yet. Changes are retained in memory. Check free storage and retry.")
    }

    @JvmStatic fun choose(positive: Boolean) {
        val callback = decision ?: return
        if (!positive && !dismissAllowed) return
        decision = null // Repeated taps cannot run the decision twice.
        FarmGameEngine.qB()
        callback(positive)
    }
}
