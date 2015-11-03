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
import interfaces.InterfaceGeneralConfig;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceServer;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
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
    private static GuiKitchenMain guiKitchenMain;
    private static GuiKitchenOrderPane guiOrderPane;
    //Interfaces
    private static InterfaceGeneralConfig generalConfig;
    private static InterfaceOrder crudOrder;
    private static InterfaceUser crudUser;
    private final InterfacePresence crudPresence;
    private static InterfaceServer server;
    //Conjunto(set) con los cocineros online
    private final Set<Map> online;
    //Atributos para el control de retrasos en pedidos
    private static Timer timer;
    private static final Integer time = 10000;//tiempo de intervalo de actualizacion de retrasos(actualmente 10 segundos)
    private static SoundPlayer soundPlayer;
    //lista de ordersPanels con todos los paneles de la gui
    private static LinkedList<GuiKitchenOrderPane> listOrdersPanels;
    private InterfaceTurn crudTurn;
    private static LinkedList<Integer> orderList;

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
        generalConfig = (InterfaceGeneralConfig) InterfaceName.registry.lookup(InterfaceName.GeneralConfig);
        server = (InterfaceServer) InterfaceName.registry.lookup(InterfaceName.server);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        online = new HashSet<>();
        for (Map m : crudPresence.getCooks()) {
            online.add(m);
        }
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
        }
        guiKitchenMain = new GuiKitchenMain();
        guiOrderPane = new GuiKitchenOrderPane();
        guiKitchenMain.setVisible(true);
        guiKitchenMain.setActionListener(this);
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
        });
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
                //soundPlayer.playSound();//Alerta sonora, descomentar para activar
                if (!orderPane.isActiveTimer()) {
                    //Parpadea el color del panel, en rojo, avisando que el pedido se retraso
                    orderPane.activateFlashing();
                }
                orderPane.getBtnPostpone().setEnabled(true);
                orderPane.getBtnPostpone().addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        orderPane.setColor(2);//Coloreo en amarillo el pedido, en señal de pospuesto
                        soundPlayer.stopSound();
                        orderPane.getBtnPostpone().setEnabled(false);
                        orderPane.stopTimer();
                        try {
                            //aviso a los mozos que esta demorado
                            server.notifyWaitersOrderDelayed(orderPane.getOrderId());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                System.out.println("El pedido: " + orderPane.getLblOrderNumber().getText() + " esta retrasado.");
                System.out.println("Tiempo de retraso: " + diff.toString());
                System.out.println("");
            }

        }
    }

    /**
     * Metodo invocado por el servidor cuando se crea un nuevo Pedido/Orden en
     * el modulo Waiter. Este metodo cargara el pedido creado en la gui
     * correspondiente
     *
     * @param order Map con la estructura : order.first : {id, order_number,
     * user_id, description, closed, persons, user_name} order.second : {id,
     * order_id, fproduct_id, quantity, done=boolean, created_at =
     * string(timestamp), updated_at = String(timestamp), name}
     * @throws RemoteException
     */
    public static void addOrder(final Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        //calculo la hora y descripcion del pedido
        final String desc = calculateDescription(order.second());
        final Timestamp date = calculateTimeOfOrder(order.second());
        //concateno id de pedido mas el nombre del mozo que lo pidio
        String orderName = order.first().get("order_number").toString() + " - " + order.first().get("user_name").toString();
        guiOrderPane = new GuiKitchenOrderPane(order.first(), orderName, desc, date.toString(), order.second());
        //Si hago doble click en el panel de un pedido
        guiOrderPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        GuiKitchenOrderDetails guiOrderDetails = new GuiKitchenOrderDetails(guiKitchenMain, true);
                        ControllerGuiOrderDetails controllerGuiOrderDetails = new ControllerGuiOrderDetails(guiOrderDetails, order);
                        guiOrderDetails.setVisible(true);
                        guiOrderDetails.toFront();
                        guiOrderDetails.setModal(true);
                        //Si el controlador me dice que debo borrar el orderPane
                        if (controllerGuiOrderDetails.removeThisPane() != null && controllerGuiOrderDetails.removeThisPane()) {
                            //elimino el panel (GuiKitchenOrderPane) de la gui principal
                            removeGuiOrderPane(controllerGuiOrderDetails.getOrderId());
                        }
                    } catch (RemoteException | NotBoundException ex) {
                        Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        //Si hago click en el boton de "Pedido Listo" de un panel
        guiOrderPane.getBtnOrderReady().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final Integer orderId = Integer.parseInt(order.first().get("id").toString());
                List<Integer> listOrderProductsId = new LinkedList<>();
                for (Map<String, Object> m : order.second()) {
                    listOrderProductsId.add(Integer.parseInt(m.get("id").toString()));
                }
                try {
                    crudOrder.updateOrdersReadyProducts(orderId, listOrderProductsId);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                //elimino el panel (GuiKitchenOrderPane) de la gui principal
                removeGuiOrderPane(orderId);
            }
        });
        guiOrderPane.setColor(0);//seteo el color del panel en blanco
        guiKitchenMain.addElementToOrdersGrid(guiOrderPane);
        orderList.add(Integer.parseInt(order.first().get("id").toString()));
        listOrdersPanels.add(guiOrderPane);
    }

    private static String calculateDescription(List<Map> listOrderProducts) {
        String auxDesc = "";
        for (Map m : listOrderProducts) {
            //calculo la descripcion
            auxDesc = auxDesc + m.get("name") + " x" + m.get("quantity") + "\n";
        }
        return auxDesc;
    }

    private static Timestamp calculateTimeOfOrder(List<Map> listOrderProducts) {
        Timestamp date = Timestamp.valueOf("1990-01-01 01:01:01");//inicio la fecha con un valor minimo
        for (Map m : listOrderProducts) {
            //calculo la hora del pedido en base al ultimo producto añadido al mismo
            if (date.before(Timestamp.valueOf(m.get("updated_at").toString()))) {
                date = Timestamp.valueOf(m.get("updated_at").toString());
            }
        }
        return date;
    }

    private static void removeListeners(GuiKitchenOrderPane orderPane) {
        MouseListener[] mouseListeners = orderPane.getMouseListeners();
        if (mouseListeners != null && mouseListeners.length != 0) {
            for (MouseListener mL : mouseListeners) {
                orderPane.removeMouseListener(mL);
            }
        }
    }

    private static void removeGuiOrderPane(int orderId) {
        boolean stop = false;
        int index = 0;
        while (!stop && index < guiKitchenMain.getOrdersPanel().getComponentCount()) {
            GuiKitchenOrderPane guiOP = (GuiKitchenOrderPane) guiKitchenMain.getOrdersPanel().getComponent(index);
            if (Objects.equals(guiOP.getOrderId(), orderId)) {
                stop = true;
                guiKitchenMain.removeElementOfOrdersGrid(index);
                listOrdersPanels.remove(guiOP);
            }
            index++;

        }
        guiKitchenMain.validate();
        guiKitchenMain.repaint();
    }

    /**
     * Metodo invocado por el servidor cuando se actualiza informacion sobre
     * Pedido/Orden en el modulo Waiter. Este metodo actualizara el pedido
     * modificado en la
     *
     *
     * @param order Map con la estructura : order.first : {id, order_number,
     * user_id, description, closed, persons, user_name} order.second : {id,
     * order_id, fproduct_id, quantity, done=boolean, created_at =
     * string(timestamp), updated_at = String(timestamp), name}
     * @throws RemoteException
     */
    public static void updatedOrder(final Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        int componentCount = guiKitchenMain.getOrdersPanel().getComponentCount();
        int i = 0;
        boolean stop = false;
        //Si no encuentra el panel en la gridbag, quiere decir que no esta por lo tanto debo agregarlo
        while (i < componentCount && !stop) {
            final GuiKitchenOrderPane orderPane = (GuiKitchenOrderPane) guiKitchenMain.getOrdersPanel().getComponent(i);
            if (orderPane.getOrderId().equals(Integer.parseInt(order.first().get("id").toString()))) {
                soundPlayer.stopSound();
                orderPane.stopTimer();
                //elimino los listener del orderPane
                removeListeners(orderPane);
                //calculo la hora y descripcion del pedido
                final String desc = calculateDescription(order.second());
                final Timestamp date = calculateTimeOfOrder(order.second());
                //concateno id de pedido mas el nombre del mozo que lo pidio
                String orderName = order.first().get("order_number").toString() + " - " + order.first().get("user_name").toString();
                final String dateAux = date.toString();
                //Seteo color, numero de orden+nomnre, descripcion, pedido, lista orderProducts, tiempo del pedido
                orderPane.setColor(1);//seteo color para indicar que tiene prod nuevos
                orderPane.setLblOrderNumber(orderName);
                orderPane.setTxtOrderDescription(desc);
                orderPane.setOrder(order.first());
                orderPane.setOrderProducts(order.second());
                orderPane.setLblTimeOrderArrival(dateAux);
                //seteo nuevos listeners en el orderPane
                //Si hago doble click en el panel de un pedido
                orderPane.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            try {
                                orderPane.setColor(0);//si ve los pedidos en la gui de detalles vuelvo el color del panel a Blanco
                                GuiKitchenOrderDetails guiOrderDetails = new GuiKitchenOrderDetails(guiKitchenMain, true);
                                ControllerGuiOrderDetails controllerGuiOrderDetails = new ControllerGuiOrderDetails(guiOrderDetails, order);
                                guiOrderDetails.setVisible(true);
                                guiOrderDetails.toFront();
                                guiOrderDetails.setModal(true);
                                //Si el controlador me dice que debo borrar el orderPane
                                if (controllerGuiOrderDetails.removeThisPane() != null && controllerGuiOrderDetails.removeThisPane()) {
                                    //elimino el panel (GuiKitchenOrderPane) de la gui principal
                                    removeGuiOrderPane(controllerGuiOrderDetails.getOrderId());
                                }
                            } catch (RemoteException | NotBoundException ex) {
                                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                //Si hago click en el boton de "Pedido Listo" de un panel
                guiOrderPane.getBtnOrderReady().addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        final Integer orderId = Integer.parseInt(order.first().get("id").toString());
                        List<Integer> listOrderProductsId = new LinkedList<>();
                        for (Map<String, Object> m : order.second()) {
                            listOrderProductsId.add(Integer.parseInt(m.get("id").toString()));
                        }
                        try {
                            crudOrder.updateOrdersReadyProducts(orderId, listOrderProductsId);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //elimino el panel (GuiKitchenOrderPane) de la gui principal
                        removeGuiOrderPane(orderId);
                    }
                });
                orderPane.revalidate();
                orderPane.repaint();
                stop = true;
            }
            i++;
        }
        //Si el panel no fue encontrado
        if (!stop) {
            addOrder(order);//creo el pedido nuevo
        }

    }

    /* Recarga todos los pedidos abiertos, sin realizar aun en cocina en la gui. */
    //PULIR ESTE METODO PARA QUE TRAIGA LOS PEDIDOS COMO MAXIMO DE DOS DIAS, Y NO TODOS
    public static void refreshOpenOrders() throws RemoteException {
        listOrdersPanels = new LinkedList<>();//reinicio la lista de ordersPanels para que se actualice en addOrder
        Pair<List<Map>, List<Map>> allOrders = crudOrder.getAllOrdersForKitchen();//saco todas los pedidos y productos cargados para la cocina
        for (Map<String, Object> order : allOrders.first()) { //Para cada pedido
            if (order.get("closed").equals(false)) {//si el pedido no esta cerrado
                //saco todos los productos asociados al pedido
                List<Map<String, Object>> listOrderProducts = new LinkedList<>();
                for (Map<String, Object> orderProducts : allOrders.second()) {
                    if (orderProducts.get("order_id").equals(order.get("id"))) {//si el producto corresponde al pedido, lo pongo en su lista
                        listOrderProducts.add(orderProducts);
                    }
                }
                if (!listOrderProducts.isEmpty()) {//si el pedido tiene al menos un producto en la cocina
                    addOrder(new Pair(order, listOrderProducts));//agrego el pedido en kitchen
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
                if (crudTurn.isTurnOpen()) {
                    guiLogin = new GuiLogin(guiKitchenMain, true);
                    Set<Map> offline = new HashSet<>();
                    offline.addAll(crudUser.getCooks());
                    offline.addAll(crudUser.getWaiters());
                    offline.removeAll(online);
                    if (offline.isEmpty()) {
                        JOptionPane.showMessageDialog(guiKitchenMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        guiLogin.loadCBoxUsers(offline);
                        guiLogin.setActionListener(this);
                        guiLogin.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(guiKitchenMain, "Ocurrió un error, no hay ningun turno abierto", "Error!", JOptionPane.ERROR_MESSAGE);
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
