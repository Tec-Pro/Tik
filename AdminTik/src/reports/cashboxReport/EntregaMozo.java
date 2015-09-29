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
    String mozo;
    String turno;
    String hora;
    float monto;

    public EntregaMozo(String mozo, String turno, String hora, float monto) {
        this.mozo = mozo;
        this.turno = turno;
        this.hora = hora;
        this.monto = monto;
    }

    public String getMozo() {
        return mozo;
    }

    public String getTurno() {
        return turno;
    }

    public String getHora() {
        return hora;
    }

    public float getMonto() {
        return monto;
    }
    
    
}
