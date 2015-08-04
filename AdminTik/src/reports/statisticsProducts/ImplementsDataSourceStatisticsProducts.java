/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.statisticsProducts;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import reports.finalProducts.FinalProduct;

/**
 *
 * @author Alan
 */
public class ImplementsDataSourceStatisticsProducts implements JRDataSource {

    private List<FinalProduct> productList = new ArrayList<FinalProduct>();
    private int index = -1;

    @Override
    public boolean next() throws JRException {
        return ++index < productList.size();
    }

    public List<FinalProduct> getProductList() {
        return productList;
    }
    
    

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;

        if ("product".equals(jrf.getName())) {
            valor = productList.get(index).getProduct();
        } else if ("amount".equals(jrf.getName())) {
            valor = productList.get(index).getAmount();
        } else if ("turn".equals(jrf.getName())) {
            valor = productList.get(index).getTurn();
        } else if ("date".equals(jrf.getName())) {
            valor = productList.get(index).getDate();
        }

        return valor;
    }

    public void addFinalProduct(FinalProduct fp) {
        this.productList.add(fp);
    }

    public void removeAllFinalProduct() {
        for (int i = 0; i < productList.size(); i++) {
            productList.remove(i);
        }
        index = -1;

    }
}
