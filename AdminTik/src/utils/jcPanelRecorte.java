/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class jcPanelRecorte extends JPanel implements MouseMotionListener,MouseListener   {

    private Image FOTO_ORIGINAL;    
    private BufferedImage Imagen_en_memoria;
    private BufferedImage tmp_Recorte;

    private Graphics2D g2D;
    private boolean con_foto = false;

    //coordenadas y tamaño del recorte
    private float clipX=0;
    private float clipY=0;
    private float clipAncho=380;
    private float clipAlto=400;

    //variables para ek movimiento
    private int Pos_Marca_new_X = 0;
    private int Pos_Marca_new_Y = 0;
    private int Pos_Marca_X = 0;
    private int Pos_Marca_Y = 0;
    private int Dist_X=0;
    private int Dist_Y=0;

    private Color color_linea = new Color(200,0,0);
    private float grosor_linea = 2f;

    public jcPanelRecorte(BufferedImage f){
        this.FOTO_ORIGINAL = f;        
        this.setSize(f.getWidth(),f.getHeight());
        this.setVisible(true);
        this.con_foto=true;
          //eventos del raton
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      if(this.con_foto){
        //se crea un lienzo del tamaño de la foto
        Imagen_en_memoria = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        g2D = Imagen_en_memoria.createGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //se añade la foto grande original
        g2D.drawImage(FOTO_ORIGINAL,0, 0, FOTO_ORIGINAL.getWidth(this), FOTO_ORIGINAL.getHeight(this), this);
        //se crea un recuadro que sirve de referencia para el recorte
        g2D.setStroke(new BasicStroke(this.grosor_linea));
        g2D.setColor(color_linea);
        Rectangle2D r2 = new Rectangle2D.Float( clipX, clipY, clipAncho, clipAlto );
        g2D.draw(r2);        
        //se dibuja todo
        g2.drawImage(Imagen_en_memoria, 0, 0, this);
      }
    }

    //se extrae una subimagen de la imagen original del tamaño der recuadro rojo
    private void recortar(){
        tmp_Recorte = ((BufferedImage) FOTO_ORIGINAL).getSubimage((int)clipX,(int) clipY,(int) clipAncho,(int) clipAlto) ;
    }

    //metodo que guarda la imagen en disco en formato JPG
    public void guardar_imagen(String f){
        recortar();
        try {            
            //se escribe en disco            
            ImageIO.write(tmp_Recorte, "jpg", new File(f));
            JOptionPane.showMessageDialog(null, "El recorte se guardo correctamente...");
 } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: no se pudo guardar la imagen");
 }
   }

    /* metodos del mouse para el cuadro de recorte */
    public void mouseDragged(MouseEvent e) {
        //nuevas coordenadas
        Pos_Marca_new_X = (int)e.getPoint().getX();
        Pos_Marca_new_Y = (int)e.getPoint().getY();

        //System.out.println("new_x=" + Pos_Marca_new_X + " new_Y=" + Pos_Marca_new_Y);
        //se obtiene distancia del movimiento
        Dist_X = Pos_Marca_new_X - Pos_Marca_X;
        Dist_Y = Pos_Marca_new_Y - Pos_Marca_Y;

        //System.out.println("Dist_x=" + Dist_X + " Dist_Y=" + Dist_Y);
        //se coloca la nueva posicion
        clipX = clipX + Dist_X;
        clipY = clipY + Dist_Y;
        //System.out.println("clipX=" + clipX + " clipY=" + clipY);

        //evita que se revace los limites de la imagen
        if(clipX<0) clipX=0;
        if(clipY<0) clipY=0;
        if((clipX + this.clipAncho) > this.getWidth()) clipX = this.getWidth() - this.clipAncho ;
        if((clipY + this.clipAlto) > this.getHeight()) clipY = this.getHeight() - this.clipAlto ;        

        Pos_Marca_X = Pos_Marca_X + Dist_X ;
        Pos_Marca_Y = Pos_Marca_Y + Dist_Y ;
        this.repaint();
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        Pos_Marca_X = (int)e.getPoint().getX();
        Pos_Marca_Y = (int)e.getPoint().getY();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

}