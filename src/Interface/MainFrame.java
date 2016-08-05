/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import static Backend.BackendServices.clearDirectory;
import static Backend.Constants.actualProgress;
import static Backend.Constants.clearQueue;
import static Backend.Constants.getSearchQueue;
import static Backend.Constants.getSearchQueueSize;
import static Backend.Constants.mainFrame;
import static Backend.Constants.progressRate;
import static Backend.Constants.resultFrame;
import static Backend.Constants.resultsCounter;
import static Backend.Constants.searchEngines;
import static Backend.Constants.settingsFrame;
import static Backend.PageRead.*;
import Backend.ResultObject;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nixholas
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textBox = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        mainLabel = new javax.swing.JLabel();
        clearCacheBtn = new javax.swing.JButton();
        mainFrameProgressBar = new javax.swing.JProgressBar();
        settingsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        textBox.setToolTipText("");
        textBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBoxActionPerformed(evt);
            }
        });

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        mainLabel.setBackground(new java.awt.Color(255, 153, 153));
        mainLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 36)); // NOI18N
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText("Wrawler");

        clearCacheBtn.setText("Clear Cache");
        clearCacheBtn.setName(""); // NOI18N
        clearCacheBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearCacheBtnActionPerformed(evt);
            }
        });

        mainFrameProgressBar.setForeground(new java.awt.Color(0, 204, 0));
        mainFrameProgressBar.setToolTipText("");
        mainFrameProgressBar.setValue(-100);

        settingsBtn.setText("Settings");
        settingsBtn.setToolTipText("");
        settingsBtn.setName(""); // NOI18N
        settingsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainFrameProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(153, 153, 153)
                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 63, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(clearCacheBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(settingsBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainFrameProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearCacheBtn)
                    .addComponent(settingsBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textBoxActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed

        // Wipe the statics
        clearQueue();

        // Get the result string
        String result = textBox.getText();

        // Setup the progress value, we need 20% for the final part
        progressRate = 80 / searchEngines.size(); // Lazy Integer for progression

        // User Input Validation
        if (result.length() < 1) {
            textBox.setText("PLEASE ENTER SOMETHING!");
            return;
        }

        // RegEx for escape characters on user input
        // Setup the Progress Bar
        searchBtn.setEnabled(false); // Disable the Search Button First
        mainFrameProgressBar.setMaximum(100);
        mainFrameProgressBar.setValue(actualProgress);

        // User validation undone
        // Initialize progressBar
        Runnable r = () -> {
            for (int i = 0; i < searchEngines.size(); i++) {
                try {
                    // Set the trimmedResult into the SearchEngine Objects
                    searchEngines.get(i).setTrimmedResult(getUrlSource(searchEngines.get(i), result));
                    actualProgress = actualProgress + progressRate;
                    mainFrameProgressBar.setValue(actualProgress);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Setup the UI
            EventQueue.invokeLater(() -> {

                /**
                 * Devise a way to dynamically create a JFrame with it's panel
                 * and to preload data that we have parsed from the Search
                 * Engine
                 *
                 * And hyperlink each of the given link we have extracted from
                 * the HTML search result. Concurrently, we will also download
                 * and save the target link as an individual HTML Page by itself
                 * so that our can utilize it if needed.
                 */
                
                // Multi-Threading system       
                ExecutorService threadPoolExecutor
                        = new ThreadPoolExecutor(
                                getSearchQueueSize(),
                                getSearchQueueSize(),
                                600000, // Give the program a decent amount of time
                                TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()
                        );
                
                // Debugging purposes only
                System.out.println("Search Queue Size is : " + getSearchQueueSize());

                for (ResultObject RO : getSearchQueue()) {
                    // Call a thread to run()
                    // This method saves the link as HTML and a Cached file
                    threadPoolExecutor.execute(RO);
                }

                // Awaits for all threads to complete before wrappin' up
                threadPoolExecutor.shutdown();

                // Once we're done with loading the searches, we'll show the frame.
                // and hide the mainFrame (which is called newFrame)
                resultFrame.setVisible(true);
                mainFrame.setVisible(false);
                searchBtn.setEnabled(true);

                // Reset the progress bar
                actualProgress = 0;
                mainFrameProgressBar.setValue(actualProgress);

                // Reset the Counters
                resultsCounter.setCount(0);
            });
        };
        Thread th = new Thread(r);
        th.start();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void clearCacheBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearCacheBtnActionPerformed
        // TODO add your handling code here:
        clearDirectory("src/Download");
        clearDirectory("src/Caches");
    }//GEN-LAST:event_clearCacheBtnActionPerformed

    private void settingsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsBtnActionPerformed
        // TODO add your handling code here:
        settingsFrame.setVisible(true);
        mainFrame.setVisible(false);
    }//GEN-LAST:event_settingsBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearCacheBtn;
    private javax.swing.JProgressBar mainFrameProgressBar;
    private javax.swing.JLabel mainLabel;
    public javax.swing.JButton searchBtn;
    private javax.swing.JButton settingsBtn;
    public static javax.swing.JTextField textBox;
    // End of variables declaration//GEN-END:variables
}
