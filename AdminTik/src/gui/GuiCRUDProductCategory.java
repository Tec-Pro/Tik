/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Gonzalez
 */
public class GuiCRUDProductCategory extends javax.swing.JInternalFrame {

    private DefaultTableModel tableCategoryDefault;
    private DefaultTableModel tableSubCategoryDefault;
    private boolean btnUpdateCategorySelected;
    private boolean btnUpdateSubCategorySelected;
    
    public GuiCRUDProductCategory() {
        initComponents();
        btnUpdateCategorySelected = false;
        tableCategoryDefault = (DefaultTableModel) tableCategory.getModel();
        tableSubCategoryDefault = (DefaultTableModel) tableSubCategory.getModel();
    }
    
    /**
     * Agrega a los botones de la interfaz un actionlistenes pasado como parametro.
     *@param ActionListener
     */
    public void setActionListener(ActionListener al){
        btnDeleteCategory.addActionListener(al);
        btnDeleteSubCategory.addActionListener(al);
        btnNewCategory.addActionListener(al);
        btnNewSubCategory.addActionListener(al);
        btnUpdateCategory.addActionListener(al);
        btnUpdateSubCategory.addActionListener(al);
    }
    
    /**
     * Define el estado en el que queda la interfaz luego clickear sobre los botones Nueva o Modificar del panel de categorias.
     */
    public void stateNewAndUpdateCategory(){
        txtCategory.setEnabled(true);
        btnUpdateCategory.setEnabled(false);
        btnNewCategory.setText("Guardar");
        btnDeleteCategory.setText("Cancelar");
        btnDeleteCategory.setEnabled(true);
    }
    
    /**
     * Define el estado inicial del panel de categorias.
     */
    public void initialStateCategoryPanel(){
        txtCategory.setEnabled(false);
        btnNewCategory.setText("Nueva");
        btnDeleteCategory.setText("Eliminar");
        btnDeleteCategory.setEnabled(false);
        btnUpdateCategory.setEnabled(false);
    }
    
    /**
     * Define el estado en el que queda la interfaz luego de crear o modificar una categoria.
     */
    public void stateAfterUpdateCategory(){
        txtCategory.setEnabled(false);
        btnNewCategory.setText("Nueva");
        btnDeleteCategory.setText("Eliminar");
        btnDeleteCategory.setEnabled(true);
        btnUpdateCategory.setEnabled(true);
    }
    
    /**
     * Define el estado en el que queda la interfaz al clickear sobre una categoria.
     */
    public void stateCategorySelected(){
        txtCategory.setEnabled(false);
        btnNewCategory.setText("Nueva");
        btnDeleteCategory.setText("Eliminar");
        btnDeleteCategory.setEnabled(true);
        btnUpdateCategory.setEnabled(true);
        
    }
    
    /**
     * Define el estado en el que queda la interfaz luego clickear sobre los botones Nueva o Modificar del panel de subcategorias.
     */
    public void stateNewAndUpdateSubCategory(){
        txtSubCategory.setEnabled(true);
        btnUpdateSubCategory.setEnabled(false);
        btnNewSubCategory.setText("Guardar");
        btnDeleteSubCategory.setText("Cancelar");
        btnDeleteSubCategory.setEnabled(true);
    }
    
    /**
     * Define el estado inicial del panel de subcategorias.
     */
    public void initialStateSubCategoryPanel(){
        txtSubCategory.setEnabled(false);
        btnNewSubCategory.setEnabled(false);
        btnNewSubCategory.setText("Nueva");
        btnDeleteSubCategory.setText("Eliminar");
        btnDeleteSubCategory.setEnabled(false);
        btnUpdateSubCategory.setEnabled(false);
    }
    
    /**
     * Define el estado en el que queda la interfaz luego de crear o modificar una subcategoria.
     */
   public void stateAfterUpdateSubCategory(){
        txtSubCategory.setEnabled(false);
        btnNewSubCategory.setText("Nueva");
        btnDeleteSubCategory.setText("Eliminar");
        btnDeleteSubCategory.setEnabled(true);
        btnUpdateSubCategory.setEnabled(true);
    }
    
   /**
     * Define el estado en el que queda la interfaz luego de clickear sobre una subcategoria.
     */
    public void stateSubCategorySelected(){
        txtSubCategory.setEnabled(false);
        btnNewSubCategory.setText("Nueva");
        btnDeleteSubCategory.setText("Eliminar");
        btnDeleteSubCategory.setEnabled(true);
        btnUpdateSubCategory.setEnabled(true);
    }
    
    /**
     * Limpia el panel de categorias.
     */
    public void cleanFieldsCategoryPanel(){
        txtCategory.setText("");
    }
    
    /**
     * Limpia el panel de subcategorias.
     */
    public void cleanFieldsSubCategoryPanel(){
        txtSubCategory.setText("");
        
    }
    
    public DefaultTableModel getTableCategoryDefault() {
        return tableCategoryDefault;
    }

    public DefaultTableModel getTableSubCategoryDefault() {
        return tableSubCategoryDefault;
    }

    public JButton getBtnDeleteCategory() {
        return btnDeleteCategory;
    }

    public JButton getBtnDeleteSubCategory() {
        return btnDeleteSubCategory;
    }

    public JButton getBtnNewCategory() {
        return btnNewCategory;
    }

    public JButton getBtnNewSubCategory() {
        return btnNewSubCategory;
    }

    public JButton getBtnUpdateCategory() {
        return btnUpdateCategory;
    }

    public JButton getBtnUpdateSubCategory() {
        return btnUpdateSubCategory;
    }

    public JTable getTableCategory() {
        return tableCategory;
    }

    public JTable getTableSubCategory() {
        return tableSubCategory;
    }

    public JTextField getTxtCategory() {
        return txtCategory;
    }

    public JTextField getTxtSubCategory() {
        return txtSubCategory;
    }

    /**
     * Retorna true en el caso en que el boton Modificar del panel de categorias ha sido seleccionado.
     * @return boolean.
     */
    public boolean isBtnUpdateCategorySelected() {
        return btnUpdateCategorySelected;
    }
    
    /**
     * Permite modificar el valor del atributo que brinda informacion sobre si el boton Modificar del panel de categorias ha sido seleccionado.
     * @param boolean
     */
    public void setBtnUpdateCategorySelected(boolean btnUpdateCategorySelected) {
        this.btnUpdateCategorySelected = btnUpdateCategorySelected;
    }

    /**
     * Retorna true en el caso en que el boton Modificar del panel de subcategorias ha sido seleccionado.
     * @return boolean.
     */
    public boolean isBtnUpdateSubCategorySelected() {
        return btnUpdateSubCategorySelected;
    }

    /**
     * * Permite modificar el valor del atributo que brinda informacion sobre si el boton Modificar del panel de subcategorias ha sido seleccionado.
     * @param btnUpdateSubCategorySelected 
     */
    public void setBtnUpdateSubCategorySelected(boolean btnUpdateSubCategorySelected) {
        this.btnUpdateSubCategorySelected = btnUpdateSubCategorySelected;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelImage3 = new org.edisoncor.gui.panel.PanelImage();
        panelTranslucido3 = new org.edisoncor.gui.panel.PanelTranslucido();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSubCategory = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtSubCategory = new javax.swing.JTextField();
        btnDeleteSubCategory = new javax.swing.JButton();
        btnUpdateSubCategory = new javax.swing.JButton();
        btnNewSubCategory = new javax.swing.JButton();
        panelTranslucido4 = new org.edisoncor.gui.panel.PanelTranslucido();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCategory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCategory = new javax.swing.JTextField();
        btnNewCategory = new javax.swing.JButton();
        btnUpdateCategory = new javax.swing.JButton();
        btnDeleteCategory = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestion de categorias y subcategorias de productos");

        panelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/black.jpg"))); // NOI18N
        panelImage3.setPreferredSize(new java.awt.Dimension(700, 426));

        panelTranslucido3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Subcategorias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tableSubCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Subcategoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableSubCategory);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Subcategoria");

        txtSubCategory.setEnabled(false);

        btnDeleteSubCategory.setText("Eliminar");
        btnDeleteSubCategory.setEnabled(false);

        btnUpdateSubCategory.setText("Modificar");
        btnUpdateSubCategory.setEnabled(false);

        btnNewSubCategory.setText("Nueva");
        btnNewSubCategory.setEnabled(false);

        javax.swing.GroupLayout panelTranslucido3Layout = new javax.swing.GroupLayout(panelTranslucido3);
        panelTranslucido3.setLayout(panelTranslucido3Layout);
        panelTranslucido3Layout.setHorizontalGroup(
            panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panelTranslucido3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTranslucido3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTranslucido3Layout.createSequentialGroup()
                        .addComponent(btnNewSubCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateSubCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteSubCategory)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        panelTranslucido3Layout.setVerticalGroup(
            panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSubCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewSubCategory)
                    .addComponent(btnUpdateSubCategory)
                    .addComponent(btnDeleteSubCategory))
                .addGap(33, 33, 33))
        );

        panelTranslucido4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Categorias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tableCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Categoria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tableCategory);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categoria");

        txtCategory.setEnabled(false);

        btnNewCategory.setText("Nueva");

        btnUpdateCategory.setText("Modificar");
        btnUpdateCategory.setEnabled(false);

        btnDeleteCategory.setText("Eliminar");
        btnDeleteCategory.setEnabled(false);

        javax.swing.GroupLayout panelTranslucido4Layout = new javax.swing.GroupLayout(panelTranslucido4);
        panelTranslucido4.setLayout(panelTranslucido4Layout);
        panelTranslucido4Layout.setHorizontalGroup(
            panelTranslucido4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panelTranslucido4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTranslucido4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTranslucido4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTranslucido4Layout.createSequentialGroup()
                        .addComponent(btnNewCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteCategory)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        panelTranslucido4Layout.setVerticalGroup(
            panelTranslucido4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido4Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelTranslucido4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(panelTranslucido4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewCategory)
                    .addComponent(btnUpdateCategory)
                    .addComponent(btnDeleteCategory))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout panelImage3Layout = new javax.swing.GroupLayout(panelImage3);
        panelImage3.setLayout(panelImage3Layout);
        panelImage3Layout.setHorizontalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTranslucido4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelTranslucido3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelImage3Layout.setVerticalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(panelTranslucido4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTranslucido3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        jScrollPane1.setViewportView(panelImage3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnDeleteSubCategory;
    private javax.swing.JButton btnNewCategory;
    private javax.swing.JButton btnNewSubCategory;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JButton btnUpdateSubCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private org.edisoncor.gui.panel.PanelImage panelImage3;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido3;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido4;
    private javax.swing.JTable tableCategory;
    private javax.swing.JTable tableSubCategory;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtSubCategory;
    // End of variables declaration//GEN-END:variables
}
