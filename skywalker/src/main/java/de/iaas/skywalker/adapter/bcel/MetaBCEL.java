//package de.iaas.skywalker.adapter.bcel;
//
//import org.apache.bcel.*;
//import org.apache.bcel.classfile.*;
//import org.apache.bcel.generic.*;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MetaBCEL {
//    private InstructionFactory _factory;
//    private ConstantPoolGen    _cp;
//    private ClassGen           _cg;
//
//    public static String provider = "Azure";
//    public Map<String, String> providerProperties = new HashMap<>();
//
//    public MetaBCEL() {
//        _cg = new ClassGen(
//                "de.adapter.GenericApplicationModelImpl",
//                "java.lang.Object",
//                "GenericApplicationModelImpl.java",
//                Const.ACC_PUBLIC | Const.ACC_SUPER, new String[] { "de.adapter.GenericApplicationModel" }
//        );
//
//        _cp = _cg.getConstantPool();
//        _factory = new InstructionFactory(_cg, _cp);
//    }
//
//    public void create(OutputStream out) throws IOException {
//        createMethod_0();
//        createMethod_1();
//        createMethod_2();
//        createMethod_3();
//        _cg.getJavaClass().dump(out);
//    }
//
//    private void createMethod_0() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {  }, "<init>", "de.adapter.GenericApplicationModelImpl", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_1() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "getEvents", "de.adapter.GenericApplicationModelImpl", il, _cp);
//
////        InstructionHandle ih_0 = il.append(new PUSH(_cp, "EventSources"));
//        InstructionHandle ih_0 = il.append(new PUSH(_cp, method.getName() + provider));
//        il.append(_factory.createReturn(Type.OBJECT));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//
//        InstructionList il2 = new InstructionList();
//        MethodGen method2 = new MethodGen(Const.ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "getEvents" + provider, "de.adapter.GenericApplicationModelImpl", il2, _cp);
//
//        InstructionHandle ih_02 = il2.append(new PUSH(_cp, providerProperties.get("EventSources")));
//        il2.append(_factory.createReturn(Type.OBJECT));
//        method2.setMaxStack();
//        method2.setMaxLocals();
//        _cg.addMethod(method2.getMethod());
//        il2.dispose();
//    }
//
////    private void createMethod_11() {
////        InstructionList il = new InstructionList();
////        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "getEvents" + provider, "de.adapter.GenericApplicationModelImpl", il, _cp);
////
////        InstructionHandle ih_0 = il.append(new PUSH(_cp, providerProperties.get("EventSources")));
////        il.append(_factory.createReturn(Type.OBJECT));
////        method.setMaxStack();
////        method.setMaxLocals();
////        _cg.addMethod(method.getMethod());
////        il.dispose();
////    }
//
//    private void createMethod_2() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "getInvokedServices", "de.adapter.GenericApplicationModelImpl", il, _cp);
//
//        InstructionHandle ih_0 = il.append(new PUSH(_cp, "InvokedServices"));
//        il.append(_factory.createReturn(Type.OBJECT));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_3() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "getFunction", "de.adapter.GenericApplicationModelImpl", il, _cp);
//
//        InstructionHandle ih_0 = il.append(new PUSH(_cp, "Function"));
//        il.append(_factory.createReturn(Type.OBJECT));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    public static void main(String[] args) throws Exception {
//        de.adapter.bcel.MetaBCEL creator = new de.adapter.bcel.MetaBCEL();
//        creator.create(new FileOutputStream("de.adapter.GenericApplicationModelImpl"+provider+ "_BCEL.class"));
//    }
//}
//
