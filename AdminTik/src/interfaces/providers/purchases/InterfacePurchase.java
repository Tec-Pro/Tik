/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.providers.purchases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import utils.Pair;

/**
 *
 * @author nico
 */
public interface InterfacePurchase extends Remote {

    /**
     *
     * @param cost
     * @param paid
     * @param date
     * @param providerId
     * @param datePaid
     * @param products
     * @return
     * @throws RemoteException
     */
    public Integer create(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products) throws java.rmi.RemoteException;

    /**
     *
     * @param idPurchase
     * @return
     * @throws RemoteException
     */
    public boolean delete(Integer idPurchase) throws java.rmi.RemoteException;

    /**
     *
     * @param idPurchase
     * @return
     * @throws RemoteException
     */
    public Pair<Map<String, Object>, List<Map>> getPurchase(Integer idPurchase) throws java.rmi.RemoteException;

    /**
     *
     * @param idProvider
     * @return
     * @throws RemoteException
     */
    public List<Pair<Map<String, Object>, List<Map>>> getPurchasesProvider(Integer idProvider) throws java.rmi.RemoteException;
}
