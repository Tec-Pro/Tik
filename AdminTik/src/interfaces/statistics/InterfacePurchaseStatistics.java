/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.statistics;

import java.rmi.Remote;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eze
 */
public interface InterfacePurchaseStatistics extends Remote {
    
    /**
     * Realiza una busqueda de estadisticas de compras entre dos fechas
     * Las estadisticas se agrupan por producto de forma diaria
     *
     * @param since fecha desde la cual se inicia la busqueda
     * @param until fecha hasta la cual se realiza la busqueda
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> findPurchaseStatisticsBetweenDays(Date since, Date until) throws java.rmi.RemoteException;

    /**
     * Realiza una busqueda de estadisticas de compras entre dos fechas
     * Las estadisticas se agrupan por producto de forma mensual
     * 
     * @param since fecha desde la cual se inicia la busqueda
     * @param until fecha hasta la cual se realiza la busqueda
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> findPurchaseStatisticsBetweenMonths(Date since, Date until) throws java.rmi.RemoteException;

    /**
     * Realiza una busqueda de estadisticas de compras entre dos fechas
     * Las estadisticas se agrupan por producto de forma anual
     *
     * @param since fecha desde la cual se inicia la busqueda
     * @param until fecha hasta la cual se realiza la busqueda
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> findPurchaseStatisticsBetweenYears(Date since, Date until) throws java.rmi.RemoteException;

    /**
     * Realiza una busqueda de estadisticas de compras entre dos fechas
     * Las estadisticas se agrupan por producto de entre las dos fechas ingresadas
     *
     * @param since fecha desde la cual se inicia la busqueda
     * @param until fecha hasta la cual se realiza la busqueda
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> findAllPurchaseStatisticsBetweenDates(Date since, Date until) throws java.rmi.RemoteException;

    
}
