package com.quanhoang.greenfarm3.S800x480

import java.util.Locale
import org.json.JSONObject

internal data class GiftcodeGrant(
    val id: String, val coins: Int, val cash: Int, val status: String
)

internal object GiftcodeClient {
    private val codeFormat = Regex("[A-Za-z0-9-]{4,48}")
    private val uuid = Regex("[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
    private const val CODE_WHITESPACE = " \t\r\n\u000b\u000c"
    const val MAX_PENDING = 20
    const val MAX_COINS = 99_999_999
    const val MAX_CASH = 3_000_000

    fun normalize(input: String): String? {
        val code = input.filterNot { it in CODE_WHITESPACE }
        return code.takeIf(codeFormat::matches)?.uppercase(Locale.ROOT)
    }

    fun redeem(token: String, farmId: String, code: String): GiftcodeGrant = parseGrant(
        SocialAccountController.giftRequest("POST", "giftcodes/redeem",
            JSONObject().put("farm_id", farmId).put("code", code), token)
    )

    fun pending(token: String, farmId: String): List<GiftcodeGrant> {
        if (!uuid.matches(farmId)) throw SocialAuthFailure("invalid_farm_id")
        val response = SocialAccountController.giftRequest("GET",
            "giftcodes/pending?farm_id=$farmId", null, token)
        return parsePending(response)
    }

    fun ack(token: String, farmId: String, grant: GiftcodeGrant) {
        val response = SocialAccountController.giftRequest("POST", "giftcodes/ack",
            JSONObject().put("farm_id", farmId).put("grant_id", grant.id), token)
        if (parseGrant(response) != grant.copy(status = "applied")) invalidResponse()
    }

    fun parsePending(response: JSONObject): List<GiftcodeGrant> {
        val grants = response.optJSONArray("grants") ?: invalidResponse()
        if (grants.length() > MAX_PENDING) invalidResponse()
        val seen = HashSet<String>()
        return List(grants.length()) { index ->
            val grant = parseGrant(grants.optJSONObject(index) ?: invalidResponse())
            if (grant.status != "pending" || !seen.add(grant.id)) invalidResponse()
            grant
        }
    }

    fun parseGrant(response: JSONObject): GiftcodeGrant {
        val id = response.opt("grant_id") as? String ?: invalidResponse()
        val status = response.opt("status") as? String ?: invalidResponse()
        val rewards = response.optJSONArray("rewards") ?: invalidResponse()
        if (!uuid.matches(id) || (status != "pending" && status != "applied") ||
            rewards.length() !in 1..2) invalidResponse()
        var coins = 0
        var cash = 0
        for (index in 0 until rewards.length()) {
            val reward = rewards.optJSONObject(index) ?: invalidResponse()
            val quantity = when (val value = reward.opt("quantity")) {
                is Int -> value.toLong()
                is Long -> value
                else -> invalidResponse()
            }
            when (reward.opt("id")) {
                "coins" -> {
                    if (coins != 0 || quantity !in 1L..MAX_COINS.toLong()) invalidResponse()
                    coins = quantity.toInt()
                }
                "cash" -> {
                    if (cash != 0 || quantity !in 1L..MAX_CASH.toLong()) invalidResponse()
                    cash = quantity.toInt()
                }
                else -> invalidResponse()
            }
        }
        return GiftcodeGrant(id, coins, cash, status)
    }

    private fun invalidResponse(): Nothing = throw SocialAuthFailure("invalid_response")
}
