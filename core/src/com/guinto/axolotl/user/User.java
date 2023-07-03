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
        Vector2 velocity = new Vector2(10, 10);
        Axolotl testOne = new Axolotl(velocity, "001", true, 1, 1, 1, 1, 1, 1);
        Axolotl testTwo = new Axolotl(velocity, "002", true, 1, 1, 1, 1, 1, 1);
        Axolotl testThree = new Axolotl(velocity, "003", true, 1, 1, 1, 1, 1, 1);
        unlockedCharacters.add(testOne);
        unlockedCharacters.add(testTwo);
        unlockedCharacters.add(testThree);
    }
}
