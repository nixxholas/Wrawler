/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

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
    private String regexForDescription;
    private String pureResult; // Stores the actual search result
    private String trimmedResult; // Stores the search result after RegEx trimming
    private List<ResultObject> results;
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

    public String getRegexForDescription() {
        return regexForDescription;
    }

    public void setRegexForDescription(String regexForDescription) {
        this.regexForDescription = regexForDescription;
    }

    public List<ResultObject> getResults() {
        return results;
    }

    public void setResults(List<ResultObject> results) {
        this.results = results;
    }
    
    public SearchEngine(
            String baseUrl,
            String regexSearchPattern,
            String regexForName,
            String regexForDescription) {
        this.baseUrl = baseUrl;
        this.regexSearchPattern = regexSearchPattern;
        this.regexForName = regexForName;
        this.regexForDescription = regexForDescription;
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
//
//    public List<String> getResultUrl() {
//        return resultUrl;
//    }
//
//    public void setResultUrl(List<String> resultUrl) {
//        this.resultUrl = resultUrl;
//    }
//
//    public List<String> getResultDescription() {
//        return resultDescription;
//    }
//
//    public void setResultDescription(List<String> resultDescription) {
//        this.resultDescription = resultDescription;
//    }

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
