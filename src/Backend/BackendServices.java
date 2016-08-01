/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.cachedResults;
import static Backend.Constants.rightPanel;
import static Interface.MainFrame.textBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Universal Class Servicing Class
 *
 * The reason why this class was created was such that each component of the
 * package Interface will have a universal "watch" class. This class will be the
 * acting "watcher" and as such, all 'emptying' methods or classes will be
 * created here so that there will be order within the project.
 *
 * @author Nixholas
 */
public class BackendServices {    
    /**
     * Method to reset the mainFrame
     */
    public static void clearMainFrame() {
        textBox.setText("");
    }

    public static void clearSecondaryFrame() {
        rightPanel.removeAll();
    }

    public static void clearDirectory(String path) {
        File directory = new File(path);

        /**
         * Strong problem here,
         * We'll have to find a way to identify the file deleting issue.
         */
        
        // Get all files in directory
        File[] files = directory.listFiles();
        for (File file : files) {
            // Delete each file
            try {
                file.delete();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
//            
//            if (!file.delete()) {
//                // Failed to delete file
//
//                System.out.println("Failed to delete " + file);
//            }
        }
    }

    public static void loadCache() {
        /**
         * Directory Files Loader
         *
         * Adapted from:
         * http://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
         */
        File dir = new File("src/Caches");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File cachedFile : directoryListing) {
                try {
                    FileInputStream fis = new FileInputStream(cachedFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    ResultObject ro = (ResultObject) ois.readObject();
                    ois.close();
                    
                    // Add the object into the cachedResults ArrayDeque
                    cachedResults.add(ro);
                } catch (Exception ex) {
                    // Something must've went wrong
                }
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
    }

}
