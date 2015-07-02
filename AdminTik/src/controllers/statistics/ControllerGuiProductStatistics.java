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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class ControllerGuiProductStatistics implements ActionListener{

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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        //Si cambia la fecha de busqueda "Hasta"
        guiProductStatistics.getDateUntil().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }
    
    /*
    * Carga en la tabla tableProductStatistics todos los productos finales con sus estadisticos
    * dependiendo de la busqueda realizada
    */
    private static void loadProductTable(List<Map> productStatistics) throws RemoteException{
        DefaultTableModel modelTableProductStatistics = guiProductStatistics.getModelTableProductStatistics();
        Object[] row = new Object[4];
        for(Map m : productStatistics){
            row[0] = m.get("name");
            row[1] = m.get("quantity");
            row[2] = m.get("turn");
            row[3] = m.get("day");
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
        if(e.getSource()==guiProductStatistics.getCheckMonthly()){
            guiProductStatistics.getCheckDaily().setSelected(false);
            guiProductStatistics.getCheckAnnual().setSelected(false);
            if(guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate()!=null){
                Timestamp since = new Timestamp(guiProductStatistics.getDateSince().getDate().getTime());
                Timestamp until = new Timestamp(guiProductStatistics.getDateUntil().getDate().getTime());
                System.out.println(since.toString()+" "+until.toString());
            }
        }
        if(e.getSource()==guiProductStatistics.getCheckAnnual()){
            guiProductStatistics.getCheckDaily().setSelected(false);
            guiProductStatistics.getCheckMonthly().setSelected(false);
            if(guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate()!=null){
                Timestamp since = new Timestamp(guiProductStatistics.getDateSince().getDate().getTime());
                Timestamp until = new Timestamp(guiProductStatistics.getDateUntil().getDate().getTime());
                System.out.println(since.toString()+" "+until.toString());
            }
        }
        if(e.getSource()==guiProductStatistics.getCheckDaily()){
            guiProductStatistics.getCheckMonthly().setSelected(false);
            guiProductStatistics.getCheckAnnual().setSelected(false);
            if(guiProductStatistics.getDateSince().getDate() != null && guiProductStatistics.getDateUntil().getDate()!=null){
                Timestamp since = new Timestamp(guiProductStatistics.getDateSince().getDate().getTime());
                Timestamp until = new Timestamp(guiProductStatistics.getDateUntil().getDate().getTime());
                System.out.println(since.toString()+" "+until.toString());
            }
        }
    }
    
}
