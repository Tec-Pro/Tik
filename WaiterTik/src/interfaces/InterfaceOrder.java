/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author agustin
 */
public interface InterfaceOrder extends Remote {

    /**
     * Crea un nuevo pedido y lo envia
     *
     * @param userId
     * @param description
     * @param persons
     * @param fproducts
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> sendOrder(int userId, String description, int persons, List<Map<String, Object>> fproducts) throws java.rmi.RemoteException;

    /**
     *
     * @param orderId
     * @param description
     * @param persons
     * @param fproducts
     * @return
     * @throws RemoteException
     */
    public boolean updateOrder(int orderId, String description, int persons, List<Map<String, Object>> fproducts) throws java.rmi.RemoteException;

    /**
     *
     * @param idOrder
     * @return
     * @throws RemoteException
     */
    public boolean closeOrder(int idOrder) throws java.rmi.RemoteException;

    /**
     *
     * @return Todos los pedidos
     * @throws java.rmi.RemoteException
     */
    public List<Map> getAllOrders() throws java.rmi.RemoteException;

    /**
     *
     * @param userId
     * @return Todos los pedidos que pertenecen a un usuario dado
     * @throws java.rmi.RemoteException
     */
    public List<Map> getOrdersByUser(int userId) throws java.rmi.RemoteException;

    /**
     * Dado el id de un pedido, retorna una lista de maps de los productos
     * contenidos en dicho pedido.
     *
     * @param orderId ID del pedido
     * @return Lista de Maps de los productos contenidos en dicho pedido
     * @throws RemoteException
     */
    public List<Map> getOrderProducts(int orderId) throws java.rmi.RemoteException;

    /**
     * Dado el id de un pedido, retorna el un Map del mismo
     *
     * @param orderId ID del Pedido
     * @return Map del pedido en cuestion
     * @throws RemoteException
     */
    public Map<String, Object> getOrder(int orderId) throws java.rmi.RemoteException;

    /**
     *
     * @param orderId
     * @throws RemoteException
     */
    public void commitProducts(int orderId) throws java.rmi.RemoteException;

    /**
     * Dada una lista de ID de productos a realizarse en un pedido, este metodo
     * los marca como realizados (done = true).
     *
     * @param idOrder id del Pedido
     * @param productsList lista de Integers ID de productos a marcar como
     * realizados
     * @return lista de Maps de los los productos marcados como realizados
     * @throws java.rmi.RemoteException
     */
    public List<Map> updateOrdersReadyProducts(Integer idOrder, List<Integer> productsList) throws java.rmi.RemoteException;

    /**
     * retorna las ordenes activas de un id, si es -1 retorna todas
     *
     * @param userId
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getActiveOrdersByUser(int userId) throws java.rmi.RemoteException;
}
