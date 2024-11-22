/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author user
 */
public class Personaje {

    private String id;
    private String nombre;
    private int salud;
    private int velocidad;
    private int agilidad;
    private String habilidad;
    private int nivelPrioridad;
    private String urlSource;
    private int counter = 0;
    private String status = "";

    public Personaje(
            String id,
            String nombre,
            int salud,
            int velocidad,
            int agilidad,
            String habilidad,
            String urlSource) {

        this.id = id;
        this.nombre = nombre;
        this.salud = salud;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.urlSource = urlSource;
        this.habilidad = habilidad;
    }

    public void getPromoted() {
        this.counter += 1;
    }

    public String toString() {
        return "Personaje{"
                + "id=" + getId()
                + ", salud=" + getSalud()
                + ", velocidad=" + getVelocidad()
                + ", agilidad=" + getAgilidad()
                + ", habilidad='" + getHabilidad() + '\''
                + ", nivelPrioridad=" + getNivelPrioridad()
                + ", urlSource='" + getUrlSource() + '\''
                + '}';
    }

    public void recibirDanio(int danio) {
        // Reducir los salud en la mitad del daño recibido
        this.salud -= (danio / 2);

        // Asegurarse de que los salud no caigan bajo cero
        if (this.salud < 0) {
            this.salud = 0;
        }
    }

    public void sanar(int puntosSalud) {
        // Incrementar los salud en la cantidad especificada
        this.salud += puntosSalud;

        int maxPS = 160;
        if (this.salud > maxPS) {
            this.salud = maxPS;
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the salud
     */
    public int getSalud() {
        return salud;
    }

    /**
     * @param salud the salud to set
     */
    public void setSalud(int salud) {
        this.salud = salud;
    }

    /**
     * @return the velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * @return the agilidad
     */
    public int getAgilidad() {
        return agilidad;
    }

    /**
     * @param agilidad the agilidad to set
     */
    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }

    /**
     * @return the habilidad
     */
    public String getHabilidad() {
        return habilidad;
    }

    /**
     * @param habilidad the habilidad to set
     */
    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    /**
     * @return the nivelPrioridad
     */
    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    /**
     * @param nivelPrioridad the nivelPrioridad to set
     */
    public void setNivelPrioridad(int nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }

    /**
     * @return the urlSource
     */
    public String getUrlSource() {
        return urlSource;
    }

    /**
     * @param urlSource the urlSource to set
     */
    public void setUrlSource(String urlSource) {
        this.urlSource = urlSource;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNameCharacter(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the counter
     */
    public int getContadorInanicion() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setContadorInanicion(int counter) {
        this.counter = counter;
    }

}
