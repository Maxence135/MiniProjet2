/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author maxen
 */
public class ListeCartes {

    public static CarteDeplacement[] getToutesLesCartes() {

        CarteDeplacement[] cartes = new CarteDeplacement[]{
            new CarteDeplacement("Tiger", new int[][]{{-2, 0}, {1, 0}}),
            new CarteDeplacement("Dragon", new int[][]{{-1, -2}, {-1, 2}, {1, -1}, {1, 1}}),
            new CarteDeplacement("Frog", new int[][]{{0, -2}, {-1, -1}, {1, 1}}),
            new CarteDeplacement("Rabbit", new int[][]{{0, 2}, {-1, 1}, {1, -1}}),
            new CarteDeplacement("Crab", new int[][]{{0, -2}, {0, 2}, {-1, 0}}),
            new CarteDeplacement("Elephant", new int[][]{{0, -1}, {0, 1}, {-1, -1}, {-1, 1}}),
            new CarteDeplacement("Goose", new int[][]{{0, -1}, {0, 1}, {-1, -1}, {1, 1}}),
            new CarteDeplacement("Rooster", new int[][]{{0, -1}, {0, 1}, {1, -1}, {-1, 1}}),
            new CarteDeplacement("Monkey", new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}),
            new CarteDeplacement("Mantis", new int[][]{{-1, -1}, {-1, 1}, {1, 0}}),
            new CarteDeplacement("Horse", new int[][]{{0, -1}, {-1, 0}, {1, 0}}),
            new CarteDeplacement("Ox", new int[][]{{0, 1}, {-1, 0}, {1, 0}}),
            new CarteDeplacement("Boar", new int[][]{{0, -1}, {0, 1}, {-1, 0}}),
            new CarteDeplacement("Eel", new int[][]{{-1, 1}, {1, -1}, {0, -1}}),
            new CarteDeplacement("Cobra", new int[][]{{-1, -1}, {1, 1}, {0, 1}}),
            new CarteDeplacement("Phoenix", new int[][]{{-1, 0}, {1, -2}, {1, 2}})
        };

        return cartes;
    }
}
