//package de.iaas.skywalker.adapter.bcel;
//
//import org.apache.bcel.Constants;
//import org.apache.bcel.classfile.ClassParser;
//import org.apache.bcel.classfile.JavaClass;
//import org.apache.bcel.generic.*;
//import org.apache.bcel.util.BCELifier;
//
//import java.io.IOException;
//
//import static org.apache.bcel.Const.*;
//
///**
// * Created by nymvno on 07.09.2019.
// */
//public class BCELMain {
//
//    public static void main(String[] args) {
//        //generateBasicClass();
//        generateBCELFromClass();
//    }
//
//    public static void generateBCELFromClass() {
//        try {
//            JavaClass jc = new ClassParser("C:\\Users\\Ayhan\\repos\\YAMLess\\target\\classes\\de\\adapter\\bcel\\BCELifierGeneratedBCEL.class").parse();
//
//            BCELifier bcelifier = new BCELifier(jc, System.out);
//            bcelifier.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void generateBasicClass() {
//
//        ClassGen cg = new ClassGen(
//                "HelloWorld",
//                "java.lang.Object",
//                "<generated>",
//                ACC_PUBLIC | ACC_SUPER, null
//        );
//
//        ConstantPoolGen cp = cg.getConstantPool();
//        InstructionList il = new InstructionList();
//
//        MethodGen  mg = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
//                Type.VOID,                                      // return type
//                new Type[] {                                    // argument types
//                new ArrayType(Type.STRING, 1) },
//                new String[] { "argv" },                        // arg names
//                "main",
//                "HelloWorld",                        // method, class
//                il,
//                cp
//        );
//
//        InstructionFactory factory = new InstructionFactory(cg);
//
//        ObjectType i_stream = new ObjectType("java.io.InputStream");
//        ObjectType p_stream = new ObjectType("java.io.PrintStream");
//
//
//        il.append(factory.createNew("java.io.BufferedReader"));
//        il.append(InstructionConstants.DUP); // Use predefined constant
//
//        il.append(factory.createNew("java.io.InputStreamReader"));
//        il.append(InstructionConstants.DUP);
//
//        il.append(factory.createFieldAccess("java.lang.System", "in", i_stream, Constants.GETSTATIC));
//        il.append(factory.createInvoke(
//                "java.io.InputStreamReader",
//                "<init>",
//                Type.VOID, new Type[] { i_stream },
//                Constants.INVOKESPECIAL)
//        );
//        il.append(factory.createInvoke(
//                "java.io.BufferedReader",
//                "<init>", Type.VOID,
//                new Type[] {new ObjectType("java.io.Reader")},
//                Constants.INVOKESPECIAL)
//        );
//
//        LocalVariableGen lg = mg.addLocalVariable("in",
//                new ObjectType("java.io.BufferedReader"), null, null);
//
//        int in = lg.getIndex();
//        lg.setStart(il.append(new ASTORE(in))); // "i" valid from here
//
//        /*
//        * Create local variable 'name' and initialize it to 'null'
//        * */
//        lg = mg.addLocalVariable("name", Type.STRING, null, null);
//        int name = lg.getIndex();
//        il.append(InstructionConstants.ACONST_NULL);
//        lg.setStart(il.append(new ASTORE(name))); // "name" valid from here
//
//
//
//        /*
//        * Create a try-catch block
//        * */
//        InstructionHandle try_start =
//                il.append(factory.createFieldAccess("java.lang.System", "out", p_stream, Constants.GETSTATIC));
//
//        il.append(new PUSH(cp, "Please enter your name> "));
//        il.append(factory.createInvoke("java.io.PrintStream", "print", Type.VOID,
//                new Type[] { Type.STRING },
//                Constants.INVOKEVIRTUAL));
//        il.append(new ALOAD(in));
//        il.append(factory.createInvoke("java.io.BufferedReader", "readLine",
//                Type.STRING, Type.NO_ARGS,
//                Constants.INVOKEVIRTUAL));
//        il.append(new ASTORE(name));
//
//        /*
//        * Upon normal execution we jump behind expectino handler, the target address is not known yet
//        * */
//        GOTO g = new GOTO(null);
//        InstructionHandle try_end = il.append(g);
//
//        /*
//        * We add the exception handler which simply returns from the method.
//         * */
//        InstructionHandle handler = il.append(InstructionConstants.RETURN);
//        mg.addExceptionHandler(try_start, try_end, handler, ObjectType.getInstance("java.io.IOException"));
//
//        /*
//        * "Normal" code continues, now we can set the branch target of the GOTO.
//         * */
//        InstructionHandle ih =
//                il.append(factory.createFieldAccess("java.lang.System", "out", p_stream, Constants.GETSTATIC));
//        g.setTarget(ih);
//
//        /*
//        Printing "Hello": String concatenation compiles to StringBuffer operations.
//         */
//        il.append(factory.createNew(Type.STRINGBUFFER));
//        il.append(InstructionConstants.DUP);
//        il.append(new PUSH(cp, "Hello, "));
//        il.append(factory.createInvoke("java.lang.StringBuffer", "<init>",
//                Type.VOID, new Type[] { Type.STRING },
//                Constants.INVOKESPECIAL));
//        il.append(new ALOAD(name));
//        il.append(factory.createInvoke("java.lang.StringBuffer", "append",
//                Type.STRINGBUFFER, new Type[] { Type.STRING },
//                Constants.INVOKEVIRTUAL));
//        il.append(factory.createInvoke("java.lang.StringBuffer", "toString",
//                Type.STRING, Type.NO_ARGS,
//                Constants.INVOKEVIRTUAL));
//
//        il.append(factory.createInvoke("java.io.PrintStream", "println",
//                Type.VOID, new Type[] { Type.STRING },
//                Constants.INVOKEVIRTUAL));
//        il.append(InstructionConstants.RETURN);
//
//
//
//        /*
//        * Finalization: Finally, we have to set the stack size, which normally would have to be computed on the fly and
//        * add a default constructor method to the class, which is empty in this case.
//         * */
//        mg.setMaxStack();
//        cg.addMethod(mg.getMethod());
//        il.dispose(); // Allow instruction handles to be reused
//        cg.addEmptyConstructor(ACC_PUBLIC);
//
//        /*
//        * Last but not least we dump the JavaClass object to a file.
//         * */
//        try {
//            cg.getJavaClass().dump("HelloWorld.class");
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//    }
//}
//
//
///*
//import java.io.*;
//
//public class HelloWorld {
//    public static void main(String[] argv) {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        String name = null;
//
//        try {
//            System.out.print("Please enter your name> ");
//            name = in.readLine();
//        } catch (IOException e) {
//            return;
//        }
//
//        System.out.println("Hello, " + name);
//    }
//}
//*/