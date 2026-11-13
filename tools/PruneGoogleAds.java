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
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.debug.LocalInfo;
import org.jf.dexlib2.iface.instruction.DualReferenceInstruction;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
import org.jf.dexlib2.iface.value.ArrayEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.EnumEncodedValue;
import org.jf.dexlib2.iface.value.FieldEncodedValue;
import org.jf.dexlib2.iface.value.MethodEncodedValue;
import org.jf.dexlib2.iface.value.StringEncodedValue;
import org.jf.dexlib2.iface.value.TypeEncodedValue;

/** Remove an audited closed set of unused Google ad presentation SDK definitions. */
public final class PruneGoogleAds {
    static final Set<String> IDENTIFIER = Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(
        "Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;",
        "Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;",
        "Lcom/google/android/gms/ads/identifier/a;", "Lcom/google/android/gms/ads/identifier/b;",
        "Lcom/google/android/gms/ads/identifier/zzb;", "Lcom/google/android/gms/ads/identifier/zzc;")));
    static final int EXPECTED_REMOVALS = 1880;
    private PruneGoogleAds() {}

    static boolean removedType(String type) {
        if (type == null) return false;
        while (type.startsWith("[")) type = type.substring(1);
        return (type.startsWith("Lcom/google/android/gms/ads/") && !IDENTIFIER.contains(type))
            || type.startsWith("Lcom/google/android/gms/internal/ads/") || type.startsWith("Lcom/google/ads/")
            || type.equals("Lcom/google/android/gms/dynamite/descriptors/com/google/android/gms/ads/dynamite/ModuleDescriptor;");
    }
    static boolean removedString(String value) {
        String normalized = value.replace('.', '/');
        for (String prefix : Arrays.asList("com/google/android/gms/ads", "com/google/android/gms/internal/ads", "com/google/ads")) {
            int offset = -1;
            while ((offset = normalized.indexOf(prefix, offset + 1)) >= 0) {
                String rest = normalized.substring(offset + prefix.length());
                if (!(rest.isEmpty() || rest.startsWith("/") || rest.startsWith(";"))) continue;
                if (prefix.equals("com/google/android/gms/ads")
                        && (rest.equals("/identifier") || rest.startsWith("/identifier/") || rest.startsWith("/identifier;"))) continue;
                return true;
            }
        }
        return false;
    }
    static class Audit {
        final Set<String> types = new TreeSet<>(), strings = new TreeSet<>();
        void type(String value) { if (removedType(value)) types.add(value); }
        void string(String value) { if (removedString(value)) strings.add(value); }
        void reference(Reference ref) {
            if (ref instanceof TypeReference) type(((TypeReference) ref).getType());
            else if (ref instanceof MethodReference) {
                MethodReference method = (MethodReference) ref;
                type(method.getDefiningClass()); type(method.getReturnType());
                for (CharSequence parameter : method.getParameterTypes()) type(parameter.toString());
            } else if (ref instanceof FieldReference) {
                FieldReference field = (FieldReference) ref; type(field.getDefiningClass()); type(field.getType());
            } else if (ref instanceof StringReference) string(((StringReference) ref).getString());
        }
        void value(EncodedValue value) {
            if (value == null) return;
            if (value instanceof TypeEncodedValue) type(((TypeEncodedValue) value).getValue());
            else if (value instanceof StringEncodedValue) string(((StringEncodedValue) value).getValue());
            else if (value instanceof MethodEncodedValue) reference(((MethodEncodedValue) value).getValue());
            else if (value instanceof FieldEncodedValue) reference(((FieldEncodedValue) value).getValue());
            else if (value instanceof EnumEncodedValue) reference(((EnumEncodedValue) value).getValue());
            else if (value instanceof ArrayEncodedValue) for (EncodedValue item : ((ArrayEncodedValue) value).getValue()) value(item);
            else if (value instanceof AnnotationEncodedValue) {
                AnnotationEncodedValue annotation = (AnnotationEncodedValue) value;
                type(annotation.getType()); for (AnnotationElement element : annotation.getElements()) value(element.getValue());
            }
        }
        void annotations(Set<? extends Annotation> annotations) {
            for (Annotation annotation : annotations) {
                type(annotation.getType()); for (AnnotationElement element : annotation.getElements()) value(element.getValue());
            }
        }
        void definition(ClassDef definition) {
            type(definition.getSuperclass()); for (String iface : definition.getInterfaces()) type(iface); annotations(definition.getAnnotations());
            for (Field field : definition.getFields()) { type(field.getType()); value(field.getInitialValue()); annotations(field.getAnnotations()); }
            for (Method method : definition.getMethods()) {
                type(method.getReturnType()); annotations(method.getAnnotations());
                for (MethodParameter parameter : method.getParameters()) { type(parameter.getType()); annotations(parameter.getAnnotations()); }
                if (method.getImplementation() == null) continue;
                for (DebugItem item : method.getImplementation().getDebugItems()) {
                    if (item instanceof LocalInfo) type(((LocalInfo) item).getType());
                }
                for (TryBlock<? extends ExceptionHandler> block : method.getImplementation().getTryBlocks()) {
                    for (ExceptionHandler handler : block.getExceptionHandlers()) type(handler.getExceptionType());
                }
                for (Instruction instruction : method.getImplementation().getInstructions()) {
                    if (instruction instanceof ReferenceInstruction) reference(((ReferenceInstruction) instruction).getReference());
                    if (instruction instanceof DualReferenceInstruction) reference(((DualReferenceInstruction) instruction).getReference2());
                }
            }
        }
        void requireClosed(String owner) throws IOException {
            if (!types.isEmpty() || !strings.isEmpty()) throw new IOException("Google Ads removal has incoming references in "
                + owner + ": types=" + types + ", strings=" + strings);
        }
    }
    static Map<String, ClassDef> prune(Map<String, ClassDef> original, boolean requireOriginalProfile) throws IOException {
        Map<String, ClassDef> retained = new TreeMap<>(); int count = 0;
        for (ClassDef definition : original.values()) {
            if (removedType(definition.getType())) { count++; continue; }
            Audit audit = new Audit(); audit.definition(definition); audit.requireClosed(definition.getType());
            retained.put(definition.getType(), definition);
        }
        if (requireOriginalProfile && (count != EXPECTED_REMOVALS || !retained.keySet().containsAll(IDENTIFIER))) {
            throw new IOException("Expected exactly 1880 reviewed Google Ads classes and six retained identifier classes; found " + count);
        }
        return retained;
    }
    static Map<String, ClassDef> classes(byte[] bytes, Opcodes opcodes) throws IOException {
        Map<String, ClassDef> result = new TreeMap<>();
        for (ClassDef definition : new DexBackedDexFile(opcodes, bytes).getClasses()) {
            if (result.put(definition.getType(), definition) != null) throw new IOException("Duplicate DEX class");
        }
        return result;
    }
    static String array(Iterable<String> values) {
        List<String> strings = new ArrayList<>(); for (String value : values) strings.add("\"" + value + "\"");
        return "[" + String.join(",", strings) + "]";
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Usage: PruneGoogleAds input.dex output.dex");
        Path input = Paths.get(args[0]).toRealPath(), output = Paths.get(args[1]).toAbsolutePath().normalize();
        Path report = output.resolveSibling(output.getFileName() + ".json");
        for (Path destination : Arrays.asList(output, report)) {
            if (input.equals(destination) || (Files.exists(destination) && Files.isSameFile(input, destination))) throw new IOException("Pruning input/output/report must be separate");
        }
        byte[] before = Files.readAllBytes(input);
        if (before.length < 112 || !new String(before, 0, 4, StandardCharsets.US_ASCII).equals("dex\n")) throw new IOException("Expected standard DEX");
        int version = Integer.parseInt(new String(before, 4, 3, StandardCharsets.US_ASCII));
        if (version != 35) throw new IOException("Only the reviewed DEX035 reference model may be pruned");
        Opcodes ops = Opcodes.forDexVersion(version);
        Map<String, ClassDef> original = classes(before, ops), retained = prune(original, true);
        byte[] bytes = RelocateGamePackage.serialize(retained.values(), ops);
        Map<String, ClassDef> actual = classes(bytes, ops);
        if (!retained.keySet().equals(actual.keySet())) throw new IOException("Unexpected class set after Google Ads pruning");
        for (ClassDef definition : actual.values()) { Audit audit = new Audit(); audit.definition(definition); audit.requireClosed(definition.getType()); }
        byte[] canonical = RelocateGamePackage.semanticBytes(retained.values(), ops);
        if (!Arrays.equals(canonical, RelocateGamePackage.semanticBytes(actual.values(), ops))) throw new IOException("Retained class semantics changed");
        Map<String, ClassDef> restored = new TreeMap<>(actual); Set<String> removed = new TreeSet<>(original.keySet()); removed.removeAll(retained.keySet());
        for (String owner : removed) restored.put(owner, original.get(owner));
        if (!Arrays.equals(RelocateGamePackage.semanticBytes(original.values(), ops), RelocateGamePackage.semanticBytes(restored.values(), ops))) throw new IOException("Inverse class-set semantics changed");
        String listHash = PatchDisabledSdkLifecycle.sha256((String.join("\n", removed) + "\n").getBytes(StandardCharsets.UTF_8));
        String json = "{\n  \"schema_version\": 1,\n  \"policy\": \"remove-google-ads-presentation-sdk\",\n"
            + "  \"input_dex_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(before) + "\",\n  \"output_dex_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(bytes) + "\",\n"
            + "  \"removed_class_count\": " + removed.size() + ",\n  \"retained_class_count\": " + actual.size() + ",\n"
            + "  \"removed_class_names_sha256\": \"" + listHash + "\",\n  \"retained_identifier_classes\": " + array(IDENTIFIER) + ",\n"
            + "  \"retained_structural_reference_audit_verified\": true,\n  \"retained_reflection_string_audit_verified\": true,\n"
            + "  \"retained_class_semantics_verified\": true,\n  \"inverse_class_set_semantics_verified\": true,\n"
            + "  \"retained_classes_canonical_sha256\": \"" + PatchDisabledSdkLifecycle.sha256(canonical) + "\"\n}\n";
        PatchDisabledSdkLifecycle.publish(output, bytes); PatchDisabledSdkLifecycle.publish(report, json.getBytes(StandardCharsets.UTF_8)); System.out.print(json);
    }
}
