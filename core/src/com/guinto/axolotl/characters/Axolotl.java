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
import java.util.jar.JarOutputStream;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Axolotl extends Actor {
    // == Dynamic fields ==
    private Vector2 position = new Vector2();
    private Vector2 velocity; // Fija? Creo que ni siquiera tiene que ser vector, ya que es la misma en x y y
    @Getter
    @Setter
    private Vector2 destination = new Vector2();
    private int moveTimer = 0;
    private int waitTimer = 0;
    private int duration = 0;

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
    private AxolotlTexture axolotlTexture;

    // == Constructor ==
    public Axolotl(String CODE) {
        this.CODE = CODE;
        this.velocity = new Vector2(10, 10);
        this.axolotlTexture = new AxolotlTexture(CODE);

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
                waitTimer = rand.nextInt(25, 35) * 60;
                System.out.println("en teoría deberíamos de estar en state = " + state);
            }

        }
    }

    // == Public Methods ==
    public Animation getCharacterAnimation () {
        TextureRegion textureRegion = null;
        switch (state) {
            case 0:
//                textureRegion = Assets.atlas.findRegion(CODE + "Idle");
//                break;
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
            if (state != STATE_WALK && waitTimer <= 0) {
                state = STATE_WALK;
                moveTimer = 0;
                Random rand = new Random();
                destination.set(rand.nextFloat() * (2000 - 200), rand.nextFloat() * 600);
//                (2000 - 200) es para que no se salga de la pantalla
//                El 2000 es el ancho de la imagen del fondo porque Gdx.graphics.getWidth(); no está funcionando me regeresa un número 640 pero no es t odo el ancho
                float distance = this.position.dst(destination);
                float time = distance / 50;
                moveTimer = (int) (time * 60);
            }
            walk();
        }
    }

    public TextureRegion getKeyFrame(float duration, boolean looping) {
        return axolotlTexture.getKeyFrame(duration, looping, state);
    }

    // == Override Methods ==
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