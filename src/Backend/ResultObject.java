/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

/**
 *
 * @author Nixholas
 */
public class ResultObject implements Runnable {
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
    
    // Create a folder specially for storing the HTML files.
    public void initializeRO() {
        File file = new File("/WebPages");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("The WebPages directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
}
