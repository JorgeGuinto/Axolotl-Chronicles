package com.guinto.axolotl.gear;

import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.InfoLoader;

public class Weapon {
    private final String CODE;
    private String name;
    private String group;
    private int extraDamage;
    private int extraAbility;
    private float extraAttackSpeed;
    private float extraRechargeAbilitySpeed;

    private JsonObject weapon;

    public Weapon(String code) {
        this.CODE = code;

        weapon = InfoLoader.find(this.CODE, InfoLoader.weaponsArray);

        if (weapon != null) {
            this.name = weapon.get("name").getAsString();
            this.group = weapon.get("group").getAsString();
            this.extraDamage = weapon.get("extraDamage").getAsInt();
            this.extraAbility = weapon.get("extraAbility").getAsInt();
            this.extraAttackSpeed = weapon.get("extraAttackSpeed").getAsFloat();
            this.extraRechargeAbilitySpeed = weapon.get("extraRechargeAbilitySpeed").getAsFloat();
        }
    }
}
