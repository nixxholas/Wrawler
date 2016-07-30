
import static Backend.BackendServices.loadCache;
import static Backend.Constants.jep;
import static Backend.Constants.leftPanel;
import static Backend.Constants.mainFrame;
import static Backend.Constants.mainPanel;
import static Backend.Constants.resultFrame;
import static Backend.Constants.rightPanel;
import static Backend.Constants.scrollPane;
import static Backend.Constants.searchEngines;
import static Backend.ResultObject.initializeRO;
import Backend.SearchEngine;
import java.awt.GridLayout;
import javax.swing.JFrame;
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
        
        // Initialize the components of the program
        initializeRO();
        
        // Load the Cache into memory
        loadCache();
        
        // Configure the Panel and Frame properly before use
        mainPanel.setLayout(new GridLayout(0, 2));
        leftPanel.setLayout(new GridLayout(0, 1));
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
        mainPanel.setLayout(new GridLayout(0, 2));
        leftPanel.setLayout(new GridLayout(0, 1));
        rightPanel.setLayout(new GridLayout(0, 5));

        // We then add the panel into the frame
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        resultFrame.add(mainPanel);

        // Setup the frame as well
        resultFrame.setSize(1200, 900);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jep.setSize(0, 1200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scrollPane);
        
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
