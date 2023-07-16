package com.guinto.axolotl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.CharacterLoader;
import com.guinto.axolotl.screens.LobbyScreen;
import com.guinto.axolotl.user.User;

public class AxolotlChronicles extends Game {
	public SpriteBatch batch;
	public User user;
	@Override
	public void create () {
		CharacterLoader.loadFile();

		batch = new SpriteBatch();
		user = new User("Jorge Test", "MX001");
		Assets.load();

		setScreen(new LobbyScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
