package com.altocorp;

/**
 * Hello world!
 *
 */
public class App
{
    private static final FileTypeValidator fileTypeValidator = new FileTypeValidator();

    public static void main( String[] args )
    {
        String fileName = args[0];
        System.out.println("Your file name is: " + fileName);
        boolean fileExtensionIsXML = fileTypeValidator.fileIsXML(fileName);
        System.out.println("File extension is valid: " + fileExtensionIsXML);

        if (!fileExtensionIsXML) {
            return;
        }

        
    }
}
