package de.iaas.skywalker.CodeAnalysis;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;

public class CodeDiscoverer {

    private CompilationUnit compiler;

    public CodeDiscoverer(String filePath) throws FileNotFoundException {
        this.compiler = StaticJavaParser.parse(new File(filePath));
    }


}
