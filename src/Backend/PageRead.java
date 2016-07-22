/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Interface.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nixholas
 */
public class PageRead {
    public static List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();
        
    public static StringBuilder readPage(String pageAddr) {
        try {
            URL url = new URL(pageAddr);
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
          
            String line;
            StringBuilder sb=new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
 
            reader.close();

			return sb;            
        } catch (MalformedURLException e) {
            e.printStackTrace();
			return new StringBuilder("");
        }  catch (IOException e) {
            e.printStackTrace();
			return new StringBuilder("");
        }
    }

    public static void main(String arg[]){
        searchEngines.add(new SearchEngine("Google", "https://www.google.com/#q="));
        
        MainFrame newFrame = new MainFrame();
        newFrame.setVisible(true);    
        
    }
    
}
