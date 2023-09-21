package com.guinto.axolotl.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.Building;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.gear.Armor;
import com.guinto.axolotl.gear.Equipment;
import com.guinto.axolotl.gear.Weapon;
import com.guinto.axolotl.resources.AxolotlComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class ForgeRenderer {

    private AxolotlChronicles game;
    private Stage stage;
    private static ArrayList<Axolotl> poppedCharacters;
    private static ArrayList<Axolotl> shownCharacters;
    public Rectangle door = new Rectangle(0, 0, 330, 1000);
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 5;
    int charactersToShow = 2;
    public Building workshop = new Building("workshopTable");
    public Building mannequin = new Building("mannequin");

    // Table
    private Table weaponContainer = new Table();
    private Table armorContainer = new Table();
    private Table details = new Table(Assets.skin);
    public boolean visibleTable = false;
    private Image picture = new Image();
    private Label name = new Label("Name", Assets.skin, "button-d", Color.BLACK);
    private TextArea description = new TextArea("This is the description Area", Assets.skin);
    private TextArea characteristics = new TextArea("This is the \n Characteristics Area", Assets.skin);

    public ForgeRenderer (AxolotlChronicles game, Stage stage) {
        this.game = game;
        this.stage = stage;
        stage.setDebugAll(true);

//        workshop.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Test");
//            }
//        });

//        stage.addActor(mannequin);
//        stage.addActor(workshop);
        Assets.loadForge();
        game.guiCam.position.set(1000, game.guiCam.position.y, 0);
        popCharacters();
    }

    public void render(float delta) {
        duration += delta;
        renderBackground();
        if (visibleTable) {
            mannequin.setVisible(false);
            workshop.setVisible(false);
//            mannequin.setTouchable(Touchable.disabled);
//            workshop.setTouchable(Touchable.disabled);
        } else {
            weaponContainer.setVisible(false);
            armorContainer.setVisible(false);
            details.setVisible(false);
            renderStaticObjects();
        }

        renderCharacters(delta);
    }

    private void renderBackground() {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundRegion, 0, 0);
        game.batch.end();
    }

    private void renderStaticObjects(){
        game.batch.enableBlending();
        game.batch.begin();
        workshop.draw(game.batch);
        mannequin.draw(game.batch);
        game.batch.end();
    }

    private void renderCharacters(float delta) {
        Random rand = new Random();
        if (timeSinceLastCharacter > characterDelay && currentCharacterIndex < charactersToShow){
            shownCharacters.add(poppedCharacters.get(currentCharacterIndex));
            currentCharacterIndex++;
            timeSinceLastCharacter = 0;
            characterDelay = rand.nextInt(5) + 4;
        }
        game.batch.enableBlending();
        game.batch.begin();
        Collections.sort(shownCharacters, new AxolotlComparator());
        for (Axolotl axolotl : shownCharacters) {
            axolotl.draw(game.batch, duration, game.guiCam.position.x);
        }
        game.batch.end();
        timeSinceLastCharacter += delta;
    }

    public void popCharacters() {
        shownCharacters = new ArrayList<>();
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            poppedCharacters = new ArrayList<>(game.user.unlockedCharacters);
            Collections.shuffle(poppedCharacters);
            charactersToShow = Math.min(charactersToShow, game.user.unlockedCharacters.size());
            poppedCharacters = new ArrayList<>(poppedCharacters.subList(0, charactersToShow));
            setOriginalPositions();
        }
    }

    public void setOriginalPositions() {
        currentCharacterIndex = 0;
        Random random = new Random();
        for (Axolotl axolotl : poppedCharacters) {
            axolotl.setPosition(game.guiCam.position.x - 1400, random.nextInt(300));
            axolotl.setDestination(new Vector2(axolotl.getX() + 1, axolotl.getY() + 1));
            axolotl.setWaitTimer(0);
        }
    }

    public void initTable() {
        details.setVisible(visibleTable);
        details.setSize(700, 900);
        details.setPosition(150, 112);
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
    private void updateTable(Equipment equipment) {
        name.setText(equipment.getName());
        description.setText(equipment.getName() + ", Group = " + equipment.getGroup());

        if (equipment instanceof Armor) {
            characteristics.setText("Extra Life: " + ((Armor) equipment).getExtraLife() +
                "\nExtra Defense: " + ((Armor) equipment).getExtraDefense() +
                    "\nExtra Attack Speed: " + equipment.getExtraAttackSpeed() +
                    "\nExtra Recharge Ability Speed: " + equipment.getExtraRechargeAbilitySpeed());
            picture.setDrawable(new TextureRegionDrawable(equipment.getEquipmentRegion()));
        } else {
            characteristics.setText("Extra Damage: " + ((Weapon) equipment).getExtraDamage() +
                "\nExtra Ability: " + ((Weapon) equipment).getExtraAbility() +
                    "\nExtra Attack Speed: " + equipment.getExtraAttackSpeed() +
                    "\nExtra Recharge Ability Speed: " + equipment.getExtraRechargeAbilitySpeed());
            picture.setDrawable(new TextureRegionDrawable(equipment.getEquipmentRegion()));
        }
    }

    public void initScrollPanes() {
        Table armorsTable = new Table();
        ScrollPane scrollPane = new ScrollPane(armorsTable, Assets.skin);

        armorContainer.setSize(700, 900);
        armorContainer.setPosition(1150, 112);
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
            armorsTable.add(item).expandX().fillX().width(500).height(100).pad(10).row();
        }

        armorContainer.add(scrollPane).width(600).height(850).row();

        TextButton closeButton = new TextButton("Close", Assets.skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                visibleTable = false;
                System.out.println("Closed table");
            }
        });

        armorContainer.add(closeButton).right().bottom();
        stage.addActor(armorContainer);
        armorContainer.setVisible(visibleTable);

        weaponContainer.setSize(700, 900);
        weaponContainer.setPosition(1150, 112);

        Table weaponsTable = new Table();
        ScrollPane weaponsScrollPane = new ScrollPane(weaponsTable, Assets.skin);
        weaponsScrollPane.layout();

        for (final Weapon weapon : game.user.unlockedWeapons){
            final ImageTextButton item = new ImageTextButton(weapon.getGroup() + ": " + weapon.getName(), Assets.skin);
            ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(Assets.skin.get("default", ImageTextButton.ImageTextButtonStyle.class));
            Drawable imageUp = new TextureRegionDrawable(weapon.getEquipmentRegion());
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
                    Vector2 localCoords = new Vector2(x, y);
                    item.localToStageCoordinates(localCoords);

                    updateTable(weapon);
                    details.setVisible(true);
                }
            });
            weaponsTable.add(item).expandX().fillX().width(500).height(100).pad(10).row();
        }

        weaponContainer.add(weaponsScrollPane).width(600).height(850).row();

        closeButton = new TextButton("Close", Assets.skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                visibleTable = false;
                System.out.println("Closed table");

            }
        });

        weaponContainer.add(closeButton).right().bottom();
        stage.addActor(weaponContainer);
        weaponContainer.setVisible(visibleTable);

    }
    public void showContainer(boolean b) {
        visibleTable = true;
        if (b) {
            weaponContainer.setVisible(true);
            armorContainer.setVisible(false);
        } else {
            armorContainer.setVisible(true);
            weaponContainer.setVisible(false);
        }
    }
}