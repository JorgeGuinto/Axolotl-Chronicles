package com.guinto.axolotl.gear;

import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.InfoLoader;

public class Weapon extends Equipment {
    private JsonObject weapon;
    private int extraDamage;
    private int extraAbility;
    public Weapon(String code) {
        super(code);
        weapon = InfoLoader.find(code, InfoLoader.weaponsArray);

        if (weapon != null) {
            this.extraDamage = weapon.get("extraDamage").getAsInt();
            this.extraAbility = weapon.get("extraAbility").getAsInt();
            loadFeatures(weapon);
        }
    }
}