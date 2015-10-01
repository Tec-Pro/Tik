/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.purchaseStatistics;

/**
 *
 * @author alangonzalez
 */
public class PproductStatistic {
    
    Object producto;
    Object unidad;
    int cant;
    Object cat;
    Object subcat;
    Object prov;
    Object unit;
    Object total;
    Object fecha;

    public PproductStatistic(Object producto, Object unidad, int cant, Object cat, Object subcat, Object prov, Object unit, Object total, Object fecha) {
        this.producto = producto;
        this.unidad = unidad;
        this.cant = cant;
        this.cat = cat;
        this.subcat = subcat;
        this.prov = prov;
        this.unit = unit;
        this.total = total;
        this.fecha = fecha;
    }

    public Object getProducto() {
        return producto;
    }

    public void setProducto(Object producto) {
        this.producto = producto;
    }

    public Object getUnidad() {
        return unidad;
    }

    public void setUnidad(Object unidad) {
        this.unidad = unidad;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Object getCat() {
        return cat;
    }

    public void setCat(Object cat) {
        this.cat = cat;
    }

    public Object getSubcat() {
        return subcat;
    }

    public void setSubcat(Object subcat) {
        this.subcat = subcat;
    }

    public Object getProv() {
        return prov;
    }

    public void setProv(Object prov) {
        this.prov = prov;
    }

    public Object getUnit() {
        return unit;
    }

    public void setUnit(Object unit) {
        this.unit = unit;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public Object getFecha() {
        return fecha;
    }

    public void setFecha(Object fecha) {
        this.fecha = fecha;
    }

    
    
    
}
