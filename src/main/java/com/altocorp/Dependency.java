package com.altocorp;

public class Dependency {

    private String group;
    private String artifact;
    private String version;

    public Dependency(String group, String artifact, String version) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
    }
}
