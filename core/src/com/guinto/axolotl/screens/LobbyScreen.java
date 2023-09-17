package com.guinto.axolotl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.renderers.LobbyRenderer;

public class LobbyScreen extends ScreenAdapter {

    private AxolotlChronicles game;
    public LobbyRenderer renderer;
    private Stage stage;
    private Vector3 touchPoint;
//    private TextButton menuButton;
    public LobbyScreen(AxolotlChronicles game, float x) {
        Assets.loadLobby();
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new LobbyRenderer(game);
        game.guiCam.position.set(x, game.guiCam.position.y, 0);
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.guiCam.unproject(touchPoint);
            if (!updateLobbyPosition()) {
                if (renderer.buildingTouch(touchPoint.x, touchPoint.y)) {
                    switch ((int) game.guiCam.position.x) {
                        case 1000:
                            game.setScreen(new InvocationScreen(game));
                            break;
                        case 3000:
                            game.setScreen(new TestScreen(game));
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

        stage.draw();

        game.batch.enableBlending();
        game.batch.begin();
        Assets.titleFont.draw(game.batch, "AXOLOTL CHRONICLES: THE LOST KINGDOM", game.guiCam.position.x - 700, 100 );
        game.batch.draw(Assets.shellsRegion, 690, 1070, 60,50);
        game.batch.draw(Assets.gemsRegion, 900, 1070, 40,40);
        game.batch.draw(Assets.obsidianRegion, 1110, 1070, 50,50);
        game.batch.end();

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        initInfo();
    }

    private void initInfo() {
        TextField back = new TextField("",Assets.skin, "default");
        back.setPosition(680,1065);
        back.setSize(640, 60);

        TextButton shells = new TextButton("" + game.user.getShells() , Assets.skin, "default");
        shells.setPosition(690, 1070);
        shells.setSize(200, 50);

        TextButton jades = new TextButton("" + game.user.getJades(), Assets.skin, "default");
        jades.setPosition(900, 1070);
        jades.setSize(200, 50);

        TextButton obsidian = new TextButton("" + game.user.getObsidianRocks(), Assets.skin, "default");
        obsidian.setPosition(1110, 1070);
        obsidian.setSize(200, 50);


        stage.addActor(back);
        stage.addActor(shells);
        stage.addActor(jades);
        stage.addActor(obsidian);
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