package com.guinto.axolotl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.AxolotlTextures;
import com.guinto.axolotl.assets.InfoLoader;
import com.guinto.axolotl.screens.LobbyScreen;
import com.guinto.axolotl.user.User;

public class AxolotlChronicles extends Game {
	public SpriteBatch batch;
	public User user;
	public Viewport viewport;
	@Override
	public void create () {
//		viewport = new FitViewport(2000, 1125);
		Assets.loadLobby();
		InfoLoader.loadFile();

		batch = new SpriteBatch();
		user = new User("Jorge Test", "MX001");
		AxolotlTextures.loadLobby(user);

		setScreen(new LobbyScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
