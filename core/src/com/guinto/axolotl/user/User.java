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
        Axolotl testOne = new Axolotl("001Axolotl");
        Axolotl testTwo = new Axolotl("002FutureKnight");
        Axolotl testThree = new Axolotl("004OriginalNinja");
        Axolotl A1 = new Axolotl("005HalfZombie");
        Axolotl A2 = new Axolotl("006Boy");
        Axolotl A3 = new Axolotl("007Dino");
        Axolotl A4 = new Axolotl("008Santa");
        Axolotl A5 = new Axolotl("009Cowgirl");
        Axolotl A6 = new Axolotl("010Knight");
        Axolotl A7 = new Axolotl("011Jack");
        Axolotl A8 = new Axolotl("012Ninja");
        Axolotl A9 = new Axolotl("013NinjaGirl");
        Axolotl A10 = new Axolotl("014Robot");
        Axolotl A11 = new Axolotl("015Cowboy");
        Axolotl A12 = new Axolotl("016Zombie");

        testOne.setPosition(50,50);
        testTwo.setPosition(250,50);
        testThree.setPosition(450,50);
        A1.setPosition(650, 50);
        A2.setPosition(850, 50);
        A3.setPosition(1050, 50);
        A4.setPosition(50, 250);
        A5.setPosition(250,250);
        A6.setPosition(450,250);
        A7.setPosition(650, 250);
        A8.setPosition(850, 250);
        A9.setPosition(1050, 250);
        A10.setPosition(50, 550);
        A11.setPosition(250,550);
        A12.setPosition(450,550);


        unlockedCharacters.add(testOne);
        unlockedCharacters.add(testTwo);
        unlockedCharacters.add(testThree);
        unlockedCharacters.add(A1);
        unlockedCharacters.add(A2);
        unlockedCharacters.add(A3);
        unlockedCharacters.add(A4);
        unlockedCharacters.add(A5);
        unlockedCharacters.add(A6);
        unlockedCharacters.add(A7);
        unlockedCharacters.add(A8);
        unlockedCharacters.add(A9);
        unlockedCharacters.add(A10);
        unlockedCharacters.add(A11);
        unlockedCharacters.add(A12);
    }
}
