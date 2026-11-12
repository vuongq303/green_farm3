import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodImplementation;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction21c;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction31c;
import org.jf.dexlib2.immutable.reference.ImmutableStringReference;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

/** Local-backend opt-in: change one reviewed SDK endpoint literal, with read-back proofs. */
public final class PatchBackendEndpoint {
    static final String OWNER = "Lcom/gameloft/a/b/a;";
    static final String METHOD = "aL(Ljava/lang/String;)Ljava/lang/String;";
    static final String ORIGINAL = "https://eve.gameloft.com/";
    private PatchBackendEndpoint() {}

    static String validateUrl(String value) throws Exception {
        URI uri = new URI(value);
        String scheme = uri.getScheme(), host = uri.getHost();
        if (scheme == null || !(scheme.equals("http") || scheme.equals("https"))
                || host == null || uri.getRawUserInfo() != null || uri.getRawQuery() != null
                || uri.getRawFragment() != null || uri.getPort() == 0 || uri.getPort() > 65535
                || !value.endsWith("/") || value.indexOf('\\') >= 0 || value.equals(ORIGINAL)) {
            throw new IllegalArgumentException("Expected a different absolute HTTP(S) base URL ending in /, without userinfo, query or fragment");
        }
        boolean loopback = host.equalsIgnoreCase("localhost") || host.equals("10.0.2.2")
                || host.equals("[::1]") || host.equals("[0:0:0:0:0:0:0:1]");
        if (host.matches("127(?:\\.[0-9]{1,3}){3}")) {
            loopback = true;
            for (String part : host.split("\\.")) if (Integer.parseInt(part) > 255) loopback = false;
        }
        if (scheme.equals("http") && !loopback) {
            throw new IllegalArgumentException("Cleartext HTTP is restricted to localhost, loopback literals or Android emulator 10.0.2.2");
        }
        return value;
    }

    private static boolean target(Method method) {
        return method.getDefiningClass().equals(OWNER) && method.getName().equals("aL")
                && method.getReturnType().equals("Ljava/lang/String;")
                && method.getParameterTypes().equals(Collections.singletonList("Ljava/lang/String;"));
    }

    private static String literal(Instruction instruction) {
        if ((instruction.getOpcode() == Opcode.CONST_STRING || instruction.getOpcode() == Opcode.CONST_STRING_JUMBO)
                && instruction instanceof ReferenceInstruction
                && ((ReferenceInstruction) instruction).getReference() instanceof StringReference) {
            return ((StringReference) ((ReferenceInstruction) instruction).getReference()).getString();
        }
        return null;
    }

    private static ClassDef replace(ClassDef definition, String before, String after) throws IOException {
        int methods = 0, literals = 0, ownerLiterals = 0;
        List<Method> changed = new ArrayList<>();
        for (Method method : definition.getMethods()) {
            MethodImplementation implementation = method.getImplementation();
            if (implementation != null) {
                for (Instruction instruction : implementation.getInstructions()) {
                    if (before.equals(literal(instruction))) ownerLiterals++;
                }
            }
            if (!target(method)) { changed.add(method); continue; }
            methods++;
            if (method.getAccessFlags() != (AccessFlags.PRIVATE.getValue() | AccessFlags.STATIC.getValue())
                    || implementation == null) throw new IOException("Target must be the original private static implemented method");
            List<Instruction> instructions = new ArrayList<>();
            for (Instruction instruction : implementation.getInstructions()) {
                if (before.equals(literal(instruction))) {
                    literals++;
                    int register = ((OneRegisterInstruction) instruction).getRegisterA();
                    ImmutableStringReference reference = new ImmutableStringReference(after);
                    instruction = instruction.getOpcode() == Opcode.CONST_STRING_JUMBO
                            ? new ImmutableInstruction31c(Opcode.CONST_STRING_JUMBO, register, reference)
                            : new ImmutableInstruction21c(Opcode.CONST_STRING, register, reference);
                }
                instructions.add(instruction);
            }
            changed.add(new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(),
                    method.getReturnType(), method.getAccessFlags(), method.getAnnotations(), method.getHiddenApiRestrictions(),
                    new ImmutableMethodImplementation(implementation.getRegisterCount(), instructions,
                            implementation.getTryBlocks(), implementation.getDebugItems())));
        }
        if (methods != 1 || literals != 1 || ownerLiterals != 1) {
            throw new IOException("Expected exactly one target method and one endpoint literal in that method/owner; found "
                    + methods + "/" + literals + "/" + ownerLiterals);
        }
        return new ImmutableClassDef(definition.getType(), definition.getAccessFlags(), definition.getSuperclass(),
                definition.getInterfaces(), definition.getSourceFile(), definition.getAnnotations(), definition.getFields(), changed);
    }

    private static byte[] serialize(Collection<? extends ClassDef> definitions, Opcodes opcodes) throws IOException {
        DexPool pool = new DexPool(opcodes);
        Map<String, ClassDef> ordered = new TreeMap<>();
        for (ClassDef definition : definitions) ordered.put(definition.getType(), definition);
        for (ClassDef definition : ordered.values()) pool.internClass(definition);
        if (pool.hasOverflowed()) throw new IOException("Single-DEX reference limit exceeded");
        MemoryDataStore memory = new MemoryDataStore();
        try { pool.writeTo(memory); return memory.getData(); }
        finally { memory.close(); }
    }

    private static Map<String, ClassDef> classes(byte[] bytes, Opcodes opcodes) throws IOException {
        Map<String, ClassDef> result = new TreeMap<>();
        for (ClassDef definition : new DexBackedDexFile(opcodes, bytes).getClasses()) {
            if (result.put(definition.getType(), definition) != null) throw new IOException("Duplicate class definition");
        }
        if (result.isEmpty()) throw new IOException("Empty DEX");
        return result;
    }

    private static String sha256(byte[] bytes) throws Exception {
        StringBuilder result = new StringBuilder();
        for (byte value : MessageDigest.getInstance("SHA-256").digest(bytes)) result.append(String.format("%02x", value & 255));
        return result.toString();
    }

    private static String quote(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            if (c == '\\' || c == '"') result.append('\\').append(c);
            else if (c < 32) result.append(String.format("\\u%04x", (int) c));
            else result.append(c);
        }
        return result.append('"').toString();
    }

    private static boolean sameFile(Path left, Path right) throws IOException {
        return left.equals(right) || (Files.exists(left) && Files.exists(right) && Files.isSameFile(left, right));
    }

    private static Path stage(Path destination, byte[] bytes) throws IOException {
        Files.createDirectories(destination.getParent());
        Path temporary = Files.createTempFile(destination.getParent(), ".backend-endpoint-", ".tmp");
        try { Files.write(temporary, bytes); return temporary; }
        catch (IOException failure) { Files.deleteIfExists(temporary); throw failure; }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("Usage: PatchBackendEndpoint input.dex output.dex baseURL");
        String replacement = validateUrl(args[2]);
        Path input = Paths.get(args[0]).toRealPath();
        Path output = Paths.get(args[1]).toAbsolutePath().normalize();
        Path report = output.resolveSibling(output.getFileName() + ".json");
        if (sameFile(input, output) || sameFile(input, report) || sameFile(output, report)) {
            throw new IllegalArgumentException("Input DEX, output DEX and report must be different files");
        }
        byte[] originalBytes = Files.readAllBytes(input);
        if (originalBytes.length < 112 || originalBytes[0] != 'd' || originalBytes[1] != 'e'
                || originalBytes[2] != 'x' || originalBytes[3] != '\n' || originalBytes[7] != 0) throw new IOException("Expected standard DEX");
        int version = Integer.parseInt(new String(originalBytes, 4, 3, StandardCharsets.US_ASCII));
        Opcodes opcodes = Opcodes.forDexVersion(version);
        if (opcodes == null) throw new IOException("Unsupported DEX version");
        Map<String, ClassDef> original = classes(originalBytes, opcodes);
        ClassDef owner = original.get(OWNER);
        if (owner == null) throw new IOException("Missing target class " + OWNER);
        Map<String, ClassDef> modified = new TreeMap<>(original);
        modified.put(OWNER, replace(owner, ORIGINAL, replacement));
        byte[] outputBytes = serialize(modified.values(), opcodes);
        Map<String, ClassDef> actual = classes(outputBytes, opcodes);
        if (!original.keySet().equals(actual.keySet())) throw new IOException("Class set changed");
        ClassDef restored = replace(actual.get(OWNER), replacement, ORIGINAL);
        if (!Arrays.equals(serialize(Collections.singletonList(owner), opcodes),
                serialize(Collections.singletonList(restored), opcodes))) throw new IOException("Reverse-patched target semantics changed");
        Map<String, ClassDef> beforeOthers = new TreeMap<>(original), afterOthers = new TreeMap<>(actual);
        beforeOthers.remove(OWNER); afterOthers.remove(OWNER);
        byte[] beforeCanonical = serialize(beforeOthers.values(), opcodes);
        if (!Arrays.equals(beforeCanonical, serialize(afterOthers.values(), opcodes))) throw new IOException("Other class semantics changed");
        String json = "{\n"
                + "  \"input_dex_sha256\": " + quote(sha256(originalBytes)) + ",\n"
                + "  \"output_dex_sha256\": " + quote(sha256(outputBytes)) + ",\n"
                + "  \"target_class\": " + quote(OWNER) + ",\n"
                + "  \"target_method\": " + quote(METHOD) + ",\n"
                + "  \"original_url\": " + quote(ORIGINAL) + ",\n"
                + "  \"replacement_url\": " + quote(replacement) + ",\n"
                + "  \"replacement_count\": 1,\n"
                + "  \"unchanged_other_classes_verified\": true,\n"
                + "  \"reverse_patch_semantics_verified\": true,\n"
                + "  \"unchanged_classes_canonical_sha256\": " + quote(sha256(beforeCanonical)) + "\n}\n";
        // Publish only after read-back verification. Both files use same-directory atomic rename.
        Path stagedOutput = stage(output, outputBytes), stagedReport = null;
        try {
            stagedReport = stage(report, json.getBytes(StandardCharsets.UTF_8));
            Files.move(stagedOutput, output, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            Files.move(stagedReport, report, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(stagedOutput);
            if (stagedReport != null) Files.deleteIfExists(stagedReport);
        }
        System.out.print(json);
    }
}
