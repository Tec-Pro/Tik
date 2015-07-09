/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiProductStatistics;
import interfaces.statistics.InterfaceStatistics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
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
public class ControllerGuiProductStatistics implements ActionListener {

    private static InterfaceStatistics interfaceStatistics;
    private static GuiProductStatistics guiProductStatistics;

    public ControllerGuiProductStatistics(GuiProductStatistics controllerGP) throws RemoteException, NotBoundException {
        guiProductStatistics = controllerGP;
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        guiProductStatistics.setActionListener(this);
        guiProductStatistics.cleanComponents();
        //si cambia la fecha de busqueda "Desde"
        guiProductStatistics.getDateSince().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //limpio la tabla de resultados de la busqueda
                guiProductStatistics.getModelTableProductStatistics().setRowCount(0);
                //si la fecha "hasta" no esta vacia
                if (guiProductStatistics.getDateUntil().getDate() != null) {
                    //saco ambas fechas del datechooser
                    java.sql.Date since = new Date(guiProductStatistics.getDateSince().getDate().getTime());
                    java.sql.Date until = new Date(guiProductStatistics.getDateUntil().getDate().getTime());
                    //si se presiono el checkbox "diario"
                    if (guiProductStatistics.getCheckDaily().isSelected()) {
                        try {
                            //realizo la busqueda entre fechas
                            List<Map> findProductStatisticsBetweenDays = interfaceStatistics.findProductStatisticsBetweenDays(since, until);
                            //cargo la tabla de la gui
                            loadProductTable(findProductStatisticsBetweenDays);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //si se presiono el checkbox "mensual"
                        if (guiProductStatistics.getCheckMonthly().isSelected()) {
                            try {
                                //realizo la busqueda entre fechas
                                List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenMonths(since, until);
                                //cargo la tabla de la gui
                                loadProductTable(findProductStatisticsBetweenMonths);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            //si se presiono el checkbox "anual"
                            if (guiProductStatistics.getCheckAnnual().isSelected()) {
                                try {
                                    //realizo la busqueda entre fechas
                                    List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenYears(since, until);
                                    //cargo la tabla de la gui
                                    loadProductTable(findProductStatisticsBetweenMonths);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        });
        //Si cambia la fecha de busqueda "Hasta"
        guiProductStatistics.getDateUntil().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //limpio la tabla de resultados de la busqueda
                guiProductStatistics.getModelTableProductStatistics().setRowCount(0);
                //si la fecha "desde" no esta vacia
                if (guiProductStatistics.getDateSince().getDate() != null) {
                    //saco ambas fechas del datechooser
                    java.sql.Date since = new Date(guiProductStatistics.getDateSince().getDate().getTime());
                    java.sql.Date until = new Date(guiProductStatistics.getDateUntil().getDate().getTime());
                    if (guiProductStatistics.getCheckDaily().isSelected()) {
                        try {
                            //realizo la busqueda entre fechas
                            List<Map> findProductStatisticsBetweenDays = interfaceStatistics.findProductStatisticsBetweenDays(since, until);
                            //cargo la tabla de la gui
                            loadProductTable(findProductStatisticsBetweenDays);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //si se presiono el checkbox "mensual"
                        if (guiProductStatistics.getCheckMonthly().isSelected()) {
                            try {
                                //realizo la busqueda entre fechas
                                List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenMonths(since, until);
                                //cargo la tabla de la gui
                                loadProductTable(findProductStatisticsBetweenMonths);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            //si se presiono el checkbox "anual"
                            if (guiProductStatistics.getCheckAnnual().isSelected()) {
                                try {
                                    //realizo la busqueda entre fechas
                                    List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenYears(since, until);
                                    //cargo la tabla de la gui
                                    loadProductTable(findProductStatisticsBetweenMonths);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }

                }
            }
        });

    }

    /*
     * Carga en la tabla tableProductStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadProductTable(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableProductStatistics = guiProductStatistics.getModelTableProductStatistics();
        Object[] row = new Object[4];
        for (Map m : productStatistics) {
            row[0] = m.get("name");
            row[1] = m.get("quantity");
            row[2] = m.get("turn");
            row[3] = m.get("day");
            modelTableProductStatistics.addRow(row);
        }
    }

    private void getYearStatistics() {

    }

    private void getMonthStatistics() {

    }

    private void getDayStatistics() {

    }

    /*
     * Calcula las estadisticas de productos de un turno, cuando se cierra la caja
     */
    public static void calculateAndSaveProductStatistics() throws RemoteException {
        interfaceStatistics.saveStatisticsCurrentProductShift();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiProductStatistics.getCheckMonthly()) {
            guiProductStatistics.getCheckDaily().setSelected(false);
            guiProductStatistics.getCheckAnnual().setSelected(false);
            //limpio la tabla de resultados de la busqueda
            guiProductStatistics.getModelTableProductStatistics().setRowCount(0);
            //Si las fechas no son vacias
            if (guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate() != null) {
                //saco ambas fechas del datechooser
                java.sql.Date since = new Date(guiProductStatistics.getDateSince().getDate().getTime());
                java.sql.Date until = new Date(guiProductStatistics.getDateUntil().getDate().getTime());
                try {
                    //realizo la busqueda entre fechas
                    List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenMonths(since, until);
                    //cargo la tabla de la gui
                    loadProductTable(findProductStatisticsBetweenMonths);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiProductStatistics.getCheckAnnual()) {
            guiProductStatistics.getCheckDaily().setSelected(false);
            guiProductStatistics.getCheckMonthly().setSelected(false);
            //limpio la tabla de resultados de la busqueda
            guiProductStatistics.getModelTableProductStatistics().setRowCount(0);
            //Si las fechas no son vacias
            if (guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate() != null) {
                //saco ambas fechas del datechooser
                java.sql.Date since = new Date(guiProductStatistics.getDateSince().getDate().getTime());
                java.sql.Date until = new Date(guiProductStatistics.getDateUntil().getDate().getTime());
                try {
                    //realizo la busqueda entre fechas
                    List<Map> findProductStatisticsBetweenMonths = interfaceStatistics.findProductStatisticsBetweenYears(since, until);
                    //cargo la tabla de la gui
                    loadProductTable(findProductStatisticsBetweenMonths);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiProductStatistics.getCheckDaily()) {
            guiProductStatistics.getCheckMonthly().setSelected(false);
            guiProductStatistics.getCheckAnnual().setSelected(false);
            //limpio la tabla de resultados de la busqueda
            guiProductStatistics.getModelTableProductStatistics().setRowCount(0);
            //Si las fechas no son vacias
            if (guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate() != null) {
                //saco ambas fechas del datechooser
                java.sql.Date since = new Date(guiProductStatistics.getDateSince().getDate().getTime());
                java.sql.Date until = new Date(guiProductStatistics.getDateUntil().getDate().getTime());
                try {
                    //realizo la busqueda entre fechas
                    List<Map> findProductStatisticsBetweenDays = interfaceStatistics.findProductStatisticsBetweenDays(since, until);
                    //cargo la tabla de la gui
                    loadProductTable(findProductStatisticsBetweenDays);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
