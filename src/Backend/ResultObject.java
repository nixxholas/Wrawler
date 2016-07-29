/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

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

/**
 *
 * @author Nixholas
 */
public class ResultObject implements Serializable, Runnable {
    private String name;
    private String url;
    private String Description;
    private String resultPage = "";
    
    public ResultObject(String name, String url, String Description) {
        this.name = name;
        this.url = url;
        this.Description = Description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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
            os.writeObject(this);
            
            reader.close();
            writer.close();
        } catch (Exception ex) {
            
        }
    }
    
}
