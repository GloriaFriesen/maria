package com.gloria.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gloria.game.Maria;

import javax.swing.text.TabExpander;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public GameOverScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(Maria.V_WIDTH, Maria.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Maria) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label whoopsLabel = new Label("Whoops.", font);
        Label outOfTime = new Label("You ran out of time.", font);
        Label playAgainLabel = new Label("Click to Play Again.", font);

        table.add(whoopsLabel).expandX();
        table.row();
        table.add(outOfTime).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(40);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((Maria) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
