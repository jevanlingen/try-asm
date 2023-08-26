import asm.ClassRewriter;
import asm.UseAsmClassAdapter;
import org.objectweb.asm.ClassVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Transformer {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined, final ProtectionDomain protectionDomain, final byte[] buffer) {
                if (className.equals("UseAsm")) {
                    return rewriteClass(className, buffer, UseAsmClassAdapter.class);
                }
                return buffer;
            }

            private static byte[] rewriteClass(final String className, final byte[] buffer, Class<? extends ClassVisitor> clazz) {
                try {
                    var newBuffer = new ClassRewriter(buffer, clazz).makeItHappen();
                    System.out.println(">> Rewrote '" + className + "' from " + buffer.length + " bytes to " + newBuffer.length + " bytes");
                    return newBuffer;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return buffer;
                }
            }
        });
    }
}
