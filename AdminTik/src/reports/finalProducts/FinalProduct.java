/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.finalProducts;

/**
 *
 * @author Alan
 */
public class FinalProduct {
   
    private String product;
    private Object productionPrice;
    private Object suggestedPrice;
    private Object sellPrice;
    private Object profit;
    private String category;
    private String subcategory;

    public FinalProduct(String product, Object productionPrice, Object suggestedPrice, Object sellPrice, Object profit, String category, String subcategory) {
        this.product = product;
        this.productionPrice = productionPrice;
        this.suggestedPrice = suggestedPrice;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Object getProductionPrice() {
        return productionPrice;
    }

    public void setProductionPrice(Object productionPrice) {
        this.productionPrice = productionPrice;
    }

    public Object getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Object suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Object getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Object sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Object getProfit() {
        return profit;
    }

    public void setProfit(Object profit) {
        this.profit = profit;
    }
    
    
}
