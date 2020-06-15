package com.altocorp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DependenciesExtractor {

    public List<Dependency> extractDependecies(String fileName, String groupIdToIgnore) {
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        NodeList dependencyNodes = document.getElementsByTagName("dependency");

        List<Dependency> dependencies = new ArrayList<>();

        for (int i = 0; i < dependencyNodes.getLength(); i++) {
            Node dependencyNode = dependencyNodes.item(i);
            Dependency dependency = convertToDomain(dependencyNode);
            if (!dependency.getGroup().contains(groupIdToIgnore)) {
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }

    private Dependency convertToDomain(Node dependency) {
        String group = getValueOfChildNode(dependency, "groupId");
        String artifact = getValueOfChildNode(dependency, "artifactId");
        String version = getValueOfChildNode(dependency, "version");
        return new Dependency(group, artifact, version);
    }

    private String getValueOfChildNode(Node parentNode, String nameOfChildNode) {
        NodeList childNodes = ((Element) parentNode).getElementsByTagName(nameOfChildNode);
        if (childNodes.getLength() > 0) {
            return childNodes.item(0).getTextContent();
        }
        return "";
    }
}
