package de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiscoveryHelper {

    private static final int LINE_LIMIT = 115;
    private static final String LINE_BREAK = "\n";
    private static final String DESTINATION_PATH = "C:\\Users\\Ayhan\\OneDrive\\INFORMATIK_M_SC\\Semester4\\MA\\08_repos\\skywalker-prototype\\skywalker\\src\\main\\resources\\functions\\";

    public static final List<String> SDK_LOOKUP = new ArrayList<String>() {{
        add("amazonaws");
        add("amazonaws.services");
        add("lambda");
    }};

    private String lineAnnotation(String line, String match) {
        match = "{" + match + "}";
        int lineSpace = Math.max(5, LINE_LIMIT - line.length() - match.length());
        String annotation = " // <";
        for (int i = 0; i < lineSpace; i++) {
            annotation += "=";
        }
        return line+annotation+match;
    }

    public void annotateHandler(String filePath, String fileName, Set<String> criticalLines) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader(filePath + fileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = buf.readLine()) != null) {
            for (String term : criticalLines) {
                line = (line.contains(term)) ? lineAnnotation(line, term) : line;
            }
            sb.append(line + LINE_BREAK);
        }

        FileWriter fw = new FileWriter(new File(DESTINATION_PATH + fileName));
        fw.write(sb.toString());
        fw.close();
    }
}
