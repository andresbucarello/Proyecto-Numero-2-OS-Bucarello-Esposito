/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helpers;

import EDD.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import Clases.Personaje;

public class FileFunctions {

    // Definimos arrays para cada categoría
    private LinkedList yellowRegularShow = new LinkedList();
    private LinkedList greenRegularShow = new LinkedList();
    private LinkedList redRegularShow = new LinkedList();

    private LinkedList yellowAvatar = new LinkedList();
    private LinkedList greenAvatar = new LinkedList();
    private LinkedList redAvatar = new LinkedList();

    public void read(File file) {
        String line;

        // Rellenar los arrays con las instancias de Personaje
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String currentShow = null;

            while ((line = br.readLine()) != null) {
                if (!(line.isEmpty())) {
                    if (line.startsWith("[")) {
                        currentShow = line.substring(1, line.length() - 1);

                    } else {
                        String[] parts = line.split(",");

                        Personaje character = new Personaje(
                                "",
                                parts[0],
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                parts[5].split(";")[0]
                        );
                        
 
                        // Clasificar el personaje en la linkedList correspondiente
                        if (line.contains("legendario.png") && "Wars".equals(currentShow)) {
                            character.setNivelPrioridad(1);
                            this.getYellowRegularShow().addEnd(character);
                        } else if (line.contains("epico.png") && "Wars".equals(currentShow)) {
                            character.setNivelPrioridad(2);
                            this.getGreenRegularShow().addEnd(character);
                        } else if (line.contains("raro.png") && "Wars".equals(currentShow)) {
                            character.setNivelPrioridad(3);
                            this.getRedRegularShow().addEnd(character);
                        } else if (line.contains("legendario.png") && "Trek".equals(currentShow)) {
                            character.setNivelPrioridad(1);
                            this.getYellowAvatar().addEnd(character);
                        } else if (line.contains("epico.png") && "Trek".equals(currentShow)) {
                            character.setNivelPrioridad(2);
                            this.getGreenAvatar().addEnd(character);
                        } else if (line.contains("raro.png") && "Trek".equals(currentShow)) {
                            character.setNivelPrioridad(3);
                            this.getRedAvatar().addEnd(character);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the yellowRegularShow
     */
    public LinkedList getYellowRegularShow() {
        return yellowRegularShow;
    }

    /**
     * @param yellowRegularShow the yellowRegularShow to set
     */
    public void setYellowRegularShow(LinkedList yellowRegularShow) {
        this.yellowRegularShow = yellowRegularShow;
    }

    /**
     * @return the greenRegularShow
     */
    public LinkedList getGreenRegularShow() {
        return greenRegularShow;
    }

    /**
     * @param greenRegularShow the greenRegularShow to set
     */
    public void setGreenRegularShow(LinkedList greenRegularShow) {
        this.greenRegularShow = greenRegularShow;
    }

    /**
     * @return the redRegularShow
     */
    public LinkedList getRedRegularShow() {
        return redRegularShow;
    }

    /**
     * @param redRegularShow the redRegularShow to set
     */
    public void setRedRegularShow(LinkedList redRegularShow) {
        this.redRegularShow = redRegularShow;
    }

    /**
     * @return the yellowAvatar
     */
    public LinkedList getYellowAvatar() {
        return yellowAvatar;
    }

    /**
     * @param yellowAvatar the yellowAvatar to set
     */
    public void setYellowAvatar(LinkedList yellowAvatar) {
        this.yellowAvatar = yellowAvatar;
    }

    /**
     * @return the greenAvatar
     */
    public LinkedList getGreenAvatar() {
        return greenAvatar;
    }

    /**
     * @param greenAvatar the greenAvatar to set
     */
    public void setGreenAvatar(LinkedList greenAvatar) {
        this.greenAvatar = greenAvatar;
    }

    /**
     * @return the redAvatar
     */
    public LinkedList getRedAvatar() {
        return redAvatar;
    }

    /**
     * @param redAvatar the redAvatar to set
     */
    public void setRedAvatar(LinkedList redAvatar) {
        this.redAvatar = redAvatar;
    }

}
