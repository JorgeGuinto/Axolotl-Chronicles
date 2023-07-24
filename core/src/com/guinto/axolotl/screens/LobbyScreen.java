package com.guinto.axolotl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.renderers.LobbyRenderer;

public class LobbyScreen extends ScreenAdapter {

    private AxolotlChronicles game;
    public LobbyRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;
    private int lobbyPositionX = -2000;

    public LobbyScreen(AxolotlChronicles game) {
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new LobbyRenderer(game);
    }

    public void update() {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.guiCam.unproject(touchPoint);

        if (Gdx.input.justTouched()) {
            if (!updateLobbyPosition()) {
                if (renderer.buildingTouch(lobbyPositionX, touchPoint.x, touchPoint.y)) {
                    System.out.println("sí se tocó un edificio");
                    System.out.println(game.guiCam.position);
                }
            }
        }
    }

    private boolean updateLobbyPosition() {
        float touchX = Gdx.input.getX();
        float screenWidth = Gdx.graphics.getWidth();
        float touchPercentageX = touchX / screenWidth;

        if (touchPercentageX < 0.2f) {
            if (lobbyPositionX != 0) renderer.popCharacters();
            switch (lobbyPositionX) {
                case -2000:
//                    game.guiCam.position.set(-1000, game.viewport.getCamera().viewportHeight/ 2 , 0);
                    System.out.println(game.guiCam.position);
                    lobbyPositionX = 0;
                    return true;
                case -4000:
                    lobbyPositionX = -2000;
                    return true;
            }
        } else if (touchPercentageX > 0.8f) {
            if (lobbyPositionX != -4000) renderer.popCharacters();
            switch (lobbyPositionX) {
                case 0:
                    lobbyPositionX = -2000;
                    return true;
                case -2000:
                    lobbyPositionX = -4000;
                    return true;
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
        renderer.render(delta, lobbyPositionX);
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
    public void hide () {
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        game.guiCam.setToOrtho(false, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
    }
}
