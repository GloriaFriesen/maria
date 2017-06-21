package com.gloria.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gloria.game.Maria;

import java.util.ArrayList;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected ArrayList<TiledMapTileLayer.Cell> cells = new ArrayList<TiledMapTileLayer.Cell>();

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Maria.PPM, (bounds.getY() + bounds.getHeight() / 2) / Maria.PPM);

        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth() / 2 / Maria.PPM, bounds.getHeight() / 2 / Maria.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);

    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public ArrayList<TiledMapTileLayer.Cell> getCells() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        int xCoordinate = (int) (body.getPosition().x * Maria.PPM / 16);
        int yCoordinate = (int) (body.getPosition().y * Maria.PPM / 16);
        cells.add(layer.getCell(xCoordinate, yCoordinate));
        cells.add(layer.getCell(xCoordinate + 1, yCoordinate));
        cells.add(layer.getCell(xCoordinate + 2, yCoordinate));
        cells.add(layer.getCell(xCoordinate - 1, yCoordinate));
        cells.add(layer.getCell(xCoordinate, yCoordinate + 1));
        cells.add(layer.getCell(xCoordinate, yCoordinate - 1));
        cells.add(layer.getCell(xCoordinate, yCoordinate - 2));
        cells.add(layer.getCell(xCoordinate - 1, yCoordinate + 1));
        cells.add(layer.getCell(xCoordinate - 1, yCoordinate - 1));
        cells.add(layer.getCell(xCoordinate - 1, yCoordinate - 2));
        cells.add(layer.getCell(xCoordinate + 1, yCoordinate + 1));
        cells.add(layer.getCell(xCoordinate + 1, yCoordinate - 1));
        cells.add(layer.getCell(xCoordinate + 1, yCoordinate - 2));
        cells.add(layer.getCell(xCoordinate + 2, yCoordinate + 1));
        cells.add(layer.getCell(xCoordinate + 2, yCoordinate - 1));
        cells.add(layer.getCell(xCoordinate + 2, yCoordinate - 2));
        return cells;
    }
}
