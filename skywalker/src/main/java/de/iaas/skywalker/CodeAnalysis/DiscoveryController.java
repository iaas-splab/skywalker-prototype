package de.iaas.skywalker.CodeAnalysis;

import de.iaas.skywalker.CodeAnalysis.utils.DiscoveryHelper;
import java.util.ArrayList;

public class DiscoveryController {

    private static String FILE_PATH = "C:\\Users\\Ayhan\\OneDrive\\INFORMATIK_M_SC\\Semester4\\MA\\08_repos\\faas-migration\\ThumbnailGenerator\\Lambda\\src\\main\\java\\xyz\\cmueller\\serverless\\";
    private static String FILE_NAME = "ThumbnailGenerationHandler.java";

    public static void main(String[] args) throws Exception {
        CodeDiscoverer cd = new CodeDiscoverer(FILE_PATH + FILE_NAME, new ArrayList<String>(){{add("ThumbnailGenerationHandler");}});
        cd.discoverImports();
        cd.discoverClassMethods();

        DiscoveryHelper discoveryHelper = new DiscoveryHelper();
        discoveryHelper.annotateHandler(FILE_PATH, FILE_NAME, cd.getCriticalTerms());
    }
}
