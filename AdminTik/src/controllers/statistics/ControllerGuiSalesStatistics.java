/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiSalesStatistics;
import interfaces.InterfaceFproduct;
import interfaces.InterfaceOrder;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import interfaces.statistics.InterfaceStatistics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class ControllerGuiSalesStatistics implements ActionListener {

    private static InterfaceStatistics interfaceStatistics;
    private static InterfaceOrder interfaceOrder;
    private static InterfaceUser interfaceUser;
    private static InterfaceFproduct interfaceFProduct;
    private static InterfaceTurn interfaceTurn;
    private static GuiSalesStatistics guiSalesStatistics;

    public ControllerGuiSalesStatistics(GuiSalesStatistics guiSS) throws RemoteException, NotBoundException {
        guiSalesStatistics = guiSS;
        interfaceOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        interfaceUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        interfaceFProduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        interfaceTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        guiSalesStatistics.setActionListener(this);
        guiSalesStatistics.cleanComponents();
        //si cambia la fecha de busqueda "Desde"
        guiSalesStatistics.getDateSince().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                obtainAndLoadSalesStatistics();
            }
        });
        //Si cambia la fecha de busqueda "Hasta"
        guiSalesStatistics.getDateUntil().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                obtainAndLoadSalesStatistics();
            }
        });
    }

    /*
     * Calcula las estadisticas de un turno, cuando se cierra la caja
     */
    public static void calculateAndSaveStatistics() throws RemoteException {
        //Inicializo todas las variables
        float saleAmount = (float) 0.00;//monto total de las ventas de un mozo
        int tables = 0;//cantidad de mesas atendidas
        int customers = 0;//clientes atendidos por el mozo
        int products = 0;//cantidad de productos vendidos
        float avgTables = (float) -1.00, avgCustomers = (float) -1.00, avgProducts = (float) -1.00, discounts = (float) -1.00, exceptions = (float) -1.00;
        int userId;//id del mozo
        String userName;//nombre del mozo
        String turn = interfaceTurn.getTurn();//turno corriente
        Date day = null;//fecha del dia corriente

        //Saco todos los mozos de la base de datos
        List<Map> waiters = interfaceUser.getWaiters();//todos los mozos de la base de datos
        for (Map m : waiters) {
            userId = Integer.parseInt(m.get("id").toString());//id de mozo actual
            userName = m.get("name").toString();//nombre del mozo actual
            exceptions = interfaceOrder.getExceptions(userId);
            List<Map> ordersByUser = interfaceOrder.getOrdersByUser(userId);//los pedidos del mozo actual
            //recorro los pedidos del mozo actual
            for (Map order : ordersByUser) {
                customers += Integer.parseInt(order.get("persons").toString());//incremento la cantidad de clientes atendidos por el mozo
                tables++;//incremento en 1 el numero de mesas atendidas
                int orderId = Integer.parseInt(order.get("id").toString());//id del pedido
                List<Map> orderProducts = interfaceOrder.getOrderProducts(orderId); //saco todos los productos asociados al pedido
                products += orderProducts.size();//sumo la cantidad de productos a los vendidos por el mozo
                //recorro cada producto del pedido
                for (Map product : orderProducts) {
                    //saco la fecha 
                    if (day == null) {
                        Timestamp timestamp = Timestamp.valueOf((product.get("created_at").toString()));
                        day = new Date(timestamp.getTime());
                    }
                    //saco el producto final del orderProduct actual
                    Map<String, Object> fproduct = interfaceFProduct.getFproduct(Integer.parseInt(product.get("fproduct_id").toString()));
                    //saco el precio de venta de ese producto
                    float price = Float.parseFloat(fproduct.get("sell_price").toString());
                    //incremento el monto total de ventas del mozo
                    saleAmount += price;
                }
            }
//            interfaceStatistics.saveSalesStatistics(userName, userId, saleAmount, tables, customers, products, avgTables, avgCustomers, avgProducts, discounts, exceptions, turn, day);
            //Reinicio todas las variables
            saleAmount = (float) 0.00;//monto total de las ventas de un mozo
            tables = 0;//cantidad de mesas atendidas
            customers = 0;//clientes atendidos por el mozo
            products = 0;//cantidad de productos vendidos
            avgTables = (float) -1.00;
            avgCustomers = (float) -1.00;
            avgProducts = (float) -1.00;
            discounts = (float) -1.00;
            exceptions = (float) -1.00;
            userId = -1;//id del mozo
            userName = null;//nombre del mozo

        }
    }

    //Carga las estadisticas de la ventas de cada mozo en la tabla correspondiente
    private static void loadTableDailyStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        Object[] row = new Object[12];
        for (Map m : productStatistics) {
            row[0] = m.get("waiter_name"); //nombre del mozo
            row[1] = "Día: " + m.get("day"); //fecha del estadistico
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
    }

    //Carga las estadisticas de la ventas de cada mozo en la tabla correspondiente
    private static void loadTableMonthlyStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        Object[] row = new Object[12];
        for (Map m : productStatistics) {
            row[0] = m.get("waiter_name"); //nombre del mozo
            Date date = Date.valueOf(m.get("day").toString());
            row[1] = "Mes: " + (date.getMonth() + 1) + " Año: " + (date.getYear() + 1900); //fecha del estadistico
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
    }

    //Carga las estadisticas de la ventas de cada mozo en la tabla correspondiente
    private static void loadTableAnnualStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        Object[] row = new Object[12];
        for (Map m : productStatistics) {
            row[0] = m.get("waiter_name"); //nombre del mozo
            Date date = Date.valueOf(m.get("day").toString());
            row[1] = "Año: " + (date.getYear() + 1900);
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
    }

    //Carga las estadisticas de la ventas de cada mozo en la tabla correspondiente
    private static void loadTableAllStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        Object[] row = new Object[12];
        for (Map m : productStatistics) {
            row[0] = m.get("waiter_name"); //nombre del mozo
            row[1] = "Entre: " + (new Date(guiSalesStatistics.getDateSince().getDate().getTime())).toString()
                    +" - "+(new Date(guiSalesStatistics.getDateUntil().getDate().getTime())).toString();
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
    }

    /*
     * Carga las estadisticas del total de ventas de todos los mozos(suma de las ventas de todos los 
     * mozos proveniente de la tabla 'salesStatisticsWaiter' en la tabla correspondiente ('totalSalesStatistics').
     */
    private static void loadTableTotalSalesStatistics() {
        //calculo los estadisticos totales
        DefaultTableModel modelTableSalesStatisticsWaiter = guiSalesStatistics.getModelTableSalesStatisticsWaiter();
        float saleAmount = (float) 0.00;//monto total de las ventas de un mozo
        int tables = 0;//cantidad de mesas atendidas
        int customers = 0;//clientes atendidos por el mozo
        int products = 0;//cantidad de productos vendidos
        float avgTables = (float) 0.00, avgCustomers = (float) 0.00, avgProducts = (float) 0.00, discounts = (float) 0.00, exceptions = (float) 0.00;
        for (int i = 0; i < guiSalesStatistics.getTableSalesStatisticsWaiter().getRowCount(); i++) {
            saleAmount += Float.parseFloat(modelTableSalesStatisticsWaiter.getValueAt(i, 3).toString());//sumo todas las ventas totales de los mozos
            tables += Integer.parseInt(modelTableSalesStatisticsWaiter.getValueAt(i, 4).toString());//sumo todas las mesas atendidas por todos los mozos
            customers += Integer.parseInt(modelTableSalesStatisticsWaiter.getValueAt(i, 5).toString());//sumo todos los clientes atendidos por todos los mozos
            products += Integer.parseInt(modelTableSalesStatisticsWaiter.getValueAt(i, 6).toString());//sumo todos los productos vendidos por todos los mozos
            discounts += Float.parseFloat(modelTableSalesStatisticsWaiter.getValueAt(i, 10).toString());//sumo todos los descuentos
            exceptions += Float.parseFloat(modelTableSalesStatisticsWaiter.getValueAt(i, 11).toString());//sumo todas las excepciones
        }

        DefaultTableModel modelTableTotalSalesStatistics = guiSalesStatistics.getModelTableTotalSalesStatistics();
        Object[] row = new Object[9];
        row[0] = saleAmount;// monto ventas(suma total de todos los mozos)
        row[1] = tables;// numero de mesas
        row[2] = customers;// cantidad de clientes atendidos por todos los mozos
        row[3] = products;// cantidad de productos vendidos por todos los mozos
        row[4] = -1;// promedio de mesas que atendio el mozo
        row[5] = -1;// promedio a preguntar
        row[6] = -1;// promedio a preguntar
        row[7] = discounts;// descuentos
        row[8] = exceptions;// excepciones
        modelTableTotalSalesStatistics.addRow(row);
    }

    private void obtainAndLoadSalesStatistics(){
        //limpio la tabla de resultados de la busqueda
                guiSalesStatistics.getModelTableSalesStatisticsWaiter().setRowCount(0);
                guiSalesStatistics.getModelTableTotalSalesStatistics().setRowCount(0);
            //si la fecha "desde" y "hasta" no estan vacias
            if (guiSalesStatistics.getDateSince().getDate() != null && guiSalesStatistics.getDateUntil().getDate() != null) {
                //saco ambas fechas del datechooser
                java.sql.Date since = new Date(guiSalesStatistics.getDateSince().getDate().getTime());
                java.sql.Date until = new Date(guiSalesStatistics.getDateUntil().getDate().getTime());
                //si se presiono el checkbox "diario"
                if (guiSalesStatistics.getCheckDaily().isSelected()) {
                    try {
                        //realizo la busqueda entre fechas
                        List<Map> findSalesStatisticsBetweenDays;
                        //si hay que hacer la busqueda dividida por turnos
                        if (guiSalesStatistics.getCheckTurn().isSelected()) {
                            findSalesStatisticsBetweenDays = interfaceStatistics.findSalesStatisticsBetweenDaysWithTurn(since, until);
                        } else {
                            findSalesStatisticsBetweenDays = interfaceStatistics.findSalesStatisticsBetweenDays(since, until);;
                        }
                        //cargo la tabla de la gui
                        loadTableDailyStatistics(findSalesStatisticsBetweenDays);
                        loadTableTotalSalesStatistics();
                            List<Map> totalSalesWaiterBetweenDays = interfaceStatistics.getTotalSalesWaiterBetweenDays(since, until);
                            guiSalesStatistics.drawingSalesChartWaiter(totalSalesWaiterBetweenDays);
                            guiSalesStatistics.drawingTablesChartWaiter(totalSalesWaiterBetweenDays);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //si se presiono el checkbox "mensual"
                    if (guiSalesStatistics.getCheckMonthly().isSelected()) {
                        try {
                            //realizo la busqueda entre fechas
                            List<Map> findSalesStatisticsBetweenMonths;
                            //si hay que hacer la busqueda dividida por turnos
                            if (guiSalesStatistics.getCheckTurn().isSelected()) {
                                findSalesStatisticsBetweenMonths = interfaceStatistics.findSalesStatisticsBetweenMonthsWithTurn(since, until);
                            } else {
                                findSalesStatisticsBetweenMonths = interfaceStatistics.findSalesStatisticsBetweenMonths(since, until);
                            }
                            //cargo la tabla de la gui
                            loadTableMonthlyStatistics(findSalesStatisticsBetweenMonths);
                            loadTableTotalSalesStatistics();
                            List<Map> totalSalesWaiterBetweenDays = interfaceStatistics.getTotalSalesWaiterBetweenDays(since, until);
                            guiSalesStatistics.drawingSalesChartWaiter(totalSalesWaiterBetweenDays);
                            guiSalesStatistics.drawingTablesChartWaiter(totalSalesWaiterBetweenDays);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //si se presiono el checkbox "anual"
                        if (guiSalesStatistics.getCheckAnnual().isSelected()) {
                            try {
                                //realizo la busqueda entre fechas
                                List<Map> findSalesStatisticsBetweenYears;
                                //si hay que hacer la busqueda dividida por turnos
                                if (guiSalesStatistics.getCheckTurn().isSelected()) {
                                    findSalesStatisticsBetweenYears = interfaceStatistics.findSalesStatisticsBetweenYearsWithTurn(since, until);
                                } else {
                                    findSalesStatisticsBetweenYears = interfaceStatistics.findSalesStatisticsBetweenYears(since, until);
                                }
                                //cargo la tabla de la gui
                                loadTableAnnualStatistics(findSalesStatisticsBetweenYears);
                                loadTableTotalSalesStatistics();
                            List<Map> totalSalesWaiterBetweenDays = interfaceStatistics.getTotalSalesWaiterBetweenDays(since, until);
                            guiSalesStatistics.drawingSalesChartWaiter(totalSalesWaiterBetweenDays);
                            guiSalesStatistics.drawingTablesChartWaiter(totalSalesWaiterBetweenDays);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            //si se presiono el checkbox "todo"
                            if (guiSalesStatistics.getCheckAll().isSelected()) {
                                try {
                                    //realizo la busqueda entre fechas
                                    List<Map> findAllSalesStatisticsBetweenDates;
                                    //si hay que hacer la busqueda dividida por turnos
                                    if (guiSalesStatistics.getCheckTurn().isSelected()) {
                                        findAllSalesStatisticsBetweenDates = interfaceStatistics.findAllSalesStatisticsBetweenDatesWithTurn(since, until);
                                    } else {
                                        findAllSalesStatisticsBetweenDates = interfaceStatistics.findAllSalesStatisticsBetweenDates(since, until);
                                    }
                                    //cargo la tabla de la gui
                                    loadTableAllStatistics(findAllSalesStatisticsBetweenDates);
                                    loadTableTotalSalesStatistics();
                            List<Map> totalSalesWaiterBetweenDays = interfaceStatistics.getTotalSalesWaiterBetweenDays(since, until);
                            guiSalesStatistics.drawingSalesChartWaiter(totalSalesWaiterBetweenDays);
                            guiSalesStatistics.drawingTablesChartWaiter(totalSalesWaiterBetweenDays);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
    }
    
    
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiSalesStatistics.getCheckMonthly()) {
            guiSalesStatistics.getCheckDaily().setSelected(false);
            guiSalesStatistics.getCheckAnnual().setSelected(false);
            guiSalesStatistics.getCheckAll().setSelected(false);
            obtainAndLoadSalesStatistics();
        }
        if (e.getSource() == guiSalesStatistics.getCheckAnnual()) {
            guiSalesStatistics.getCheckDaily().setSelected(false);
            guiSalesStatistics.getCheckMonthly().setSelected(false);
            guiSalesStatistics.getCheckAll().setSelected(false);
            obtainAndLoadSalesStatistics();
        }
        if (e.getSource() == guiSalesStatistics.getCheckDaily()) {
            guiSalesStatistics.getCheckMonthly().setSelected(false);
            guiSalesStatistics.getCheckAnnual().setSelected(false);
            guiSalesStatistics.getCheckAll().setSelected(false);
            obtainAndLoadSalesStatistics();
        }
        if (e.getSource() == guiSalesStatistics.getCheckAll()) {
            guiSalesStatistics.getCheckMonthly().setSelected(false);
            guiSalesStatistics.getCheckAnnual().setSelected(false);
            guiSalesStatistics.getCheckDaily().setSelected(false);
            obtainAndLoadSalesStatistics();
        }
        if (e.getSource() == guiSalesStatistics.getCheckTurn()) {
            obtainAndLoadSalesStatistics();
        }
    }

}
