import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodImplementation;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction11n;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction11x;

/** Remove Facebook after retiring its sole retained nullable avatar-provider method. */
public final class PruneFacebookSdk {
    static final String PROFILE = "Lcom/gameloft/android2d/socialnetwork/i;";
    static final String PROFILE_CANONICAL_SHA256 = "5b1b329c8487c068c8a2e1b4dbd2a0fdfabfa7a96faf7ec01acb0098ccd7fd12";
    static final String AVATAR = "UN()Ljavax/microedition/lcdui/ao;";
    static final Set<String> WRAPPERS;
    static {
        Set<String> values = new TreeSet<>();
        for (String name : Arrays.asList("aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai",
                "l", "m", "n", "o", "p", "q", "s", "t", "u", "v", "w", "x", "y", "z")) {
            values.add("Lcom/gameloft/android2d/socialnetwork/" + name + ";");
        }
        values.add("Ljavax/microedition/midlet/v;"); WRAPPERS = Collections.unmodifiableSet(values);
    }
    private PruneFacebookSdk() {}
    static boolean removedType(String value) {
        if (value == null) return false;
        while (value.startsWith("[")) value = value.substring(1);
        return value.startsWith("Lcom/facebook/") || WRAPPERS.contains(value);
    }
    static boolean removedString(String value) {
        String normalized = value.replace('.', '/');
        if (normalized.contains("com/facebook/") || normalized.equals("com/facebook")) return true;
        for (String wrapper : WRAPPERS) {
            String name = wrapper.substring(1, wrapper.length() - 1); int offset = -1;
            while ((offset = normalized.indexOf(name, offset + 1)) >= 0) {
                int end = offset + name.length();
                if (end == normalized.length() || ";$[(/ ".indexOf(normalized.charAt(end)) >= 0) return true;
            }
        }
        return false;
    }
    static final class Audit extends PruneGoogleAds.Audit {
        @Override void type(String value) { if (removedType(value)) types.add(value); }
        @Override void string(String value) { if (removedString(value)) strings.add(value); }
        @Override void requireClosed(String owner) throws IOException {
            if (!types.isEmpty() || !strings.isEmpty()) throw new IOException("Facebook SDK removal has incoming references in "
                + owner + ": types=" + types + ", strings=" + strings);
        }
    }
    static boolean avatar(Method method) { return PatchDisabledSdkLifecycle.signature(method).equals(AVATAR); }
    static Method originalAvatar(ClassDef definition) throws IOException {
        if (definition == null || !definition.getType().equals(PROFILE)) throw new IOException("Missing original social profile data class");
        Method found = null;
        for (Method method : definition.getMethods()) if (avatar(method)) {
            if (found != null || method.getAccessFlags() != 1 || method.getImplementation() == null) throw new IOException("Unreviewed avatar provider signature");
            found = method;
        }
        if (found == null) throw new IOException("Missing original avatar provider");
        return found;
    }
    static ClassDef replaceAvatar(ClassDef profile, Method replacement) throws IOException {
        originalAvatar(profile); List<Method> methods = new ArrayList<>();
        for (Method method : profile.getMethods()) methods.add(avatar(method) ? replacement : method);
        return new ImmutableClassDef(profile.getType(), profile.getAccessFlags(), profile.getSuperclass(),
            profile.getInterfaces(), profile.getSourceFile(), profile.getAnnotations(), profile.getFields(), methods);
    }
    static ClassDef retireAvatar(ClassDef profile, Opcodes ops) throws Exception {
        Method original = originalAvatar(profile);
        if (!PatchDisabledSdkLifecycle.sha256(RelocateGamePackage.semanticBytes(Collections.singletonList(profile), ops))
                .equals(PROFILE_CANONICAL_SHA256)) throw new IOException("Original profile data class fingerprint differs; refusing to alter player-data behavior");
        Method unavailable = new ImmutableMethod(original.getDefiningClass(), original.getName(), original.getParameters(),
            original.getReturnType(), original.getAccessFlags(), original.getAnnotations(), original.getHiddenApiRestrictions(),
            new ImmutableMethodImplementation(1, Arrays.asList(
                new ImmutableInstruction11n(Opcode.CONST_4, 0, 0), new ImmutableInstruction11x(Opcode.RETURN_OBJECT, 0)),
                Collections.emptyList(), Collections.emptyList()));
        return replaceAvatar(profile, unavailable);
    }
    static void verifyUnavailableAvatar(ClassDef definition) throws IOException {
        Method method = originalAvatar(definition); List<Instruction> instructions = new ArrayList<>();
        for (Instruction instruction : method.getImplementation().getInstructions()) instructions.add(instruction);
        if (instructions.size() != 2 || method.getImplementation().getRegisterCount() != 1
                || instructions.get(0).getOpcode() != Opcode.CONST_4 || ((NarrowLiteralInstruction) instructions.get(0)).getNarrowLiteral() != 0
                || ((OneRegisterInstruction) instructions.get(0)).getRegisterA() != 0
                || instructions.get(1).getOpcode() != Opcode.RETURN_OBJECT || ((OneRegisterInstruction) instructions.get(1)).getRegisterA() != 0
                || !method.getImplementation().getTryBlocks().isEmpty() || method.getImplementation().getDebugItems().iterator().hasNext()) {
            throw new IOException("Retired avatar provider differs from its reviewed nullable return");
        }
    }
    static Map<String, ClassDef> prune(Map<String, ClassDef> original, Opcodes ops, boolean requireOriginalProfile) throws Exception {
        Map<String, ClassDef> retained = new TreeMap<>(original);
        if (requireOriginalProfile) retained.put(PROFILE, retireAvatar(original.get(PROFILE), ops));
        int facebook = 0, wrappers = 0;
        for (String owner : original.keySet()) if (removedType(owner)) {
            if (owner.startsWith("Lcom/facebook/")) facebook++; else wrappers++;
            retained.remove(owner);
        }
        if (requireOriginalProfile && (facebook != 717 || wrappers != 24 || !original.keySet().containsAll(WRAPPERS))) {
            throw new IOException("Expected exactly 717 Facebook SDK and 24 reviewed provider bridge classes");
        }
        for (ClassDef definition : retained.values()) {
            Audit audit = new Audit(); audit.definition(definition); audit.requireClosed(definition.getType());
        }
        return retained;
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Usage: PruneFacebookSdk input.dex output.dex");
        Path input = Paths.get(args[0]).toRealPath(), output = Paths.get(args[1]).toAbsolutePath().normalize();
        Path report = output.resolveSibling(output.getFileName() + ".json");
        for (Path destination : Arrays.asList(output, report)) {
            if (input.equals(destination) || (Files.exists(destination) && Files.isSameFile(input, destination))) throw new IOException("Facebook pruning input/output/report must be separate");
        }
        byte[] before = Files.readAllBytes(input);
        if (before.length < 112 || !new String(before, 0, 8, StandardCharsets.US_ASCII).equals("dex\n035\0")) throw new IOException("Only the reviewed DEX035 reference model may be pruned");
        Opcodes ops = Opcodes.forDexVersion(35);
        Map<String, ClassDef> original = PruneGoogleAds.classes(before, ops), retained = prune(original, ops, true);
        byte[] bytes = RelocateGamePackage.serialize(retained.values(), ops);
        Map<String, ClassDef> actual = PruneGoogleAds.classes(bytes, ops);
        if (!actual.keySet().equals(retained.keySet())) throw new IOException("Unexpected class set after Facebook pruning");
        for (ClassDef definition : actual.values()) { Audit audit = new Audit(); audit.definition(definition); audit.requireClosed(definition.getType()); }
        PatchDisabledSdkLifecycle.assertNoAdNativeInvocations(actual);
        verifyUnavailableAvatar(actual.get(PROFILE));
        byte[] canonical = RelocateGamePackage.semanticBytes(retained.values(), ops);
        if (!Arrays.equals(canonical, RelocateGamePackage.semanticBytes(actual.values(), ops))) throw new IOException("Retained Facebook-pruned semantics changed");
        ClassDef restoredProfile = replaceAvatar(actual.get(PROFILE), originalAvatar(original.get(PROFILE)));
        if (!Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(original.get(PROFILE)), ops),
                RelocateGamePackage.semanticBytes(Collections.singletonList(restoredProfile), ops))) throw new IOException("Other profile fields or methods changed");
        Map<String, ClassDef> restored = new TreeMap<>(actual); restored.put(PROFILE, restoredProfile);
        Set<String> removed = new TreeSet<>(original.keySet()); removed.removeAll(actual.keySet());
        for (String owner : removed) restored.put(owner, original.get(owner));
        if (!Arrays.equals(RelocateGamePackage.semanticBytes(original.values(), ops), RelocateGamePackage.semanticBytes(restored.values(), ops))) throw new IOException("Inverse Facebook class-set/method semantics changed");
        String json = "{\n  \"schema_version\": 1,\n  \"policy\": \"remove-facebook-sdk-and-provider-bridge\",\n"
            + "  \"input_dex_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(before) + "\",\n  \"output_dex_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(bytes) + "\",\n"
            + "  \"removed_facebook_class_count\": 717,\n  \"removed_provider_bridge_count\": 24,\n  \"removed_class_count\": 741,\n"
            + "  \"retained_class_count\": " + actual.size() + ",\n  \"removed_class_names_sha256\": \""
            + PatchDisabledSdkLifecycle.sha256((String.join("\n", removed) + "\n").getBytes(StandardCharsets.UTF_8)) + "\",\n"
            + "  \"retired_avatar_method\": \"" + PROFILE + "->" + AVATAR + "\",\n  \"original_profile_canonical_sha256\": \"" + PROFILE_CANONICAL_SHA256 + "\",\n"
            + "  \"retained_structural_reference_audit_verified\": true,\n  \"retained_reflection_string_audit_verified\": true,\n"
            + "  \"native_ads_manager_invocations_remaining\": 0,\n  \"global_ad_native_invocation_audit_verified\": true,\n"
            + "  \"retired_nullable_avatar_method_verified\": true,\n  \"other_profile_data_and_methods_preserved\": true,\n"
            + "  \"retained_class_semantics_verified\": true,\n  \"inverse_class_set_and_method_semantics_verified\": true,\n"
            + "  \"retained_classes_canonical_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(canonical) + "\"\n}\n";
        PatchDisabledSdkLifecycle.publish(output, bytes); PatchDisabledSdkLifecycle.publish(report, json.getBytes(StandardCharsets.UTF_8)); System.out.print(json);
    }
}
