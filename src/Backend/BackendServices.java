/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.PageRead.cachedResults;
import static Backend.PageRead.rightPanel;
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
