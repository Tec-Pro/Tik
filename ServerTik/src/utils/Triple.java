/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author Alan
 */
public class Triple implements Serializable{
    
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.deposit);
        hash = 53 * hash + Objects.hashCode(this.withdrawal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Triple other = (Triple) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.deposit, other.deposit)) {
            return false;
        }
        if (!Objects.equals(this.withdrawal, other.withdrawal)) {
            return false;
        }
        return true;
    }

   
    
    
}
