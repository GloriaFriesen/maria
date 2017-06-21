package com.gloria.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.gloria.game.Maria;
import com.gloria.game.scenes.Hud;
import com.gloria.game.sprites.MariaSprite;
import com.gloria.game.tools.B2WorldCreator;
import com.gloria.game.tools.WorldContactListener;

public class PlayScreen implements Screen {
    private Maria game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private MariaSprite player;
    private TextureAtlas atlas;

    public PlayScreen(Maria game) {
        atlas = new TextureAtlas("ninjacat.txt");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Maria.V_WIDTH / Maria.PPM, Maria.V_HEIGHT / Maria.PPM, gameCam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("maria.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Maria.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        player = new MariaSprite(world, this);
        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 1)
            player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(.5f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-.5f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float delta) {
        handleInput(delta);
        world.step(1/10f, 6, 2);
        player.update(delta);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
