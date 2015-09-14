/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiPurchaseStatistics;
import interfaces.statistics.InterfacePurchaseStatistics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
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
public class ControllerGuiPurchaseStatistics implements ActionListener {

    private static InterfacePurchaseStatistics interfacePurchaseStatistics;
    private static GuiPurchaseStatistics guiPurchaseStatistics;

    public ControllerGuiPurchaseStatistics(GuiPurchaseStatistics controllerGP) throws RemoteException, NotBoundException {
        guiPurchaseStatistics = controllerGP;
        interfacePurchaseStatistics = (InterfacePurchaseStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDPurchaseStatistics);
        guiPurchaseStatistics.setActionListener(this);
        guiPurchaseStatistics.cleanComponents();
        //si cambia la fecha de busqueda "Desde"
        guiPurchaseStatistics.getDateSince().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                obtainAndLoadPurchaseStatistics();
            }
        });
        //Si cambia la fecha de busqueda "Hasta"
        guiPurchaseStatistics.getDateUntil().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                obtainAndLoadPurchaseStatistics();
            }
        });
        //si se escribe en el txt de búsqueda de la lista de productos
        guiPurchaseStatistics.getTxtSearchPurchaseStatistics().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                obtainAndLoadPurchaseStatistics();
            }
        });

    }

    /*
     * Carga en la tabla tablePurchaseStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadTableDailyStatistics(List<Map> productStatistics, String s) throws RemoteException {
        DefaultTableModel modelTablePurchaseStatistics = guiPurchaseStatistics.getModelTablePurchaseStatistics();
        Object[] row = new Object[9];
        for (Map m : productStatistics) {
            //Si el nombre contiene la subcadena 's'
            if (m.get("pproduct_name").toString().toLowerCase().contains(s.toLowerCase())) {
                row[0] = m.get("pproduct_name");
                row[1] = m.get("measure_unit");
                row[2] = m.get("quantity");
                row[3] = m.get("pproductcategory_name");
                row[4] = m.get("pproductsubcategory_name");
                row[5] = m.get("provider_name");
                row[6] = m.get("unit_price");
                row[7] = m.get("total_price");
                row[8] = "Día: " + m.get("day");
                modelTablePurchaseStatistics.addRow(row);
            }
        }
    }

    /*
     * Carga en la tabla tablePurchaseStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadTableMonthlyStatistics(List<Map> productStatistics, String s) throws RemoteException {
        DefaultTableModel modelTablePurchaseStatistics = guiPurchaseStatistics.getModelTablePurchaseStatistics();
        Object[] row = new Object[9];
        for (Map m : productStatistics) {
            if (m.get("pproduct_name").toString().toLowerCase().contains(s.toLowerCase())) {
                row[0] = m.get("pproduct_name");
                row[1] = m.get("measure_unit");
                row[2] = m.get("quantity");
                row[3] = m.get("pproductcategory_name");
                row[4] = m.get("pproductsubcategory_name");
                row[5] = m.get("provider_name");
                row[6] = m.get("unit_price");
                row[7] = m.get("total_price");
                Date date = Date.valueOf(m.get("day").toString());
                row[8] = "Mes: " + (date.getMonth() + 1) + " Año: " + (date.getYear() + 1900);
                modelTablePurchaseStatistics.addRow(row);
            }
        }
    }

    /*
     * Carga en la tabla tablePurchaseStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadTableAnnualStatistics(List<Map> productStatistics, String s) throws RemoteException {
        DefaultTableModel modelTablePurchaseStatistics = guiPurchaseStatistics.getModelTablePurchaseStatistics();
        Object[] row = new Object[9];
        for (Map m : productStatistics) {
            //Si el nombre contiene la subcadena 's'
            if (m.get("pproduct_name").toString().toLowerCase().contains(s.toLowerCase())) {
                row[0] = m.get("pproduct_name");
                row[1] = m.get("measure_unit");
                row[2] = m.get("quantity");
                row[3] = m.get("pproductcategory_name");
                row[4] = m.get("pproductsubcategory_name");
                row[5] = m.get("provider_name");
                row[6] = m.get("unit_price");
                row[7] = m.get("total_price");
                Date date = Date.valueOf(m.get("day").toString());
                row[8] = "Año: " + (date.getYear() + 1900);
                modelTablePurchaseStatistics.addRow(row);
            }
        }
    }
    /*
     * Carga en la tabla tablePurchaseStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */

    private static void loadTableAllStatistics(List<Map> productStatistics, String s) throws RemoteException {
        DefaultTableModel modelTablePurchaseStatistics = guiPurchaseStatistics.getModelTablePurchaseStatistics();
        Object[] row = new Object[9];
        for (Map m : productStatistics) {
            //Si el nombre contiene la subcadena 's'
            if (m.get("pproduct_name").toString().toLowerCase().contains(s.toLowerCase())) {
                row[0] = m.get("pproduct_name");
                row[1] = m.get("measure_unit");
                row[2] = m.get("quantity");
                row[3] = m.get("pproductcategory_name");
                row[4] = m.get("pproductsubcategory_name");
                row[5] = m.get("provider_name");
                row[6] = m.get("unit_price");
                row[7] = m.get("total_price");
                Date date = Date.valueOf(m.get("day").toString());
                row[8] = "Entre: " + (new Date(guiPurchaseStatistics.getDateSince().getDate().getTime())).toString()
                        + " - " + (new Date(guiPurchaseStatistics.getDateUntil().getDate().getTime())).toString();
                modelTablePurchaseStatistics.addRow(row);
            }
        }
    }

    private void obtainAndLoadPurchaseStatistics() {
        //limpio la tabla de resultados de la busqueda
        guiPurchaseStatistics.getModelTablePurchaseStatistics().setRowCount(0);
        //si la fecha "desde" y "hasta" no estan vacias
        if (guiPurchaseStatistics.getDateSince().getDate() != null && guiPurchaseStatistics.getDateUntil().getDate() != null) {
            //saco ambas fechas del datechooser
            java.sql.Date since = new Date(guiPurchaseStatistics.getDateSince().getDate().getTime());
            java.sql.Date until = new Date(guiPurchaseStatistics.getDateUntil().getDate().getTime());
            //si se presiono el checkbox "diario"
            if (guiPurchaseStatistics.getCheckDaily().isSelected()) {
                try {
                    //realizo la busqueda entre fechas
                    List<Map> findPurchaseStatisticsBetweenDays;
                        findPurchaseStatisticsBetweenDays = interfacePurchaseStatistics.findPurchaseStatisticsBetweenDays(since, until);;
                    //cargo la tabla de la gui
                    loadTableDailyStatistics(findPurchaseStatisticsBetweenDays, guiPurchaseStatistics.getTxtSearchPurchaseStatistics().getText());
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiPurchaseStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //si se presiono el checkbox "mensual"
                if (guiPurchaseStatistics.getCheckMonthly().isSelected()) {
                    try {
                        //realizo la busqueda entre fechas
                        List<Map> findPurchaseStatisticsBetweenMonths;
                        //si hay que hacer la busqueda dividida por turnos
                        findPurchaseStatisticsBetweenMonths = interfacePurchaseStatistics.findPurchaseStatisticsBetweenMonths(since, until);
                        //cargo la tabla de la gui
                        loadTableMonthlyStatistics(findPurchaseStatisticsBetweenMonths, guiPurchaseStatistics.getTxtSearchPurchaseStatistics().getText());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiPurchaseStatistics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //si se presiono el checkbox "anual"
                    if (guiPurchaseStatistics.getCheckAnnual().isSelected()) {
                        try {
                            //realizo la busqueda entre fechas
                            List<Map> findPurchaseStatisticsBetweenYears;
                            findPurchaseStatisticsBetweenYears = interfacePurchaseStatistics.findPurchaseStatisticsBetweenYears(since, until);
                            //cargo la tabla de la gui
                            loadTableAnnualStatistics(findPurchaseStatisticsBetweenYears, guiPurchaseStatistics.getTxtSearchPurchaseStatistics().getText());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiPurchaseStatistics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //si se presiono el checkbox "todo"
                        if (guiPurchaseStatistics.getCheckAll().isSelected()) {
                            try {
                                //realizo la busqueda entre fechas
                                List<Map> findAllPurchaseStatisticsBetweenDates;
                                //si hay que hacer la busqueda dividida por turnos
                                findAllPurchaseStatisticsBetweenDates = interfacePurchaseStatistics.findAllPurchaseStatisticsBetweenDates(since, until);
                                //cargo la tabla de la gui
                                loadTableAllStatistics(findAllPurchaseStatisticsBetweenDates, guiPurchaseStatistics.getTxtSearchPurchaseStatistics().getText());
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiPurchaseStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiPurchaseStatistics.getCheckMonthly()) {
            guiPurchaseStatistics.getCheckDaily().setSelected(false);
            guiPurchaseStatistics.getCheckAnnual().setSelected(false);
            guiPurchaseStatistics.getCheckAll().setSelected(false);
            obtainAndLoadPurchaseStatistics();
        }
        if (e.getSource() == guiPurchaseStatistics.getCheckAnnual()) {
            guiPurchaseStatistics.getCheckDaily().setSelected(false);
            guiPurchaseStatistics.getCheckMonthly().setSelected(false);
            guiPurchaseStatistics.getCheckAll().setSelected(false);
            obtainAndLoadPurchaseStatistics();
        }
        if (e.getSource() == guiPurchaseStatistics.getCheckDaily()) {
            guiPurchaseStatistics.getCheckMonthly().setSelected(false);
            guiPurchaseStatistics.getCheckAnnual().setSelected(false);
            guiPurchaseStatistics.getCheckAll().setSelected(false);
            obtainAndLoadPurchaseStatistics();
        }
        if (e.getSource() == guiPurchaseStatistics.getCheckAll()) {
            guiPurchaseStatistics.getCheckMonthly().setSelected(false);
            guiPurchaseStatistics.getCheckAnnual().setSelected(false);
            guiPurchaseStatistics.getCheckDaily().setSelected(false);
            obtainAndLoadPurchaseStatistics();
        }
        if (e.getSource() == guiPurchaseStatistics.getBtnPrintReport()) {
            System.out.println("El reporte no anda Vieja!");
        }
    }

}
