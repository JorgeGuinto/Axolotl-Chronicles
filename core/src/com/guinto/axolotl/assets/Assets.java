package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonObject;
import com.guinto.axolotl.characters.Axolotl;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Assets {

    public static TextureAtlas atlas;
    public static TextureAtlas idleAtlas;
    public static TextureAtlas walkingAtlas;

    // == Lobby assets ==
    public static Texture lobbyBackground;
    public static TextureRegion lobbyBackgroundRegion;

    public static Texture buildingLeft;
    public static Texture buildingRight;
    public static Texture buildingCenter;
    public static TextureRegion buildingLRegion;
    public static TextureRegion buildingRRegion;
    public static TextureRegion buildingCRegion;




    // == Load Methods ==

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("final.txt"));
        loadLobby();
    }
    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadLobby () {
        idleAtlas = new TextureAtlas(Gdx.files.internal("charactersAtlas/idleAtlas25.txt"));
        walkingAtlas = new TextureAtlas(Gdx.files.internal("charactersAtlas/idleAtlas.txt"));

        lobbyBackground = loadTexture("data/forest.png");
//        lobbyBackgroundRegion = new TextureRegion(lobbyBackground, 0, 0,6000,1200);
        lobbyBackgroundRegion = new TextureRegion(lobbyBackground, 0, 0,6000,1200);
        buildingLeft = loadTexture("data/BuildingLeft.png");
        buildingRight = loadTexture("data/BuildingRight.png");
        buildingCenter = loadTexture("data/BuildingCenter.png");
        buildingLRegion = new TextureRegion(buildingLeft, 0, 0, 348, 244);
        buildingCRegion = new TextureRegion(buildingCenter, 0, 0, 920, 569);
        buildingRRegion = new TextureRegion(buildingRight, 0, 0, 900, 597);

    }

    // == Dispose Methods ==
    public void disposeLobby() {
        lobbyBackground.dispose();
    }
}
