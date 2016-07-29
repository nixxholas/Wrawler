/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

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
    private Queue<ResultObject> results = new ArrayDeque<>();
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

    public Queue<ResultObject> getResults() {
        return results;
    }

    public void setResults(Queue<ResultObject> results) {
        this.results = results;
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
}
