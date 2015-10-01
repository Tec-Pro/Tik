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
    
    Object id;
    Object tipo;
    Object detalle;
    Object monto;

    public Detalle(Object id, Object tipo, Object detalle, Object monto) {
        this.id = id;
        this.tipo = tipo;
        this.detalle = detalle;
        this.monto = monto;
    }

    public Object getId() {
        return id;
    }

    public Object getTipo() {
        return tipo;
    }

    public Object getDetalle() {
        return detalle;
    }

    public Object getMonto() {
        return monto;
    }
    
    
}
