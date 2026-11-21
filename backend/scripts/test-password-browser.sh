#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

# Use an operator-supplied, trusted localhost certificate, or create a one-day
# fixture and trust only its exact key in the disposable Chromium process.
# Neither route modifies system trust or application HTTPS configuration.
if [[ -z "${BROWSER_TEST_TLS_CERT:-}${BROWSER_TEST_TLS_KEY:-}${BROWSER_TEST_TLS_CA:-}" ]]; then
  fixture_dir=$(mktemp -d "${TMPDIR:-/tmp}/greenfarm-browser-XXXXXX")
  trap 'rm -rf "$fixture_dir"' EXIT
  umask 077
  openssl req -x509 -newkey rsa:2048 -nodes -days 1 \
    -subj /CN=localhost -addext 'subjectAltName=DNS:localhost,IP:127.0.0.1' \
    -keyout "$fixture_dir/key.pem" -out "$fixture_dir/cert.pem" >/dev/null 2>&1
  export BROWSER_TEST_TLS_KEY="$fixture_dir/key.pem"
  export BROWSER_TEST_TLS_CERT="$fixture_dir/cert.pem"
  export BROWSER_TEST_TLS_CA="$fixture_dir/cert.pem"
  export BROWSER_TEST_PIN_LOCAL_CERT=1
fi
if [[ -z "${CHROMIUM_EXECUTABLE_PATH:-}" ]] && command -v chromium >/dev/null; then
  export CHROMIUM_EXECUTABLE_PATH
  CHROMIUM_EXECUTABLE_PATH=$(command -v chromium)
fi
if [[ $# -eq 0 ]]; then
  set -- test/password-browser.integration.mjs test/giftcode-browser.integration.mjs
fi
node --test --test-concurrency=1 "$@"
