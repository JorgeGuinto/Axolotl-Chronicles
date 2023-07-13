package com.guinto.axolotl.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.CharacterLoader;

import java.util.Random;

import lombok.Data;

@Data
public class Axolotl extends Actor {
    // == Dynamic fields ==
    private Vector2 position = new Vector2();
    private Vector2 velocity; // Fija? Creo que ni siquiera tiene que ser vector, ya que es la misma en x y y
    private Vector2 destination = new Vector2();
    private int moveTimer = 0;
    private int waitTimer = 0;

    // == Classification fields ==
    private final String CODE;
    private String NAME;
    private String GROUP;
    private boolean owned = false;

    // == Game character fields ==
    private int life;
    private int ability;
    private int defense;
    private int damage;
    private int attackSpeed;
    private int rechargeAbilitySpeed;

//    private Armor armor;
//    private Weapon weapon:

    // == State fields ==
    private final int STATE_STILL = 0;
    public static final int STATE_WALK = 1;
    public static final int STATE_RUN = 2;
    public static final int STATE_ATTACK = 3;
    public static final int STATE_HIT = 4;
    private int state = 0;

    // == Information fields ==
    private JsonObject character;

    // == Constructor ==
    public Axolotl(String CODE) {
        this.CODE = CODE;
        this.velocity = new Vector2(10, 10);

        character = CharacterLoader.findCharacter(this.CODE);

        if (character != null) {
            this.NAME = character.get("name").getAsString();
            this.GROUP = character.get("group").getAsString();
            this.life = character.get("life").getAsInt();
            this.damage = character.get("damage").getAsInt();
            this.ability = character.get("ability").getAsInt();
            this.defense = character.get("defense").getAsInt();
            this.attackSpeed = character.get("attackSpeed").getAsInt();
            this.rechargeAbilitySpeed = character.get("rechargeAbilitySpeed").getAsInt();
        }
    }

    // == Private Methods ==
    private void walk() {
        if (waitTimer > 0) {
            waitTimer--;
        }
        if (waitTimer == 0) {
            float deltaX = (destination.x - position.x) / moveTimer;
            float deltaY = (destination.y - position.y) / moveTimer;
            position.x += deltaX;
            position.y += deltaY;
            setPosition(position.x, position.y);
            moveTimer--;
            if (moveTimer <= 0) {
                state = STATE_STILL;
                waitTimer = (int) ((3 + Math.random() * 7) * 60);
            }

        }
    }

    // == Public Methods ==
    public Animation getCharacterAnimation () {
        // Este método solía estar en Assets pero creo que es mejor que esté aquí
        TextureRegion textureRegion = null;
        switch (state) {
            case 0:
            case 1:
                textureRegion = Assets.atlas.findRegion(CODE + "Walking");
                break;
            case 2:
                textureRegion = Assets.atlas.findRegion(CODE + "Running");
                break;
        }

        JsonObject characterFound = CharacterLoader.findCharacter(CODE);

        TextureRegion [][] temp = textureRegion.split(textureRegion.getRegionWidth() / characterFound.get("framesWH").getAsInt(), textureRegion.getRegionHeight()/ characterFound.get("framesWV").getAsInt());
        TextureRegion[] frames = new TextureRegion[temp.length * temp[0].length];
        int index = 0;
        for (TextureRegion[] textureRegions : temp) {
            for (TextureRegion textureRegion1 : textureRegions) {
                frames[index++] = textureRegion1;
            }
        }

        return new Animation<>(0.1f, frames);
    }

    public void update(int newState) {
        if (newState == STATE_WALK) {
            if (state != STATE_WALK) {
                state = STATE_WALK;
                moveTimer = 0;
                Random rand = new Random();
                float targetX = rand.nextFloat() * Gdx.graphics.getWidth();
                float targetY = rand.nextFloat() * Gdx.graphics.getHeight() / 2;
                destination.set(targetX, targetY);
                float distance = this.position.dst(destination);
                float time = distance / 50;
                moveTimer = (int) (time * 60);
            }
            walk();
        }
    }

    // == Override Methods ==
    @Override
    public void draw(Batch batch, float parentAlpha) {
//        TextureRegion keyFrame = Assets.bobFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
//
//        TextureRegion robertRegion = new TextureRegion(Assets.items, 64, 128, 32, 32);
//        batch.draw(robertRegion, 150, 200);
//
//        float side = velocity.x < 0 ? -1 : 1;
//
//        if (side < 0) {
//            batch.draw(keyFrame, position.x + 0.5f, position.y - 0.5f);
//        } else {
//            batch.draw(keyFrame, position.x - 0.5f, position.y - 0.5f);
//        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        this.position.set(x, y);
        super.setPosition(x, y);
    }
}