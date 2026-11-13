# Isolated offline test grant

The offline debug APK uses application ID `com.quanhoang.greenfarm3.offlinetest`.
Its Android UID, private save database and signing/package identity are separate
from the normal game. `OfflineTestBalances` additionally checks both the generated
offline build flag and the installed application ID before changing an own farm.
Normal builds and visiting farms receive no grant.

At the first verified world load of a new test profile, the helper initializes
coins and cash to **9,999,999 each** and player level to **50**, the hard maximum
enforced by this client's `FC()` and `hL()`. It calls the original protected
setters, which update both value and shadow validation fields. It applies the
original `hW()` level-50 capacity limits (60 plots, 25 trees, 40 animals, 20 ponds)
through `ia/ib/ic/id`; it does not create any of these objects. XP is the original
within-level value and remains unchanged: `hX()` returns immediately at level50.
The helper does not replay level-up callbacks, reward spawns or quest updates.
Inventory stock, quests and tutorial progression retain their original behavior.

The grant marker `offline_test_balances_v1` is captured inside the same immutable
full-farm snapshot as the balances, level and limits. The existing save queue and
SQLite transaction commit them together. A rejected or failed write can retry;
there is no separately committed marker that could suppress an unsaved grant.
After a successful save, purchases deduct the normal costs and reopening the game
keeps the remaining amounts. There is no per-frame or per-launch refill. Clearing
this test application's data intentionally creates a new test profile.

The original cash cap is3,000,000. Every test world load raises that process's cap
to9,999,999 so the next legitimate purchase does not truncate the starting cash.
The normal cap is unchanged. Coins already have a99,999,999 cap. Marker corruption
fails the existing load barrier without silently resetting saved progress.

The normal save archive whitelist does not support this separate test package;
save export/import for the test APK is not supplied. Installing this APK does not
import, replace or reset the normal application's private save.

`tests/test_offline_test_balances.py` compiles the real Kotlin helper, original
protected wrapper classes and extracted original getter/setter/spending methods.
It verifies profile guards, sign and shadow consistency, max level and capacities,
unchanged XP/tutorial/inventory, failed atomic captures, spending and reopening.
Android runtime and SQLite durability results are recorded separately from these
host fixture checks.
