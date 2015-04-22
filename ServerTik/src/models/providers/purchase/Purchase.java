/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.providers.purchase;

import java.util.LinkedList;
import org.javalite.activejdbc.Model;
import utils.Pair;

/**
 *
 * @author nico
 */
public class Purchase extends Model {
    //Lista de pares <Producto,cantidad>
    private LinkedList<Pair> products;
    private LinkedList<Float> finalPrices;
    
    public Purchase() {
        this.products = null;
        this.finalPrices = null;
    }


    public LinkedList<Pair> getProducts() {
        return products;
    }

    public void setProductos(LinkedList<Pair> productos) {
        this.products = productos;
    }

    public LinkedList<Float> getFinalPrices() {
        return finalPrices;
    }

    public void setFinalPrices(LinkedList<Float> finalPrices) {
        this.finalPrices = finalPrices;
    }
}
