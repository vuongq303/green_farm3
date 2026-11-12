import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

/**
 * Merge D8-compiled game-core classes with original, unconverted APK bytecode.
 *
 * Usage: MergeCoreDex original.apk core-only.apk output.dex [report.json]
 * The caller pins the original APK hash. No SDK/wrapper/runtime class is passed
 * through dex2jar here. Pool indices and DEX layout necessarily change; class
 * semantics are checked by independently canonicalizing the retained and overlay
 * ClassDefs before and after writing the merged DEX.
 */
public final class MergeCoreDex {
    private static final String CORE = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/";
    private static final List<String> R_FAMILIES = Arrays.asList(
            "Lcom/gameloft/android/ANMP/GloftGF2F/R",
            "Lcom/facebook/R",
            "Lcom/google/android/gms/ads/R",
            "Lcom/google/android/gms/ads/impl/R",
            "Lcom/google/firebase/messaging/R");
    private static final Set<String> REQUIRED_CORE = new HashSet<>(Arrays.asList(
            CORE + "GLLib;", CORE + "ci;", CORE + "GloftGF2F;"));
    private static final Set<String> ORIGINAL_STREAMS = new HashSet<>(Arrays.asList(
            CORE + "l;", CORE + "g;"));

    private MergeCoreDex() {}

    private static final class ApkDex {
        final Map<String, ClassDef> classes = new TreeMap<>();
        final List<String> entries = new ArrayList<>();
        int version;
    }

    private static boolean isProtectedStream(String descriptor) {
        return ORIGINAL_STREAMS.contains(descriptor)
                || descriptor.startsWith(CORE + "l$")
                || descriptor.startsWith(CORE + "g$");
    }

    private static boolean allowedOverlay(String descriptor) {
        if (isProtectedStream(descriptor)) return false;
        if (descriptor.startsWith(CORE) && descriptor.endsWith(";")) return true;
        for (String family : R_FAMILIES) {
            if (descriptor.equals(family + ";")
                    || (descriptor.startsWith(family + "$") && descriptor.endsWith(";"))) return true;
        }
        return false;
    }

    private static ApkDex readApk(Path apk) throws IOException {
        ApkDex result = new ApkDex();
        try (ZipFile archive = new ZipFile(apk.toFile())) {
            List<? extends ZipEntry> entries = Collections.list(archive.entries());
            entries.sort(Comparator.comparing(ZipEntry::getName));
            Set<String> seenEntries = new HashSet<>();
            boolean primary = false;
            for (ZipEntry entry : entries) {
                String name = entry.getName();
                if (!name.matches("classes(?:[2-9]|[1-9][0-9]+)?\\.dex")) continue;
                if (!seenEntries.add(name)) throw new IOException("Duplicate DEX ZIP entry: " + name);
                if (entry.isDirectory()) throw new IOException("DEX ZIP entry is a directory: " + name);
                primary |= name.equals("classes.dex");
                byte[] bytes;
                try (InputStream stream = archive.getInputStream(entry)) { bytes = stream.readAllBytes(); }
                if (bytes.length < 112 || bytes[0] != 'd' || bytes[1] != 'e'
                        || bytes[2] != 'x' || bytes[3] != '\n' || bytes[7] != 0) {
                    throw new IOException("Not a standard DEX file: " + apk + "!" + name);
                }
                int version;
                try { version = Integer.parseInt(new String(bytes, 4, 3, StandardCharsets.US_ASCII)); }
                catch (NumberFormatException failure) { throw new IOException("Invalid DEX version in " + name, failure); }
                Opcodes opcodes = Opcodes.forDexVersion(version);
                if (opcodes == null) throw new IOException("Unsupported DEX version: " + version);
                DexBackedDexFile dex = new DexBackedDexFile(opcodes, bytes);
                int count = 0;
                for (ClassDef definition : dex.getClasses()) {
                    if (result.classes.putIfAbsent(definition.getType(), definition) != null) {
                        throw new IOException("Duplicate class definition in " + apk + ": " + definition.getType());
                    }
                    count++;
                }
                if (count == 0) throw new IOException("Empty DEX class-definition set: " + apk + "!" + name);
                result.entries.add(name);
                result.version = Math.max(result.version, version);
            }
            if (!primary) throw new IOException("APK has no classes.dex: " + apk);
        }
        return result;
    }

    private static byte[] serialize(Collection<? extends ClassDef> definitions, Opcodes opcodes) throws IOException {
        DexPool pool = new DexPool(opcodes);
        // A fixed descriptor order makes both independent semantic checks reproducible.
        List<ClassDef> ordered = new ArrayList<>(definitions);
        ordered.sort(Comparator.comparing(ClassDef::getType));
        for (ClassDef definition : ordered) pool.internClass(definition);
        if (pool.hasOverflowed()) {
            throw new IOException("Single-DEX reference limit exceeded: methods=" + pool.getMethodReferences().size()
                    + ", fields=" + pool.getFieldReferences().size() + ", types=" + pool.getTypeReferences().size()
                    + ". Refusing to change the original Android16 multidex requirements.");
        }
        MemoryDataStore data = new MemoryDataStore();
        try {
            pool.writeTo(data);
            return data.getData();
        } finally { data.close(); }
    }

    private static String sha256(byte[] bytes) throws Exception {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(bytes);
        StringBuilder text = new StringBuilder(64);
        for (byte value : hash) text.append(String.format("%02x", value & 255));
        return text.toString();
    }

    private static String quote(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (char character : value.toCharArray()) {
            switch (character) {
                case '\\': result.append("\\\\"); break;
                case '"': result.append("\\\""); break;
                case '\n': result.append("\\n"); break;
                case '\r': result.append("\\r"); break;
                case '\t': result.append("\\t"); break;
                default:
                    if (character < 32) result.append(String.format("\\u%04x", (int) character));
                    else result.append(character);
            }
        }
        return result.append('"').toString();
    }

    private static String jsonArray(Collection<String> values) {
        List<String> quoted = new ArrayList<>();
        for (String value : values) quoted.add(quote(value));
        return "[" + String.join(", ", quoted) + "]";
    }

    private static void writeAtomic(Path destination, byte[] data) throws IOException {
        Path absolute = destination.toAbsolutePath();
        Files.createDirectories(absolute.getParent());
        Path temporary = Files.createTempFile(absolute.getParent(), ".core-dex-", ".tmp");
        try {
            Files.write(temporary, data);
            try { Files.move(temporary, absolute, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
            catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temporary, absolute, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally { Files.deleteIfExists(temporary); }
    }

    private static boolean sameFile(Path left, Path right) throws IOException {
        return left.equals(right) || (Files.exists(left) && Files.exists(right) && Files.isSameFile(left, right));
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException("Usage: MergeCoreDex original.apk core-only.apk output.dex [report.json]");
        }
        Path originalApk = Paths.get(args[0]).toRealPath();
        Path overlayApk = Paths.get(args[1]).toRealPath();
        Path output = Paths.get(args[2]).toAbsolutePath().normalize();
        Path report = args.length == 4 ? Paths.get(args[3]).toAbsolutePath().normalize()
                : output.resolveSibling(output.getFileName() + ".json");
        if (sameFile(output, originalApk) || sameFile(output, overlayApk)
                || sameFile(report, originalApk) || sameFile(report, overlayApk) || sameFile(report, output)) {
            throw new IllegalArgumentException("Inputs, output DEX and report must be different files");
        }
        ApkDex original = readApk(originalApk);
        ApkDex overlay = readApk(overlayApk);
        if (!original.classes.keySet().containsAll(ORIGINAL_STREAMS)) {
            throw new IOException("Original APK is missing protected stream class definitions: " + ORIGINAL_STREAMS);
        }
        if (!original.classes.keySet().containsAll(REQUIRED_CORE)
                || !overlay.classes.keySet().containsAll(REQUIRED_CORE)) {
            throw new IOException("Original/overlay APK must define every required game core: " + REQUIRED_CORE);
        }
        for (String descriptor : overlay.classes.keySet()) {
            if (!allowedOverlay(descriptor)) throw new IOException("Overlay class is outside whitelist or protected: " + descriptor);
        }
        if (overlay.version > original.version) {
            throw new IOException("Overlay DEX version " + overlay.version + " requires a newer runtime than original " + original.version);
        }
        Opcodes opcodes = Opcodes.forDexVersion(original.version);
        Map<String, ClassDef> retained = new TreeMap<>(original.classes);
        List<String> replaced = new ArrayList<>(), added = new ArrayList<>();
        for (String descriptor : overlay.classes.keySet()) {
            if (retained.remove(descriptor) == null) added.add(descriptor); else replaced.add(descriptor);
        }
        Map<String, ClassDef> merged = new TreeMap<>(retained);
        merged.putAll(overlay.classes);
        byte[] mergedBytes = serialize(merged.values(), opcodes);
        DexBackedDexFile written = new DexBackedDexFile(opcodes, mergedBytes);
        Map<String, ClassDef> actual = new TreeMap<>();
        for (ClassDef definition : written.getClasses()) {
            if (actual.putIfAbsent(definition.getType(), definition) != null) throw new IOException("Output has duplicate class definitions");
        }
        if (!actual.keySet().equals(merged.keySet())) throw new IOException("Output is missing or has unexpected class definitions");
        Map<String, ClassDef> actualRetained = new TreeMap<>(), actualOverlay = new TreeMap<>();
        for (String descriptor : retained.keySet()) actualRetained.put(descriptor, actual.get(descriptor));
        for (String descriptor : overlay.classes.keySet()) actualOverlay.put(descriptor, actual.get(descriptor));
        byte[] retainedBefore = serialize(retained.values(), opcodes);
        byte[] retainedAfter = serialize(actualRetained.values(), opcodes);
        if (!Arrays.equals(retainedBefore, retainedAfter)) {
            throw new IOException("Original non-overlay class semantics changed during DEX writing");
        }
        byte[] overlayBefore = serialize(overlay.classes.values(), opcodes);
        byte[] overlayAfter = serialize(actualOverlay.values(), opcodes);
        if (!Arrays.equals(overlayBefore, overlayAfter)) throw new IOException("Compiled overlay class semantics changed during DEX writing");
        String json = "{\n"
                + "  \"original_apk_sha256\": " + quote(sha256(Files.readAllBytes(originalApk))) + ",\n"
                + "  \"overlay_apk_sha256\": " + quote(sha256(Files.readAllBytes(overlayApk))) + ",\n"
                + "  \"output_dex_sha256\": " + quote(sha256(mergedBytes)) + ",\n"
                + "  \"dex_version\": " + original.version + ",\n"
                + "  \"original_dex_entries\": " + jsonArray(original.entries) + ",\n"
                + "  \"overlay_dex_entries\": " + jsonArray(overlay.entries) + ",\n"
                + "  \"original_class_count\": " + original.classes.size() + ",\n"
                + "  \"overlay_class_count\": " + overlay.classes.size() + ",\n"
                + "  \"retained_original_class_count\": " + retained.size() + ",\n"
                + "  \"output_class_count\": " + actual.size() + ",\n"
                + "  \"retained_original_canonical_sha256\": " + quote(sha256(retainedBefore)) + ",\n"
                + "  \"compiled_overlay_canonical_sha256\": " + quote(sha256(overlayBefore)) + ",\n"
                + "  \"original_non_overlay_semantics_verified\": true,\n"
                + "  \"compiled_overlay_semantics_verified\": true,\n"
                + "  \"protected_original_stream_classes\": " + jsonArray(new TreeSet<>(ORIGINAL_STREAMS)) + ",\n"
                + "  \"replaced_classes\": " + jsonArray(replaced) + ",\n"
                + "  \"added_classes\": " + jsonArray(added) + "\n}\n";
        // Verification completes before either output is published.
        writeAtomic(output, mergedBytes);
        writeAtomic(report, json.getBytes(StandardCharsets.UTF_8));
        System.out.print(json);
    }
}
