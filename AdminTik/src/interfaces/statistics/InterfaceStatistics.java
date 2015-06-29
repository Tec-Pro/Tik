/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.statistics;

import java.rmi.Remote;
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
    
}
