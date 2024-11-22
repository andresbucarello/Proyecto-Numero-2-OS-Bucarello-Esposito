/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import GUIClases.ControlMainUI;
import Helpers.ImageUtils;
import Main.App;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


/**
 *
 * @author 
 */
public class IA extends Thread {

    private Administrador administrador;
    private Personaje peleadorWars;
    private Personaje peleadorTrek;
    private int victoriasWars = 0;
    private int victoriasTrek = 0;

    private final Semaphore semaforo;

    private long duracion;
    private int round;

    public IA() {
        this.administrador = App.getApp().getAdmin();
        this.semaforo = App.getApp().getMutex();
        this.duracion = App.getApp().getDuracion();
        this.round = 0;
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
        ControlMainUI.getHome().getPeleadorWars().FightersLabels();
    }

    @Override
    public void run() {
        while (true) {
            try {
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                this.semaforo.acquire();
                

                this.round += 1;
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();

                ControlMainUI.getHome().getWinnerLabelID().setText("");
                ControlMainUI.getHome().getIaStatusLabel().setText("Determinando el resultado del combate...");
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                updateCardsUI(getPeleadorWars(), getPeleadorTrek());
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();

                ControlMainUI.getHome().getRoundLabel().setText("Round: " + String.valueOf(round));

                Thread.sleep((long) (this.getTime() * 1000 * 0.7));

                double aux = Math.random(); 

                if (aux <= 0.4) {
                    ControlMainUI.getHome().getIaStatusLabel().setText("¡Hay un ganador!");
                    Personaje ganador = getGanador(this.peleadorWars, this.peleadorTrek);
                    ControlMainUI.getHome().getWinnerLabelID().setText(ganador.getId());
                    ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                    ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));
                    ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                    ControlMainUI.getHome().getPeleadorWars().FightersLabels();

                } else if (aux > 0.40 && aux <= 0.67) {
                    ControlMainUI.getHome().getIaStatusLabel().setText("¡El combate termina en empate!");
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));
                    

                    this.getAdministrador().getWars().getColaP1().enqueue(this.peleadorWars);
                    this.getAdministrador().getTrek().getColaP1().enqueue(this.peleadorTrek);
                } else {
                    ControlMainUI.getHome().getIaStatusLabel().setText("El combate no se llevará a cabo.");
                    Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.6));

                    this.getAdministrador().getWars().getColaRefuerzo().enqueue(this.peleadorWars);
                    this.getAdministrador().getTrek().getColaRefuerzo().enqueue(this.peleadorTrek);
                }

                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                vaciarPeleadoresUI();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                Thread.sleep((long) ((getTime() * 1000 * 0.3) * 0.4));
                this.semaforo.release();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                Thread.sleep(100);
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();

            } catch (InterruptedException ex) {
                Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void vaciarPeleadoresUI() {
        ControlMainUI.getHome().getPeleadorWars().FightersLabels();
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
        ControlMainUI.getHome().getIaStatusLabel().setText("Esperando nuevos personajes...");
        ControlMainUI.getHome().getWinnerLabelID().setText("");
        ControlMainUI.getHome().getPeleadorWars().clearFightersLabels();
        ControlMainUI.getHome().getPeleadorTrek().clearFightersLabels();
        ControlMainUI.getHome().getPeleadorWars().FightersLabels();
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
    }

    private Personaje getGanador(Personaje peleadorWars, Personaje peleadorTrek) {
        long inicioPelea = System.currentTimeMillis();
        long finalPelea = inicioPelea + getTime() * 1000; // Convierte tiempo de combate a milisegundos
        boolean esFinalPelea = false;

        // Determina quién ataca primero basado en la velocidad inicialmente
        boolean esTurnoWars = peleadorWars.getVelocidad() >= peleadorTrek.getVelocidad();

        while (System.currentTimeMillis() < finalPelea && !esFinalPelea) {
            int danio;
            if (esTurnoWars) {
                // Wars ataca
                ControlMainUI.getHome().getPeleadorWars().getStatusLabel().setText("Enviando daño");
                ControlMainUI.getHome().getPeleadorTrek().getStatusLabel().setText("Recibiendo daño");
                danio = calcularDanio(peleadorWars, peleadorTrek);
                peleadorTrek.recibirDanio(danio);
                ControlMainUI.getHome().getPeleadorTrek().getHPLabel().setText(String.valueOf(peleadorTrek.getSalud()));
                if (peleadorTrek.getSalud() <= 0) esFinalPelea = true;
            } else {
                // Trek ataca
                ControlMainUI.getHome().getPeleadorTrek().getStatusLabel().setText("Enviando daño");
                ControlMainUI.getHome().getPeleadorWars().getStatusLabel().setText("Recibiendo daño");
                danio = calcularDanio(peleadorTrek, peleadorWars);
                peleadorWars.recibirDanio(danio);
                ControlMainUI.getHome().getPeleadorWars().getHPLabel().setText(String.valueOf(peleadorWars.getSalud()));
                if (peleadorWars.getSalud() <= 0) esFinalPelea = true;
            }

            // Alterna el turno para el próximo ciclo
            esTurnoWars = !esTurnoWars;
            ControlMainUI.getHome().getPeleadorTrek().getHPLabel().setText(String.valueOf(peleadorTrek.getSalud()));
            ControlMainUI.getHome().getPeleadorTrek().getjLabel5().setText("PersonajeID");
            ControlMainUI.getHome().getPeleadorWars().getjLabel5().setText("PersonajeID");
            ControlMainUI.getHome().getPeleadorWars().getHPLabel().setText(String.valueOf(peleadorWars.getSalud()));

            // Simula una pausa por ronda
            try {
                Thread.sleep(1000); // Ajusta según necesidad para permitir actualización de UI
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (esFinalPelea) break; // Salir del bucle si el combate terminó.
        }
        
        if (!esFinalPelea) {
        // Aquí se decide el ganador basado en quién tiene más HP.
            if (peleadorWars.getSalud() > peleadorTrek.getSalud()) {
                this.victoriasWars++;
                ControlMainUI.getHome().getFranquiciaUI1().getVictoriesLabel().setText(String.valueOf(this.victoriasWars));
                this.getAdministrador().getWars().getColaP1().enqueue(peleadorWars);
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                return peleadorWars;
            } else if (peleadorWars.getSalud() < peleadorTrek.getSalud()) {
                this.victoriasTrek++;
                ControlMainUI.getHome().getFranquiciaUI2().getVictoriesLabel().setText(String.valueOf(this.victoriasTrek));
                this.getAdministrador().getTrek().getColaP1().enqueue(peleadorTrek);
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                return peleadorTrek;
            } else {
                // En caso de empate por HP
                this.getAdministrador().getWars().getColaP1().enqueue(peleadorWars);
                this.getAdministrador().getTrek().getColaP1().enqueue(peleadorTrek);
                ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
                return peleadorTrek;
            }
        }

        // Determinar ganador basado en HP restante.
        if (peleadorWars.getSalud() > 0) {
            this.victoriasWars++;
            ControlMainUI.getHome().getFranquiciaUI1().getVictoriesLabel().setText(String.valueOf(this.victoriasWars));
            this.getAdministrador().getWars().getColaP1().enqueue(peleadorWars);
            ControlMainUI.getHome().getPeleadorWars().FightersLabels();
            ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
            return peleadorWars;
        } else if (peleadorTrek.getSalud() > 0) {
            this.victoriasTrek++;
            ControlMainUI.getHome().getFranquiciaUI2().getVictoriesLabel().setText(String.valueOf(this.victoriasTrek));
            this.getAdministrador().getTrek().getColaP1().enqueue(peleadorTrek);
            ControlMainUI.getHome().getPeleadorWars().FightersLabels();
            ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
            return peleadorTrek;
        } else {
            this.getAdministrador().getWars().getColaP1().enqueue(peleadorWars);
            this.getAdministrador().getTrek().getColaP1().enqueue(peleadorTrek);
            ControlMainUI.getHome().getPeleadorWars().FightersLabels();
                ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
            return null; // Manejo de empate
        }
    }


    private int calcularDanio(Personaje atacante, Personaje defensor) {
        // Daño base con la lógica que el ataque no puede ser completo porque sino lo matariamos de one.
         int danioBase = (atacante.getVelocidad() + (atacante.getAgilidad() / 2)) / 2;

         // Inicializamos el daño
         int danio = danioBase;

         switch (atacante.getHabilidad()) {
             case "Intensificación":
                 //INCREMENTE EL DAÑO BASE DE X1.5
                 danio *= 1.5;
                 break;
             case "Sanación":
                 // RECUPERA EN VIDA LA MITAD DE LO QUE LO ATACARÍA 
                 int healAmount = danioBase / 2;
                 atacante.sanar(healAmount);
                 danio = (atacante.getVelocidad() + (atacante.getAgilidad() / 2)) / 4;
                 break;
             case "Entorpecimiento":
                 // Se le disminuye la agilidad al enemigo en un 50%
                 defensor.setAgilidad(defensor.getAgilidad() / 2);
                 break;
             case "Ralentización":
                 // Se le disminuye la velocidad al enemigo en un 50%
                 defensor.setVelocidad(defensor.getVelocidad() / 2);
                 break;
             default:
                 // No special ability
                 break;
         }

         return danio;
     }

    private void updateCardsUI(Personaje personajeWars, Personaje personajeTrek) {
        ImageUtils imageUtils = new ImageUtils();

        ImageIcon cartaPersonajeWars = imageUtils.loadScaledImage(
                personajeWars.getUrlSource(), 150, 200
        );

        ImageIcon cartaPersonajeTrek = imageUtils.loadScaledImage(
                personajeTrek.getUrlSource(), 150, 200
        );

        ControlMainUI.getHome().getPeleadorWars().getFighterCard().setIcon(cartaPersonajeWars);
        ControlMainUI.getHome().getPeleadorWars().getCharacterIDLabel().setText(personajeWars.getId());
        ControlMainUI.getHome().getPeleadorWars().getHPLabel().setText(String.valueOf(personajeWars.getSalud()));
        ControlMainUI.getHome().getPeleadorWars().FightersLabels();
        

        ControlMainUI.getHome().getPeleadorTrek().getFighterCard().setIcon(cartaPersonajeTrek);
        ControlMainUI.getHome().getPeleadorTrek().getCharacterIDLabel().setText(personajeTrek.getId());
        ControlMainUI.getHome().getPeleadorTrek().getHPLabel().setText(String.valueOf(personajeTrek.getSalud()));
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
        
    }

    /**
     * @return the peleadorWars
     */
    public Personaje getPeleadorWars() {
        
        ControlMainUI.getHome().getPeleadorWars().FightersLabels();
        return peleadorWars;
    }

    /**
     * @param peleadorWars the peleadorWars to set
     */
    public void setPeleadorWars(Personaje peleadorWars) {
        ControlMainUI.getHome().getPeleadorWars().getjLabel5().setText("VINOTINTOOO");
        this.peleadorWars = peleadorWars;
    }

    /**
     * @return the peleadorTrek
     */
    public Personaje getPeleadorTrek() {
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
        return peleadorTrek;
    }

    /**
     * @param peleadorTrek the peleadorTrek to set
     */
    public void setPeleadorTrek(Personaje peleadorTrek) {
        ControlMainUI.getHome().getPeleadorTrek().FightersLabels();
        this.peleadorTrek = peleadorTrek;
    }

    /**
     * @return the administrador
     */
    public Administrador getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the duracion
     */
    public long getTime() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setTime(long duracion) {
        this.duracion = duracion;
    }

}
