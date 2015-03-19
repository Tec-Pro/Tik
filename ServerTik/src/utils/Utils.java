/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class Utils {

    //Se debe cerrar y abrir la base en cada movida ahora, sino no funciona RMI
    public static void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/tik", "root", "root");
        }
    }

    public static void cerrarBase() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }
}
