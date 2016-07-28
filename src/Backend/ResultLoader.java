/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Concurrent Downloading Object for Search Results
 * 
 * @author Nixholas
 */
public class ResultLoader extends ResultObject implements Runnable {
    // Name is not neeeded.

    public ResultLoader(String name, String url, String Description) {
        super(name, url, Description);
    }    
    
    /**
     * Upon execution of run(), this loader will in turn run the necessary 
     * codes in order to pull the HTML data from the Search Engine Results.
     */
    @Override
    public void run() {
        try {
            URL url = new URL(this.getUrl());
            PrintWriter writer = new PrintWriter(this.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".html", "UTF-8");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line, finalResult = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                finalResult += line + "\n";
                sb.append(line + "\n");
                writer.println(line);
            }

            this.setResultPage(finalResult);
            reader.close();
            writer.close();
        } catch (Exception ex) {
            
        }
    }
    
}
