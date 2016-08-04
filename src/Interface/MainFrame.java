/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import static Backend.BackendServices.clearDirectory;
import static Backend.Constants.actualProgress;
import static Backend.Constants.mainFrame;
import static Backend.Constants.progressRate;
import static Backend.Constants.resultFrame;
import static Backend.Constants.searchEngines;
import static Backend.PageRead.*;
import java.awt.EventQueue;
import java.io.IOException;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(clearCacheBtn)))
                .addContainerGap(57, Short.MAX_VALUE))
            .addComponent(mainFrameProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainFrameProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(clearCacheBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textBoxActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed

        String result = textBox.getText();
        
        // Setup the progress value
        progressRate = 100 / searchEngines.size(); // Lazy Integer for progression
         
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
                // Once we're done with loading the searches, we'll show the frame.
                // and hide the mainFrame (which is called newFrame)
                resultFrame.setVisible(true);
                mainFrame.setVisible(false);
                searchBtn.setEnabled(true);
                
                // Reset the progress bar
                actualProgress = 0;
                mainFrameProgressBar.setValue(actualProgress);
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
    public static javax.swing.JTextField textBox;
    // End of variables declaration//GEN-END:variables
}
