package com.gloria.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.gloria.game.Maria;

import java.util.ArrayList;

public class Brick extends InteractiveTileObject {
    private ArrayList<TiledMapTileLayer.Cell> brickCells;

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Maria.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(Maria.DESTROYED_BIT);
        brickCells = getCells();
        for (TiledMapTileLayer.Cell brick : brickCells) {
            brick.setTile(null);
        }

    }
}
