package com.gloria.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gloria.game.screens.PlayScreen;

public class Maria extends Game {
	public static final int V_WIDTH = 700;
	public static final int V_HEIGHT = 500;
	public SpriteBatch batch;
	public static final float PPM = 64;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
