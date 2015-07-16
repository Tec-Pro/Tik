/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.waiter;

import controllers.waiter.ControllerGuiMain;
import controllers.waiter.ControllerGuiLoginGrid;
import gui.waiter.GuiMain;
import gui.waiter.GuiOrder;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    private final InterfaceFproduct crudFproduct;
    String currentSelectedNodeName = "";
    DefaultMutableTreeNode currentNode = null;
    private ImageIcon rootIcon;
    private ImageIcon categoryIcon;
    private ImageIcon subcategoryIcon;
    private ImageIcon productIcon;
    private final InterfaceOrder crudOrder;
    private GuiMain guiMain;
    private ControllerGuiMain controllerGuiMain;

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
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        guiOrder.setActionListener(this);
        rootIcon = new ImageIcon(getClass().getResource("/Icons/menu.png"));
        categoryIcon = new ImageIcon(getClass().getResource("/Icons/category.png"));
        subcategoryIcon = new ImageIcon(getClass().getResource("/Icons/subcategory.png"));
        productIcon = new ImageIcon(getClass().getResource("/Icons/products.png"));

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
        loadProducts();

    }

    private void removeRowProviderCategoriesTable() {
        int selectedRow = guiOrder.getTableProducts().getSelectedRow();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiOrder.getTableProductsDefault());
        //Me fijo el id de la categoría seleccionada.
        boolean isPay = (boolean) guiOrder.getTableProducts().getValueAt(selectedRow, 4) || (boolean) guiOrder.getTableProducts().getValueAt(selectedRow, 5);
        if (isPay) {
            JOptionPane.showMessageDialog(guiOrder, "Producto ya hecho o cobrado, no se puede borrar", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                long idLong = (long) currentOrder.get("id");
                currentOrderId = (int) idLong;
                crudOrder.deleteProduct(Integer.valueOf(guiOrder.getTableProducts().getValueAt(selectedRow, 7).toString()), Integer.valueOf(guiOrder.getTableProducts().getValueAt(selectedRow, 0).toString()), ParserFloat.stringToFloat(guiOrder.getTableProducts().getValueAt(selectedRow, 1).toString()), currentOrderId);
                categoryModel.removeRow(selectedRow);
                //aca debo borrar el articulo y restaurar el stock
                JOptionPane.showMessageDialog(guiOrder, "Producto borrado", "", JOptionPane.INFORMATION_MESSAGE);
                controllerGuiMain.loadOrders(controllerGuiMain.idWaiter, controllerGuiMain.seeAll());

            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }

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
            Object[] row = new Object[8];
            row[0] = prod.get("id");
            float quantity = (float) Orderprod.get("quantity");
            row[1] = quantity;
            row[2] = prod.get("name");
            float price = (float) prod.get("sell_price");
            row[3] = ParserFloat.floatToString(price * quantity);
            if (!(boolean) Orderprod.get("paid") && !(boolean) Orderprod.get("discount")) {
                totalPrice += price * quantity;
            }
            row[4] = (boolean) Orderprod.get("done");
            row[5] = (boolean) Orderprod.get("paid");
            row[6] = (boolean) Orderprod.get("discount");
            row[7] = (Integer) Orderprod.get("id");
            guiOrder.getTableProductsDefault().addRow(row);
        }
        /**
         * AGREGO EXCEPCIONES
         */
        float paidEx = crudOrder.getPaidException(currentOrderId);
        float ex = crudOrder.getException(currentOrderId);
        if (ex > 0) {
            Object[] row = new Object[8];
            row[0] = "";
            row[1] = 1;
            row[2] = "exepciones no pagas";
            totalPrice = totalPrice + ex;
            row[3] = ex;
            row[4] = true;
            row[5] = false;
            row[6] = false;
            row[7] = -1;
            guiOrder.getTableProductsDefault().addRow(row);
        }
        if (paidEx > 0) {
            Object[] row = new Object[8];
            row[0] = "";
            row[1] = 1;
            row[2] = "exepciones pagas";
            row[3] = paidEx;
            row[4] = true;
            row[5] = false;
            row[6] = false;
            row[7] = -1;
            guiOrder.getTableProductsDefault().addRow(row);
        }
        guiOrder.getjTextDescription().setText(currentOrder.get("description").toString());
        Integer persons = (Integer) currentOrder.get("persons");
        if (persons == null) {
            persons = 0;
        }
        guiOrder.getjSpinnerPersons().setValue(persons);
        guiOrder.getLblTotalPrice().setText(ParserFloat.floatToString(totalPrice-(float)currentOrder.get("discount")));
        guiOrder.getLblDiscount().setText(ParserFloat.floatToString((float)currentOrder.get("discount")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //*******GuiOrder**************//
        if (e.getSource().equals(guiOrder.getBtnSend())) {
            if ((boolean) currentOrder.get("closed")) {
                int res = JOptionPane.showConfirmDialog(guiOrder, "           El pedido esta cerrado \n Desea agregar más productos?", "Atencion", JOptionPane.WARNING_MESSAGE);

            }
            for (int i = 0; i < guiOrder.getTableProducts().getRowCount(); i++) {
                if (!(boolean) guiOrder.getTableProducts().getValueAt(i, 5)) {
                    try {
                        int isDiscount = 0;
                        if ((boolean) guiOrder.getTableProducts().getValueAt(i, 6)) {
                            isDiscount = 1;
                        } else {
                            isDiscount = 0;
                        }
                        crudOrder.discountProduct(Integer.valueOf(guiOrder.getTableProducts().getValueAt(i, 7).toString()), isDiscount, Integer.valueOf(guiOrder.getTableProducts().getValueAt(i, 0).toString()), (int) currentOrder.get("user_id"), currentOrderId);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            DefaultTableModel productsTable = guiOrder.getTableProductsDefault();
            try {
                long idLong = (long) currentOrder.get("id");
                currentOrderId = (int) idLong;
            } catch (java.lang.ClassCastException ex) {
                currentOrderId = (int) currentOrder.get("id");
            }
            try {
                crudOrder.setDiscountEfec(currentOrderId, ParserFloat.stringToFloat(guiOrder.getLblDiscount().getText()));
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            productsTable.setRowCount(0);
            try {
                loadProducts();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            //aca debo llamar para crear los descuentos 
            JOptionPane.showMessageDialog(guiOrder, "Pedido Actualizado!", "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
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

}
