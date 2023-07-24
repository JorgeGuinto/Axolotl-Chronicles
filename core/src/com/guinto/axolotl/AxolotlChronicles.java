package com.guinto.axolotl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
		Assets.loadLobby();
		InfoLoader.loadFile();

		batch = new SpriteBatch();
		user = new User("Jorge Test", "MX001");
		AxolotlTextures.loadLobby(user);

		setScreen(new LobbyScreen(this));
	}

	@Override
	public void render () {
		guiCam.update();
		super.render();
	}

	public void setViewport() {

		float targetAspectRatio = 16/9;
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		float screenAspectRatio = (float) screenWidth / screenHeight;
		float scaleFactor = screenAspectRatio / targetAspectRatio;
		float viewportWidth = 1600 * scaleFactor;
		float viewportHeight = 900 * scaleFactor;

		viewport = new FitViewport(2000, 1125);
		guiCam = new OrthographicCamera();
		guiCam.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
		guiCam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
		guiCam.update();
	}
}
