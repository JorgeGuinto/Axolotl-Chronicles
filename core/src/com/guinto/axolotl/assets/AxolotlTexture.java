package com.guinto.axolotl.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.InfoLoader;

public class AxolotlTexture {
    private final String CODE;
    private TextureRegion walking;
    private TextureRegion running;
    private TextureRegion idle;
    private TextureRegion attack;
    private TextureRegion ability;
    private TextureRegion hit;
    private TextureRegion dying;

    private Animation walkingAnimation;
    private Animation runningAnimation;
    private Animation idleAnimation;
    private Animation attackAnimation;
    private Animation abilityAnimation;
    private Animation hitAnimation;
    private Animation dyingAnimation;

    public AxolotlTexture(String code) {
        this.CODE = code;
        load("a");
    }

    public void load(String state) {
        loadTextures();
        loadAnimations();
    }
    private void loadTextures() {
        walking = Assets.atlas.findRegion(CODE + "Walking");
        running = Assets.atlas.findRegion(CODE + "Running");
        idle = Assets.atlas.findRegion(CODE + "Idle");
    }

    private void loadAnimations() {
        walkingAnimation = getCharacterAnimation(walking, "W");
        runningAnimation = getCharacterAnimation(running, "R");
        idleAnimation = getCharacterAnimation(idle, "I");
    }

    private Animation<TextureRegion> getCharacterAnimation (TextureRegion textureRegion, String state) {
        JsonObject characterFound = InfoLoader.findCharacter(CODE);

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

    public TextureRegion getKeyFrame(float duration, boolean looping, int state) {
        switch (state) {
            case 0:
                return (TextureRegion) idleAnimation.getKeyFrame(duration, looping);
            case 1:
                return (TextureRegion) walkingAnimation.getKeyFrame(duration, looping);
            case 2:
                return (TextureRegion) runningAnimation.getKeyFrame(duration, looping);

        }
        return null;
    }
}
