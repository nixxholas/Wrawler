/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.searchQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author nixho
 */
public class SearchEngine extends Thread implements Runnable {

    private String baseUrl;
    private String regexSearchPattern;
    private String regexForName;
    // private String regexForURL; We do not need this attribute anymore.
    private String regexForSearchObject;
    private String pureResult; // Stores the actual search result
    private String trimmedResult; // Stores the search result after RegEx trimming
    private List<ResultObject> results = new ArrayList<>();
    //private List<String> resultTitle; // Stores all the titles of the result
    //private List<String> resultUrl; // Stores all the URLs of the result
    //private List<String> resultDescription; // Stores all the descriptions of the result

    public String getRegexForName() {
        return regexForName;
    }

    public void setRegexForName(String regexForName) {
        this.regexForName = regexForName;
    }

//    public String getRegexForURL() {
//        return regexForURL;
//    }
//
//    public void setRegexForURL(String regexForURL) {
//        this.regexForURL = regexForURL;
//    }
    public String getregexForSearchObject() {
        return regexForSearchObject;
    }

    public void setregexForSearchObject(String regexForSearchObject) {
        this.regexForSearchObject = regexForSearchObject;
    }

    public List<ResultObject> getResults() {
        return results;
    }

    public void addResult(ResultObject ro) {
        this.results.add(ro);
    }

    public void removeResult(ResultObject ro) {
        this.results.remove(ro);
    }

    public SearchEngine(
            String baseUrl,
            String regexSearchPattern,
            String regexForSearchObject,
            String regexForName) {
        this.baseUrl = baseUrl;
        this.regexSearchPattern = regexSearchPattern;
        this.regexForSearchObject = regexForSearchObject;
        this.regexForName = regexForName;
    }

    public String getPureResult() {
        return pureResult;
    }

    public void setPureResult(String pureResult) {
        this.pureResult = pureResult;
    }

    public String getTrimmedResult() {
        return trimmedResult;
    }

    public void setTrimmedResult(String trimmedResult) {
        this.trimmedResult = trimmedResult;
    }

    public String getRegexSearchPattern() {
        return regexSearchPattern;
    }

    public void setRegexSearchPattern(String regexSearchPattern) {
        this.regexSearchPattern = regexSearchPattern;
    }

//    public List<String> getResultTitle() {
//        return resultTitle;
//    }
//    
//    public void setResultTitle(List<String> resultTitle) {
//        this.resultTitle = resultTitle;
//    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void run() {

        /**
         * Devise a way to dynamically create a JFrame with it's panel and to
         * preload data that we have parsed from the Search Engine
         *
         * And hyperlink each of the given link we have extracted from the HTML
         * search result. Concurrently, we will also download and save the
         * target link as an individual HTML Page by itself so that our can
         * utilize it if needed.
         */
        // Multi-Threading system       
        ExecutorService threadPoolExecutor
                = new ThreadPoolExecutor(
                        this.results.size(),
                        this.results.size(),
                        600000, // Give the program a decent amount of time
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );

        for (ResultObject RO : this.results) {
            // Call a thread to run()
            // This method saves the link as HTML and a Cached file
            threadPoolExecutor.execute(RO);
        }

        // Awaits for all threads to complete before wrappin' up
        threadPoolExecutor.shutdown();

    }
}
