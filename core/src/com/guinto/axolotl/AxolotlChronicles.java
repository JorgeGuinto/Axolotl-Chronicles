package com.guinto.axolotl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.AxolotlTextures;
import com.guinto.axolotl.assets.InfoLoader;
import com.guinto.axolotl.screens.LobbyScreen;
import com.guinto.axolotl.user.User;

public class AxolotlChronicles extends Game {
	public SpriteBatch batch;
	public User user;
	@Override
	public void create () {
		InfoLoader.loadFile();
		Assets.load();

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
