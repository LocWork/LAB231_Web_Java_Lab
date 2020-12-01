/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.email;

import java.util.Random;

/**
 *
 * @author hi
 */
public class RandomGenerator {

    public RandomGenerator() {
    }

    public String randomID() {
        StringBuilder buildString = new StringBuilder();
        String alpha = "ABCDEFGHIJKLMNOBQRSTUVWSYZ123456789";
        String id;
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int x = rand.nextInt(35) + 1;
            buildString.append(alpha.charAt(x));
        }
        id = buildString.toString();

        return id;
    }
}
