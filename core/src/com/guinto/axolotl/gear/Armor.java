package com.guinto.axolotl.gear;

import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.InfoLoader;

import lombok.Getter;

public class Armor extends Equipment{
    private JsonObject armor;
    @Getter
    private int extraLife;
    @Getter
    private int extraDefense;
    public Armor(String code) {
        super(code);
        armor = InfoLoader.find(code, InfoLoader.armorsArray);

        if (armor != null) {
            this.extraLife = armor.get("extraLife").getAsInt();
            this.extraDefense = armor.get("extraDefense").getAsInt();
            loadFeatures(armor);
        }
    }
}
