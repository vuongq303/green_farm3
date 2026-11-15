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
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.builder.MutableMethodImplementation;
import org.jf.dexlib2.builder.instruction.BuilderInstruction10x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
import org.jf.dexlib2.builder.BuilderInstruction;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.OffsetInstruction;
import org.jf.dexlib2.iface.instruction.VariableRegisterInstruction;
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodImplementation;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction21c;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction31c;
import org.jf.dexlib2.immutable.reference.ImmutableStringReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

/** Redirect the two reviewed SDK/game bootstrap constructors, with read-back proofs. */
public final class PatchBackendEndpoint {
    static final String OWNER = "Lcom/gameloft/a/b/a;";
    static final String CORE_OWNER = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/s;";
    static final Map<String, Integer> TARGETS = new TreeMap<>();
    static { TARGETS.put(OWNER, 1); TARGETS.put(CORE_OWNER, 2); }
    static final String METHOD = "aL(Ljava/lang/String;)Ljava/lang/String;";
    static final String ORIGINAL = "https://eve.gameloft.com/";
    static final String STARTUP_OWNER = "Lcom/gameloft/android/wrapper/au;";
    static final String LOCAL_MODE = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LocalDevelopmentMode;";
    static final String HTTP_OWNER = "Lcom/gameloft/a/b/e;";
    static final String NETWORK_HELPER = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/NetworkDiagnostics;";

    private static final class NetworkHook {
        final String owner, name, result, helperName;
        final List<String> parameters, helperParameters;

        NetworkHook(String owner, String name, List<String> parameters, String result,
                    String helperName, List<String> helperParameters) {
            this.owner = owner; this.name = name; this.parameters = parameters;
            this.result = result; this.helperName = helperName; this.helperParameters = helperParameters;
        }

        boolean matches(MethodReference reference, boolean reverse) {
            return reference.getDefiningClass().equals(reverse ? NETWORK_HELPER : owner)
                    && reference.getName().equals(reverse ? helperName : name)
                    && reference.getParameterTypes().equals(reverse ? helperParameters : parameters)
                    && reference.getReturnType().equals(result);
        }

        MethodReference reference(boolean reverse) {
            return new ImmutableMethodReference(reverse ? owner : NETWORK_HELPER,
                    reverse ? name : helperName, reverse ? parameters : helperParameters, result);
        }
    }

    // Wrappers consume the existing receiver as their first argument. No
    // register, move-result, branch or exception handler needs to be rewritten.
    private static final List<NetworkHook> NETWORK_HOOKS = Arrays.asList(
            new NetworkHook("Ljava/net/URL;", "openConnection", Collections.emptyList(),
                    "Ljava/net/URLConnection;", "openConnection", Collections.singletonList("Ljava/net/URL;")),
            new NetworkHook("Ljava/net/URL;", "openConnection", Collections.singletonList("Ljava/net/Proxy;"),
                    "Ljava/net/URLConnection;", "openConnection", Arrays.asList("Ljava/net/URL;", "Ljava/net/Proxy;")),
            new NetworkHook("Ljava/net/HttpURLConnection;", "getResponseCode", Collections.emptyList(),
                    "I", "responseCode", Collections.singletonList("Ljava/net/HttpURLConnection;")),
            new NetworkHook("Ljava/lang/Exception;", "printStackTrace", Collections.emptyList(),
                    "V", "failure", Collections.singletonList("Ljava/lang/Throwable;")));
    private PatchBackendEndpoint() {}

    private static boolean sdkHttpRun(Method method) {
        return method.getDefiningClass().equals(HTTP_OWNER) && method.getName().equals("run")
                && method.getParameterTypes().isEmpty() && method.getReturnType().equals("V");
    }

    private static MethodReference methodReference(Instruction instruction) {
        if (!(instruction instanceof ReferenceInstruction)) return null;
        Object reference = ((ReferenceInstruction) instruction).getReference();
        return reference instanceof MethodReference ? (MethodReference) reference : null;
    }

    private static boolean beginReference(MethodReference reference) {
        return reference != null && reference.getDefiningClass().equals(NETWORK_HELPER)
                && reference.getName().equals("begin") && reference.getParameterTypes().isEmpty()
                && reference.getReturnType().equals("V");
    }

    private static BuilderInstruction replaceNetworkInvoke(Instruction instruction, NetworkHook hook,
                                                           boolean reverse) throws IOException {
        int count = ((VariableRegisterInstruction) instruction).getRegisterCount();
        if (count != hook.helperParameters.size()) throw new IOException("Unexpected SDK HTTP invoke register count");
        if (instruction.getOpcode() == (reverse ? Opcode.INVOKE_STATIC : Opcode.INVOKE_VIRTUAL)
                && instruction instanceof FiveRegisterInstruction) {
            FiveRegisterInstruction registers = (FiveRegisterInstruction) instruction;
            return new BuilderInstruction35c(reverse ? Opcode.INVOKE_VIRTUAL : Opcode.INVOKE_STATIC,
                    count, registers.getRegisterC(), registers.getRegisterD(), registers.getRegisterE(),
                    registers.getRegisterF(), registers.getRegisterG(), hook.reference(reverse));
        }
        if (instruction.getOpcode() == (reverse ? Opcode.INVOKE_STATIC_RANGE : Opcode.INVOKE_VIRTUAL_RANGE)
                && instruction instanceof RegisterRangeInstruction) {
            return new BuilderInstruction3rc(reverse ? Opcode.INVOKE_VIRTUAL_RANGE : Opcode.INVOKE_STATIC_RANGE,
                    ((RegisterRangeInstruction) instruction).getStartRegister(), count, hook.reference(reverse));
        }
        throw new IOException("Unexpected SDK HTTP invoke opcode or format");
    }

    /** Add diagnostics around the original SDK's four reviewed HTTP calls. */
    private static ClassDef instrumentNetwork(ClassDef definition, boolean reverse) throws IOException {
        int found = 0;
        List<Method> methods = new ArrayList<>();
        for (Method method : definition.getMethods()) {
            if (!sdkHttpRun(method)) { methods.add(method); continue; }
            found++;
            MethodImplementation implementation = method.getImplementation();
            if (!AccessFlags.PUBLIC.isSet(method.getAccessFlags()) || AccessFlags.STATIC.isSet(method.getAccessFlags())
                    || implementation == null || implementation.getRegisterCount() < 1) {
                throw new IOException("Unexpected SDK HTTP run method shape");
            }
            MutableMethodImplementation code = new MutableMethodImplementation(implementation);
            if (reverse) {
                Instruction first = code.getInstructions().get(0);
                if (first.getOpcode() != Opcode.INVOKE_STATIC || !beginReference(methodReference(first))
                        || ((VariableRegisterInstruction) first).getRegisterCount() != 0) {
                    throw new IOException("SDK network diagnostics entry is missing or malformed");
                }
                code.removeInstruction(0);
            }
            int[] counts = new int[NETWORK_HOOKS.size()];
            for (int index = 0; index < code.getInstructions().size(); index++) {
                Instruction instruction = code.getInstructions().get(index);
                MethodReference reference = methodReference(instruction);
                if (reference == null) continue;
                boolean matched = false;
                for (int hookIndex = 0; hookIndex < NETWORK_HOOKS.size(); hookIndex++) {
                    NetworkHook hook = NETWORK_HOOKS.get(hookIndex);
                    if (!hook.matches(reference, reverse)) continue;
                    counts[hookIndex]++;
                    code.replaceInstruction(index, replaceNetworkInvoke(instruction, hook, reverse));
                    matched = true;
                    break;
                }
                if (!matched && reference.getDefiningClass().equals(NETWORK_HELPER)) {
                    throw new IOException("Unexpected SDK network diagnostics call");
                }
            }
            for (int count : counts) if (count != 1) {
                throw new IOException("Expected exactly one SDK HTTP call for each network diagnostics hook");
            }
            if (!reverse) code.addInstruction(0, new BuilderInstruction35c(Opcode.INVOKE_STATIC,
                    0, 0, 0, 0, 0, 0, new ImmutableMethodReference(NETWORK_HELPER, "begin", Collections.emptyList(), "V")));
            methods.add(new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(),
                    method.getReturnType(), method.getAccessFlags(), method.getAnnotations(),
                    method.getHiddenApiRestrictions(), code));
        }
        if (found != 1) throw new IOException("Expected exactly one SDK HTTP run()V entry");
        return new ImmutableClassDef(definition.getType(), definition.getAccessFlags(), definition.getSuperclass(),
                definition.getInterfaces(), definition.getSourceFile(), definition.getAnnotations(), definition.getFields(), methods);
    }

    private static void requireNetworkHelper(Map<String, ClassDef> definitions) throws IOException {
        ClassDef helper = definitions.get(NETWORK_HELPER);
        if (helper == null) throw new IOException("The compiled network diagnostics helper is missing");
        List<MethodReference> expected = new ArrayList<>();
        expected.add(new ImmutableMethodReference(NETWORK_HELPER, "begin", Collections.emptyList(), "V"));
        for (NetworkHook hook : NETWORK_HOOKS) expected.add(hook.reference(false));
        for (MethodReference target : expected) {
            boolean found = false;
            for (Method method : helper.getMethods()) {
                if (method.getName().equals(target.getName()) && method.getReturnType().equals(target.getReturnType())
                        && method.getParameterTypes().equals(target.getParameterTypes()) && method.getImplementation() != null
                        && AccessFlags.PUBLIC.isSet(method.getAccessFlags()) && AccessFlags.STATIC.isSet(method.getAccessFlags())) {
                    found = true;
                    break;
                }
            }
            if (!found) throw new IOException("The compiled network diagnostics helper method is missing: " + target.getName());
        }
    }

    private static boolean startup(Method method) {
        return method.getDefiningClass().equals(STARTUP_OWNER) && method.getName().equals("Qh")
                && method.getParameterTypes().isEmpty() && method.getReturnType().equals("V");
    }

    /** Add a conditional early return; the complete SDK path remains after it. */
    private static ClassDef guardStartup(ClassDef definition, boolean reverse) throws IOException {
        int found = 0;
        List<Method> methods = new ArrayList<>();
        for (Method method : definition.getMethods()) {
            if (!startup(method)) { methods.add(method); continue; }
            found++;
            MethodImplementation implementation = method.getImplementation();
            if (method.getAccessFlags() != (AccessFlags.PUBLIC.getValue() | AccessFlags.STATIC.getValue())
                    || implementation == null || implementation.getRegisterCount() < 1) {
                throw new IOException("Unexpected SDK startup method shape");
            }
            MutableMethodImplementation code = new MutableMethodImplementation(implementation);
            if (reverse) {
                List<? extends Instruction> instructions = code.getInstructions();
                if (instructions.size() < 5 || instructions.get(0).getOpcode() != Opcode.INVOKE_STATIC
                        || instructions.get(1).getOpcode() != Opcode.MOVE_RESULT
                        || instructions.get(2).getOpcode() != Opcode.IF_EQZ
                        || instructions.get(3).getOpcode() != Opcode.RETURN_VOID) {
                    throw new IOException("SDK startup guard is missing or malformed");
                }
                MethodReference predicate = (MethodReference) ((ReferenceInstruction) instructions.get(0)).getReference();
                if (!predicate.getDefiningClass().equals(LOCAL_MODE) || !predicate.getName().equals("skipLegacyBackend")
                        || !predicate.getParameterTypes().isEmpty() || !predicate.getReturnType().equals("Z")
                        || ((VariableRegisterInstruction) instructions.get(0)).getRegisterCount() != 0
                        || ((OneRegisterInstruction) instructions.get(1)).getRegisterA() != 0
                        || ((OneRegisterInstruction) instructions.get(2)).getRegisterA() != 0
                        || ((OffsetInstruction) instructions.get(2)).getCodeOffset() != 3) {
                    throw new IOException("SDK startup guard does not preserve the false branch");
                }
                for (int index = 0; index < 4; index++) code.removeInstruction(0);
            } else {
                code.addInstruction(0, new BuilderInstruction35c(Opcode.INVOKE_STATIC, 0, 0, 0, 0, 0, 0,
                        new ImmutableMethodReference(LOCAL_MODE, "skipLegacyBackend", Collections.emptyList(), "Z")));
                code.addInstruction(1, new BuilderInstruction11x(Opcode.MOVE_RESULT, 0));
                // The return is outside the SDK's original try regions. Builder
                // labels preserve every original branch/debug/handler address.
                code.addInstruction(2, new BuilderInstruction10x(Opcode.RETURN_VOID));
                code.addInstruction(2, new BuilderInstruction21t(Opcode.IF_EQZ, 0, code.newLabelForIndex(3)));
            }
            methods.add(new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(),
                    method.getReturnType(), method.getAccessFlags(), method.getAnnotations(),
                    method.getHiddenApiRestrictions(), code));
        }
        if (found != 1) throw new IOException("Expected exactly one SDK Qh()V startup entry");
        return new ImmutableClassDef(definition.getType(), definition.getAccessFlags(), definition.getSuperclass(),
                definition.getInterfaces(), definition.getSourceFile(), definition.getAnnotations(), definition.getFields(), methods);
    }

    private static void requireLocalMode(Map<String, ClassDef> definitions) throws IOException {
        ClassDef helper = definitions.get(LOCAL_MODE);
        if (helper != null) for (Method method : helper.getMethods()) {
            if (method.getName().equals("skipLegacyBackend") && method.getParameterTypes().isEmpty()
                    && method.getReturnType().equals("Z") && method.getImplementation() != null
                    && AccessFlags.STATIC.isSet(method.getAccessFlags())
                    && AccessFlags.PUBLIC.isSet(method.getAccessFlags())) return;
        }
        throw new IOException("The compiled local development mode helper is missing");
    }

    static String validateUrl(String value) throws Exception {
        String message = "Set BACKEND_BASE_URL in root gradle.properties to an HTTP(S) IPv4 LAN origin, "
                + "for example http://<server-IPv4>:<port>/; no userinfo, path, query, fragment or loopback address";
        try {
            URI uri = new URI(value.trim());
            String scheme = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
            String host = uri.getHost();
            if (!(scheme.equals("http") || scheme.equals("https")) || host == null
                    || !host.matches("(?:0|[1-9][0-9]{0,2})(?:\\.(?:0|[1-9][0-9]{0,2})){3}")
                    || uri.getRawUserInfo() != null || uri.getRawQuery() != null || uri.getRawFragment() != null
                    || !(uri.getRawPath().isEmpty() || uri.getRawPath().equals("/"))
                    || uri.getPort() == 0 || uri.getPort() > 65535 || uri.getRawAuthority().endsWith(":")
                    || value.indexOf('\\') >= 0) {
                throw new IllegalArgumentException(message);
            }
            String[] parts = host.split("\\.");
            int[] octets = new int[4];
            for (int index = 0; index < 4; index++) {
                octets[index] = Integer.parseInt(parts[index]);
                if (octets[index] > 255) throw new IllegalArgumentException(message);
            }
            if (octets[0] == 0 || octets[0] == 127 || octets[0] >= 224
                    || (octets[0] == 169 && octets[1] == 254) || host.equals("10.0.2.2")) {
                throw new IllegalArgumentException(message);
            }
            return scheme + "://" + host + (uri.getPort() < 0 ? "" : ":" + uri.getPort()) + "/";
        } catch (Exception failure) {
            // A URI parser's exception may echo accidental userinfo/passwords.
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean target(Method method) {
        return TARGETS.containsKey(method.getDefiningClass()) && method.getName().equals("aL")
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
        int expected = TARGETS.get(definition.getType());
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
        if (methods != 1 || literals != expected || ownerLiterals != expected) {
            throw new IOException("Expected exactly one target method and " + expected + " endpoint literals in "
                    + definition.getType() + "; found " + methods + "/" + literals + "/" + ownerLiterals);
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
        if (args.length < 3 || args.length > 5) {
            throw new IllegalArgumentException("Usage: PatchBackendEndpoint input.dex output.dex baseURL [--guard-local-startup] [--network-diagnostics]");
        }
        boolean localStartupGuard = false, networkDiagnostics = false;
        for (int index = 3; index < args.length; index++) {
            if (args[index].equals("--guard-local-startup") && !localStartupGuard) localStartupGuard = true;
            else if (args[index].equals("--network-diagnostics") && !networkDiagnostics) networkDiagnostics = true;
            else throw new IllegalArgumentException("Unknown or duplicate patch option");
        }
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
        Map<String, ClassDef> modified = new TreeMap<>(original);
        for (String owner : TARGETS.keySet()) {
            if (!original.containsKey(owner)) throw new IOException("Missing target class " + owner);
            modified.put(owner, replace(original.get(owner), ORIGINAL, replacement));
        }
        if (localStartupGuard) {
            requireLocalMode(original);
            if (!original.containsKey(STARTUP_OWNER)) throw new IOException("Missing SDK startup owner");
            modified.put(STARTUP_OWNER, guardStartup(original.get(STARTUP_OWNER), false));
        }
        if (networkDiagnostics) {
            requireNetworkHelper(original);
            if (!original.containsKey(HTTP_OWNER)) throw new IOException("Missing SDK HTTP owner");
            modified.put(HTTP_OWNER, instrumentNetwork(original.get(HTTP_OWNER), false));
        }
        byte[] outputBytes = serialize(modified.values(), opcodes);
        Map<String, ClassDef> actual = classes(outputBytes, opcodes);
        if (!original.keySet().equals(actual.keySet())) throw new IOException("Class set changed");
        for (String owner : TARGETS.keySet()) {
            ClassDef restored = replace(actual.get(owner), replacement, ORIGINAL);
            if (!Arrays.equals(serialize(Collections.singletonList(original.get(owner)), opcodes),
                    serialize(Collections.singletonList(restored), opcodes))) {
                throw new IOException("Reverse-patched target semantics changed: " + owner);
            }
        }
        if (localStartupGuard) {
            ClassDef restored = guardStartup(actual.get(STARTUP_OWNER), true);
            if (!Arrays.equals(serialize(Collections.singletonList(original.get(STARTUP_OWNER)), opcodes),
                    serialize(Collections.singletonList(restored), opcodes))) {
                throw new IOException("Reverse-patched SDK startup semantics changed");
            }
        }
        if (networkDiagnostics) {
            ClassDef restored = instrumentNetwork(actual.get(HTTP_OWNER), true);
            if (!Arrays.equals(serialize(Collections.singletonList(original.get(HTTP_OWNER)), opcodes),
                    serialize(Collections.singletonList(restored), opcodes))) {
                throw new IOException("Reverse-patched SDK network diagnostics semantics changed");
            }
        }
        Map<String, ClassDef> beforeOthers = new TreeMap<>(original), afterOthers = new TreeMap<>(actual);
        for (String owner : TARGETS.keySet()) { beforeOthers.remove(owner); afterOthers.remove(owner); }
        if (localStartupGuard) { beforeOthers.remove(STARTUP_OWNER); afterOthers.remove(STARTUP_OWNER); }
        if (networkDiagnostics) { beforeOthers.remove(HTTP_OWNER); afterOthers.remove(HTTP_OWNER); }
        byte[] beforeCanonical = serialize(beforeOthers.values(), opcodes);
        if (!Arrays.equals(beforeCanonical, serialize(afterOthers.values(), opcodes))) throw new IOException("Other class semantics changed");
        String json = "{\n"
                + "  \"input_dex_sha256\": " + quote(sha256(originalBytes)) + ",\n"
                + "  \"output_dex_sha256\": " + quote(sha256(outputBytes)) + ",\n"
                + "  \"schema_version\": 2,\n"
                + "  \"target_classes\": [" + quote(OWNER) + ", " + quote(CORE_OWNER) + "],\n"
                + "  \"target_method\": " + quote(METHOD) + ",\n"
                + "  \"original_url\": " + quote(ORIGINAL) + ",\n"
                + "  \"replacement_url\": " + quote(replacement) + ",\n"
                + "  \"replacement_count\": 3,\n"
                + "  \"replacement_counts\": {" + quote(OWNER) + ": 1, " + quote(CORE_OWNER) + ": 2},\n"
                + "  \"unchanged_other_classes_verified\": true,\n"
                + "  \"reverse_patch_semantics_verified\": true,\n"
                + "  \"local_startup_guard\": " + (localStartupGuard
                    ? "{\"owner\":" + quote(STARTUP_OWNER) + ",\"method\":\"Qh()V\",\"predicate\":"
                        + quote(LOCAL_MODE + "->skipLegacyBackend()Z")
                        + ",\"original_false_branch_preserved\":true,\"reverse_patch_semantics_verified\":true}"
                    : "null") + ",\n"
                + "  \"network_diagnostics\": " + (networkDiagnostics
                    ? "{\"owner\":" + quote(HTTP_OWNER) + ",\"method\":\"run()V\",\"helper\":" + quote(NETWORK_HELPER)
                        + ",\"begin_calls\":1,\"hook_counts\":{\"open_connection\":1,\"open_connection_proxy\":1,\"response_code\":1,\"failure\":1}"
                        + ",\"reverse_patch_semantics_verified\":true}"
                    : "null") + ",\n"
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
