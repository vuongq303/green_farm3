# Server-controlled new player initialization

The editable source of account defaults is
`backend/config/new_player_defaults.json`. The server validates and normalizes
this file, then stores a pinned configuration/hash for each new guest profile.
Changing and reloading the server configuration affects accounts created after
that reload. It does not refill or rewrite existing profiles. The backend guide
describes its reload/startup behavior and accepted configuration fields.

The verified original4.4.2 starting state is level1, XP0, **2000 coins**,50 cash,
tutorial0, automatic language and capacity limits10 plots/10 trees/10 animals/
1 pond. Table20/aZw supplies2000/50, while `aY()` supplies level/XP and these
initial limits. The1900 coins observed in an existing farm are spent progress,
not the original account default. `original-balance-tables.json` contains the
read-only original runtime table evidence. The original table27 level-up limits
are a separate concept and must not replace `aY()`'s initial capacities.

The client receives `{configHash,defaults}` only after the authenticated server
confirms NO_SAVE and the local device has neither an existing gameplay record nor
pending cloud work. `NewPlayerDefaults` verifies exact keys, integer types, ranges,
the original per-level XP limit and a canonical SHA-256 hash. Missing or invalid
initialization blocks a new profile with an error; the client does not invent
fallback account resources. Existing local saves use the separate explicit local
copy approval flow, and remote profiles restore their committed snapshots.

The original engine still constructs its original asset/template structures.
Before the first immutable farm checkpoint, the helper applies the server-pinned
coins/cash/level/XP/capacities using original protected setters, preserving shadow
validation fields. It sets tutorial0 and does not invoke level-up reward callbacks,
complete quests, grant stock or modify map objects. `initialWorldTemplate` currently
accepts only `original-4.4.2`: original assets retain the starting objects,
buildings, plot arrangement and quest definitions. Individual unverified object
IDs/stock layouts are not configurable or invented by this change.

`initialLanguage: "auto"` preserves original locale/language behavior. Supported
explicit codes are en/de/fr/it/es/pt-br/ja/ko/ru/pl/tr/th/vi/es-419. An explicit
language is resolved before text assets load, including after the original
ES/PT-BR regional overrides. Existing profiles retain their saved language.

The production marker `new_player_defaults_v1` holds only the64 ASCII characters
of the pinned config hash. It is captured with the first complete farm snapshot,
not written separately. The controller creates this immutable tutorial0 checkpoint
before the original entry code advances the tutorial, retains failed work for
retry, and blocks input/new captures until that first local snapshot is durable.
Later gameplay can spend resources normally; loading an existing marked farm
never reapplies the initialization. Local durability does not mean the server has
acknowledged the snapshot: cloud work remains pending until its matching commit
receipt arrives.

The backend's first fresh-save policy decodes original format4 values using the
verified original layout and signed Java arithmetic; it compares resources,
level/XP/tutorial, limits, explicit language and marker hash with the pinned server
configuration. The separate local-copy authorization flow does not silently claim
old Facebook ownership or reset a legitimate existing farm. The oracle, original
DEX instruction listings and5 JVM-generated vectors are under
`docs/backend-restoration-validation/new-player-oracle/`.

Focused host checks execute the real helper, original protected wrappers/setters
and the actual original ES/PT-BR language branches. They cover strict malformed
configuration rejection, retry, atomic marker/no refill, old-profile protection,
unchanged inventory/quests and pre-asset language handling. The decoder's Node
checks use original-method JVM vectors, UTF-8 binding byte lengths, signed tutorial
values and truncation/format rejection. These checks do not establish Android
startup/gameplay, network transport or full lifecycle behavior; those require the
separate APK/device and backend integration evidence.
