/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import interfaces.InterfaceOrder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import utils.Config;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class ControllerGuiKitchenMain  implements ActionListener {

    private static InterfaceOrder crudOrder;
    private static LinkedList<Map> orderList;
    
    /**
     *
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiKitchenMain() throws NotBoundException, MalformedURLException, RemoteException{
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/"+InterfaceName.CRUDOrder);
        orderList = new LinkedList<>();
    }
    
    /**
     * Metodo invocado por el servidor cuando se crea un nuevo Pedido/Orden en el modulo Waiter.
     * Este metodo cargara el pedido creado en la gui correspondiente
     * @param id del pedido
     * @throws RemoteException
     */
    public static void addOrder(int id) throws RemoteException{
        /* "order" es el Map de un pedido con la siguiente estructura:
        * {order_number, id, user_id, closed=boolean, description}*/
        Map<String,Object> order = crudOrder.getOrder(id);
        /* "orderProducts" es una lista de Maps, de los productos finales que
        * contiene el pedido "order", cada Map tiene la siguiente estructura:
        * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        List<Map> orderProducts = crudOrder.getOrderProducts(id);
        //Aca debe cargarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        
        System.out.println("Pedido: "+order.toString()+" "+orderProducts.toString());
    }
    
    /**
     * Metodo invocado por el servidor cuando se actualiza informacion sobre Pedido/Orden en el modulo Waiter.
     * Este metodo actualizara el pedido modificado en la gui correspondiente
     * @param id del pedido
     * @throws RemoteException
     */
    public static void updatedOrder(int id) throws RemoteException{
        /* "order" es el Map de un pedido con la siguiente estructura:
        * {order_number, id, user_id, closed=boolean, description}*/
        Map<String,Object> order = crudOrder.getOrder(id);
        /* "orderProducts" es una lista de Maps, de los productos finales que
        * contiene el pedido "order", cada Map tiene la siguiente estructura:
        * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        List<Map> orderProducts = crudOrder.getOrderProducts(id);
        //Aca debe actualizarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        
        System.out.println("Pedido: "+order.toString()+" "+orderProducts.toString());
    }

    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* PARA ACTUALIZAR LOS PRODUCTOS LISTOS SE DEBE USAR ESTE METODO
    * SI HAY ALGUNA DUDA LEER SU JAVADOC:
    * crudOrder.updateOrdersReadyProducts(id, list)
    */
}
