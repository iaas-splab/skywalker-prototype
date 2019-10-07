package de.iaas.skywalker.CodeAnalysis;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentFinder {

    private static final String FILE_PATH = "C:\\Users\\Ayhan\\OneDrive\\INFORMATIK_M_SC\\Semester4\\MA\\08_repos\\faas-migration\\ThumbnailGenerator\\Lambda\\src\\main\\java\\xyz\\cmueller\\serverless\\ThumbnailGenerationHandler.java";
    private static String LINE = "\n-------------------------------------------------------------------------------\n";

    public static void main(String[] args) throws Exception {
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));

        CodeDiscoverer cd = new CodeDiscoverer(FILE_PATH);
        cd.discoverImports();

        List<String> stmtBucket = new ArrayList<>();

        List<Node> childNodes = cu.getChildNodes();
        for (Node node : childNodes) {
            if(node instanceof ClassOrInterfaceDeclaration) {
                for(Node member : ((ClassOrInterfaceDeclaration) node).getMembers()) {
                    if (member instanceof MethodDeclaration) {
//                        BlockStmt body = (BlockStmt) ((MethodDeclaration) member).getBody();
//                        BlockStmt statements = body;
                        Optional<BlockStmt> body = ((MethodDeclaration) member).getBody();
                        NodeList<Statement> statements = body.get().getStatements();
                        statements.forEach(stmt -> {
                            System.out.println(stmt.toString());
                        });
                        System.out.println("uheuf");
                    }
                }
            }
        }

//        for (String expression : stmtBucket) {
//            if(expression.contains("input")) System.out.println(LINE + expression + LINE);
//        }


//        List<CommentReportEntry> comments = cu.getAllContainedComments()
//                .stream()
//                .map(p -> new CommentReportEntry(p.getClass().getSimpleName(),
//                        p.getContent(),
//                        p.getRange().get().begin.line,
//                        !p.getCommentedNode().isPresent()))
//                .collect(Collectors.toList());
//
//        comments.forEach(System.out::println);



        System.out.println("eijf");
    }

    private static class CommentReportEntry {
        private String type;
        private String text;
        private int lineNumber;
        private boolean isOrphan;

        CommentReportEntry(String type, String text, int lineNumber, boolean isOrphan) {
            this.type = type;
            this.text = text;
            this.lineNumber = lineNumber;
            this.isOrphan = isOrphan;
        }

        @Override
        public String toString() {
            return lineNumber + "|" + type + "|" + isOrphan + "|" + text.replaceAll("\\n", "").trim();
        }
    }

}
