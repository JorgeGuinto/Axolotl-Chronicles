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
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.characters.Axolotl;

public class LobbyScreen extends ScreenAdapter {

    private AxolotlChronicles game;
    private OrthographicCamera guiCam;
//    private LobbyRenderer renderer;
    private float duration = 0;
    private Stage stage;
    private Viewport viewport;
    private Vector3 touchPoint;

    public LobbyScreen(AxolotlChronicles game) {
        this.game = game;
        setViewport();
        touchPoint = new Vector3();
        stage = new Stage(viewport, game.batch);
        temp();
    }

    public void temp() {
        Axolotl axolotl = new Axolotl("ABC123");
        stage.addActor(axolotl);
    }
    public void setViewport() {
        float targetAspectRatio = 16f / 9f;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float screenAspectRatio = (float) screenWidth / screenHeight;
        float scaleFactor = screenAspectRatio / targetAspectRatio;
        float viewportWidth = 1600f * scaleFactor;
        float viewportHeight = 900f * scaleFactor;

        viewport = new FitViewport(2000, 1125);
        guiCam = new OrthographicCamera();
        guiCam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
    }

    public void update () {
//        if (Gdx.input.justTouched()) {
//            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//            if (exitBounds.contains(touchPoint.x, touchPoint.y)) {
//                Assets.playSound(Assets.clickSound);
//                game.setScreen(new MainMenuScreen(game));
//            }
//        }
    }

    public void draw (float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.disableBlending();

        game.batch.begin();
        game.batch.draw(Assets.lobbyBackgroundRegion, 0, 0);
        game.batch.end();

//        game.batch.enableBlending();
//        game.batch.begin();
//        glyphLayout.setText(Assets.font, "Test Screen");
//        Assets.font.draw(game.batch, glyphLayout, 160 - glyphLayout.width / 2, 480 - glyphLayout.height);
//        glyphLayout.setText(Assets.font, textField.getText());
//        Assets.font.draw(game.batch, glyphLayout, 160 - glyphLayout.width / 2, 480 - 100);
//        game.batch.draw(Assets.arrow, 320, 0, -64, 64);
//        game.batch.end();
//
//        stage.act(delta);
//        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        guiCam.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    @Override
    public void render (float delta) {
        draw(delta);
        update();
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
