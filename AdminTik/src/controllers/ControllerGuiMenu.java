/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiAddUpdateProductCategory;
import gui.GuiAddUpdateProductSubcategory;
import gui.GuiCRUDProductCategory;
import gui.GuiMenu;
import gui.main.GuiMain;
import interfaces.InterfaceCategory;
import interfaces.InterfaceEproduct;
import interfaces.InterfaceFproduct;
import interfaces.InterfacePproduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import utils.Config;

/**
 *
 * @author Alan Gonzalez
 */
public class ControllerGuiMenu implements ActionListener {

    private final GuiMenu guiMenu;
    private final InterfaceCategory crudProductCategory;
    private final InterfaceFproduct crudFproduct;
    private final InterfacePproduct crudPproduct;
    private final InterfaceEproduct crudEproduct;
    private List<Map> fproductPproductList;
    private List<Map> fproductEproductList;
    private GuiAddUpdateProductCategory guiAddUpdateProductCategory;
    private GuiAddUpdateProductSubcategory guiAddUpdateProductSubcategory;
    String currentSelectedNodeName = "";
    DefaultMutableTreeNode currentNode = null;

    public ControllerGuiMenu(GuiMenu gt, GuiMain gm) throws NotBoundException, MalformedURLException, RemoteException {
        crudProductCategory = (InterfaceCategory) Naming.lookup("//localhost/CRUDCategory");
        guiMenu = gt;
        crudFproduct = (InterfaceFproduct) Naming.lookup("//localhost/CRUDFproduct");
        crudPproduct = (InterfacePproduct) Naming.lookup("//localhost/CRUDPproduct");
        crudEproduct = (InterfaceEproduct) Naming.lookup("//localhost/CRUDEproduct");
        guiAddUpdateProductCategory = new GuiAddUpdateProductCategory(gm, true);
        guiAddUpdateProductCategory.setLocationRelativeTo(guiMenu);
        guiAddUpdateProductCategory.setActionListener(this);
        guiAddUpdateProductSubcategory = new GuiAddUpdateProductSubcategory(gm, true);
        guiAddUpdateProductSubcategory.setLocationRelativeTo(guiMenu);
        guiAddUpdateProductSubcategory.setActionListener(this);
        guiMenu.setActionListener(this);

        guiMenu.getTreeMenu().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                changeSelection(me);
            }
        });
        guiMenu.getTreeMenu().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                if(currentNode!=null){
                    if (currentNode.getLevel() == 0) {
                        guiMenu.getBtnDelete().setEnabled(false);
                        guiMenu.getBtnUpdate().setEnabled(false);
                    }
                }
                

            }

        });
    }

    private void changeSelection(MouseEvent me) {
        guiMenu.getBtnDelete().setEnabled(true);
        guiMenu.getBtnUpdate().setEnabled(true);
        TreePath currentSelection = guiMenu.getTreeMenu().getSelectionPath();
        if (currentSelection != null) {
            currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
            currentSelectedNodeName = currentNode.toString();// nombre de la hoja seleccionada
            if (currentNode.isLeaf()) {
                switch (currentSelectedNodeName) {
                    case "AGREGAR CATEGORIA":
                        guiMenu.getBtnDelete().setEnabled(false);
                        guiMenu.getBtnUpdate().setEnabled(false);
                        if (me.getClickCount() == 2) {
                            guiAddUpdateProductCategory.addCategoryState();
                            guiAddUpdateProductCategory.setVisible(true);
                        }
                        break;
                    case "AGREGAR SUBCATEGORIA":
                        guiMenu.getBtnDelete().setEnabled(false);
                        guiMenu.getBtnUpdate().setEnabled(false);
                        if (me.getClickCount() == 2) {
                            guiAddUpdateProductSubcategory.addSucategoryState();
                            guiAddUpdateProductSubcategory.setVisible(true);
                        }
                        break;
                    case "AGREGAR PRODUCTO":
                        guiMenu.getBtnDelete().setEnabled(false);
                        guiMenu.getBtnUpdate().setEnabled(false);
                        if (me.getClickCount() == 2) {
                            
                        }
                        break;
                    default:
                        fProductSelected(currentSelectedNodeName);
                        break;
                }
            }
        }
    }

    public void CreateTree() throws RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode category;
        DefaultMutableTreeNode subcategory;
        DefaultMutableTreeNode final_prod;

        List<Map> listCategories = crudProductCategory.getCategories();
        if (!listCategories.isEmpty()) {
            Iterator<Map> iteratorCategory = listCategories.iterator();
            while (iteratorCategory.hasNext()) {
                Map<String, Object> cat = iteratorCategory.next();
                //Por cada categoria agrego un nodo a la raiz
                category = new DefaultMutableTreeNode(cat.get("name").toString());
                root.add(category);
                //Obtengo las subcategorias de cada categoria
                int idCategory = Integer.parseInt(cat.get("id").toString());
                List<Map> listSubCategory = crudProductCategory.getSubcategoriesCategory(idCategory);
                if (!listSubCategory.isEmpty()) {
                    Iterator<Map> iteratorSubCategory = listSubCategory.iterator();
                    while (iteratorSubCategory.hasNext()) {
                        Map<String, Object> subcat = iteratorSubCategory.next();
                        //Agrego nodos de subcategoria a su correspondiente categoria.
                        subcategory = new DefaultMutableTreeNode(subcat.get("name").toString());
                        category.add(subcategory);
                        //Obtengo los productos finales de cada subcategoria
                        int idSubCategory = Integer.parseInt(subcat.get("id").toString());
                        List<Map> listFProds = crudProductCategory.getFProductsSubcategory(idSubCategory);
                        Iterator<Map> iteratorFinalProducts = listFProds.iterator();
                        while (iteratorFinalProducts.hasNext()) {
                            Map<String, Object> fprod = iteratorFinalProducts.next();
                            //Por cada producto final creo un nodo en su correspondiente subcategoria
                            final_prod = new DefaultMutableTreeNode(fprod.get("name").toString());
                            subcategory.add(final_prod);
                        }
                        subcategory.add(new DefaultMutableTreeNode("AGREGAR PRODUCTO"));
                    }
                }
                category.add(new DefaultMutableTreeNode("AGREGAR SUBCATEGORIA"));
            }
            root.add(new DefaultMutableTreeNode("AGREGAR CATEGORIA"));
        }

        DefaultTreeModel modelo = new DefaultTreeModel(root);
        guiMenu.getTreeMenu().setModel(modelo);

    }

    private void addSubcategorySelected() {

    }

    private void fProductSelected(String currentNode) {
        try {
            List<Map> products = crudFproduct.getFproducts(currentNode);
            if (products.size() > 1) {
                JOptionPane.showMessageDialog(guiMenu, "Error: Se encontro mas de un producto con el mismo nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Map<String, Object> p = products.get(0);//el unico elemento de la lista
                int id = Integer.parseInt(String.valueOf(p.get("id")));
                fproductPproductList = crudFproduct.getFproductPproduts(id);
                fproductEproductList = crudFproduct.getFproductEproduts(id);
                refreshReciperList();

            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiMenu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshReciperList() throws RemoteException {
        guiMenu.getTableReciperDefault().setRowCount(0);
        for (Map fpPp : fproductPproductList) {
            Object row[] = new String[4];
            row[0] = fpPp.get("pproduct_id").toString();
            row[1] = crudPproduct.getPproduct(Integer.parseInt(fpPp.get("pproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpPp.get("amount").toString(); // Cantidad     
            guiMenu.getTableReciperDefault().addRow(row);
        }
        for (Map fpEp : fproductEproductList) {
            Object row[] = new String[4];
            row[0] = fpEp.get("eproduct_id").toString();
            row[1] = crudEproduct.getEproduct(Integer.parseInt(fpEp.get("eproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpEp.get("amount").toString(); // Cantidad     
            guiMenu.getTableReciperDefault().addRow(row);
        }
    }

    private boolean dataCategoryIsValid() {
        return !guiAddUpdateProductCategory.getTxtCategory().getText().equals("");
    }

    private boolean dataSubcategoryIsValid() {
        return !guiAddUpdateProductSubcategory.getTxtSubcategory().getText().equals("");
    }

    private int getIdCategory() throws RemoteException {
        Map<String, Object> category = crudProductCategory.getCategoryByName(currentNode.getParent().toString());
        return (int) category.get("id");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * ************Botones GuiAddUpdateProductCategory***************
         */
        if (e.getSource().equals(guiAddUpdateProductCategory.getBtnSave()) && !guiMenu.isBtnUpdateSelected()) {
            if (!dataCategoryIsValid()) {
                JOptionPane.showMessageDialog(guiMenu, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String, Object> newCategory;
                    newCategory = crudProductCategory.create(guiAddUpdateProductCategory.getTxtCategory().getText());
                    if (newCategory != null) {
                        JOptionPane.showMessageDialog(guiMenu, "Nueva categoria creada exitosamente!", "Categoria creada!", JOptionPane.INFORMATION_MESSAGE);
                        guiAddUpdateProductCategory.setVisible(false);
                        CreateTree();
                    } else {
                        JOptionPane.showMessageDialog(guiMenu, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            if (e.getSource().equals(guiAddUpdateProductCategory.getBtnSave()) && guiMenu.isBtnUpdateSelected()) {
                if (!dataCategoryIsValid()) {
                    JOptionPane.showMessageDialog(guiAddUpdateProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!currentSelectedNodeName.equals(guiAddUpdateProductCategory.getTxtCategory().getText())) {
                        try {
                            if (crudProductCategory.categoryExists(guiAddUpdateProductCategory.getTxtCategory().getText())) {
                                JOptionPane.showMessageDialog(guiMenu, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                try {
                                    Map<String, Object> modifiedCategory;
                                    Map<String, Object> category = crudProductCategory.getCategoryByName(currentSelectedNodeName);
                                    int id = (int) category.get("id");
                                    modifiedCategory = crudProductCategory.modify(id, guiAddUpdateProductCategory.getTxtCategory().getText());
                                    if (modifiedCategory != null) {
                                        JOptionPane.showMessageDialog(guiMenu, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                                        guiAddUpdateProductCategory.setVisible(false);
                                        CreateTree();
                                    } else {
                                        JOptionPane.showMessageDialog(guiMenu, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);

                                    }
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiCRUDAdmin.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiProductCategory.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(guiMenu, "No se detecto cambio en el nombre de la categoria.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        if (e.getSource().equals(guiAddUpdateProductCategory.getBtnCancel())) {
            guiAddUpdateProductCategory.setVisible(false);
            guiMenu.setBtnUpdateSelected(false);
        }

        /**
         * ************Botones GuiAddUpdateProductSubcategory***************
         */
        if (e.getSource().equals(guiAddUpdateProductSubcategory.getBtnSave()) && !guiMenu.isBtnUpdateSelected()) {
            if (!dataSubcategoryIsValid()) {
                JOptionPane.showMessageDialog(guiMenu, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String, Object> newSubcategory;
                    newSubcategory = crudProductCategory.addSubcategory(getIdCategory(), guiAddUpdateProductSubcategory.getTxtSubcategory().getText());
                    if (newSubcategory != null) {
                        JOptionPane.showMessageDialog(guiMenu, "Nueva subcategoria creada exitosamente!", "Subcategoria creada!", JOptionPane.INFORMATION_MESSAGE);
                        guiAddUpdateProductSubcategory.setVisible(false);
                        CreateTree();
                    } else {
                        JOptionPane.showMessageDialog(guiMenu, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            if (e.getSource().equals(guiAddUpdateProductSubcategory.getBtnSave()) && guiMenu.isBtnUpdateSelected()) {
                if (!dataSubcategoryIsValid()) {
                    JOptionPane.showMessageDialog(guiAddUpdateProductSubcategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!currentSelectedNodeName.equals(guiAddUpdateProductSubcategory.getTxtSubcategory().getText())) {
                        try {
                            if (crudProductCategory.subCategoryExists(guiAddUpdateProductSubcategory.getTxtSubcategory().getText())) {
                                JOptionPane.showMessageDialog(guiMenu, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                try {
                                    Map<String, Object> modifiedSubcategory;
                                    Map<String, Object> subcategory = crudProductCategory.getSubcategory(currentSelectedNodeName);
                                    int id = (int) subcategory.get("id");
                                    modifiedSubcategory = crudProductCategory.modifySubcategory(id, guiAddUpdateProductSubcategory.getTxtSubcategory().getText());
                                    if (modifiedSubcategory != null) {
                                        JOptionPane.showMessageDialog(guiMenu, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                                        guiAddUpdateProductSubcategory.setVisible(false);
                                        CreateTree();
                                    } else {
                                        JOptionPane.showMessageDialog(guiMenu, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);

                                    }
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiCRUDAdmin.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiProductCategory.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(guiMenu, "No se detecto cambio en el nombre de la subcategoria.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        if (e.getSource().equals(guiAddUpdateProductSubcategory.getBtnCancel())) {
            guiAddUpdateProductSubcategory.setVisible(false);
            guiMenu.setBtnUpdateSelected(false);
        }
        /**
         * *********Botones GuiMenu***********
         */
        if (e.getSource().equals(guiMenu.getBtnUpdate())) {
            guiMenu.setBtnUpdateSelected(true);
            switch (currentNode.getLevel()) {
                case 0:
                    break;
                case 1:
                    guiAddUpdateProductCategory.UpdateCategoryState();
                    guiAddUpdateProductCategory.getTxtCategory().setText(currentSelectedNodeName);
                    guiAddUpdateProductCategory.setVisible(true);
                    break;
                case 2:
                    guiAddUpdateProductSubcategory.UpdateSubcategoryState();
                    guiAddUpdateProductSubcategory.getTxtSubcategory().setText(currentSelectedNodeName);
                    guiAddUpdateProductSubcategory.setVisible(true);
            }

        }

    }

}
