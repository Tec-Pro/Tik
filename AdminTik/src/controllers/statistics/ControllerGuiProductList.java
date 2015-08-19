/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiProductList;
import interfaces.statistics.InterfaceStatistics;
import reports.finalProducts.ImplementsDataSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
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
import utils.GeneralConfig;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author eze
 */
public class ControllerGuiProductList implements ActionListener {
    
    private static InterfaceStatistics interfaceStatistics;
    private static GuiProductList guiProductList;
    private static List<Map<String, Object>> productList;
    
    public ControllerGuiProductList(GuiProductList guiPL) throws RemoteException, NotBoundException {
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        guiProductList = guiPL;
        productList = interfaceStatistics.getProductList();
        loadProductTable("");
        guiProductList.setActionListener(this);
        //si se escribe en el txt de búsqueda de la lista de productos
        guiProductList.getTxtSearchProductList().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                guiProductList.cleanComponents();
                try {
                    loadProductTable(guiProductList.getTxtSearchProductList().getText());
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    /*
     * Carga en la tabla tableProductList todos los productos finales (que contegan la subcadena 's' en
     * alguna parte de su nombre), con sus estadísticos.
     */
    
    private static void loadProductTable(String s) throws RemoteException {
        DefaultTableModel modelTableProductList = guiProductList.getModelTableProductList();
        Object[] row = new Object[8];
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (Map<String, Object> m : productList) {
            //Si el nombre contiene la subcadena 's'
            if (m.get("name").toString().toLowerCase().contains(s.toLowerCase())) {
                row[0] = m.get("name").toString();//nombre del producto final
                row[1] = m.get("category").toString();//categoria
                row[2] = m.get("subcategory").toString();//subcategoria
                row[3] = m.get("elaboration_price");//precio de elaboracion
                Float productionPrice = Float.parseFloat(m.get("elaboration_price").toString());
                row[4] = productionPrice + productionPrice * GeneralConfig.percent / 100;//precio sugerido
                Float sellPrice = Float.parseFloat(m.get("sell_price").toString());
                row[5] = sellPrice;//precio de venta
                Float percentGain = ParserFloat.stringToFloat(df.format(((sellPrice - productionPrice) / productionPrice) * 100));
                row[6] = percentGain;//porcentaje (%) de ganancia
                row[7] = (sellPrice - productionPrice);//ganancia (en pesos $)
                modelTableProductList.addRow(row);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(guiProductList.getBtnRefresh())){
            try {
                productList = interfaceStatistics.getProductList();
                guiProductList.cleanComponents();
                loadProductTable(guiProductList.getTxtSearchProductList().getText());
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource().equals(guiProductList.getBtnPrintReport())) {
            ImplementsDataSource datasource = new ImplementsDataSource();
            JTable tableFP = guiProductList.getTableProductList();
            for (int i = 0; i < guiProductList.getTableProductList().getRowCount(); i++) {
                FinalProduct fp = new FinalProduct(String.valueOf(tableFP.getValueAt(i, 0)),
                        tableFP.getValueAt(i, 3),
                        tableFP.getValueAt(i, 4),
                        tableFP.getValueAt(i, 5),
                        tableFP.getValueAt(i, 7),
                        String.valueOf(tableFP.getValueAt(i, 1)),
                        String.valueOf(tableFP.getValueAt(i, 2)));
                datasource.addFinalProduct(fp);
            }
            
            try {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/finalProducts/ReportFinalProducts.jasper"));//cargo el reporte
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
