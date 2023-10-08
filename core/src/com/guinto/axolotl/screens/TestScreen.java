package com.guinto.axolotl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.gear.Armor;
import com.guinto.axolotl.renderers.TestRenderer;

public class TestScreen extends ScreenAdapter {
    private AxolotlChronicles game;
    public TestRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;

    public TestScreen(AxolotlChronicles game, float x) {
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new TestRenderer(game, stage);
        game.guiCam.position.set(x, game.guiCam.position.y, 0);
        Gdx.input.setInputProcessor(stage);
    }

    public void update() {
//        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//        game.guiCam.unproject(touchPoint);
//        if (Gdx.input.justTouched()) {
//            if (renderer.door.contains(touchPoint.x, touchPoint.y)) {
//                game.setScreen(new LobbyScreen(game, 3000));
//            }
//        }
        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.guiCam.unproject(touchPoint);
            if (!updateLobbyPosition()) {

            }
        }
    }

    private boolean updateLobbyPosition() {
        float touchX = Gdx.input.getX();
        float screenWidth = Gdx.graphics.getWidth();
        float touchPercentageX = touchX / screenWidth;

        if (touchPercentageX < 0.2f) {
            if (game.guiCam.position.x > 1000) {
                game.guiCam.position.add(-2000, 0, 0);
                renderer.popCharacters();
            }
        } else if (touchPercentageX > 0.8f) {
            if (game.guiCam.position.x < 5000) {
                game.guiCam.position.add(2000, 0, 0);
                renderer.popCharacters();
            }
        }
        return false;
    }

    public void draw (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.guiCam.update();
        game.batch.setProjectionMatrix(game.guiCam.combined);
        renderer.render(delta);

        game.batch.enableBlending();
        game.batch.begin();
        Assets.titleFont.draw(game.batch, "TEST", game.guiCam.position.x - 700, 100 );
        game.batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void render (float delta) {
        update();
        draw(delta);
    }

    @Override
    public void resize(int width, int height) {game.viewport.update(width, height, true);}

    @Override
    public void dispose() {
        Assets.disposeForge();
        super.dispose();
    }
}