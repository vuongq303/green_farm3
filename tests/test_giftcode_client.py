"""Exercise the Android giftcode client and coordinator against staged I/O peers."""
import os
from pathlib import Path
import struct
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

SOURCE = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
JAVA = {
    'android/os/Looper.java': '''package android.os; public class Looper {
      private static final Looper MAIN = new Looper();
      public static Looper getMainLooper(){return MAIN;} public static Looper myLooper(){return MAIN;}
    }''',
    'android/os/Handler.java': '''package android.os; public class Handler {
      private final Looper looper;
      public Handler(Looper looper){this.looper=looper;} public Looper getLooper(){return looper;}
      public boolean post(Runnable work){work.run();return true;}
    }''',
    'android/util/Log.java': '''package android.util; public class Log {
      public static int w(String tag,String detail){return 0;}
    }''',
}

HARNESS = r'''
package com.quanhoang.greenfarm3.S800x480

import java.util.ArrayDeque
import org.json.JSONArray
import org.json.JSONObject

private const val FARM = "1473a085-ad04-4c73-9df8-beb72de0d950"
private const val GRANT = "c2312009-b195-412a-bb6c-f16c0b1548db"
private const val ACCOUNT = "GF1000001"

class SocialAuthFailure(val code: String) : RuntimeException(code)
data class SocialIdentity(val gameId: String)
object LocalDevelopmentConfig { var ONLINE_ACCOUNTS = true }
object ProgressSaveController { fun isWorldReadyForMenu() = true }
object FarmGameEngine { var aZB = 0 }

object GiftcodeFarmStore {
    var saved = 0
    var fail = false
    fun getFarmId() = FARM
    fun apply(farmId: String, account: String, id: String, coins: Int, cash: Int, callback: (String) -> Unit) {
        check(farmId == FARM && account == ACCOUNT && id == GRANT && coins == 10 && cash == 12)
        callback(when {
            fail -> "save_failed"
            saved > 0 -> "already_applied"
            else -> { saved++; "applied" }
        })
    }
}

object SocialAccountController {
    const val READY = 1
    var active = READY
    var requests = 0
    var pending = false
    var loseReply = false
    var loseAck = false
    private val worker = ArrayDeque<() -> Unit>()
    fun status() = active
    fun gameId() = ACCOUNT
    fun withReadyAccess(operation: (SocialIdentity, String) -> Unit, onFailure: (String) -> Unit) {
        worker.add {
            try { operation(SocialIdentity(ACCOUNT), "bearer") }
            catch (failure: SocialAuthFailure) { onFailure(failure.code) }
        }
    }
    fun flush() { while (worker.isNotEmpty()) worker.removeFirst().invoke() }

    fun giftRequest(method: String, path: String, body: JSONObject?, token: String): JSONObject {
        check(token == "bearer")
        return when (path) {
            "giftcodes/redeem" -> {
                check(method == "POST" && body!!.getString("farm_id") == FARM &&
                    body.getString("code") == "FARM-123")
                requests++
                if (GiftcodeFarmStore.saved > 0 && !pending) grant("applied")
                else {
                    pending = true
                    if (loseReply) throw SocialAuthFailure("network_error")
                    grant("pending")
                }
            }
            "giftcodes/pending?farm_id=$FARM" -> {
                check(method == "GET" && body == null)
                val grants = JSONArray()
                if (pending) grants.put(grant("pending"))
                JSONObject().put("grants", grants)
            }
            "giftcodes/ack" -> {
                check(method == "POST" && body!!.getString("farm_id") == FARM &&
                    body.getString("grant_id") == GRANT && GiftcodeFarmStore.saved > 0)
                if (loseAck) throw SocialAuthFailure("network_error")
                pending = false
                grant("applied")
            }
            else -> error("Unknown endpoint")
        }
    }
}

fun grant(status: String = "pending") = JSONObject().put("grant_id", GRANT).put("status", status)
    .put("rewards", JSONArray().put(JSONObject().put("id", "coins").put("quantity", 10))
        .put(JSONObject().put("id", "cash").put("quantity", 12)))

fun invalid(response: JSONObject) {
    try {
        GiftcodeClient.parseGrant(response)
        error("Malformed grant accepted")
    } catch (failure: SocialAuthFailure) { check(failure.code == "invalid_response") }
}

fun main() {
    val whitespace = listOf(9, 13, 10, 11, 12).map(Int::toChar).joinToString("")
    check(GiftcodeClient.normalize(" fArM-123 " + whitespace) == "FARM-123")
    check(GiftcodeClient.normalize("FA RM") == null)
    check(GiftcodeClient.normalize("ſARM") == null)
    invalid(grant("accepted"))
    invalid(grant().put("grant_id", FARM.uppercase()))
    for (quantity in listOf(0, -1, 100_000_000, 10.0, "10")) {
        val malformed = grant()
        malformed.getJSONArray("rewards").getJSONObject(0).put("quantity", quantity)
        invalid(malformed)
    }
    invalid(grant().apply { getJSONArray("rewards").getJSONObject(1).put("id", "coins") })

    var state = GiftcodeController.ViewState()
    GiftcodeController.attach { state = it }
    check(state.busy && state.message == "recovering")
    SocialAccountController.flush()
    check(state.message == "enter_code")

    SocialAccountController.loseReply = true
    GiftcodeController.redeem(" Farm-123 ")
    GiftcodeController.redeem("Farm-123")
    SocialAccountController.flush()
    check(!state.busy && state.message == "network_error" && SocialAccountController.requests == 1)
    check(SocialAccountController.pending && GiftcodeFarmStore.saved == 0)

    GiftcodeFarmStore.fail = true
    GiftcodeController.detach()
    GiftcodeController.attach { state = it }
    SocialAccountController.flush()
    check(state.message == "save_failed" && SocialAccountController.pending && GiftcodeFarmStore.saved == 0)

    GiftcodeFarmStore.fail = false
    SocialAccountController.loseAck = true
    GiftcodeController.recover()
    SocialAccountController.flush()
    check(state.message == "grant_ack_pending" && state.coins == 10L && state.cash == 12L)
    check(SocialAccountController.pending && GiftcodeFarmStore.saved == 1)

    SocialAccountController.loseAck = false
    GiftcodeController.recover()
    SocialAccountController.flush()
    check(state.message == "grant_acknowledged")
    check(!SocialAccountController.pending && GiftcodeFarmStore.saved == 1)
    GiftcodeController.redeem("Farm-123")
    SocialAccountController.flush()
    check(state.message == "giftcode_already_redeemed" && GiftcodeFarmStore.saved == 1)

    FarmGameEngine.aZB = 1
    GiftcodeController.redeem("Farm-123")
    SocialAccountController.flush()
    check(state.message == "farm_not_ready" && SocialAccountController.requests == 2)

    SocialAccountController.active = 0
    GiftcodeController.redeem("Farm-123")
    check(state.message == "sign_in_required" && SocialAccountController.requests == 2)
}
'''


class GiftcodeClientTest(unittest.TestCase):
    def test_controller_recovers_redeem_save_and_ack_without_duplicate_application(self):
        cache = Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle'))
        runtime = sorted((cache / 'caches/modules-2/files-2.1/org.robolectric/android-all/9-robolectric-4913185-2')
                         .glob('*/*.jar'))
        if not runtime:
            self.skipTest('Requires Android JSON runtime from tests/sqlite-host dependencies')
        with tempfile.TemporaryDirectory() as temporary:
            directory = Path(temporary)
            java_files = []
            for name, code in JAVA.items():
                file = directory / name
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(code)
                java_files.append(file)
            harness = directory / 'GiftcodeChecks.kt'
            harness.write_text(HARNESS)
            classes = directory / 'classes'
            compile_mixed([SOURCE / 'GiftcodeController.kt', SOURCE / 'GiftcodeClient.kt', harness],
                          java_files, classes, classpath=[runtime[0]])
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(classes, runtime[0]),
                                     'com.quanhoang.greenfarm3.S800x480.GiftcodeChecksKt'],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_settings_widget_slots_follow_shipped_gui_pack(self):
        raw = (ROOT / 'app/src/main/assets/800x480/10').read_bytes()
        entries, packs, first_entry = struct.unpack_from('<3H', raw)
        self.assertEqual((packs, first_entry), (1, 0))
        offsets = struct.unpack_from(f'<{entries + 1}I', raw, 6)
        gui = raw[offsets[15] + 1:offsets[16]]
        lengths = {201: 9, 202: 10, 203: 14, 204: 19, 205: 24, 206: 12, 207: 13, 208: 1, 209: 7}
        nodes = []
        offset = 0
        while offset < len(gui):
            node = struct.unpack_from('<BhhhB', gui, offset)
            nodes.append(node)
            offset += 8 + lengths[node[0]]
        self.assertEqual(offset, len(gui))
        self.assertEqual(len(nodes), 164)
        self.assertEqual((nodes[11][0], nodes[12][0]), (205, 204))
        self.assertEqual((nodes[7][2], nodes[9][2]), (406, 193))
        self.assertEqual((nodes[11][3], nodes[12][3]), (287, 287))
