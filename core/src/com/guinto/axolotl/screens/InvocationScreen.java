package com.guinto.axolotl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.renderers.ForgeRenderer;
import com.guinto.axolotl.renderers.InvocationRenderer;

public class InvocationScreen extends ScreenAdapter {
    private AxolotlChronicles game;
    public InvocationRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;
    public InvocationScreen(AxolotlChronicles game) {
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new InvocationRenderer(game);

    }

    public void update() {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.guiCam.unproject(touchPoint);
        if (Gdx.input.justTouched()) {
            if (renderer.door.contains(touchPoint.x, touchPoint.y)){
                game.setScreen(new LobbyScreen(game, 1000));
            }
        }
    }

    public void draw (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.guiCam.update();
        game.batch.setProjectionMatrix(game.guiCam.combined);
        renderer.render(delta);
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
        Assets.disposeInvocation();
        super.dispose();
    }
}