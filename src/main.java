
import static Backend.BackendServices.loadCache;
import static Backend.Constants.getSearchQueue;
import static Backend.Constants.jep;
import static Backend.Constants.jepPure;
import static Backend.Constants.leftPanel;
import static Backend.Constants.mainFrame;
import static Backend.Constants.mainPanel;
import static Backend.Constants.resultFrame;
import static Backend.Constants.rightPanel;
import static Backend.Constants.scrollPane;
import static Backend.Constants.scrollPanePure;
import static Backend.Constants.searchEngines;
import static Backend.Constants.searchResultsSliderTable;
import static Backend.Constants.settingsFrame;
import Backend.ResultObject;
import static Backend.ResultObject.initializeRO;
import Backend.SearchEngine;
import static Interface.Settings.searchResultsSlider;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nixholas
 */
public class main {

    public static void main(String arg[]) {
        // ----------------- START OF PROGRAM SETUP -------------------------- //

        // Data Constants
    
        // ResultsSlider Table Data Entries
        searchResultsSliderTable.put (1, new JLabel("1"));
        searchResultsSliderTable.put (2, new JLabel("2"));
        searchResultsSliderTable.put (3, new JLabel("3"));
        searchResultsSliderTable.put (4, new JLabel("4"));
        searchResultsSliderTable.put (5, new JLabel("5"));
        searchResultsSliderTable.put (6, new JLabel("6"));
        searchResultsSliderTable.put (7, new JLabel("7"));
        searchResultsSliderTable.put (8, new JLabel("8"));
        searchResultsSliderTable.put (9, new JLabel("9"));
        searchResultsSliderTable.put (10, new JLabel("10"));
        searchResultsSliderTable.put (11, new JLabel("11"));
        searchResultsSliderTable.put (12, new JLabel("12"));
        searchResultsSliderTable.put (13, new JLabel("13"));
        searchResultsSliderTable.put (14, new JLabel("14"));
        searchResultsSliderTable.put (15, new JLabel("15"));
        searchResultsSliderTable.put (16, new JLabel("16"));
        searchResultsSliderTable.put (17, new JLabel("17"));
        searchResultsSliderTable.put (18, new JLabel("18"));
        searchResultsSliderTable.put (19, new JLabel("19"));
        searchResultsSliderTable.put (20, new JLabel("20"));
        searchResultsSlider.setPaintLabels(true);
        searchResultsSlider.setLabelTable(searchResultsSliderTable);
        
        
        // Initialize the components of the program
        initializeRO();

        // Load the Cache into memory
        loadCache();

        // Configure the Panel and Frame properly before use
        mainPanel.setLayout(new GridLayout(0, 2));
        leftPanel.setLayout(new GridLayout(0, 2));
        rightPanel.setLayout(new GridLayout(0, 5));

        // We then add the panel into the frame
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        resultFrame.add(mainPanel);

        // Setup the frame as well
        resultFrame.setSize(1200, 900);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup the browser view
        jep.setEditable(false);
        jepPure.setEditable(false);
        jep.setContentType("text/html");

        // We then add the panel into the frame
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        resultFrame.add(mainPanel);

        // Setup the frame as well
        resultFrame.setSize(1200, 900);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        resultFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                resultFrame.setVisible(false);
                rightPanel.removeAll();
                rightPanel.revalidate();
                rightPanel.repaint();

                // Remove the current search results
                for (ResultObject ro : getSearchQueue()) {
                    getSearchQueue().remove(ro);
                }
            }
        });

        jep.setSize(0, 1200);
        jepPure.setSize(0, 1200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePure.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePure.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(scrollPane);
        leftPanel.add(scrollPanePure);
        
        // Settings Frame Settings
        settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        settingsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                jep.setText("");
                jepPure.setText("");
            }
        });

        // -------------------- END OF PROGRAM SETUP --------------------- //
        // Initialize Google's Search Information
        SearchEngine Google = new SearchEngine("https://www.google.com/search?q=", "(<h3 class=\"r\">)(.+?)(<\\/h3>)", "(?<=\">)(.+?)(?=<a)", "(?<=\\)\\\">)(.+?)(?=<)");
        Google.setName("Google");
        searchEngines.add(Google);

        // Initialize Bing's Search Information
        SearchEngine Bing = new SearchEngine("https://bing.com/search?q=", "(<li class=\"b_algo\">)(.+?)(<\\/li>)", "(?<=<h2>)(.+?)(?=<\\/h2>)", "(?<=\">)(.+?)(?=<\\/a>)");
        Bing.setName("Bing");
        searchEngines.add(Bing);

        //searchEngines.add(new SearchEngine("DuckDuckGo", "https://duckduckgo.com/?q="));
        // Have yet to add Opera Search
        // Have yet to add Yahoo Search
        mainFrame.setVisible(true);
    }
}
