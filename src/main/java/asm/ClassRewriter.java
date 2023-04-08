package asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.InvocationTargetException;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.Opcodes.ASM9;

public class ClassRewriter {

    private final ClassReader reader;
    private final ClassWriter writer;
    private final ClassVisitor visitor;

    public ClassRewriter(byte[] contents, Class<? extends ClassVisitor> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        reader = new ClassReader(contents);
        writer = new ClassWriter(reader, COMPUTE_FRAMES);
        visitor = clazz.getConstructor(int.class, ClassVisitor.class).newInstance(ASM9, writer);
    }

    public byte[] makeItHappen() {
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }
}
