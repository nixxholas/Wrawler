/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.PageRead.panel;
import static Interface.MainFrame.textBox;

/**
 * Universal Class Servicing Class
 * 
 * The reason why this class was created was such that each component of
 * the package Interface will have a universal "watch" class. This class will
 * be the acting "watcher" and as such, all 'emptying' methods or classes will
 * be created here so that there will be order within the project.
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
        panel.removeAll();
    }
}
