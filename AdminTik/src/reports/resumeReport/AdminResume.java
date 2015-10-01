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
    
    Object fecha;
    Object admin;
    Object entrega;
    Object retiro;
    Object total;

    public AdminResume(Object fecha, Object admin, Object entrega, Object retiro, Object total) {
        this.fecha = fecha;
        this.admin = admin;
        this.entrega = entrega;
        this.retiro = retiro;
        this.total = total;
    }

    public Object getFecha() {
        return fecha;
    }

    public Object getAdmin() {
        return admin;
    }

    public Object getEntrega() {
        return entrega;
    }

    public Object getRetiro() {
        return retiro;
    }

    public Object getTotal() {
        return total;
    }
    
    
}
