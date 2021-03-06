/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.controllerGuiOrder;

import controller.ControllerGuiMain;
import controller.login.ControllerGuiLoginGrid;
import gui.GuiMain;
import gui.login.GuiLoginGrid;
import gui.order.ComponentOrderBtn;
import gui.order.GuiAmount;
import gui.order.GuiException;
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
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.payments.ImplementsDataSourcePayment;
import reports.payments.Payment;
import utils.Config;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author alan
 */
public class ControllerGuiOrder extends DefaultTreeCellRenderer implements ActionListener {

    private Integer currentOrderId;
    private int currentWaiterId;
    private Map<String, Object> currentOrder;
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
    private GuiMain guiMain;
    private ControllerGuiMain controllerGuiMain;

    private ImplementsDataSourcePayment datasource;

    /**
     * Setea el id del mozo actual, y el id del pedido actual.
     *
     * @param orderId si es un nuevo pedido, poner en null
     * @param waiterId
     */
    public void setIds(Integer orderId, int waiterId) {
        guiOrder.getTableProductsDefault().setRowCount(0);
        currentOrderId = orderId;
        currentWaiterId = waiterId;
        if (currentOrderId != null) {
            try {
                currentOrder = crudOrder.getOrder(currentOrderId);
                guiOrder.getLblOrderNum().setText(currentOrder.get("order_number").toString());
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            guiOrder.getLblOrderNum().setText("");
        }
        guiOrder.getLblTotalPrice().setText("");
        try {
            loadProducts();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        //guiOrder.getBtnSend().setEnabled(false);
    }

    public ControllerGuiOrder(GuiOrder go) throws NotBoundException, MalformedURLException, RemoteException {
        guiOrder = go;
        //guiOrder.getBtnSend().setEnabled(false);
        crudProductCategory = (InterfaceCategory) InterfaceName.registry.lookup(InterfaceName.CRUDCategory);
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        guiOrder.setActionListener(this);
        rootIcon = new ImageIcon(getClass().getResource("/Icons/menu.png"));
        categoryIcon = new ImageIcon(getClass().getResource("/Icons/category.png"));
        subcategoryIcon = new ImageIcon(getClass().getResource("/Icons/subcategory.png"));
        productIcon = new ImageIcon(getClass().getResource("/Icons/products.png"));
        guiAmount = new GuiAmount(guiOrder, true);
        guiAmount.setActionListener(this);
        guiOrder.getTreeMenu().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                TreePath currentSelection = guiOrder.getTreeMenu().getSelectionPath();
                if (currentSelection != null) {
                    currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
                    currentSelectedNodeName = currentNode.toString();// nombre de la hoja seleccionada
                    if (currentNode.getLevel() == 3) {
                        if (me.getClickCount() == 2) {
                            guiAmount.getLblProd().setText(currentSelectedNodeName);
                            guiAmount.setLocationRelativeTo(null);
                            guiAmount.setVisible(true);
                        }
                    }
                }
            }
        });

        guiOrder.getTableMostUsedProducts().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int r = guiOrder.getTableMostUsedProducts().getSelectedRow();
                    guiAmount.getLblProd().setText(guiOrder.getTableMostUsedProducts().getValueAt(r, 0).toString());
                    guiAmount.setLocationRelativeTo(null);
                    guiAmount.setVisible(true);
                }
            }
        });

        guiOrder.getTxtSearch().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent evt) {
                try {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        search();
                    }
                    if (guiOrder.getTxtSearch().getText().length() == 0) {
                        CreateTree();
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        guiOrder.getTableProducts().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Si se hace doble click en la tabla de categorías del proveedor.
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    if (target.getSelectedRow() != -1) {
                        //Elimino la categoría de la tabla.
                        removeRowProviderCategoriesTable();
                    }
                }
            }
        });
        CreateTree();
        loadProducts();
        datasource = new ImplementsDataSourcePayment();

    }

    private void removeRowProviderCategoriesTable() {
        int selectedRow = guiOrder.getTableProducts().getSelectedRow();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiOrder.getTableProductsDefault());
        //Me fijo el id de la categoría seleccionada.
        boolean isDone = (boolean) guiOrder.getTableProducts().getValueAt(selectedRow, 6);
        if (isDone) {
            JOptionPane.showMessageDialog(guiOrder, "Producto ya enviado, no se puede modificar", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            categoryModel.removeRow(selectedRow);
        }
    }
//    public void addMyComponent(String user) {
//        //instancia nueva a componente
//        ComponentOrderBtn OrderBtn = new ComponentOrderBtn(user);
//        OrderBtn.setBackground(Color.RED);
//        OrderBtn.btn.addActionListener(this);//escucha eventos
//        OrderBtn.setSize(guiMain.getBtnLogin().getSize());
//        guiMain.getPanelActiveOrders().add(OrderBtn);//se añade al jpanel 
//        guiMain.getPanelActiveOrders().revalidate();
//        OrderBtn.setVisible(true);
//        //se añade al MAP
//        controllerGuiMain.getButtonsOrder().put(user, OrderBtn);
//    }

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
        //abro todas las ramas
        for (int i = 0; i < guiOrder.getTreeMenu().getRowCount(); i++) {
            guiOrder.getTreeMenu().expandRow(i);
        }
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

        //*****Cargo los ultimos 5 productos usados a la tabla**********
        guiOrder.getTableMostUsedProductsDefault().setRowCount(0);
        List<Map> lastFproducts = crudFproduct.getLastUsedProducts();
        for (Map fp : lastFproducts) {
            Object[] row = new Object[1];
            row[0] = fp.get("name");
            guiOrder.getTableMostUsedProductsDefault().addRow(row);
        }

    }

    /* carga los productos de la order actual */
    private void loadProducts() throws RemoteException {
        guiOrder.getjTextDescription().setText("");
        guiOrder.getjSpinnerPersons().setValue(0);
        if (currentOrderId == null) {
            return;
        }

        float totalPrice = 0; // para actualizar el precio total del pedido
        List<Map> orderProducts = crudOrder.getOrderProducts(currentOrderId);
        for (Map Orderprod : orderProducts) {
            Map prod = crudFproduct.getFproduct((int) Orderprod.get("fproduct_id"));
            Object[] row = new Object[9];
            row[0] = prod.get("id");
            float quantity = (float) Orderprod.get("quantity");
            row[1] = quantity;
            row[2] = prod.get("name");
            float price = (float) prod.get("sell_price");
            row[3] = ParserFloat.floatToString(price * quantity);
            if (!(boolean) Orderprod.get("paid")&& !(boolean) Orderprod.get("discount")) {
                totalPrice += price * quantity;
            }
            row[4] = (boolean) Orderprod.get("done");
            row[5] = (boolean) Orderprod.get("commited");
            row[6] = (boolean) Orderprod.get("issued");
            row[7] = (boolean) Orderprod.get("paid");
            row[8] = (boolean) Orderprod.get("discount");
            guiOrder.getTableProductsDefault().addRow(row);
        }
        /**
         * AGREGO EXCEPCIONES
         */
        float paidEx = crudOrder.getPaidException(currentOrderId);
        float ex = crudOrder.getException(currentOrderId);
        if (ex > 0) {
            Object[] row = new Object[9];
            row[0] = "";
            row[1] = 1;
            row[2] = "exepciones no pagas";
            totalPrice = totalPrice + ex;
            row[3] = ex;
            row[4] = true;
            row[5] = true;
            row[6] = true;
            row[7] = false;
            row[8] = false;
            guiOrder.getTableProductsDefault().addRow(row);
        }
        if (paidEx > 0) {
            Object[] row = new Object[9];
            row[0] = "";
            row[1] = 1;
            row[2] = "exepciones pagas";
            row[3] = paidEx;
            row[4] = true;
            row[5] = true;
            row[6] = true;
            row[7] = true;
            row[8] = false;
            guiOrder.getTableProductsDefault().addRow(row);
        }
        guiOrder.getjTextDescription().setText(currentOrder.get("description").toString());
        Integer persons = (Integer) currentOrder.get("persons");
        if (persons == null) {
            persons = 1;
        }
        guiOrder.getjSpinnerPersons().setValue(persons);
        float aux=0;
        if(currentOrder.get("discount")!=null)
            aux=(float)currentOrder.get("discount");
        if(totalPrice>aux){// me fijo que lo que haya que cobrar sea mayor que el descuento
            guiOrder.getLblTotalPrice().setText(ParserFloat.floatToString(totalPrice-aux));
            guiOrder.getLblDiscount().setText(ParserFloat.floatToString(aux));
        }else{
            guiOrder.getLblTotalPrice().setText(ParserFloat.floatToString(totalPrice));
            guiOrder.getLblDiscount().setText(ParserFloat.floatToString((float)0));
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
        if (e.getSource().equals(guiOrder.getBtnSend())) {

            if (currentOrderId == null) { //si el pedido es nuevo, carga todos los productos y los envia
                DefaultTableModel productsTable = guiOrder.getTableProductsDefault();
                List<Map<String, Object>> products = new LinkedList<>();

                for (int i = 0; i < productsTable.getRowCount(); i++) {
                    Map<String, Object> prodMap = new HashMap();
                    prodMap.put("fproductId", productsTable.getValueAt(i, 0));
                    prodMap.put("quantity", productsTable.getValueAt(i, 1));
                    prodMap.put("done", productsTable.getValueAt(i, 4));
                    prodMap.put("commited", productsTable.getValueAt(i, 5));
                    prodMap.put("issued", true);
                    products.add(prodMap);
                }
                try {
                    int persons = (Integer) guiOrder.getjSpinnerPersons().getValue();
                    currentOrder = crudOrder.sendOrder(currentWaiterId, guiOrder.getjTextDescription().getText(), persons, products);
                    guiOrder.getLblOrderNum().setText(currentOrder.get("order_number").toString());
                    long idLong = (long) currentOrder.get("id");
                    currentOrderId = (int) idLong;
                    productsTable.setRowCount(0);
                    loadProducts();
                    JOptionPane.showMessageDialog(guiOrder, "Nuevo pedido Enviado!", "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
                    // guiOrder.getBtnSend().setEnabled(false);
//                    addMyComponent(currentOrder.get("order_number").toString());
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { //si el pedido existe agrega los productos que no han sido enviados, y envia el pedido
                DefaultTableModel productsTable = guiOrder.getTableProductsDefault();
                List<Map<String, Object>> products = new LinkedList<>();
                for (int i = 0; i < productsTable.getRowCount(); i++) {
                    boolean issued = (boolean) productsTable.getValueAt(i, 6);
                    if (!issued) {
                        Map<String, Object> prodMap = new HashMap();
                        prodMap.put("fproductId", productsTable.getValueAt(i, 0));
                        prodMap.put("quantity", productsTable.getValueAt(i, 1));
                        prodMap.put("done", productsTable.getValueAt(i, 4));
                        prodMap.put("commited", productsTable.getValueAt(i, 5));
                        prodMap.put("issued", true);
                        products.add(prodMap);
                    }
                }
                try {
                    if ((boolean) currentOrder.get("closed")) {
                        int res = JOptionPane.showConfirmDialog(guiOrder, "           El pedido esta cerrado \n Desea agregar más productos?", "Atencion", JOptionPane.OK_CANCEL_OPTION);
                        if (res == JOptionPane.CANCEL_OPTION) {
                            productsTable.setRowCount(0);
                            loadProducts();
                            return;
                        }
                    }
                    int persons = (Integer) guiOrder.getjSpinnerPersons().getValue();
                    crudOrder.updateOrder(currentOrderId, guiOrder.getjTextDescription().getText(), persons, products);
                    productsTable.setRowCount(0);
                    loadProducts();
                    JOptionPane.showMessageDialog(guiOrder, "Pedido Actualizado!", "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
                    //guiOrder.getBtnSend().setEnabled(false);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int r = JOptionPane.showConfirmDialog(null, "Quiere hacer algo mas?");
            if (r == 0) {
                guiOrder.setVisible(false);
                return;
            } else {
                if (r == 1) {
                    guiOrder.setVisible(false);
                    guiOrder.getParent().setVisible(false);
                    try {
                        controllerGuiMain.loadOrders(controllerGuiMain.idWaiter, controllerGuiMain.seeAll());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    controllerGuiMain.setLoginGridVisible(true);

                } else {
                    return;
                }
            }
        }

        if (e.getSource().equals(guiOrder.getBtnClose())) { //cierra el pedido
            
            int r = JOptionPane.showConfirmDialog(null, "Desea cerrar el pedido? \n \n DEBE COBRARLE: $"+guiOrder.getLblTotalPrice().getText());
            if (r != 0) {
                return;
            }
            if (currentOrderId == null) {
                return;
            }
            try {
                r = JOptionPane.showConfirmDialog(null, "Desea imprimir un comprobante?");
                if(r==JOptionPane.OK_OPTION){
                    printTicket(currentOrderId);
                }
                crudOrder.closeOrder(currentOrderId); 
                    
                
                currentOrder = crudOrder.getOrder(currentOrderId);
                ControllerGuiMain.seeAll();
                JOptionPane.showMessageDialog(guiOrder, "Pedido Cerrado!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiOrder.setVisible(false);
        }

        if (e.getSource().equals(guiOrder.getBtnCommit())) {
            if (currentOrderId == null) {
                JOptionPane.showMessageDialog(guiOrder, "El pedido no ha sido creado todavia!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if ((boolean) currentOrder.get("closed")) {
                JOptionPane.showMessageDialog(guiOrder, "El pedido esta cerrado!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                crudOrder.commitProducts(currentOrderId);
                JOptionPane.showMessageDialog(guiOrder, "Productos entregados!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                guiOrder.getTableProductsDefault().setRowCount(0);
                loadProducts();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(guiOrder.getBtnException())) {
            if (currentOrderId == null) {
                JOptionPane.showMessageDialog(guiOrder, "El pedido no ha sido creado todavia!", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            GuiException ex = new GuiException(guiMain, true);
            ex.setLocationRelativeTo(guiOrder);
            ex.setVisible(true);
            if (ex.getReturnStatus() == GuiException.RET_OK) {
                float f = ex.getAmount();
                try {
                    crudOrder.addException(currentOrderId, f);
                    Object[] row = new Object[9];
                    row[0] = "";
                    row[1] = 1;
                    row[2] = "";
                    row[3] = f;
                    row[4] = true;
                    row[5] = true;
                    row[6] = true;
                    row[7] = false;
                    row[8] = false;
                    guiOrder.getTableProductsDefault().addRow(row);
                    float totalPrice = ParserFloat.stringToFloat(guiOrder.getLblTotalPrice().getText());
                    guiOrder.getLblTotalPrice().setText(ParserFloat.floatToString(totalPrice + f));

                } catch (RemoteException ex1) {
                    Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

        //*******GuiAmount**************//
        if (e.getSource().equals(guiAmount.getBtnAccept())) {
            try {
                List<Map> fproducts = crudFproduct.getFproducts(guiAmount.getLblProd().getText());
                if (fproducts.size() == 1) {
                    Map<String, Object> fp = fproducts.get(0);
                    Object[] row = new Object[9];
                    row[0] = fp.get("id");
                    row[1] = ParserFloat.stringToFloat(guiAmount.getTxtAmount().getText());
                    row[2] = guiAmount.getLblProd().getText();
                    float price = (float) fp.get("sell_price");
                    float amount = ParserFloat.stringToFloat(guiAmount.getTxtAmount().getText());
                    row[3] = ParserFloat.floatToString(price * amount);
                    row[4] = false;
                    row[5] = false;
                    row[6] = false;
                    row[7] = false;
                    row[8] = false;
                    guiOrder.getTableProductsDefault().addRow(row);
                } else {
                    JOptionPane.showMessageDialog(guiOrder, "No se encontro el producto o existen varios productos con el mismo nombre", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
                guiAmount.getTxtAmount().setText("1");
                guiAmount.setVisible(false);
                // guiOrder.getBtnSend().setEnabled(true);

            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * imprime el ticket dado un id de orden, solo muestra lo que no esta pago
     *
     * @param id
     */
    private void printTicket(int id) throws RemoteException {
        List<Map> ord = crudOrder.getDataPrinterOrd(id);
        for (Map m : ord) {
            Payment p = new Payment((String) m.get("name"), (float) m.get("quantity"), (float) m.get("sell_price"), (float) m.get("paid_exceptions"),(float) m.get("discount"));
            datasource.addPayment(p);
        }
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/payments/ticket.jasper"));//cargo el reporte
            JasperPrint jasperPrint;
            jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
            JasperPrintManager.printReport(jasperPrint,true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        datasource.removeAllFinalProduct();
    }
}
