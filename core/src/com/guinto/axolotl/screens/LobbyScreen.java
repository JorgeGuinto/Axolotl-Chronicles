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
    private OrthographicCamera guiCam;
    private Stage stage;
    private Viewport viewport;
    private Vector3 touchPoint;
    private int lobbyPositionX = -2000;

    public LobbyScreen(AxolotlChronicles game) {
        this.game = game;
        setViewport();
        touchPoint = new Vector3();
        stage = new Stage(viewport, game.batch);
        renderer = new LobbyRenderer(game);
    }

    public void setViewport() {

        float targetAspectRatio = 16/9;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float screenAspectRatio = (float) screenWidth / screenHeight;
        float scaleFactor = screenAspectRatio / targetAspectRatio;
        float viewportWidth = 1600 * scaleFactor;
        float viewportHeight = 900 * scaleFactor;



        viewport = new FitViewport(2000, 1125);
        guiCam = new OrthographicCamera();
        guiCam.position.set(lobbyPositionX + viewportWidth / 2, viewportHeight / 2, 0);
    }

    public void update() {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        guiCam.unproject(touchPoint);

        if (Gdx.input.justTouched()) {
            if (!updateLobbyPosition()) {
                System.out.println("GDX x = " + Gdx.input.getX() + " GDX Y = " + (Gdx.graphics.getWidth() - Gdx.input.getY()));
                System.out.println("Width  = " + Gdx.graphics.getWidth() + ", Height = " + Gdx.graphics.getHeight());

                System.out.println("transformed X = " + touchPoint.x + ", Y = " + touchPoint.y);
                System.out.println("Viewport Screen X = " + viewport.getScreenX() + ", Screen Y = " + viewport.getScreenY());
                System.out.println("Viewport WorldWidth = " + viewport.getWorldWidth() + ", WH = " + viewport.getWorldHeight());
                System.out.println("Viewport Screen w = " + viewport.getScreenWidth() + ", Screen H = " + viewport.getScreenHeight());
                if (renderer.buildingTouch(lobbyPositionX, touchPoint.x, touchPoint.y)) {
                    System.out.println("sí se tocó un edificio");
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
