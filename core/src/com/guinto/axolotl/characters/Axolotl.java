package com.guinto.axolotl.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.AxolotlTextures;
import com.guinto.axolotl.assets.InfoLoader;

import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
public class Axolotl extends Actor {
    // == Dynamic fields ==
    private Vector2 destination = new Vector2();
    private int waitTimer = 0;
    private int moveTimer = 0;

    // == Classification fields ==
    private final String CODE;
    private String NAME, GROUP;

    // == Game character fields ==
    private int life, ability, defense, damage;
    private float attackSpeed, rechargeAbilitySpeed;

//    private Armor armor;
//    private Weapon weapon:

    // == State fields ==
    private final int STATE_STILL = 0;
    public static final int STATE_WALK = 1;
    public static final int STATE_RUN = 2;
    public static final int STATE_ATTACK = 3;
    public static final int STATE_HIT = 4;
    private int state = 0;
    public boolean facing;

    // == Information fields ==
    private JsonObject character;

    public Axolotl(String CODE) {
        this.CODE = CODE;
        character = InfoLoader.find(this.CODE, InfoLoader.charactersArray);

        if (character != null) {
            this.NAME = character.get("name").getAsString();
            this.GROUP = character.get("group").getAsString();
            this.life = character.get("life").getAsInt();
            this.damage = character.get("damage").getAsInt();
            this.ability = character.get("ability").getAsInt();
            this.defense = character.get("defense").getAsInt();
            this.attackSpeed = character.get("attackSpeed").getAsFloat();
            this.rechargeAbilitySpeed = character.get("rechargeAbilitySpeed").getAsFloat();
        }
    }

    public void update(int newState, float cameraX) {
        if (newState == STATE_WALK) {
            if (state != STATE_WALK && waitTimer <= 0) {
                state = STATE_WALK;
                moveTimer = 0;
                Random rand = new Random();
                destination.set((cameraX-1000) + (rand.nextFloat() * (2000 - 400)), rand.nextFloat() * 300);
                float distance = new Vector2(getX(), getY()).dst(destination);
                float time = distance / 70;
                moveTimer = (int) (time * 60);
                facing = getX() < destination.x;
            }
            walk();
        }
    }

    private void walk() {
        Random rand = new Random();
        if (waitTimer > 0) {
            waitTimer--;
        } else {
            setPosition(getX() + (destination.x - getX()) / moveTimer, getY() + (destination.y - getY()) / moveTimer);
            moveTimer--;
            if (moveTimer <= 0) {
                state = STATE_STILL;
                waitTimer = (rand.nextInt(10) + 20) * 60;
            }
        }
    }

    public TextureRegion getKeyFrame(float duration, boolean looping) {
        return AxolotlTextures.getKeyFrame(duration, looping, state, this);
    }

    public void draw(Batch batch, float parentAlpha, float cameraX) {
        TextureRegion temp = getKeyFrame(parentAlpha, true);
        int width = facing ? 200 : -200;
        batch.draw(temp, getX() + 200, getY(), width,200);
        update(Axolotl.STATE_WALK, cameraX);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}