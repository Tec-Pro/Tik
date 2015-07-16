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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.finalProducts.FinalProduct;
import reports.finalProducts.ImplementsDataSourceStatisticsProducts;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class ControllerGuiProductStatistics implements ActionListener {

    private static InterfaceStatistics interfaceStatistics;
    private static GuiProductStatistics guiProductStatistics;
    private JTable tableFP;
    private ImplementsDataSourceStatisticsProducts datasource;

    public ControllerGuiProductStatistics(GuiProductStatistics controllerGP) throws RemoteException, NotBoundException {
        guiProductStatistics = controllerGP;
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        guiProductStatistics.setActionListener(this);
        guiProductStatistics.cleanComponents();
        tableFP = guiProductStatistics.getTableProductStatistics();
        datasource = new ImplementsDataSourceStatisticsProducts();
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
                            loadTableDailyStatistics(findProductStatisticsBetweenDays);
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
                                loadTableMonthlyStatistics(findProductStatisticsBetweenMonths);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            //si se presiono el checkbox "anual"
                            if (guiProductStatistics.getCheckAnnual().isSelected()) {
                                try {
                                    //realizo la busqueda entre fechas
                                    List<Map> findProductStatisticsBetweenYears = interfaceStatistics.findProductStatisticsBetweenYears(since, until);
                                    //cargo la tabla de la gui
                                    loadTableAnnualStatistics(findProductStatisticsBetweenYears);
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
                            loadTableDailyStatistics(findProductStatisticsBetweenDays);
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
                                loadTableMonthlyStatistics(findProductStatisticsBetweenMonths);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            //si se presiono el checkbox "anual"
                            if (guiProductStatistics.getCheckAnnual().isSelected()) {
                                try {
                                    //realizo la busqueda entre fechas
                                    List<Map> findProductStatisticsBetweenYears = interfaceStatistics.findProductStatisticsBetweenYears(since, until);
                                    //cargo la tabla de la gui
                                    loadTableAnnualStatistics(findProductStatisticsBetweenYears);
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
    private static void loadTableDailyStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableProductStatistics = guiProductStatistics.getModelTableProductStatistics();
        Object[] row = new Object[4];
        for (Map m : productStatistics) {
            row[0] = m.get("name");
            row[1] = m.get("quantity");
            row[2] = m.get("turn");
            row[3] = "Día: "+m.get("day");
            modelTableProductStatistics.addRow(row);
        }
    }
    
    /*
     * Carga en la tabla tableProductStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadTableMonthlyStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableProductStatistics = guiProductStatistics.getModelTableProductStatistics();
        Object[] row = new Object[4];
        for (Map m : productStatistics) {
            row[0] = m.get("name");
            row[1] = m.get("quantity");
            row[2] = m.get("turn");
            Date date = Date.valueOf(m.get("day").toString());
            row[3] = "Mes: "+(date.getMonth()+1)+" Año: "+(date.getYear()+1900);
            modelTableProductStatistics.addRow(row);
        }
    }
    
    /*
     * Carga en la tabla tableProductStatistics todos los productos finales con sus estadisticos
     * dependiendo de la busqueda realizada
     */
    private static void loadTableAnnualStatistics(List<Map> productStatistics) throws RemoteException {
        DefaultTableModel modelTableProductStatistics = guiProductStatistics.getModelTableProductStatistics();
        Object[] row = new Object[4];
        for (Map m : productStatistics) {
            row[0] = m.get("name");
            row[1] = m.get("quantity");
            row[2] = m.get("turn");
            Date date = Date.valueOf(m.get("day").toString());
            row[3] = "Año: "+(date.getYear()+1900);
            modelTableProductStatistics.addRow(row);
        }
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
                    loadTableMonthlyStatistics(findProductStatisticsBetweenMonths);
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
                    loadTableAnnualStatistics(findProductStatisticsBetweenMonths);
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
                    loadTableDailyStatistics(findProductStatisticsBetweenDays);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiProductStatistics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiProductStatistics.getBtnPrintReport()) {
            for (int i = 0; i < guiProductStatistics.getTableProductStatistics().getRowCount(); i++) {
                FinalProduct fp = new FinalProduct(String.valueOf(tableFP.getValueAt(i, 0)),
                        tableFP.getValueAt(i, 1),
                        tableFP.getValueAt(i, 2),
                        tableFP.getValueAt(i, 3));
                datasource.addFinalProduct(fp);
            }

            try {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/finalProducts/ReportStatisticsProducts.jasper"));//cargo el reporte
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
            datasource.removeAllFinalProduct();
        }
    }

}
