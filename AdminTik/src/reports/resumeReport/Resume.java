/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.resumeReport;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author alangonzalez
 */
public class Resume {
    
    Object fecha;
    Object entrada;
    Object recM;
    Object recT;
    Object rec;
    Object gasto;
    Object saldo;
    
    public Resume(Object fecha, Object cajaEntrada, Object recM, Object recT, Object rec, Object gasto, Object saldo) {
        this.fecha = fecha;
        this.entrada = cajaEntrada;
        this.recM = recM;
        this.recT = recT;
        this.rec = rec;
        this.gasto = gasto;
        this.saldo = saldo;
    }

    
    public Object getFecha() {
        return fecha;
    }

    public Object getEntrada() {
        return entrada;
    }

    public Object getRecM() {
        return recM;
    }

    public Object getRecT() {
        return recT;
    }

    public Object getRec() {
        return rec;
    }

    public Object getGasto() {
        return gasto;
    }

    public Object getSaldo() {
        return saldo;
    }
    
    
}
