package JdN;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        Ventana a = new Ventana();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class Ventana extends JFrame {

    Lamina lamina = new Lamina();

    public Ventana(){
        setVisible(true);
        setBounds(0,0,Utilitaria.getScreen(1)/2,Utilitaria.getScreen(2)/2);
        setResizable(true);
        add(lamina);
        addKeyListener(lamina);
    }

}


class Lamina extends JPanel implements KeyListener{

    Timer timer1 = new Timer();
    Timer timer2 = new Timer();

    private BolaDeFuegoAliada bolaAliada = BolaDeFuegoAliada.getInstance();
    private BolaDeFuegoEnemiga bolaEnemiga = BolaDeFuegoEnemiga.getInstance();

    BufferedImage bolaA;
    BufferedImage fondo;
    BufferedImage naveImg;
    BufferedImage naveClase1;
    BufferedImage naveClase2;
    BufferedImage naveClase3;
    BufferedImage bolaE;

    NaveEnemiga naveEne = NaveEnemiga.getInstance(1);
    Nave nave = Nave.getInstance();

    public Lamina(){
        setBounds(0,0,Utilitaria.getScreen(1)/2,Utilitaria.getScreen(2)/2);

        cargarImagenes();

        timer2.scheduleAtFixedRate(new Timertask2(),1000,120);
    }

    public void crearEnemigos(Graphics gra){

         if(naveEne.getClase() == 1){
            gra.drawImage(naveClase1,naveEne.getX(),naveEne.getY(),naveEne.getAnchura(),naveEne.getAltura(),null);
        }else if(naveEne.getClase() == 2){
            gra.drawImage(naveClase2,naveEne.getX(),naveEne.getY(),naveEne.getAnchura(),naveEne.getAltura(),null);
        }else if(naveEne.getClase() == 3){
            gra.drawImage(naveClase3,naveEne.getX(),naveEne.getY(),naveEne.getAnchura(),naveEne.getAltura(),null);
        }

        repaint();
    }



    public void paintComponent(Graphics g){

        for(int i=0;i<40;i++){
            for(int z=0;z<40;z++){
                g.drawImage(fondo,i*800,z*800,800,800,null);
            }
        }//FONDO--------------FIN

        g.drawImage(naveImg,nave.getX(),nave.getY(),nave.getAltura(),nave.getAnchura(),null);
        ///MI NAVE---------FIN

        g.drawImage(bolaA,bolaAliada.getX(),bolaAliada.getY(),bolaAliada.getAltura(),bolaAliada.getAnchura(),null);
        //MI BOLA--------------FIN

        crearEnemigos(g);
        //LOS ENEMIGOS------------------FIN

        g.drawImage(bolaE,bolaEnemiga.getX(),bolaEnemiga.getY(),bolaEnemiga.getAltura(),bolaEnemiga.getAnchura(),null);
        //BOLA ENEMIGA-----------------FIN




    }

    public void cargarImagenes(){

        try{
            fondo = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/fondo.png"));
            naveImg = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/nave.png"));
            naveClase1 = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/naveClase1.png"));
            naveClase2 = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/naveClase2.png"));
            naveClase3 = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/naveClase3.png"));
            bolaA = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/bolaA.png"));
            bolaE = ImageIO.read(new File("/Users/MatiDaneri/Documents/Java SE/JuegoDeNaves/assets/bolaE.png"));
        }catch(Exception e){
            System.out.println("Error al cargar las imagenes");
        }


    }

    //KEYLISTENER
    @Override
    public void keyTyped(KeyEvent e) {
       if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W'){
            nave.setY(nave.getY()-nave.getVelocidad());
           repaint();
        }else if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D'){
            nave.setX(nave.getX()+nave.getVelocidad());
           repaint();
        }else if(e.getKeyChar() == 'S' || e.getKeyChar() == 's'){
           nave.setY(nave.getY()+nave.getVelocidad());
           repaint();
        }else if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
            nave.setX(nave.getX()-nave.getVelocidad());
           repaint();
        }else if(e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {

           bolaAliada.setX(nave.getX());
           bolaAliada.setY(nave.getY());



            timer1.scheduleAtFixedRate(new Timertask1(),0,150);
            repaint();
       }




    }


    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void limites(){

        if(naveEne.getX()<=0){
            naveEne.setX(20);
        }else if(naveEne.getX() >= Utilitaria.getScreen(1)/2){
            naveEne.setX(Utilitaria.getScreen(1)/2-20);
        }

        /*if(nave.getX() <= 0){
            nave.setX(0);
        }
        if(nave.getX() >= Utilitaria.getScreen(1)/2){
            nave.setX(Utilitaria.getScreen(1)/2-20);
        }
        if(nave.getY() <= 150){
            nave.setY(Utilitaria.getScreen(2)-10);
        }
        if(nave.getY() >= Utilitaria.getScreen(2)/2-20){
            nave.setY(100);
        }*/


    }

    public void controlarAtaque(){

        int distancia = 5;

     /*   if(bolaAliada.getX() == naveEne.getX() && bolaAliada.getY() == naveEne.getY()){
            naveEne.setVida(naveEne.getVida()-nave.getDaño());
            System.out.println(naveEne.getVida() + "----Maatias le metiste daño.");
        }else{
            //Le erraste! :D (label o algo).
        }

        if(bolaEnemiga.getX() == nave.getX() && bolaEnemiga.getY() == nave.getY()){
            nave.setVida(nave.getVida()-naveEne.getDaño());
            System.out.println(nave.getVida() + "----Maatias te metieron daño.");
        }else{
            //le erró.
        }*/

        if(bolaAliada.getX() - naveEne.getX() <= distancia && bolaAliada.getX() - naveEne.getX() >= -distancia && bolaAliada.getY() - naveEne.getY() <= distancia && bolaAliada.getY() - naveEne.getY() >= -distancia){
            naveEne.setVida(naveEne.getVida()-nave.getDaño());
            System.out.println(naveEne.getVida() + "----Maatias le metiste daño.");
        }
        if(bolaEnemiga.getX() - nave.getX() <= distancia && bolaEnemiga.getX() - nave.getX() >= -distancia && bolaEnemiga.getY() - nave.getY() <= distancia && bolaEnemiga.getY() - nave.getY() >= -distancia){
            nave.setVida(nave.getVida()-naveEne.getDaño());
            System.out.println(nave.getVida() + "----Maatias te metieron daño.");
        }



    }

    private class Timertask1 extends TimerTask{

        public void run(){

            bolaAliada.setY(bolaAliada.getY()-10);

            repaint();

            if(bolaAliada.getY() <= 10){
                cancel();
            }
            limites();
            controlarAtaque();
        }


    }

    private class Timertask2 extends TimerTask{

        public void run(){
            naveEne.mover();
            naveEne.atacar();

            if(bolaEnemiga.getY() >= Utilitaria.getScreen(2)-260){
                bolaEnemiga.setX(naveEne.getX());
                bolaEnemiga.setY(naveEne.getY());
                bolaEnemiga.setInicio(true);
            }
            repaint();
            limites();
            controlarAtaque();
        }


    }




}




class NaveEnemiga{

    BolaDeFuegoEnemiga bola = BolaDeFuegoEnemiga.getInstance();

    private static Nave nave = Nave.getInstance();
    private static NaveEnemiga NaveEne;

    protected int vida;
    protected int daño;
    protected int clase;
    protected int x = 100;
    protected int y = 100;//Utilitaria.getScreen(2)/2;
    protected int altura = 40;
    protected int anchura = 40;

    public NaveEnemiga(int clase){
        this.clase = clase;
        newInstance(clase);
    }

    public void newInstance(int clase){
        if(this.clase == 1){
            this.vida = 100;
            this.daño = 4;
        }else if(this.clase == 2){
            this.vida = 200;
            this.daño = 24;
        }else if(this.clase == 3){
            this.vida = 300;
            this.daño = 30;
        }

    }

    public static NaveEnemiga getInstance(int clase){

        if(NaveEne == null){
            NaveEne = new NaveEnemiga(clase);
        }

        return NaveEne;
    }

    public void atacar(){

        int azar = (int)(Math.random()*2);

       // if(azar == 1){
           // if(this.clase == 1){
                setDaño(15);
                if(bola.getInicio() == true) {
                    bola.setX(getX());
                    bola.setInicio(false);
                }
                bola.setY(bola.getY()+10);

                bola.setAltura(20);
                bola.setAnchura(20);
   //         }
    /*else if(this.clase == 2){
                setDaño(25);

               if(bola.getInicio() == true) {
                   bola.setX(getX());
                   bola.setY(getY() + 10);
               }
                bola.setAltura(30);
                bola.setAnchura(30);
            }else if(this.clase == 3){
                setDaño(40);
                if(bola.getInicio() == true) {
                    bola.setX(getX());
                    bola.setY(getY() + 10);
                }
                bola.setAltura(40);
                bola.setAnchura(40);
            }
        }else if(azar == 0){
            System.out.println("no ataca");
        }else{
            System.out.println("otra cosa que 2");
        }*/




    }

    public void mover(){

        int azar = (int)(Math.random()*100);

 /*       if(azar <=49){
            setX(getX()+10);
        }else{
            setX(getX()-10);
        }
*/
        if(azar <= 38){//Movimiento común.
            int azar2 = (int)(Math.random()*100);

            if(azar2 <=49){
                setX(getX()+10);
            }else{
                setX(getX()-10);
            }
        }else{//Movimiento inteligente.
            if(nave.getX() < getX()){
                setX(getX()-10);
            }else if(nave.getX() > getX()){
                setX(getX()+10);
            }//No use el else, porque si están en la misma x quiero que dispare justo a la nave del jugador


        }



    }



//GETTERS Y SETTERS -------------------------------------
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getClase() {
        return clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }
}

class Nave{

    protected static Nave navePropia;
    protected int vida;
    protected int daño;
    protected int x = Utilitaria.getScreen(2)/4+60;
    protected int y = Utilitaria.getScreen(1)/4;
    protected int altura;
    protected int anchura;
    protected int velocidad;

    private Nave(){
        vida = 100;
        daño = 10;
        altura = 30;
        anchura = 30;
        velocidad = 10;
    }

    public static Nave getInstance(){

        if(navePropia == null){
            navePropia = new Nave();
        }

        return navePropia;
    }

    //GETTERS Y SETTERS---------------------

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }



}

class BolaDeFuegoAliada{

    private static BolaDeFuegoAliada bola;

    private int altura;
    private int anchura;
    private int x = 0;
    private int y = 0;

    private BolaDeFuegoAliada(){
        altura = 10;
        anchura = 10;

    }

    public static BolaDeFuegoAliada getInstance(){

        if(bola == null){
            bola = new BolaDeFuegoAliada();
        }

        return bola;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}

class BolaDeFuegoEnemiga{

    private static BolaDeFuegoEnemiga bola;

    private int altura;
    private int anchura;
    private int x;
    private int y;
    private boolean inicio = true;

    private BolaDeFuegoEnemiga(){
        altura = 10;
        anchura = 10;

    }

    public static BolaDeFuegoEnemiga getInstance(){

        if(bola == null){
            bola = new BolaDeFuegoEnemiga();
        }

        return bola;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }
}

class Utilitaria{

    public static int getScreen(int xy){
        Toolkit myToolkit = Toolkit.getDefaultToolkit();

       Dimension dimension = myToolkit.getScreenSize();

       if(xy == 2){
          return (int)dimension.getHeight();
       }else if(xy == 1){
          return  (int)dimension.getWidth();
       }


        return 3;
    }



}