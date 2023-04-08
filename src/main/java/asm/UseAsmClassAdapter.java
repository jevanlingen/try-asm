package asm;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;

public class UseAsmClassAdapter extends ClassVisitor {

    public UseAsmClassAdapter(final int api, final ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        var currentInterfaces = new ArrayList<>(Arrays.asList(interfaces));
        currentInterfaces.add("java/lang/Cloneable");

        cv.visit(V17, access, name, signature, superName, currentInterfaces.toArray(String[]::new));
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
        if (name.equals("shouldNotBePublic")) {
            return cv.visitMethod(ACC_PUBLIC + ACC_STATIC, name, descriptor, signature, exceptions);
        }
        return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        createANewBooleanField();
        createAddMethod();
        createDoReverseMethod();
        createFibMethod();
        cv.visitEnd();
    }

    /**
     * public static boolean aNewBooleanField
     */
    private void createANewBooleanField() {
        var fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "aNewBooleanField", BOOLEAN_TYPE.toString(), null, true);
        fv.visitEnd();
    }

    /**
     * public static int add(int a, int b) {
     *   return a + b;
     * }
     */
    private void createAddMethod() {
        var mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "(II)I", null, null);
        mv.visitCode();
        mv.visitVarInsn(ILOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitInsn(IADD);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    /**
     * public boolean doReverse(boolean a) {
     *   if (a) {
     *     return false;
     *   }
     *  return true;
     *}
     */
    private void createDoReverseMethod() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "doReverse", "(Z)Z", null, null);

        // Define the if statement
        Label ifLabel = new Label();
        mv.visitVarInsn(ILOAD, 0);
        mv.visitJumpInsn(IFEQ, ifLabel);

        // Define the "return false" statement
        mv.visitInsn(ICONST_0);
        mv.visitInsn(IRETURN);

        // Define the if statement condition
        mv.visitLabel(ifLabel);

        // Define the "return true" statement
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        // Define the method footer
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    /**
     * public static float fib(final float n) {
     *     if (n <= 1)
     *       return n;
     *
     *     return fib(n - 1) + fib(n - 2);
     * }
     */
    private void createFibMethod() {
        var mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "fib", "(F)F", null, null);
        mv.visitCode();

        // Load the n argument onto the stack
        mv.visitVarInsn(FLOAD, 0);

        // Compare n to 1.0f
        mv.visitInsn(FCONST_1);
        mv.visitInsn(FCMPG);

        // If n <= 1.0f, return n
        Label elseLabel = new Label();
        mv.visitJumpInsn(IFGT, elseLabel);
        mv.visitVarInsn(FLOAD, 0);
        mv.visitInsn(FRETURN);

        // Otherwise, compute fib(n-1) and fib(n-2) and add them
        mv.visitLabel(elseLabel);
        mv.visitVarInsn(FLOAD, 0);
        mv.visitInsn(FCONST_1);
        mv.visitInsn(FSUB);
        mv.visitMethodInsn(INVOKESTATIC, "UseAsm", "fib", "(F)F", false);

        mv.visitVarInsn(FLOAD, 0);
        mv.visitInsn(FCONST_2);
        mv.visitInsn(FSUB);
        mv.visitMethodInsn(INVOKESTATIC, "UseAsm", "fib", "(F)F", false);

        mv.visitInsn(FADD);
        mv.visitInsn(FRETURN);

        mv.visitMaxs(3, 1);

        mv.visitEnd();
    }
}
