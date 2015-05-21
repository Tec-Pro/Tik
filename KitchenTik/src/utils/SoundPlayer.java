/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author eze
 */
public class SoundPlayer {
    
    private static final String soundPath = System.getProperty("user.dir") + "/sounds/alarma.mp3";
    private static Player player;
    
    //Detiene el sonido que avisa sobre un retraso en un pedido
    public void stopSound() {
        if (player != null) {
            player.close();
        }
    }

    //Inicia el sonido que avisa sobre retraso en un pedido
    public void playSound() {
        //si habia un sonido reproduciendose, lo cierro para reproducir uno nuevo
        if (player != null) {
            player.close();
        }
        try {
            FileInputStream fis = new FileInputStream(soundPath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (FileNotFoundException | JavaLayerException e) {
            System.out.println(e);
        }
        // correo el proceso en un nuevo hilo para deterner la ejecucion del programa
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();

    }
    
}
