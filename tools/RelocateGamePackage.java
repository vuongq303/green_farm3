import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.debug.StartLocal;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.StringEncodedValue;
import org.jf.dexlib2.immutable.debug.ImmutableStartLocal;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.builder.MutableMethodImplementation;
import org.jf.dexlib2.builder.BuilderInstruction;
import org.jf.dexlib2.builder.BuilderOffsetInstruction;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction30t;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction21c;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction31c;
import org.jf.dexlib2.immutable.reference.ImmutableStringReference;
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
import org.jf.dexlib2.immutable.value.ImmutableStringEncodedValue;
import org.jf.dexlib2.rewriter.DebugItemRewriter;
import org.jf.dexlib2.rewriter.DexRewriter;
import org.jf.dexlib2.rewriter.EncodedValueRewriter;
import org.jf.dexlib2.rewriter.InstructionRewriter;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.RewriterModule;
import org.jf.dexlib2.rewriter.Rewriters;
import org.jf.dexlib2.rewriter.FieldReferenceRewriter;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

/**
 * Structurally relocate game-owned DEX identities, including references in SDKs.
 * No opcode, branch, value, resource ID or native payload is replaced. Literal
 * package identities (component names, actions, authorities and OBB paths) move
 * with the app. The migration package intentionally keeps plain identity strings
 * so it can address both old and new apps; descriptor/signature strings still
 * move. An inverse rewrite must reproduce the complete canonical input DEX.
 */
public final class RelocateGamePackage {
    public static final String OLD_PACKAGE = "com.gameloft.android.ANMP.GloftGF2F";
    public static final String NEW_PACKAGE = "com.quanhoang.greenfarm3";
    public static final String OLD_INTERNAL = OLD_PACKAGE.replace('.', '/');
    public static final String NEW_INTERNAL = NEW_PACKAGE.replace('.', '/');
    private RelocateGamePackage() {}

    private static String replaceNullable(String value, String before, String after) {
        return value == null ? null : value.replace(before, after);
    }

    public static String mapType(String value, boolean reverse) {
        return mapType(value, reverse, false);
    }
    private static String renameClassIdentity(String value, String before, String after) {
        if (value == null) return null;
        if (!value.contains(before)) return value;
        return Pattern.compile(Pattern.quote(before) + "(?=[$;.<]|$)").matcher(value)
                .replaceAll(java.util.regex.Matcher.quoteReplacement(after));
    }
    private static String mapClassNames(String value, boolean reverse) {
        String[] before = {"ci", "ch", "GLLib"};
        String[] after = {"FarmGameEngine", "FarmEntity", "GameRuntime"};
        for (int index = 0; index < before.length; index++) {
            String source = reverse ? after[index] : before[index];
            String target = reverse ? before[index] : after[index];
            value = renameClassIdentity(value, NEW_INTERNAL + "/S800x480/" + source,
                    NEW_INTERNAL + "/S800x480/" + target);
            value = renameClassIdentity(value, NEW_PACKAGE + ".S800x480." + source,
                    NEW_PACKAGE + ".S800x480." + target);
        }
        return value;
    }
    public static String mapType(String value, boolean reverse, boolean renameSymbols) {
        if (renameSymbols && reverse) value = mapClassNames(value, true);
        value = replaceNullable(value, "L" + (reverse ? NEW_INTERNAL : OLD_INTERNAL) + "/",
                "L" + (reverse ? OLD_INTERNAL : NEW_INTERNAL) + "/");
        return renameSymbols && !reverse ? mapClassNames(value, false) : value;
    }

    private static boolean migrationOwner(String owner) {
        return owner.startsWith("L" + OLD_INTERNAL + "/migration/")
                || owner.startsWith("L" + NEW_INTERNAL + "/migration/");
    }

    static String mapString(String value, boolean reverse, boolean migration) {
        return mapString(value, reverse, migration, false);
    }
    static String mapString(String value, boolean reverse, boolean migration, boolean renameSymbols) {
        if (value == null) return null;
        // Migration URIs and application IDs are data, not class identities.
        // Keep both endpoint identities stable under forward and inverse maps.
        if (migration && !value.contains("L" + OLD_INTERNAL + "/")
                && !value.contains("L" + NEW_INTERNAL + "/")) return value;
        if (renameSymbols && reverse) value = mapClassNames(value, true);
        value = value.replace(reverse ? NEW_INTERNAL : OLD_INTERNAL, reverse ? OLD_INTERNAL : NEW_INTERNAL)
                .replace(reverse ? NEW_PACKAGE : OLD_PACKAGE, reverse ? OLD_PACKAGE : NEW_PACKAGE);
        return renameSymbols && !reverse ? mapClassNames(value, false) : value;
    }

    public static ClassDef rewriteClass(ClassDef definition, final boolean reverse) {
        return rewriteClass(definition, reverse, false);
    }
    public static ClassDef rewriteClass(ClassDef definition, final boolean reverse, final boolean renameSymbols) {
        final boolean migration = migrationOwner(definition.getType());
        DexRewriter rewriter = new DexRewriter(new RewriterModule() {
            @Override public Rewriter<String> getTypeRewriter(Rewriters rewriters) {
                return value -> mapType(value, reverse, renameSymbols);
            }
            @Override public Rewriter<FieldReference> getFieldReferenceRewriter(Rewriters rewriters) {
                return new FieldReferenceRewriter(rewriters) {
                    @Override public FieldReference rewrite(FieldReference field) {
                        FieldReference mapped = super.rewrite(field);
                        String name = field.getName();
                        if (renameSymbols) {
                            String owner = reverse ? "L" + NEW_INTERNAL + "/S800x480/FarmGameEngine;"
                                    : "L" + OLD_INTERNAL + "/S800x480/ci;";
                            if (field.getDefiningClass().equals(owner)) {
                                if (field.getType().equals("I") && name.equals(reverse ? "currentTutorialStepIndex" : "aYR")) {
                                    name = reverse ? "aYR" : "currentTutorialStepIndex";
                                } else if (field.getType().equals("Z") && name.equals(reverse ? "hasUnsavedGameChanges" : "baa")) {
                                    name = reverse ? "baa" : "hasUnsavedGameChanges";
                                }
                            }
                        }
                        return new ImmutableFieldReference(mapped.getDefiningClass(), name, mapped.getType());
                    }
                };
            }
            @Override public Rewriter<MethodReference> getMethodReferenceRewriter(Rewriters rewriters) {
                return new MethodReferenceRewriter(rewriters) {
                    @Override public MethodReference rewrite(MethodReference method) {
                        MethodReference mapped = super.rewrite(method);
                        String name = method.getName();
                        String owner = reverse ? "L" + NEW_INTERNAL + "/S800x480/FarmGameEngine;"
                                : "L" + OLD_INTERNAL + "/S800x480/ci;";
                        if (renameSymbols && method.getDefiningClass().equals(owner)
                                && method.getParameterTypes().isEmpty() && method.getReturnType().equals("I")) {
                            if (name.equals(reverse ? "currentEngineState" : "qD")) name = reverse ? "qD" : "currentEngineState";
                            else if (name.equals(reverse ? "previousEngineState" : "qE")) name = reverse ? "qE" : "previousEngineState";
                        }
                        return new ImmutableMethodReference(mapped.getDefiningClass(), name,
                                mapped.getParameterTypes(), mapped.getReturnType());
                    }
                };
            }
            @Override public Rewriter<Instruction> getInstructionRewriter(Rewriters rewriters) {
                return new InstructionRewriter(rewriters) {
                    @Override public Instruction rewrite(Instruction instruction) {
                        if ((instruction.getOpcode() == Opcode.CONST_STRING
                                || instruction.getOpcode() == Opcode.CONST_STRING_JUMBO)
                                && instruction instanceof ReferenceInstruction) {
                            StringReference reference = (StringReference) ((ReferenceInstruction) instruction).getReference();
                            String mapped = mapString(reference.getString(), reverse, migration, renameSymbols);
                            // Exact reflective suffix audited in lcdui.f.ajY;
                            // unrelated GLLibInstances and SDK names stay intact.
                            if (renameSymbols && definition.getType().equals("Ljavax/microedition/lcdui/f;")
                                    && reference.getString().equals(reverse ? ".GameRuntime" : ".GLLib")) {
                                mapped = reverse ? ".GLLib" : ".GameRuntime";
                            }
                            if (!mapped.equals(reference.getString())) {
                                int register = ((OneRegisterInstruction) instruction).getRegisterA();
                                ImmutableStringReference replacement = new ImmutableStringReference(mapped);
                                return instruction.getOpcode() == Opcode.CONST_STRING
                                        ? new ImmutableInstruction21c(Opcode.CONST_STRING, register, replacement)
                                        : new ImmutableInstruction31c(Opcode.CONST_STRING_JUMBO, register, replacement);
                            }
                        }
                        return super.rewrite(instruction);
                    }
                };
            }
            @Override public Rewriter<EncodedValue> getEncodedValueRewriter(Rewriters rewriters) {
                return new EncodedValueRewriter(rewriters) {
                    @Override public EncodedValue rewrite(EncodedValue value) {
                        if (value instanceof StringEncodedValue) {
                            return new ImmutableStringEncodedValue(mapString(
                                    ((StringEncodedValue) value).getValue(), reverse, migration, renameSymbols));
                        }
                        return super.rewrite(value);
                    }
                };
            }
            @Override public Rewriter<DebugItem> getDebugItemRewriter(Rewriters rewriters) {
                return new DebugItemRewriter(rewriters) {
                    @Override public DebugItem rewrite(DebugItem item) {
                        if (item instanceof StartLocal) {
                            StartLocal local = (StartLocal) item;
                            return new ImmutableStartLocal(local.getCodeAddress(), local.getRegister(),
                                    local.getName(), mapType(local.getType(), reverse, renameSymbols),
                                    mapString(local.getSignature(), reverse, false, renameSymbols));
                        }
                        return super.rewrite(item);
                    }
                };
            }
        });
        return rewriter.getClassDefRewriter().rewrite(definition);
    }

    public static Map<String, ClassDef> rewriteClasses(Collection<? extends ClassDef> input, boolean reverse)
            throws IOException {
        return rewriteClasses(input, reverse, false);
    }
    public static Map<String, ClassDef> rewriteClasses(Collection<? extends ClassDef> input, boolean reverse, boolean renameSymbols)
            throws IOException {
        Map<String, ClassDef> result = new TreeMap<>();
        for (ClassDef definition : input) {
            ClassDef mapped = rewriteClass(definition, reverse, renameSymbols);
            if (result.put(mapped.getType(), mapped) != null) {
                throw new IOException("Relocation creates a duplicate class: " + mapped.getType());
            }
        }
        return result;
    }

    static byte[] serialize(Collection<? extends ClassDef> classes, Opcodes opcodes) throws IOException {
        DexPool pool = new DexPool(opcodes);
        Map<String, ClassDef> sorted = new TreeMap<>();
        for (ClassDef definition : classes) sorted.put(definition.getType(), definition);
        for (ClassDef definition : sorted.values()) pool.internClass(definition);
        if (pool.hasOverflowed()) throw new IOException("Single DEX reference limit exceeded during package relocation");
        MemoryDataStore data = new MemoryDataStore();
        try { pool.writeTo(data); return data.getData(); }
        finally { data.close(); }
    }

    /**
     * Canonical proof form, not runtime output. DexWriter promotes string loads
     * when the merged string pool exceeds65535; that also shifts branch targets,
     * switch alignment, try ranges and debug addresses. MutableMethodImplementation
     * carries labels through a fixed jumbo/goto32 representation so equivalent
     * encodings compare exactly, while changed registers/literals/targets do not.
     */
    public static byte[] semanticBytes(Collection<? extends ClassDef> classes, Opcodes opcodes) throws IOException {
        List<ClassDef> normalized = new ArrayList<>();
        for (ClassDef definition : classes) {
            List<Method> methods = new ArrayList<>();
            for (Method method : definition.getMethods()) {
                if (method.getImplementation() == null) { methods.add(method); continue; }
                MutableMethodImplementation body = new MutableMethodImplementation(method.getImplementation());
                List<BuilderInstruction> instructions = body.getInstructions();
                for (int index = 0; index < instructions.size(); index++) {
                    BuilderInstruction instruction = instructions.get(index);
                    if (instruction.getOpcode() == Opcode.CONST_STRING) {
                        body.replaceInstruction(index, new BuilderInstruction31c(Opcode.CONST_STRING_JUMBO,
                                ((OneRegisterInstruction) instruction).getRegisterA(),
                                ((ReferenceInstruction) instruction).getReference()));
                    } else if (instruction.getOpcode() == Opcode.GOTO || instruction.getOpcode() == Opcode.GOTO_16) {
                        body.replaceInstruction(index, new BuilderInstruction30t(Opcode.GOTO_32,
                                ((BuilderOffsetInstruction) instruction).getTarget()));
                    }
                }
                methods.add(new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(),
                        method.getReturnType(), method.getAccessFlags(), method.getAnnotations(),
                        method.getHiddenApiRestrictions(), body));
            }
            normalized.add(new ImmutableClassDef(definition.getType(), definition.getAccessFlags(),
                    definition.getSuperclass(), definition.getInterfaces(), definition.getSourceFile(),
                    definition.getAnnotations(), definition.getFields(), methods));
        }
        return serialize(normalized, opcodes);
    }

    private static Map<String, ClassDef> readClasses(byte[] bytes, Opcodes opcodes) throws IOException {
        Map<String, ClassDef> classes = new TreeMap<>();
        for (ClassDef definition : new DexBackedDexFile(opcodes, bytes).getClasses()) {
            if (classes.put(definition.getType(), definition) != null) throw new IOException("Duplicate input DEX class");
        }
        if (classes.isEmpty()) throw new IOException("Empty input DEX");
        return classes;
    }

    private static String sha256(byte[] bytes) throws Exception {
        StringBuilder text = new StringBuilder();
        for (byte value : MessageDigest.getInstance("SHA-256").digest(bytes)) text.append(String.format("%02x", value & 255));
        return text.toString();
    }
    private static String quote(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
    private static void writeAtomic(Path output, byte[] data) throws IOException {
        Files.createDirectories(output.getParent());
        Path pending = Files.createTempFile(output.getParent(), ".relocate-package-", ".tmp");
        try {
            Files.write(pending, data);
            try { Files.move(pending, output, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
            catch (AtomicMoveNotSupportedException unsupported) {
                Files.move(pending, output, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally { Files.deleteIfExists(pending); }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3 || (args.length == 3 && !args[2].equals("--rename-symbols"))) {
            throw new IllegalArgumentException("Usage: RelocateGamePackage input.dex output.dex [--rename-symbols]");
        }
        boolean renameSymbols = args.length == 3;
        Path input = Paths.get(args[0]).toRealPath();
        Path output = Paths.get(args[1]).toAbsolutePath().normalize();
        Path report = output.resolveSibling(output.getFileName() + ".json");
        if (input.equals(output) || input.equals(report)
                || (Files.exists(output) && Files.isSameFile(input, output))) {
            throw new IOException("Relocation input, output and report must be separate files");
        }
        byte[] bytes = Files.readAllBytes(input);
        if (bytes.length < 112 || !new String(bytes, 0, 4, StandardCharsets.US_ASCII).equals("dex\n")) {
            throw new IOException("Not a standard DEX");
        }
        int version = Integer.parseInt(new String(bytes, 4, 3, StandardCharsets.US_ASCII));
        Opcodes opcodes = Opcodes.forDexVersion(version);
        if (opcodes == null) throw new IOException("Unsupported DEX version: " + version);
        Map<String, ClassDef> original = readClasses(bytes, opcodes);
        for (String owner : original.keySet()) {
            if (owner.startsWith("L" + NEW_INTERNAL + "/")) {
                throw new IOException("Input must use the normalized legacy identities before relocation: " + owner);
            }
        }
        Map<String, ClassDef> relocated = rewriteClasses(original.values(), false, renameSymbols);
        byte[] outputBytes = serialize(relocated.values(), opcodes);
        Map<String, ClassDef> actual = readClasses(outputBytes, opcodes);
        if (!actual.keySet().equals(relocated.keySet())) throw new IOException("Relocated class set differs after read-back");
        for (String owner : actual.keySet()) {
            if (owner.startsWith("L" + OLD_INTERNAL + "/")) throw new IOException("Legacy game class remains: " + owner);
        }
        for (org.jf.dexlib2.iface.reference.TypeReference type :
                new DexBackedDexFile(opcodes, outputBytes).getTypeReferences()) {
            if (type.getType().contains("L" + OLD_INTERNAL + "/")) {
                throw new IOException("Legacy game type reference remains: " + type.getType());
            }
        }
        byte[] canonicalOriginal = semanticBytes(original.values(), opcodes);
        byte[] canonicalRestored = semanticBytes(rewriteClasses(actual.values(), true, renameSymbols).values(), opcodes);
        if (!Arrays.equals(canonicalOriginal, canonicalRestored)) {
            throw new IOException("Inverse relocation changed canonical DEX semantics");
        }
        int moved = 0;
        List<String> preserved = new ArrayList<>();
        for (String owner : original.keySet()) {
            if (owner.startsWith("L" + OLD_INTERNAL + "/")) moved++;
            if (migrationOwner(owner)) preserved.add(quote(mapType(owner, false)));
        }
        if (moved == 0) throw new IOException("No game classes found to relocate");
        String json = "{\n  \"schema_version\": 1,\n"
                + "  \"input_dex_sha256\": " + quote(sha256(bytes)) + ",\n"
                + "  \"output_dex_sha256\": " + quote(sha256(outputBytes)) + ",\n"
                + "  \"from_package\": " + quote(OLD_PACKAGE) + ",\n"
                + "  \"to_package\": " + quote(NEW_PACKAGE) + ",\n"
                + "  \"class_count\": " + actual.size() + ",\n"
                + "  \"relocated_class_count\": " + moved + ",\n"
                + "  \"legacy_game_class_definitions_remaining\": 0,\n"
                + "  \"inverse_canonical_semantics_verified\": true,\n"
                + "  \"game_symbols_renamed\": " + renameSymbols + ",\n"
                + "  \"input_canonical_sha256\": " + quote(sha256(canonicalOriginal)) + ",\n"
                + "  \"migration_identity_literal_exception_owners\": [" + String.join(", ", preserved) + "]\n}\n";
        writeAtomic(output, outputBytes);
        writeAtomic(report, json.getBytes(StandardCharsets.UTF_8));
        System.out.print(json);
    }
}
