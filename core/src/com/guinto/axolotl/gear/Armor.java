package com.guinto.axolotl.gear;

import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.InfoLoader;

public class Armor {
    private final String CODE;
    private String name;
    private String group;
    private int extraLife;
    private int extraDefense;
    private float extraAttackSpeed;
    private float extraRechargeAbilitySpeed;

    private JsonObject armor;

    public Armor(String code) {
        this.CODE = code;

        armor = InfoLoader.find(this.CODE, InfoLoader.armorsArray);

        if (armor != null) {
            this.name = armor.get("name").getAsString();
            this.group = armor.get("group").getAsString();
            this.extraLife = armor.get("extraLife").getAsInt();
            this.extraDefense = armor.get("extraDefense").getAsInt();
            this.extraAttackSpeed = armor.get("extraAttackSpeed").getAsFloat();
            this.extraRechargeAbilitySpeed = armor.get("extraRechargeAbilitySpeed").getAsFloat();
        }
    }

}
