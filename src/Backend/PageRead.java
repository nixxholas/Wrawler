/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Interface.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nixholas
 */
public class PageRead {

    public static List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();

    /**
     * Page Reader method adapted from SP Blackboard
     *
     * @param pageAddr
     * @return
     */
    public static StringBuilder readPage(String pageAddr) {
        try {
            URL url = new URL(pageAddr);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            reader.close();

            return sb;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new StringBuilder("");
        } catch (IOException e) {
            e.printStackTrace();
            return new StringBuilder("");
        }
    }

    /**
     * Custom Page Reader
     *
     * @param arg
     *
     * Google Link Tag RegEx (<h3 class="r">)(.+?)(<\/h3>)
     */
    public synchronized static String getUrlSource(SearchEngine se, String result) throws IOException {        
        /**
         * Note that this Regular Expression DOES take in the open AND closed 
         * inverted commas that is beside the URL.
         * 
         * This pattern is universal. Thus, we DO NOT need to store this in each
         * and every SearchEngine Object we have.
         */
        String searchObjectRegex = "(http)(.+?)(?=\")|"; //"(<a href=\"http)(.+?)(\")";
 
        // Declaring Local Variables
        String searchResult = ""; // This will be the final output
        String trimmedSearchResults = ""; // This is after the initial RegEx
        String taggedSearchResults = ""; // After removing all the useless parts of the source
        List<String> titleResults; // Stores all the Titles temporarily
        List<String> urlResults; // Stores all the URLs temporarily
        
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
        Pattern elementPattern = Pattern.compile(se.getRegexForName());
        Matcher elementMatcher = elementPattern.matcher(trimmedSearchResults);
        while(!elementMatcher.hitEnd()) {
            if (elementMatcher.find()) {
                taggedSearchResults += elementMatcher.group();
            }
        }
        
        Pattern searchObjectPattern = Pattern.compile(searchObjectRegex + se.getregexForSearchObject());
        Matcher searchObjectMatcher = searchObjectPattern.matcher(taggedSearchResults);
        while(!searchObjectMatcher.hitEnd()) {
            if (searchObjectMatcher.find()) {
                System.out.println(searchObjectMatcher.group().replaceAll("\\<.*?>",""));
            }
        }
        
        return searchResult;
    }

    public static void main(String arg[]) {
        // Initialize Google's Search Information
//        SearchEngine Google = new SearchEngine("https://www.google.com/search?q=", "(<h3 class=\"r\">)(.+?)(<\\/h3>)", "(?<=\">)(.+?)(?=</a>)", "(?<=\\)\">)(.+)(?=)");        
//        Google.setName("Google");
//        searchEngines.add(Google);

        // Initialize Bing's Search Information
        SearchEngine Bing = new SearchEngine("https://bing.com/search?q=", "(<li class=\"b_algo\">)(.+?)(<\\/li>)", "(?<=<h2>)(.+?)(?=<\\/h2>)", "(?<=<strong>)(.+?)(?=<\\/a>)");
        Bing.setName("Bing");
        searchEngines.add(Bing);

        //searchEngines.add(new SearchEngine("DuckDuckGo", "https://duckduckgo.com/?q="));
        
        MainFrame newFrame = new MainFrame();
        newFrame.setVisible(true);

    }

}
