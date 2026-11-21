package com.quanhoang.greenfarm3.S800x480

import java.util.concurrent.atomic.AtomicReference

/** Projects a server grant into the local farm on the same frame boundary as autosave. */
object GiftcodeFarmStore {
    fun interface Completion { fun completed(result: String) }

    private val uuid = Regex("[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
    private val queued = AtomicReference<Request>()
    private var active: Request? = null

    private class Request(
        val farmId: String, val accountId: String, val grantId: String,
        val coins: Int, val cash: Int, var completion: Completion?
    ) {
        val receipt = "$accountId\n$coins\n$cash".toByteArray(Charsets.UTF_8)
        fun notify(result: String) {
            val callback = completion
            completion = null
            callback?.completed(result)
        }
    }

    @JvmStatic fun getFarmId(): String = GameSaveStore.giftcodeFarmId()

    @JvmStatic fun apply(farmId: String, accountId: String, grantId: String, coins: Int, cash: Int, completion: Completion) {
        if (!uuid.matches(grantId) || accountId.isEmpty() || accountId.length > 80 ||
            coins !in 0..99_999_999 || cash !in 0..3_000_000 || coins + cash == 0) {
            completion.completed("invalid_grant")
            return
        }
        if (!queued.compareAndSet(null, Request(farmId, accountId, grantId, coins, cash, completion))) {
            completion.completed("farm_not_ready")
        }
    }

    @JvmStatic fun frameFinished() {
        val request = queued.getAndSet(null) ?: return
        if (SocialAccountController.status() != SocialAccountController.READY ||
            SocialAccountController.gameId() != request.accountId) {
            request.notify("account_changed")
            return
        }
        if (!GameSaveStore.isGiftcodeFarm(request.farmId)) {
            request.notify("farm_not_ready")
            return
        }
        val receipt = GameSaveStore.giftcodeReceipt(request.grantId)
        if (receipt != null) {
            request.notify(if (receipt.contentEquals(request.receipt)) "already_applied" else "invalid_grant")
            return
        }
        val pending = active
        if (pending != null) {
            if (pending.grantId == request.grantId && pending.receipt.contentEquals(request.receipt) &&
                pending.completion == null) {
                pending.completion = request.completion
                ProgressSaveController.retryAtomicUpdate()
            } else request.notify("farm_not_ready")
            return
        }
        if (!ProgressSaveController.canCaptureAtomicUpdate()) {
            request.notify("farm_not_ready")
            return
        }

        val oldCoins = FarmGameEngine.FA()
        val oldCash = FarmGameEngine.FB()
        val newCoins = oldCoins.toLong() + request.coins
        val newCash = oldCash.toLong() + request.cash
        if (newCoins > FarmGameEngine.aBe || newCash > FarmGameEngine.aBg) {
            request.notify("balance_limit")
            return
        }
        val coinValue = FarmGameEngine.aBc
        val coinCheck = FarmGameEngine.aCP
        val cashValue = FarmGameEngine.aBd
        val cashCheck = FarmGameEngine.aCQ
        val coinDebt = FarmGameEngine.aBI
        val cashDebt = FarmGameEngine.aBH
        val accepted = ProgressSaveController.captureAtomicUpdate({
            // Gifts change wallet balances, without counting as farm-earned quest progress.
            setBalances(newCoins.toInt(), newCash.toInt())
            GameSaveStore.captureGiftcodeReceipt(request.grantId, request.receipt)
        }) { durable, finished ->
            if (finished) active = null
            request.notify(if (durable) "applied" else "save_failed")
        }
        if (accepted) {
            active = request
        } else {
            FarmGameEngine.aBc = coinValue
            FarmGameEngine.aCP = coinCheck
            FarmGameEngine.aBd = cashValue
            FarmGameEngine.aCQ = cashCheck
            FarmGameEngine.aBI = coinDebt
            FarmGameEngine.aBH = cashDebt
            request.notify("save_failed")
        }
    }

    private fun setBalances(coins: Int, cash: Int) {
        FarmGameEngine.aBI = coins < 0
        FarmGameEngine.aBH = cash < 0
        FarmGameEngine.hJ(kotlin.math.abs(coins))
        FarmGameEngine.hK(kotlin.math.abs(cash))
    }
}
