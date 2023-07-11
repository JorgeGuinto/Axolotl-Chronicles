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
    // == Lobby assets ==
    public static Texture lobbyBackground;
    public static TextureRegion lobbyBackgroundRegion;

    public static Texture character;
    public static Animation characterAnimation;


    // == Load Methods ==

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("final.txt"));
        loadLobby();
    }
    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadLobby () {
        lobbyBackground = loadTexture("data/testHome.png");
        lobbyBackgroundRegion = new TextureRegion(lobbyBackground, 0, 0,2000,1200);

        character = loadTexture("data/character.png");
        characterAnimation = new Animation(0.3f, new TextureRegion(character,0, 0, 370, 370), new TextureRegion(character,370, 0, 370, 370), new TextureRegion(character,740, 0, 370, 370), new TextureRegion(character,1110, 0, 370, 370), new TextureRegion(character,1480, 0, 370, 370));
    }

    // == Dispose Methods ==
    public void disposeLobby() {
        lobbyBackground.dispose();
        character.dispose();
    }
}
