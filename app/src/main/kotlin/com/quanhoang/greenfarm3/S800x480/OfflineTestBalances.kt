package com.quanhoang.greenfarm3.S800x480

import javax.microedition.midlet.MIDlet

/** One initial grant in the isolated offline-test application, never the normal save. */
object OfflineTestBalances {
    const val INITIAL_BALANCE = 9_999_999
    const val MAX_PLAYER_LEVEL = 50
    private const val TEST_APPLICATION_ID = "com.quanhoang.greenfarm3.offlinetest"
    private const val GRANT_RECORD = "offline_test_balances_v1"
    // Test-only metadata: GFTB, version1, initial amount, level, all big endian.
    private val GRANT_PAYLOAD = byteArrayOf(0x47, 0x46, 0x54, 0x42, 0, 0, 0, 1,
        0, 0x98.toByte(), 0x96.toByte(), 0x7f, 0, 0, 0, MAX_PLAYER_LEVEL.toByte())
    private var grantNeedsCapture = false

    private fun appliesToOwnTestFarm(): Boolean = OfflineTestConfig.enabled() &&
        MIDlet.cnA?.packageName == TEST_APPLICATION_ID && FarmGameEngine.aZB == 0

    /** Called after a verified world load, before normal gameplay can change it. */
    @JvmStatic
    fun onWorldReady() {
        grantNeedsCapture = false
        if (!appliesToOwnTestFarm()) return

        // Original cash is capped at 3,000,000. Raise only this test process's
        // caps, including after reopen, so normal deductions do not clip cash.
        FarmGameEngine.aBe = maxOf(FarmGameEngine.aBe, INITIAL_BALANCE)
        FarmGameEngine.aBg = maxOf(FarmGameEngine.aBg, INITIAL_BALANCE)
        val previousGrant = GameSaveStore.read(GRANT_RECORD)
        if (previousGrant != null) {
            check(previousGrant.contentEquals(GRANT_PAYLOAD)) {
                "Offline test currency grant metadata is invalid; existing progress retained"
            }
            return
        }

        // Use the game's protected setters: each updates the value AND its
        // shadow check. This is a seed, not a fabricated balance getter.
        FarmGameEngine.aBI = false
        FarmGameEngine.aBH = false
        FarmGameEngine.hJ(INITIAL_BALANCE)
        FarmGameEngine.hK(INITIAL_BALANCE)
        FarmGameEngine.hL(MAX_PLAYER_LEVEL)
        // Exact level-50 capacity branch from hW, without spawning its rewards
        // or advancing quests/tutorial. These are limits, not inventory stock.
        FarmGameEngine.ia(60)
        FarmGameEngine.ib(25)
        FarmGameEngine.ic(40)
        FarmGameEngine.id(20)
        // hX stores XP within the current level and exits at level 50. Preserve
        // that saved value; there is no cumulative-XP threshold to fabricate.
        FarmGameEngine.hasUnsavedGameChanges = true
        grantNeedsCapture = true
    }

    /** Runs inside the same immutable capture as money, level and capacities. */
    @JvmStatic
    fun captureGrantMarker() {
        if (!grantNeedsCapture || !appliesToOwnTestFarm()) return
        // Keep this pending through a rejected/failed write. The existing queue
        // retries absolute snapshots, so a marker can never outrun the balance.
        GameSaveStore.write(GRANT_RECORD, GRANT_PAYLOAD, 0, GRANT_PAYLOAD.size)
    }
}
