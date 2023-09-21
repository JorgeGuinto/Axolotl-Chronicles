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
    private Table container = new Table();
    private Table details = new Table(Assets.skin);
    private boolean visibleTable, showDetails = false;
    private Image picture = new Image();
    private Label name = new Label("Name", Assets.skin, "button-d", Color.BLACK);
    private TextArea description = new TextArea("This is the description Area", Assets.skin);
    private TextArea characteristics = new TextArea("This is the \n Characteristics Area", Assets.skin);


    public TestScreen(AxolotlChronicles game) {
        this.game = game;
        touchPoint = new Vector3();
        stage = new Stage(game.viewport, game.batch);
        renderer = new TestRenderer(game);
        Gdx.input.setInputProcessor(stage);
    }

    public void update() {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.guiCam.unproject(touchPoint);
        if (Gdx.input.justTouched()) {
            if (renderer.door.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new LobbyScreen(game, 3000));
            } else if(touchPoint.x < 500 & touchPoint.y > 500) {
                container.setVisible(!visibleTable);
                visibleTable = !visibleTable;
                details.setVisible(false);
            }
        }
    }

    private void initTable() {
        details.setVisible(false);
        details.setSize(700, 900);
        details.setPosition(150, 112);
        details.setDebug(true);
        details.add(new Label("Details", Assets.skin, "button-d", Color.BLACK))
                .expandX()
                .fillX()
                .height(100)
                .top()
                .left()
                .colspan(2)
                .row();
        details.add(picture)
                .left()
                .width(300)
                .height(300);
        Table nameDesc = new Table(Assets.skin);
        nameDesc.add(name)
                .width(350)
                .height(100)
                .top()
                .left()
                .row();
        nameDesc.add(description).expandX()
                .fillX()
                .height(100)
                .top()
                .left()
                .row();
        details.add(nameDesc)
                .expandX()
                .fillX()
                .row();
        details.add(characteristics)
                .expandX()
                .fillX()
                .left()
                .height(400)
                .colspan(2)
                .row();
        stage.addActor(details);
    }
    private void updateTable(Armor armor) {
        name.setText(armor.getName());
        description.setText(armor.getName() + ", Group = " + armor.getGroup());
        characteristics.setText("Extra Life: " + armor.getExtraLife() +
                "\nExtra Defense: " + armor.getExtraDefense() +
                "\nExtra Attack Speed: " + armor.getExtraAttackSpeed() +
                "\nExtra Recharge Ability Speed: " + armor.getExtraRechargeAbilitySpeed());
        picture.setDrawable(new TextureRegionDrawable(armor.getEquipmentRegion()));
    }

    private void initScrollPane() {
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, Assets.skin);

        container.setSize(700, 900);
        container.setPosition(1150, 112);
        scrollPane.layout();

        for (final Armor armor : game.user.unlockedArmors){
            final ImageTextButton item = new ImageTextButton(armor.getGroup() + ": " + armor.getName(), Assets.skin);
            ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(Assets.skin.get("default", ImageTextButton.ImageTextButtonStyle.class));
            Drawable imageUp = new TextureRegionDrawable(armor.getEquipmentRegion());
            buttonStyle.imageUp = imageUp;
            buttonStyle.imageDown = imageUp;
            buttonStyle.font = Assets.skin.getFont("button-d");
            item.setStyle(buttonStyle);

            item.left();
            item.getImageCell().width(100);
            item.getLabelCell().expandX();

            item.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateTable(armor);
                    details.setVisible(true);
                }
            });
            item.setDebug(true);
            table.add(item).expandX().fillX().width(500).height(100).pad(10).row();
        }

        container.add(scrollPane).width(600).height(850);
        container.setDebug(true);
        stage.addActor(container);
        container.setVisible(false);
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
        initTable();
        initScrollPane();
    }

    @Override
    public void render (float delta) {
        update();
        stage.act();
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