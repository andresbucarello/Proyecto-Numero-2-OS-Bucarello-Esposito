/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import EDD.LinkedList;
import EDD.Node;
import EDD.Queue;
import Helpers.Helpers;

/**
 *
 * @author user
 */
public class Franquicia {

    private LinkedList yellowCards;
    private LinkedList greenCards;
    private LinkedList redCards;

    private Queue colaP1 = new Queue();
    private Queue colaP2 = new Queue();
    private Queue colaP3 = new Queue();
    private Queue colaRefuerzo = new Queue();
    private String packageImg;
    private String logoUrl;
    private String franquicia;

    public Franquicia(String franquicia, String packageImg, LinkedList yellowCards, LinkedList greenCards, LinkedList redCards) {
        this.yellowCards = yellowCards;
        this.greenCards = greenCards;
        this.redCards = redCards;

        this.packageImg = packageImg;
        this.logoUrl = this.packageImg + "/logo.png";

    }

    private void crearEncolarPersonaje(Node nodoPersonaje) {
        nodoPersonaje.setIdNode(nodoPersonaje.getIdNode() + 1);

        Personaje personaje = nodoPersonaje.getTInfo();

        int nivelPrioridad = personaje.getNivelPrioridad();

        String id = personaje.getNombre() + "-"
                + Helpers.priority[nivelPrioridad - 1] + "-"
                + nodoPersonaje.getIdNode();

        String nombre = personaje.getNombre();
        int salud = personaje.getSalud();
        int velocidad = personaje.getVelocidad();
        int agilidad = personaje.getAgilidad();
        String habilidad = personaje.getHabilidad();
        String urlSource = personaje.getUrlSource();

        Personaje personajeNuevo = new Personaje(
                id,
                nombre,
                salud,
                velocidad,
                agilidad,
                habilidad,
                urlSource
        );

        personajeNuevo.setNivelPrioridad(nivelPrioridad);
        
        System.out.println("PRIORIDADD 1-Buena | 3-Mala");
        System.out.println(nivelPrioridad);

        if (nivelPrioridad == 1) {
            this.colaP1.enqueue(personajeNuevo);
        } else if (nivelPrioridad == 2) {
            this.colaP2.enqueue(personajeNuevo);
        } else {
            this.colaP3.enqueue(personajeNuevo);
        }
    }

    public void crearPersonaje() {
        int calidad = 0;
        double salud = Math.random();
        double velocidad = Math.random();
        double habilidad = Math.random();
        double agilidad = Math.random();

        calidad = (habilidad <= 0.6) ? calidad + 1 : calidad;
        calidad = (salud <= 0.7) ? calidad + 1 : calidad;
        calidad = (velocidad <= 0.5) ? calidad + 1 : calidad;
        calidad = (agilidad <= 0.4) ? calidad + 1 : calidad;

        Node node = new Node();
        
        System.out.println("CALIDADDD 1-MAlA  | 4-BUENA");
        System.out.println(calidad);

        if (calidad == 4) {
            node = yellowCards.getRandomNode();
        } else if (calidad == 3 || calidad == 2) {
            node = greenCards.getRandomNode();
        } else {
            node = redCards.getRandomNode();
        }
        crearEncolarPersonaje(node);
    }

    public void actualizarColaP1() {
        if (!(this.colaP2.isEmpty())) {
            Personaje aux = this.colaP2.dequeue();
            aux.setContadorInanicion(0);
            this.colaP1.enqueue(aux);
        } else {
            Personaje aux = this.colaP3.dequeue();
            aux.setContadorInanicion(0);
            this.colaP1.enqueue(aux);
        }
    }

    /**
     * @return the yellowCards
     */
    public LinkedList getYellowCards() {
        return yellowCards;
    }

    /**
     * @param yellowCards the yellowCards to set
     */
    public void setYellowCards(LinkedList yellowCards) {
        this.yellowCards = yellowCards;
    }

    /**
     * @return the greenCards
     */
    public LinkedList getGreenCards() {
        return greenCards;
    }

    /**
     * @param greenCards the greenCards to set
     */
    public void setGreenCards(LinkedList greenCards) {
        this.greenCards = greenCards;
    }

    /**
     * @return the redCards
     */
    public LinkedList getRedCards() {
        return redCards;
    }

    /**
     * @param redCards the redCards to set
     */
    public void setRedCards(LinkedList redCards) {
        this.redCards = redCards;
    }

    /**
     * @return the colaP1
     */
    public Queue getColaP1() {
        return colaP1;
    }

    /**
     * @param colaP1 the colaP1 to set
     */
    public void setColaP1(Queue colaP1) {
        this.colaP1 = colaP1;
    }

    /**
     * @return the colaP2
     */
    public Queue getColaP2() {
        return colaP2;
    }

    /**
     * @param colaP2 the colaP2 to set
     */
    public void setColaP2(Queue colaP2) {
        this.colaP2 = colaP2;
    }

    /**
     * @return the colaP3
     */
    public Queue getColaP3() {
        return colaP3;
    }

    /**
     * @param colaP3 the colaP3 to set
     */
    public void setColaP3(Queue colaP3) {
        this.colaP3 = colaP3;
    }

    /**
     * @return the colaRefuerzo
     */
    public Queue getColaRefuerzo() {
        return colaRefuerzo;
    }

    /**
     * @param colaRefuerzo the colaRefuerzo to set
     */
    public void setColaRefuerzo(Queue colaRefuerzo) {
        this.colaRefuerzo = colaRefuerzo;
    }

    /**
     * @return the packageImg
     */
    public String getPackageImg() {
        return packageImg;
    }

    /**
     * @param packageImg the packageImg to set
     */
    public void setPackageImg(String packageImg) {
        this.packageImg = packageImg;
    }

    /**
     * @return the logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        String ListInfo = "\n\n\nListas:\n"
                + "-YellowCards:" + this.getYellowCards().toString() + "\n\n"
                + "-GreenCards:" + this.getGreenCards().toString() + "\n\n"
                + "-RedCards:" + this.getRedCards().toString() + "\n\n";

        String queueInfo = "\n\nColas:\n"
                + "Prioridad 1: " + this.getColaP1().toString() + "\n\n"
                + "Prioridad 2: " + this.getColaP2().toString() + "\n\n"
                + "Prioridad 3: " + this.getColaP3().toString() + "\n\n"
                + "Prioridad 4: " + this.getColaRefuerzo().toString() + "\n\n";

        return ListInfo + queueInfo;
    }

}