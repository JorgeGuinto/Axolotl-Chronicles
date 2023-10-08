package com.guinto.axolotl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	public OrthographicCamera guiCam;
	@Override
	public void create () {
		setViewport();
		Assets.loadStart();
		InfoLoader.loadFiles();

		batch = new SpriteBatch();
		user = new User("Jorge Test", "MX001");
		AxolotlTextures.loadLobby(user);

		setScreen(new LobbyScreen(this, 3000));
//		setScreen(new TestScreen(this, 3000));
	}

	@Override
	public void render () {
		guiCam.update();
		super.render();
	}

	public void setViewport() {
		viewport = new FitViewport(2000, 1125);
		guiCam = new OrthographicCamera();
		viewport.setCamera(guiCam);
		guiCam.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
//		guiCam.position.set((viewport.getWorldWidth() / 2), viewport.getWorldHeight() / 2, 0);
		guiCam.update();
	}
}
