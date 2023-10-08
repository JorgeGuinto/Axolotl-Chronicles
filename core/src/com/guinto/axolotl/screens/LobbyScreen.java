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

    private final AxolotlChronicles game;
    public LobbyRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;
    private float x;

    public LobbyScreen(AxolotlChronicles game, float x) {
        Assets.loadLobby();
        this.game = game;
        this.x = x;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        game.guiCam.position.set(3000, game.guiCam.position.y, 0);
        renderer = new LobbyRenderer(game, stage);
        System.out.println("hi");
    }

    public void update() {
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
                x = x - 2000;
                game.guiCam.position.add(-2000, 0, 0);
                renderer.popCharacters();
            }
        } else if (touchPercentageX > 0.8f) {
            if (game.guiCam.position.x < 5000) {
                x = x + 2000;
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

//        game.guiCam.update();
        game.batch.setProjectionMatrix(game.guiCam.combined);
        renderer.render(delta);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        // ESTA PARTE NO ME GUSTA QUE ESTÉ EN EL RENDER,
        // HAY ALGO ENTRE EL FINAL DEL CONSTRUCTOR Y EL RENDER
        // QUE REGRESA MI GUICAM A 1000 CUANDO DEBERÍA DE SER 3000 PERO NO ENTIENDO QUÉ
        // DEBUGGEANDO TERMINA EL CONSTRUCTOR Y SÍ ESTÁ EN 3K PERO EMPIEZA EL RENDER Y ESTÁ NUEVAMENTE EN 1K
        // ALGO DEL CÓDIGO LO ESTÁ DEVOLVIENDO

        if (x != game.guiCam.position.x) game.guiCam.position.set(x, game.guiCam.position.y, 0);
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