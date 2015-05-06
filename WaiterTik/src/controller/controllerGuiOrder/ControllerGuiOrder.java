/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllerGuiOrder;

import gui.GuiMain;
import gui.order.GuiAmount;
import gui.order.GuiOrder;
import interfaces.InterfaceCategory;
import interfaces.InterfaceFproduct;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author alan
 */
public class ControllerGuiOrder extends DefaultTreeCellRenderer implements ActionListener {

    private final GuiOrder guiOrder;
    private final InterfaceCategory crudProductCategory;
    private final InterfaceFproduct crudFproduct;
    private List<Map> fproductPproductList;
    private List<Map> fproductEproductList;
    String currentSelectedNodeName = "";
    DefaultMutableTreeNode currentNode = null;
    private ImageIcon rootIcon;
    private ImageIcon categoryIcon;
    private ImageIcon subcategoryIcon;
    private ImageIcon productIcon;
    private GuiAmount guiAmount;

    public ControllerGuiOrder(GuiOrder go, GuiMain gm) throws NotBoundException, MalformedURLException, RemoteException {
        guiOrder = go;
        crudProductCategory = (InterfaceCategory) Naming.lookup("//localhost/CRUDCategory");
        crudFproduct = (InterfaceFproduct) Naming.lookup("//localhost/CRUDFproduct");
        guiOrder.setActionListener(this);
        rootIcon = new ImageIcon(getClass().getResource("/Icons/menu.png"));
        categoryIcon = new ImageIcon(getClass().getResource("/Icons/category.png"));
        subcategoryIcon = new ImageIcon(getClass().getResource("/Icons/subcategory.png"));
        productIcon = new ImageIcon(getClass().getResource("/Icons/products.png"));
        guiAmount = new GuiAmount(gm, true);
        guiAmount.setActionListener(this);

        guiOrder.getTreeMenu().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                TreePath currentSelection = guiOrder.getTreeMenu().getSelectionPath();
                if (currentSelection != null) {
                    currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
                    currentSelectedNodeName = currentNode.toString();// nombre de la hoja seleccionada
                    if (currentNode.getLevel() == 3) {
                        guiAmount.getLblProd().setText(currentSelectedNodeName);
                        guiAmount.setLocationRelativeTo(null);
                        guiAmount.setVisible(true);
                    }
                }
            }
        });
        guiOrder.getTxtSearch().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent evt) {
                try {
                    search();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        CreateTree();
    }

    private void search() throws RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode categoryNode;
        DefaultMutableTreeNode subcategoryNode;
        DefaultMutableTreeNode final_prodNode;
        String txt = guiOrder.getTxtSearch().getText();
        //List<Map> listCategories = crudProductCategory.searchCategories(txt);
        //List<Map> listSubategories = crudProductCategory.searchSubcategories(txt);
        List<Map> listfproducts = crudFproduct.getFproducts(txt);
        for (Map<String, Object> fp : listfproducts) {
            int idSubcategory = (int) fp.get("subcategory_id");
            Map<String, Object> subcategory = crudProductCategory.getSubcategory(idSubcategory);
            int idCategory = (int) subcategory.get("category_id");
            Map<String, Object> category = crudProductCategory.getCategory(idCategory);
            categoryNode = new DefaultMutableTreeNode(category.get("name").toString());
            root.add(categoryNode);
            subcategoryNode = new DefaultMutableTreeNode(subcategory.get("name").toString());
            categoryNode.add(subcategoryNode);
            final_prodNode = new DefaultMutableTreeNode(fp.get("name").toString());
            subcategoryNode.add(final_prodNode);
        }

            DefaultTreeModel modelo = new DefaultTreeModel(root);
            guiOrder.getTreeMenu().setModel(modelo);
            guiOrder.getTreeMenu().setCellRenderer(this);
        

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
                    }
                }
            }
        }

        DefaultTreeModel modelo = new DefaultTreeModel(root);
        guiOrder.getTreeMenu().setModel(modelo);
        guiOrder.getTreeMenu().setCellRenderer(this);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        //altura de cada nodo
        tree.setRowHeight(26);
        setOpaque(true);
        //color de texto
        setForeground(Color.black);
        if (selected) {
            setForeground(Color.blue);
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        switch (node.getLevel()) {
            case 0:
                setIcon(rootIcon);
                break;
            case 1:
                setIcon(categoryIcon);
                break;
            case 2:
                setIcon(subcategoryIcon);
                break;
            case 3:
                setIcon(productIcon);
                break;
        }
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //*******GuiAmount**************//
        if (e.getSource().equals(guiAmount.getBtnAccept())) {
            try {
                List<Map> fproducts = crudFproduct.getFproducts(currentSelectedNodeName);
                if (fproducts.size() == 1) {
                    Map<String, Object> fp = fproducts.get(0);
                    Object[] row = new Object[5];
                    row[0] = fp.get("id");
                    row[1] = guiAmount.getTxtAmount().getText();
                    row[2] = currentSelectedNodeName;
                    row[3] = false;
                    row[4] = false;
                    guiOrder.getTableProductsDefault().addRow(row);
                } else {
                    JOptionPane.showMessageDialog(guiOrder, "No se encontro el producto o existen varios productos con el mismo nombre", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
                guiAmount.getTxtAmount().setText("1");
                guiAmount.setVisible(false);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
