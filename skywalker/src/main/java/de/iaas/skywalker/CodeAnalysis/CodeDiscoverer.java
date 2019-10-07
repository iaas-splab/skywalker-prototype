package de.iaas.skywalker.CodeAnalysis;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeDiscoverer {

    private CompilationUnit compiler;
    private List<String> listOfHandlers;
    private List<String> criticalStmts = new ArrayList<>();
    private Set<String> criticalTerms = new HashSet<>();

    private static List<String> sdkLookup = new ArrayList<String>() {{
        add("amazonaws");
        add("amazonaws.services");
        add("lambda");
    }};

    public CodeDiscoverer(String filePath, List<String> listOfHandlers) throws FileNotFoundException {
        this.compiler = StaticJavaParser.parse(new File(filePath));
        this.listOfHandlers = listOfHandlers;
    }

    /**
     * Returns import identifiers which implement provider specific api usage.
     * These identifiers can be used to track down expressions with provider specific
     * interface type usage, e.g.:
     * - Context (signature of Lambda handlers)
     * - S3Object (handler object for interaction with Amazon S3)
     * - ...
     */
    public void discoverImports() {
        Set<String> providerLibraries = new HashSet<>();
        NodeList<ImportDeclaration> imports = this.compiler.getImports();
        sdkLookup.forEach(term -> {
            for (ImportDeclaration importDeclaration : imports) {
                if (importDeclaration.getName().getQualifier().toString().contains(term))
                    providerLibraries.add(importDeclaration.getName().getIdentifier());
            }
        });
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

    private void collectCriticalFieldDeclarations(ClassOrInterfaceDeclaration javaClass) {
        List<FieldDeclaration> fieldDeclarations = (List<FieldDeclaration>)
                javaClass.getMembers().stream().filter(member -> member instanceof FieldDeclaration);

        for (FieldDeclaration field : fieldDeclarations) {
            this.criticalTerms.forEach(type -> {
                if (field.toString().contains(type)) {
                    this.criticalStmts.add(field.toString());
                    this.criticalTerms.add(field.getVariable(0).getNameAsString());
                }
            });
        }
    }

    public int discoverClassMethods() {
        ClassOrInterfaceDeclaration javaClass =
                (ClassOrInterfaceDeclaration) this.compiler.getChildNodes().stream()
                        .filter(x -> x instanceof ClassOrInterfaceDeclaration).collect(Collectors.toList()).get(0);

        if (!isLambdaHandler(javaClass)) return 0;

        collectCriticalFieldDeclarations(javaClass);
        // find all methods and go through them
        // // find all local fields in the method and add to criticalTerms + criticalStmts
        // // // iterate all remaining stmts (ForEach, If, etc.) and add them to criticalStmts, if suitable


        return 0;
    }
}
