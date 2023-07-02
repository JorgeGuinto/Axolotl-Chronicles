package com.guinto.axolotl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.screens.LobbyScreen;

public class AxolotlChronicles extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.load();
		setScreen(new LobbyScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
