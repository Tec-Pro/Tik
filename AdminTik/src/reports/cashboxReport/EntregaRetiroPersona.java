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
public class EntregaRetiroPersona {
    
    String persona;
    String turno;
    String hora;
    String monto;

    public EntregaRetiroPersona(String persona, String turno, String hora, String monto) {
        this.persona = persona;
        this.turno = turno;
        this.hora = hora;
        this.monto = monto;
    }

    public String getPersona() {
        return persona;
    }

    public String getTurno() {
        return turno;
    }

    public String getHora() {
        return hora;
    }

    public String getMonto() {
        return monto;
    }
    
    
}
