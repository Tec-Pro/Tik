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
public class EntregaMozo {
    Object mozo;
    Object turno;
    Object hora;
    Object monto;

    public EntregaMozo(Object mozo, Object turno, Object hora, Object monto) {
        this.mozo = mozo;
        this.turno = turno;
        this.hora = hora;
        this.monto = monto;
    }

    public Object getMozo() {
        return mozo;
    }

    public Object getTurno() {
        return turno;
    }

    public Object getHora() {
        return hora;
    }

    public Object getMonto() {
        return monto;
    }
    
    
}
