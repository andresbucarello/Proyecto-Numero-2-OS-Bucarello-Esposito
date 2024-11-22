/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helpers;

import Clases.Administrador;
import Clases.IA;
import Main.App;

/**
 *
 * @author user
 */
public class Helpers {

    public final static String[] priority = {"Legendario", "Epico", "Raro"};

    public static void loadParams() {
        App app = App.getInstance();
        FileFunctions fileFunctions = new FileFunctions();
        fileFunctions.read(App.getSelectedFile());

        app.setIa(new IA());

        Administrador admin = new Administrador(
                app.getIa(),
                app.getMutex(),
                fileFunctions.getYellowRegularShow(), fileFunctions.getGreenRegularShow(), fileFunctions.getRedRegularShow(),
                fileFunctions.getYellowAvatar(), fileFunctions.getGreenAvatar(), fileFunctions.getRedAvatar());

        app.setAdmin(admin);
        app.getAdmin().getIa().setAdministrador(admin);
        app.getAdmin().iniciarSimulacion();

    }
}