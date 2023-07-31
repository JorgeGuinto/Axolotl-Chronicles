package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.Setter;

public class Assets {

    public static TextureAtlas idleAtlas;
    public static TextureAtlas walkingAtlas;
    public static TextureAtlas sceneObjectsAtlas;
    public static Texture background;
    public static TextureRegion backgroundRegion;
    @Setter
    private static boolean leaveLobby = true;

    // == Load Methods ==
    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadStart() {
        idleAtlas = new TextureAtlas(Gdx.files.internal("atlas/idleAtlas.txt"));
        walkingAtlas = new TextureAtlas(Gdx.files.internal("atlas/walkingAtlas.txt"));
        sceneObjectsAtlas = new TextureAtlas(Gdx.files.internal("atlas/sceneObjects.txt"));
    }

    public static void loadLobby () {
        background = loadTexture("data/forest.png");
        backgroundRegion = new TextureRegion(background, 0, 0,6000,1200);
    }

    public static void loadForge(){
        background = loadTexture("data/blacksmith.jpg");
        backgroundRegion = new TextureRegion(background, 0, 0,2000,1225);
    }
    public  static void loadInvocation() {
        background = loadTexture("data/blacksmith2.jpg");
        backgroundRegion = new TextureRegion(background, 0, 0,2000,1200);
    }

    // == Dispose Methods ==
    public static void disposeLobby(boolean deleteAll) {
        if (deleteAll) {
            idleAtlas.dispose();
            walkingAtlas.dispose();
            sceneObjectsAtlas.dispose();
        }
        background.dispose();
    }
    public static void disposeForge() {
        background.dispose();
    }
    public static void disposeInvocation() {
        background.dispose();
    }

    public void initFonts() {
//        FreeTypeFontGenerator generator = new FreeType
    }
}
