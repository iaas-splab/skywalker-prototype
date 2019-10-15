package de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiscoveryHelper {

    private static final int LINE_LIMIT = 115;
    private static final String LINE_BREAK = "\n";
    private static final String DESTINATION_PATH = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/functions/";

    public static final List<String> SDK_LOOKUP = new ArrayList<String>() {{
        add("amazonaws");
        add("amazonaws.services");
        add("lambda");
    }};

    /**
     * Aligns the annotation of the current line so that they can be viewed in standard code editors in a convenient way
     */
    private String lineAnnotation(String line, String match) {
        match = "{" + match + "}";
        int lineSpace = Math.max(5, LINE_LIMIT - line.length() - match.length());
        String annotation = " // <";
        for (int i = 0; i < lineSpace; i++) {
            annotation += "=";
        }
        return line+annotation+match;
    }

    /**
     * Annotates handler by replacing the lines which the criticalLines correspond to
     * @param filePath path of the file in the file system
     * @param fileName name of the file at the end of the passed file path
     * @param criticalLines Set of lines which were analyzed before for the passed file
     * @return File of the annotated function
     * @throws IOException
     */
    public File annotateHandlerFile(String filePath, String fileName, Set<String> criticalLines) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader(filePath + fileName + ".java"));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = buf.readLine()) != null) {
            for (String term : criticalLines) {
                line = (line.contains(term)) ? lineAnnotation(line, term) : line;
            }
            sb.append(line + LINE_BREAK);
        }
        File analyzedFunction = new File(DESTINATION_PATH + fileName + "_Annotated" + ".java");
        FileWriter fw = new FileWriter(analyzedFunction);
        fw.write(sb.toString());
        fw.close();
        return analyzedFunction;
    }

    /**
     * Annotates handler by replacing the lines which the criticalLines correspond to
     * @param function Body of the function in form of a formatted string
     * @param criticalLines Set of lines which were analyzed before for the passed function
     * @return Formatted string with the body of the annotated function
     */
    public String annotateHandlerBody(String function, Set<String> criticalLines) {
        String[] body = function.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : body) {
            for (String term : criticalLines) {
                line = (line.contains(term)) ? lineAnnotation(line, term) : line;
            }
            sb.append(line + LINE_BREAK);
        }
        return sb.toString();
    }
}
