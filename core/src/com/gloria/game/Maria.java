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
	public static final float PPM = 70;

	public static final short DEFAULT_BIT = 1;
	public static final short MARIA_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DESTROYED_BIT = 8;

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
