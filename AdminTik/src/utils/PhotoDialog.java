/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author xen
 */
public class PhotoDialog extends javax.swing.JDialog {
   private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Imagen","jpg","png");
    private JFileChooser fileChooser = new JFileChooser();
    private File directory = fileChooser.getCurrentDirectory();
    private jcPanelRecorte imageCut;
    private String name;
    
    /**
     * Creates new form PhotoWindow
     */
    public PhotoDialog(String name) {
        initComponents();
        this.setModal(true);
        this.setTitle("Recorte de Imagen");
        this.setLocationRelativeTo(null);
        this.name = name;
        this.setVisible(true);
        this.toFront();
    }
    
    private void openImage(){
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            try {
                imageCut = new jcPanelRecorte((ImageIO.read(fileChooser.getSelectedFile())));
                this.panelPhoto.removeAll();
                this.panelPhoto.add(imageCut);
                this.panelPhoto.repaint();
                
            } catch (IOException ex){
                System.out.println("error: "+ex);
            }
        }
                
    }
    
    private void saveImage(){
        String file = new File("src/Photos/"+getName()).toString();
        imageCut.guardar_imagen(file+".jpg");
        name = name+".jpg";
        
    }
    
    private int onClose(){
        this.setVisible(false);
        this.setModal(false);
        return 1;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPhoto = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuSelect = new javax.swing.JMenuItem();
        menuSaveImage = new javax.swing.JMenuItem();

        setDefaultCloseOperation(onClose()
        );

        javax.swing.GroupLayout panelPhotoLayout = new javax.swing.GroupLayout(panelPhoto);
        panelPhoto.setLayout(panelPhotoLayout);
        panelPhotoLayout.setHorizontalGroup(
            panelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelPhotoLayout.setVerticalGroup(
            panelPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );

        menuFile.setText("File");

        menuSelect.setText("Abrir imagen ...");
        menuSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSelectActionPerformed(evt);
            }
        });
        menuFile.add(menuSelect);

        menuSaveImage.setText("Guardar");
        menuSaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveImageActionPerformed(evt);
            }
        });
        menuFile.add(menuSaveImage);

        jMenuBar1.add(menuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSelectActionPerformed
        openImage();
    }//GEN-LAST:event_menuSelectActionPerformed

    private void menuSaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveImageActionPerformed
        saveImage();
    }//GEN-LAST:event_menuSaveImageActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuSaveImage;
    private javax.swing.JMenuItem menuSelect;
    private javax.swing.JPanel panelPhoto;
    // End of variables declaration//GEN-END:variables

    public String getName() {
        return name;
    }
}
