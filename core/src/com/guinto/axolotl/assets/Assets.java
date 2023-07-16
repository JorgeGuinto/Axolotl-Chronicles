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
        lobbyBackground = loadTexture("data/lobby.png");
        lobbyBackgroundRegion = new TextureRegion(lobbyBackground, 0, 0,6000,1200);
    }

    // == Dispose Methods ==
    public void disposeLobby() {
        lobbyBackground.dispose();
    }
}
