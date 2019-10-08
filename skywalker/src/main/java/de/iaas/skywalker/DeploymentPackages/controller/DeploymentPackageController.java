package de.iaas.skywalker.DeploymentPackages.controller;

import de.iaas.skywalker.CodeAnalysis.lambda.javalang.CodeDiscoverer;
import de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils.DiscoveryHelper;
import de.iaas.skywalker.DeploymentModels.model.AppExtractionData;
import de.iaas.skywalker.DeploymentPackages.model.DeploymentPackage;
import de.iaas.skywalker.DeploymentPackages.repository.DeploymentPackageRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/packages")
public class DeploymentPackageController {
    private DeploymentPackageRepository deploymentPackageRepository;

    public DeploymentPackageController(DeploymentPackageRepository deploymentPackageRepository) {
        this.deploymentPackageRepository = deploymentPackageRepository;
    }

    @GetMapping(path = "/")
    public Collection<DeploymentPackage> getAll() { return this.deploymentPackageRepository.findAll(); }

    @PostMapping(path = "/crawl")
    public ResponseEntity<Object> crawlFromPlatform(@RequestBody AppExtractionData appExtractionData) {
        System.out.println("ARN: " + appExtractionData.getArn() + "\n" + "Provider: " + appExtractionData.getProvider());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private String readFileToString(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
        return sb.toString();
    }

    @PostMapping(path = "/functions")
    public ResponseEntity<Object> analyzeFunctions(@RequestBody DeploymentPackage deploymentPackage) {
        if (deploymentPackage.getAnalyzed()) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();

        Map<String, String> analyzedFunctions = new HashMap<>();
        deploymentPackage.getFunctions().forEach((fnName, fnBody) -> {
            String path = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/packages/thumbnailer/src/main/java/xyz/cmueller/serverless/";
            try {
                CodeDiscoverer cd = new CodeDiscoverer(path + fnName + ".java", new ArrayList<String>(){{addAll(deploymentPackage.getFunctions().keySet());}});
                cd.discoverImports();
                cd.discoverClassMethods();
                DiscoveryHelper discoveryHelper = new DiscoveryHelper();
                File analyzedFunction = discoveryHelper.annotateHandler(path, fnName, cd.getCriticalTerms());
                analyzedFunctions.put(fnName, readFileToString(analyzedFunction.getPath().toString()));
            } catch (java.io.IOException e) { e.printStackTrace(); }
        });

        deploymentPackage.setAnalyzed(true);
        deploymentPackage.setFunctions(analyzedFunctions);
        this.deploymentPackageRepository.save(deploymentPackage);

        return ResponseEntity.status(HttpStatus.OK).build();
        }

    @PutMapping(path = "/")
    public ResponseEntity<Object> putDeploymentModel(@RequestBody DeploymentModel deploymentModel) {
//        this.deploymentPackageRepository.deleteByName(deploymentModel.getName());
//        if(this.deploymentPackageRepository.findByName(deploymentModel.getName()) != null) this.deploymentPackageRepository.save(deploymentModel);
//        String currentPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources";
//        try {
//            FileWriter fw = new FileWriter(currentPath + "\\templates\\" + deploymentModel.getName());
//            fw.write(deploymentModel.getBody());
//            fw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {}
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping(path = "/")
    public ResponseEntity<Object> deleteAll() {
        this.deploymentPackageRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
