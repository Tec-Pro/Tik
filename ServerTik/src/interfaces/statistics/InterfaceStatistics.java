/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.statistics;

import java.rmi.Remote;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eze
 */
public interface InterfaceStatistics extends Remote {
    
    
    /**
     * Retorna una lista de productos finales con detalles agregados
     * @return List<Map<String, Object>> con:
     * Nombre(name) de fproduct,
     * Precio de Venta(sell_price),
     * Subcategoría(subcategory),
     * Categoría(category),
     * Precio de elaboracion(elaboration_price)
     * @throws java.rmi.RemoteException
     */
    public List<Map<String, Object>> getProductList() throws java.rmi.RemoteException;
    
    /**
     * Crea las estadisticas de ventas de un turno, en la base de datos
     * @param waiterName nombre del mozo
     * @param userId id del mozo
     * @param saleAmount monto total de las ventas del mozo
     * @param tables cantidad de mesas atendidas por el mozo
     * @param customers cantidad de clientes atendidos por el mozo
     * @param products cantidad de productos vendidos por el mozo
     * @param avgTables promedio de mesas atendidas por el mozo
     * @param avgCustomers promedio
     * @param avgProducts promedio
     * @param discounts descuentos realizados
     * @param exceptions excepciones
     * @param turn turno del dia correspondiente a este estadistico
     * @param day fecha
     * @return 
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> saveSalesStatistics(String waiterName, int userId, Double saleAmount, int tables, int customers, int products, Double avgTables, Double avgCustomers, Double avgProducts, Double discounts, Double exceptions, String turn, Timestamp day) throws java.rmi.RemoteException;
    
    /**
     * Retorna una lista de estadisticas de ventas de todos los mozos, en todos los turnos
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getAllSalesStatistics() throws java.rmi.RemoteException;
    
    /**
     * Retorna una lista de estadisticas de ventas de un mozo, en todos los turnos
     * @param userId id del mozo
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getSalesStatisticsFromAWaiter(int userId) throws java.rmi.RemoteException;
    
    /**
     * Retorna una lista de estadisticas de ventas de todos los productos, en todos los turnos
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getAllProductStatistics() throws java.rmi.RemoteException;
    
    /**
     * Calcula automaticamente y Crea las estadisticas de ventas de productos del turno actual, en la base de datos
     * Retorna una Lista de productos con sus cantidades vendidas, turno y fecha
     * @return 
     * @throws java.rmi.RemoteException
     */
    public List<Map> saveStatisticsCurrentProductShift() throws java.rmi.RemoteException;
    
    /**
     * Realiza una busqueda de estadisticas de productos entre dos fechas dadas
     * @param since fecha desde la cual se inicia la busqueda
     * @param until fecha hasta la cual se realiza la busqueda
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> findProductStatisticsBetweenDates(Timestamp since, Timestamp until) throws java.rmi.RemoteException;
    
}
