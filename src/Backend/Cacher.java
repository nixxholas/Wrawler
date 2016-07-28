/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.Serializable;

/**
 * This class is created in order to cache Search Results
 * 
 * @author Nixholas
 */
public class Cacher implements Serializable {
    private String name; // Must be the same as the proceeding object.
    private String cachedWebPageData; // Stores the Webpage data here to cache it
                                      // in later.
    
    public Cacher() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCachedWebPageData() {
        return cachedWebPageData;
    }

    public void setCachedWebPageData(String cachedWebPageData) {
        this.cachedWebPageData = cachedWebPageData;
    }
    
    
}
