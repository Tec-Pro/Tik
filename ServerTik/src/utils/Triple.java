/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


/**
 *
 * @author Alan
 */
public class Triple {
    
    String name;
    Float deposit;
    Float withdrawal;

    public Triple(String name, Float deposit, Float withdrawal) {
        this.name = name;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    public Float getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Float withdrawal) {
        this.withdrawal = withdrawal;
    }

   
    
    
}
