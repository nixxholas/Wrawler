/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import static Backend.Constants.addToQueue;
import static Backend.Constants.cachedResults;
import static Backend.Constants.jep;
import static Backend.Constants.jepPure;
import static Backend.Constants.mainFrame;
import static Backend.Constants.resultsCounter;
import static Backend.Constants.rightPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;

/**
 *
 * @author Nixholas
 */
public class ResultObject implements Serializable, Runnable {

    private String name;
    private String url;
    private String userQuery;
    private String resultPage = "";
    private boolean isCachedResult;
    public SearchEngine queryingEngine;

    public ResultObject(String name, String url, String userQuery, boolean isCached) {
        this.name = name;
        this.url = url;
        this.userQuery = userQuery;
        this.resultPage = "";
        this.isCachedResult = isCached;
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

    public boolean isIsCachedResult() {
        return isCachedResult;
    }

    public void setIsCachedResult(boolean isCachedResult) {
        this.isCachedResult = isCachedResult;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public SearchEngine getQueryingEngine() {
        return queryingEngine;
    }

    public void setQueryingEngine(SearchEngine queryingEngine) {
        this.queryingEngine = queryingEngine;
    }

    /**
     * Find Results that have been found from previous few searches
     *
     * If the incoming result actually exists in the cache, we'll add the
     * cachedResult into the searchQueue instead.
     *
     * @param result
     * @return
     */
    public static boolean findInCache(ResultObject IncomingResult) {
        // Check against the history of results
        for (ResultObject RO : cachedResults) {
            // If found, add the object to the searchQueue as well
            if (RO.url.equals(IncomingResult.url)) {
                RO.setIsCachedResult(true); // Make sure we let other classes know its cached
                addToQueue(RO);
                resultsCounter.incrementCount();
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

    /**
     * This runnable method executes the "downloading" of the web page if the
     * cache does not have it.
     *
     * The SearchEngine Object's Run() method Multi-Threads this runnable method
     * in order to maximize speed
     *
     * So we'll have to run checks by doing running a code like this:
     *
     * for (int i = 0; i < searchEngines.length; i++) { for (ResultObject ro in
     * searchEngines.get(i) { for (ResultObject ro2 in searchEngines.get(i + 1)
     * { if (ro2.url.equals(ro.url)) { searchEngines.get(i +
     * 1).removeResult(ro2); } } }
     *
     * In english that means
     *
     * For each searchEngine in searchengines for each ResultObject in
     * searchEngine for each ResultObject2 in secondSearchEngine if
     * ResultObject2's url equals to ResultObject's url, Delete ResultObject2
     * from secondSearchEngine
     *
     */
    @Override
    public void run() {
        // Temporary String datatype to store the loadedResult
        String loadedResult;

        try {
            if (!this.isCachedResult) { // If this was not cached,
                // Since we can't find it, we download it
                URL url = new URL(this.getUrl());
                PrintWriter writer = new PrintWriter("src/Download/" + this.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + ".html", "UTF-8");

                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                String line, finalResult = "";
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    finalResult += line + "\n";
                    sb.append(line + "\n");
                    writer.println(line);
                }

                this.setResultPage(finalResult);
                loadedResult = finalResult;

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

            } else {
                // But since it was cached,                
                loadedResult = this.getResultPage();
            }

            // Ultimately, we'll have to create the button
            JButton button = new JButton(this.getName());

            // For the action listener
            String resultName = this.getName();
            /**
             * Action Listener adapted from:
             *
             * http://alvinalexander.com/java/jbutton-listener-pressed-actionlistener
             */
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /**
                     * Webpage Viewer within the Java Project
                     *
                     * http://stackoverflow.com/questions/10601676/display-a-webpage-inside-a-swing-application
                     */
                    jep.removeAll();
                    jepPure.removeAll();

                    try {
                        // Pulls the file path
                        File file = new File("src/Download/" + resultName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html");

                        // Threading for HTML Views to reduce lag
                        Thread t = new Thread() {
                            public void run() {
                                try {
                                    jep.setPage(file.toURI().toURL());
                                } catch (Exception ex) {
                                    jep.setContentType("text/html");
                                    jep.setText("<html>Could not load the page.</html>");
                                }
                            }
                        };
                        t.start();

                        jepPure.setText(loadedResult);
                        mainFrame.pack();
                    } catch (Exception ex) {
                        jep.setContentType("text/html");
                        jep.setText("<html>Could not load the page.</html>");
                    }
                }
            });

            rightPanel.add(button);
        } catch (Exception ex) {

        }
    }

}
