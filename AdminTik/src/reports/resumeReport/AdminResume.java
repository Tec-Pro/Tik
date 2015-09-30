/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.resumeReport;

/**
 *
 * @author alangonzalez
 */
public class AdminResume {
    
    String fecha;
    String admin;
    float entrega;
    float retiro;
    float total;

    public AdminResume(String fecha, String admin, float entrega, float retiro, float total) {
        this.fecha = fecha;
        this.admin = admin;
        this.entrega = entrega;
        this.retiro = retiro;
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAdmin() {
        return admin;
    }

    public float getEntrega() {
        return entrega;
    }

    public float getRetiro() {
        return retiro;
    }

    public float getTotal() {
        return total;
    }
    
    
}
