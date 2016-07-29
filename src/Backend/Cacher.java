/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

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
    
    /**
     * Write and Read Object adapted from Practical 7
     * 
     * @param out
     * 
     * The incoming object that will be serialized
     * 
     * @throws IOException
     * 
     * The exception speaks for itself
     * 
     * @throws ClassNotFoundException 
     * 
     * The exception speaks for itself
     */
    private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        // This is the default behaviour if this method
        // is not implemented.
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        double size = in.readDouble();

        // Because the data that is read in is an Object,
        // we need to typecast it.
        String colour = (String)in.readObject();

        // Finally, we can re-create our collar object
        this.collar = new Collar(colour, size);
    }
    // writeObject & readObject are default..
    
    
    // Create a folder specially for the serialized objects.
    public static void initializeCache() {
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
