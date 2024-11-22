/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import EDD.LinkedList;
import EDD.Node;
import EDD.Queue;
import GUIClases.ControlMainUI;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andresbucarello
 */
public class Administrador extends Thread {
    
    private IA ia;
    private final Semaphore semaforo;
    private final Franquicia wars;
    private final Franquicia trek;
    private int round = 0;

    public Administrador(IA ia, Semaphore semaforo, LinkedList yellowCards1, LinkedList greenCards1, LinkedList redCards1,
            LinkedList yellowCards2, LinkedList greenCards2, LinkedList redCards2) {

        this.ia = ia;
        this.semaforo = semaforo;
        this.wars = new Franquicia("Wars", "/GUI/Assets/Wars",
                yellowCards1, greenCards1, redCards1);
        this.trek = new Franquicia("Trek", "/GUI/Assets/Trek",
                yellowCards2, greenCards2, redCards2);
    }

    public void iniciarSimulacion() {
        
//        ControlMainUI.getHome().setVisible(true);

        for (int i = 0; i < 20; i++) {
            getWars().crearPersonaje();
            getTrek().crearPersonaje();
        }

        ControlMainUI.getHome().getFranquiciaUI1().actualizarColasUI(getWars().getColaP1(),
                getWars().getColaP2(),
                getWars().getColaP3(),
                getWars().getColaRefuerzo()
        );

        ControlMainUI.getHome().getFranquiciaUI2().actualizarColasUI(getTrek().getColaP1(),
                getTrek().getColaP2(),
                getTrek().getColaP3(),
                getTrek().getColaRefuerzo()
        );

        ControlMainUI.getHome().setVisible(true);

        try {
            semaforo.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.start();
        this.getIa().start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int duracion = ControlMainUI.getHome().getDuracion().getValue();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                ia.setTime(duracion);
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();

                actualizarColaRefuerzo(this.wars);
                actualizarColaRefuerzo(this.trek);
                
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();

                if (round == 2) {
                    intentarCrearPersonaje();
                    round = 0;
                }

                Personaje peleadorWars = elegirPeleador(this.getWars());
                Personaje peleadorTrek = elegirPeleador(this.getTrek());

                //------------------
                //TODO: Pasarle los fighters a la IA
                // Aca 0j0
                //------------------
                this.getIa().setPeleadorWars(peleadorWars);
                this.getIa().setPeleadorTrek(peleadorTrek);
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                actualizarColasUI();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                semaforo.release();
                Thread.sleep(100);
                semaforo.acquire();

                this.round += 1;
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                
                intentarAumentarPrioridad(this.getWars());
                intentarAumentarPrioridad(this.getTrek());
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                actualizarColasUI();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();

            } catch (InterruptedException ex) {
                Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void intentarAumentarPrioridad(Franquicia franquicia) {
        aumentarPrioridad(franquicia.getColaP2(), franquicia.getColaP1());
        aumentarPrioridad(franquicia.getColaP3(), franquicia.getColaP2());
    }

    private void aumentarPrioridad(Queue actualPrioridad, Queue siguientePrioridad) {
        int len = actualPrioridad.getLength();

        for (int i = 0; i < len; i++) {
            Personaje personaje = actualPrioridad.dequeue();
            personaje.setContadorInanicion(personaje.getContadorInanicion() + 1);

            if (personaje.getContadorInanicion() >= 8) {
                personaje.setContadorInanicion(0);
                siguientePrioridad.enqueue(personaje);
            } else {
                actualPrioridad.enqueue(personaje);
            }
        }
    }

    private Personaje elegirPeleador(Franquicia franquicia) {
        if (franquicia.getColaP1().isEmpty()) {
            franquicia.actualizarColaP1();
            this.actualizarColasUI();
        }
        Node nodo = franquicia.getColaP1().getFront();
        int index = franquicia.getColaP1().getLength();
        Personaje peleador=nodo.getTInfo();
        
        if (peleador.getNivelPrioridad()==1) {
            franquicia.getColaP1().dequeueAtPosition(0);
            peleador.setContadorInanicion(0);
            return peleador;
        }
        boolean cambio = false;
        
        System.out.println("INDICEEE");
        System.out.println(index);
        int indice=0;
        for (int i = 0; i < index; i++) {
            if (nodo.getTInfo().getNivelPrioridad()<peleador.getNivelPrioridad()) {
                cambio=true;
                peleador=nodo.getTInfo();
                indice=i;
                if (peleador.getNivelPrioridad()==1) {
                    franquicia.getColaP1().dequeueAtPosition(i);
                    peleador.setContadorInanicion(0);
                    return peleador;
                }
            }
            nodo=nodo.getNextNode();
        }
        franquicia.getColaP1().dequeueAtPosition(indice);
        peleador.setContadorInanicion(0);
        return peleador;
    }

    public void actualizarColasUI() {
        ControlMainUI.actualizarColasUI("wars",
                this.getWars().getColaP1(),
                this.getWars().getColaP2(),
                this.getWars().getColaP3(),
                this.getWars().getColaRefuerzo());
        ControlMainUI.actualizarColasUI("trek",
                this.getTrek().getColaP1(),
                this.getTrek().getColaP2(),
                this.getTrek().getColaP3(),
                this.getTrek().getColaRefuerzo());
    }

    private void actualizarColaRefuerzo(Franquicia franquicia) {
        if (!(franquicia.getColaRefuerzo().isEmpty())) {
            double randomNum = Math.random();

            if (randomNum <= 0.4) {
                Personaje personaje = franquicia.getColaRefuerzo().dequeue();
                personaje.setContadorInanicion(0);
                franquicia.getColaP1().enqueue(personaje);
            }else {
            Personaje personaje = franquicia.getColaRefuerzo().dequeue();
            franquicia.getColaRefuerzo().enqueue(personaje);
            }
        }
    }

    private void intentarCrearPersonaje() {
        double randomNum = Math.random();

        if (randomNum <= 0.8) {
            System.out.println("OCURRIO");
            getWars().crearPersonaje();
            getTrek().crearPersonaje();
        }
    }

    /**
     * @return the wars
     */
    public Franquicia getWars() {
        return wars;
    }

    /**
     * @return the trek
     */
    public Franquicia getTrek() {
        return trek;
    }

    /**
     * @return the ia
     */
    public IA getIa() {
        return ia;
    }

    /**
     * @param ia the ia to set
     */
    public void setIa(IA ia) {
        this.ia = ia;
    }

}