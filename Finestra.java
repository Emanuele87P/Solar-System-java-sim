import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

class Finestra extends JFrame{
  final static int DIM_CANVAS = 480;
  final static int TRASLAZIONE = DIM_CANVAS/2;
  final static Punto CENTRO = new Punto(TRASLAZIONE, TRASLAZIONE);
  int raggioSole = 35;
  int raggioTerra = 20;
  int raggioLuna = 10;
  int distanzaX = raggioSole+65+raggioTerra;
  int distanzaLunare = raggioTerra+7+raggioLuna;
  int raggioOrbita = (int) (Math.sqrt(distanzaX*distanzaX*2));
  int raggioOrbitaLunare = (int) (Math.sqrt((distanzaLunare*distanzaLunare)*2));
  Punto centroTerra = new Punto(CENTRO.getX()+distanzaX, CENTRO.getY()+distanzaX);
  Punto centroLuna = new Punto(centroTerra.getX()-distanzaLunare, centroTerra.getY()-distanzaLunare);
  double rotazioneRadInit = 0.3;
  double rotazioneRad = 0;
  double boostRot = 0;
  double boostRotInit = 0.8;
  Canvas cielo;
  
  public Finestra(){
    super("Sistema Solare");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(creaPannelloCentro(), BorderLayout.CENTER);
    add(creaPannelloBasso(), BorderLayout.SOUTH);
    pack();
  }
  JPanel creaPannelloCentro(){
    JPanel jpCentro = new JPanel();
    jpCentro.setBackground(Color.gray);
    cielo = new Canvas(){
      public void paint(Graphics g){
        creaPianeti(g);
      }
    };
    cielo.setSize(DIM_CANVAS, DIM_CANVAS);
    cielo.setBackground(Color.black);
    jpCentro.add(cielo);
    return jpCentro;
  }
  void creaPianeti(Graphics g){
    try{
      BufferedImage textureSole = ImageIO.read(new File("SunTexture.png"));
      BufferedImage textureTerra = ImageIO.read(new File("EarthTexture.png"));
      BufferedImage textureLuna = ImageIO.read(new File("MoonTexture.png"));
      Pianeta sole = new Pianeta(raggioSole, CENTRO, textureSole);
      sole.disegna(g, true, Color.red);
      Pianeta orbita = new Pianeta(raggioOrbita, CENTRO, null);
      orbita.disegna(g, false, Color.blue);
      Pianeta terra = new Pianeta(raggioTerra, centroTerra, textureTerra);
      terra.disegna(g, true, Color.blue);
      Pianeta orbitaLunare = new Pianeta(raggioOrbitaLunare, centroTerra, null);
      orbitaLunare.disegna(g, false, Color.blue);
      Pianeta luna = new Pianeta(raggioLuna, centroLuna, textureLuna);
      luna.disegna(g, true, Color.gray);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
  JPanel creaPannelloBasso(){
    Punto centroTerraInit = new Punto(centroTerra.getX(), centroTerra.getY());
    Punto lunaInit = new Punto(centroTerra.getX()-centroLuna.getX(), centroTerra.getY()-centroLuna.getY());

    System.out.println(lunaInit.getX() + " " + lunaInit.getY());
    
    JPanel jpBasso = new JPanel();
    jpBasso.setBackground(Color.gray);
    JButton jbStart = new JButton("Rotazione");
    jbStart.addActionListener(
      new ActionListener(){
        public void actionPerformed(ActionEvent e){
          ruotaPianeti(centroTerraInit, lunaInit, rotazioneRad);
          rotazioneRad=rotazioneRadInit+rotazioneRad;
          boostRot = boostRotInit+boostRot;
        }
      }
    );
    jpBasso.add(jbStart);
    return jpBasso;
  }
  void ruotaPianeti(Punto centroTerraInit, Punto lunaInit, double rotazioneRad){
    int terraX = (int) ((centroTerraInit.getX()-TRASLAZIONE)*Math.cos(-rotazioneRad)-(centroTerraInit.getY()-TRASLAZIONE)*Math.sin(-rotazioneRad));
    int terraY = (int) ((centroTerraInit.getX()-TRASLAZIONE)*Math.sin(-rotazioneRad)+(centroTerraInit.getY()-TRASLAZIONE)*Math.cos(-rotazioneRad));
    centroTerra.setX(terraX+TRASLAZIONE);
    centroTerra.setY(terraY+TRASLAZIONE);

    int lunaX = (int) ((lunaInit.getX())*Math.cos(-rotazioneRad+boostRot)-(lunaInit.getY())*Math.sin(-rotazioneRad+boostRot));
    int lunaY = (int) ((lunaInit.getX())*Math.sin(-rotazioneRad+boostRot)+(lunaInit.getY())*Math.cos(-rotazioneRad+boostRot));

    System.out.println(lunaX + " " + lunaY);

    centroLuna.setX(centroTerra.getX()-lunaX);
    centroLuna.setY(centroTerra.getY()-lunaY);
    
    cielo.repaint();
  }
}
