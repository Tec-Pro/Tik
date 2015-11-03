/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.providers.purchases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import utils.Pair;

/**
 *
 * @author nico
 */
public interface InterfacePurchase extends Remote {

    /**
     * crea una nueva compra
     *
     * @param cost
     * @param paid
     * @param date
     * @param providerId
     * @param datePaid
     * @param products
     * @param ivaProducts
     * @return
     * @throws RemoteException
     */
    public Integer create(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products,HashMap<Integer,Float> ivaProducts) throws java.rmi.RemoteException;

    /**
     * borra una compra
     *
     * @param idPurchase
     * @return
     * @throws RemoteException
     */
    public boolean delete(Integer idPurchase) throws java.rmi.RemoteException;

    /**
     * retorna la compra con el id idPurchase
     *
     * @param idPurchase
     * @return
     * @throws RemoteException
     */
    public Pair<Map<String, Object>, List<Map>> getPurchase(Integer idPurchase) throws java.rmi.RemoteException;

    /**
     * obtiene las compras de un proveedor
     *
     * @param idProvider
     * @return
     * @throws RemoteException
     */
    public List<Pair<SortedMap<String, Object>, List<Map>>> getPurchasesProvider(Integer idProvider) throws java.rmi.RemoteException;

    /**
     * obtiene las compras de un proveedor dada dos fechas
     *
     * @param idProvider
     * @param from
     * @param until
     * @return
     * @throws RemoteException
     */
    public List<Pair<SortedMap<String, Object>, List<Map>>> getProviderPurchasesBetweenDates(Integer idProvider, String from, String until) throws java.rmi.RemoteException;
    
    
    /**
     * modifica una compra, restaurando el stock, la estadisticas 
     * @param cost
     * @param paid
     * @param date
     * @param providerId
     * @param datePaid
     * @param products
     * @param idPurchase
     * @param ivaProducts
     * @return
     * @throws java.rmi.RemoteException 
     */
    public Integer modify(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products, Integer idPurchase,HashMap<Integer,Float> ivaProducts) throws java.rmi.RemoteException;

}
