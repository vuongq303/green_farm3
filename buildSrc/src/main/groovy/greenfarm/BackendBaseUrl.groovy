package greenfarm

import java.net.URI
import java.net.URISyntaxException
import java.net.IDN
import java.util.Locale

/** The one build-time origin used by every migrated game backend client. */
final class BackendBaseUrl {
    private static final String HELP =
            'Set BACKEND_BASE_URL in the root gradle.properties to an HTTP(S) origin ' +
            '(DNS name, localhost, IPv4 or bracketed IPv6). ' +
            'Optional port; no credentials, path, query or fragment. ' +
            'Rebuild and reinstall the APK after changing it.'

    private BackendBaseUrl() {}

    static String normalize(String value) {
        String text = value == null ? '' : value
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
        String authority = uri.rawAuthority
        if (!(scheme in ['http', 'https']) || authority == null ||
                uri.rawUserInfo != null ||
                uri.rawQuery != null || uri.rawFragment != null ||
                !(uri.rawPath in ['', '/']) ||
                authority.contains('%') || authority.contains('@')) {
            throw invalid()
        }
        String rawHost
        String portText = null
        String host
        if (authority.startsWith('[')) {
            int end = authority.indexOf(']')
            if (end < 0 || uri.host == null) throw invalid()
            rawHost = authority.substring(1, end)
            String suffix = authority.substring(end + 1)
            if (suffix) {
                if (!suffix.startsWith(':')) throw invalid()
                portText = suffix.substring(1)
            }
            host = '[' + ipv6(rawHost) + ']'
        } else {
            int colon = authority.indexOf(':')
            if (colon >= 0) {
                if (colon != authority.lastIndexOf(':')) throw invalid()
                rawHost = authority.substring(0, colon)
                portText = authority.substring(colon + 1)
            } else rawHost = authority
            host = hostname(rawHost)
        }
        if (portText != null && (!(portText ==~ /[0-9]{1,5}/) ||
                Integer.parseInt(portText) < 1 || Integer.parseInt(portText) > 65535)) throw invalid()
        return scheme + '://' + host + (portText == null ? '' : ':' + Integer.parseInt(portText)) + '/'
    }

    private static List<Integer> ipv4(String host) {
        List<String> parts = host.split('\\.', -1).toList()
        if (parts.size() != 4 || parts.any { !(it ==~ /0|[1-9][0-9]{0,2}/) }) {
            throw invalid()
        }
        List<Integer> octets = parts.collect { Integer.parseInt(it) }
        if (octets.any { it > 255 }) throw invalid()
        return octets
    }

    private static String hostname(String value) {
        String host
        try { host = IDN.toASCII(value, IDN.USE_STD3_ASCII_RULES).toLowerCase(Locale.ROOT) }
        catch (IllegalArgumentException ignored) { throw invalid() }
        if (!host || host.length() > 253) throw invalid()
        if (host ==~ /[0-9.]+/) {
            if (host != value) throw invalid()
            ipv4(host)
            return host
        }
        String name = host.endsWith('.') ? host.substring(0, host.length() - 1) : host
        List<String> labels = name.split('\\.', -1).toList()
        if (labels.any { !(it ==~ /[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?/) } ||
                labels.last() ==~ /[0-9]+|0x[0-9a-f]+/) throw invalid()
        return host
    }

    /** Literal parsing only: never resolve a configured hostname while building. */
    private static String ipv6(String value) {
        String text = value.toLowerCase(Locale.ROOT)
        if (text.contains('.')) {
            int last = text.lastIndexOf(':')
            if (last < 0) throw invalid()
            List<Integer> octets = ipv4(text.substring(last + 1))
            text = text.substring(0, last + 1) + Integer.toHexString(octets[0] * 256 + octets[1]) +
                    ':' + Integer.toHexString(octets[2] * 256 + octets[3])
        }
        List<String> sides = text.split('::', -1).toList()
        if (sides.size() > 2) throw invalid()
        List<String> left = sides[0] ? sides[0].split(':', -1).toList() : []
        List<String> right = sides.size() == 2 && sides[1] ? sides[1].split(':', -1).toList() : []
        List<String> all = left + right
        if (all.any { !(it ==~ /[0-9a-f]{1,4}/) } ||
                (sides.size() == 1 ? all.size() != 8 : all.size() >= 8)) throw invalid()
        List<Integer> words = left.collect { Integer.parseInt(it, 16) } +
                Collections.nCopies(8 - all.size(), 0) + right.collect { Integer.parseInt(it, 16) }
        int start = -1, length = 0
        for (int i = 0; i < 8;) {
            if (words[i] != 0) { i++; continue }
            int j = i
            while (j < 8 && words[j] == 0) j++
            if (j - i > length && j - i >= 2) { start = i; length = j - i }
            i = j
        }
        List<String> hex = words.collect { Integer.toHexString(it) }
        if (start < 0) return hex.join(':')
        return hex.subList(0, start).join(':') + '::' + hex.subList(start + length, 8).join(':')
    }

    private static IllegalArgumentException invalid() {
        // Do not echo a malformed value: it could contain credentials.
        new IllegalArgumentException('Invalid or missing BACKEND_BASE_URL. ' + HELP)
    }
}
