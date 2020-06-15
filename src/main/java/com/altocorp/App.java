package com.altocorp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    private static final FileTypeValidator fileTypeValidator = new FileTypeValidator();
    private static final DependenciesExtractor dependenciesExtractor = new DependenciesExtractor();
    private static final DependencyDiscrepancyAssessor dependencyDiscrepancyAssessor = new DependencyDiscrepancyAssessor();
    private static final DependencyDiscrepancyScorer dependencyDiscrepancyScorer = new DependencyDiscrepancyScorer();

    public static void main( String[] args ) throws InterruptedException {
        String fileName = args[0];
        String groupIdToIgnore = args[1];
        System.out.println("Your file name is: " + fileName);
        boolean fileExtensionIsXML = fileTypeValidator.fileIsXML(fileName);
        System.out.println("File extension is valid: " + fileExtensionIsXML);

        if (!fileExtensionIsXML) {
            return;
        }

        List<Dependency> dependencies = dependenciesExtractor.extractDependecies(fileName, groupIdToIgnore);

        System.out.println("Number of dependencies: " + dependencies.size());

        List<Discrepancy> discrepancies = new ArrayList<>();
        for (Dependency dependency : dependencies) {
            System.out.println("Dependency: ");
            System.out.println("==========: ");
            System.out.println("Group: " + dependency.getGroup());
            System.out.println("Artifact: " + dependency.getArtifact());
            System.out.println("Version: " + dependency.getVersion());
            Discrepancy discrepancy = dependencyDiscrepancyAssessor.computeDiscrepancy(dependency);
            if (discrepancy != null) {
                discrepancies.add(discrepancy);
            }
            TimeUnit.SECONDS.sleep(5);
        }

        int score = dependencyDiscrepancyScorer.score(discrepancies);

        System.out.println("Your score is: " + score);
    }
}
