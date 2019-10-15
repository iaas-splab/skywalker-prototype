package de.iaas.skywalker.CodeAnalysis.lambda.javalang;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils.DiscoveryHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeDiscoverer {

    private CompilationUnit compiler;
    private List<String> listOfHandlers;
    private Set<String> criticalStatements = new HashSet<>();
    private Set<String> criticalTerms = new HashSet<>();
    private DiscoveryHelper helper = new DiscoveryHelper();

    public CodeDiscoverer(String function, List<String> listOfHandlers) {
        this.compiler = StaticJavaParser.parse(function);
        this.listOfHandlers = listOfHandlers;
    }

    public Set<String> getCriticalTerms() {
        return criticalTerms;
    }

    public void setCriticalTerms(Set<String> criticalTerms) {
        this.criticalTerms = criticalTerms;
    }

    /**
     * Sets import identifiers which implement provider specific api usage as critical terms.
     * These identifiers can be used to track down expressions with provider specific
     * interface type usage, e.g.:
     * - Context (signature of lambda handlers)
     * - S3Object (handler object for interaction with Amazon S3)
     * - ...
     */
    public void discoverImports() {
        NodeList<ImportDeclaration> imports = this.compiler.getImports();
        helper.SDK_LOOKUP.forEach(term -> {
            for (ImportDeclaration importDeclaration : imports) {
                if (importDeclaration.getName().getQualifier().toString().contains(term))
                    this.criticalTerms.add(importDeclaration.getName().getIdentifier());
            }
        });
    }

    /**
     *
     */
    public void discoverClassMethods() {
        ClassOrInterfaceDeclaration javaClass =
                (ClassOrInterfaceDeclaration) this.compiler.getChildNodes().stream()
                        .filter(x -> x instanceof ClassOrInterfaceDeclaration).collect(Collectors.toList()).get(0);

        if (!isLambdaHandler(javaClass)) {/*nothing yet*/}

        collectCriticalFieldDeclarations(javaClass);
        List<MethodDeclaration> classMethods = getClassMethods(javaClass);
        classMethods.forEach(method -> discoverClassMethod(method));
    }

    private boolean isLambdaHandler(ClassOrInterfaceDeclaration javaClass) {
        boolean implementsInterface =
                (!javaClass.getImplementedTypes().stream()
                        .filter(x -> x.getName().toString().equals("RequestHandler"))
                        .collect(Collectors.toList())
                        .isEmpty());
        boolean isHandler = this.listOfHandlers.contains(javaClass.getNameAsString());
        return isHandler && implementsInterface;
    }

    /**
     * Discovery of field declarations in a class declaration which can be associated with the usage of provider-specific
     * SDK.
     * @param javaClass to be investigated
     */
    private void collectCriticalFieldDeclarations(ClassOrInterfaceDeclaration javaClass) {
        List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
        for (BodyDeclaration member: javaClass.getMembers()) {
            if (member instanceof FieldDeclaration) fieldDeclarations.add((FieldDeclaration) member);
        }
        for (FieldDeclaration field : fieldDeclarations) {
            Set<String> buffer = new HashSet<>();
            this.criticalTerms.forEach(type -> {
                if (field.toString().contains(type)) {
                    this.criticalStatements.add(field.toString());
                    buffer.add(field.getVariable(0).getNameAsString());
                }
            });
            this.criticalTerms.addAll(buffer);
        }
    }

    /**
     * Discovery of field declarations in a method of the current class declaration which can be associated with the
     * usage of provider-specific SDK.
     * @param method to be investigated
     */
    private void collectCriticalClassMethodFieldDeclarations(MethodDeclaration method) {
        NodeList<Statement> statements = method.getBody().get().getStatements();
        Set<String> buffer = new HashSet<>();
        for (Statement statement : statements) {
            if (statement instanceof ExpressionStmt) {
                this.criticalTerms.forEach(type -> {
                    if (statement.toString().contains(type)) {
                        this.criticalStatements.add(statement.toString());
                        buffer.add(((VariableDeclarationExpr)
                                ((ExpressionStmt) statement).getExpression()).getVariables().get(0).getNameAsString());
                    }
                });
            }
        }
        this.criticalTerms.addAll(buffer);
    }

    /**
     * Analyze the method body and extract the lines which contain potential provider-specific SDK usage.
     * @param method to be analyzed
     */
    private void discoverClassMethodBody(MethodDeclaration method) {
        NodeList<Statement> statements = method.getBody().get().getStatements();
        for (Statement statement : statements) {
            if (!(statement instanceof ExpressionStmt)) {
                this.criticalTerms.forEach(type -> {
                    if (statement.toString().contains(type)) {
                        this.criticalStatements.add(statement.toString());
                    }
                });
            }
        }
    }

    private List<MethodDeclaration> getClassMethods(ClassOrInterfaceDeclaration javaClass) {
        List<MethodDeclaration> classMethods = new ArrayList<>();
        for (BodyDeclaration member: javaClass.getMembers()) {
            if (member instanceof MethodDeclaration) classMethods.add((MethodDeclaration) member);
        }
        return classMethods;
    }

    /**
     * Checks if the method is an implementation by checking its annotations (for @Override)
     * @param method to be checked
     * @return true if annotation @Override exists for current method, false otherwise
     */
    private boolean isImplementedMethod(MethodDeclaration method) {
        if (method.getAnnotations().isEmpty()) return false;
        else if (method.getAnnotations().contains("@Override")) return true;
        else return false;
    }

    /**
     * Discover potential provider-specific SDK usage in the current method header's parameters
     * @param method to be checked for passed provider-specific parameters
     */
    private void checkClassMethodHeader(MethodDeclaration method) {
        Set<String> buffer = new HashSet<>();
        for (String term : this.getCriticalTerms()) {
            method.getParameters().forEach(param -> {
                if (param.getType().equals(term)) {
                    buffer.add(param.getNameAsString());
                }
            });
        }
        this.criticalTerms.addAll(buffer);
    }

    private void discoverClassMethod(MethodDeclaration method) {
        if (isImplementedMethod(method)){/*nothing yet*/}
        checkClassMethodHeader(method);
        collectCriticalClassMethodFieldDeclarations(method);
        discoverClassMethodBody(method);
    }
}
