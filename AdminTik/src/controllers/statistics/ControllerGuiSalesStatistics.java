/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiSalesStatistics;
import interfaces.InterfaceOrder;
import interfaces.InterfaceUser;
import interfaces.statistics.InterfaceStatistics;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class ControllerGuiSalesStatistics {

    private static InterfaceStatistics interfaceStatistics;
    private static InterfaceOrder interfaceOrder;
    private static InterfaceUser interfaceUser;
    private static GuiSalesStatistics guiSalesStatistics;

    public ControllerGuiSalesStatistics(GuiSalesStatistics guiSS) throws RemoteException, NotBoundException {
        guiSalesStatistics = guiSS;
        interfaceOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        interfaceUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        
        //cargo las tablas de estadisticas y graficos en la gui
        guiSalesStatistics.cleanGuiSalesStatistics();
        loadTableSalesStatisticsWaiter();
    }

    /*
    * Calcula las estadisticas de un turno, cuando se cierra la caja
    */
    public static void calculateAndSaveStatistics() throws RemoteException {
        List<Map> waiters = interfaceUser.getWaiters();//todos los mozos de la base de datos
        float saleAmount = -1;//monto total de las ventas de un mozo
        int tables = 0;//cantidad de mesas atendidas
        int customers = 0;//clientes atendidos por el mozo
        int products = 0;//cantidad de productos vendidos
        float avgTables = -1, avgCustomers = -1, avgProducts = -1, discounts = -1, exceptions = -1;
        int userId;//id del mozo
        String userName;//nombre del mozo
        String turn = "SIN CALCULAR";//turno corriente
        Timestamp day = null;//fecha del dia corriente
        for (Map m : waiters) {
            //saco los pedidos de cada mozo
            userId = Integer.parseInt(m.get("id").toString());
            userName = m.get("name").toString();
            List<Map> ordersByUser = interfaceOrder.getOrdersByUser(userId);
            for (Map order : ordersByUser) {
                customers += Integer.parseInt(order.get("persons").toString());//incremento la cantidad de clientes del pedido actual
                tables++;//incremento en 1 el numero de mesas atendidas
                int orderId = Integer.parseInt(order.get("id").toString());//id del pedido
                List<Map> orderProducts = interfaceOrder.getOrderProducts(orderId); //saco todos los productos asociados al pedido
                products += orderProducts.size();//sumo la cantidad de productos a los vendidos por el mozo
            }
            interfaceStatistics.storeSalesStatistics(userName, userId, saleAmount, tables, customers, products, avgTables, avgCustomers, avgProducts, discounts, exceptions, turn, day);
        }
    }

    //Carga las estadisticas de la ventas de cada mozo en la tabla correspondiente
    private static void loadTableSalesStatisticsWaiter() throws RemoteException {
        //saco todas las estadisticas, pero esto debe reemplazarse por la busqueda entre fechas
        //diarias, mensuales o anuales
        List<Map> allSalesStatistics = interfaceStatistics.getAllSalesStatistics();
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        Object[] row = new Object[12];
        for (Map m : allSalesStatistics) {
            row[0] = m.get("waiter_name"); //nombre del mozo
            row[1] = m.get("day"); //fecha del estadistico
            row[2] = m.get("turn"); //turno en esa fecha
            row[3] = m.get("sale_amount"); //monto total de ventas del mozo
            row[4] = m.get("tables"); //cantidad de mesas atendidas
            row[5] = m.get("customers"); //cantidad de clientes atendidos
            row[6] = m.get("products"); //cantidad de productos vendidos
            row[7] = m.get("average_tables"); //promedio 
            row[8] = m.get("average_customers"); //promedio
            row[9] = m.get("average_products"); //promedio
            row[10] = m.get("discounts"); //descuentos realizados
            row[11] = m.get("exceptions"); //excepciones
            modelTableSalesStatisticsWaiter.addRow(row);
        }
        //cargo el total de las ventas de todos los mozos en la tabla correspondiente
        loadTableTotalSalesStatistics();
    }

    /*
    * Carga las estadisticas del total de ventas de todos los mozos(suma de las ventas de todos los 
    * mozos proveniente de la tabla 'salesStatisticsWaiter' en la tabla correspondiente ('totalSalesStatistics').
    */
    private static void loadTableTotalSalesStatistics() {
        DefaultTableModel modelTableTotalSalesStatistics = guiSalesStatistics.getModelTableTotalSalesStatistics();
        Object[] row = new Object[9];
        row[0] = -1;// monto ventas
        row[1] = -1;// numero de mesas
        row[2] = -1;// cantidad de personas
        row[3] = -1;// cantidad de productos vendidos
        row[4] = -1;// promedio de mesas que atendio el mozo
        row[5] = -1;// promedio a preguntar
        row[6] = -1;// promedio a preguntar
        row[7] = -1;// descuentos
        row[8] = -1;// excepciones
        modelTableTotalSalesStatistics.addRow(row);
    }

}
