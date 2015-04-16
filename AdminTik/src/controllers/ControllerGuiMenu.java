/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDProductCategory;
import gui.GuiIngredientsFProducts;
import gui.GuiMenu;
import gui.main.GuiMain;
import interfaces.InterfaceCategory;
import interfaces.InterfaceEproduct;
import interfaces.InterfaceFproduct;
import interfaces.InterfacePproduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ControllerGuiMenu{

    private final GuiMenu guiMenu;
    private final InterfaceCategory crudProductCategory;
    private final InterfaceFproduct crudFproduct;
    InterfacePproduct crudPproduct;
    InterfaceEproduct crudEproduct;
    private GuiIngredientsFProducts guiIngredientsFProducts;
    private List<Map> fproductPproductList;
    private List<Map> fproductEproductList;

    public ControllerGuiMenu(GuiMenu gt, GuiMain gm) throws NotBoundException, MalformedURLException, RemoteException {
        crudProductCategory = (InterfaceCategory) Naming.lookup("//localhost/CRUDCategory");
        guiMenu = gt;
        guiIngredientsFProducts = new GuiIngredientsFProducts(gm, true);
        crudFproduct = (InterfaceFproduct) Naming.lookup("//localhost/CRUDFproduct");
        crudPproduct = (InterfacePproduct) Naming.lookup("//localhost/CRUDPproduct");
        crudEproduct = (InterfaceEproduct) Naming.lookup("//localhost/CRUDEproduct");
    }

    public void CreateTree() throws RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categorias");
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
                    }
                }
            }
        }
        DefaultTreeModel modelo = new DefaultTreeModel(root);
        guiMenu.getTreeCategory().setModel(modelo);
        guiMenu.getTreeCategory().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                TreePath currentSelection = guiMenu.getTreeCategory().getSelectionPath();
                if (currentSelection != null) {
                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                    if(currentNode.isLeaf()){
                        try {
                            List<Map> products = crudFproduct.getFproducts(currentNode.toString());
                            Iterator<Map> i = products.iterator();
                            while(i.hasNext()){
                                Map<String,Object> p = i.next();
                                int id = Integer.parseInt(String.valueOf(p.get("id")));
                                fproductPproductList = crudFproduct.getFproductPproduts(id);
                                fproductEproductList = crudFproduct.getFproductEproduts(id);
                                refreshReciperList();
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        guiIngredientsFProducts.setVisible(true);
                        
                    }
                }
        
        }
        
        });
    }
        public void refreshReciperList() throws RemoteException {
        guiIngredientsFProducts.getTableReciperDefault().setRowCount(0);
        for (Map fpPp : fproductPproductList) {
            Object row[] = new String[4];
            row[0] = fpPp.get("pproduct_id").toString();
            row[1] = crudPproduct.getPproduct(Integer.parseInt(fpPp.get("pproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpPp.get("amount").toString(); // Cantidad     
            guiIngredientsFProducts.getTableReciperDefault().addRow(row);
        }
        for (Map fpEp : fproductEproductList) {
            Object row[] = new String[4];
            row[0] = fpEp.get("eproduct_id").toString();
            row[1] = crudEproduct.getEproduct(Integer.parseInt(fpEp.get("eproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpEp.get("amount").toString(); // Cantidad     
            guiIngredientsFProducts.getTableReciperDefault().addRow(row);
        }
    }

    
        
                

}
                
