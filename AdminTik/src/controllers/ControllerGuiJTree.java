/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDProductCategory;
import gui.GuiJTree;
import interfaces.InterfaceCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class ControllerGuiJTree implements ActionListener {

    private final GuiJTree guiJTree;
    private final InterfaceCategory crudProductCategory;

    public ControllerGuiJTree(GuiJTree gt) throws NotBoundException, MalformedURLException, RemoteException {
        crudProductCategory = (InterfaceCategory) Naming.lookup("//localhost/CRUDCategory");
        guiJTree = gt;
        CreateTree();
    }

    private void CreateTree() throws RemoteException {
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
        guiJTree.getTreeCategory().setModel(modelo);
        guiJTree.getTreeCategory().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                // Se obtiene el Path seleccionado
                TreePath path = e.getPath();
                Object[] nodos = path.getPath();
                String txt = "producto seleccionado: ";
                for (Object nodo : nodos) {
                    txt += nodo.toString() + " | ";
                }
                txt += "\n";
                // Se obtiene el Nodo seleccionado
                DefaultMutableTreeNode NodoSeleccionado = (DefaultMutableTreeNode) nodos[nodos.length - 1];
                txt += NodoSeleccionado.getUserObject().toString();
                txt += "\n";
                guiJTree.getAreaMessage().setText(txt);
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
