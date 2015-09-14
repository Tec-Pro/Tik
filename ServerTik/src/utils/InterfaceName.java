/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.rmi.registry.Registry;

/**
 * Nombres de interfaces que provienen desde el server
 *
 * @author nico
 */
public class InterfaceName {

    public static final String CRUDAdmin = "CRUDAdmin";
    public static final String CRUDPproduct = "CRUDPproduct";
    public static final String CRUDEproduct = "CRUDEproduct";
    public static final String CRUDFproduct = "CRUDFproduct";
    public static final String CRUDCategory = "CRUDCategory";
    public static final String CRUDPurchase = "CRUDPurchase";
    public static final String CRUDUser = "CRUDUser";
    public static final String CRUDTurn = "CRUDTurn";
    public static final String CRUDProvider = "CRUDProvider";
    public static final String CRUDProviderCategory = "CRUDProviderCategory";
    public static final String providersSearch = "providersSearch";
    public static final String CRUDPresence = "CRUDPresence";
    public static final String CRUDOrder = "CRUDOrder";
    public static final String server = "Server";
    public static final String CRUDWithdrawal = "CRUDWithdrawal";
    public static final String CRUDDeposit = "CRUDDeposit";
    public static Registry registry;
    public static final String GeneralConfig = "GeneralConfig";
    public static final String CRUDStatistics = "CRUDStatistics";
    public static final String CRUDPurchaseStatistics = "CRUDPurchaseStatistics";
    public static final String CRUDpayments = "CRUDPayments";
    public static final String CRUDExpenses = "CRUDExpenses";
    public static final String CRUDCashbox = "CRUDCashbox";
    public static final String CRUDDiscounts = "CRUDDiscounts";
    public static final String CRUDResume = "CRUDResume";
}
