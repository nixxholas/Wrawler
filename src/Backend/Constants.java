/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Interface.MainFrame;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Home to all of the constants.
 * 
 * @author Nixholas
 */
public class Constants {
    
    // Every single SearchEngine we add will be stored and be available globally
    public static List<ResultObject> cachedResults = new ArrayList<ResultObject>();
    public static List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();
    
    /**
     * Main Queue of the whole program
     * 
     * Reasons why ArrayDeque was used
     * 
     * 3x Faster than LinkedList
     * http://microbenchmarks.appspot.com/run/limpbizkit@gmail.com/com.publicobject.blog.TreeListBenchmark
     * http://stackoverflow.com/questions/6129805/what-is-the-fastest-java-collection-with-the-basic-functionality-of-a-queue
     */
    public static Queue<ResultObject> searchQueue = new ArrayDeque<>();

    // Default JFrame of the Program
    public static MainFrame mainFrame = new MainFrame();

    /**
     * Search Result JFrame
     */
    // Create a new JFrame and set it up properly for use
    public static JFrame resultFrame = new JFrame();
    public static JPanel leftPanel = new JPanel();
    public static JPanel rightPanel = new JPanel();
    public static JEditorPane jep = new JEditorPane();
    public static JScrollPane scrollPane = new JScrollPane(jep);
    
    // Create a mainPanel for uniform layout
    public static JPanel mainPanel = new JPanel();    
        
}
