package greenfarm

import java.net.URI
import java.net.URISyntaxException
import java.util.Locale

/** The one build-time origin used by every migrated game backend client. */
final class BackendBaseUrl {
    private static final String HELP =
            'Set BACKEND_BASE_URL in the root gradle.properties to an HTTP(S) IPv4 URL ' +
            'or an HTTPS DNS hostname (for example, a Cloudflare Tunnel URL). ' +
            'Optional port; no credentials, path, query or fragment. ' +
            'Rebuild and reinstall the APK after changing it.'

    private BackendBaseUrl() {}

    static String normalize(String value) {
        String text = value == null ? '' : value.trim()
        if (!text || text.indexOf('\\') >= 0 || text.find(/[\s\p{Cntrl}]/)) {
            throw invalid()
        }

        URI uri
        try {
            uri = new URI(text)
        } catch (URISyntaxException ignored) {
            throw invalid()
        }

        String scheme = uri.scheme == null ? '' : uri.scheme.toLowerCase(Locale.ROOT)
        String host = uri.host == null ? null : uri.host.toLowerCase(Locale.ROOT)

        // An origin only: do not allow credentials, paths, query strings,
        // fragments, IPv6 literals or escaped/ambiguous authority content.
        if (!(scheme in ['http', 'https']) || host == null ||
                uri.rawUserInfo != null ||
                uri.rawQuery != null || uri.rawFragment != null ||
                !(uri.rawPath in ['', '/']) ||
                !(uri.rawAuthority ==~ /[A-Za-z0-9.-]+(?::[0-9]{1,5})?/) ||
                uri.port == 0 || uri.port > 65535 ||
                (uri.rawAuthority.contains(':') && uri.port == -1)) {
            throw invalid()
        }

        if (host ==~ /[0-9.]+/) {
            // Preserve the project's existing IPv4 validation for LAN testing.
            if (!isAllowedIpv4(host)) throw invalid()
        } else {
            // Public DNS names are allowed only with HTTPS. This supports
            // trycloudflare.com and named tunnels without permitting HTTP DNS.
            if (scheme != 'https' || !isValidHttpsHostname(host)) throw invalid()
        }

        return scheme + '://' + host + (uri.port == -1 ? '' : ':' + uri.port) + '/'
    }

    private static boolean isAllowedIpv4(String host) {
        List<String> parts = host.split('\\.').toList()
        if (parts.size() != 4 || parts.any { !(it ==~ /0|[1-9][0-9]{0,2}/) }) {
            return false
        }

        List<Integer> octets = parts.collect { Integer.parseInt(it) }
        return !(octets.any { it > 255 } || octets[0] == 0 || octets[0] == 127 ||
                octets[0] >= 224 || (octets[0] == 169 && octets[1] == 254) ||
                host == '10.0.2.2')
    }

    private static boolean isValidHttpsHostname(String host) {
        // No localhost, mDNS or other local-only hostnames.
        if (host.length() > 253 || host.endsWith('.local') ||
                host.endsWith('.localhost') || host.endsWith('.internal')) {
            return false
        }

        List<String> labels = host.split('\\.').toList()
        if (labels.size() < 2 ||
                labels.any { !(it ==~ /[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?/) }) {
            return false
        }
        // A numeric final label is not a public DNS TLD.
        return !(labels.last() ==~ /[0-9]+/)
    }

    private static IllegalArgumentException invalid() {
        // Do not echo a malformed value: it could contain credentials.
        new IllegalArgumentException('Invalid or missing BACKEND_BASE_URL. ' + HELP)
    }
}
