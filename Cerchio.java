import java.awt.*;

class Cerchio{
  int raggio;
  Punto centro = new Punto(0, 0);
  Punto vertice = new Punto(0, 0);
  public Cerchio(int raggio, Punto inputCentro){
    this.raggio=raggio;
    centro.setX(inputCentro.getX());
    centro.setY(inputCentro.getY());
  }
  public void disegna(Graphics g, boolean pieno, Color colore){
    vertice.setX(centro.getX()-raggio);
    vertice.setY(centro.getY()-raggio);
    g.setColor(colore);
    if(pieno)
      g.fillOval(vertice.getX(), vertice.getY(), raggio*2, raggio*2); 
    else
      g.drawOval(vertice.getX(), vertice.getY(), raggio*2, raggio*2); 
  }
}
