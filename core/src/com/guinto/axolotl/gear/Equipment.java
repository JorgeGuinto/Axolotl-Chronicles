package com.guinto.axolotl.gear;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.Assets;

import lombok.Data;

@Data
public class Equipment {
    private final String CODE;
    private String name;
    private String group;
    private float extraAttackSpeed;
    private float extraRechargeAbilitySpeed;
    private JsonObject equipment;
    private TextureRegion equipmentRegion;

    public Equipment(String code) {
        this.CODE = code;
    }

    private void loadRegion() {
        this.equipmentRegion = Assets.equipmentsAtlas.findRegion(getCODE());
    }

    public void loadFeatures(JsonObject equipment) {
        this.equipment = equipment;

        if (equipment != null) {
            setName(equipment.get("name").getAsString());
            this.group = equipment.get("group").getAsString();
            this.extraAttackSpeed = equipment.get("extraAttackSpeed").getAsFloat();
            this.extraRechargeAbilitySpeed = equipment.get("extraRechargeAbilitySpeed").getAsFloat();
            loadRegion();
        }
    }
}
