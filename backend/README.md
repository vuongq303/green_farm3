# Local research backend

This package implements the five bootstrap/Accept wire contracts verified in [ACCEPT_PROTOCOL_EVIDENCE.md](../docs/backend/ACCEPT_PROTOCOL_EVIDENCE.md) and [accept-contract.json](../docs/backend/accept-contract.json), the two profile startup routes verified in [CONFIG_STARTUP_EVIDENCE.md](../docs/backend/CONFIG_STARTUP_EVIDENCE.md), and a new `/healthz` endpoint. It uses Node 24 native TypeScript and standard-library modules. There are no npm dependencies, remote calls, service forwarding, Gameloft accounts, or Gameloft credentials.

The current scope is a local emulator experiment. Sessions exist only in process memory. The anonymous password derivation is public and deterministic; accepting it establishes wire compatibility, not account ownership. This is not production authentication. The entry point rejects `NODE_ENV=production`. PostgreSQL, persistent accounts/farms, cloud synchronization, recovery, and production deployment remain later work.

## Run and test

From the repository root:

```sh
cd backend
npm test
PUBLIC_BASE_URL=http://10.0.2.2:8787 npm start
```

The listener binds to `127.0.0.1:8787` by default. Android Emulator uses `10.0.2.2` to reach the host loopback interface; `PUBLIC_BASE_URL` controls the origins returned to the app. For host-only use, plain `npm start` advertises `http://127.0.0.1:8787`. A container/VM/emulator arrangement must provide its own host connectivity. This package does not alter the emulator, app networking, APK, or routing.

The start command runs a foreground process; Ctrl-C stops it. `/healthz` can be checked on the host at `http://127.0.0.1:8787/healthz`. Tests start isolated HTTP listeners on loopback ephemeral ports and close them; no persistent server is left running.

| Environment variable | Default | Meaning |
| --- | --- | --- |
| `BIND_HOST` | `127.0.0.1` | Listener address. Keep loopback for this experiment. |
| `PORT` | `8787` | Integer `1..65535`. |
| `PUBLIC_BASE_URL` | `http://127.0.0.1:<PORT>` | HTTP(S) origin only, with no credentials, path, query, or fragment. No trailing slash is returned. The built-in listener itself is plain HTTP. |
| `SESSION_TTL_SECONDS` | `900` | Integer `1..86400`. |
| `RESEARCH_SESSION_SECRET` | Fresh random 32-byte secret | Optional environment-supplied UTF-8 value, at least 32 bytes. Never commit it. |

An omitted secret is ephemeral and changes on every start. Even with a stable environment secret, restarting loses all active sessions. No secrets are written automatically. `.local/` and `.env*` files inside this package are ignored, but this program does not automatically load dotenv files. Startup logs report only whether the secret is ephemeral or environment supplied.

## Implemented routes

Successful protocol responses use HTTP `200`: the original HTTP transport and privacy callback have different status acceptance rules. Response bodies are UTF-8 with `Cache-Control: no-store`.

| Route | Response |
| --- | --- |
| `GET /healthz` | `{"status":"ok","mode":"local-research"}`. New service health check. |
| `GET /config/{client}/datacenters` | `[{"name":"local","_datacenter_id":"local-research"}]`. |
| `GET /config/{client}/datacenters/local/urls` | All ten exact string keys listed below. |
| `GET /locate/auth` | Exact plain `PUBLIC_BASE_URL` origin, without JSON quotes, trailing slash, or final newline. |
| `GET /locate/storage` | Same exact plain origin for the profile service. |
| `GET /profiles/me/myprofile?access_token=<token>` | `{"credential":"<authorized anonymous username>"}` from the validated session's stored identity. |
| `POST /authorize` | `{"access_token":"<new opaque local session token>"}`. |
| `POST /users/me/compliance` | `{"LOCAL_RESEARCH_16_PLUS":{"compliant":true}}` for age at least 16, or the same named object with false below 16. |

The URL-map keys are `pandora`, `gllive-ope`, `tapjoy`, `offline_items`, `game_portal`, `status`, `crm_iap`, `ads_agency`, `marketing_site`, and `online_connectivity`. `pandora` is the public base origin. Every other value is an own-origin `/unsupported/<key>/` URL. Those operations return `501`; they are not fabricated working advertising, marketing, commerce, or game services.

The seven compatibility routes come from original client evidence. The health route, input bounds, signed token format, lifetime, and research policy are **new project choices**, not reconstructed server behavior.

## Forms, sessions, and the research rule

Both POST routes require `application/x-www-form-urlencoded`, allowing media-type parameters. The parser decodes UTF-8 form values with `+` as a space. It rejects malformed UTF-8/percent escapes, duplicate or unexpected fields, missing `=`, empty pairs, and bodies above 8 KiB. It never reads JSON request bodies.

`/authorize` requires these five nonempty fields; control characters are rejected except scope whitespace separators:

| Field | Local research validation |
| --- | --- |
| `client_id` | At most 512 UTF-16 code units. Accepted as a compatibility label; it does not select a remote service or privileged account. |
| `username` | `anonymous:<credential>`, with nonempty credential at most 1024 UTF-16 code units. |
| `password` | The original `a.aR(credential)` derivation: for each of the first at most 16 UTF-16 code units, emit character `(codeUnit % 25) + 97`. At most 16 characters. |
| `scope` | At most 256 code units; whitespace-separated tokens from `storage auth config asset transaction message`. These legacy declarations do not enable extra services or privileges. |
| `device_id` | At most 256 UTF-16 code units; required compatibility input, not a hardware attestation. |

There is no pre-existing user database or Gameloft account verification. Each accepted authorization generates a random session identifier and an HMAC-SHA-256 signed opaque token. The token contains only version, random session identifier, issue time, and expiry; no client/device/credential/country data. The in-memory session stores the validated client namespace and full authorized anonymous username. Compliance and profile reads verify the signature, expiry, and membership in this process's session map. Tokens from another process are invalid even with a shared signing secret. At most 10,000 unexpired sessions are retained; capacity returns `503`, and expired entries are removed when issuing/verifying sessions.

The profile endpoint returns only the authenticated session's stored username under the evidenced `credential` key. Two users receive their own identities; there is no query parameter for selecting another user. It does not invent dates, currency, purchases, inventory, or farm progress. Only `access_token` is allowed in its query; malformed, duplicate, or unknown query fields return `400`, and a missing/invalid/expired token returns `401`. Raw query strings and identities are excluded from logs. This minimal session-backed identity profile is not a persistent farm-save service.

`/users/me/compliance` accepts exactly `access_token`, `country`, and `age`. It first requires a valid local session. Country must be two ASCII letters, case insensitive; no membership in an ISO country registry is claimed. Age must be canonical decimal integer text from `0` through `120`: no sign, fraction, whitespace, or leading zeros except `0` itself. These validations are new server constraints; the original checker lacks them.

`LOCAL_RESEARCH_16_PLUS` is an explicit project experiment: age `>=16` returns true, younger ages return false. It is not a legal determination, a country-specific privacy policy, or a claim of COPPA/GDPR compliance. Use the intended adult age of `30` for the first emulator smoke test. The response is always a nonempty named policy object; the original parser's permissive `{}` success branch is not used. In the original app, a false policy response still invokes the successful callback but converts the resulting age to zero.

## Failure behavior and limits

Errors use a small new JSON body `{"error":"<code>"}` and meaningful HTTP status. Original clients may ignore these bodies and can leave callbacks pending on non-200 responses; that behavior is documented in the evidence and is not hidden by success responses here.

| Status | Meaning |
| --- | --- |
| `400` | Missing/invalid input, malformed form, unknown/duplicate field, unsupported scope. |
| `401` | Invalid anonymous compatibility credential or missing/invalid/expired local session. |
| `404` | Unknown route or datacenter. |
| `405` | Wrong method on a known route, with `Allow`. |
| `413` | Form exceeds 8 KiB. |
| `415` | POST content type is not form encoded. |
| `501` | Explicitly unsupported service surface, including advertised auxiliary URLs, known non-auth locators, device registration, and user config. |
| `503` | Local in-memory session capacity reached. |

The service does not implement `/configs/users/me`, `/devices/mydevice`, profile writes/deletes, assets, farm storage/synchronization, transactions, ads, purchases, legal text, or full game startup. Unknown routes never receive a fake success, and no request is forwarded remotely.

Each completed request logs exactly method, a sanitized route-template path, and status. Logs omit raw client IDs, query strings, form values, credentials, tokens, and device/country data. Do not add raw request or token logging during emulator work.

The HTTP tests exercise real routes, all ten URL keys, exact plaintext locators, anonymous derivation, signed sessions and expiry, own-profile identity isolation, policy boundaries, validation failures, malformed forms, unsupported routes, and log redaction. They do not prove Android/DEX integration, which is a separate emulator task.
