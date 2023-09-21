package com.guinto.axolotl.gear;

import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.InfoLoader;

import lombok.Getter;

public class Weapon extends Equipment {
    private JsonObject weapon;
    @Getter
    private int extraDamage;
    @Getter
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