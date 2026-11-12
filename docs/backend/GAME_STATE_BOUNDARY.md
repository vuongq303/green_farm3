# Farm state and independent backend boundary

Static analysis of the original 4.4.2 APK, against repository baseline
`ce19d54`. This report changes documentation only. No application, save,
device, build, test or server state was changed, and no original service was
contacted.

The original client contains both local farm persistence and remote account,
social and cloud-save facilities. **Coins, cash and XP are present in local
gameplay saves.** Their presence does not establish that the original server
validated every economy operation. A new server-authoritative economy would be
a new design decision, not a recovered original guarantee.

## Evidence convention

All line numbers below are physical lines in the retained original baksmali
output under `/workspace/tooling/original-smali`. Prefix `S/` means
`com/gameloft/android/ANMP/GloftGF2F/S800x480/`. For example,
`S/ci.smali:178486` identifies
`Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/ci;->ax(Z)Z`.
These are not Java source line numbers. The original APK/DEX hashes are recorded
in [RECORDSTORE_RECOVERY.md](../RECORDSTORE_RECOVERY.md).

The recovered Java was used to navigate; the findings below were checked in
original smali. This is a bounded call-path review, not proof of successful
runtime gameplay or a complete field-by-field save decoder.

## Local storage that already exists

| State | Original evidence | Implication |
| --- | --- | --- |
| Settings, account/UI flags and age-related values | `S/ci.smali:372569`, `ro()V`, writes `RMSHeader`; `:372982`, `rp()Z`, reads it. The header also persists language and migration information. | Startup preferences and locally remembered account/UI state have their own record. A saved login flag is not proof of a valid server session. |
| Global gameplay | `S/ci.smali:373849`, `rt()V`, writes `save_gameplay` at lines 376118–376130; `:178486`, `ax(Z)Z`, reads it or the in-memory `aCw:[B` buffer. | The game has a local gameplay snapshot independent of a cloud download. |
| Level, XP, coins and cash | `rt()V`, lines 374336–374382, serializes `aBa:I`, `aBb:bi`, `aBc:bk`, `aBd:bi`; `ax(Z)Z`, lines 179573–179700, restores the same values. The load diagnostics explicitly identify XP, money and cash; cloud-description code labels coin/cash at lines 177241 and 177263. | A backend plan must retain these values in compatibility snapshots. Server validation of all mutations is not established by these methods. The `bi`/`bk` wrappers and numeric transform helpers should not be mistaken for service calls. |
| Quest state and other arrays | `rt()V`, including quest arrays before line 374336 and the five nested `aAW:[[I` arrays at lines 374455–374466; inverse reads in `ax(Z)Z`, including lines 180102–180168. | Significant nonvisual gameplay state is local. The exact semantic mapping of every opaque array remains to be decoded; do not publish a guessed inventory schema as original protocol. |
| Own-farm layout and entity state | `S/ci.smali:376826`, `ry()V`, records expansion flags, `aEe:[I` and the entity buffer, then writes `save_farm` + `aZB` at lines 377018–377060; `:214265`, `d(ZZ)Z`, reads that record or `aCx:[B`, then restores flags/entities. | Own-farm geometry and timed/entity state have local serialization. `az(Z)Z` delegates to `d(ZZ)Z`. |
| Social UI/cache state | `S/ci.smali:376245`, `rw()V`, writes party invites, help requests and other invite lists to `online_gameplay` at lines 376745–376747. | Social records can exist locally as remembered state. This does not make relationships, other players' farms or remote messages locally authoritative. |
| Packed cloud-save container | `S/ci.smali:176916`, `aZ(Z)V`, places global and farm buffers into `greenfarm3`; `S/bq.smali:2147`, `cv(Ljava/lang/String;)I`, reads a local `javax.microedition.b.b` record. | “Cloud” in a method/log name does not by itself mean a network request. Cloud support also maintains local containers. |

The persistence call chain is concrete. `ci.av(Z)V` calls `ro()`, `rt()` and
`ry()` at `S/ci.smali:178331–178339`, and only conditionally calls `aZ(Z)`.
`GLLib.a(Ljava/lang/String;[BLjava/lang/String;)V` at
`S/GLLib.smali:13683` passes an optional key as bytes through to
`b(Ljava/lang/String;[BII[B)V` at line 21878. That method optionally transforms
the bytes and writes record 1 through `Ljavax/microedition/b/b;->c(I[BII)V`
or adds it with `n([BII)I`. The inverse reader is
`GLLib.B(Ljava/lang/String;Ljava/lang/String;)[B` at line 3982, through
`d(Ljava/lang/String;[B)[B` at line 27973, using `pi(I)[B`. These are RMS
operations, not HTTP requests. The underlying record writer and CRC/backup
format are separately documented in [RECORDSTORE_RECOVERY.md](../RECORDSTORE_RECOVERY.md).

`ci.rv()Ljava/lang/String;` (`S/ci.smali:376231`) reads the local system
property `HDIDFV`; callers pass it to those keyed storage helpers. In
`ax(Z)Z`, a stored device identifier is also compared with `rl()` (ultimately
wrapper `au.Os()`). On the evidenced mismatch branch, when `asP` is false and
identifier lengths match, the client clears remembered flags, marks `asq`
and returns false (`S/ci.smali:178740–178815`). Therefore copying a save to a
different installation is not equivalent to exercising an original cloud
restore. No identity-bypass change is proposed here.

## Minimum farm creation/load path, once gameplay loading is reached

The local loader provides a useful narrow target for the first independent
backend milestone:

1. `ci.fM(I)V` case 44 invokes `ax(false)`
   (`S/ci.smali:226137–226151`; case mapping at lines 227209–227212).
   When neither `aCw` nor `save_gameplay` supplies bytes, `ax(Z)Z` sets
   `asq=true` and returns false (`S/ci.smali:178506–178570`).
2. Case 46 sets current farm `aZB` from requested farm `aZC` and loads tiles
   (`S/ci.smali:225924–225936`). For the own farm, current farm is zero.
3. Case 47 resets the grid, loads common entities with `fN(3)` and calls
   `qY()` (`S/ci.smali:226198–226215`). If `aZB==0` and `asq==true`, it
   clears `asq`, calls `fN(4)` and `ch.fd(1)`
   (`S/ci.smali:226350–226377`). `fN(I)V` opens package `/14`, reads the
   indexed bytes with `GLLib.db(I)[B`, closes the package and constructs
   entities (`S/ci.smali:227256–227887`). This branch creates the starting
   farm from packaged data; it does not request a farm from an account server.
4. For `aZB==0` and `asq==false`, case 47 calls `az(false)` at line 226377.
   Its `d(ZZ)Z` implementation loads local `save_farm0`/`aCx`, expansion
   state and entities (`S/ci.smali:214265–214610`).

The other branches have distinct roles: farms 1–10 are also initialized from
packaged data; a farm index above 10 enters `aw(true)` and can consume a
downloaded farm buffer. `d(ZZ)Z` can decode `aND` when its first argument is
true, so remote-neighbor farm loading must not be conflated with own-farm RMS.

The original loader arrays support that separation. The initial `asd` array
includes steps 44, 46 and 47 and omits online-data step 50; `asf` also omits
step 50, while `asg` starts with it (`S/ci.smali:5821–5851`, array payloads
at lines 18232–18318). `qV()V` explicitly waits on empty `aND` at step 50
(`S/ci.smali:367085–367109`). Therefore an empty remote-neighbor buffer is
not by itself an explanation for an own-farm `asd`/`asf` loader stall.

State 18 dispatches to `jM(I)Z` (`aR(II)Z`, `S/ci.smali:173596`, handler
at line 173677; `jM` begins at line 288996). On entry, its initial online
request `B(1,-1,-1)` is gated by `qN() && bt.nY() && !aLR`; the following
continuation sets `aLR=true` regardless of whether that request was made
(lines 289239–289268). No response wait occurs in that short branch.
In loader case 44, the other calls `Ge()` and `qw()` read local
`RMS_CloudMandatory` and `RMS_NewVersionScreen` records, respectively
(`S/ci.smali:76748` and `:367835`); `d(false,atH)` is explicitly the
`IAP_SendTrackingClickOnIAPPacks` tracking routine (`:213042`). Those names
should not be taken as evidence of a mandatory farm-download wait. Later
state-18 updates still contain network/social features and have not been
exhaustively audited here.

This establishes a **conditional local creation/load path**, not that Accept
already reaches it without a server. The Accept/account/privacy continuation,
loading-state selection, initialization defaults and later startup callbacks
must also complete. The narrow first acceptance check should be: after the
authorized consent/account continuation, reach own-farm loader case 47, show
the packaged starting entities, then save and reload the same farm. No new
account, cloud, economy, social or purchase response is shown to be required
inside the specific fresh-own-farm creation branch above. It is not evidence
that all upstream initialization or every gameplay feature is offline.

Saving has meaningful original gates: `av(Z)V` rejects saving a visited farm
or an active special mode; ordinary saves reject low progress or an unfinished
tutorial (`S/ci.smali:178021–178363`). `rt()`/`ry()` also check level/progress
through `FC()`. Reaching a visible tutorial farm alone does not prove durable
saving; verify at an original eligible save point.

## Remote facilities and boundaries

Cloud upload is explicitly separable from local save. In `aZ(Z)V`, the true
argument checks online gates `qN()` and `arg`, requires both `aCw` and `aCx`,
and invokes the save manager. The false path calls `bq.i(description,false)`;
the true path selects `"me"` and calls `bq.i(description,true)`
(`S/ci.smali:176916–177447`).
`bq.i(Ljava/lang/String;Z)I` delegates to
`bq.a(Ljava/lang/String;ZB)I` (`S/bq.smali:2965` and `:558`). That method
commits a local record first; it starts the upload operation only if the
boolean argument is true (lines 674–719). The remote implementation includes
storage calls such as `$savegamelib.objects.TOC`,
`$savegamelib.objects.key*` and `game_dedicated_slot.dat`, with callback/event
names such as `cloudUploadGetToc`, `cloudUploadPutKey` and `cloudUploadGame`
(`bq.nI()V`, lines 3243–4051; `nJ()V`, lines 4053–4253). These names are
original evidence, not a complete recovered HTTP protocol.

Cloud metadata parsing expects `SeshatFileKey`, `Description`, `Date` and
`GLUID` inside TOC entries (`bq.nM()V`, `S/bq.smali:4335–4623`). Restores
first retrieve remote bytes, write a temporary local record, then decode/repack
the buffers (`Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/bq;->a(Ljava/lang/String;Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/e;)V`,
`S/bq.smali:963–1313`). An independent snapshot format should not
pretend to be those bytes without fully recovering the codec and transport.

Account authentication, credential linking, service discovery, remote profiles,
neighbor farms, social requests, purchases and remotely delivered configuration
remain separate integration concerns. The presence of locally saved balances,
friend caches or a `"me"` selector is not enough to infer their original
authorization rules, conflict policy, request signatures or full response
schemas. No complete login/link schema or server-side authority model is
claimed here; those require their own evidenced transport/consumer paths.

Two naming traps matter for backend planning:

- `ci.baf` is a field of type `Lcom/gameloft/android2d/d/b;`, not an account
  class. That class declares `.source "TrackingManager.java"` and delegates
  tracking/time/lifecycle methods to the outer game package's `bx`
  (`com/gameloft/android2d/d/b.smali:1–281`). Its existence and tracking
  callbacks do not establish ownership of a farm save.
- `S/by` is a TCP receive helper; `S/bc` is a JSON-like parser/serializer.
  Their transport/serialization roles do not establish that farm simulation
  runs on a server (`S/by.smali:132`, `ok()V`; `:1083`, `run()V`;
  `S/bc.smali:979`, `ca(Ljava/lang/String;)V`; `:1898`, `toString()Ljava/lang/String;`).

## Proposed independent schema — NEW design, not original protocol

Start with separate guest ownership, snapshot storage and optional identity
linking. The names and fields in this table are proposals for a new backend;
none should be described as recovered original endpoints or tables.

| New record | Suggested minimum fields | Authority/responsibility |
| --- | --- | --- |
| `player` | random `player_id`, creation time, status | Backend issues an independent guest identity. Starting a local farm should not require a third-party account link. |
| `identity_link` | `player_id`, provider, provider subject, linked time | Optional authenticated identity linkage. Never infer ownership from a copied device ID or local remembered-login flag. |
| `farm_snapshot` | `player_id`, `farm_id`, revision, codec version, game build, global bytes, farm bytes, payload hash, server received time | Initially preserve a versioned opaque compatibility snapshot with explicit size limits and compare-and-set revision. Decode only fields whose original codec is verified. |
| `player_preferences` | language, audio/UI preferences, consent version/time where applicable | Preferences and recorded consent are separate from farm/economy ownership. Apply the app's intended consent semantics before continuing startup. |
| `economy_operation` | operation ID, player ID, expected revision, operation kind, validated inputs, resulting revision | A later new service-authoritative economy can validate actions and issue balances. This is an architectural change; it requires client integration and rule recovery rather than trusting arbitrary snapshot balance edits. |
| `social_edge` / `social_request` | independently authenticated participants, type, state, idempotency key, expiry | Shared social facts belong to the new service; local lists become caches. Add only after own-farm boot/save/load works. |

For the first milestone, use one own farm per guest and explicit snapshot
revisions. Preserve failed or conflicting revisions for review; do not merge
opaque arrays or choose a winner by a device clock. A schema change for fields
such as inventory must wait for their decoder and mutation semantics. Purchase
receipts and entitlements need a dedicated verified boundary before any paid
currency can be service-authoritative.

## Remaining uncertainty and verification

Static evidence establishes local codecs and the conditional packaged-farm
branch. It does not prove the current app clears the Accept gate, initializes
every default, completes all loader callbacks, renders a playable farm, or
survives an eligible save/restart. No runtime assertion or new tests were run
for this report. The first device verification belongs to the separate app
integration milestone, with failures reported at their actual continuation or
loader step; fabricated original responses would hide that distinction.
