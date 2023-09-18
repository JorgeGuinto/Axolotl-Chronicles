package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import lombok.Setter;

public class Assets {

    public static TextureAtlas idleAtlas;
    public static TextureAtlas walkingAtlas;
    public static TextureAtlas sceneObjectsAtlas;
    public static TextureAtlas equipmentsAtlas;
    public static Texture background;
    public static Texture gems;
    public static Texture obsidian;
    public static Texture shells;

    public static TextureRegion gemsRegion;
    public static TextureRegion obsidianRegion;
    public static TextureRegion shellsRegion;
    public static TextureRegion backgroundRegion;
    public static BitmapFont titleFont;
    public static BitmapFont lootFont;


    public static Skin skin = new Skin();

    @Setter
    private static boolean leaveLobby = true;

    // == Load Methods ==
    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadStart() {
//        initFonts();
        initSkin();
        idleAtlas = new TextureAtlas(Gdx.files.internal("atlas/idleAtlas.txt"));
        walkingAtlas = new TextureAtlas(Gdx.files.internal("atlas/walkingAtlas.txt"));
        sceneObjectsAtlas = new TextureAtlas(Gdx.files.internal("atlas/staticObjects.txt"));
        equipmentsAtlas = new TextureAtlas(Gdx.files.internal("atlas/equipment.txt"));

        gems = loadTexture("data/gems.png");
        obsidian = loadTexture("data/obsidian.png");
        shells = loadTexture("data/shells.png");
        gemsRegion = new TextureRegion(gems, 720, 720);
        obsidianRegion = new TextureRegion(obsidian, 1920, 1920);
        shellsRegion = new TextureRegion(shells, 348, 493);
    }

    public static void loadLobby () {
        background = loadTexture("data/forest.png");
        backgroundRegion = new TextureRegion(background, 0, 0,6000,1200);
    }

    public static void loadForge(){
        background = loadTexture("data/blacksmith.jpg");
        backgroundRegion = new TextureRegion(background, 0, 0,2000,1225);
    }

    public static void loadTest(){
        background = loadTexture("data/forest.png");
        backgroundRegion = new TextureRegion(background, 2000, 0,2000,1225);
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

    public static void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/fonts/eastTiger.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size =  75;
        params.color = Color.BLACK;
        titleFont = generator.generateFont(params);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/fonts/OdinsonLight.ttf"));
        params.size =  25;
        params.color = Color.BLACK;
        lootFont = generator.generateFont(params);

        skin.add("button-d", lootFont);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/fonts/marako.ttf"));
        params.size =  25;
        params.color = Color.BLACK;
        lootFont = generator.generateFont(params);
        skin.add("textField-d", lootFont);

    }

    public static void initSkin() {
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/flat-earth-ui.atlas"));
        skin.addRegions(skinAtlas);
        initFonts();
        skin.load(Gdx.files.internal("skin/flat-earth-ui.json"));

    }
}
