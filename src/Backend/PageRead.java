/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Cacher.initializeCache;
import static Backend.ResultObject.initializeRO;
import Interface.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nixholas
 */
public class PageRead {

    // Every single SearchEngine we add will be stored and be available globally
    public static List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();

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
    
    
    /**
     * Page Reader method adapted from SP Blackboard
     *
     * Deprecated.
     * 
     * @param pageAddr
     * @return
     */
//    public static StringBuilder printPage(ResultObject inRO) {
//        try {
//            URL url = new URL(inRO.getUrl());
//            PrintWriter writer = new PrintWriter(inRO.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".html", "UTF-8");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//
//            String line, finalResult = "";
//            StringBuilder sb = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                finalResult += line + "\n";
//                sb.append(line + "\n");
//                writer.println(line);
//            }
//
//            inRO.setResultPage(finalResult);
//            reader.close();
//            writer.close();
//            return sb;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return new StringBuilder("");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new StringBuilder("");
//        }
//    }

    /**
     * Custom Page Reader
     *
     * @param se
     * 
     * SearchEngine Object that is going to be used to search.
     * 
     * @param result
     * 
     * The input that the user wants to search up on.
     * 
     * @return 
     * 
     * Returns a result to inform the Frame class if the search was successful
     * 
     * @throws java.io.IOException
     */
    public static String getUrlSource(SearchEngine se, String result) throws IOException {
        /**
         * Note that this Regular Expression DOES take in the open AND closed
         * inverted commas that is beside the URL.
         *
         * This pattern is universal. Thus, we DO NOT need to store this in each
         * and every SearchEngine Object we have.
         */
        String urlObjectRegex = "(http)(.+?)(?=\")"; //"(<a href=\"http)(.+?)(\")";
        String nameObjectRegex = se.getRegexForName();

        // Declaring Local Variables
        String searchResult = ""; // This will be the final output
        String trimmedSearchResults = ""; // This is after the initial RegEx
        String taggedSearchResults = ""; // After removing all the useless parts of the source
        List<ResultObject> Results = new ArrayList(); // Stores all the SearchObjects Temporarily
        
        //We'll need to filter the result String
        String parsedResult = result.replaceAll(" ", "+");

        URL url = new URL(se.getBaseUrl() + parsedResult);
        URLConnection urlConn = url.openConnection();
        urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }
        in.close();

        // Toss the untrimmed HTML page into an attribute of the object
        se.setPureResult(sb.toString());

        /**
         * We'll have to chop off all the sections of the HTML page which we
         * don't really need.
         */
        // Create a RegEx that removes the unrequired elements
        Pattern pattern = Pattern.compile(se.getRegexSearchPattern());
        Matcher matcher = pattern.matcher(sb);
        // Iterate through the result html page and remove the useless
        // chunks
        while (!matcher.hitEnd()) {
            if (matcher.find()) {
                trimmedSearchResults += matcher.group();
            }
        }

        /**
         * At the same time, we'll have to convert the result into an
         * Object-Oriented form for the whole program to read.
         *
         * Each object will be stored in their own individual SearchEngine
         * Objects
         */
        Pattern elementPattern = Pattern.compile(se.getregexForSearchObject());
        Matcher elementMatcher = elementPattern.matcher(trimmedSearchResults);
        while (!elementMatcher.hitEnd()) {
            if (elementMatcher.find()) {
                taggedSearchResults += elementMatcher.group();
            }
        }

        // Pattern for URLs
        Pattern searchObjectPattern = Pattern.compile(urlObjectRegex);
        Matcher searchObjectMatcher = searchObjectPattern.matcher(taggedSearchResults);

        // Create a temporary storage variable for the URLs
        List<String> urlForObjects = new ArrayList<String>();

        while (!searchObjectMatcher.hitEnd()) {
            // If we find one URL
            if (searchObjectMatcher.find()) {
                // We'll add it into the List String
                urlForObjects.add(searchObjectMatcher.group());
            }
        }

        // We then start a RegEx for the title
        Pattern nameObjectPattern = Pattern.compile(nameObjectRegex);
        Matcher nameObjectMatcher = nameObjectPattern.matcher(taggedSearchResults);

        // Put a counter for the List String we have made above.
        int counter = 0;
        while (!nameObjectMatcher.hitEnd()) {
            if (nameObjectMatcher.find()) {
                // .replaceAll trims the remaining HTML tags if they exist.
                Results.add(new ResultObject(nameObjectMatcher.group().replaceAll("\\<.*?>", ""), urlForObjects.get(counter), ""));
                // Parse the data in order for returning
                searchResult += nameObjectMatcher.group().replaceAll("\\<.*?>", "") + "\t" + urlForObjects.get(counter) + "\n";
                counter++;
            }
        }

        /**
         * Devise a way to dynamically create a JFrame with it's panel and to
         * preload data that we have parsed from the Search Engine
         *
         * And hyperlink each of the given link we have extracted from the HTML
         * search result. Concurrently, we will also download and save the
         * target link as an individual HTML Page by itself so that our can
         * utilize it if needed.
         */
        
        // Multi-Threading system for the pulling of views online        
        ExecutorService threadPoolExecutor =
        new ThreadPoolExecutor(
                Results.size(),
                Results.size(),
                600000, // Give the program a decent amount of time
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()
                );
        
        
        for (ResultObject RO : Results) {            
            
            threadPoolExecutor.execute(RO);
            
            // Save each of the URLs as files.
            //printPage(RO);

            JButton button = new JButton(RO.getName());

            /**
             * Action Listener adapted from:
             * 
             * http://alvinalexander.com/java/jbutton-listener-pressed-actionlistener
             */
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    /**
                     * Webpage Viewer within the Java Project
                     * 
                     * http://stackoverflow.com/questions/10601676/display-a-webpage-inside-a-swing-application
                     */

                    try {
                        // Required for us to load from the file we have created.
                        URL url = getClass().getResource(RO.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".html");
                                                
                        //jep.setText("<html>" + RO.getResultPage() + "<html>");
                        
                        // We have yet to load the file directly
                        jep.setPage(RO.getUrl());
                        mainFrame.pack();
                    } catch (Exception ex) {
                        jep.setContentType("text/html");
                        jep.setText("<html>Could not load the page.</html>");
                    }
                }
            });
            
            rightPanel.add(button);
        }
        
        // Awaits for all threads to complete before wrappin' up
        threadPoolExecutor.shutdown();
        
        return searchResult;
    }   

}
