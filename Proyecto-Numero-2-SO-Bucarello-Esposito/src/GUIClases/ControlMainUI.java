/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIClases;

import EDD.Queue;

/**
 *
 * @author andresbucarello
 */
public class ControlMainUI {

    private static final Home home = new Home();

    public static Home getHome() {
        return home;
    }

    public static void actualizarColasUI(String franquicia, Queue colaP1, Queue colaP2, Queue colaP3, Queue colaRefuerzo) {
        if (franquicia.equalsIgnoreCase("wars")) {
            home.getFranquiciaUI1().actualizarColasUI(colaP1, colaP2, colaP3, colaRefuerzo);
        } else {
            home.getFranquiciaUI2().actualizarColasUI(colaP1, colaP2, colaP3, colaRefuerzo);
        }
    }

}
