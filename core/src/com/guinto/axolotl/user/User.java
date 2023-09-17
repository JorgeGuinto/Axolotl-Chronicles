package com.guinto.axolotl.user;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.gear.Armor;
import com.guinto.axolotl.gear.Weapon;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class User {
    private final String name;
    private final String code;
    public ArrayList <Axolotl> unlockedCharacters = new ArrayList<>();
    public ArrayList <Armor> unlockedArmors = new ArrayList<>();
    public ArrayList <Weapon> unlockedWeapons = new ArrayList<>();
    @Getter
    @Setter
    private int shells, jades, obsidianRocks;

    public User(String name, String code) {
        this.name = name;
        this.code = code;

        this.shells = 1000;
        this.jades = 50;
        this.obsidianRocks = 300;

        createCharacters();
        createWeapons();
        createArmors();
    }

    // These are temporary methods until I create the database and obtain data from it.
    public void createCharacters() {
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
        Axolotl A13 = new Axolotl("017Zombie");

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
        unlockedCharacters.add(A13);
    }

    public void createWeapons(){
        Weapon a1 = new Weapon("fds");
        Weapon b2 = new Weapon("hgfd");
        Weapon c3 = new Weapon("jyt");
        Weapon d4 = new Weapon("rd");
        Weapon e5 = new Weapon("fgby");

        unlockedWeapons.add(a1);
        unlockedWeapons.add(b2);
        unlockedWeapons.add(c3);
        unlockedWeapons.add(d4);
        unlockedWeapons.add(e5);
    }
    public void createArmors(){
        Armor a01;
        for (int i = 0; i < 9; i++) {
            a01 = new Armor("a0" + (i+1));
            unlockedArmors.add(a01);
            a01 = new Armor("b0" + (i+1));
            unlockedArmors.add(a01);
            a01 = new Armor("c0" + (i+1));
            unlockedArmors.add(a01);
        }
        a01 = new Armor("a10");
        unlockedArmors.add(a01);
        a01 = new Armor("b10");
        unlockedArmors.add(a01);
        a01 = new Armor("c10");
        unlockedArmors.add(a01);
    }
}