package com.guinto.axolotl.assets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Building {
    private String code;
    private TextureRegion buildingRegion;
    private Rectangle rectangle;
    private Polygon polygon;
    private int x;
    private int y;
    private int width;
    private int height;

    private JsonObject building;

    public Building(String code) {
        this.code = code;

        building = InfoLoader.findBuilding(this.code);

        if (building != null) {
            this.x = building.get("x").getAsInt();
            this.y = building.get("y").getAsInt();
            this.width = building.get("width").getAsInt();
            this.height = building.get("height").getAsInt();
            rectangle = new Rectangle(x, y, width, height);

            JsonArray verticesArray = building.getAsJsonArray("vertices");
            float[] vertices = new float[verticesArray.size()];
            for (int i = 0; i < verticesArray.size(); i++) {
                vertices[i] = verticesArray.get(i).getAsFloat();
            }

            polygon = new Polygon(vertices);
            polygon.setPosition(0,0);

            loadRegion();
        }
    }

    private void loadRegion() {
        this.buildingRegion = Assets.sceneObjectsAtlas.findRegion(code);
    }

    public void draw(Batch batch) {
        batch.draw(buildingRegion, x, y, width, height);
    }
}
