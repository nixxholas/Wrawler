/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.searchEngines;
import java.util.List;

/**
 * A boolean that can only be accessed once.
 *
 * @author nixho
 */
public class ToughBoolean {
    private boolean value; // For fun
    private boolean accessed = false; // For single accessibility
    
    public synchronized void setValue(boolean a) {
        if (accessed = false){
            accessed = true;
            value = a;
            checkDuplicates();
        } else {
            // Do nothing
        }
    }
    
    /**
     * Doesn't work lol
     */
    private void checkDuplicates() {
        System.out.println("Checking dupes");
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
                            System.out.println("Found Dupes");
                            // remove it from the results of the next searchengine
                            searchEngines.get(i + 1).removeResult(nextRO);
                        }
                    }
                }
            }
        }
    }
}
