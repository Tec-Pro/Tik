/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.cashboxReport;

/**
 *
 * @author alangonzalez
 */
public class Detalle {
    
    String id;
    String tipo;
    String detalle;
    String monto;

    public Detalle(String id, String tipo, String detalle, String monto) {
        this.id = id;
        this.tipo = tipo;
        this.detalle = detalle;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getMonto() {
        return monto;
    }
    
    
}
