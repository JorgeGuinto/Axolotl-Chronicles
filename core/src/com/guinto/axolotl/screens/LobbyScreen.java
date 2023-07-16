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
    LobbyRenderer renderer;
    private OrthographicCamera guiCam;
    private float duration = 0;
    private Stage stage;
    private Viewport viewport;
    private Vector3 touchPoint;
    private boolean positionsSet = false;
    private int lobbyPositionX = -2000;

    public LobbyScreen(AxolotlChronicles game) {
        this.game = game;
        setViewport();
        touchPoint = new Vector3();
        stage = new Stage(viewport, game.batch);
        renderer = new LobbyRenderer(game);
    }

    public void setViewport() {
        float targetAspectRatio = 16f / 9f;
        int screenWidth = Gdx.graphics.getWidth();
        System.out.println("Ultimo intento = " + Gdx.graphics.getWidth());
//        System.out.println("EL otro es = " + Gdx.graphics.getDisplayMode.width());
        int screenHeight = Gdx.graphics.getHeight();
        float screenAspectRatio = (float) screenWidth / screenHeight;
        float scaleFactor = screenAspectRatio / targetAspectRatio;
        float viewportWidth = 1600f * scaleFactor;
        float viewportHeight = 900f * scaleFactor;

        viewport = new FitViewport(2000, 1125);
        guiCam = new OrthographicCamera();
        guiCam.position.set(lobbyPositionX + viewportWidth / 2, viewportHeight / 2, 0);
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            updateLobbyPosition();
        }
    }

    private void updateLobbyPosition() {
        float touchX = Gdx.input.getX();
        float screenWidth = Gdx.graphics.getWidth();
        float touchPercentageX = touchX / screenWidth;
        if (touchPercentageX < 0.3f) {
            switch (lobbyPositionX){
                case -2000:
                    lobbyPositionX = 0;
                    renderer.setOriginalPositions();
                    renderer.setCurrentCharacterIndex(0);
                    break;
                case -4000:
                    lobbyPositionX = - 2000;
                    renderer.setOriginalPositions();
                    renderer.setCurrentCharacterIndex(0);
                    break;
            }
        } else if (touchPercentageX > 0.7f) {
            switch (lobbyPositionX){
                case 0:
                    lobbyPositionX = - 2000;
                    renderer.setOriginalPositions();
                    renderer.setCurrentCharacterIndex(0);
                    break;
                case -2000:
                    lobbyPositionX = -4000;
                    renderer.setOriginalPositions();
                    renderer.setCurrentCharacterIndex(0);
                    break;
            }
        }
    }

    public void draw (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        renderer.render(delta, lobbyPositionX);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        guiCam.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
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
        viewport.update(width, height, true);
        guiCam.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
    }
}
