#!/usr/bin/env python3
"""Offline network-string inventory of the pinned original APK (stdlib only).

Reads APK/DEX and optional original baksmali output; never modifies inputs,
contacts a service, executes APK code, or reconstructs a server protocol.
JSON goes to stdout. Query values and long path identifiers are redacted.
"""

import argparse
import ast
from collections import Counter, defaultdict
import hashlib
import json
from pathlib import Path
import re
import struct
import sys
from urllib.parse import urlsplit
import zipfile


APK_SHA256 = "34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b"
DEX_SHA256 = "3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4"
GAME = "com/gameloft/android/ANMP/GloftGF2F/"
FEDERATION = GAME + "S800x480/s.smali"
URL = re.compile(r"\b(?:https?|wss?|socket)://[^\s\x00\"'<>\\]+", re.I)
BARE = re.compile(r"(?<![\w.@])(?:[a-z0-9-]+\.)*gameloft\.com(?::[0-9]+)?(?:/[^\s\x00\"'<>\\]*)?", re.I)
CONST = re.compile(r'^\s*const-string(?:/jumbo)?\s+\w+,\s+(".*")\s*$')
SERVICE_NAMES = {"message", "asset", "auth", "leaderboard", "social", "storage", "lobby", "matchmaker", "gllive", "config", "chat", "alert"}


def sha256(data):
    return hashlib.sha256(data).hexdigest()


def dex_strings(data):
    """Yield string-table index, string_data_item offset, decoded string.

    ASCII URLs survive this bounded MUTF-8 decode; malformed/non-ASCII data
    is replaced, not interpreted as executable instructions.
    """
    if not data.startswith(b"dex\n") or len(data) < 112:
        raise ValueError("not a classic DEX file")
    count, table = struct.unpack_from("<II", data, 56)
    for index in range(count):
        offset = struct.unpack_from("<I", data, table + index * 4)[0]
        start = offset
        for _ in range(5):
            byte = data[start]
            start += 1
            if byte < 128:
                break
        end = data.index(0, start)
        yield index, offset, data[start:end].decode("utf-8", "replace")


def endpoints(value):
    found = list(URL.finditer(value))
    for match in found:
        yield match.group(), match.start(), "url_literal"
    for match in BARE.finditer(value):
        if not any(x.start() <= match.start() < x.end() for x in found):
            yield match.group(), match.start(), "scheme_less_gameloft_literal"


def safe_endpoint(raw):
    raw = raw.rstrip(".,;)}]")
    # Gameloft IGP templates use #NAME# inside query values; these are
    # placeholders, not URI fragments. Keep subsequent parameter names.
    raw = re.sub(r"#[A-Z_0-9]+#", "<placeholder>", raw)
    has_scheme = "://" in raw
    try:
        parsed = urlsplit(raw if has_scheme else "//" + raw)
        host = (parsed.hostname or "").lower()
        if not host:
            return None
        port = parsed.port
    except ValueError:
        return None
    path = re.sub(r"(?<=/)[A-Za-z0-9_=-]{32,}(?=/|$)", "<redacted-segment>", parsed.path)
    keys = []
    if "?" in raw:
        for part in raw.split("?", 1)[1].split("#", 1)[0].split("&"):
            key = part.split("=", 1)[0]
            if key and re.fullmatch(r"[A-Za-z0-9_.%\[\]-]{1,80}", key):
                keys.append(key)
    authority = host + (":" + str(port) if port else "")
    prefix = (parsed.scheme.lower() + "://") if has_scheme else ""
    sanitized = prefix + authority + path
    if keys:
        sanitized += "?" + "&".join(key + "=<redacted>" for key in keys)
    if parsed.fragment:
        sanitized += "#<redacted>"
    return sanitized, host


def category(host, refs):
    if host == "eve.gameloft.com":
        return "gameloft_bootstrap_config"
    if host == "vgold.gameloft.com":
        return "gameloft_tracking_discovery"
    if host == "state-gold.gameloft.com":
        return "gameloft_state_tracking"
    if host.endswith("gameloft.com"):
        if host in {"201205igp.gameloft.com", "578756.gameloft.com"}:
            return "gameloft_ads_cross_promotion"
        if host == "game-portal-beta.gameloft.com":
            return "gameloft_game_portal"
        if host == "interstatic01.gameloft.com":
            return "gameloft_static_content"
        return "gameloft_support_legal_or_iap"
    if host == "jabber.org" or host.endswith(".jabber.org") or host in {"www.w3.org", "xmlpull.org", "schemas.android.com"}:
        return "xml_namespace_or_schema_not_network_destination"
    if host == "gcc.gnu.org":
        return "embedded_compiler_diagnostic_not_service"
    if host == "www.example.com":
        return "example_placeholder"
    paths = " ".join(ref.get("file", "") for ref in refs)
    if any(x in host for x in ("facebook", "fbcdn", "instagram")) or "com/facebook/" in paths:
        return "third_party_social_sdk"
    if any(x in host for x in ("appsflyer", "adjust", "flurry", "crashlytics", "app-measurement", "google-analytics")):
        return "third_party_analytics_sdk"
    if any(x in host for x in ("admob", "doubleclick", "inmobi", "vungle", "applovin", "unityads", "chartboost", "mopub", "adcolony", "smaato", "ads.", "adsafeprotected")):
        return "third_party_ad_sdk"
    if any(x in host for x in ("play.google.com", "market.android.com", "amazon.com")):
        return "store_iap_or_external_link"
    if "google" in host or "gstatic" in host or host == "goo.gl":
        return "google_sdk_or_external_link"
    if any(x in paths for x in ("/ads/", "/glads/", "/adcolony/", "/vungle/")):
        return "sdk_ad_related"
    if host in {"tsto.re", "www.youtube.com", "signal-back.com"}:
        return "game_wrapper_external_link"
    return "other_literal_role_unresolved"


def scan_smali(root, dex_values):
    refs = defaultdict(list)
    files = {}
    services = []
    routes = []
    for path in sorted(root.rglob("*.smali")):
        relative = path.relative_to(root).as_posix()
        blob = path.read_bytes()
        lines = blob.decode("utf-8").splitlines()
        method = None
        class_name = None
        route = None
        table = None
        for number, line in enumerate(lines, 1):
            if line.startswith(".class "):
                class_name = line.rsplit(" ", 1)[-1]
            if line.startswith(".method "):
                method = line.rsplit(" ", 1)[-1]
            if relative == FEDERATION and method == "ja()V":
                request = re.search(r"/t;->(\w+):I", line)
                if request and request.group(1) != "max":
                    route = {"request_field": request.group(1), "line": number, "path_constants": [], "parameter_names": []}
                    routes.append(route)
                    table = None
                table_match = re.search(r"/s;->(MH|MI|MJ):", line)
                if table_match:
                    table = table_match.group(1)
            match = CONST.match(line)
            if not match:
                continue
            try:
                value = ast.literal_eval(match.group(1))
            except (SyntaxError, ValueError):
                continue
            evidence = {"file": relative, "line": number, "class": class_name, "method": method}
            if relative == FEDERATION and method == "<clinit>()V" and value in SERVICE_NAMES:
                services.append({"name": value, "line": number})
            if route is not None and relative == FEDERATION and method == "ja()V":
                if table == "MI":
                    route["path_constants"].append(value)
                elif table == "MJ":
                    route["parameter_names"].append(value)
            # A smali cross-reference is reported only if its entire literal is
            # also in the original DEX string pool. This is not opcode proof.
            if value not in dex_values:
                continue
            for raw, _, kind in endpoints(value):
                safe = safe_endpoint(raw)
                if safe:
                    evidence["kind"] = kind
                    refs[safe[0]].append(dict(evidence))
                    files[relative] = sha256(blob)
    context_files = [FEDERATION, GAME + "S800x480/am.smali", GAME + "m.smali",
                     "com/gameloft/glads/GLURLConnection.smali"]
    for relative in context_files:
        if (root / relative).is_file():
            files[relative] = sha256((root / relative).read_bytes())
    return refs, files, services, routes


def inventory(apk, smali=None):
    apk_bytes = apk.read_bytes()
    actual = sha256(apk_bytes)
    if actual != APK_SHA256:
        raise ValueError("APK checksum does not match pinned original")
    records = {}
    pool = set()
    dex_metadata = []
    payload_metadata = []

    def add(raw, evidence):
        safe = safe_endpoint(raw)
        if not safe:
            return
        endpoint, host = safe
        record = records.setdefault(endpoint, {"endpoint": endpoint, "host": host, "evidence": []})
        record["evidence"].append(evidence)

    with zipfile.ZipFile(apk) as archive:
        for entry in sorted(archive.namelist()):
            if re.fullmatch(r"classes\d*\.dex", entry):
                data = archive.read(entry)
                digest = sha256(data)
                if entry == "classes.dex" and digest != DEX_SHA256:
                    raise ValueError("DEX checksum does not match pinned original")
                count = 0
                for index, offset, value in dex_strings(data):
                    count += 1
                    pool.add(value)
                    for raw, relative, kind in endpoints(value):
                        add(raw, {"source": "dex_string_pool", "entry": entry, "string_index": index, "string_data_offset": offset, "match_character_offset": relative, "kind": kind})
                dex_metadata.append({"entry": entry, "sha256": digest, "size": len(data), "string_count": count})
            elif entry.startswith(("assets/", "lib/")) and not entry.endswith("/"):
                data = archive.read(entry)
                payload_metadata.append({"entry": entry, "sha256": sha256(data), "size": len(data)})
                # Binary asset strings may be adjacent without separators.
                # For assets report domain only; do not invent URL paths.
                text = data.decode("latin-1")
                for raw, offset, kind in endpoints(text):
                    if entry.startswith("assets/"):
                        # Localized binary text can immediately follow .com
                        # without a delimiter. Use only the known Gameloft
                        # domain ending, not urlsplit's overlong hostname.
                        domain = re.search(r"(?:[a-z0-9-]+\.)*gameloft\.com", raw, re.I)
                        if not domain:
                            continue
                        raw = domain.group()
                    add(raw, {"source": "native_literal" if entry.startswith("lib/") else "asset_domain_only", "entry": entry, "uncompressed_byte_offset": offset, "kind": kind})
    refs, files, services, routes = {}, {}, [], []
    if smali is not None:
        if not smali.is_dir():
            raise ValueError("smali directory does not exist")
        refs, files, services, routes = scan_smali(smali, pool)
    for endpoint, record in sorted(records.items()):
        full_refs = refs.get(endpoint, [])
        record["smali_reference_count"] = len(full_refs)
        record["smali_references"] = full_refs if "gameloft.com" in record["host"] else full_refs[:3]
        record["category"] = category(record["host"], full_refs)
        record["host_is_template_or_cookie_domain"] = "%" in record["host"] or record["host"].startswith(".")
        record["evidence_count"] = len(record["evidence"])
        if "gameloft.com" not in record["host"]:
            record["evidence"] = record["evidence"][:3]
    return {
        "schema_version": 1,
        "scope": "offline static literal inventory; references are not execution evidence",
        "apk": {"name": apk.name, "sha256": actual, "size": len(apk_bytes)},
        "dex": dex_metadata,
        "coverage": {
            "payload_entries_scanned": len(payload_metadata),
            "native_entries_scanned": sum(x["entry"].startswith("lib/") for x in payload_metadata),
            "asset_entries_scanned": sum(x["entry"].startswith("assets/") for x in payload_metadata),
            "distinct_hosts": len({r["host"] for r in records.values()}),
            "distinct_sanitized_endpoints": len(records),
            "categories": dict(sorted(Counter(r["category"] for r in records.values()).items())),
            "third_party_reference_limit_per_endpoint": 3,
        },
        "redaction": "All query values, fragments and long path identifiers omitted; no arbitrary literal dumps. Assets report domains only because string boundaries are unresolved.",
        "limits": [
            "URL literals and DEX-pool-matching smali references do not establish execution or complete data flow.",
            "DEX string offsets are relative to uncompressed DEX; native/asset offsets are relative to uncompressed APK entry.",
            "Only literal URL schemes and scheme-less Gameloft domains are recognized; computed, encoded, remote-configured and native-generated destinations remain unresolved.",
            "No DNS, HTTP, device, build, credential submission, service liveness, native callgraph or protocol reconstruction performed.",
            "Smali reference files are hashed but not reassembled/validated as original DEX opcodes by this scanner.",
            "Federation route constants are declarative MI/MJ table strings; null/dynamic path slots and method/feature gates are not interpreted.",
        ],
        "smali_evidence_files_sha256": dict(sorted(files.items())),
        "federation": {"file": FEDERATION, "service_name_literals": services, "route_table_constants": routes},
        "payload_entries": payload_metadata,
        "literal_endpoints": [records[k] for k in sorted(records)],
    }


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--apk", type=Path, default=Path(__file__).resolve().parents[1] / "green-farm-3.apk")
    parser.add_argument("--smali", type=Path, help="Original baksmali directory, optional (adds method/line evidence)")
    args = parser.parse_args()
    try:
        result = inventory(args.apk, args.smali)
    except (OSError, ValueError, zipfile.BadZipFile, struct.error) as error:
        parser.exit(1, f"inventory_network: {error}\n")
    json.dump(result, sys.stdout, indent=2, sort_keys=True)
    sys.stdout.write("\n")


if __name__ == "__main__":
    main()
