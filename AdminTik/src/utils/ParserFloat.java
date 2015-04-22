/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author nico
 */
public class ParserFloat {

    private static DecimalFormat numberFormat = new DecimalFormat("#0.00");

    public static String floatToString(Float number) {
        return numberFormat.format(number);

    }

    public static float stringToFloat(String number) {
        float ret;
        
//        try {
            try {
                //ret=Float.valueOf(number);
                ret=numberFormat.parse(number).floatValue();
                //ret = Float.parseFloat(number.replace(',', '.'));
            } catch (ParseException ex) {
JOptionPane.showMessageDialog(null, "ERROR! el número \"" + number + "\" no respeta el formato\n Se pondrá un -9999", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return -9999;
            }
//        } catch (java.lang.NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "ERROR! el número \"" + number + "\" no respeta el formato\n Se pondrá un -9999", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
//            return -9999;
//        }
        return ret;
    }
}
