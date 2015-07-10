/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.payments;


/**
 *
 * @author Alan
 */
public class Payment {
   
    private String product;
    private float cant;
    private float price;
    private float exceptions;


    public Payment(String product, float cant, float price, float exceptions) {
        this.product = product;
        this.cant = cant;
        this.price = price;
        this.exceptions =exceptions;
    }

    public String getProduct() {
        return product;
    }

    public float getCant() {
        return cant;
    }

    public float getPrice() {
        return price;
    }

    public float getExceptions() {
        return exceptions;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setCant(float cant) {
        this.cant = cant;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setExceptions(float exceptions) {
        this.exceptions = exceptions;
    }

 
    
    
}
