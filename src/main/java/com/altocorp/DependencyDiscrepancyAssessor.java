package com.altocorp;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class DependencyDiscrepancyAssessor {

    private static final int MAJOR_INDEX = 0;
    private static final int MINOR_INDEX = 1;
    private static final int PATCH_INDEX = 2;

    private RestTemplate restTemplate = new RestTemplate();

    public Discrepancy computeDiscrepancy(Dependency dependency) {
        if (dependency.getVersion().equals("")) {
            return null;
        }
        String forObject = restTemplate.getForObject("https://mvnrepository.com/artifact/" + dependency.getGroup() + "/" + dependency.getArtifact() + "/" + dependency.getVersion(), String.class);

        if (forObject != null) {
            int index = forObject.indexOf("There is a new version for this artifact");
            if (index > 0) {
                String substring = forObject.substring(index);
                int i = substring.indexOf("</a>");
                substring = substring.substring(0, i);
                String[] split = substring.split(">");
                String latestVersion = split[split.length - 1];
                System.out.println("Latest Version: " + latestVersion);
                return calculateVersionDiscrepancy(dependency.getVersion(), latestVersion);
            }
        }
        return null;
    }

    private Discrepancy calculateVersionDiscrepancy(String currentVersion, String latestVersion) {
        String[] currentVersionParts = currentVersion.split("\\.");
        String[] latestVersionParts = latestVersion.split("\\.");

        if (currentVersionParts.length < 3) {
            return null;
        }

        int currentMajorVersion = Integer.parseInt(currentVersionParts[MAJOR_INDEX]);
        int latestMajorVersion = Integer.parseInt(latestVersionParts[MAJOR_INDEX]);

        int majorDiscrepancy = latestMajorVersion - currentMajorVersion;

        if (majorDiscrepancy > 0) {
            return new Discrepancy(DiscrepancyType.MAJOR, majorDiscrepancy);
        }

        int currentMinorVersion = Integer.parseInt(currentVersionParts[MINOR_INDEX]);
        int latestMinorVersion = Integer.parseInt(latestVersionParts[MINOR_INDEX]);

        int minorDiscrepancy = latestMinorVersion - currentMinorVersion;

        if (minorDiscrepancy > 0) {
            return new Discrepancy(DiscrepancyType.MINOR, minorDiscrepancy);
        }

        if (currentVersionParts.length > 2 && latestVersionParts.length > 2) {
            int currentPatchVersion = Integer.parseInt(currentVersionParts[PATCH_INDEX]);
            int latestPatchVersion = Integer.parseInt(latestVersionParts[PATCH_INDEX]);

            int patchDiscrepancy = latestPatchVersion - currentPatchVersion;

            return new Discrepancy(DiscrepancyType.PATCH, patchDiscrepancy);
        }

        int discrepancyMagnitude = Integer.parseInt(latestVersionParts[PATCH_INDEX]);
        return new Discrepancy(DiscrepancyType.PATCH, discrepancyMagnitude);
    }

}
