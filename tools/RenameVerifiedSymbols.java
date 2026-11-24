import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/** Refactor only explicitly reviewed Java symbols using javac's resolved Trees. */
public final class RenameVerifiedSymbols {
    private static final Map<String, String> CLASS_NAMES = new LinkedHashMap<>();
    private static final Map<String, String> FIELD_NAMES = new LinkedHashMap<>();
    private static final Map<String, String> METHOD_NAMES = new LinkedHashMap<>();
    static {
        CLASS_NAMES.put("ci", "FarmGameEngine");
        CLASS_NAMES.put("ch", "FarmEntity");
        CLASS_NAMES.put("GLLib", "GameRuntime");
        FIELD_NAMES.put("aYR", "currentTutorialStepIndex");
        FIELD_NAMES.put("baa", "hasUnsavedGameChanges");
        METHOD_NAMES.put("qD", "currentEngineState");
        METHOD_NAMES.put("qE", "previousEngineState");
    }

    private static final class Edit {
        final int start, end;
        final String oldName, newName;
        Edit(int start, int end, String oldName, String newName) {
            this.start = start; this.end = end; this.oldName = oldName; this.newName = newName;
        }
    }

    private static final class Token {
        final int start, end;
        final String text;
        Token(int start, int end, String text) { this.start = start; this.end = end; this.text = text; }
    }

    private static final class Source {
        final Path path;
        final String text;
        final Map<Integer, Edit> edits = new TreeMap<>();
        Source(Path path) throws IOException { this.path = path; this.text = Files.readString(path); }
        void edit(int start, int end, String before, String after) {
            if (start < 0 || end < start || end > text.length() || !text.substring(start, end).equals(before)) {
                throw new IllegalStateException("Unexpected resolved token at " + path + ":" + start + " for " + before);
            }
            Edit previous = edits.putIfAbsent(start, new Edit(start, end, before, after));
            if (previous != null && (previous.end != end || !previous.newName.equals(after))) {
                throw new IllegalStateException("Overlapping symbol edits in " + path);
            }
        }
        String changedText() {
            StringBuilder result = new StringBuilder(text);
            List<Edit> ordered = new ArrayList<>(edits.values());
            ordered.sort(Comparator.comparingInt((Edit edit) -> edit.start).reversed());
            int previousStart = text.length();
            for (Edit edit : ordered) {
                if (edit.end > previousStart) throw new IllegalStateException("Overlapping symbols in " + path);
                result.replace(edit.start, edit.end, edit.newName);
                previousStart = edit.start;
            }
            return result.toString();
        }
    }

    /** Declaration tokens only; comments and string/char literals cannot be names. */
    private static List<Token> tokens(String text, int start, int end) {
        List<Token> result = new ArrayList<>();
        for (int index = start; index < end;) {
            char value = text.charAt(index);
            if (Character.isWhitespace(value)) { index++; continue; }
            if (value == '/' && index + 1 < end && text.charAt(index + 1) == '/') {
                index += 2; while (index < end && text.charAt(index) != '\n') index++;
                continue;
            }
            if (value == '/' && index + 1 < end && text.charAt(index + 1) == '*') {
                int closing = text.indexOf("*/", index + 2);
                if (closing < 0) throw new IllegalStateException("Unclosed declaration comment");
                index = closing + 2; continue;
            }
            if (value == '"' || value == '\'') {
                char quote = value; index++;
                while (index < end) {
                    char current = text.charAt(index++);
                    if (current == '\\' && index < end) index++;
                    else if (current == quote) break;
                }
                continue;
            }
            int opening = index++;
            if (Character.isJavaIdentifierStart(value)) {
                while (index < end && Character.isJavaIdentifierPart(text.charAt(index))) index++;
            }
            result.add(new Token(opening, index, text.substring(opening, index)));
        }
        return result;
    }

    private static String owner(Element element) {
        Element parent = element == null ? null : element.getEnclosingElement();
        return parent instanceof TypeElement ? ((TypeElement) parent).getQualifiedName().toString() : "";
    }

    private static String quote(String text) {
        return "\"" + text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"";
    }

    public static void main(String[] arguments) throws Exception {
        Map<String, String> args = new LinkedHashMap<>();
        boolean apply = false;
        for (int index = 0; index < arguments.length; index++) {
            if (arguments[index].equals("--apply")) apply = true;
            else if (index + 1 < arguments.length) args.put(arguments[index], arguments[++index]);
            else throw new IllegalArgumentException("Missing argument value");
        }
        Path root = Paths.get(args.get("--source-root")).toAbsolutePath().normalize();
        String packageName = args.get("--package");
        String engine = packageName + ".ci";
        List<Path> paths;
        try (var stream = Files.list(root)) {
            paths = stream.filter(path -> path.toString().endsWith(".java") && !path.getFileName().toString().equals("l.java"))
                    .sorted().collect(Collectors.toList());
        }
        if (paths.isEmpty()) throw new IllegalArgumentException("No selected game Java sources");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("A JDK compiler is required");
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        Map<Path, Source> sources = new LinkedHashMap<>();
        for (Path path : paths) sources.put(path, new Source(path));
        Map<String, String> qualifiedClasses = new LinkedHashMap<>();
        CLASS_NAMES.forEach((oldName, newName) -> qualifiedClasses.put(packageName + "." + oldName, newName));
        try (StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8)) {
            List<String> options = Arrays.asList("-proc:none", "-source", "8", "-target", "8", "-sourcepath", "",
                    "-classpath", args.getOrDefault("--classpath", ""));
            JavacTask task = (JavacTask) compiler.getTask(null, manager, diagnostics, options, null,
                    manager.getJavaFileObjectsFromPaths(paths));
            List<CompilationUnitTree> units = new ArrayList<>();
            task.parse().forEach(units::add);
            task.analyze();
            List<String> errors = diagnostics.getDiagnostics().stream()
                    .filter(item -> item.getKind() == Diagnostic.Kind.ERROR).map(Object::toString).collect(Collectors.toList());
            if (!errors.isEmpty()) throw new IllegalStateException("Resolve every selected Java symbol before refactoring:\n" + String.join("\n", errors));
            Trees trees = Trees.instance(task);
            SourcePositions positions = trees.getSourcePositions();
            for (CompilationUnitTree unit : units) {
                Source source = sources.get(Paths.get(unit.getSourceFile().toUri()).toAbsolutePath().normalize());
                new TreePathScanner<Void, Void>() {
                    String replacement(Element element) {
                        if (element instanceof TypeElement) return qualifiedClasses.get(((TypeElement) element).getQualifiedName().toString());
                        if (element instanceof ExecutableElement && element.getKind() == ElementKind.METHOD && owner(element).equals(engine)) {
                            ExecutableElement method = (ExecutableElement) element;
                            // The reviewed map is descriptor-specific: ()I. An
                            // overload, instance method or other owner stays put.
                            if (method.getParameters().isEmpty() && method.getReturnType().getKind() == TypeKind.INT &&
                                    method.getModifiers().contains(Modifier.STATIC)) {
                                return METHOD_NAMES.get(method.getSimpleName().toString());
                            }
                        }
                        if (element instanceof VariableElement && element.getKind() == ElementKind.FIELD && owner(element).equals(engine)) {
                            String name = element.getSimpleName().toString();
                            String result = FIELD_NAMES.get(name);
                            if (result != null) {
                                TypeKind expected = name.equals("aYR") ? TypeKind.INT : TypeKind.BOOLEAN;
                                if (element.asType().getKind() != expected) throw new IllegalStateException("Reviewed field descriptor changed: " + name);
                            }
                            return result;
                        }
                        return null;
                    }
                    void nameAtEnd(Tree tree, String oldName, String newName) {
                        int end = Math.toIntExact(positions.getEndPosition(unit, tree));
                        source.edit(end - oldName.length(), end, oldName, newName);
                    }
                    @Override public Void visitIdentifier(IdentifierTree tree, Void ignored) {
                        String renamed = replacement(trees.getElement(getCurrentPath()));
                        if (renamed != null) nameAtEnd(tree, tree.getName().toString(), renamed);
                        return super.visitIdentifier(tree, ignored);
                    }
                    @Override public Void visitMemberSelect(MemberSelectTree tree, Void ignored) {
                        String renamed = replacement(trees.getElement(getCurrentPath()));
                        if (renamed != null) nameAtEnd(tree, tree.getIdentifier().toString(), renamed);
                        return super.visitMemberSelect(tree, ignored);
                    }
                    @Override public Void visitClass(ClassTree tree, Void ignored) {
                        Element element = trees.getElement(getCurrentPath());
                        String renamed = replacement(element);
                        if (renamed != null) {
                            int start = Math.toIntExact(positions.getStartPosition(unit, tree));
                            int end = Math.toIntExact(positions.getEndPosition(unit, tree));
                            List<Token> header = tokens(source.text, start, end);
                            boolean found = false;
                            for (int index = 0; index + 1 < header.size(); index++) {
                                if (header.get(index).text.equals("class") && header.get(index + 1).text.equals(tree.getSimpleName().toString())) {
                                    Token name = header.get(index + 1);
                                    source.edit(name.start, name.end, name.text, renamed); found = true; break;
                                }
                            }
                            if (!found) throw new IllegalStateException("Missing resolved class declaration");
                        }
                        return super.visitClass(tree, ignored);
                    }
                    @Override public Void visitMethod(MethodTree tree, Void ignored) {
                        Element element = trees.getElement(getCurrentPath());
                        if (element != null && element.getKind() == ElementKind.CONSTRUCTOR) {
                            String owner = owner(element), renamed = qualifiedClasses.get(owner);
                            if (renamed != null) {
                                int start = Math.toIntExact(positions.getStartPosition(unit, tree));
                                int end = tree.getBody() == null ? Math.toIntExact(positions.getEndPosition(unit, tree))
                                        : Math.toIntExact(positions.getStartPosition(unit, tree.getBody()));
                                if (start >= 0 && end >= start) {
                                    List<Token> header = tokens(source.text, start, end);
                                    String oldName = owner.substring(owner.lastIndexOf('.') + 1);
                                    for (int index = 0; index + 1 < header.size(); index++) {
                                        Token name = header.get(index);
                                        if (name.text.equals(oldName) && header.get(index + 1).text.equals("(")) {
                                            source.edit(name.start, name.end, name.text, renamed); break;
                                        }
                                    }
                                }
                            }
                        }
                        if (element != null && element.getKind() == ElementKind.METHOD) {
                            String renamed = replacement(element);
                            if (renamed != null) {
                                int start = Math.toIntExact(positions.getStartPosition(unit, tree));
                                int end = tree.getBody() == null ? Math.toIntExact(positions.getEndPosition(unit, tree))
                                        : Math.toIntExact(positions.getStartPosition(unit, tree.getBody()));
                                List<Token> header = tokens(source.text, start, end);
                                List<Token> matches = new ArrayList<>();
                                for (int index = 0; index + 1 < header.size(); index++) {
                                    Token token = header.get(index);
                                    if (token.text.equals(tree.getName().toString()) && header.get(index + 1).text.equals("(")) matches.add(token);
                                }
                                if (matches.size() != 1) throw new IllegalStateException("Ambiguous reviewed method declaration: " + source.path);
                                Token name = matches.get(0);
                                source.edit(name.start, name.end, name.text, renamed);
                            }
                        }
                        return super.visitMethod(tree, ignored);
                    }
                    @Override public Void visitVariable(VariableTree tree, Void ignored) {
                        Element element = trees.getElement(getCurrentPath());
                        String renamed = replacement(element);
                        if (renamed != null) {
                            int start = Math.toIntExact(positions.getStartPosition(unit, tree));
                            int end = tree.getInitializer() == null ? Math.toIntExact(positions.getEndPosition(unit, tree))
                                    : Math.toIntExact(positions.getStartPosition(unit, tree.getInitializer()));
                            List<Token> header = tokens(source.text, start, end);
                            for (int index = header.size() - 1; index >= 0; index--) {
                                Token name = header.get(index);
                                if (name.text.equals(tree.getName().toString())) {
                                    source.edit(name.start, name.end, name.text, renamed); break;
                                }
                            }
                        }
                        return super.visitVariable(tree, ignored);
                    }
                }.scan(unit, null);
            }
        }
        // Finish and validate every edit before changing the first source file.
        Map<Path, String> changed = new LinkedHashMap<>();
        Map<Path, Path> moved = new LinkedHashMap<>();
        for (Source source : sources.values()) {
            if (source.edits.isEmpty()) continue;
            changed.put(source.path, source.changedText());
            String stem = source.path.getFileName().toString().replaceFirst("\\.java$", "");
            if (CLASS_NAMES.containsKey(stem)) {
                Path destination = source.path.resolveSibling(CLASS_NAMES.get(stem) + ".java");
                if (Files.exists(destination)) throw new IllegalStateException("Refusing to replace existing renamed class: " + destination);
                moved.put(source.path, destination);
            }
        }
        StringBuilder report = new StringBuilder("{\n  \"engine_owner\": " + quote(engine) + ",\n  \"symbol_resolution\": \"javac Trees and Element identity\",\n  \"applied\": " + apply + ",\n  \"files\": [\n");
        boolean first = true;
        for (Map.Entry<Path, String> item : changed.entrySet()) {
            if (!first) report.append(",\n"); first = false;
            Source source = sources.get(item.getKey());
            report.append("    {\"source\": ").append(quote(root.relativize(source.path).toString()))
                    .append(", \"target\": ").append(quote(root.relativize(moved.getOrDefault(source.path, source.path)).toString()))
                    .append(", \"resolved_edits\": ").append(source.edits.size()).append("}");
        }
        report.append("\n  ]\n}\n");
        if (apply) {
            for (Map.Entry<Path, String> item : changed.entrySet()) Files.writeString(item.getKey(), item.getValue(), StandardCharsets.UTF_8);
            for (Map.Entry<Path, Path> item : moved.entrySet()) Files.move(item.getKey(), item.getValue());
        }
        Path reportPath = Paths.get(args.get("--report"));
        if (reportPath.getParent() != null) Files.createDirectories(reportPath.getParent());
        Files.writeString(reportPath, report.toString());
        System.out.println("Resolved " + changed.size() + " Java source files; " + (apply ? "applied" : "dry-run only"));
    }
}
