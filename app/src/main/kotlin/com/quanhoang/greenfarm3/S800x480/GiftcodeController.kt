package com.quanhoang.greenfarm3.S800x480

import android.os.Handler
import android.os.Looper
import android.util.Log

/** Android UI owns the operation presentation. The auth and SQLite workers own I/O. */
internal object GiftcodeController {
    data class ViewState(
        val busy: Boolean = false,
        val message: String = "enter_code",
        val coins: Long = 0,
        val cash: Long = 0,
    )
    private data class Target(val farmId: String, val account: String)
    private class Totals(var coins: Long = 0, var cash: Long = 0, var synced: Boolean = false) {
        fun add(grant: GiftcodeGrant) {
            coins += grant.coins
            cash += grant.cash
        }
        fun any() = coins != 0L || cash != 0L
    }

    private val main = Handler(Looper.getMainLooper())
    private var state = ViewState()
    private var observer: ((ViewState) -> Unit)? = null

    fun attach(observer: (ViewState) -> Unit) {
        this.observer = observer
        if (state.busy) observer(state) else recover()
    }

    fun detach() {
        observer = null
    }

    fun redeem(input: String) {
        if (state.busy) return
        val code = GiftcodeClient.normalize(input)
        if (code == null) {
            finish("invalid_giftcode")
            return
        }
        start("redeeming") { target, token ->
            val grant = GiftcodeClient.redeem(token, target.farmId, code)
            if (grant.status == "applied") finish("giftcode_already_redeemed")
            else {
                val totals = Totals()
                apply(target, grant, totals) { localResult, acked ->
                    finish(when {
                        localResult == "already_applied" && !acked -> "grant_ack_pending"
                        localResult == "already_applied" -> "giftcode_already_redeemed"
                        localResult != "applied" -> localResult
                        acked -> "granted"
                        else -> "grant_ack_pending"
                    }, totals)
                }
            }
        }
    }

    fun recover() {
        if (state.busy) return
        start("recovering") { target, token ->
            recoverPage(target, token, Totals())
        }
    }

    private fun recoverPage(target: Target, token: String, totals: Totals) {
        val grants = GiftcodeClient.pending(token, target.farmId)
        recoverNext(target, grants, 0, totals, null)
    }

    private fun recoverNext(
        target: Target, grants: List<GiftcodeGrant>, index: Int, totals: Totals, blocked: String?
    ) {
        if (index == grants.size) {
            if (blocked == null && grants.size == GiftcodeClient.MAX_PENDING) {
                access(target.account, { _, token -> recoverPage(target, token, totals) },
                    { finish(it, totals) })
            } else {
                finish(blocked ?: when {
                    totals.any() -> "granted"
                    totals.synced -> "grant_acknowledged"
                    else -> "enter_code"
                }, totals)
            }
            return
        }
        apply(target, grants[index], totals) { localResult, acked ->
            val nextBlocked = blocked ?: when {
                localResult != "applied" && localResult != "already_applied" -> localResult
                !acked -> "grant_ack_pending"
                else -> null
            }
            if (localResult != "applied" && localResult != "already_applied" && localResult != "balance_limit") {
                finish(localResult, totals)
            } else {
                recoverNext(target, grants, index + 1, totals, nextBlocked)
            }
        }
    }

    private fun apply(
        target: Target, grant: GiftcodeGrant, totals: Totals,
        next: (localResult: String, acked: Boolean) -> Unit
    ) {
        GiftcodeFarmStore.apply(target.farmId, target.account, grant.id, grant.coins, grant.cash) { result ->
            if (result == "applied") totals.add(grant)
            if (result != "applied" && result != "already_applied") next(result, false)
            else access(target.account, { _, token ->
                GiftcodeClient.ack(token, target.farmId, grant)
                totals.synced = true
                next(result, true)
            }, {
                Log.w("GreenFarmGiftcode", "stage=ack result=pending")
                next(result, false)
            })
        }
    }

    private fun start(message: String, operation: (Target, String) -> Unit) {
        if (!LocalDevelopmentConfig.ONLINE_ACCOUNTS) {
            finish("giftcode_offline")
            return
        }
        if (SocialAccountController.status() != SocialAccountController.READY) {
            finish("sign_in_required")
            return
        }
        update(ViewState(busy = true, message = message))
        access(SocialAccountController.gameId(), { account, token ->
            val ownFarm = synchronized(FarmGameEngine::class.java) {
                ProgressSaveController.isWorldReadyForMenu() && FarmGameEngine.aZB == 0
            }
            if (!ownFarm) throw SocialAuthFailure("farm_not_ready")
            val farm = try { GiftcodeFarmStore.getFarmId() }
            catch (_: RuntimeException) { throw SocialAuthFailure("save_failed") }
            operation(Target(farm, account), token)
        }, { finish(it) })
    }

    private fun access(account: String, operation: (String, String) -> Unit, onFailure: (String) -> Unit) {
        SocialAccountController.withReadyAccess({ identity, token ->
            if (identity.gameId != account) throw SocialAuthFailure("account_changed")
            operation(identity.gameId, token)
        }, onFailure)
    }

    private fun finish(message: String, totals: Totals = Totals()) {
        val finished = ViewState(message = message, coins = totals.coins, cash = totals.cash)
        if (Looper.myLooper() == main.looper) update(finished)
        else main.post { update(finished) }
    }

    private fun update(value: ViewState) {
        state = value
        observer?.invoke(value)
    }
}
