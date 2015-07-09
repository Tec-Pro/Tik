/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.finalProducts;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Alan
 */
public class ImplementsDataSource implements JRDataSource {

    private List<FinalProduct> productList = new ArrayList<FinalProduct>();
    private int index = -1;

    @Override
    public boolean next() throws JRException {
        return ++index < productList.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;

        if ("product".equals(jrf.getName())) {
            valor = productList.get(index).getProduct();
        } else if ("productionPrice".equals(jrf.getName())) {
            valor = productList.get(index).getProductionPrice();
        } else if ("suggestedPrice".equals(jrf.getName())) {
            valor = productList.get(index).getSuggestedPrice();
        } else if ("sellPrice".equals(jrf.getName())) {
            valor = productList.get(index).getSellPrice();
        } else if ("category".equals(jrf.getName())) {
            valor = productList.get(index).getCategory();
        } else if ("subcategory".equals(jrf.getName())) {
            valor = productList.get(index).getSubcategory();
        } else if ("profit".equals(jrf.getName())) {
            valor = productList.get(index).getProfit();
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
