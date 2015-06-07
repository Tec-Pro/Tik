/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;


public class DesktopPaneImage extends JDesktopPane {

    //busco la imagen, y seteo al desktop pane la imagen pintando
    
    private Image IMG = new ImageIcon(getClass().getResource("/Images/fondo gris.png")).getImage();

    public void paintChildren(Graphics g) {
        g.drawImage(IMG, 0, 0, getWidth(), getHeight(), this);
        super.paintChildren(g);
    }
}
