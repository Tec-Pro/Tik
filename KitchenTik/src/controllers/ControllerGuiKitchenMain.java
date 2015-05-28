/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.login.GuiLogin;
import gui.login.GuiOnlineUsers;
import gui.main.GuiKitchenMain;
import gui.order.GuiKitchenOrderDetails;
import gui.order.GuiKitchenOrderPane;
import interfaces.InterfaceFproduct;
import interfaces.InterfaceGeneralConfig;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;
import utils.Pair;
import utils.SoundPlayer;

/**
 *
 * @author eze
 */
public class ControllerGuiKitchenMain implements ActionListener {

    //Guis
    private GuiLogin guiLogin;
    private static GuiKitchenOrderDetails guiOrderDetails;
    private static GuiKitchenMain guiKitchenMain;
    private static GuiKitchenOrderPane guiOrderPane;
    //Interfaces
    private static InterfaceGeneralConfig generalConfig;
    private static InterfaceOrder crudOrder;
    private static InterfaceFproduct crudFProduct;
    private static InterfaceUser crudUser;
    private final InterfacePresence crudPresence;
    //Conjunto(set) con los cocineros online
    private final Set<Map> online;
    //Atributos para el control de retrasos en pedidos
    private static Timer timer;
    private static final Integer time = 10000;//tiempo de intervalo de actualizacion de retrasos(actualmente 10 segundos)
    private static SoundPlayer soundPlayer;
    //lista de ordersPanels con todos los paneles de la gui
    private static LinkedList<GuiKitchenOrderPane> listOrdersPanels;

    private static LinkedList<Integer> orderList;
    //
    private static DefaultTableModel dtmOrderDetails;

    /**
     *
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiKitchenMain() throws NotBoundException, MalformedURLException, RemoteException {
        orderList = new LinkedList<>();
        soundPlayer = new SoundPlayer();
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudFProduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        generalConfig = (InterfaceGeneralConfig) InterfaceName.registry.lookup(InterfaceName.GeneralConfig);
        online = new HashSet<>();
        for (Map m : crudPresence.getCooks()) {
            online.add(m);
        }
        guiOrderDetails = new GuiKitchenOrderDetails(guiKitchenMain, true);
        guiOrderDetails.setVisible(false);

        dtmOrderDetails = guiOrderDetails.getDefaultTableModelOrderProducts();

        guiKitchenMain = new GuiKitchenMain();
        guiOrderPane = new GuiKitchenOrderPane();
        guiKitchenMain.setVisible(true);
        guiKitchenMain.setActionListener(this);
        guiOrderDetails.setActionListener(this);
        guiOrderPane.setActionListener(this);
        listOrdersPanels = new LinkedList<>();
        guiLogin = null;
        //traigo todos los pedidos que estan abiertos
        refreshOpenOrders();

        //controlo cada 1minuto si hay algun pedido retrasado
        timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    //Cada cierto tiempo "time" hago una busqueda de las ordenes retrasadas
                    searchDelayedOrders();
                } catch (ParseException | RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        timer.start();
    }

    /**
     * Computa la diferencia entre dos fechas dadas.
     *
     * @param date1 Fecha menor (fecha de creacion del pedido)
     * @param date2 Fecha mayor (fecha actual)
     * @return Map con la diferencia entre ambas fechas. (Formato del Map:
     * {DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS, MICROSECONDS, NANOSECONDS})
     */
    public static Map<String, Object> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        LinkedList<TimeUnit> units = new LinkedList(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map result = new LinkedHashMap();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit.toString(), diff);
        }
        return result;
    }

    private static void searchDelayedOrders() throws ParseException, RemoteException {
        Iterator<GuiKitchenOrderPane> itr = listOrdersPanels.iterator();
        //Recorro todos los paneles de la gridbaglayout
        while (itr.hasNext()) {
            final GuiKitchenOrderPane orderPane = itr.next();//saco el panel actual
            final Timestamp currentTime = new Timestamp(System.currentTimeMillis());//hora y fecha actual
            Timestamp timeOrderArrival = Timestamp.valueOf(orderPane.getLblTimeOrderArrival().getText());//hora y fecha del pedido
            Map<String, Object> diff = computeDiff(timeOrderArrival, currentTime);//diferencia entre horas
            //Si transcurrieron mas minutos de los especificados por el usuario en la configuracion
            //Y ademas el pedido no esta pospuesto (No esta coloreado en amarillo)
            if (orderPane.getColor() != 2 && (Integer.parseInt(diff.get("MINUTES").toString()) >= Integer.parseInt(generalConfig.getDelayTime())
                    || Integer.parseInt(diff.get("HOURS").toString()) > 0
                    || Integer.parseInt(diff.get("DAYS").toString()) > 0)) {
                soundPlayer.playSound();//Alerta sonora
                if (!orderPane.isActiveTimer()) {
                    //Parpadea el color del panel, en rojo, avisando que el pedido se retraso
                    orderPane.setTimer(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (orderPane.getColor() == 3) {
                                orderPane.setColor(0);
                            } else {
                                orderPane.setColor(3);
                            }
                        }
                    }, 3600, 500);
                }
                orderPane.getBtnPostpone().setEnabled(true);
                orderPane.getBtnPostpone().addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        orderPane.setColor(2);//Coloreo en amarillo el pedido, en señal de pospuesto
                        soundPlayer.stopSound();
                        orderPane.getBtnPostpone().setEnabled(false);
                        orderPane.stopTimer();
                    }
                });
//                System.out.println("El pedido: " + orderPane.getLblOrderNumber().getText() + " esta retrasado.");
//                System.out.println("Tiempo de retraso: " + diff.toString());
//                System.out.println("");
            }

        }
    }

    private static void loadGuiOrderDetails(int idOrder) throws RemoteException {
        GuiKitchenOrderPane orderPane = null;
        for(GuiKitchenOrderPane gui: listOrdersPanels){
            if ((Integer) gui.getOrder().first().get("id") == idOrder){
                orderPane = gui;
            }
        }
        guiOrderDetails.getTxtOrderDescription().setText((String) orderPane.getOrder().first().get("description"));
        guiOrderDetails.getBtnSendOrderDone().setEnabled(false);
        guiOrderDetails.setOrderID((Integer) orderPane.getOrder().first().get("id"));
        dtmOrderDetails.setRowCount(0);
        for (Map<String, Object> m : orderPane.getOrder().second()) { // For each product
            Object rowDtm[] = new Object[4]; // New row
            rowDtm[0] = m.get("fproduct_id");
            rowDtm[1] = m.get("name");
            rowDtm[2] = m.get("quantity");
            rowDtm[3] = false;
            dtmOrderDetails.addRow(rowDtm);
        }
        guiOrderDetails.setTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) { // When a value in the table changes, I make configurations
                boolean modify = false;
                int i = 0;
                while (i < dtmOrderDetails.getRowCount() && !modify) { // If one value is true, It's able to send and order and check before closing the diag
                    if ((boolean) dtmOrderDetails.getValueAt(i, 3) == true) {
                        modify = true;
                    } else {
                        i++;
                    }
                }
                guiOrderDetails.setModified(modify);
                guiOrderDetails.getBtnSendOrderDone().setEnabled(modify);
            }
        });
        guiOrderDetails.setVisible(true);
        guiOrderDetails.setModal(true);
        guiOrderDetails.toFront();
    }

    private static boolean noMoreToCook(int id) throws RemoteException {
        boolean noMore = true;
        Map<String, Object> order = crudOrder.getOrder(id);
        List<Map> orderProducts = crudOrder.getOrderProducts(id);
        for (Map<String, Object> m : orderProducts) { // For each product 
            if (((boolean) m.get("done") == false)) {
                noMore = false;
            }
        }
        return noMore;
    }

    /**
     * Metodo invocado por el servidor cuando se crea un nuevo Pedido/Orden en
     * el modulo Waiter. Este metodo cargara el pedido creado en la gui
     * correspondiente
     *
     * @param order
     * @throws RemoteException
     */
    public static void addOrder(final Pair<Map<String, Object>, List<Map>> order) throws RemoteException {

        Timestamp date = Timestamp.valueOf("1990-01-01 01:01:01");//inicio la fecha con un valor minimo
        final String desc;
        String aux = "";
        for (Map m : order.second()) {
            aux = aux + m.get("name") + " x" + m.get("quantity") + "\n";
            //calculo la hora del pedido en base al ultimo producto añadido al mismo
            if (date.before(Timestamp.valueOf(m.get("updated_at").toString()))) {
                 date = Timestamp.valueOf(m.get("updated_at").toString());
             }
        }
        desc = aux; //concateno id de pedido mas el nombre del mozo que lo pidio
        String orderName = "hola"; //order.first().get("order_number").toString() + " - " + (crudUser.getUser(Integer.parseInt((order.first().get("user_id")).toString()))).get("name");
        Integer orderId = Integer.parseInt(order.first().get("id").toString());
        guiOrderPane = new GuiKitchenOrderPane(orderName, desc, date.toString(), order);
        guiOrderPane.getTxtOrderDescription().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        loadGuiOrderDetails((Integer) order.first().get("id"));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        guiOrderPane.getBtnOrderReady().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                List<Integer> list = new LinkedList();

                for (Map m : guiOrderPane.getOrder().second()) {
                    System.out.println("agregue a lista de prod listos: "+m.get("name"));
                    list.add((Integer) m.get("fproduct_id"));
                }
                List<Map> updateOrder = new LinkedList<>();
                try {
                    System.out.println("Updating order: "+guiOrderDetails.getOrderID());
                    updateOrder = crudOrder.updateOrdersReadyProducts(guiOrderDetails.getOrderID(), list);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    guiKitchenMain.cleanAllOrders();//borro todas las ordenes de la gui
                    refreshOpenOrders(); //Actualizo la gui de pedidos para eliminar el que se realizo
                    guiOrderDetails.closeWindow();
                    guiKitchenMain.validate();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                guiOrderPane.setModified(false);
            }
        });
        orderList.add(Integer.parseInt(order.first().get("id").toString()));
        guiKitchenMain.addElementToOrdersGrid(guiOrderPane);
        listOrdersPanels.add(guiOrderPane);

//        final GuiMenuDetail newOrder = new GuiMenuDetail();
//            newOrder.getTxtDetail().addMouseListener(new MouseAdapter() {//agrego un mouselistener
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    if (e.getClickCount() == 2) {
//                        orderClic = newOrder.getIdOrder();
//                        guiOrder.setLocationRelativeTo(null);
//                        controllerGuiOrder.setIds(orderClic, idWaiter);
//                        guiOrder.setVisible(true);
//                        try {
//                            controllerGuiOrder.CreateTree();
//                        } catch (RemoteException ex) {
//                            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            });
//            newOrder.getLblDone().addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    System.out.println("done id=" + newOrder.getIdOrder());
//                }
//            });
//            newOrder.getLblCommited().addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    System.out.println("commited id=" + newOrder.getIdOrder());
//                }
//            });

    }

    /**
     * Metodo invocado por el servidor cuando se actualiza informacion sobre
     * Pedido/Orden en el modulo Waiter. Este metodo actualizara el pedido
     * modificado en la
     *
     *
     * @param order
     * @throws RemoteException
     */
    public static void updatedOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        /* "order" es el Map de un pedido con la siguiente estructura:
         * {order_number, id, user_id, closed=boolean, description}*/
        /* "orderProducts" es una lista de Maps, de los productos finales que
         * contiene el pedido "order", cada Map tiene la siguiente estructura:
         * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        //List<Map> orderProducts = crudOrder.getOrderProducts(id);
        //Aca debe actualizarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        int size = guiKitchenMain.getOrdersPanel().getComponentCount(); // the amount of orders in the order panel
        int i = 0;
        boolean check = false;
        while (i < size || check) {
            if (orderList.get(i) == Integer.parseInt(order.first().get("id").toString())) {
                check = true;
            } else {
                i++;
            }
        }
        guiKitchenMain.updateElementOfOrdersGrid(i, order);
        guiKitchenMain.cleanAllOrders();
        refreshOpenOrders();
    }

    /* Recarga todos los pedidos abiertos, sin realizar aun en cocina en la gui. */
    //PULIR ESTE METODO PARA QUE TRAIGA LOS PEDIDOS COMO MAXIMO DE DOS DIAS, Y NO TODOS
    public static void refreshOpenOrders() throws RemoteException {
        listOrdersPanels = new LinkedList<>();//reinicio la lista de ordersPanels para que se actualice en addOrder
        orderList = new LinkedList<>();//reinicio la lista de ordenes para que se actualice en addOrder
        List<Map> allOrders = crudOrder.getAllOrders();//saco todas los pedidos cargados
        for (Map<String, Object> order : allOrders) {
            if (order.get("closed").equals(false)) {//si el pedido no esta cerrado
                boolean orderClosed = true;
                //saco todos los productos asociados al pedido
                List<Map> orderProducts = crudOrder.getOrderProducts(Integer.parseInt(order.get("id").toString()));

                Iterator<Map> itr = orderProducts.iterator();
                while (itr.hasNext() && orderClosed) {
                    Map<String, Object> orderProduct = itr.next();
                    Map<String, Object> fproduct = crudFProduct.getFproduct(Integer.parseInt(orderProduct.get("fproduct_id").toString()));
                    //si el producto corresponde a Cocina y no fue hecho
                    if (fproduct.get("belong").equals("Cocina") && orderProduct.get("done").equals(false)) {
                        orderClosed = false; //marco el pedido como abierto
                    }
                }

                if (!orderClosed) {//si el pedido esta abierto (NO CERRADO)
                    List<Map> listCook = new LinkedList();
                    for (Map<String, Object> prod : orderProducts) {
                        Map<String, Object> finalProd = crudFProduct.getFproduct(Integer.parseInt(prod.get("fproduct_id").toString())); //get the final product to verify where it belongs
                        if (finalProd.get("belong").equals("Cocina") && prod.get("done").equals(false)) {
                            Map<String, Object> addProd = new HashMap(); //Create a map with the product

                            addProd.put("fproduct_id", (int) prod.get("fproduct_id"));
                            addProd.put("quantity", (float) prod.get("quantity"));
                            addProd.put("done", (boolean) prod.get("done"));
                            addProd.put("commited", (boolean) prod.get("commited"));
                            addProd.put("issued", (boolean) prod.get("issued"));
                            addProd.put("created_at", prod.get("created_at"));
                            addProd.put("updated_at", prod.get("updated_at"));

                            addProd.put("name", finalProd.get("name"));

                            listCook.add(addProd);
                        }
                    }
                    addOrder(new Pair(order, listCook));//agrego el pedido en kitchen
                }
            }
        }
    }

    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiOrderDetails.getBtnCheckAll())) { // If one value is false, I set them all with true. If they're all true, I set them all with false.
            boolean modify = false;
            int i = 0;
            while (i < dtmOrderDetails.getRowCount() && !modify) {
                if ((boolean) dtmOrderDetails.getValueAt(i, 3) == false) {
                    modify = true;
                } else {
                    i++;
                }
            }
            for (i = 0; i < dtmOrderDetails.getRowCount(); i++) {
                dtmOrderDetails.setValueAt(modify, i, 3);
            }
        }
        if (ae.getSource().equals(guiOrderDetails.getBtnSendOrderDone())) { // Send the Order
            List<Integer> list = new LinkedList();
            int i = 0;
            while (i < dtmOrderDetails.getRowCount()) { // Add all products that are true
                if ((boolean) dtmOrderDetails.getValueAt(i, 3) == true) {
                    int id = (Integer) dtmOrderDetails.getValueAt(i, 0);
                    list.add(id);
                    int j = 0;
                    for (Map m : guiOrderPane.getOrder().second()) {// Remove each of those products from the local List
                        if ((Integer) m.get("fproduct_id") == id) {
                            System.out.println("agregue a lista de prod listos: "+m.get("name"));
                            guiOrderPane.getOrder().second().remove(j);
                        }
                        j++;
                    }
                }
                i++;
            }
            List<Map> updateOrder = new LinkedList<>();
            try {
                System.out.println("Updating order: "+guiOrderDetails.getOrderID());
                updateOrder = crudOrder.updateOrdersReadyProducts(guiOrderDetails.getOrderID(), list);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (guiOrderPane.getOrder().second().isEmpty()) { //(no products for the cooks to cook)
                    for (int j = 0; j < orderList.size(); j++) {
                        if (orderList.get(j) == guiOrderDetails.getOrderID()) {
                            System.out.println("entra a eliminar posicion: "+ j);
                            orderList.remove(j);
                            guiKitchenMain.removeElementOfOrdersGrid(j);
                            listOrdersPanels.remove(j);
                        }
                    }
                    guiKitchenMain.cleanAllOrders();
                    refreshOpenOrders();
                    guiOrderDetails.closeWindow();
                } else { // Still has products for the cook to cook
                    loadGuiOrderDetails((int) guiOrderPane.getOrder().first().get("id"));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiKitchenMain.validate();
        }
        if (guiLogin != null) {
            if (ae.getSource() == guiLogin.getBtnAccept()) {
                String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
                String split[] = user.split("-");
                int userId = Integer.parseInt(split[0]);
                try {
                    crudPresence.create(userId);
                    guiLogin.dispose();
                    online.add(crudUser.getUser(userId));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ae.getSource() == guiLogin.getBtnCancel()) {
                guiLogin.dispose();
            }
        }

        if (ae.getSource() == guiKitchenMain.getMenuItemNewLogin()) {
            try {
                guiLogin = new GuiLogin(guiKitchenMain, true);
                Set<Map> offline = new HashSet<>();
                offline.addAll(crudUser.getCooks());
                offline.removeAll(online);
                if (offline.isEmpty()) {
                    JOptionPane.showMessageDialog(guiKitchenMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setActionListener(this);
                    guiLogin.setVisible(true);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == guiKitchenMain.getMenuItemLoggedUsers()) {
            GuiOnlineUsers gulu = new GuiOnlineUsers(guiKitchenMain, true);
            gulu.setVisible(true);
        }
    }
}
