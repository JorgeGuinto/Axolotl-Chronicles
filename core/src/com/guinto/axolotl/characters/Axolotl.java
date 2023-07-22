package com.guinto.axolotl.characters;

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
    private Vector2 position = new Vector2();
    @Getter
    @Setter
    private Vector2 destination = new Vector2();
    @Setter
    private int waitTimer = 0;
    private int moveTimer = 0, duration = 0;

    // == Classification fields ==
    private final String CODE;
    private String NAME, GROUP;
    private boolean owned = false;

    // == Game character fields ==
    private int life, ability, defense, damage, attackSpeed, rechargeAbilitySpeed;

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
//        this.axolotlTexture = new AxolotlTexture(CODE);

        character = InfoLoader.findCharacter(this.CODE);

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

    public void update(int newState) {
        if (newState == STATE_WALK) {
            if (state != STATE_WALK && waitTimer <= 0) {
                state = STATE_WALK;
                moveTimer = 0;
                Random rand = new Random();
                destination.set(rand.nextFloat() * (2000 - 200), rand.nextFloat() * 300);
//                (2000 - 200) es para que no se salga de la pantalla
//                El 2000 es el ancho de la imagen del fondo porque Gdx.graphics.getWidth(); no está funcionando me regeresa un número 640 pero no es t odo el ancho
                float distance = this.position.dst(destination);
                float time = distance / 70;
                moveTimer = (int) (time * 60);
                facing = position.x < destination.x;
            }
            walk();
        }
    }

    private void walk() {
        Random rand = new Random();
        if (waitTimer > 0) {
            waitTimer--;
        } else {
            position.x += (destination.x - position.x) / moveTimer;;
            position.y += (destination.y - position.y) / moveTimer;
            setPosition(position.x, position.y);
            moveTimer--;
            if (moveTimer <= 0) {
                state = STATE_STILL;
                waitTimer = rand.nextInt(20, 30) * 60;
            }

        }
    }

    public TextureRegion getKeyFrame(float duration, boolean looping) {
//        return axolotlTexture.getKeyFrame(duration, looping, state);
        return AxolotlTextures.getKeyFrame(duration, looping, state, this);
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