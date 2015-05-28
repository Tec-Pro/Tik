/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.login.GuiLogin;
import gui.login.GuiOnlineUsers;
import gui.main.GuiBarMain;
import gui.order.GuiBarOrderDetails;
import gui.order.GuiBarOrderPane;
import interfaces.InterfaceFproduct;
import interfaces.InterfaceGeneralConfig;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
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
public class ControllerGuiBarMain implements ActionListener {

    //Guis
    private GuiLogin guiLogin;
    private static GuiBarOrderDetails guiOrderDetails;
    private static GuiBarMain guiBarMain;
    private static GuiBarOrderPane guiOrderPane;
    //Interfaces
    private static InterfaceOrder crudOrder;
    private static InterfaceUser crudUser;
    private static InterfaceFproduct crudFProduct;
    private final InterfacePresence crudPresence;
    private static InterfaceGeneralConfig generalConfig;
    //Conjunto(set) con los barman online
    private final Set<Map> online;
    //Atributos para el control de retrasos en pedidos
    private static Timer timer;
    private static final Integer time = 10000;//tiempo de intervalo de actualizacion de retrasos(actualmente 10 segundos)
    private static SoundPlayer soundPlayer;
    //lista de ordersPanels con todos los paneles de la gui
    private static LinkedList<GuiBarOrderPane> listOrdersPanels;
    //
    private static LinkedList<Integer> orderList;
    private static DefaultTableModel dtmOrderDetails;
    

    /**
     *
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiBarMain() throws NotBoundException, MalformedURLException, RemoteException {
        soundPlayer = new SoundPlayer();
        orderList = new LinkedList<>();
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudFProduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        generalConfig = (InterfaceGeneralConfig) InterfaceName.registry.lookup(InterfaceName.GeneralConfig);
        online = new HashSet<>();
        listOrdersPanels = new LinkedList<>();
        for (Map m : crudPresence.getCooks()) {
            online.add(m);
        }
        guiOrderDetails = new GuiBarOrderDetails(guiBarMain, true);
        guiOrderDetails.setVisible(false);

        dtmOrderDetails = guiOrderDetails.getDefaultTableModelOrderProducts();
        
        guiOrderPane = new GuiBarOrderPane();
        
        guiBarMain = new GuiBarMain();
        guiBarMain.setVisible(true);
        guiBarMain.setActionListener(this);
        guiOrderDetails.setActionListener(this);
        guiOrderPane.setActionListener(this);

        guiLogin = null;
        
        //traigo todos los pedidos que estan abiertos
        refreshOpenOrders();
        //controlo cada cierto tiempo("time") si hay algun pedido retrasado
        timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    //Cada cierto tiempo "time" hago una busqueda de las ordenes retrasadas
                    searchDelayedOrders();
                } catch (ParseException | RemoteException ex) {
                    Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
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
        Iterator<GuiBarOrderPane> itr = listOrdersPanels.iterator();
        //Recorro todos los paneles de la gridbaglayout
        while (itr.hasNext()) {
            final GuiBarOrderPane orderPane = itr.next();//saco el panel actual
            final Timestamp currentTime = new Timestamp(System.currentTimeMillis());//hora y fecha actual
            Timestamp timeOrderArrival = Timestamp.valueOf(orderPane.getLblTimeOrderArrival().getText());//hora y fecha del pedido
            Map<String, Object> diff = computeDiff(timeOrderArrival, currentTime);//diferencia entre horas
            //Si transcurrieron mas minutos de los especificados por el usuario en la configuracion
            //Y ademas el pedido no esta pospuesto (No esta coloreado en amarillo)
            if (orderPane.getColor() != 2 && (Integer.parseInt(diff.get("MINUTES").toString()) >= Integer.parseInt(generalConfig.getDelayTime())
                    || Integer.parseInt(diff.get("HOURS").toString()) > 0
                    || Integer.parseInt(diff.get("DAYS").toString()) > 0)) {
                soundPlayer.playSound();//Alerta sonora
                if(!orderPane.isActiveTimer()){
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
                System.out.println("El pedido: " + orderPane.getLblOrderNumber().getText() + " esta retrasado.");
                System.out.println("Tiempo de retraso: " + diff.toString());
                System.out.println("");
            }

        }
    }

    private static void loadGuiOrderDetails(int order, String desc, String arrival) throws RemoteException {
        guiOrderDetails.getLabelOrderArrivalTime().setText(arrival);
        guiOrderDetails.getTxtOrderDescription().setText(desc);
        guiOrderDetails.getBtnSendOrderDone().setEnabled(false);
        guiOrderDetails.setOrderID(order);
        dtmOrderDetails.setRowCount(0);
        List<Map> map = crudOrder.getOrderProducts(order); // I obtain all the products
        for (Map<String, Object> m : map) { // For each product
            if ((boolean) m.get("done") == false) { // If the product isn't done
                int prodID = (int) m.get("fproduct_id"); // I obtain the product ID
                Map<String, Object> prod = crudFProduct.getFproduct(prodID); // I obtain the product (importantly, it's name and if it belongs)
                String cook = (String) prod.get("belong");
                if (cook.equals("Bar")) { // If the product is for the Bar
                    Object rowDtm[] = new Object[4]; // New row
                    rowDtm[0] = m.get("id");
                    rowDtm[1] = prod.get("name");
                    rowDtm[2] = m.get("quantity");
                    rowDtm[3] = false;
                    dtmOrderDetails.addRow(rowDtm);
                }
            }
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
        guiOrderDetails.toFront();
        guiOrderDetails.setModal(true);
    }

    private static boolean itBelongsBar(List<Map> orderProducts) throws RemoteException { // There was either no products to make, or they've all been made allready
        boolean itBelongs = false;
        //Map<String, Object> order = crudOrder.getOrder(id);

        for (Map<String, Object> m : orderProducts) { // For each product 
            int prodID = (int) m.get("fproduct_id"); // I obtain the product ID
            Map<String, Object> prod = crudFProduct.getFproduct(prodID); // I obtain the product (importantly, where it belongs)
            String cook = (String) prod.get("belong");
            if (cook.equals("Bar")) { // Does it belong to cocina?
                itBelongs = true;
            }
        }
        return itBelongs;
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
    public static void addBarOrder(final Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        /* "order" es el Map de un pedido con la siguiente estructura:
         * {order_number, id, user_id, closed=boolean, description}*/
        /* "orderProducts" es una lista de Maps, de los productos finales que
         * contiene el pedido "order", cada Map tiene la siguiente estructura:
         * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        //Aca debe cargarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        if (itBelongsBar(order.second())) {
            Timestamp date = Timestamp.valueOf("1990-01-01 01:01:01");//inicio la fecha con un valor minimo
            final String desc;
            String aux = "";
            for (Map m : order.second()) {
                if (!m.get("done").equals(true)) {
                    Map<String, Object> fProduct = crudFProduct.getFproduct(Integer.parseInt(m.get("fproduct_id").toString()));
                    aux = aux + fProduct.get("name") + " x" + m.get("quantity") + "\n";
                }
                if (date.before(Timestamp.valueOf(m.get("updated_at").toString()))) {
                    date = Timestamp.valueOf(m.get("updated_at").toString());
                }
            }
            desc = aux;
            //concateno id de pedido mas el nombre del mozo que lo pidio
            String orderName = order.first().get("order_number").toString() + " - " + (crudUser.getUser(Integer.parseInt((order.first().get("user_id")).toString()))).get("name");
            guiOrderPane = new GuiBarOrderPane();
            guiOrderPane.getTxtOrderDescription().setText(desc);
            guiOrderPane.getLblTimeOrderArrival().setText(date.toString());
            guiOrderPane.getLblOrderNumber().setText(orderName);
            guiBarMain.addElementToOrdersGrid(guiOrderPane,0);
            final String dateAux = date.toString();
            guiOrderPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    loadGuiOrderDetails(Integer.parseInt(order.first().get("order_number").toString()), dateAux, order.first().get("description").toString());
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }});
            orderList.add(Integer.parseInt(order.first().get("id").toString()));
            listOrdersPanels.add(guiOrderPane);
        }
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
        guiBarMain.cleanAllOrders();
        refreshOpenOrders();
    }

    /* Recarga todos los pedidos abiertos, sin realizar aun en cocina en la gui. */
    //PULIR ESTE METODO PARA QUE TRAIGA LOS PEDIDOS COMO MAXIMO DE DOS DIAS, Y NO TODOS
    public static void refreshOpenOrders() throws RemoteException {
        listOrdersPanels = new LinkedList<>();//reinicio la lista de ordersPanels para que se actualice en addOrder
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
                    //si el producto corresponde a Bar y no fue hecho
                    if (fproduct.get("belong").equals("Bar") && orderProduct.get("done").equals(false)) {
                        orderClosed = false; //marco el pedido como abierto
                    }
                }
                if (!orderClosed) {//si el pedido esta abierto (NO CERRADO)
                    addBarOrder(new Pair(order, orderProducts));//agrego el pedido en bar
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
                }
                i++;
            }
            List<Map> updateOrder = new LinkedList<>();
            try {
                updateOrder = crudOrder.updateOrdersReadyProducts(guiOrderDetails.getOrderID(), list);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (list.size() == dtmOrderDetails.getRowCount()) { //si la lista de productos hechos es igual a la cantidad de productos en la tabla
                    guiBarMain.cleanAllOrders();//borro todas las ordenes de la gui
                    refreshOpenOrders(); //Actualizo la gui de pedidos para eliminar el que se realizo
                    guiOrderDetails.closeWindow();
                } else {
                    guiOrderDetails.closeWindow();
                }

            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiBarMain.validate();
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
                    Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ae.getSource() == guiLogin.getBtnCancel()) {
                guiLogin.dispose();
            }
        }

        if (ae.getSource() == guiBarMain.getMenuItemNewLogin()) {
            try {
                guiLogin = new GuiLogin(guiBarMain, true);
                Set<Map> offline = new HashSet<>();
                offline.addAll(crudUser.getCooks());
                offline.removeAll(online);
                if (offline.isEmpty()) {
                    JOptionPane.showMessageDialog(guiBarMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setActionListener(this);
                    guiLogin.setVisible(true);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == guiBarMain.getMenuItemLoggedUsers()) {
            GuiOnlineUsers gulu = new GuiOnlineUsers(guiBarMain, true);
            gulu.setVisible(true);
        }
    }
}
