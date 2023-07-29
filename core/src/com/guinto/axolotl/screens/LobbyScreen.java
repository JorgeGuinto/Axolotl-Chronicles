package com.guinto.axolotl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.renderers.LobbyRenderer;

public class LobbyScreen extends ScreenAdapter {

    private AxolotlChronicles game;
    public LobbyRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;
    public LobbyScreen(AxolotlChronicles game, float x) {
        Assets.loadLobby();
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new LobbyRenderer(game);
        game.guiCam.position.set(x, game.guiCam.position.y, 0);
    }

    public void update() {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.guiCam.unproject(touchPoint);

        if (Gdx.input.justTouched()) {
            if (!updateLobbyPosition()) {
                if (renderer.buildingTouch(touchPoint.x, touchPoint.y)) {
                    switch ((int) game.guiCam.position.x) {
                        case 1000:
                            game.setScreen(new InvocationScreen(game));
                            break;
                        case 3000:
                            break;
                        case 5000:
                            game.setScreen(new ForgeScreen(game));
                            break;
                    }
                }
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
        boolean mid = game.guiCam.position.x == 3000;
        Assets.disposeLobby(mid);
        super.dispose();
    }
}