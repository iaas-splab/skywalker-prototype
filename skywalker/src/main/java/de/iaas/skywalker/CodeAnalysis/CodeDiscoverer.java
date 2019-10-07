package de.iaas.skywalker.CodeAnalysis;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeDiscoverer {

    private CompilationUnit compiler;

    private static List<String> sdkLookup = new ArrayList<String>() {{
        add("amazonaws");
        add("amazonaws.services");
        add("lambda");
    }};

    public CodeDiscoverer(String filePath) throws FileNotFoundException {
        this.compiler = StaticJavaParser.parse(new File(filePath));
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
}
