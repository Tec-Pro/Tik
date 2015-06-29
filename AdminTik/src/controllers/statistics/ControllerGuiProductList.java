/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.statistics;

import gui.statistics.GuiProductList;
import interfaces.statistics.InterfaceStatistics;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import utils.GeneralConfig;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author eze
 */
public class ControllerGuiProductList {
    
    private static InterfaceStatistics interfaceStatistics;
    private static GuiProductList guiProductList;
    

    public ControllerGuiProductList(GuiProductList guiPL) throws RemoteException, NotBoundException {
        interfaceStatistics = (InterfaceStatistics) InterfaceName.registry.lookup(InterfaceName.CRUDStatistics);
        guiProductList = guiPL;
        loadProductTable();
    }
    
    private static void loadProductTable() throws RemoteException{
        List<Map<String, Object>> productList = interfaceStatistics.getProductList();
        DefaultTableModel modelTableProductList = guiProductList.getModelTableProductList();
        Object[] row = new Object[8];
        DecimalFormat df = new DecimalFormat("0.00"); 
        for(Map<String, Object> m : productList){
            row[0] = m.get("name");//nombre del producto final
            row[1] = m.get("category");//categoria
            row[2] = m.get("subcategory");//subcategoria
            row[3] = "$"+m.get("elaboration_price");//precio de elaboracion
            float productionPrice = Float.parseFloat(m.get("elaboration_price").toString());
            row[4] = "$"+ParserFloat.floatToString(productionPrice + productionPrice * GeneralConfig.percent / 100);//precio sugerido
            float sellPrice = Float.parseFloat(m.get("sell_price").toString());
            row[5] = "$"+sellPrice;//precio de venta
            row[6] = "%"+(df.format(((sellPrice - productionPrice)/productionPrice)*100).toString());//porcentaje (%) de ganancia
            row[7] = "$"+(sellPrice - productionPrice);//ganancia (en pesos $)
            modelTableProductList.addRow(row);
        }
    }
}
