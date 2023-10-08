package com.guinto.axolotl.resources;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guinto.axolotl.assets.Assets;

import lombok.Getter;
import lombok.Setter;

public class BackgroundActor extends Actor {

//    @Getter
//    @Setter
//    private int positionX, positionY;

//    public BackgroundActor(int positionX, int positionY) {
//        this.positionX = positionX;
//        this.positionY = positionY;
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.backgroundRegion, 0, 0);
    }
}