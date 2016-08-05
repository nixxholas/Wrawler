/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.searchEngines;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nixho
 */
public class SearchEngine extends Thread {

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

    public synchronized void checkDuplicates() {
        /**
         * SearchEngine Duplicate Entry Cross Checker
         */
        // Iterate through the searchEngines list
        for (int i = 0; i < searchEngines.size(); i++) {
            // Pull the current searchengine to work on
            List<ResultObject> curResults = searchEngines.get(i).getResults();
            // While there are two searchengines to compare,
            while (searchEngines.get(i + 1) != null) {
                List<ResultObject> nextResults = searchEngines.get(i + 1).getResults();
                // For loop for current search engine
                for (ResultObject ro : curResults) {
                    // Iterate through each nextResult
                    for (ResultObject nextRO : nextResults) {
                        // If the result already exists
                        if (nextRO.getUrl().equals(ro.getUrl())) {
                            // remove it from the results of the next searchengine
                            searchEngines.get(i + 1).removeResult(nextRO);
                        }
                    }
                }
            }
        }
    }
}
