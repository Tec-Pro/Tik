/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.statisticsSales;

/**
 *
 * @author A
 */
public class SaleStatistic {
    
    private Object mozo;
    private Object turno;
    private Object fecha;
    private Object venta;
    private Object mesa;
    private Object persona;
    private Object producto;
    private Object prom_mesa;
    private Object prom_persona;
    private Object prom_producto;
    private Object descuento;
    private Object excepcion;

    public SaleStatistic(Object mozo, Object fecha,Object turno, Object venta, Object mesa, Object persona, Object producto, Object prom_mesa, Object prom_persona, Object prom_producto, Object descuento, Object excepcion) {
        this.mozo = mozo;
        this.turno = turno;
        this.fecha = fecha;
        this.venta = venta;
        this.mesa = mesa;
        this.persona = persona;
        this.producto = producto;
        this.prom_mesa = prom_mesa;
        this.prom_persona = prom_persona;
        this.prom_producto = prom_producto;
        this.descuento = descuento;
        this.excepcion = excepcion;
    }

    public SaleStatistic(Object venta, Object mesa, Object persona, Object producto, Object prom_mesa, Object prom_persona, Object prom_producto, Object descuento, Object excepcion,Object turno) {
        this.turno = turno;
        this.venta = venta;
        this.mesa = mesa;
        this.persona = persona;
        this.producto = producto;
        this.prom_mesa = prom_mesa;
        this.prom_persona = prom_persona;
        this.prom_producto = prom_producto;
        this.descuento = descuento;
        this.excepcion = excepcion;
    }

    
    public Object getMozo() {
        return mozo;
    }

    public void setMozo(Object mozo) {
        this.mozo = mozo;
    }

    public Object getTurno() {
        return turno;
    }

    public void setTurno(Object turno) {
        this.turno = turno;
    }

    public Object getFecha() {
        return fecha;
    }

    public void setFecha(Object fecha) {
        this.fecha = fecha;
    }

    public Object getVenta() {
        return venta;
    }

    public void setVenta(Object venta) {
        this.venta = venta;
    }

    public Object getMesa() {
        return mesa;
    }

    public void setMesa(Object mesa) {
        this.mesa = mesa;
    }

    public Object getPersona() {
        return persona;
    }

    public void setPersona(Object persona) {
        this.persona = persona;
    }

    public Object getProducto() {
        return producto;
    }

    public void setProducto(Object producto) {
        this.producto = producto;
    }

    public Object getProm_mesa() {
        return prom_mesa;
    }

    public void setProm_mesa(Object prom_mesa) {
        this.prom_mesa = prom_mesa;
    }

    public Object getProm_persona() {
        return prom_persona;
    }

    public void setProm_persona(Object prom_persona) {
        this.prom_persona = prom_persona;
    }

    public Object getProm_producto() {
        return prom_producto;
    }

    public void setProm_producto(Object prom_producto) {
        this.prom_producto = prom_producto;
    }

    public Object getDescuento() {
        return descuento;
    }

    public void setDescuento(Object descuento) {
        this.descuento = descuento;
    }

    public Object getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(Object excepcion) {
        this.excepcion = excepcion;
    }
    
    
}
