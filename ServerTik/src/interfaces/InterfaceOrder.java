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
import utils.Pair;

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
     * @param all
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getActiveOrdersByUser(int userId) throws java.rmi.RemoteException;
    
    /**
     * 
     * @return Par de listas :
     *      pair.first = lista de Maps pedidos, con el formato: 
     *                      {id, order_number, user_id, description, closed, persons, user_name}
     *      pair.second = lista de Maps productos de los pedidos, con el formato:
     *                      {id, order_id, fproduct_id, quantity, done=boolean,
     *                          created_at = string(timestamp), updated_at = String(timestamp), name}  
     * @throws RemoteException
     */
    public Pair<List<Map>, List<Map>> getAllOrdersForBar() throws java.rmi.RemoteException;
    
    /**
     *
     * @return Par de listas :
     *      pair.first = lista de Maps pedidos, con el formato: 
     *                      {id, order_number, user_id, description, closed, persons, user_name}
     *      pair.second = lista de Maps productos de los pedidos, con el formato:
     *                      {id, order_id, fproduct_id, quantity, done=boolean,
     *                          created_at = string(timestamp), updated_at = String(timestamp), name} 
     * @throws RemoteException
     */
    public Pair<List<Map>, List<Map>> getAllOrdersForKitchen() throws java.rmi.RemoteException;
    
    public  List<Map> getOrderProductsWithName(int orderId) throws RemoteException;
    
     /**
     * Devuelve la recaudacion total
     */
    public float totalEarn() throws RemoteException;
    
     /**
     * Devuelve la recaudacion total de un mozo
     */
    public float EarnByUser(int userId) throws RemoteException;
    
    /**
     * Agrega una exepcion a un pedido, se suma siempre
     * @param orderId
     * @param amount
     * @return
     * @throws RemoteException 
     */
    public boolean addException(int orderId, float amount) throws RemoteException;
    
    /**
     * retorna el saldo con las excepciones de una orden que NO están pagas
     * @param orderId
     * @return
     * @throws RemoteException 
     */
    public float getException(int orderId)throws RemoteException;
    
        /**
     * retorna el saldo de las excepciones de una orden,  PAGADAS
     * @param orderId
     * @return
     * @throws RemoteException 
     */
    public float getPaidException(int orderId)throws RemoteException;
    
    /**
     * retorna el saldo total de todas las exepciones de un mozo
     * @param userId
     * @return
     * @throws RemoteException 
     */
    public float getExceptions(int userId)throws RemoteException;
    
    /**
     * retorna el saldo total de todas las exepciones
     * 
     * @return
     * @throws RemoteException 
     */
    public float getAllExceptions()throws RemoteException;
    
    /**
     * elimina todas las ordenes
     * @throws RemoteException 
     */
    public void deleteAll() throws RemoteException;

    /**
     * Retorna la data para imprimir un pago de una orden
     * @throws RemoteException 
     */
    public List<Map> getDataPrinterOrd(int id) throws RemoteException;
    
    /**
     * BORRA UN PRODUCTO DE UNA ORDEN
     * @param id
     * @throws RemoteException 
     */
   public void deleteProduct(int id_prod_order, int fprod, float quantity, int order_id) throws RemoteException;
    
    /**
     * DESCUENTA UN PRODUCTO, o lo elimina del descuento
     * @param id
     * @param isDiscount
     * @throws RemoteException 
     */
    public void discountProduct(int id, int isDiscount, int fproduct_id, int user_id, int order_id) throws RemoteException ;

    
    /**
     * retorna los articulos que descontó un mozo entre 2 fechas, si el id es -1 retorna el de todos los mozos
     * @param user_id
     * @param dateFrom
     * @param dateTo
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Map> getDiscounts(int user_id, String dateFrom, String dateTo) throws java.rmi.RemoteException;

    /**
     * obtiene los descuentos en productos de un mozo de ese mismo turno
     * @param user_id
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Map> getCurrentDiscounts(int user_id) throws java.rmi.RemoteException;
    
    
    /**
     * obtiene los descuentos en efectivos de un mozo en ese mismo turno
     * @param user_id
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Map> getCurrentDiscountsInEfective(int user_id) throws java.rmi.RemoteException;
    
    /**
     * setea el el descuento en una orden, pisa la info si hay un valor
     * @param order_id
     * @param amount
     * @throws java.rmi.RemoteException 
     */
    public void setDiscountEfec(int order_id, float amount) throws java.rmi.RemoteException;
    
    /**
     * obtiene el descuento de un pedido
     * @param order_id
     * @return
     * @throws java.rmi.RemoteException 
     */
    public float getDiscountEfec(int order_id) throws java.rmi.RemoteException;

     /**
     * Retorna el total de descuentos en efectivo mas el total de descuentos en productos
     * del TURNO CORRIENTE (se obtiene de la tabla de pedidos)
     * @return
     * @throws RemoteException 
     */
    public float getTotalDiscounts() throws java.rmi.RemoteException;
    
}
