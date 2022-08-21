import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

class Pianeta extends Cerchio{
  BufferedImage texture;
  int raggioAncora;
  Punto ancora;
  public Pianeta(int raggio, Punto inputCentro, BufferedImage texture){
    super(raggio, inputCentro);
    this.texture = texture;
  }
  public void setTexture(BufferedImage texture){
    this.texture = texture;
  }
  Punto getVertice(){
    return vertice;
  }
  Punto getAncora(){
    return ancora;
  }
  @Override
  public void disegna(Graphics g, boolean pieno, Color colore){
    vertice.setX(centro.getX()-raggio);
    vertice.setY(centro.getY()-raggio);
    g.setColor(colore);
    if(pieno){
      g.fillOval(vertice.getX(), vertice.getY(), raggio*2, raggio*2); 
      g.drawImage(texture, vertice.getX(), vertice.getY(), raggio*2,raggio*2, null);
    } else{
      g.drawOval(vertice.getX(), vertice.getY(), raggio*2, raggio*2); 
    }
  }
}
