/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.payments;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Alan
 */
public class ImplementsDataSourcePayment implements JRDataSource {

    private List<Payment> payment = new ArrayList<Payment>();
    private int index = -1;

    @Override
    public boolean next() throws JRException {
        return ++index < payment.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        switch (jrf.getName()) {
            case "producto":
                valor = payment.get(index).getProduct();
                break;
            case "cantidad":
                valor = payment.get(index).getCant();
                break;
            case "excepcion":
                valor = payment.get(index).getExceptions();
                break;
            case "descuento":
                valor = payment.get(index).getDiscount();
                break;            
            case "precio":
                valor = payment.get(index).getPrice();
                break;
        }
        return valor;
    }

    public void addPayment(Payment fp) {
        this.payment.add(fp);
    }

    public void removeAllFinalProduct() {
        for (int i = 0; i < payment.size(); i++) {
            payment.remove(i);
        }
        index = -1;
    }

}
