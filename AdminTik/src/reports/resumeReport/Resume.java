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
    
    String fecha;
    float entrada;
    float recM;
    float recT;
    float rec;
    float gasto;
    float saldo;
    
    public Resume(String fecha, float cajaEntrada, float recM, float recT, float rec, float gasto, float saldo) {
        this.fecha = fecha;
        this.entrada = cajaEntrada;
        this.recM = recM;
        this.recT = recT;
        this.rec = rec;
        this.gasto = gasto;
        this.saldo = saldo;
    }

    
    public String getFecha() {
        return fecha;
    }

    public float getEntrada() {
        return entrada;
    }

    public float getRecM() {
        return recM;
    }

    public float getRecT() {
        return recT;
    }

    public float getRec() {
        return rec;
    }

    public float getGasto() {
        return gasto;
    }

    public float getSaldo() {
        return saldo;
    }
    
    
}
