/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.text.DecimalFormat;

/**
 *
 * @author nico
 */
public  class ParserFloat {
    
    private static DecimalFormat numberFormat=new DecimalFormat("#0.00");


    
    public static String floatToString(Float number){
        return numberFormat.format(number);
}
    
    public static float stringToFloat(String number){
        return Float.parseFloat(number);
    }
}
