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
import interfaces.InterfaceOrder;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import utils.Config;
import utils.ParserFloat;

/**
 *
 * @author alan
 */
public class ControllerGuiOrder extends DefaultTreeCellRenderer implements ActionListener {

    private Integer currentOrderId;
    private int currentWaiterId;
    private boolean orderClosed;
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
    private final InterfaceOrder crudOrder;

    
    /**
     * Setea el id del mozo actual, y el id del pedido actual.
     * @param orderId si es un nuevo pedido, poner en null
     * @param waiterId
     */
    public void setIds(Integer orderId, int waiterId){
        currentOrderId = orderId;
        currentWaiterId = waiterId;
        if(currentOrderId!= null)
            try {
                orderClosed = (boolean)crudOrder.getOrder(currentOrderId).get("closed"); //setea el esatado del pedido actual, cerrado o no
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        else
            orderClosed = false; //si es nuevo, el pedido no esta cerrado
        try {
            loadProducts();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        guiOrder.getBtnSend().setEnabled(false);
    }
    
    public ControllerGuiOrder(GuiOrder go, GuiMain gm) throws NotBoundException, MalformedURLException, RemoteException {
        guiOrder = go;
        guiOrder.getBtnSend().setEnabled(false);
        crudProductCategory = (InterfaceCategory) Naming.lookup("//localhost/CRUDCategory");
        crudFproduct = (InterfaceFproduct) Naming.lookup("//localhost/CRUDFproduct");
        crudOrder = (InterfaceOrder)Naming.lookup("//" + Config.ip + "/crudOrder") ;
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
        loadProducts();
    }

    private void search() throws RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultTreeModel model = new DefaultTreeModel(root);
        guiOrder.getTreeMenu().setModel(model);
        guiOrder.getTreeMenu().setCellRenderer(this);
        DefaultMutableTreeNode categoryNode;
        DefaultMutableTreeNode subcategoryNode;
        DefaultMutableTreeNode final_prodNode;
        String txt = guiOrder.getTxtSearch().getText();
        List<Map> listCategories = crudProductCategory.searchCategories(txt);
        List<Map> listSubategories = crudProductCategory.searchSubcategories(txt);
        List<Map> listfproducts = crudFproduct.getFproducts(txt);
        /**
         * ***********Muestro por categoria***********
         */
        for (Map<String, Object> cat : listCategories) {
            categoryNode = searchNode((String) cat.get("name"), root);
            if (categoryNode == null) {
                categoryNode = new DefaultMutableTreeNode(cat.get("name").toString());
                root.add(categoryNode);
            }
            List<Map> listCategorySubcayegory = crudProductCategory.getSubcategoriesCategory((int) cat.get("id"));
            if (!listCategorySubcayegory.isEmpty()) {
                for (Map<String, Object> sub : listCategorySubcayegory) {
                    subcategoryNode = searchNode((String) sub.get("name"), root);
                    if (subcategoryNode == null) {
                        subcategoryNode = new DefaultMutableTreeNode(sub.get("name").toString());
                        categoryNode.add(subcategoryNode);
                    }
                    int idSub = (int) sub.get("id");
                    List<Map> prods = crudProductCategory.getFProductsSubcategory(idSub);
                    if (!prods.isEmpty()) {
                        for (Map<String, Object> prod : prods) {
                            final_prodNode = searchNode((String) prod.get("name"), root);
                            if (final_prodNode == null) {
                                final_prodNode = new DefaultMutableTreeNode(prod.get("name").toString());
                                subcategoryNode.add(final_prodNode);
                            }
                        }
                    }

                }
            }
        }

        /**
         * ************Muestro por subcategorias*******************
         */
        for (Map<String, Object> subcategory : listSubategories) {
            int idCat = (int) subcategory.get("category_id");
            int idSub = (int) subcategory.get("id");
            Map<String, Object> cat = crudProductCategory.getCategory(idCat);
            categoryNode = searchNode((String) cat.get("name"), root);
            if (categoryNode == null) {
                categoryNode = new DefaultMutableTreeNode(cat.get("name").toString());
                root.add(categoryNode);
            }
            subcategoryNode = searchNode((String) subcategory.get("name"), root);
            if (subcategoryNode == null) {
                subcategoryNode = new DefaultMutableTreeNode(subcategory.get("name").toString());
                categoryNode.add(subcategoryNode);
            }
            List<Map> prods = crudProductCategory.getFProductsSubcategory(idSub);
            if (!prods.isEmpty()) {
                for (Map<String, Object> prod : prods) {
                    final_prodNode = searchNode((String) prod.get("name"), root);
                    if (final_prodNode == null) {
                        final_prodNode = new DefaultMutableTreeNode(prod.get("name").toString());
                        subcategoryNode.add(final_prodNode);
                    }
                }
            }
        }

        /**
         * ***Muestro por producto*****
         */
        for (Map<String, Object> fp : listfproducts) {
            int idSubcategory = (int) fp.get("subcategory_id");
            Map<String, Object> subcategory = crudProductCategory.getSubcategory(idSubcategory);
            int idCategory = (int) subcategory.get("category_id");
            Map<String, Object> category = crudProductCategory.getCategory(idCategory);
            categoryNode = searchNode((String) category.get("name"), root);
            if (categoryNode == null) {
                categoryNode = new DefaultMutableTreeNode(category.get("name").toString());
                root.add(categoryNode);
            }
            subcategoryNode = searchNode((String) subcategory.get("name"), root);
            if (subcategoryNode == null) {
                subcategoryNode = new DefaultMutableTreeNode(subcategory.get("name").toString());
                categoryNode.add(subcategoryNode);
            }
            final_prodNode = searchNode((String) fp.get("name"), root);
            if (final_prodNode == null) {
                final_prodNode = new DefaultMutableTreeNode(fp.get("name").toString());
                subcategoryNode.add(final_prodNode);
            }
        }

        DefaultTreeModel modelo = new DefaultTreeModel(root);
        guiOrder.getTreeMenu().setModel(modelo);
        guiOrder.getTreeMenu().setCellRenderer(this);
    }

    public DefaultMutableTreeNode searchNode(String nodeStr, DefaultMutableTreeNode root) {
        DefaultMutableTreeNode node = null;
        Enumeration e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (nodeStr.equals(node.getUserObject().toString())) {
                return node;
            }
        }
        return null;
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
    
    /* carga los productos de la order actual */
    private void loadProducts() throws RemoteException{
        if(currentOrderId == null)
            return;
        List<Map> orderProducts = crudOrder.getOrderProducts(currentOrderId);
        for(Map Orderprod : orderProducts){
            Map prod = crudFproduct.getFproduct((int)Orderprod.get("fproduct_id"));
            Object[] row = new Object[7];
            row[0] = prod.get("id");
            float quantity = (float)Orderprod.get("quantity");
            row[1] = quantity;
            row[2] = prod.get("name");
            float price = (float)prod.get("sell_price");
            row[3] = ParserFloat.floatToString(price*quantity);
            row[4] = (boolean)Orderprod.get("done");
            row[5] = (boolean)Orderprod.get("commited");
            row[6] = (boolean)Orderprod.get("issued");
            guiOrder.getTableProductsDefault().addRow(row);
        }    
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
        
        //*******GuiOrder**************//
        if(e.getSource().equals(guiOrder.getBtnSend())){               
            if(currentOrderId == null){ //si el pedido es nuevo, carga todos los productos y los envia
                DefaultTableModel productsTable = guiOrder.getTableProductsDefault();
                List<Map<String,Object>> products = new LinkedList<>();
                for(int i = 0; i < productsTable.getRowCount(); i++){
                    Map<String,Object> prodMap = new HashMap();
                    prodMap.put("fproductId",productsTable.getValueAt(i, 0) );
                    prodMap.put("quantity",productsTable.getValueAt(i, 1));
                    prodMap.put("done", productsTable.getValueAt(i, 4));
                    prodMap.put("commited",productsTable.getValueAt(i, 5) );
                    prodMap.put("issued",true ); 
                    products.add(prodMap);
                }
                try {
                    Map<String, Object> newOrder = crudOrder.sendOrder(currentWaiterId,guiOrder.getjTextDescription().getText(),products); 
                    long idLong = (long)newOrder.get("id");
                    currentOrderId = (int)idLong;
                    productsTable.setRowCount(0);
                    loadProducts();
                    JOptionPane.showMessageDialog(guiOrder, "Nuevo pedido Enviado!", "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
                    guiOrder.getBtnSend().setEnabled(false);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{ //si el pedido existe agrega los productos que no han sido enviados, y envia el pedido
                DefaultTableModel productsTable = guiOrder.getTableProductsDefault();
                List<Map<String,Object>> products = new LinkedList<>();
                for(int i = 0; i < productsTable.getRowCount(); i++){
                    boolean issued = (boolean)productsTable.getValueAt(i, 6);
                    if(!issued){                       
                        Map<String,Object> prodMap = new HashMap();
                        prodMap.put("fproductId",productsTable.getValueAt(i, 0) );
                        prodMap.put("quantity",productsTable.getValueAt(i, 1));
                        prodMap.put("done", productsTable.getValueAt(i, 4));
                        prodMap.put("commited",productsTable.getValueAt(i, 5) );
                        prodMap.put("issued", true); 
                        products.add(prodMap);
                    }
                }
                try {
                    if(orderClosed){
                        JOptionPane.showMessageDialog(guiOrder, "           El pedido esta cerrado \n No se pueden agregar mas productos!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                        productsTable.setRowCount(0);
                        loadProducts();
                        return;
                    }
                    crudOrder.addProducts(currentOrderId, products);
                    productsTable.setRowCount(0);
                    loadProducts();
                    JOptionPane.showMessageDialog(guiOrder, "Productos Agregados y Enviados!", "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
                    guiOrder.getBtnSend().setEnabled(false);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
        if(e.getSource().equals(guiOrder.getBtnClose())){ //cierra el pedido          
            if(currentOrderId == null)
                return;
            try {
                crudOrder.closeOrder(currentOrderId);
                orderClosed = true;
                JOptionPane.showMessageDialog(guiOrder, "Pedido Cerrado!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        
        
        //*******GuiAmount**************//
        if (e.getSource().equals(guiAmount.getBtnAccept())) {
            try {
                List<Map> fproducts = crudFproduct.getFproducts(currentSelectedNodeName);
                if (fproducts.size() == 1) {
                    Map<String, Object> fp = fproducts.get(0);
                    Object[] row = new Object[7];
                    row[0] = fp.get("id");
                    row[1] = ParserFloat.stringToFloat(guiAmount.getTxtAmount().getText());
                    row[2] = currentSelectedNodeName;
                    float price = (float)fp.get("sell_price");
                    float amount = ParserFloat.stringToFloat(guiAmount.getTxtAmount().getText());
                    row[3] = ParserFloat.floatToString(price*amount);
                    row[4] = false;
                    row[5] = false;
                    row[6] = false;
                    guiOrder.getTableProductsDefault().addRow(row);
                } else {
                    JOptionPane.showMessageDialog(guiOrder, "No se encontro el producto o existen varios productos con el mismo nombre", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
                guiAmount.getTxtAmount().setText("1");
                guiAmount.setVisible(false);
                guiOrder.getBtnSend().setEnabled(true);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
