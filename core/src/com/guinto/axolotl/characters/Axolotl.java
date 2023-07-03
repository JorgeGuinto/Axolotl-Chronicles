package com.guinto.axolotl.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guinto.axolotl.assets.Assets;

public class Axolotl extends Actor {
    // == Dynamic fields ==
    private Vector2 velocity; // Fija? Creo que ni siquiera tiene que ser vector, ya que
//    public Vector2 acceleration;?

    // == Classification fields ==
    private final String CODE;
    private final String NAME = "test";
    private final String GROUP = "testGroup";
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
    private final int STATE_WALK = 1;
    private final int STATE_RUN = 2;
    private final int STATE_ATTACK = 3;
    private final int STATE_HIT = 4;

    // == Constructor ==
    public Axolotl(String CODE) {
        this.CODE = CODE;
        //Get all the fields from the json file with the code
    }

    public Axolotl(Vector2 velocity, String CODE, boolean owned, int life, int ability, int defense, int damage, int attackSpeed, int rechargeAbilitySpeed) {
        this.velocity = velocity;
        this.CODE = CODE;
        this.owned = owned;
        this.life = life;
        this.ability = ability;
        this.defense = defense;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.rechargeAbilitySpeed = rechargeAbilitySpeed;
    }

    // == Private Methods ==
    // == Public Methods ==
    // == Override Methods ==
    @Override
    public void draw(Batch batch, float parentAlpha) {

        // Tal vez en el Assets crear la lista de los personajes aleatorios

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
}