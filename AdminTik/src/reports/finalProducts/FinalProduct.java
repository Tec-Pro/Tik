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
    private float productionPrice;
    private float suggestedPrice;
    private float sellPrice;
    private float profit;
    private String category;
    private String subcategory;

    public FinalProduct(String product, float productionPrice, float suggestedPrice, float sellPrice, float profit, String category, String subcategory) {
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

    public float getProductionPrice() {
        return productionPrice;
    }

    public void setProductionPrice(float productionPrice) {
        this.productionPrice = productionPrice;
    }

    public float getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(float suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }
    
    
}
