package asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

public class CustomAnnotationsClassAdapter extends ClassVisitor {

    public CustomAnnotationsClassAdapter(final int api, final ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    private String className = "";
    private boolean setAllArgsConstructor = false;
    private boolean setGetter = false;
    private boolean setSetter = false;
    private final List<Field> fields = new ArrayList<>();

    @Override
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
        if (descriptor.contains("exercise/AllArgsConstructor")) {
            setAllArgsConstructor = true;
        }
        if (descriptor.contains("exercise/Getter")) {
            setGetter = true;
        }
        if (descriptor.contains("exercise/Setter")) {
            setSetter = true;
        }

        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String descriptor, final String signature, final Object value) {
        final var field = new Field(name, descriptor);
        if (setGetter) {
            createGetter(field);
        }
        if (setSetter) {
            createSetter(field);
        }
        if (setAllArgsConstructor) {
            fields.add(field);
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        if (setAllArgsConstructor) {
            createAllArgsConstructor();
        }

        cv.visitEnd();
    }

    private void createAllArgsConstructor() {
        final var incomingTypes = fields.stream().map(it -> it.type).collect(Collectors.joining());

        /*System.out.println(" ---------- createAllArgsConstructor ---------  ");
        System.out.println("public <init>(" + incomingTypes + ")V");
        System.out.println("ALOAD 0");
        System.out.println("INVOKESPECIAL java/lang/Object.<init> ()V");
        for (int i = 0; i < fields.size(); i++) {
            System.out.println("ALOAD 0");
            System.out.println(switch (fields.get(i).type) {
                case "I", "Z" -> "ILOAD";
                case default -> "ALOAD";
            } + " " + (i + 1));

            System.out.println("PUTFIELD " + className + "." + fields.get(i).name + " : " + fields.get(i).type);
        }
        System.out.println("RETURN");*/

        final var mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(" + incomingTypes + ")V", null, null);
        mv.visitVarInsn(ALOAD, 0); // Load 'this'
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        for (int i = 0; i < fields.size(); i++) {
            mv.visitVarInsn(ALOAD, 0); // Load 'this'
            mv.visitVarInsn(fields.get(i).loadOp(), i + 1); // Load 'field'
            mv.visitFieldInsn(PUTFIELD, className, fields.get(i).name, fields.get(i).type); // Set 'field'
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();
    }

    private void createGetter(Field field) {
        final var prefix = "Z".equals(field.type) ? "is" : "get";
        final var methodName = prefix + field.name.substring(0, 1).toUpperCase() + field.name.substring(1);

        /*System.out.println(" ---------- GETTER ---------  ");
        System.out.println("public " + methodName + "()" + field.type);
        System.out.println("ALOAD 0");
        System.out.println("GETFIELD " + className + "." + field.name + " : " + field.type);
        System.out.println(switch (field.type) {
            case "I", "Z" -> "IRETURN";
            case default -> "ARETURN";
        });*/

        var mv = cv.visitMethod(ACC_PUBLIC, methodName, "()" + field.type, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, className, field.name, field.type);
        mv.visitInsn(field.returnOp());
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void createSetter(Field field) {
        final var methodName = "set" + field.name.substring(0, 1).toUpperCase() + field.name.substring(1);

        /*System.out.println(" ---------- SETTER ---------  ");
        System.out.println("public " + methodName + "(" + field.type + ")V");
        System.out.println("ALOAD 0");
        System.out.println(switch (field.type) {
            case "I", "Z" -> "ILOAD";
            case default -> "ALOAD";
        } + " 1");
        System.out.println("PUTFIELD " + className + "." + field.name + " : " + field.type);
        System.out.println("RETURN");*/

        var mv = cv.visitMethod(ACC_PUBLIC, methodName, "(" + field.type + ")V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(field.loadOp(), 1);
        mv.visitFieldInsn(PUTFIELD, className, field.name, field.type);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    record Field(String name, String type) {

        public int loadOp() {
            return switch (this.type) {
                case "I", "Z" -> ILOAD;
                case default -> ALOAD;
            };
        }

        public int returnOp() {
            return switch (this.type) {
                case "I", "Z" -> IRETURN;
                case default -> ARETURN;
            };
        }
    }
}

