package com.quanhoang.greenfarm3.S800x480

import java.security.MessageDigest
import org.json.JSONObject

/** Applies the authenticated server's pinned initialization only to a new farm. */
object NewPlayerDefaults {
    private const val MARKER = "new_player_defaults_v1"
    private val LANGUAGES = mapOf("en" to 0, "de" to 1, "fr" to 2, "it" to 3,
        "es" to 4, "pt-br" to 5, "ja" to 7, "ko" to 9, "ru" to 10, "tr" to 11,
        "pl" to 12, "th" to 15, "vi" to 16, "es-419" to 20)
    // Original table5/aBv per-level XP limits, not a second set of player defaults.
    // Verified source/runtime evidence: original-balance-tables.json.
    private val EXPERIENCE_LIMITS = intArrayOf(0, 13, 19, 30, 50, 80, 120, 180, 270, 400, 600, 900, 1300, 1800, 2500, 3500, 4800, 6400, 8400, 11000, 14000, 19000, 24500, 30000, 36000, 42000, 48000, 54500, 61000, 68000, 75000, 82000, 89000, 96500, 104500, 112500, 121500, 130500, 140000, 150000, 160000, 170000, 180000, 190000, 200000, 210000, 220000, 230000, 240000, 250000)
    private var pendingHash: String? = null

    private data class Config(val hash: String, val level: Int, val experience: Int,
        val coins: Int, val cash: Int, val language: String, val plots: Int,
        val trees: Int, val animals: Int, val ponds: Int)

    @JvmStatic
    fun validateInitialization(initialization: JSONObject) { parse(initialization) }

    /** Resolves explicit language before text assets load; auto keeps original locale behavior. */
    @JvmStatic
    fun startupLanguageOverride(): Int? {
        val supplied = BackendCloudController.newPlayerInitialization() ?: return null
        val config = parse(supplied)
        val marker = GameSaveStore.read(MARKER)
        if (marker != null) {
            require(marker.contentEquals(config.hash.toByteArray(Charsets.US_ASCII))) {
                "NEW_PLAYER_INITIALIZATION_MARKER_CONFLICT"
            }
            return null
        }
        return LANGUAGES[config.language]
    }

    /** The caller captures immediately, before the original tutorial advances step0 to1. */
    @JvmStatic
    fun onWorldReady(): Boolean {
        val supplied = BackendCloudController.newPlayerInitialization() ?: run {
            pendingHash = null
            return false
        }
        val config = parse(supplied)
        val marker = GameSaveStore.read(MARKER)
        if (marker != null) {
            require(marker.contentEquals(config.hash.toByteArray(Charsets.US_ASCII))) {
                "NEW_PLAYER_INITIALIZATION_MARKER_CONFLICT"
            }
            pendingHash = null
            return false
        }
        check(FarmGameEngine.aZB == 0 && GameSaveStore.read("save_gameplay") == null) {
            "NEW_PLAYER_INITIALIZATION_REQUIRES_EMPTY_OWN_FARM"
        }
        if (pendingHash != null) {
            check(pendingHash == config.hash) { "NEW_PLAYER_INITIALIZATION_CHANGED_DURING_CAPTURE" }
        }
        FarmGameEngine.aBI = false
        FarmGameEngine.aBH = false
        FarmGameEngine.hJ(config.coins)
        FarmGameEngine.hK(config.cash)
        FarmGameEngine.hL(config.level)
        FarmGameEngine.hM(config.experience)
        FarmGameEngine.ia(config.plots)
        FarmGameEngine.ib(config.trees)
        FarmGameEngine.ic(config.animals)
        FarmGameEngine.id(config.ponds)
        FarmGameEngine.currentTutorialStepIndex = 0
        FarmGameEngine.hasUnsavedGameChanges = true
        pendingHash = config.hash
        return true
    }

    /** Must run inside the same first immutable full-farm capture, never an auxiliary write. */
    @JvmStatic
    fun captureInitializationMarker() {
        val hash = pendingHash ?: return
        check(FarmGameEngine.aZB == 0) { "NEW_PLAYER_INITIALIZATION_REQUIRES_OWN_FARM_CAPTURE" }
        val bytes = hash.toByteArray(Charsets.US_ASCII)
        GameSaveStore.write(MARKER, bytes, 0, bytes.size)
        // Retain until the first snapshot is durable; a rejected writer retries
        // the exact same absolute snapshot. No successful-login state is fabricated.
    }

    private fun parse(initialization: JSONObject): Config {
        exactKeys(initialization, setOf("configHash", "defaults"))
        val hash = initialization.get("configHash")
        require(hash is String && hash.matches(Regex("[0-9a-f]{64}"))) { "NEW_PLAYER_DEFAULTS_HASH_INVALID" }
        val values = initialization.get("defaults")
        require(values is JSONObject) { "NEW_PLAYER_DEFAULTS_INVALID" }
        exactKeys(values, setOf("schemaVersion", "initialLevel", "initialExperience", "initialCoins",
            "initialCash", "initialTutorialStep", "initialLanguage", "initialCapacityLimits", "initialWorldTemplate"))
        number(values, "schemaVersion", 1..1)
        val level = number(values, "initialLevel", 1..50)
        val experience = number(values, "initialExperience", 0..if (level == 50) 0 else EXPERIENCE_LIMITS[level] - 1)
        val coins = number(values, "initialCoins", 0..99_999_999)
        val cash = number(values, "initialCash", 0..3_000_000)
        number(values, "initialTutorialStep", 0..0)
        val language = values.get("initialLanguage")
        require(language is String && (language == "auto" || LANGUAGES.containsKey(language))) { "NEW_PLAYER_LANGUAGE_INVALID" }
        val capacities = values.get("initialCapacityLimits")
        require(capacities is JSONObject) { "NEW_PLAYER_CAPACITIES_INVALID" }
        exactKeys(capacities, setOf("plots", "trees", "animals", "ponds"))
        val plots = number(capacities, "plots", 1..60)
        val trees = number(capacities, "trees", 0..25)
        val animals = number(capacities, "animals", 0..40)
        val ponds = number(capacities, "ponds", 0..20)
        require(values.get("initialWorldTemplate") == "original-4.4.2") { "NEW_PLAYER_WORLD_TEMPLATE_INVALID" }
        val config = Config(hash, level, experience, coins, cash, language, plots, trees, animals, ponds)
        val actualHash = MessageDigest.getInstance("SHA-256").digest(canonical(config).toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it.toInt() and 255) }
        require(actualHash == hash) { "NEW_PLAYER_DEFAULTS_HASH_MISMATCH" }
        return config
    }

    private fun canonical(c: Config): String = "{\"schemaVersion\":1,\"initialLevel\":${c.level}," +
        "\"initialExperience\":${c.experience},\"initialCoins\":${c.coins},\"initialCash\":${c.cash}," +
        "\"initialTutorialStep\":0,\"initialLanguage\":\"${c.language}\",\"initialCapacityLimits\":{" +
        "\"plots\":${c.plots},\"trees\":${c.trees},\"animals\":${c.animals},\"ponds\":${c.ponds}}," +
        "\"initialWorldTemplate\":\"original-4.4.2\"}"

    private fun exactKeys(value: JSONObject, expected: Set<String>) {
        val actual = HashSet<String>()
        val keys = value.keys()
        while (keys.hasNext()) actual.add(keys.next())
        require(actual == expected) { "NEW_PLAYER_DEFAULTS_FIELDS_INVALID" }
    }

    private fun number(value: JSONObject, name: String, range: IntRange): Int {
        val raw = value.get(name)
        require(raw is Int || raw is Long) { "NEW_PLAYER_DEFAULTS_INTEGER_REQUIRED" }
        val number = (raw as Number).toLong()
        require(number >= range.first.toLong() && number <= range.last.toLong()) { "NEW_PLAYER_DEFAULTS_OUT_OF_RANGE" }
        return number.toInt()
    }
}
