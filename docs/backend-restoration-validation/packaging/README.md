# Packaging checkpoints

All APK checks below inspect the completed signed APK, including its binary
manifest and DEX. No `compile-view.jar` method bodies are packaged as game code.

- `cloud/`: source `0a36769`, first candidate; **failed actual Android startup**
  at the old privacy SDK `AdsManager.SetAge` JNI call. Not published.
- `cloud-fixed/`: source `8fb4b89`, all scoped Ads JNI invokes removed; actual
  old/fresh farms, planting/harvesting and interrupted-network durability tested.
  Fresh-profile cold reopen then exposed missing codec-binding metadata.
- `windows/`: source `8fb4b89`, matching Windows-origin candidate, held and
  superseded before publication. Its historical unchanged-artifact statements
  describe that checkpoint before integrating user cleanup on `main`.
- `cloud-fixed2/`: source `cb57a03`, authenticated full-archive identity repair;
  Android runtime testing is documented separately under `runtime/fixed/`.
- `windows-fixed/`: source `cb57a03`, final Windows-origin build using the
  unchanged root `BACKEND_BASE_URL`. Final artifact SHA is recorded in
  `artifacts/build-info.json`.

Cloud and Windows use different endpoint origins. The final comparison proves
identical merged game DEX before endpoint patching; this does not establish
reachability of the user's Windows LAN from the cloud environment.
