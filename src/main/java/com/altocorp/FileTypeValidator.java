package com.altocorp;

public class FileTypeValidator {

    public boolean fileIsXML(String fileName) {
        String[] piecesOfFileName = fileName.split("\\.");
        int indexOfFileExtension = piecesOfFileName.length - 1;
        String fileExtension = piecesOfFileName[indexOfFileExtension];

        return fileExtension.equals("xml");
    }
}
