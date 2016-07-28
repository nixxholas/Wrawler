/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.File;
import java.io.Serializable;

/**
 * This class is created in order to cache Search Results
 * 
 * We can also tell whether if we're using a "present" or "past" object
 * just by seeing if the object is a Cacher Object or a ResultObject Object.
 * 
 * @author Nixholas
 */
public class Cacher extends ResultObject implements Serializable {

    public Cacher(String name, String url, String Description) {
        super(name, url, Description);
    }
    
    // writeObject & readObject are default..
    
    // Create a folder specially for the serialized objects.
    public void initializeCache() {
        File file = new File("/Caches");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("The cache directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
}
