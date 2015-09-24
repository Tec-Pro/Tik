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
    
    String producto;
    String unidad;
    int cant;
    String cat;
    String subcat;
    String prov;
    float unit;
    float total;
    String fecha;

    public PproductStatistic(String producto, String unidad, int cant, String cat, String subcat, String prov, float unit, float total, String fecha) {
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public float getUnit() {
        return unit;
    }

    public void setUnit(float unit) {
        this.unit = unit;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    
}
