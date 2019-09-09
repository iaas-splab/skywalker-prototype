//package de.iaas.skywalker.adapter.bcel;
//
//import org.apache.bcel.*;
//import org.apache.bcel.classfile.*;
//import org.apache.bcel.generic.*;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class MetaBCELifier {
//    private InstructionFactory _factory;
//    private ConstantPoolGen    _cp;
//    private ClassGen           _cg;
//
//    public MetaBCELifier() {
//        _cg = new ClassGen("de.adapter.bcel.BCELifierGeneratedBCEL", "java.lang.Object", "BCELifierGeneratedBCEL.java", Const.ACC_PUBLIC | Const.ACC_SUPER, new String[] {  });
//
//        _cp = _cg.getConstantPool();
//        _factory = new InstructionFactory(_cg, _cp);
//    }
//
//    public void create(OutputStream out) throws IOException {
//        createFields();
//        createMethod_0();
//        createMethod_1();
//        createMethod_2();
//        createMethod_3();
//        createMethod_4();
//        createMethod_5();
//        createMethod_6();
//        _cg.getJavaClass().dump(out);
//    }
//
//    private void createFields() {
//        FieldGen field;
//
//        field = new FieldGen(Const.ACC_PRIVATE, new ObjectType("org.apache.bcel.generic.InstructionFactory"), "_factory", _cp);
//        _cg.addField(field.getField());
//
//        field = new FieldGen(Const.ACC_PRIVATE, new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), "_cp", _cp);
//        _cg.addField(field.getField());
//
//        field = new FieldGen(Const.ACC_PRIVATE, new ObjectType("org.apache.bcel.generic.ClassGen"), "_cg", _cp);
//        _cg.addField(field.getField());
//    }
//
//    private void createMethod_0() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {  }, "<init>", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        InstructionHandle ih_4 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createNew("org.apache.bcel.generic.ClassGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(new PUSH(_cp, "java.lang.Object"));
//        il.append(new PUSH(_cp, "GenericApplicationModelImpl.java"));
//        il.append(new PUSH(_cp, 33));
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 0));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModel"));
//        il.append(InstructionConst.AASTORE);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "<init>", Type.VOID, new Type[] { Type.STRING, Type.STRING, Type.STRING, Type.INT, new ArrayType(Type.STRING, 1) }, Const.INVOKESPECIAL));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.PUTFIELD));
//        InstructionHandle ih_32 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "getConstantPool", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.PUTFIELD));
//        InstructionHandle ih_43 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createNew("org.apache.bcel.generic.InstructionFactory"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "<init>", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.generic.ClassGen"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.PUTFIELD));
//        InstructionHandle ih_62 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_1() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType("java.io.OutputStream") }, new String[] { "arg0" }, "create", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "createMethod_0", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        InstructionHandle ih_4 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "createMethod_1", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        InstructionHandle ih_8 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "createMethod_2", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        InstructionHandle ih_12 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "createMethod_3", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        InstructionHandle ih_16 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "getJavaClass", new ObjectType("org.apache.bcel.classfile.JavaClass"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.classfile.JavaClass", "dump", Type.VOID, new Type[] { new ObjectType("java.io.OutputStream") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_27 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_2() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PRIVATE, Type.VOID, Type.NO_ARGS, new String[] {  }, "createMethod_0", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("org.apache.bcel.generic.InstructionList"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//        InstructionHandle ih_8 = il.append(_factory.createNew("org.apache.bcel.generic.MethodGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "VOID", new ObjectType("org.apache.bcel.generic.BasicType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(new PUSH(_cp, "<init>"));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "<init>", Type.VOID, new Type[] { Type.INT, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), new ArrayType(Type.STRING, 1), Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.InstructionList"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 2));
//        InstructionHandle ih_36 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "OBJECT", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createLoad", new ObjectType("org.apache.bcel.generic.LocalVariableInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type"), Type.INT }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createStore(Type.OBJECT, 3));
//        InstructionHandle ih_53 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(new PUSH(_cp, "java.lang.Object"));
//        il.append(new PUSH(_cp, "<init>"));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "VOID", new ObjectType("org.apache.bcel.generic.BasicType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 183));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createInvoke", new ObjectType("org.apache.bcel.generic.InvokeInstruction"), new Type[] { Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Type.SHORT }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_78 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "VOID", new ObjectType("org.apache.bcel.generic.BasicType"), Const.GETSTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createReturn", new ObjectType("org.apache.bcel.generic.ReturnInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type") }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_94 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxStack", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_98 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxLocals", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_102 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "getMethod", new ObjectType("org.apache.bcel.classfile.Method"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "addMethod", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.classfile.Method") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_113 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "dispose", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_117 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_3() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PRIVATE, Type.VOID, Type.NO_ARGS, new String[] {  }, "createMethod_1", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("org.apache.bcel.generic.InstructionList"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//
//        InstructionHandle ih_8 = il.append(_factory.createNew("org.apache.bcel.generic.MethodGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "STRING", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(new PUSH(_cp, "getEvents"));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "<init>", Type.VOID, new Type[] { Type.INT, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), new ArrayType(Type.STRING, 1), Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.InstructionList"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 2));
//        InstructionHandle ih_36 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createNew("org.apache.bcel.generic.PUSH"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(new PUSH(_cp, "EventSources"));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.PUSH", "<init>", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Type.STRING }, Const.INVOKESPECIAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.CompoundInstruction") }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createStore(Type.OBJECT, 3));
//        InstructionHandle ih_54 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "OBJECT", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createReturn", new ObjectType("org.apache.bcel.generic.ReturnInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type") }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_70 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxStack", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_74 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxLocals", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_78 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "getMethod", new ObjectType("org.apache.bcel.classfile.Method"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "addMethod", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.classfile.Method") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_89 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "dispose", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_93 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_33() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PRIVATE, Type.VOID, Type.NO_ARGS, new String[] {  }, "createMethod_1", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("org.apache.bcel.generic.InstructionList"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//        InstructionHandle ih_8 = il.append(_factory.createNew("org.apache.bcel.generic.MethodGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "STRING", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(new PUSH(_cp, "getEvents"));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "<init>", Type.VOID, new Type[] { Type.INT, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), new ArrayType(Type.STRING, 1), Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.InstructionList"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 2));
//        InstructionHandle ih_36 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createNew("org.apache.bcel.generic.PUSH"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(new PUSH(_cp, "EventSources"));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.PUSH", "<init>", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Type.STRING }, Const.INVOKESPECIAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.CompoundInstruction") }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createStore(Type.OBJECT, 3));
//        InstructionHandle ih_54 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "OBJECT", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createReturn", new ObjectType("org.apache.bcel.generic.ReturnInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type") }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_70 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxStack", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_74 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxLocals", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_78 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "getMethod", new ObjectType("org.apache.bcel.classfile.Method"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "addMethod", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.classfile.Method") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_89 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "dispose", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_93 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_4() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PRIVATE, Type.VOID, Type.NO_ARGS, new String[] {  }, "createMethod_2", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("org.apache.bcel.generic.InstructionList"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//        InstructionHandle ih_8 = il.append(_factory.createNew("org.apache.bcel.generic.MethodGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "STRING", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(new PUSH(_cp, "getInvokedServices"));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "<init>", Type.VOID, new Type[] { Type.INT, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), new ArrayType(Type.STRING, 1), Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.InstructionList"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 2));
//        InstructionHandle ih_36 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createNew("org.apache.bcel.generic.PUSH"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(new PUSH(_cp, "InvokedServices"));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.PUSH", "<init>", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Type.STRING }, Const.INVOKESPECIAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.CompoundInstruction") }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createStore(Type.OBJECT, 3));
//        InstructionHandle ih_54 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "OBJECT", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createReturn", new ObjectType("org.apache.bcel.generic.ReturnInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type") }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_70 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxStack", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_74 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxLocals", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_78 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "getMethod", new ObjectType("org.apache.bcel.classfile.Method"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "addMethod", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.classfile.Method") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_89 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "dispose", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_93 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_5() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PRIVATE, Type.VOID, Type.NO_ARGS, new String[] {  }, "createMethod_3", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("org.apache.bcel.generic.InstructionList"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//        InstructionHandle ih_8 = il.append(_factory.createNew("org.apache.bcel.generic.MethodGen"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, 1));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "STRING", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "NO_ARGS", new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), Const.GETSTATIC));
//        il.append(new PUSH(_cp, 0));
//        il.append(_factory.createNewArray(Type.STRING, (short) 1));
//        il.append(new PUSH(_cp, "getFunction"));
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl"));
//        il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "<init>", Type.VOID, new Type[] { Type.INT, new ObjectType("org.apache.bcel.generic.Type"), new ArrayType(new ObjectType("org.apache.bcel.generic.Type"), 1), new ArrayType(Type.STRING, 1), Type.STRING, Type.STRING, new ObjectType("org.apache.bcel.generic.InstructionList"), new ObjectType("org.apache.bcel.generic.ConstantPoolGen") }, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 2));
//        InstructionHandle ih_36 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createNew("org.apache.bcel.generic.PUSH"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cp", new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Const.GETFIELD));
//        il.append(new PUSH(_cp, "Function"));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.PUSH", "<init>", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.generic.ConstantPoolGen"), Type.STRING }, Const.INVOKESPECIAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.CompoundInstruction") }, Const.INVOKEVIRTUAL));
//        il.append(_factory.createStore(Type.OBJECT, 3));
//        InstructionHandle ih_54 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_factory", new ObjectType("org.apache.bcel.generic.InstructionFactory"), Const.GETFIELD));
//        il.append(InstructionConst.POP);
//        il.append(_factory.createFieldAccess("org.apache.bcel.generic.Type", "OBJECT", new ObjectType("org.apache.bcel.generic.ObjectType"), Const.GETSTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionFactory", "createReturn", new ObjectType("org.apache.bcel.generic.ReturnInstruction"), new Type[] { new ObjectType("org.apache.bcel.generic.Type") }, Const.INVOKESTATIC));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "append", new ObjectType("org.apache.bcel.generic.InstructionHandle"), new Type[] { new ObjectType("org.apache.bcel.generic.Instruction") }, Const.INVOKEVIRTUAL));
//        il.append(InstructionConst.POP);
//        InstructionHandle ih_70 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxStack", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_74 = il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "setMaxLocals", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_78 = il.append(_factory.createLoad(Type.OBJECT, 0));
//        il.append(_factory.createFieldAccess("de.adapter.bcel.BCELifierGeneratedBCEL", "_cg", new ObjectType("org.apache.bcel.generic.ClassGen"), Const.GETFIELD));
//        il.append(_factory.createLoad(Type.OBJECT, 2));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.MethodGen", "getMethod", new ObjectType("org.apache.bcel.classfile.Method"), Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.ClassGen", "addMethod", Type.VOID, new Type[] { new ObjectType("org.apache.bcel.classfile.Method") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_89 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createInvoke("org.apache.bcel.generic.InstructionList", "dispose", Type.VOID, Type.NO_ARGS, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_93 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    private void createMethod_6() {
//        InstructionList il = new InstructionList();
//        MethodGen method = new MethodGen(Const.ACC_PUBLIC | Const.ACC_STATIC, Type.VOID, new Type[] { new ArrayType(Type.STRING, 1) }, new String[] { "arg0" }, "main", "de.adapter.bcel.BCELifierGeneratedBCEL", il, _cp);
//
//        InstructionHandle ih_0 = il.append(_factory.createNew("de.adapter.bcel.BCELifierGeneratedBCEL"));
//        il.append(InstructionConst.DUP);
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "<init>", Type.VOID, Type.NO_ARGS, Const.INVOKESPECIAL));
//        il.append(_factory.createStore(Type.OBJECT, 1));
//        InstructionHandle ih_8 = il.append(_factory.createLoad(Type.OBJECT, 1));
//        il.append(_factory.createNew("java.io.FileOutputStream"));
//        il.append(InstructionConst.DUP);
//        il.append(new PUSH(_cp, "de.adapter.GenericApplicationModelImpl.class"));
//        il.append(_factory.createInvoke("java.io.FileOutputStream", "<init>", Type.VOID, new Type[] { Type.STRING }, Const.INVOKESPECIAL));
//        il.append(_factory.createInvoke("de.adapter.bcel.BCELifierGeneratedBCEL", "create", Type.VOID, new Type[] { new ObjectType("java.io.OutputStream") }, Const.INVOKEVIRTUAL));
//        InstructionHandle ih_21 = il.append(_factory.createReturn(Type.VOID));
//        method.setMaxStack();
//        method.setMaxLocals();
//        _cg.addMethod(method.getMethod());
//        il.dispose();
//    }
//
//    public static void main(String[] args) throws Exception {
//        de.adapter.bcel.MetaBCELifier creator = new de.adapter.bcel.MetaBCELifier();
//        creator.create(new FileOutputStream("de.adapter.bcel.BCELifierGeneratedBCEL.class"));
//    }
//}