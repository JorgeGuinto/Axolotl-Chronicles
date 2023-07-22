package com.guinto.axolotl.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonObject;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.user.User;

import java.util.HashMap;
import java.util.Map;

public class AxolotlTextures {

    private static Map<String, Animation<TextureRegion>> idleAnimations = new HashMap<>();
    private static Map<String, Animation<TextureRegion>> walkingAnimations = new HashMap<>();
    private static Map<String, Animation<TextureRegion>> runningAnimations = new HashMap<>();
    private static Map<String, Animation<TextureRegion>> hittingAnimations = new HashMap<>();
    private static Map<String, Animation<TextureRegion>> dyingAnimations = new HashMap<>();
    public static void loadLobby(User user) {
        TextureRegion walking;
        TextureRegion idle;

        for (Axolotl axolotl : user.unlockedCharacters) {
            String code = axolotl.getCODE();
            walking = loadTexture(code, "Walking");
            idle = loadTexture(code, "Idle");
            idleAnimations.put(code, getCharacterAnimation(idle, "I", code));
            walkingAnimations.put(code, getCharacterAnimation(walking, "W", code));
        }
    }


    private static TextureRegion loadTexture(String code, String state) {
        return Assets.atlas.findRegion(code + state);
    }

    private static Animation<TextureRegion> getCharacterAnimation(TextureRegion textureRegion, String state, String code) {
        JsonObject characterFound = InfoLoader.findCharacter(code);

        TextureRegion [][] temp = textureRegion.split(textureRegion.getRegionWidth() / characterFound.get("frames" + state + "H").getAsInt(), textureRegion.getRegionHeight()/ characterFound.get("frames" + state + "V").getAsInt());
        TextureRegion[] frames = new TextureRegion[temp.length * temp[0].length];
        int index = 0;
        for (TextureRegion[] textureRegions : temp) {
            for (TextureRegion textureRegion1 : textureRegions) {
                frames[index++] = textureRegion1;
            }
        }
        return new Animation<>(0.1f, frames);
    }

    public static TextureRegion getKeyFrame(float duration, boolean looping, int state, Axolotl axolotl) {
        switch (state){
            case 0:
                return idleAnimations.get(axolotl.getCODE()).getKeyFrame(duration, looping);
            case 1:
                return walkingAnimations.get(axolotl.getCODE()).getKeyFrame(duration, looping);

        }
        return null;
    }


}
