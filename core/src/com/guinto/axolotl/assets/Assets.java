package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    // == Lobby assets ==
    public static Texture lobbyBackground;
    public static Texture character;
    public static Animation characterAnimation;

    public static TextureRegion lobbyBackgroundRegion;


    // == Load Methods ==

    public static void load() {
//        Aquí se cargará el Atlas
//        Y ya en los métodos específicos se cargarán las texturas
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
