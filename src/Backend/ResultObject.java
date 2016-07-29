/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.cachedResults;
import static Backend.Constants.searchQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nixholas
 */
public class ResultObject implements Serializable, Runnable {
    private String name;
    private String url;
    private String userQuery;
    private String resultPage = "";
    
    public ResultObject(String name, String url, String userQuery) {
        this.name = name;
        this.url = url;
        this.userQuery = userQuery;
        this.resultPage = "";
    }

    public String getResultPage() {
        return resultPage;
    }

    public void setResultPage(String resultPage) {
        this.resultPage = resultPage;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }
    
    /**
     * Find Results that have been found from previous few searches
     * 
     * @param result
     * @return 
     */
    public static List<ResultObject> findResults(String result) {
        List<ResultObject> foundResults = new ArrayList();
        
        for (ResultObject RO : cachedResults) {
            if (RO.userQuery.equals(result)) {
                foundResults.add(RO);
            }
        }
        
        return foundResults;
    }
    
    public static boolean urlExists(String inURL) {
        
        for (ResultObject RO : cachedResults) {
            if (RO.url.equals(inURL)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static ResultObject getResultObject(String inURL) {
        
        for (ResultObject RO : cachedResults) {
            if (RO.url.equals(inURL)) {
                return RO;
            }
        }
        
        // Definitely won't get here
        return new ResultObject("", "", "");
    }
    
    // Checks the current ResultObject with the cachedResults
    public synchronized boolean checkDupes() {
        for (ResultObject ro : cachedResults) {
            if (ro.name == this.name) {
                // Since this exists, we'll remove it from the searchQueue
                // and we'll use the old one instead
                searchQueue.remove(this);
                searchQueue.add(this);
                
                return true;
            }
        }
        return false;
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
        //double size = in.readDouble();

        // Because the data that is read in is an Object,
        // we need to typecast it.
        //String colour = (String)in.readObject();

        // Finally, we can re-create our collar object
        //this.collar = new Collar(colour, size);
        
        in.defaultReadObject();
        
    } 
    
    /**
     * Directory Setup Method
     * 
     * Adapted from:
     * 
     * https://www.mkyong.com/java/how-to-create-directory-in-java/
     */
    // Create a folder specially for storing the HTML files.
    public static void initializeRO() {
        File file = new File("src/Download");
        File file2 = new File("src/Caches");
        
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("The download directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        
        if (!file2.exists()) {
            if (file2.mkdir()) {
                System.out.println("The Cache directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }  
    
    @Override
    public void run() {        
        try {
            URL url = new URL(this.getUrl());
            PrintWriter writer = new PrintWriter("src/Download/" +this.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".html", "UTF-8");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line, finalResult = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                finalResult += line + "\n";
                sb.append(line + "\n");
                writer.println(line);
            }

            this.setResultPage(finalResult);
            
            // Create a serialized form of the object
            FileOutputStream fos = new FileOutputStream("src/Caches/" + this.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".ser");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            
            // Caches the object
            os.writeObject(this);
            
            // We'll have to add it to the cachedResults as well
            // incase the user tries to searcha again
            cachedResults.add(this);
            
            reader.close();
            writer.close();
        } catch (Exception ex) {
            
        }
    }
    
}
