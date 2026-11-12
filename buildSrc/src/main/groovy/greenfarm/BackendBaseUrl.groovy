package greenfarm

import java.net.URI
import java.net.URISyntaxException

/** The one build-time origin used by every migrated game backend client. */
final class BackendBaseUrl {
    private static final String HELP =
            'Set BACKEND_BASE_URL in the root gradle.properties to the backend machine IPv4 URL ' +
            '(HTTP or HTTPS, optional port, no credentials/path/query/fragment). ' +
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
        String host = uri.host
        if (!(scheme in ['http', 'https']) || host == null || uri.rawUserInfo != null ||
                uri.rawQuery != null || uri.rawFragment != null ||
                !(uri.rawPath in ['', '/']) ||
                !(uri.rawAuthority ==~ /[0-9]+(?:\.[0-9]+){3}(?::[0-9]+)?/) ||
                uri.port == 0 || uri.port > 65535) {
            throw invalid()
        }
        List<String> parts = host.split('\\.').toList()
        if (parts.size() != 4 || parts.any { !(it ==~ /0|[1-9][0-9]{0,2}/) }) {
            throw invalid()
        }
        List<Integer> octets = parts.collect { Integer.parseInt(it) }
        if (octets.any { it > 255 } || octets[0] == 0 || octets[0] == 127 ||
                octets[0] >= 224 || (octets[0] == 169 && octets[1] == 254) ||
                host == '10.0.2.2') {
            throw invalid()
        }
        // Never use hostname resolution: the app must address the configured
        // machine, rather than DNS, localhost, or the emulator gateway alias.
        return scheme + '://' + host + (uri.port == -1 ? '' : ':' + uri.port) + '/'
    }

    private static IllegalArgumentException invalid() {
        // Do not echo a malformed value: it could contain credentials.
        new IllegalArgumentException('Invalid or missing BACKEND_BASE_URL. ' + HELP)
    }
}
