# Try ASM

Welcome to this GitHub repository dedicated to learning JVM byte code and the ASM library with Java!
Here, you'll find a collection of Java classes demonstrating how to use [ASM](https://asm.ow2.io) to manipulate Java bytecode. 

## Usage

- Run `mvn install`
- Run javac and javap commands like `javac src/main/java/examine/Basic.java` and `javap -c src/main/java/examine/Basic.class` to look at JVM byte code.
- Run `java UseAsm -javaagent:"/<path-to-.m2>/.m2/repository/org/example/try-asm/1.0-SNAPSHOT/try-asm-1.0-SNAPSHOT.jar"` to try the ASM stuff.
