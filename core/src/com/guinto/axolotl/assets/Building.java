package com.guinto.axolotl.assets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Building extends Actor {
    private String code;
    private TextureRegion buildingRegion;
    public Polygon polygon;
    private int drawX;
    private int drawY;
    private int drawWidth;
    private int drawHeight;
    private JsonObject building;

    public Building(String code) {
        this.code = code;
        building = InfoLoader.find(this.code, InfoLoader.buildingsArray);

        if (building != null) {
            this.drawX = building.get("x").getAsInt();
            this.drawY = building.get("y").getAsInt();
            this.drawWidth = building.get("width").getAsInt();
            this.drawHeight = building.get("height").getAsInt();

            JsonArray verticesArray = building.getAsJsonArray("vertices");
            float[] vertices = new float[verticesArray.size()];
            for (int i = 0; i < verticesArray.size(); i++) {
                vertices[i] = verticesArray.get(i).getAsFloat();
            }
            polygon = new Polygon(vertices);
            polygon.setPosition(drawX + 200, drawY + 200);
            loadRegion();
            setBounds(drawX, drawY, drawWidth, drawHeight);
        }
    }

    private void loadRegion() {
        this.buildingRegion = Assets.sceneObjectsAtlas.findRegion(code);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(buildingRegion, drawX, drawY, drawWidth, drawHeight);
    }
    public void draw(Batch batch) {
//        batch.draw(buildingRegion, drawX, drawY, drawWidth, drawHeight);
    }
}