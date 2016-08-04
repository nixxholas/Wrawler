/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Interface.MainFrame;
import Interface.Settings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Home to all of the constants.
 *
 * @author Nixholas
 */
public class Constants {

    // Every single SearchEngine we add will be stored and be available globally
    public static Queue<ResultObject> cachedResults = new ArrayDeque<ResultObject>();
    public static List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();

    /**
     * Main Queue of the whole program
     *
     * Reasons why ArrayDeque was used
     *
     * 3x Faster than LinkedList
     * http://microbenchmarks.appspot.com/run/limpbizkit@gmail.com/com.publicobject.blog.TreeListBenchmark
     * http://stackoverflow.com/questions/6129805/what-is-the-fastest-java-collection-with-the-basic-functionality-of-a-queue
     *
     * Switched to ConcurrentLinkedQueue
     *
     * http://stackoverflow.com/questions/616484/how-to-use-concurrentlinkedqueue
     */
    public static Queue<ResultObject> searchQueue = new ConcurrentLinkedQueue<>();

    // ------------------- JFrame Elements --------------------- //
    // Default JFrame of the Program
    public static MainFrame mainFrame = new MainFrame();
    public static ConcurrentInt btnCounter = new ConcurrentInt();
    public static ToughBoolean toughBoolean = new ToughBoolean();

    // Settings JFrame
    public static Settings settingsFrame = new Settings();

    /**
     * Search Result JFrame
     */
    // Create a new JFrame and set it up properly for use
    public static JFrame resultFrame = new JFrame();
    public static JPanel leftPanel = new JPanel();
    public static JPanel rightPanel = new JPanel();
    public static JEditorPane jep = new JEditorPane();
    public static JTextArea jepPure = new JTextArea();
    public static JScrollPane scrollPane = new JScrollPane(jep);
    public static JScrollPane scrollPanePure = new JScrollPane(jepPure);

    // Create a mainPanel for uniform layout
    public static JPanel mainPanel = new JPanel();

    // Global variables for progres    
    public static int progressRate;
    public static int actualProgress = 0;

    // Map Constants
    public static Hashtable<Integer, JLabel> searchResultsSliderTable = new Hashtable<Integer, JLabel>(); // HashTable for ResultsSlider in Settings JFrame

    // Global Variables for searchResultsSlider
    public static int numberOfResults = 12; // 12 Shall be the default
}
