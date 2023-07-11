package com.guinto.axolotl.user;

import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.characters.Axolotl;

import java.util.ArrayList;

public class User {
    private final String name;
    private final String code;
    public ArrayList <Axolotl> unlockedCharacters;

    public User(String name, String code) {
        this.name = name;
        this.code = code;

        unlockedCharacters = new ArrayList<>();
        createCharacters();
    }

    public void createCharacters() {
//        Vector2 velocity = new Vector2(10, 10);
//        Axolotl testOne = new Axolotl(velocity, "001walking", true, 1, 1, 1, 1, 1, 1);
//        Axolotl testTwo = new Axolotl(velocity, "002running", true, 1, 1, 1, 1, 1, 1);
//        Axolotl testThree = new Axolotl(velocity, "003running", true, 1, 1, 1, 1, 1, 1);
        Axolotl testOne = new Axolotl("001walking");
        Axolotl testTwo = new Axolotl("002running");
        Axolotl testThree = new Axolotl("003running");

        testOne.setPosition(50,50);
        testTwo.setPosition(500,50);
        testThree.setPosition(1000,50);

        unlockedCharacters.add(testOne);
        unlockedCharacters.add(testTwo);
        unlockedCharacters.add(testThree);
    }
}
