/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nixho
 */
public class searchGeneric<A, B, C, D> extends Thread {
    private A url;
    private B regexSearchPattern;
    private C regexResultObject;
    private D regexForTitle;
    private String pureResult; // Stores the actual search result
    private String trimmedResult; // Stores the search result after RegEx trimming
    private List<ResultObject> results = new ArrayList<>();

    public searchGeneric(A url, B regexSearchPattern, C regexResultObject, D regexForTitle) {
        this.url = url;
        this.regexSearchPattern = regexSearchPattern;
        this.regexResultObject = regexResultObject;
        this.regexForTitle = regexForTitle;
    }
    
    public A getUrl(){
        return url;
    }
    
    public void setUrl(A url) {
        this.url = url;
    }

    public B getRegexSearchPattern() {
        return regexSearchPattern;
    }

    public void setRegexSearchPattern(B regexSearchPattern) {
        this.regexSearchPattern = regexSearchPattern;
    }

    public C getRegexResultObject() {
        return regexResultObject;
    }

    public void setRegexResultObject(C regexResultObject) {
        this.regexResultObject = regexResultObject;
    }

    public D getRegexForTitle() {
        return regexForTitle;
    }

    public void setRegexForTitle(D regexForTitle) {
        this.regexForTitle = regexForTitle;
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

    public List<ResultObject> getResults() {
        return results;
    }

    public void setResults(List<ResultObject> results) {
        this.results = results;
    }    
}
