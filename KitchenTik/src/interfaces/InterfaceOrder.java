/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;


import java.rmi.Remote;
import java.util.List;
import java.util.Map;
/**
 *
 * @author agustin
 */
public interface InterfaceOrder extends Remote{
    
    
    /**
     * Crea un nuevo pedido y lo envia
     * @param userId
     * @param description
     * @param fproducts
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> sendOrder(int userId, String description,List<Map<String,Object>> fproducts) throws java.rmi.RemoteException;
     
    public boolean addProducts(int orderId,List<Map<String,Object>> fproducts) throws java.rmi.RemoteException;
    
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
    
    public List<Map> getOrderProducts(int orderId) throws java.rmi.RemoteException;
    
}