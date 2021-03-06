/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.addToQueue;
import static Backend.Constants.numberOfResults;
import static Backend.Constants.queueHasResult;
import static Backend.Constants.resultsCounter;
import static Backend.ResultObject.findInCache;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        // Toss the untrimmed HTML page into an attribute of the object
        se.setPureResult(sb.toString());

        in.close();
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

        int counter = 0;
        while (!nameObjectMatcher.hitEnd()) {
            // If the URL result does not exist, we'll add it in
            if (nameObjectMatcher.find()) {
                ResultObject currentResult = new ResultObject(nameObjectMatcher.group().replaceAll("\\<.*?>", ""), urlForObjects.get(counter), result, false);

                // .replaceAll trims the remaining HTML tags if they exist.
                // Add the search result into the searchEngine object's own Queue
                se.addResult(currentResult);

                // Check with the cachedResults
                if (!findInCache(currentResult) && resultsCounter.getCount() < numberOfResults) {  // Since it has been found and has been added, move on

                    // But if it's here, means it's not cached.
                    // Add the result into the searchQueue as well if it isn't cached
                    if (!queueHasResult(currentResult) && resultsCounter.getCount() < numberOfResults) {
                        // If queue does not have it, we add it and if there adequate results
                        addToQueue(currentResult);
                        resultsCounter.incrementCount();
                    }
                }

                // Parse the data in order for returning
                searchResult += nameObjectMatcher.group().replaceAll("\\<.*?>", "") + "\t" + urlForObjects.get(counter) + "\n";
                counter++;
            }
        }

        return searchResult;
    }

}
